package com.vault.jtv.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.model.WishList;
import com.vault.jtv.repository.WishlistitemRep;
import com.vault.util.MessageConstants;

@Service
public class WishlistitemServiceImpl implements WishlistitemService{

	protected Logger logger = LoggerFactory.getLogger(WishlistitemServiceImpl.class);
	private final WishlistitemRep repository;
	
	
	
	@Inject
	public WishlistitemServiceImpl (final WishlistitemRep repository){
		this.repository = repository;
		
	}


	@Override
	public List<Object> findByUserId(int userId) {
		String isAct = MessageConstants.IS_ACTIVE;
		List<Object> returnWishList = new ArrayList<>();
		try {
			List<Object> wishList=repository.findExtendedByUserId(userId,isAct);
			Map<String, Object> returnMap = null;
			for(Object w: wishList){
				Object[] eachItem = (Object[]) w;
				returnMap = new HashMap<>();
				returnMap.put(MessageConstants.WISHLIST_ITEM, eachItem[0]);
				GemopediaItem gemItem = (GemopediaItem) eachItem[1];
				if(gemItem != null){
					returnMap.put(MessageConstants.COMMON_NAME, gemItem.getCommonName());
					returnMap.put(MessageConstants.SPECIES, gemItem.getSpecies());
					returnMap.put(MessageConstants.COLORS, gemItem.getColors());
					returnMap.put(MessageConstants.VARIETY, gemItem.getVariety());
					if(gemItem.getGemopediaItemImages() != null && gemItem.getGemopediaItemImages().size() != 0){
						returnMap.put(MessageConstants.IMAGEURL, gemItem.getGemopediaItemImages().get(0).getImage_link());
					} else {
						returnMap.put(MessageConstants.IMAGEURL, "");
					}
				}				
				returnWishList.add(returnMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return returnWishList;		
	}

	@Override
	@Transactional	
	public ResponseEntity<WishList> addToWishList(int userId, int gemopedia_Id) {		
		WishList wishList = new WishList();
		HttpStatus response = null;
		String active = MessageConstants.IS_ACTIVE;
		try {
			WishList wl =repository.findByUserIdAndGemopediaIdAndActive(userId,gemopedia_Id,active);
			if(wl != null){
				response = HttpStatus.PRECONDITION_FAILED;
			}
			else{
				wishList = repository.save(new WishList(gemopedia_Id, userId, MessageConstants.IS_ACTIVE, new Date(), new Date()));
				if(wishList != null){
					response = HttpStatus.OK;
				} else {
					response = HttpStatus.NOT_FOUND;
				}
			}
			
		} catch (Exception e) {
			response = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<WishList>(wishList, response);
	}


	@Override
	@Transactional
	public ResponseEntity<Integer> removeFromWishList(int userId, int gemopedia_Id) {		
		HttpStatus response = null;
		int updatewishList=0;
		String active = MessageConstants.IS_ACTIVE;
		try {
			WishList wl =repository.findByUserIdAndGemopediaIdAndActive(userId,gemopedia_Id,active);
			if(wl == null){
				response = HttpStatus.PRECONDITION_FAILED;
			}
			else{
			updatewishList=repository.removeFromWishList(userId,gemopedia_Id,MessageConstants.IN_ACTIVE);			
			if(updatewishList !=0){
				response = HttpStatus.OK;
			} else {
				response = HttpStatus.NOT_FOUND;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Integer>(updatewishList, response);
	}


	@Override
	public boolean isGemAdded(int gemopedia_Id, int userId) {	
		WishList wl =repository.findByUserIdAndGemopediaIdAndActive(userId,gemopedia_Id,MessageConstants.IS_ACTIVE);
		if(wl == null){
			return false;
		}
		else{
			return true;
		}
	}


	@Override
	public ResponseEntity<Integer> removeItemsFromWishList(int userId, String gemList) {
		HttpStatus response = null;
		int updatewishList=0;
		String active = MessageConstants.IS_ACTIVE;
		try {
			List<Integer> gemIds = new ArrayList<>();
			for(String s : Arrays.asList(gemList.split(","))) {
				gemIds.add(Integer.valueOf(s));
			}
				if(null != gemIds && gemIds.size() > 0){
					List<Integer> gemItems =repository.findGemopediaIds(userId,gemIds,active);
					if(gemItems != null && gemItems.size() > 0 ){
						updatewishList= repository.removeItemsFromWishList(userId,gemItems,MessageConstants.IN_ACTIVE);
						if(updatewishList > 0){
							response = HttpStatus.OK;
						} else {
							response = HttpStatus.NOT_FOUND;
						}
					}else{
						response = HttpStatus.PRECONDITION_FAILED;
					}	
				}
				else{
					response = HttpStatus.PRECONDITION_FAILED;
				}			
		} catch (Exception e) {
			e.printStackTrace();
			response = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Integer>(updatewishList, response);
	}
		
	
}
