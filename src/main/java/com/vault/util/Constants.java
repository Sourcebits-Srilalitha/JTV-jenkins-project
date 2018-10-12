package com.vault.util;

public interface Constants {
	
	//Request Mappings
	public String REQMAP_API = "/api";
	
	
	//collection controller	
	public String REQMAP_COLLECTION_QUESTIONNAIRE = "/questionnaire/{name}";
	public String REQMAP_COLLECTION_USER_SUMMARY = "/vault/userCollectionSummary";
	public String REQMAP_COLLECTION_ITEMS = "/vault/collections/items";
	public String REQMAP_COLLECTIONS = "/vault/collections/{collectionId}";
	public String REQMAP_CREATE_COLLECTION = "/vault/createCollection/";
	public String REQMAP_EDIT_COLLECTION = "/vault/editCollection/";	
	public String REQMAP_ADD_COLLECTIONS_ITEM = "/vault/collections/item";
	
	
	//ArticleTypeController	
	public String REQMAP_GET_ARTICLETYPES = "/articleTypes/activeArticleTypes";
	
	//AuthController	
	public String REQMAP_REFRESHTOKEN = "/api/auth/jtvRefreshToken";
	
	//GemController
	public String REQMAP_GET_GEMDETAILS = "/gemItem/{itemId}";
	public String REQMAP_GET_ALLGEMDETAILS = "/gems";
	public String REQMAP_GET_SHOWCASE_GEM_ITEM = "/gem/{showcaseitemId}";
	public String REQMAP_GET_GEMDETAILS_SLIDER = "/gemItems";
	public String REQMAP_GET_ALLGEMS = "/allGems";
	public String REQMAP_GET_VARIETY = "/gem/variety";
	public String REQMAP_GET_SPECIES = "/gem/species";

	//SearchController
	public String REQMAP_SEARCH = "/search";
	
	//ShowcaseController
	public String REQMAP_SHOWCASE_COLLECTIONS = "/showcaseCollections";
	
	//UserController
	public String REQMAP_LOGIN_USER = "/loginUser";
	
	//UtilityController
	public String REQMAP_GET_COLORS = "/colors";
	public String REQMAP_GET_CUTS = "/cuts";
	public String REQMAP_GET_SHAPES = "/shapes";
	public String REQMAP_GET_CLASSIFICATIONS = "/classifications";
	public String REQMAP_GET_TREATMENTS = "/treatments";
	public String REQMAP_GET_PHENOMENOS = "/phenomenos";
	public String REQMAP_GET_ACQUISITIONS = "/acquisitions";
	
	//VaultController
	public String REQMAP_SINGLE_ARCHIVE = 	"/vault/item/{itemId}/{reasonId}";
	public String REQMAP_MULTI_ARCHIVE = "/vault/items";
	public String REQMAP_VAULT = "/vault/";
	public String REQMAP_GET_ORDERS = "/vault/orders";
	public String REQMAP_GET_ITEM_DETAILS = "/vault/item/{itemId}";
	public String REQMAP_ARCHIVE_ITEMS = "/vault/archive/items";
	public String REQMAP_GET_ITEM = "/vault/item";
	public String REQMAP_ADD_ITEMS = "/vault/addItems";
	public String REQMAP_UPLOAD_IMAGE = "/vault/uploadImage";
	public String REQMAP_EDIT_ITEMS = "/vault/editItems";
	public String REQMAP_MULTI_ARCHIVE_V1 = "/vault/archiveItems";
	
	//VideoTypeController
	public String REQMAP_GET_VIDEOTYPES = "/videosTypes/activeVideoTypes";

	
	//WishlistitemController
	public String REQMAP_API_VAULT = "/api/vault";
	public String REQMAP_GET_WISHLIST_ITEMS = "/wishlistitems";
	public String REQMAP_WISHLIST_ITEM = "/wishlistitems/item/{itemId}";
	public String REQMAP_WISHLIST_FLAG = "/wishlistitems/{itemId}";
	public String REQMAP_WISHLIST_REMOVE = "/wishlistitems/item";
	

	
	
	
}
