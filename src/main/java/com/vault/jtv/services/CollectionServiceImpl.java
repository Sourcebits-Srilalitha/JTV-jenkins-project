package com.vault.jtv.services;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vault.jtv.beans.GlobalResponse;
import com.vault.jtv.model.ArchiveQuestionnaire;
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.CollectionItemsLookup;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.repository.CollectionItemRep;
import com.vault.jtv.repository.CollectionItemsLookupRep;
import com.vault.jtv.repository.CollectionRep;
import com.vault.jtv.repository.WishlistitemRep;
import com.vault.jtv.security.CloudinaryClient;
import com.vault.util.MessageConstants;

@Service
public class CollectionServiceImpl implements CollectionService {

	@Autowired
	private CollectionRep collectionRepository;

	@Autowired
	private WishlistitemRep wishlistRepository;

	@Autowired
	private VaultService vaultService;

	@Autowired
	private CollectionItemsLookupRep lookUpRep;

	@Autowired
	private CollectionItemRep collectionItemRepository;

	@Autowired
	private CloudinaryClient cloudinaryClient;

	protected Logger logger = LoggerFactory.getLogger(CollectionServiceImpl.class);

	@Override
	public GlobalResponse getAllCollection() {

		Set<Collection> collectionList = null;
		Map<String, Object> response = new HashMap<>();
		try {
			collectionList = collectionRepository.getAllCollection(MessageConstants.IS_ACTIVE);
			if (collectionList != null && collectionList.size() != 0) {
				response.put(MessageConstants.COLLECTION_LIST, collectionList);
			} else {
				return new GlobalResponse(HttpStatus.NOT_FOUND.value(), MessageConstants.UNABLE_TO_GET, -200,
						MessageConstants.NO_COLLECTION_PRESENT);
			}
		} catch (Exception e) {
			return new GlobalResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getLocalizedMessage(), -200,
					MessageConstants.COLLECTION_FAILURE);
		}
		return new GlobalResponse(HttpStatus.OK.value(), MessageConstants.SUCCESS, response, 200,
				MessageConstants.COLLECTION_SUCCESS);
	}

	@Override
	public List<Collection> findByUserId(int userId) {
		List<Collection> userDetailsList = null;
		try {
			userDetailsList = collectionRepository.findByUserId(userId, MessageConstants.IS_ACTIVE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDetailsList;
	}

	@Override
	public Collection save(Collection collection) {
		Collection collectionSaved = null;
		try {
			collectionSaved = collectionRepository.save(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionSaved;
	}

	@Override
	public List<ArchiveQuestionnaire> getQuestionnaireList(String name) {
		List<ArchiveQuestionnaire> questionsList = null;
		try {
			questionsList = collectionRepository.getQuestionnaireList();
			if (questionsList != null && questionsList.size() != 0) {
				for (ArchiveQuestionnaire each : questionsList) {
					each.setDescription(MessageFormat.format(each.getDescription(), name));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return questionsList;
	}

	@Override
	public Collection getCollectionById(int collectionId) {
		Collection collection = null;
		try {
			collection = collectionRepository.getCollectionById(collectionId, MessageConstants.IS_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	@Override
	public Collection getCollectionByName(String collectionName) {
		Collection collection = null;
		try {
			collection = collectionRepository.getCollectionByName(collectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collection;
	}

	@Override
	public int updateAsInactive(List<Integer> idList, String inactive) {
		int updated = 0;
		try {
			updated = collectionRepository.updateAsInactive(idList, inactive);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	@Override
	public Map<String, Object> getuserCollectionSummary(UserAccount user, String token) {
		logger.info(MessageConstants.USER_SUMMARY);
		Map<String, Object> userCollection = null;
		try {
			userCollection = new HashMap<>();

			// get Custom Collections
			List<Object> customCollectionList = getCustomCollections(user.getId());
			userCollection.put(MessageConstants.CUSTOM_COLLECTION,
					customCollectionList != null ? customCollectionList : new ArrayList<>());	
			logger.info(MessageConstants.CUSTOM_COLLECTION + customCollectionList.size());

			// get Wishlist items
			int wishlistCount = getWishList(user.getId());
			userCollection.put(MessageConstants.WISHLIST, wishlistCount);
			//added for production changes - order history : need to remove later
			userCollection.put(MessageConstants.WISHLIST_P, wishlistCount);

			// get MasterCollection
			Map<String, Integer> masterMap = getMasterCollection(user, token);
			userCollection.put(MessageConstants.MASTER_COLLECTION, masterMap != null ? masterMap : new HashMap<>());
			//added for production changes - order history : need to remove later
			userCollection.put(MessageConstants.MASTER_COLLECTION_P, masterMap != null ? masterMap : new HashMap<>());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
		return userCollection;
	}

	/**
	 * Get the WishListCount
	 * 
	 * @param id
	 * @return
	 */
	private int getWishList(Integer userId) {
		int wishlistCount = 0;
		try {
			wishlistCount = wishlistRepository.getCount(userId, MessageConstants.IS_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
		return wishlistCount;
	}

	/**
	 * *
	 * 
	 * @param user
	 * @param token
	 * @return
	 */
	private Map<String, Integer> getMasterCollection(UserAccount user, String token) {
		List<CollectionItems> recentSaved = null;
		Map<String, Integer> masterMap = new HashMap<>();
		try {
			int totalItems = 0;
			Set<CollectionItems> masterCollection = vaultService.getVaultItemsByUserId(user.getId());
			if (masterCollection != null && masterCollection.size() != 0) {
				totalItems = masterCollection.size();
			}
			try {
				recentSaved = new ArrayList<>();
				recentSaved = vaultService.getOrderHistoryFromJTV(user, token);
			} catch (Exception e) {
				e.printStackTrace();
			}

			masterMap.put(MessageConstants.BADGE, recentSaved != null ? recentSaved.size() : 0);
			masterMap.put(MessageConstants.ITEM_COUNT, totalItems + (recentSaved != null ? recentSaved.size() : 0));
			logger.info(MessageConstants.BADGE + recentSaved.size());
			logger.info(MessageConstants.ITEM_COUNT + masterMap.get(MessageConstants.ITEM_COUNT));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
		return masterMap;
	}

	/**
	 * Get User specific Custom collections and number of items in the
	 * Collections
	 * 
	 * @param userId
	 * @return
	 */
	private List<Object> getCustomCollections(Integer userId) {
		List<Object> customCollectionList = new ArrayList<>();
		List<Collection> customCollections = null;
		try {
			customCollections = collectionRepository.findByUserId(userId, MessageConstants.IS_ACTIVE);
			if (null != customCollections && customCollections.size() > 0) {
				for (Collection eachCustom : customCollections) {
					int itemCount = collectionRepository.getCustomCollectionDetails(MessageConstants.IS_ACTIVE,
							eachCustom.getId());
					Map<String, Object> customMap = new HashMap<>();
					customMap.put(MessageConstants.COLLECTION, eachCustom);
					customMap.put(MessageConstants.ITEM_COUNT, itemCount);
					customCollectionList.add(customMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
		return customCollectionList;

	}

	@Override
	public int addItemsToCollection(String collectionId, String itemsId, int userId) {
		logger.info(MessageConstants.ADD_ITEMS_COLLECTION);
		int entries = 0;
		try {
			List<String> collectionIds = Arrays.asList(collectionId.split(","));
			for (String cid : collectionIds) {
				Collection collection = collectionRepository.getCollectionById(Integer.parseInt(cid),
						MessageConstants.IS_ACTIVE);
				if (collection != null) {
					// delete all items in that collection
					lookUpRep.deleteFromLookup(collection.getId());
					List<String> ids = Arrays.asList(itemsId.split(","));
					if (ids != null && ids.size() != 0) {
						for (String id : ids) {
							lookUpRep.save(new CollectionItemsLookup(collection.getId(), Integer.parseInt(id),
									collection.getName(), MessageConstants.IS_ACTIVE, new Date()));
							entries++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entries;

	}

	@Override
	public List<CollectionItems> getCustomCollectionById(int collectionId) {
		List<CollectionItems> items = null;
		try {
			items = collectionItemRepository.getCustomCollectionById(collectionId, MessageConstants.IS_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Transactional
	@Override
	public Object createCollection(Collection collection, MultipartFile file, int userId) {
		Collection collectionSaved = null;
		JSONObject imageObject = null;
		try {
			collection.setCreated_on(new Date());
			collection.setUserId(userId);
			collection.setActive(MessageConstants.IS_ACTIVE);
			collection.setCollectionType(MessageConstants.COLLECTION_TYPE);
			collection.setDescription(MessageConstants.COLLECTION_TYPE_DESCRIPTION);
			if (null == collection.getCustomType() || !(collection.getCustomType().trim().length() > 0)) {
				collection.setCustomType(MessageConstants.CUSTOM_TYPE);
			}

			if (file != null && file.getSize() > 0) {
				imageObject = cloudinaryClient.imageUploadTrans(file);
				if (null != imageObject) {
					collection.setImageUrl(imageObject.getString(MessageConstants.SECURE_URL));
					collection.setImageName(file.getOriginalFilename());
					collection.setPublic_id(imageObject.getString(MessageConstants.PUBLIC_ID));
				}
			}

			collectionSaved = collectionRepository.save(collection);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionSaved;
	}

	@Transactional
	@Override
	public Object editCollection(Collection collection, MultipartFile file, int userId, String itemsId,
			String publicId) {
		JSONObject imageObject = null;
		Collection collectionSaved = null;
		try {
			Collection existingItem = collectionRepository.findById(collection.getId());

			if (existingItem != null) {
				collection.setCreated_on(existingItem.getCreated_on());
				collection.setUserId(existingItem.getUserId());
				collection.setActive(existingItem.getActive());
				collection.setCollectionType(existingItem.getCollectionType());
				collection.setCustomType(existingItem.getCustomType());
				collection.setDescription(existingItem.getDescription());
				collection.setModified_on(new Date());

				if (file != null && file.getSize() > 0) {
					// delete the old image
					cloudinaryClient.deleteImage(existingItem.getPublic_id());

					imageObject = cloudinaryClient.imageUploadTrans(file);
					if (null != imageObject) {
						collection.setImageUrl(imageObject.getString(MessageConstants.SECURE_URL));
						collection.setImageName(file.getOriginalFilename());
						collection.setPublic_id(imageObject.getString(MessageConstants.PUBLIC_ID));
					} else {
						collection.setImageUrl("");
						collection.setImageName("");
						collection.setPublic_id("");
					}
					// delete/keep the existing image
				} else if (null == publicId || !(publicId.trim().length() > 0)) {
					collection.setImageUrl("");
					collection.setImageName("");
					collection.setPublic_id("");
					cloudinaryClient.deleteImage(existingItem.getPublic_id());

				} else {
					collection.setImageUrl(existingItem.getImageUrl());
					collection.setImageName(existingItem.getImageName());
					collection.setPublic_id(existingItem.getPublic_id());
				}
			}
			collectionSaved = collectionRepository.save(collection);

			if (null != collectionSaved && null != collectionSaved.getId() && null != collectionSaved.getName()
					&& collectionSaved.getName().trim().length() > 0) {
				// delete all items in that collection
				lookUpRep.deleteFromLookup(collectionSaved.getId());

				if (null != itemsId && itemsId.trim().length() > 0) {
					// add new items
					List<String> ids = Arrays.asList(itemsId.split(","));
					if (ids != null && ids.size() != 0) {
						for (String id : ids) {
							lookUpRep.save(new CollectionItemsLookup(collectionSaved.getId(), Integer.parseInt(id),
									collectionSaved.getName(), MessageConstants.IS_ACTIVE, new Date()));
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionSaved;

	}

	@Transactional
	@Override
	public Object deleteCollection(int collectionId) {
		int collectionUpdated = 0;
		try {
			// delete all collection items
			lookUpRep.deleteFromLookup(collectionId);

			// make collection inactive
			collectionUpdated = collectionRepository.deleteCollectionById(collectionId, MessageConstants.IN_ACTIVE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionUpdated;
	}

	@Transactional
	@Override
	public Object addCollectionsItem(String collectionId, int itemId, int userId) {
		logger.info(MessageConstants.ADD_COLLECTIONS_ITEM);
		int entries = 0;
		try {
			// delete the item from existing collections
			lookUpRep.deleteCollections(itemId);

			// add the collections to Item
			List<String> collectionIds = Arrays.asList(collectionId.split(","));
			for (String cid : collectionIds) {
				Collection collection = collectionRepository.getCollectionById(Integer.parseInt(cid),
						MessageConstants.IS_ACTIVE);
				if (collection != null) {
					lookUpRep.save(new CollectionItemsLookup(collection.getId(), itemId, collection.getName(),
							MessageConstants.IS_ACTIVE, new Date()));
					entries++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entries;
	}

}