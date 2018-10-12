package com.vault.jtv.controllers;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.model.UserAccount;
import com.vault.jtv.services.UserNotFoundException;
import com.vault.jtv.services.WishlistitemService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;

@RequestMapping(value = Constants.REQMAP_API_VAULT)
@RestController
public class WishlistitemController {

	protected Logger logger = LoggerFactory.getLogger(WishlistitemController.class);
	
	private final WishlistitemService wishListService;
	
	@Inject
	public WishlistitemController(WishlistitemService wishListService) {
		this.wishListService = wishListService;		
	}	
	
		
	/**
	 * Getting Wishlist Items for an authorized user 
	 * @param request
	 * @return List of Items
	 */
	@RequestMapping(value = Constants.REQMAP_GET_WISHLIST_ITEMS, method= RequestMethod.GET)
	public ResponseEntity<?>/*List<Object>*/ getByUserId(HttpServletRequest request){
		UserAccount user = (UserAccount)request.getAttribute(MessageConstants.JWT_USER);
		if(null != user && null != user.getId()){
			return ResponseEntity.ok(wishListService.findByUserId(user.getId()));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Adding Items to wishlist for an authorized User
	 * @param request
	 * @param gemopedia_Id
	 * @return WishList
	 */
	@RequestMapping(value = Constants.REQMAP_WISHLIST_ITEM, method = RequestMethod.POST)
	public ResponseEntity<?> addToWishList(HttpServletRequest request,@PathVariable(MessageConstants.ITEM_ID) int gemopedia_Id){
		UserAccount user = (UserAccount)request.getAttribute(MessageConstants.JWT_USER);
		if(null != user && null != user.getId()){
		   return wishListService.addToWishList(user.getId(), gemopedia_Id);	
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Remove item from wishlist for an authorized user
	 * @param request
	 * @param gemopedia_Id
	 * @return Success/failure (1/0)
	 */
	@RequestMapping(value = Constants.REQMAP_WISHLIST_ITEM, method = RequestMethod.DELETE)
	public ResponseEntity<?> removeFromWishList(HttpServletRequest request, @PathVariable(MessageConstants.ITEM_ID) int gemopedia_Id){
		UserAccount user = (UserAccount)request.getAttribute(MessageConstants.JWT_USER);
		if(null != user && null != user.getId()){
			return wishListService.removeFromWishList(user.getId(), gemopedia_Id);	
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Check whether the gem is already added to wishlist or not
	 * @param itemId
	 * @param request
	 * @return true/false
	 */
	@RequestMapping(value = Constants.REQMAP_WISHLIST_FLAG, method= RequestMethod.GET)
	public ResponseEntity<?> isGemAdded(@PathVariable(MessageConstants.ITEM_ID) int itemId, HttpServletRequest request){	
		UserAccount user = (UserAccount)request.getAttribute(MessageConstants.JWT_USER);
		if(null != user && null != user.getId()){
			return ResponseEntity.ok(wishListService.isGemAdded(itemId, user.getId()));
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);		
	}
	
	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(UserNotFoundException ex) {
		logger.info(MessageConstants.ERROR_HANDLING + ex.getMessage());
    }	
	
	
	
	/**phase2*/
	/**
	 * Remove items from wishlist for an authorized user
	 * @param request
	 * @param List of gemopedia_Id's
	 * @return number of items removed
	 */
	@RequestMapping(value = Constants.REQMAP_WISHLIST_REMOVE, method = RequestMethod.POST)
	public ResponseEntity<?> removeItemsFromWishList(@RequestParam(MessageConstants.GEM_LIST) String gemList,HttpServletRequest request) throws Exception{
		UserAccount user = (UserAccount)request.getAttribute(MessageConstants.JWT_USER);
		if(null != user && null != user.getId() && null != gemList && gemList.trim().length() > 0){
			return wishListService.removeItemsFromWishList(user.getId(), gemList);	
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
	}
	
	
}

