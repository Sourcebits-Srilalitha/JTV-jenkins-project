package com.vault.jtv.schedulers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vault.jtv.model.Article;
import com.vault.jtv.model.ArticleImages;
import com.vault.jtv.model.ArticleTypes;
import com.vault.jtv.repository.ArticleImagesRep;
import com.vault.jtv.repository.ArticleRep;
import com.vault.jtv.services.ArticleTypeService;
import com.vault.util.MessageConstants;

@Service
public class JtvArticleIndex {
	private static Logger logger = LoggerFactory.getLogger(JtvArticleIndex.class);

	@Value("${article.Index.url}")
	private String articleIndexUrl;

	@Autowired
	ArticleRep articleRep;

	@Autowired
	ArticleTypeService articleTypeService;

	@Autowired
	ArticleImagesRep imagerep;

	@Autowired
	JtvArticleContent jtvArticleContent;

	@Value("${article.Index.url.wp}")
	private String articleIndexUrlWp;

	private static Map<String, Integer> contentOrderMap = new HashMap<String, Integer>();
	private static Map<String, Date> contentDateMap = new HashMap<String, Date>();
	private static Map<String, String> contentSeriesMap = new HashMap<String, String>();
	private static Map<String, String> contentHeroImageMap = new HashMap<String, String>();

