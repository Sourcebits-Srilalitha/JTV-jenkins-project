package com.vault.jtv.controllers;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vault.jtv.model.ArchiveQuestionnaire;
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.services.CollectionService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;



@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class CollectionController {

	@Autowired
	private CollectionService collectionService;

	/**
	 * When user clicks on the archive button of the Vault, list of questions will appear on the screen
	 * @param name
	 * @return QuestionnaireList  
	 */
	
	@RequestMapping(value = Constants.REQMAP_COLLECTION_QUESTIONNAIRE, method= RequestMethod.GET)
	public List<ArchiveQuestionnaire> getQuestionnaireList(@PathVariable(MessageConstants.NAME) String name){	
		return collectionService.getQuestionnaireList(name);		
	}

	
	

	/** Phase 2
	 * Get the user specific collections Summary
	 * @param request
	 * @return
	 */
	
	
	@RequestMapping(value = Constants.REQMAP_COLLECTION_USER_SUMMARY, method= RequestMethod.GET)
	public  ResponseEntity<Object> getuserCollectionSummary(HttpServletRequest request){
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(null != user && user.getId() != null && null != request.getHeader(MessageConstants.X_AUTH_TOKEN)){
			Map<String,Object> response= collectionService.getuserCollectionSummary(user,request.getHeader(MessageConstants.X_AUTH_TOKEN));
		if (response != null) {
			return ResponseEntity.ok(response);
		} else {
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		}
		else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value() + "-" + MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		}

	}


	/** phase2 */
	
	/**
	 * This api will add multiple items from master collection to a given custom collections
	 * @param collectionId
	 * @param itemsId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_COLLECTION_ITEMS, method = RequestMethod.POST)
	public ResponseEntity<?> addItemsToCollection(@RequestParam(MessageConstants.COLLECTION_ID) String collectionId, @RequestParam(MessageConstants.ITEMS_ID) String itemsId, HttpServletRequest request) throws Exception{
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null){
			if(null != collectionId && collectionId.trim().length() > 0 && null !=itemsId && itemsId.trim().length() > 0){
				return  ResponseEntity.ok(collectionService.addItemsToCollection(collectionId, itemsId, user.getId()));						
			}else{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
			}				
		}
		else{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);	
		}	
		
	}
	
	/**
	 * Give all the collections items of the custom collection
	 * @param collectionId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = Constants.REQMAP_COLLECTIONS, method = RequestMethod.GET)
	public ResponseEntity<?> getCustomCollectionById(@PathVariable(MessageConstants.COLLECTION_ID) int collectionId, HttpServletRequest request){	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
			if(user != null && user.getId() != null){
				List<CollectionItems> response=collectionService.getCustomCollectionById(collectionId);
				if(null != response){
					return ResponseEntity.ok(response);
				}
				else{
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);	
			}	
			
		}	
	
	
	/**
	 * Create a collection
	 * @param collection
	 * @return created collection	 	 * 
	 */
	@RequestMapping(value = Constants.REQMAP_CREATE_COLLECTION, method = RequestMethod.POST)
	public ResponseEntity<Object> createCollection(@RequestParam(value = MessageConstants.FILE, required = false) MultipartFile file,@RequestParam(MessageConstants.COLLECTION) String collection, HttpServletRequest request) throws Exception {
		DateFormat df = new SimpleDateFormat(MessageConstants.DATE_FMT);
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if (user != null && user.getId() != null) {
			if (null != collection && collection.trim().length() > 0) {				
				Collection col = new ObjectMapper().setDateFormat(df).readValue(collection, Collection.class);
				
				if (null != col && null != col.getName() && col.getName().trim().length() > 0) {
					Object response = collectionService.createCollection(col, file, user.getId());
					if (response != null) {
						return ResponseEntity.ok(response);
					} else {
						return new ResponseEntity<>(
								HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}				
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_COLLECTION,	HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_REQUEST_ID,	HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value() + "-" + MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		}

	}		
		
		
	
	/**
	 * Edit Collection
	 * @param file
	 * @param collection
	 * @param itemsId
	 * @param publicId
	 * @param request
	 * @return
	 * @throws Exception
	 * Image saving 3 options (publicId & file)
	 * Existing: send the publicId
	 * New: send the file
	 * Nothing: send the publicId empty
	 * 
	 * itemsId: List of items added to collection
	 */
	 
	 
	@RequestMapping(value = Constants.REQMAP_EDIT_COLLECTION, method = RequestMethod.POST)
	public ResponseEntity<Object> editCollection(@RequestParam(value = MessageConstants.FILE, required = false) MultipartFile file,@RequestParam(MessageConstants.COLLECTION) String collection, @RequestParam(MessageConstants.ITEMS_ID) String itemsId,@RequestParam(MessageConstants.PUBLICID) String publicId, HttpServletRequest request) throws Exception {
		DateFormat df = new SimpleDateFormat(MessageConstants.DATE_FMT);
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if (user != null && user.getId() != null) {
			if (null != collection && collection.trim().length() > 0) {				
				Collection col = new ObjectMapper().setDateFormat(df).readValue(collection, Collection.class);				
				if (null != col && null != col.getId()) {
					Object response = collectionService.editCollection(col, file, user.getId(),itemsId,publicId);
					if (response != null) {
						return ResponseEntity.ok(response);
					} else {
						return new ResponseEntity<>(
								HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}				
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_COLLECTION,	HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_REQUEST_ID,	HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value() + "-" + MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
		}

	}	
	
	
	/**
	 * Delete collection and items in the collection
	 * @param collectionId
	 * @param request
	 * @return 0 means Failure
	 * 		   1 means success
	 */
	@RequestMapping(value = Constants.REQMAP_COLLECTIONS, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCollection(@PathVariable(MessageConstants.COLLECTION_ID) int collectionId, HttpServletRequest request){	
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
			if(user != null && user.getId() != null){
				Object response=collectionService.deleteCollection(collectionId);
				if(null != response){
					return ResponseEntity.ok(response);
				}
				else{
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else{
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);	
			}	
			
		}	
	
	

	/**
	 * This api will add multiple collections from master collection to a given custom collections
	 * @param collectionId
	 * @param itemsId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_ADD_COLLECTIONS_ITEM, method = RequestMethod.POST)
	public ResponseEntity<?> addCollectionsItem(@RequestParam(MessageConstants.COLLECTION_ID) String collectionId, @RequestParam(MessageConstants.ITEM_ID) Integer itemId, HttpServletRequest request) throws Exception{
		UserAccount user = (UserAccount) request.getAttribute(MessageConstants.JWT_USER);
		if(user != null && user.getId() != null){
			if(null != collectionId && collectionId.trim().length() > 0 && null !=itemId){
				return  ResponseEntity.ok(collectionService.addCollectionsItem(collectionId, itemId, user.getId()));						
			}else{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
			}				
		}
		else{
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);	
		}	
		
	}
	

}