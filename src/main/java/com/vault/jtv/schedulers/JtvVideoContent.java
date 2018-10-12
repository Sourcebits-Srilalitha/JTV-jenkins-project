package com.vault.jtv.schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vault.jtv.model.Video;
import com.vault.jtv.model.VideoTypes;
import com.vault.jtv.services.VideoService;
import com.vault.jtv.services.VideoTypeService;
import com.vault.util.MessageConstants;

@Service
public class JtvVideoContent {
	
	@Autowired
	VideoTypeService videoTypeService;
	
	@Autowired
	VideoService videoService;
	
	@Value("${video.url.wp}")
	private String videoUrl;
	
	public void getVideoContent(){
		JsonObject videoContentJson = getVideoContentFromWP(videoUrl);
		if(videoContentJson != null){
			List<Video> saveList = getVideoContentFromJson(videoContentJson);
			if(saveList != null && saveList.size() != 0){
				saveVideoItems(saveList);
			}
		}
	}
	
	//Getting Showcase Content from Wordpress
	private JsonObject getVideoContentFromWP(String urlString){
		RestTemplate restTemplate = new RestTemplate();
    	JsonObject jsonResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add(MessageConstants.CONTENT_TYPE, MessageConstants.APP_JSON);
		HttpEntity<String> entity = new HttpEntity<String>( headers);
		ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, entity, String.class);	
		if(response.getStatusCode().value() == 200){
			jsonResponse = (JsonObject) new JsonParser().parse(response.getBody());
		}
		return jsonResponse;
	}
	
	//getting Video Content from wp response and saving/update the videotypes
	private List<Video> getVideoContentFromJson(JsonObject jsonResponse){
		List<Video> saveList = new ArrayList<Video>();
		List<Integer> videoTypeIdList = null;
		int videoOrder;
		int videoTypeOrder;
		try {			
			if(jsonResponse.has(MessageConstants.PAGE)){
				JsonObject mainObject = jsonResponse.getAsJsonObject(MessageConstants.PAGE);
				if(mainObject != null && mainObject.has(MessageConstants.CHILDREN)){
					JsonArray videoTypesChilds = mainObject.getAsJsonArray(MessageConstants.CHILDREN);
					videoTypeOrder = 0;
					videoTypeIdList = new ArrayList<>();
					for(JsonElement eachElement : videoTypesChilds){
						try {
							JsonObject eachVideoType = (JsonObject)eachElement;
							if(eachVideoType.has(MessageConstants.TITLE)){
								VideoTypes videoType = videoTypeService.getByName(eachVideoType.get(MessageConstants.TITLE).getAsString(), MessageConstants.IS_ACTIVE);
								if(videoType == null){
									videoType = new VideoTypes();	
									videoType.setActive(MessageConstants.IS_ACTIVE);
									videoType.setCreated_on(new Date());
									videoType.setModified_on(new Date());									
									videoType.setTitle(eachVideoType.get(MessageConstants.TITLE).getAsString());									
									videoType.setPage_index(++videoTypeOrder);
									videoType = videoTypeService.save(videoType);
								} else {
									videoType.setModified_on(new Date());
									videoType.setTitle(eachVideoType.get(MessageConstants.TITLE).getAsString());
									videoType.setPage_index(++videoTypeOrder);
									videoType = videoTypeService.save(videoType);
								}
								if(videoType != null){
									videoTypeIdList.add(videoType.getId());
									if(eachVideoType.has(MessageConstants.CHILDREN)){
										JsonArray videoItems = eachVideoType.getAsJsonArray(MessageConstants.CHILDREN);
										videoOrder = 0;
										for(JsonElement eachVideoItemElement : videoItems){
											JsonObject eachVideoItem = (JsonObject)eachVideoItemElement;
											
											Video video = new Video();
											
											video.setCreated_on(new Date());
											video.setModified_on(new Date());
											video.setPage_index(++videoOrder);
											video.setType(videoType.getId());											
											video.setTitle(eachVideoItem.get(MessageConstants.TITLE).getAsString());
											video.setDescription(HtmlUtils.htmlUnescape(eachVideoItem.get(MessageConstants.CONTENT).getAsString()).replaceAll(MessageConstants.P, "").replaceAll(MessageConstants.P_C, ""));
											if(eachVideoItem.has(MessageConstants.CUSTOM_FIELDS)){
												JsonObject custom_fields = eachVideoItem.getAsJsonObject(MessageConstants.CUSTOM_FIELDS);
												if(custom_fields.has(MessageConstants.LINK)){
													video.setLink(custom_fields.get(MessageConstants.LINK).getAsString());
												}												
												if(custom_fields.has(MessageConstants.ACTIVE)){
													video.setActive(custom_fields.get(MessageConstants.ACTIVE).getAsString());
												}												
											}											
											saveList.add(video);
										}										
									}
								}															
							}							
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}
					if(videoTypeIdList != null){
						videoTypeService.updateAsInactive(videoTypeIdList, MessageConstants.IN_ACTIVE);
					}
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveList;
	}
	
	private void saveVideoItems(List<Video> saveList){
		List<Integer> videoIdList = new ArrayList<>();
		try {
			for(Video item : saveList){
				Video resultItem = videoService.getVideoByName(item.getTitle(), item.getType());
				if(resultItem != null){
					resultItem.setModified_on(item.getModified_on());
					resultItem.setPage_index(item.getPage_index());					
					resultItem.setDescription(item.getDescription());
					resultItem.setActive(item.getActive());
					resultItem.setLink(item.getLink());
					try {
						videoIdList.add(videoService.saveVideo(resultItem).getId());
					} catch (Exception e) {}					
				} else {
					try {
						videoIdList.add(videoService.saveVideo(item).getId());
					} catch (Exception e) {}
				}	
			}
			videoService.updateAsInactive(videoIdList, MessageConstants.IN_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