	/**
	 * Getting the Article Content_id from Json and check whether it exists in
	 * local database IF exists, then update it by adding the modified date If
	 * new , then add If article exists in local database and not there in the
	 * json, mark it as deleted
	 * 
	 * @throws Exception
	 */
	public void getArticleIndex() throws Exception {
		List<String> articlesToShowIndexList = new ArrayList<String>(); // JTV
		List<String> existingActiveArticleIndexList = new ArrayList<String>(); // db
		List<String> AddArticleIndexList = new ArrayList<String>();// derived
		List<String> UpdateInactiveArticlesIndexList = new ArrayList<String>();// derived
		List<String> UpdateActiveArticlesIndexList = new ArrayList<String>();// derived
		try {
			// Code for getting ContentId from Wordpress response
			JsonObject jsonResponse = getArticleJsonFromWP(articleIndexUrlWp);
			if (jsonResponse != null) {
				articlesToShowIndexList = getArticleContentId(jsonResponse);
				logger.info(MessageConstants.CONTENTORDERMAP + contentOrderMap);
			}

			if (articlesToShowIndexList.size() != 0) {
				// that's the existing list of active articles
				existingActiveArticleIndexList = articleRep.findContentId();
				for (String temp : articlesToShowIndexList) {
					AddArticleIndexList.add(temp);
					UpdateActiveArticlesIndexList.add(temp);
				}
				// removes the ones that are already in the table as active
				AddArticleIndexList.removeAll(existingActiveArticleIndexList);

				for (String temp : existingActiveArticleIndexList) {
					UpdateInactiveArticlesIndexList.add(temp);
				}
				// removes all articles that are in the toshow list
				UpdateInactiveArticlesIndexList.removeAll(articlesToShowIndexList);
				if (UpdateInactiveArticlesIndexList.size() != 0) {
					articleRep.updateArticles(MessageConstants.IN_ACTIVE, UpdateInactiveArticlesIndexList);
				}
				// this should remove articles that are new to the sytem,
				// resulting in article list that are already existing
				UpdateActiveArticlesIndexList.removeAll(AddArticleIndexList);

				updateArticles(UpdateActiveArticlesIndexList);

				newArticles(AddArticleIndexList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	private void updateArticles(List<String> updateArticlesList) {
		Article art = new Article();
		ArticleImages aImg = null;
		logger.info(MessageConstants.START_UPD_ARTICLES);

		for (String newId : updateArticlesList) {
			try {
				logger.info(MessageConstants.UPD_ARTICLES_CONTENTID + newId);
				art = jtvArticleContent.getArticle(newId);
				if (art.getBody() != null) {
					Article existingArticle = articleRep.findByContentId(newId);
					if (existingArticle != null) {
						existingArticle.setBody(art.getBody());
						existingArticle.setTitle(art.getTitle());
						existingArticle.setSeries(contentSeriesMap.get(newId));
						existingArticle.setArticletype(getArticleType(existingArticle.getSeries()));
						existingArticle.setDescription(art.getDescription());
						// remove all article images for this article entity in
						// the article image table
						imagerep.deleteArticleImages(existingArticle.getId());

						// add hero image from WordPress
						if (null != contentHeroImageMap && contentHeroImageMap.size() > 0
								&& null != contentHeroImageMap.get(newId)) {
							aImg = new ArticleImages();
							aImg.setImageurl(contentHeroImageMap.get(newId));
							aImg.setIsHeroImage(MessageConstants.IS_ACTIVE);
						}
						if (null != aImg) {
							art.getArticleImages().add(aImg);
						}
						//

						for (ArticleImages ai : art.getArticleImages()) {
							// add id to all image entities in collection
							ai.setArticle_id(existingArticle.getId());
						}

						existingArticle.setActive(MessageConstants.IS_ACTIVE);
						existingArticle.setArticleImages(art.getArticleImages());
						existingArticle.setPage_index(contentOrderMap.get(newId));
						existingArticle.setModified_on(contentDateMap.get(newId));
						existingArticle.setPublished(art.getPublished());
						articleRep.save(existingArticle);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(MessageConstants.NO_VALID_JSON_ARTICLE_ID + newId + "'");
			}
		}
		logger.info(MessageConstants.END_ARTICLES_UPD);
	}

	@Transactional
	private void newArticles(List<String> AddArticleIndexList) {
		logger.info(MessageConstants.START_ARTICLES);
		try {
			Article art = new Article();

			for (String newId : AddArticleIndexList) {
				try {
					art = jtvArticleContent.getArticle(newId);
				} catch (Exception e) {
					logger.error(MessageConstants.NEW_ARTICLE_EXP, e);
				}
				if (art.getBody() != null) {
					if (art.getBody().length() > 19000) {
						// this is a temporary workaround for the character
						// limitation of oracle varchar field
						art.setBody(art.getBody().substring(0, 19000) + "...");
					}
					art.setCreated_on(new Date());
					art.setModified_on(contentDateMap.get(newId));
					art.setActive(MessageConstants.IS_ACTIVE);
					art.setPage_index(contentOrderMap.get(newId));
					art.setSeries(contentSeriesMap.get(newId));
					art.setArticletype(getArticleType(art.getSeries()));
					List<ArticleImages> tempImages = new ArrayList<ArticleImages>();
					if (null != art.getArticleImages()) {
						// temporary save the article images collection
						tempImages = art.getArticleImages();
					}

					// add hero image from WordPress
					if (null != contentHeroImageMap && contentHeroImageMap.size() > 0
							&& null != contentHeroImageMap.get(newId)) {
						ArticleImages aImg = new ArticleImages();
						aImg.setImageurl(contentHeroImageMap.get(newId));
						aImg.setIsHeroImage(MessageConstants.IS_ACTIVE);
						tempImages.add(aImg);
					}

					// remove image collection from article entity for separate
					// save/update if article entity
					art.setArticleImages(new ArrayList<ArticleImages>());
					try {
						// save/update article entity to get id
						Article art1 = articleRep.save(art);
						if (null != art1) {
							// remove all article images for this article entity
							// in the article image table
							imagerep.deleteArticleImages(art.getId());
							for (ArticleImages ai : tempImages) {
								// add id to all image entities in collection
								ai.setArticle_id(art1.getId());
							}
							// add collection to entity returned from previous
							// save/update
							art1.setArticleImages(tempImages);
							// save/update new article entity with corresponding
							// image collection
							articleRep.save(art1);
						}
					} catch (Exception e) {
						logger.error(MessageConstants.SAVE_DEL_ARTICLE, e);
					}
				} else {
					logger.info(MessageConstants.NO_VALID_JSON);
				}
			}
		} catch (Exception e) {
			logger.error(MessageConstants.EXP_SCH, e);
		}
		logger.info(MessageConstants.END_ARTICLES);
	}

	// Rest call for Wordpress
	private JsonObject getArticleJsonFromWP(String urlstring) {
		RestTemplate restTemplate = new RestTemplate();
		JsonObject jsonResponse = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add(MessageConstants.CONTENT_TYPE, MessageConstants.APP_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(urlstring, HttpMethod.GET, entity, String.class);
		if (response.getStatusCode().value() == 200) {
			jsonResponse = (JsonObject) new JsonParser().parse(response.getBody());
		}
		return jsonResponse;
	}

	// getting contentId from wp response
	@Transactional
	private List<String> getArticleContentId(JsonObject jsonResponse) {

		List<String> articlesToShowIndexList = new ArrayList<String>();
		List<Integer> articleTypeIds = null;
		int pageOrder;
		int articleTypeOrder;
		try {
			if (jsonResponse.has(MessageConstants.PAGE)) {
				JsonObject mainObject = jsonResponse.getAsJsonObject(MessageConstants.PAGE);
				if (mainObject != null && mainObject.has(MessageConstants.CHILDREN)) {
					JsonArray articleChilds = mainObject.getAsJsonArray(MessageConstants.CHILDREN);
					articleTypeOrder = 0;
					articleTypeIds = new ArrayList<>();
					contentOrderMap = new HashMap<>();
					for (JsonElement eachElement : articleChilds) {
						JsonObject eachAtricle = (JsonObject) eachElement;
						ArticleTypes type = null;
						type = articleTypeService.findByTitle(eachAtricle.get(MessageConstants.TITLE).getAsString());
						if (type != null) {
							type.setModified_on(new Date());
							type.setActive(MessageConstants.IS_ACTIVE);
							type.setPage_index(++articleTypeOrder);
							try {
								articleTypeIds.add(articleTypeService.save(type).getId());
							} catch (Exception e) {
							}
						} else {
							type = new ArticleTypes();
							type.setActive(MessageConstants.IS_ACTIVE);
							type.setCreated_on(new Date());
							type.setModified_on(new Date());
							type.setTitle(eachAtricle.get(MessageConstants.TITLE).getAsString());
							type.setPage_index(++articleTypeOrder);
							try {
								articleTypeIds.add(articleTypeService.save(type).getId());
							} catch (Exception e) {
							}
						}
						if (eachAtricle.has(MessageConstants.CHILDREN)) {
							JsonArray childArticles = eachAtricle.getAsJsonArray(MessageConstants.CHILDREN);
							pageOrder = 0;
							try {
								for (JsonElement eachChildElement : childArticles) {
									JsonObject eachChildAtricle = (JsonObject) eachChildElement;
									if (eachChildAtricle.has(MessageConstants.CUSTOM_FIELDS)) {
										JsonObject custom_fields = eachChildAtricle.getAsJsonObject(MessageConstants.CUSTOM_FIELDS);
										if (custom_fields.has(MessageConstants.CONTENT_ID)) {
											articlesToShowIndexList.add(custom_fields.get(MessageConstants.CONTENT_ID).getAsString());
											contentOrderMap.put(custom_fields.get(MessageConstants.CONTENT_ID).getAsString(),
													++pageOrder);
											contentDateMap.put(custom_fields.get(MessageConstants.CONTENT_ID).getAsString(),
													new SimpleDateFormat(MessageConstants.DATE_FRMT)
															.parse(eachChildAtricle.get(MessageConstants.MODIFIED).getAsString()));
											contentSeriesMap.put(custom_fields.get(MessageConstants.CONTENT_ID).getAsString(),
													eachAtricle.get(MessageConstants.TITLE).getAsString());
											if (custom_fields.has(MessageConstants.ISHEROIMAGE)) {
												contentHeroImageMap.put(custom_fields.get(MessageConstants.CONTENT_ID).getAsString(),
														custom_fields.get(MessageConstants.ISHEROIMAGE).getAsString());
											}
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					if (articleTypeIds != null) {
						articleTypeService.updateInactive(articleTypeIds, MessageConstants.IN_ACTIVE);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articlesToShowIndexList;
	}

	@Transactional
	private Integer getArticleType(String series) {
		Integer articlesTypeId = null;
		try {
			ArticleTypes articletype = articleTypeService.findByTitle(series);
			if (articletype != null) {
				articlesTypeId = articletype.getId();
			} else {
				articletype = new ArticleTypes(series, MessageConstants.IS_ACTIVE, new Date(), null);
				ArticleTypes newArticletype = articleTypeService.save(articletype);
				articlesTypeId = newArticletype.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articlesTypeId;
	}

}
