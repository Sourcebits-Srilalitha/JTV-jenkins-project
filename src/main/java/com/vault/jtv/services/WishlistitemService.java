package com.vault.jtv.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vault.jtv.model.WishList;

public interface WishlistitemService {
	
	List<Object> findByUserId(int userId) ;

	ResponseEntity<WishList> addToWishList(int userId, int gemopedia_Id);

	ResponseEntity<Integer> removeFromWishList(int userId, int gemopedia_Id);	

	boolean isGemAdded(int gemopedia_Id, int userId);

	ResponseEntity<Integer> removeItemsFromWishList(int userId, String gemList);

	
	
}
