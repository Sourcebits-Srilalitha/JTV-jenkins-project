package com.vault.jtv.schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vault.jtv.model.Article;
import com.vault.jtv.model.ArticleImages;
import com.vault.util.MessageConstants;

@Service
public class JtvArticleContent {



	@Value("${article.Content.new.url}")
	private String articleNewUrl;

	private static Logger logger = LoggerFactory.getLogger(JtvArticleIndex.class);

	/**
	 * Reading the Article Content JSON file and prepare Article object
	 *
	 * @param contentId
	 * @return Article object to getArticleIndex()
	 * @throws Exception
	 */
	public Article getArticle(String contentId) {
		Article article = new Article();
		List<ArticleImages> images = new ArrayList<ArticleImages>();
		String urslstring = articleNewUrl + contentId.toString() + MessageConstants.FORMAT_JSON;
		HashMap<String, Object> articleJson = null;
		logger.info(MessageConstants.URL_STRING + urslstring);
		String headerTitle = "";
		String pageDescription = "";
		String pubDate = "";
		String keptPubDate = "";
		String nohtml = "";
		List<String> content = new ArrayList<String>();
		List<String> imageList = new ArrayList<String>();

		try {
			articleJson = getArticleJsonContent(urslstring);
			if (null != articleJson) {

				if (null != articleJson.get(MessageConstants.CONTENT_TITLE)) {
					content = (List<String>) articleJson.get(MessageConstants.CONTENT_TITLE);
				}
				if (null != articleJson.get(MessageConstants.URL_LIST)) {
					imageList = (List<String>) articleJson.get(MessageConstants.URL_LIST);
				}

				// Content ID
				article.setContentId(contentId);

				// Page Description
				if (null != articleJson.get(MessageConstants.PAGE_DESCRIPTION)) {
					pageDescription = (String) articleJson.get(MessageConstants.PAGE_DESCRIPTION);
				}
				article.setDescription(pageDescription);

				if (content.size() > 0) {

					// Title
					headerTitle = (null != content.get(0)) ? content.get(0) : headerTitle;
					article.setTitle(headerTitle);

					// Published Date
					pubDate = (null != content.get(1)) ? content.get(1) : pubDate;
					if (pubDate.trim().length() > 0) {
						nohtml = pubDate.replaceAll("\\<.*?>", "");
					}
					boolean isFound = nohtml.indexOf(MessageConstants.BY) != -1 ? true : false;
					if (isFound) {
						keptPubDate = nohtml.substring(0, nohtml.indexOf(MessageConstants.BY));
						article.setPublished(keptPubDate);
					} else {
						keptPubDate = nohtml;
						article.setPublished(keptPubDate);
					}

					content.remove(0);
					content.remove(0);

					// Body
					article.setBody(String.join("", content));

				}

				if (imageList.size() > 0) {
					for (String imageUrl : imageList) {
						ArticleImages articleImage = new ArticleImages();
						articleImage.setImageurl(MessageConstants.BASE_IMAGE_URL + imageUrl);
						articleImage.setIsHeroImage(MessageConstants.IS_HERO_IMAGE);
						images.add(articleImage);
					}
					article.setArticleImages(images);
				}
			}

		} catch (Exception e) {			
			logger.error(MessageConstants.EXP_ARTICLE,e);
		}
		return article;
	}

	/**
	 * This method is getting Article content from JTV Server by content ID
	 *
	 * @param urlString
	 * @return Article content String
	 */
	private HashMap<String, Object> getArticleJsonContent(String urlString) {
		logger.info(MessageConstants.GET_ARTICLE_JSON);
		RestTemplate restTemplate = new RestTemplate();
		HashMap<String, Object> res = new HashMap<>();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add(MessageConstants.CONTENT_TYPE, MessageConstants.APP_JSON);
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<String> response = restTemplate.exchange(urlString, HttpMethod.GET, request, String.class);

			if (response.getStatusCode().value() == 200) {
				if (null != response.getBody()) {
					JsonObject jsonObject = (JsonObject) new JsonParser().parse(response.getBody());
					if (null != jsonObject) {
						res = createHashMapFromJsonString(jsonObject, res);
					}
				}
			}
		} catch (Exception e) {			
			logger.error(MessageConstants.EXP_GET_ARTICLE_JSON,e);
		}
		return res;

	}

	private HashMap<String, Object> createHashMapFromJsonString(JsonObject object, HashMap<String, Object> map) {
		Set<Map.Entry<String, JsonElement>> set = object.entrySet();
		for (Map.Entry<String, JsonElement> entry : set) {
			try {
				String key = entry.getKey();
				JsonElement value = entry.getValue();
				if (null != value) {
					processContent(map, key, value);
					processUrlList(map, key, value);
					processPageDescription(map, key, value);
					if (value.isJsonObject()) {
						createHashMapFromJsonString(value.getAsJsonObject(), map);
					} else if (value.isJsonArray() && value.toString().contains(":")) {
						processJSONArray(map, value);
					}
				}
			} catch (Exception e) {				
				logger.error(MessageConstants.EXP_CREATE_HM,e);
			}
		}
		return map;
	}

	private void processJSONArray(HashMap<String, Object> map, JsonElement value) {
		JsonArray array = value.getAsJsonArray();
		if (null != array) {
			for (JsonElement element : array) {
				if (!element.toString().contains(MessageConstants.RELATED_ARTICLES)) {
					createHashMapFromJsonString(element.getAsJsonObject(), map);
				}
			}

		}
	}

	private void processPageDescription(HashMap<String, Object> map, String key, JsonElement value) {
		if (shouldAdd(key, value, MessageConstants.PAGE_DESCRIPTION)) {
			String pageDesc = value.getAsString();
			map.put(MessageConstants.PAGE_DESCRIPTION, pageDesc);
		}
	}

	private boolean shouldAdd(String key, JsonElement value, String elementDescription) {
		return key.equalsIgnoreCase(elementDescription) && !value.isJsonNull() && !value.getAsString().isEmpty();
	}

	private void processUrlList(HashMap<String, Object> map, String key, JsonElement value) {
		List<String> urlList = map.get(MessageConstants.URL_LIST) != null ? (List<String>) map.get(MessageConstants.URL_LIST) : new ArrayList<>();
		if (shouldAdd(key, value, MessageConstants.URI)) {
			urlList.add(value.getAsString());
		}
		map.put(MessageConstants.URL_LIST, urlList);
	}

	private void processContent(HashMap<String, Object> map, String key, JsonElement value) {
		List<String> title = map.get(MessageConstants.CONTENT_TITLE) != null ? (List<String>) map.get(MessageConstants.CONTENT_TITLE) : new ArrayList<>();
		if (shouldAdd(key, value, MessageConstants.CONTENT_TITLE)) {
			title.add(value.getAsString());
		}
		if (shouldAdd(key, value, MessageConstants.CONTENT)) {
			title.add(value.getAsString());
		}
		map.put(MessageConstants.CONTENT_TITLE, title);
	}	
}