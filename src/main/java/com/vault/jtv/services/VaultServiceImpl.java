package com.vault.jtv.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.cloudinary.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vault.jtv.beans.ArchiveBean;
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItemHistory;
import com.vault.jtv.model.CollectionItemImages;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.model.ItemApprDocs;
import com.vault.jtv.model.ItemApprPhotos;
import com.vault.jtv.model.TmpArchiveItems;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.repository.CollectionItemHistoryRep;
import com.vault.jtv.repository.CollectionItemImageRep;
import com.vault.jtv.repository.CollectionItemRep;
import com.vault.jtv.repository.CollectionRep;
import com.vault.jtv.repository.GemopediaItemRep;
import com.vault.jtv.repository.ItemApprDocsRep;
import com.vault.jtv.repository.ItemApprPhotosRep;
import com.vault.jtv.repository.TmpArchiveItemsRep;
import com.vault.jtv.repository.VaultRep;
import com.vault.jtv.security.CloudinaryClient;
import com.vault.jtv.security.ImageHelper;
import com.vault.util.MessageConstants;

@Service
public class VaultServiceImpl implements VaultService {

	@Autowired
	private UserService userService;

	@Autowired
	private CollectionItemHistoryRep collectionItemHistoryRep;

	@Autowired
	private CollectionItemRep collectionItemRep;

	@Autowired
	private VaultRep vaultRep;

	@Autowired
	private KeycloakUtility keycloakUtility;

	@Autowired
	private CollectionRep collectionRepository;

	@Autowired
	private GemopediaItemRep repository;

	@Autowired
	private CollectionItemImageRep collectionItemImageRep;

	@Autowired
	private ItemApprDocsRep itemApprDocsRep;

	@Autowired
	private ItemApprPhotosRep itemApprPhotosRep;

	@Autowired
	private CloudinaryClient cloudinaryClient;

	@Autowired
	private ImageHelper utilHelper;

	@Autowired
	private TmpArchiveItemsRep tmpArchiveItemsRep;

	@Value("${order.recall.time}")
	private int recallDuration;

	@Value("${recent.items.days}")
	private int recentDuration;

	protected Logger logger = LoggerFactory.getLogger(VaultServiceImpl.class);

	@Override
	public String deleteFromVaultItems(int userId, Map<String, String> archivedItems) {
		String response = "";
		int total = 0;

		for (Map.Entry<String, String> eachItem : archivedItems.entrySet()) {
			int result = vaultRep.deleteFromVault(userId, MessageConstants.IN_ACTIVE,
					Integer.parseInt(eachItem.getKey()));
			if (result != 0) {
				CollectionItemHistory collectionHistory = collectionItemHistoryRep
						.save(new CollectionItemHistory(userId, Integer.parseInt(eachItem.getKey()),
								Integer.parseInt(eachItem.getValue()), MessageConstants.ACTION_R, new Date()));

				if (collectionHistory != null) {
					total++;
				} else {
					response = MessageConstants.UNABLE_TO_UPDATE_CH;
				}
			}
		}
		if (total != 0) {
			response = MessageConstants.REMOVE_SUCCESS + total;
		}
		return response;
	}

