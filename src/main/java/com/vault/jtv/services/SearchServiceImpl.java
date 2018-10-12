package com.vault.jtv.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.beans.SearchBean;
import com.vault.jtv.model.Article;
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.model.Video;
import com.vault.jtv.repository.ArticleRep;
import com.vault.jtv.repository.CollectionItemRep;
import com.vault.jtv.repository.CollectionRep;
import com.vault.jtv.repository.GemopediaItemRep;
import com.vault.jtv.repository.ShowcaseRep;
import com.vault.jtv.repository.VideoRep;
import com.vault.util.MessageConstants;

@Service
public class SearchServiceImpl implements SearchService {

	// gems
	@Autowired
	GemopediaItemRep gemRepository;

	// videos
	@Autowired
	VideoRep videoRep;

	// articles
	@Autowired
	ArticleRep articleRep;

	// showcase
	@Autowired
	ShowcaseRep showcaseRep;

	// collectionItems
	@Autowired
	CollectionItemRep collectionItemRep;

	// collections
	@Autowired
	CollectionRep collectionRep;

	

	protected Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	private Map<String, Object> globalSearch(SearchBean searchBean) {
		logger.info(MessageConstants.GLOBAL_SEARCH_);
		List<GemopediaItem> gemList = null;
		List<Video> videoList = null;
		List<Article> articleList = null;
		List<Collection> showcaseCollectionNameList = null;
		// List<ShowcaseCollectionItems> showcaseItemList=null;
		List<Object> scCollectionNameList = null;
		Map<String, Object> gloabalSearch = new HashMap<String, Object>();
		try {
			String keyPrefix = MessageConstants.PERCENTAGE + searchBean.getSearchKey();
			String keySuffix = searchBean.getSearchKey() + MessageConstants.PERCENTAGE;
			String keyfix = MessageConstants.PERCENTAGE + searchBean.getSearchKey() + MessageConstants.PERCENTAGE;
			gemList = gemRepository.getAllGemSearch(keyPrefix, keySuffix, keyfix);
			if (null != gemList && gemList.size() > 0) {
				gloabalSearch.put(MessageConstants.GEMS, gemList);
			}

			videoList = videoRep.getAllVideoSearch(keyPrefix, keySuffix, keyfix, MessageConstants.IS_ACTIVE);
			if (null != videoList && videoList.size() > 0) {
				gloabalSearch.put(MessageConstants.VIDEOS, videoList);
			}

			articleList = articleRep.getAllArticleSearch(keyPrefix, keySuffix, keyfix, MessageConstants.IS_ACTIVE);
			if (null != articleList && articleList.size() > 0) {
				gloabalSearch.put(MessageConstants.ARTICLES, articleList);
			}

			showcaseCollectionNameList = collectionRep.getAllShowcaseCollection(keyPrefix, keySuffix, keyfix,
					MessageConstants.IS_ACTIVE);
			if (null != showcaseCollectionNameList && showcaseCollectionNameList.size() > 0) {
				scCollectionNameList = new ArrayList<>();
				for (Collection eachShowcase : showcaseCollectionNameList) {
					int itemCount = showcaseRep.getAllShowcaseDetails(eachShowcase.getId(), MessageConstants.IS_ACTIVE);
					Map<String, Object> scMap = new HashMap<>();
					scMap.put(MessageConstants.COLLECTION, eachShowcase);
					scMap.put(MessageConstants.ITEM_COUNT, itemCount);
					scCollectionNameList.add(scMap);
				}
				if (null != scCollectionNameList) {
					gloabalSearch.put(MessageConstants.SHOWCASE_COLLECTION_NAME, scCollectionNameList);
				}
			}
			// This section is not required
			/*
			 * showcaseItemList=showcaseRep.getAllShowcaseItemSearch(keyPrefix,
			 * keySuffix,keyfix,IS_ACTIVE); if(null != showcaseItemList &&
			 * showcaseItemList.size() > 0){
			 * gloabalSearch.put("ShowcaseItemList", showcaseItemList); }
			 */
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return gloabalSearch;
	}

	private Map<String, Object> globalSearchVault(SearchBean searchBean) {
		logger.info(MessageConstants.GLOBAL_SEARCH_VAULT);
		List<CollectionItems> collectionItemsList = null;
		List<Collection> collectionList = null;
		List<Object> customCollectionList = null;
		Map<String, Object> globalSearchVault = new HashMap<String, Object>();
		try {
			String keyPrefix = MessageConstants.PERCENTAGE + searchBean.getSearchKey();
			String keySuffix = searchBean.getSearchKey() + MessageConstants.PERCENTAGE;
			String keyfix = MessageConstants.PERCENTAGE + searchBean.getSearchKey() + MessageConstants.PERCENTAGE;
			collectionList = collectionRep.getAllUserCollections(keyPrefix, keySuffix, keyfix, MessageConstants.IS_ACTIVE,
					searchBean.getUserId());
			if (null != collectionList && collectionList.size() > 0) {
				customCollectionList = new ArrayList<>();
				for (Collection eachCustom : collectionList) {
					int itemCount = collectionRep.getCustomCollectionDetails(MessageConstants.IS_ACTIVE, eachCustom.getId());
					Map<String, Object> customMap = new HashMap<>();
					customMap.put(MessageConstants.COLLECTION, eachCustom);
					customMap.put(MessageConstants.ITEM_COUNT, itemCount);
					customCollectionList.add(customMap);
				}
			}

			if (null != customCollectionList) {
				globalSearchVault.put(MessageConstants.CUSTOM_COLLECTION, customCollectionList);
			}

			collectionItemsList = collectionItemRep.getAllCollectionItems(keyPrefix, keySuffix, keyfix, MessageConstants.IS_ACTIVE,
					searchBean.getUserId());
			if (null != collectionItemsList && collectionItemsList.size() > 0) {
				globalSearchVault.put(MessageConstants.COLLECTION_ITEMS_LIST, collectionItemsList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return globalSearchVault;
	}

	private Map<String, Object> sortSearch(Map<String, Object> responseUser, Map<String, Object> response,
			SearchBean searchBean) {
		logger.info(MessageConstants.SORT_SEARCH);
		Map<String, Object> finalMap = new LinkedHashMap<>();
		try {
			// Add Order according to Screen :Discover 0 and Collection 1
			if (searchBean.getScreenFlag() == 0) {
				/*
				 * Gemopedia , Articles ,Videos ,Collection Showcase, Master
				 * Collection ,Custom Collections
				 */
				if (response != null) {
					if (response.containsKey(MessageConstants.GEMS)) {
						finalMap.put(MessageConstants.GEMOPEDIA_LABLE, response.get(MessageConstants.GEMS));
					}
					if (response.containsKey(MessageConstants.ARTICLES)) {
						finalMap.put(MessageConstants.ARTICLES_LABLE, response.get(MessageConstants.ARTICLES));
					}
					if (response.containsKey(MessageConstants.VIDEOS)) {
						finalMap.put(MessageConstants.VIDEOS_LABLE, response.get(MessageConstants.VIDEOS));
					}
					if (response.containsKey(MessageConstants.SHOWCASE_COLLECTION_NAME)) {
						finalMap.put(MessageConstants.COLLECTION_SHOWCASE_LABLE, response.get(MessageConstants.SHOWCASE_COLLECTION_NAME));
					}
				}

				if (responseUser != null) {
					if (responseUser.containsKey(MessageConstants.COLLECTION_ITEMS_LIST)) {
						finalMap.put(MessageConstants.GEMSTONES_LABLE, responseUser.get(MessageConstants.COLLECTION_ITEMS_LIST));
					}
					if (responseUser.containsKey(MessageConstants.CUSTOM_COLLECTION)) {
						finalMap.put(MessageConstants.COLLECTIONS_LABLE, responseUser.get(MessageConstants.CUSTOM_COLLECTION));
					}
				}

			} else {
				/*
				 * Master Collection , Custom Collections. Gemopedia , Articles
				 * ,Videos ,Collection Showcase
				 */
				if (responseUser != null) {
					if (responseUser.containsKey(MessageConstants.COLLECTION_ITEMS_LIST)) {
						finalMap.put(MessageConstants.GEMSTONES_LABLE, responseUser.get(MessageConstants.COLLECTION_ITEMS_LIST));
					}
					if (responseUser.containsKey(MessageConstants.CUSTOM_COLLECTION)) {
						finalMap.put(MessageConstants.COLLECTIONS_LABLE, responseUser.get(MessageConstants.CUSTOM_COLLECTION));
					}
				}
				if (response != null) {
					if (response.containsKey(MessageConstants.GEMS)) {
						finalMap.put(MessageConstants.GEMOPEDIA_LABLE, response.get(MessageConstants.GEMS));
					}
					if (response.containsKey(MessageConstants.ARTICLES)) {
						finalMap.put(MessageConstants.ARTICLES_LABLE, response.get(MessageConstants.ARTICLES));
					}
					if (response.containsKey(MessageConstants.VIDEOS)) {
						finalMap.put(MessageConstants.VIDEOS_LABLE, response.get(MessageConstants.VIDEOS));
					}
					if (response.containsKey(MessageConstants.SHOWCASE_COLLECTION_NAME)) {
						finalMap.put(MessageConstants.COLLECTION_SHOWCASE_LABLE, response.get(MessageConstants.SHOWCASE_COLLECTION_NAME));
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return finalMap;
	}

	@Override
	public Map<String, Object> globalSearchKey(SearchBean searchBean) {
		logger.info(MessageConstants.GLOBAL_SEARCH_KEY);
		Map<String, Object> responseUser = null;
		Map<String, Object> response = null;
		Map<String, Object> searchMap = null;
		try {
			if (null != searchBean.getUserId() && searchBean.getUserId() != 0) {
				responseUser = globalSearchVault(searchBean);
			}

			// without userId
			response = globalSearch(searchBean);

			// Give the sorted response according to screen
			searchMap = sortSearch(responseUser, response, searchBean);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return searchMap;
	}

}
