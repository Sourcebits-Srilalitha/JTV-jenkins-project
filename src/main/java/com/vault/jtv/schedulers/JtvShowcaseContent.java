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
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.ShowcaseCollectionItems;
import com.vault.jtv.services.CollectionService;
import com.vault.jtv.services.ShowcaseService;
import com.vault.util.MessageConstants;

@Service
public class JtvShowcaseContent {
	
	
	@Autowired
	private CollectionService collectionService;
	
	@Autowired
	private ShowcaseService showcaseService;
	
	@Value("${showcase.url.wp}")
	private String showcaseUrl;
	
	public void getShowcaseContent(){
		
		JsonObject showcaseResponseJson = getShowcaseJsonFromWP(showcaseUrl);
		if(showcaseResponseJson != null){
			List<ShowcaseCollectionItems> saveList = getShowcaseContentFromJson(showcaseResponseJson);
			if(saveList != null && saveList.size() != 0){
				saveShowcaseItem(saveList);
			}
		}
		
	}
	
	//Getting Showcase Content from Wordpress
	private JsonObject getShowcaseJsonFromWP(String urlString){
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
	
	//getting Showcase Content from wp response
	private List<ShowcaseCollectionItems> getShowcaseContentFromJson(JsonObject jsonResponse){	
		List<ShowcaseCollectionItems> showcaseCollectionItems = new ArrayList<ShowcaseCollectionItems>();
		List<Integer> collectionIds = null;
		int itemOrder;
		int collectionOrder;
		try {
			if(jsonResponse.has(MessageConstants.PAGE)){
				JsonObject mainObject = jsonResponse.getAsJsonObject(MessageConstants.PAGE);
				if(mainObject != null && mainObject.has(MessageConstants.CHILDREN)){
					JsonArray showcaseChilds = mainObject.getAsJsonArray(MessageConstants.CHILDREN);
					collectionOrder = 0;
					collectionIds = new ArrayList<>();
					for(JsonElement eachElement : showcaseChilds){
						try {
							JsonObject eachShowcase = (JsonObject)eachElement;
							if(eachShowcase.has(MessageConstants.TITLE)){
								Collection collection = collectionService.getCollectionByName(eachShowcase.get(MessageConstants.TITLE).getAsString());
								if(collection == null){
									collection = new Collection();									
									collection.setCreated_on(new Date());
									collection.setModified_on(new Date());
									collection.setDescription(HtmlUtils.htmlUnescape(eachShowcase.get(MessageConstants.CONTENT).getAsString()).replaceAll(MessageConstants.P, "").replaceAll(MessageConstants.P_C, ""));
									collection.setName(eachShowcase.get(MessageConstants.TITLE).getAsString());
									if(eachShowcase.has(MessageConstants.CUSTOM_FIELDS)){
										JsonObject custom_fields = eachShowcase.getAsJsonObject(MessageConstants.CUSTOM_FIELDS);
										if(custom_fields.has(MessageConstants.IMAGE_URL)){
											collection.setImageUrl(custom_fields.get(MessageConstants.IMAGE_URL).getAsString());
										}										
										collection.setActive(custom_fields.get(MessageConstants.ACTIVE).getAsString());
										collection.setCollectionType(custom_fields.get(MessageConstants.COLLECTIONTYPE).getAsInt());
									}
									collection.setPage_index(++collectionOrder);
									collection = collectionService.save(collection);
								} else {
									collection.setModified_on(new Date());
									collection.setDescription(HtmlUtils.htmlUnescape(eachShowcase.get(MessageConstants.CONTENT).getAsString()).replaceAll(MessageConstants.P, "").replaceAll(MessageConstants.P_C, ""));
									if(eachShowcase.has(MessageConstants.CUSTOM_FIELDS)){
										JsonObject custom_fields = eachShowcase.getAsJsonObject(MessageConstants.CUSTOM_FIELDS);
										if(custom_fields.has(MessageConstants.IMAGE_URL)){
											collection.setImageUrl(custom_fields.get(MessageConstants.IMAGE_URL).getAsString());
										}										
										collection.setActive(custom_fields.get(MessageConstants.ACTIVE).getAsString());
										collection.setCollectionType(custom_fields.get(MessageConstants.COLLECTIONTYPE).getAsInt());
									}
									collection.setPage_index(++collectionOrder);
									collection = collectionService.save(collection);
								}
								if(collection != null){
									collectionIds.add(collection.getId());
									if(eachShowcase.has(MessageConstants.CHILDREN)){
										JsonArray childShowcaseitems = eachShowcase.getAsJsonArray(MessageConstants.CHILDREN);
										itemOrder = 0;
										for(JsonElement eachShowcaseItemElement : childShowcaseitems){
											JsonObject eachShowcaseItem = (JsonObject)eachShowcaseItemElement;											
											ShowcaseCollectionItems showcaseItem = new ShowcaseCollectionItems();
											
											showcaseItem.setPage_index(++itemOrder);
											showcaseItem.setCollection(collection.getName());
											showcaseItem.setCollectionId(collection.getId());
											showcaseItem.setTitle(HtmlUtils.htmlUnescape(eachShowcaseItem.get(MessageConstants.TITLE).getAsString()));
											showcaseItem.setDescriptionShort(HtmlUtils.htmlUnescape(eachShowcaseItem.get(MessageConstants.CONTENT).getAsString()).replaceAll(MessageConstants.P, "").replaceAll(MessageConstants.P_C, ""));
											if(eachShowcaseItem.has(MessageConstants.CUSTOM_FIELDS)){
												JsonObject custom_fields = eachShowcaseItem.getAsJsonObject(MessageConstants.CUSTOM_FIELDS);
												if(custom_fields.has(MessageConstants.IMAGE_NAME)){
													showcaseItem.setImageName(custom_fields.get(MessageConstants.IMAGE_NAME).getAsString());
												}												
												showcaseItem.setGpCommName(custom_fields.get(MessageConstants.GPCOMMNAME).getAsString());
												showcaseItem.setGpNature(custom_fields.get(MessageConstants.GP_NATURE).getAsString());
												showcaseItem.setVisible(custom_fields.get(MessageConstants.VISIBLE).getAsString());
												showcaseItem.setDeleted(custom_fields.get(MessageConstants.DELETED).getAsString());
												if(custom_fields.has(MessageConstants.SUBTITLE)){
													showcaseItem.setSubtitle(custom_fields.get(MessageConstants.SUBTITLE).getAsString());
												}
											}											
											showcaseCollectionItems.add(showcaseItem);
										}										
									}
								}															
							}							
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}
					if(collectionIds != null){
						collectionService.updateAsInactive(collectionIds, MessageConstants.IN_ACTIVE);
					}					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showcaseCollectionItems;
	}
	
	//Save and update the Showcase Items
	private void saveShowcaseItem(List<ShowcaseCollectionItems> saveList){
		List<Integer> showcaseIdList = new ArrayList<>();
		try {
			for(ShowcaseCollectionItems item : saveList){
				ShowcaseCollectionItems resultItem = showcaseService.getShowcaseItemByName(item.getTitle(), item.getCollection());
				if(resultItem != null){
					resultItem.setPage_index(item.getPage_index());
					resultItem.setGpCommName(item.getGpCommName());
					resultItem.setGpNature(item.getGpNature());
					resultItem.setDescriptionShort(item.getDescriptionShort());
					resultItem.setVisible(item.getVisible());
					resultItem.setImageName(item.getImageName());
					try {
						showcaseIdList.add(showcaseService.save(resultItem).getId());
					} catch (Exception e) {}
					
				} else {
					try {
						showcaseIdList.add(showcaseService.save(item).getId());
					} catch (Exception e) {}					
				}
			}
			showcaseService.updateInactive(showcaseIdList, MessageConstants.IN_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