	@Override
	public Set<Collection> getVaultByUserId(int userId) throws Exception {
		Set<Collection> collectionList = null;
		try {
			collectionList = vaultRep.findByUserId(userId, MessageConstants.IS_ACTIVE, MessageConstants.IS_ACTIVE);
			if (null == collectionList || collectionList.size() == 0) {
				collectionList = vaultRep.findCollection(userId, MessageConstants.IS_ACTIVE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionList;
	}

	@Override
	public Set<CollectionItems> getVaultItemsByUserId(int userId) {
		Set<CollectionItems> collectionItemsList = null;
		try {
			collectionItemsList = vaultRep.findItemsByUserId(userId, MessageConstants.IS_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionItemsList;
	}

	
	/**
	 * OrderHistory Changes are done due to the Oracle Migration, we are cleaning all the orders saved in GD DB 
	 * and pulling the Order items of the user and saving it in GD DB.
	 * We are implementing the below functionality (insertArchiveItems()) to save the items which are archived by the user.
	 * Need to remove this functionality once the tmpArchiveItems table is empty	
	 * 
	 * Steps:
	 * get data from temp table of the specific user , if List is not empty
       insert into history table and make item inactive in collection_items table
       delete from tmp table
     *         
     * Code to be Removed:
              tempArchivedItemsList
              insertArchiveItems() block
	 */

	@Transactional
	@Override
	public List<CollectionItems> getOrderHistoryFromJTV(UserAccount user, String token) {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		JsonObject jsonobj = new JsonObject();
		// Collection collection = null;
		boolean updateLastCallTime = false;
		List<CollectionItems> recentSaved = new ArrayList<>();
		List<TmpArchiveItems> tempArchivedItemsList = new ArrayList<>();
		try {
			if (user != null && (user.getEnterpriseId() != null && user.getEnterpriseId() != "")) {
				// phase2: changes for new collection item flow :userid mapped
				// to itemid
				if (user.getLastJTVCall() != null) {
					// This is normal login for subsequent time
					cal.setTime(user.getLastJTVCall());
					cal.add(Calendar.MINUTE, recallDuration);
					if (currentDate.after(cal.getTime())) {
						jsonobj = keycloakUtility.getOrderHistoryJSON(user, token);
						updateLastCallTime = true;
					}
				} else {
					// added for orderHistoryIssue
					// User is logging in first time, check whether he has
					// archived items
					tempArchivedItemsList = tmpArchiveItemsRep.getArchiveItems(user.getId());
					jsonobj = keycloakUtility.getOrderHistoryJSON(user, token);
					if (jsonobj != null) {
						updateLastCallTime = true;
					}
				}

				if (jsonobj != null && jsonobj.size() != 0) {
					List<CollectionItems> list = getItemsFromJson(jsonobj, user.getId());
					CollectionItems savedOrderItem = null;
					if (list != null && !list.isEmpty()) {
						for (CollectionItems items : list) {							
							// check the tmp archive table have items
							// if yes,then check sku else just add in item table
							if (null != tempArchivedItemsList && !tempArchivedItemsList.isEmpty()) {
								savedOrderItem = insertArchiveItems(tempArchivedItemsList, items,
										user.getId());
								
							} else {
								savedOrderItem = collectionItemRep.save(items);		
							}
							if(null != savedOrderItem){
								saveItemImage(savedOrderItem);
								recentSaved.add(savedOrderItem);
								savedOrderItem = null;
							}
						}
						// delete tmp archive records for the user 
							tmpArchiveItemsRep.deleteArchiveItem(user.getId());

						if (updateLastCallTime) {
							user.setLastJTVCall(currentDate);
							userService.update(user);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recentSaved;
	}

	/**
	 * Save item image in CollectionItemImages table
	 * iterate and save item according to image count
	 * @param savedOrderItem
	 */
	private void saveItemImage(CollectionItems savedOrderItem) {
		try {
			// iterate according to image count
			int first = 0;
			IntStream.range(first, null != savedOrderItem.getProductImageCount() ? savedOrderItem.getProductImageCount() : first).forEach(i -> {
				collectionItemImageRep.save(new CollectionItemImages(savedOrderItem.getId(),
						savedOrderItem.getProductImageUrl() + MessageConstants.IMG + i, MessageConstants.IS_ACTIVE));
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
	}
	
	/** 
	 * Steps: 1. First check the sku is in archive list 2. if yes, then mark
	 * visible as N in items table n save 3. then save item in history table 4.
	 * then remove items from tmp table based on user 5. if no, only insert in
	 * items table
	 * @param archivedItemsList
	 * @param orderItem
	 * @param userId
	 * @return CollectionItems
	 */	
	private CollectionItems insertArchiveItems(List<TmpArchiveItems> tempArchivedItemsList, CollectionItems orderItem,
			int userId) {
		CollectionItems savedItem = null;
		for (Iterator<TmpArchiveItems> itr = tempArchivedItemsList.iterator(); itr.hasNext();) {
			try {				
				TmpArchiveItems archiveItem = itr.next();
				if (archiveItem.getSku().equals(orderItem.getSku())
						&& archiveItem.getDateOfPurchase().getTime() == (orderItem.getDateOfPurchase().getTime())) {
					logger.info(MessageConstants.SKU + archiveItem.getSku());
					
					//set visible as N as item is archived
					orderItem.setVisible(MessageConstants.IN_ACTIVE);					
					savedItem = collectionItemRep.save(orderItem);
					
					//save in collection history table as item is archived
					collectionItemHistoryRep.save(new CollectionItemHistory(userId, savedItem.getId(),
							archiveItem.getArchiveType(), MessageConstants.ACTION_R, new Date()));
					
					// remove the item from archiveItemList as there can multiple items with same SKU and purchased date
					itr.remove();					
				} else {
					savedItem = collectionItemRep.save(orderItem);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(MessageConstants.EXCEPTION_IAI + e);
			}
		}
		return savedItem;
	}

	private List<CollectionItems> getItemsFromJson(JsonObject jsonObject, Integer userId) {

		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		List<CollectionItems> orderHistoryList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(df);
		try {
			if (jsonObject != null && jsonObject.has(MessageConstants.ORDER_HISTORY)) {
				JsonObject mainObject = (JsonObject) jsonObject.get(MessageConstants.ORDER_HISTORY);
				if (mainObject != null && mainObject.has(MessageConstants.ITEMS)) {
					JsonArray itemList = mainObject.getAsJsonArray(MessageConstants.ITEMS);
					if (itemList != null && itemList.size() != 0) {
						for (Object eachObject : itemList) {
							try {
								CollectionItems orderhistory = mapper.readValue(((JsonObject) eachObject).toString(),
										CollectionItems.class);
								orderhistory.setUserId(userId);
								orderhistory.setDeleted(MessageConstants.IN_ACTIVE);
								orderhistory.setItemName(
										((JsonObject) eachObject).get(MessageConstants.GEMSTONE).getAsString());
								orderhistory.setVisible(MessageConstants.IS_ACTIVE);
								orderhistory.setCtw(((JsonObject) eachObject).get(MessageConstants.SIZE).getAsString());
								orderhistory.setVariety(
										((JsonObject) eachObject).get(MessageConstants.GEMSTONE).getAsString());
								orderhistory.setCreated_on(new Date());

								List<GemopediaItem> gemList = repository.getGem(orderhistory.getComposition(),
										orderhistory.getVariety(), orderhistory.getOpticalProperty());
								if (gemList != null && gemList.size() != 0 && gemList.get(0).getCommonName() != null) {
									orderhistory.setCommonName(gemList.get(0).getCommonName());
								}
								orderHistoryList.add(orderhistory);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderHistoryList;
	}

	@Override
	public GemopediaItem getGemFromVaultItem(Integer itemId) {
		CollectionItems collectionItem = collectionItemRep.getById(itemId);
		List<GemopediaItem> gemList = null;
		if (collectionItem != null) {
			gemList = repository.getGem(collectionItem.getComposition(), collectionItem.getVariety(),
					collectionItem.getOpticalProperty());
			if (gemList != null && gemList.size() != 0) {
				return gemList.get(0);
			}
		}
		return null;
	}

	@Override
	public List<Object> getArchivedItems(int userId) {
		List<Object> returnObject = null;
		List<Object> ArchivedItemList = new ArrayList<>();
		try {
			Map<String, Object> returnMap = null;
			returnObject = vaultRep.getArchiveditems(userId);
			if (returnObject != null && returnObject.size() > 0) {
				for (Object eachObj : returnObject) {
					returnMap = new HashMap<>();
					if (null != eachObj) {
						Object[] element = (Object[]) eachObj;
						if (null != element) {
							returnMap.put(MessageConstants.ITEM_ID, element[0]);
							returnMap.put(MessageConstants.USER_ID, element[1]);
							returnMap.put(MessageConstants.ARCHIVED_ON, element[2]);
							returnMap.put(MessageConstants.REASON_ID, element[3]);
							returnMap.put(MessageConstants.ITEM_NAME, element[4]);
							// check whether third party or not and take the image url
							//oracle
							/*if (((BigDecimal) element[6]).intValue() == 0) {
								returnMap.put(MessageConstants.IMAGE_URL, element[5]);
							} else {
								returnMap.put(MessageConstants.IMAGE_URL, element[7]);
							}*/
							//sql
							if(element[6].equals(false))
							{
								returnMap.put(MessageConstants.IMAGE_URL, element[5]);
							}else{
								returnMap.put(MessageConstants.IMAGE_URL, element[7]);	
							}
							returnMap.put(MessageConstants.REASON_NAME, element[8]);
							ArchivedItemList.add(returnMap);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ArchivedItemList;
	}

	@Override
	public int restoreFromVault(int userId, String itemIDs) {
		int restored = 0;
		try {
			List<Integer> ids = new ArrayList<>();
			for (String s : Arrays.asList(itemIDs.split(",")))
				ids.add(Integer.valueOf(s));
			restored = vaultRep.restoreFromVault(ids, MessageConstants.IS_ACTIVE);
			if (restored != 0) {
				for (int id : ids) {
					try {
						collectionItemHistoryRep.save(
								new CollectionItemHistory(userId, id, null, MessageConstants.ACTION_RE, new Date()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restored;
	}

	@Override
	public String deleteFromVault(int userId, Integer itemId, Integer reasonId) {
		String response = "";
		int result = vaultRep.deleteFromVault(userId, MessageConstants.IN_ACTIVE, itemId);
		if (result != 0) {
			CollectionItemHistory collectionHistory = collectionItemHistoryRep
					.save(new CollectionItemHistory(userId, itemId, reasonId, MessageConstants.ACTION_R, new Date()));
			if (collectionHistory != null) {
				response = MessageConstants.REMOVE_SUCCESS + result;
			} else {
				response = MessageConstants.UNABLE_TO_UPDATE_CH;
			}
		} else {
			response = MessageConstants.NO_RECORDS_REMOVED;
		}
		return response;
	}

	@Override
	public Object getItemDetails(int itemId) {
		CollectionItems collectionItem = null;
		try {
			return collectionItem = collectionItemRep.getById(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collectionItem;
	}

	/**
	 * Phase2: Get the items list and recently added items list (recentDuration
	 * in days)
	 */
	@Override
	public List<CollectionItems> getRecentlyAddedItemsByUserId(int userId) {
		Calendar cal = Calendar.getInstance();
		List<CollectionItems> recentlyAddedItems = new ArrayList<>();
		try {
			cal.add(Calendar.DATE, -(recentDuration));
			Date backDate = cal.getTime();
			recentlyAddedItems = vaultRep.getRecentlyAddedItems(userId, backDate, MessageConstants.IS_ACTIVE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recentlyAddedItems;
	}

	@Transactional
	@Override
	public CollectionItems addItems(CollectionItems collectionItems, int userId) {
		CollectionItems savedItem = null;
		try {
			collectionItems.setCreated_on(new Date());
			collectionItems.setModified_on(new Date());
			collectionItems.setVisible(MessageConstants.IS_ACTIVE);
			collectionItems.setThirdParty(true);
			collectionItems.setUserId(userId);
			collectionItems.setProductImageCount(0);
			savedItem = collectionItemRep.save(collectionItems);
			if (savedItem != null) {
				collectionItemHistoryRep.save(new CollectionItemHistory(userId, savedItem.getId(), null,
						MessageConstants.ADDITEM, new Date(), new Date()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return savedItem;
	}

	@Transactional
	@Override
	public Object uploadImage(int itemId, List<MultipartFile> itemList, List<MultipartFile> appDocList,
			List<MultipartFile> appPhotoList, int userId) {
		JSONObject imageObject = null;
		CollectionItems existingItem = null;
		CollectionItemImages ci = null;
		ItemApprDocs appDocs = null;
		ItemApprPhotos appPhotos = null;
		List<CollectionItemImages> imageList = new ArrayList<>();
		List<ItemApprDocs> appDocsList = new ArrayList<>();
		List<ItemApprPhotos> appPhotosList = new ArrayList<>();
		String validateResult = "";
		try {
			existingItem = collectionItemRep.getById(itemId);
			if (existingItem != null) {
				// save item images
				if (itemList != null && itemList.size() > 0) {
					for (MultipartFile file : itemList) {
						if (null != file && file.getSize() > 0 && file.getOriginalFilename() != null) {
							validateResult = utilHelper.validateImage(file);
							if (validateResult.equals(MessageConstants.SUCCESS)) {
								imageObject = cloudinaryClient.imageUploadTrans(file);
								if (null != imageObject) {
									ci = collectionItemImageRep.save(new CollectionItemImages(itemId,
											imageObject.getString(MessageConstants.URL), file.getOriginalFilename(),
											MessageConstants.IS_ACTIVE,
											imageObject.getString(MessageConstants.PUBLIC_ID),
											imageObject.getString(MessageConstants.SECURE_URL)));
									if (ci != null) {
										imageList.add(ci);
										logger.info(MessageConstants.IMAGELIST_SIZE + imageList.size());
									}
								}
							}
						}
					}
				}

				// save appr docs

				if (appDocList != null && appDocList.size() > 0) {
					for (MultipartFile doc : appDocList) {
						if (null != doc && doc.getSize() > 0 && doc.getOriginalFilename() != null) {
							validateResult = utilHelper.validateDocument(doc);
							if (validateResult.equals(MessageConstants.SUCCESS)) {
								imageObject = cloudinaryClient.docUploadTrans(doc);
								if (null != imageObject) {
									appDocs = itemApprDocsRep
											.save(new ItemApprDocs(itemId, imageObject.getString(MessageConstants.URL),
													doc.getOriginalFilename(), MessageConstants.IS_ACTIVE,
													imageObject.getString(MessageConstants.PUBLIC_ID),
													imageObject.getString(MessageConstants.SECURE_URL)));
									if (appDocs != null) {
										appDocsList.add(appDocs);
									}
								}
							}

						}
					}
				}

				// save appr photos
				if (appPhotoList != null && appPhotoList.size() > 0) {
					for (MultipartFile photo : appPhotoList) {
						if (null != photo && photo.getSize() > 0 && photo.getOriginalFilename() != null) {
							validateResult = utilHelper.validateImage(photo);
							if (validateResult.equals(MessageConstants.SUCCESS)) {
								imageObject = cloudinaryClient.imageUploadTrans(photo);
								if (null != imageObject) {
									appPhotos = itemApprPhotosRep.save(
											new ItemApprPhotos(itemId, imageObject.getString(MessageConstants.URL),
													photo.getOriginalFilename(), MessageConstants.IS_ACTIVE,
													imageObject.getString(MessageConstants.PUBLIC_ID),
													imageObject.getString(MessageConstants.SECURE_URL)));
									if (appPhotos != null) {
										appPhotosList.add(appPhotos);
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return existingItem;
	}

	@Transactional
	@Override
	public Object editItems(CollectionItems collectionItems, int userId) {
		CollectionItems savedItem = null;
		// CollectionItemImages ci = null;
		ItemApprDocs appDocs = null;
		ItemApprPhotos appPhotos = null;

		try {
			CollectionItems existingItem = collectionItemRep.getById(collectionItems.getId());
			if (existingItem != null) {

				collectionItems.setCreated_on(existingItem.getCreated_on());
				collectionItems.setModified_on(new Date());
				collectionItems.setVisible(existingItem.getVisible());
				collectionItems.setThirdParty(existingItem.isThirdParty());
				collectionItems.setUserId(existingItem.getUserId());
				collectionItems.setProductImageCount(0);

				/**
				 * Acquisition Type: Purchased, Vendor: JTV, SKU #:, Date
				 * Purchased:, Purchase Price:, Price Per Carat are non-editable From Client Side
				 * so we will update it with the existing details and no error
				 * message will be shown
				 */
				if (!existingItem.isThirdParty()) {
					collectionItems.setAcquisitionType(existingItem.getAcquisitionType());
					collectionItems.setVendor(existingItem.getVendor());
					collectionItems.setSku(existingItem.getSku());
					collectionItems.setDateOfPurchase(existingItem.getDateOfPurchase());
					collectionItems.setPurchasePrice(existingItem.getPurchasePrice());
				}				

				// changes for Edit JTV purchased items
				if (null != collectionItems && null != collectionItems.getDelItemImg()
						&& collectionItems.getDelItemImg().size() > 0) {
					// deleting item images with the id
					deleteItemImages(collectionItems.getDelItemImg());
				}

				// delete the docs from cloudinary and db
				if (null != collectionItems && null != collectionItems.getDelAppDoc()
						&& collectionItems.getDelAppDoc().size() > 0) {
					for (Integer delDocId : collectionItems.getDelAppDoc()) {
						appDocs = itemApprDocsRep.getById(delDocId);
						if (null != appDocs && null != appDocs.getPublic_id()
								&& appDocs.getPublic_id().trim().length() > 0) {
							cloudinaryClient.deleteImage(appDocs.getPublic_id());
						}
						itemApprDocsRep.deleteExistingDocs(delDocId);
					}
				}

				// delete the photos from cloudinary and db
				if (null != collectionItems && null != collectionItems.getDelAppPhoto()
						&& collectionItems.getDelAppPhoto().size() > 0) {
					for (Integer delPhotoId : collectionItems.getDelAppPhoto()) {
						appPhotos = itemApprPhotosRep.getById(delPhotoId);
						if (null != appPhotos && null != appPhotos.getPublic_id()
								&& appPhotos.getPublic_id().trim().length() > 0) {
							cloudinaryClient.deleteImage(appPhotos.getPublic_id());
						}
						itemApprPhotosRep.deleteExistingPhotos(delPhotoId);
					}
				}

				savedItem = collectionItemRep.save(collectionItems);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedItem;
	}
	
/**
 * check thirdparty or not
   //if yes , then delete from cloudinary , then from gd table
   //if no , then delete from gd table
 * @param isThirdParty
 * @param itemImageList
 */
	
	private void deleteItemImages(List<Integer> itemImageList) {		
			itemImageList.forEach(itemImageId -> {deleteCloudinaryImage(itemImageId); deleteExistingItemImage(itemImageId);	});		
	}
	
	/**
	 * delete image from JTV GD DB
	 * @param itemImageId
	 */
	private void deleteExistingItemImage(int itemImageId){
		logger.info(MessageConstants.DEL_EXIST_IMG);
		try{
			collectionItemImageRep.deleteExistingItemImages(itemImageId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
	}
	
	/**
	 * delete image from Cloudinary
	 * @param itemImageId
	 */
	private void deleteCloudinaryImage(int itemImageId){
		logger.info(MessageConstants.DEL_CLOUD_IMG);
		try{
			CollectionItemImages ci = collectionItemImageRep.getById(itemImageId);
			if (null != ci && null != ci.getPublic_id() && ci.getPublic_id().trim().length() > 0) {
				cloudinaryClient.deleteImage(ci.getPublic_id());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(MessageConstants.EXCEPTION + e);
		}
	}
	
	

	@Transactional
	@Override
	public String archiveFromVaultItems(int userId, List<ArchiveBean> archivedItems) {
		String response = "";
		int total = 0;
		for (ArchiveBean item : archivedItems) {
			if (item.getItemId() != null && item.getReasonId() != null) {
				int result = vaultRep.deleteFromVault(userId, MessageConstants.IN_ACTIVE, item.getItemId());
				if (result > 0) {
					CollectionItemHistory collectionHistory = collectionItemHistoryRep.save(new CollectionItemHistory(
							userId, item.getItemId(), item.getReasonId(), MessageConstants.ACTION_R, new Date()));
					if (collectionHistory != null) {
						total++;
					} else {
						response = MessageConstants.UNABLE_TO_UPDATE_CH;
					}
				}
			} else {
				response = MessageConstants.UNABLE_TO_UPDATE_CH;
			}
		}
		if (total > 0) {
			response = MessageConstants.REMOVE_SUCCESS + total;
		}
		return response;
	}	

	
}