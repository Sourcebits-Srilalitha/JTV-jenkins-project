package com.vault.jtv.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.vault.jtv.beans.ArchiveBean;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.services.VaultService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;

@RequestMapping(value=Constants.REQMAP_API)
@RestController
public class VaultController {
	
	@Autowired
	private VaultService vaultService;
	
	
	
	
	/**
	 * Archive purchased Item form My Collection (vault)
	 * @param itemId
	 * @param reasonId
	 * @param request
	 * @return Status of the process
	 */
	@RequestMapping(value = Constants.REQMAP_SINGLE_ARCHIVE, method= RequestMethod.DELETE)
	public ResponseEntity<?> deleteFromVault(@PathVariable(MessageConstants.ITEM_ID) Integer itemId, @PathVariable(MessageConstants.REASON_ID) Integer reasonId, HttpServletRequest request){
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null){
			return ResponseEntity.ok(vaultService.deleteFromVault(user.getId(), itemId, reasonId));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Archive multiple purchased Item form My Collection (vault)
	 * @param itemId
	 * @param reasonId
	 * @param request
	 * @return Status of the process
	 */
	
	
	@RequestMapping(value = Constants.REQMAP_MULTI_ARCHIVE, method= RequestMethod.DELETE)
	public ResponseEntity<?> deleteFromVaultItems(@RequestBody Map<String, String> archivedItems, HttpServletRequest request){
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null ){
			if(archivedItems != null && archivedItems.size() != 0){
				return ResponseEntity.ok(vaultService.deleteFromVaultItems(user.getId(), archivedItems));
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.ARCHIVE_REQUEST, HttpStatus.BAD_REQUEST);
			}			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Getting Purchased Items list for My collection(Vault) 
	 * @param request
	 * @return List of Collection with Items
	 * @throws Exception
	 */	
	@RequestMapping(value = Constants.REQMAP_VAULT, method= RequestMethod.GET)
	public ResponseEntity<?> getVaultByUserId(HttpServletRequest request) throws Exception{	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null){
			return getOrderHistoryFromJTV(request);			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);		
	}
	

	
	/**Phase 2 changes to get recently added items as well
	 * This will hit JTV system to get the Purchased Items list for a specific user
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_GET_ORDERS, method= RequestMethod.GET)
	public ResponseEntity<?> getOrderHistoryFromJTV(HttpServletRequest request) throws Exception{	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		//new login flow		
		if(user != null && user.getId() != null && null != request.getHeader(MessageConstants.X_AUTH_TOKEN)){
			Map<String, Object> returnMap = new HashMap<>();
			vaultService.getOrderHistoryFromJTV(user,request.getHeader(MessageConstants.X_AUTH_TOKEN)); 		
			returnMap.put(MessageConstants.COLLECTION_ITEMS, vaultService.getVaultItemsByUserId(user.getId()));
			returnMap.put(MessageConstants.RECENT_ITEMS, vaultService.getRecentlyAddedItemsByUserId(user.getId()));
			return ResponseEntity.ok(returnMap);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	
	/**
	 * Getting the Gemopedia Details for the Purchased Items  
	 * @param itemId
	 * @return Gemopedia Detail for a particular gem
	 */
	@RequestMapping(value = Constants.REQMAP_GET_ITEM_DETAILS, method= RequestMethod.GET)
	public GemopediaItem getGemFromVaultItem(@PathVariable(MessageConstants.ITEM_ID) Integer itemId){		
		return vaultService.getGemFromVaultItem(itemId);
	}
	
	
	//phase2
	/**
	 * To get the list of archived items of a user
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = Constants.REQMAP_ARCHIVE_ITEMS, method= RequestMethod.GET)
	public ResponseEntity<?> getVaultArchivedItems( HttpServletRequest request){
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null){
			return ResponseEntity.ok(vaultService.getArchivedItems(user.getId()));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * To restore the archive items of a user
	 * @param itemsIds
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = Constants.REQMAP_ARCHIVE_ITEMS, method= RequestMethod.POST)
	public ResponseEntity<?> restoreVaultArchivedItems(@RequestParam(MessageConstants.ITEMS_IDS) String itemsIds, HttpServletRequest request){
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null){
			return ResponseEntity.ok(vaultService.restoreFromVault(user.getId(), itemsIds));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	




	/**
	 * To get all the collection item details according to the itemid passed
	 * @param itemId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = Constants.REQMAP_GET_ITEM , method = RequestMethod.GET)
	public ResponseEntity<Object> getItemDetails(@RequestParam(MessageConstants.ITEM_ID) Integer itemId ,HttpServletRequest request) throws Exception {	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null){
			if(itemId != null){	
				Object response = vaultService.getItemDetails(itemId);
				if(response != null){
					return ResponseEntity.ok(response);	
				}else{
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value()+"-"+MessageConstants.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR); 
				}				
			}else{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.INVALID_ITEMID, HttpStatus.BAD_REQUEST);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);	
		}
	}

	
	

/**
 * Add Third party item only with out image
 * @param collectionItems
 * @param request
 * @return
 * @throws Exception
 */

