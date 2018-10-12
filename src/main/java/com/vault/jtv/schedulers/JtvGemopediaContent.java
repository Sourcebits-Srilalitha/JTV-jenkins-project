package com.vault.jtv.schedulers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vault.jtv.model.GemopediaSliderItems;
import com.vault.jtv.services.GemService;
import com.vault.jtv.services.GemopediaSliderService;
import com.vault.util.MessageConstants;

@Service
public class JtvGemopediaContent {
	
	
	@Autowired
	private GemService gemService;
	
	@Autowired
	private GemopediaSliderService gemopediaSliderservice;
	
	@Value("${gemopedia.url.wp}")
	private String gemopediaUrl;
	
	private static Logger logger = LoggerFactory.getLogger(JtvGemopediaContent.class);
	
	public void getGemopediaContent(){		
		JsonObject gemopediaResponseJson = getGemopediaJsonFromWP(gemopediaUrl);
		if(gemopediaResponseJson != null){
			 getGemopediaItemsFromJson(gemopediaResponseJson);			
		}
		
	}
	
	//Getting gemopedia Content from Wordpress
	private JsonObject getGemopediaJsonFromWP(String urlString){
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
	
	//getting getGemopediaItems from wp response
	private void getGemopediaItemsFromJson(JsonObject jsonResponse) {			
		int gemItemOrder=0;
		List<Integer> gemIdList=null;
		try {
			if (jsonResponse.has(MessageConstants.PAGE)) {
				JsonObject mainObject = jsonResponse.getAsJsonObject(MessageConstants.PAGE);
				if(mainObject != null && mainObject.has(MessageConstants.CHILDREN)){	
					JsonArray gemopediaChilds = mainObject.getAsJsonArray(MessageConstants.CHILDREN);
					gemIdList = new ArrayList<>();
					for(JsonElement eachElement : gemopediaChilds){
						JsonObject eachgem = (JsonObject)eachElement;
						if(eachgem.has(MessageConstants.CUSTOM_FIELDS)){						
							JsonObject custom_fields = eachgem.getAsJsonObject(MessageConstants.CUSTOM_FIELDS);
							if(custom_fields.has(MessageConstants.GPCOMMNAME)){	
								logger.info(MessageConstants.GP_NAME+custom_fields.get(MessageConstants.GPCOMMNAME).getAsString());
								Integer gemopediaId=gemService.getGemIDByCommonName(custom_fields.get(MessageConstants.GPCOMMNAME).getAsString());
								if(null != gemopediaId){
									logger.info(MessageConstants.GEMOPEDIAID+gemopediaId);
									gemIdList.add(gemopediaId);
									GemopediaSliderItems gemItems = gemopediaSliderservice.findByGemID(gemopediaId);
									if(null == gemItems){
										gemItems=new GemopediaSliderItems();
										gemItems.setGemopediaId(gemopediaId);
										gemItems.setCreated_on(new Date());
										gemItems.setModified_on(new Date());
										gemItems.setActive(custom_fields.get(MessageConstants.ACTIVE).getAsString());
										gemItems.setPage_index(++gemItemOrder);
										gemItems=gemopediaSliderservice.save(gemItems);
									}else{
										gemItems.setModified_on(new Date());
										gemItems.setActive(custom_fields.get(MessageConstants.ACTIVE).getAsString());
										gemItems.setPage_index(++gemItemOrder);
										gemItems=gemopediaSliderservice.save(gemItems);
									}
								}									
							}							
						}			
						
					}
					logger.info(MessageConstants.LIST_SIZE+gemIdList.size());
					if(null != gemIdList && gemIdList.size() >0){
						gemopediaSliderservice.updateAsInactive(gemIdList, MessageConstants.IN_ACTIVE);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}			
		
	
	
}