	@RequestMapping(value = Constants.REQMAP_ADD_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<Object> addItems(@RequestBody CollectionItems collectionItems, HttpServletRequest request) throws Exception {	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if (user != null && user.getId() != null) {
			if (null != collectionItems && null != collectionItems.getItemName() && collectionItems.getItemName().trim().length() > 0) {
				CollectionItems response = vaultService.addItems(collectionItems, user.getId());
				if (response != null) {
					return ResponseEntity.ok(response);
				} else {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,	HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_ITEM_NAME,
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value() + "-" + MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		}

	}


	@RequestMapping(value = Constants.REQMAP_UPLOAD_IMAGE, method = RequestMethod.POST)
	public ResponseEntity<Object> uploadImage(@RequestParam(MessageConstants.ITEM_ID) Integer itemId,@RequestParam(value = MessageConstants.ITEM, required=false) List<MultipartFile> itemList,@RequestParam(value = MessageConstants.APP_DOC, required=false) List<MultipartFile> appDocList,@RequestParam(value = MessageConstants.APP_PHOTO, required=false) List<MultipartFile> appPhotoList,HttpServletRequest request) throws Exception {	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if (user != null && user.getId() != null) {
			if (null != itemId) {
				Object response = vaultService.uploadImage(itemId,itemList,appDocList,appPhotoList, user.getId());
				if (response != null) {
					return ResponseEntity.ok(response);
				} else {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,	HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_ITEMID, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value() + "-" + MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		}

	}





/**
 * Edit the existing item of 3rd party cloudinary without image
 * @param collectionItems
 * @param delItemImg
 * @param delAppDoc
 * @param delAppPhoto
 * @param request
 * @return
 * @throws Exception
 */
 
	@RequestMapping(value = Constants.REQMAP_EDIT_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<Object> editItems(@RequestBody CollectionItems collectionItems, HttpServletRequest request) throws Exception {	
	
	UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
	if(user != null && user.getId() != null){
		if(null != collectionItems && null != collectionItems.getId()){				
			Object response = vaultService.editItems(collectionItems,user.getId());
				if(response != null){
					return ResponseEntity.ok(response);	
					}else{
						return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value()+"-"+MessageConstants.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR); 
					}									
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.INVALID_ITEM, HttpStatus.BAD_REQUEST);
		}
	}else{
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);	
	}

}
	
	

	/**
	 * Archive multiple purchased Items from My Collection (vault)
	 * @param itemId
	 * @param reasonId
	 * @param request
	 * @return Status of the process
	 */
	
	
	@RequestMapping(value = Constants.REQMAP_MULTI_ARCHIVE_V1, method= RequestMethod.DELETE)
	public ResponseEntity<?> archiveFromVaultItems(@RequestBody List<ArchiveBean> archivedItems, HttpServletRequest request){
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null ){
			if(archivedItems != null && archivedItems.size() > 0 ){				
				return ResponseEntity.ok(vaultService.archiveFromVaultItems(user.getId(), archivedItems));
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.ARCHIVE_REQUEST, HttpStatus.BAD_REQUEST);
			}			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}

	

}