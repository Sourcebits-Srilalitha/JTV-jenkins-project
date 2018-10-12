package com.vault.util;

public interface QueryConstants {

	// ArticleImagesRep
	public String QRY_FINDBYARTICLE_ID = "SELECT ai FROM ArticleImages ai WHERE ai.article_id =:article_id";
	public String QRY_COUNT_ARTICLE_IMAGES = "SELECT count(*) from ArticleImages";
	public String QRY_FINDARTICLEIMAGES_BY_ISHEROIMAGE = "SELECT i FROM ArticleImages i where i.is_hero_image=:isHeroImage";
	public String QRY_DELETE_ARTICLE_IMAGES = "DELETE FROM ArticleImages ai WHERE ai.article_id=:article_id";

	
	
	//UtilityRep
	public String QRY_GET_COLORS = "SELECT c FROM Colors c";
	public String QRY_GET_SHAPES = "SELECT s FROM Shapes s  where s.active=:isAct";
	public String QRY_GET_CUTS = "SELECT c FROM Cuts c  where c.active=:isAct";
	public String QRY_GET_CLASSIFICATION = "SELECT cl FROM Classification cl  where cl.active=:isAct";
	public String QRY_GET_TREATMENT = "SELECT t FROM Treatment t  where t.active=:isAct";
	public String QRY_GET_PHENOMENON = "SELECT p FROM Phenomenon p  where p.active=:isAct";
	public String QRY_GET_AQUISITIONS = "SELECT a FROM Acquisitions a  where a.active=:isAct";

	
	
	//ArticleRep
	public String QRY_COUNT_ARTICLES = "SELECT count(*) from Article";
	public String QRY_FIND_ALL_ACTIVE_ARTICLES = "SELECT i FROM Article i where i.active=:isAct";
	public String QRY_FIND_CONTENTID = "SELECT contentId FROM Article";
	public String QRY_UPDATE_ARTICLES = "Update Article a set a.active =:isAct where a.contentId in (:articleList)";
	public String QRY_GET_ALL_ARTICLE_SEARCH = "select a from Article a where (lower(a.title) like lower(:keyPrefix) or lower(a.title) like lower(:keySuffix) or lower(a.title) like lower(:keyfix)) and a.active=:isAct ";
	
	
	//ArticleTypeRep
	public String QRY_FIND_ALL_ACTIVE_ARTICLETYPES = "SELECT i FROM ArticleTypes i JOIN FETCH i.articles a where i.active=:isAct and i.id=a.type and a.active=:isAct";
	public String QRY_FIND_BY_ARTICLETYPE_TITLE = "SELECT i FROM ArticleTypes i where lower(i.title) like lower(:title) ";
	public String QRY_UPDATE_ARTICLES_INACT = "Update ArticleTypes a set a.active =:inAct where a.id not in (:idList)";
			
	
	//CollectionItemImageRep
	public String QRY_DELETE_EXISTING_IMAGES = "DELETE FROM CollectionItemImages ci WHERE ci.collectionItemId=:id";
	public String QRY_DELETE_EXISTING_ITEM_IMAGES = "DELETE FROM CollectionItemImages ci WHERE ci.id=:delItem";
	public String QRY_GET_BY_ITEMID = "SELECT ci FROM CollectionItemImages ci WHERE ci.collectionItemId=:id";
				
	//CollectionItemRep
	public String QRY_GET_CUSTOM_COLLECTION_BY_ID = "SELECT * FROM collection_items ci ,collection_items_lookup cl WHERE cl.collection_id = :collectionId AND ci.id=cl.collection_items_id AND cl.active=:isAct and ci.visible=:isAct";
	public String QRY_GET_ALL_COLLECTION_ITEMS = "SELECT * FROM collection_items ci WHERE  ci.user_id=:userId AND ci.visible=:isAct and (LOWER(ci.item_name) LIKE LOWER(:keyPrefix) OR  LOWER(ci.item_name) LIKE LOWER(:keySuffix) OR  LOWER(ci.item_name) LIKE LOWER(:keyfix))";
	
	//CollectionItemsLookupRep
	public String QRY_DELETE_FROM_LOOKUP = "delete from CollectionItemsLookup cl where cl.collectionId = :cid";
	public String QRY_DELETE_COLLECTIONS = "delete from CollectionItemsLookup cl where cl.collectionItemsId = :itemId";
	
	
	//CollectionRep
	public String QRY_FIND_BY_USERID = "SELECT c FROM Collection c where c.userId = :userId and c.active=:isAct";
	public String QRY_COUNT_USERS = "SELECT count(*) from Collection";
	public String QRY_GET_ALL_COLLECTION = "SELECT c FROM Collection c  where c.active=:isAct";
	public String QRY_GET_QUESTIONNAIRELIST = "SELECT a from ArchiveQuestionnaire a";
	public String QRY_GET_COLLECTIONBYID = "SELECT c FROM Collection c where c.id = :id and c.active=:isAct";
	public String QRY_GET_COLLECTIONBYNAME = "SELECT c FROM Collection c where c.name = :name and c.collectionType = 2";
	public String QRY_GET_SHOWCASECOLLECTIONS = "SELECT c FROM Collection c JOIN FETCH c.showcaseCollectionItems sci where c.collectionType = 2 and c.id = sci.collectionId and c.active=:isAct and sci.visible = :isVisible";
	public String QRY_UPDATEASINACTIVE = "Update Collection c set c.active =:inAct where c.collectionType = 2 and c.id not in (:idList)";
	public String QRY_GET_CUSTOMCOLLECTIONDETAILS = "SELECT COUNT(ci.id) FROM collection_items ci, collection_items_lookup cl WHERE cl.collection_id = :collectionId AND ci.id=cl.collection_items_id AND cl.active=:isAct and ci.visible=:isAct";
	public String QRY_DELETE_COLLECTIONBYID = "Update Collection c set c.active =:inAct where c.collectionType = 3 and c.id = :collectionId and c.active='Y'";
	public String QRY_GET_REASONNAME = "SELECT a.reason from ArchiveQuestionnaire a where a.id = :reasonId";
	public String QRY_GET_ALLUSERCOLLECTIONS = "SELECT c FROM Collection c where c.collectionType = 3 and c.active= :isAct and c.userId=:userId and (lower(c.name) like lower(:keyPrefix) or lower(c.name) like lower(:keySuffix) or lower(c.name) like lower(:keyfix)) ";
	public String QRY_GET_ALLSHOWCASECOLLECTION = "SELECT c FROM Collection c where c.collectionType = 2 and c.active= :isAct and (lower(c.name) like lower(:keyPrefix) or lower(c.name) like lower(:keySuffix) or lower(c.name) like lower(:keyfix)) ";
	
	//GemopediaItemRep
	public String QRY_FIND_GEMDETAILS = "SELECT itemId FROM GemopediaProductLookup gp where lower(gp.gemstone) = lower(:gemstone) and lower(gp.opticalProperty) = lower(:optical) and lower(gp.composition) = lower(:composition)";
	public String QRY_GET_GEM = "select g from GemopediaItem g where g.id in (select gp.itemId from GemopediaProductLookup gp where (lower(gp.composition) = lower(:gpNature) or lower(gp.composition) is null) and (lower(gp.gemstone) = lower(:gpCommName) or  lower(gp.gemstone) is null ) and (lower(gp.opticalProperty) = lower(:opticalProperty) or lower(gp.opticalProperty) is null))";
	public String QRY_GET_GEMFORSHOWCASE = "select g from GemopediaItem g where lower(g.commonName) =  lower(:gpCommName)";
	public String QRY_GET_GEMIDBYCOMMONNAME = "select g.id from GemopediaItem g where lower(g.commonName) =  lower(:gpCommName)";
	public String QRY_GET_GEMDETAILS = "select g from GemopediaItem g where g.id IN (:idList)";
	public String QRY_GET_GEMITEM = "select g from GemopediaItem g where g.id IN (:id)";
	public String QRY_GET_ALLGEMS = "select g from GemopediaItem g  order by g.commonName asc";
	public String QRY_GET_VARIETY = "select distinct(g.variety) from GemopediaItem g where g.variety is NOT NULL and g.variety != ' ' and g.variety NOT LIKE 'NA' ";
	public String QRY_GET_SPECIES = "select distinct(g.species) from GemopediaItem g where g.species is NOT NULL and g.species != ' ' and g.species NOT LIKE 'NA' ";
	public String QRY_GET_ALLGEMSEARCH = "select g from GemopediaItem g where lower(g.commonName) like lower(:keySuffix) or lower(g.commonName) like lower(:keyfix) or lower(g.commonName) like lower(:keyPrefix)";
	
	//GemopediaSliderRep
	public String QRY_GET_BY_GEMID_GEMSLIDER = "SELECT g FROM GemopediaSliderItems g where g.gemopediaId = :gemopediaId and g.active=:isAct";
	public String QRY_UPDATEASINACTIVE_GEMSLIDER = "UPDATE GemopediaSliderItems g SET g.active = :inAct WHERE g.gemopediaId NOT IN (:gemIdList)";
	public String QRY_GET_ALLGEMS_GEMSLIDER = "SELECT g FROM GemopediaSliderItems g where g.active=:isAct";
	
	//ItemApprDocsRep
	public String QRY_DELETE_EXISTING_DOCS = "DELETE FROM ItemApprDocs ci WHERE ci.id=:delItem";
	public String QRY_GET_BY_ITEMID_APPDOCS = "SELECT ci FROM ItemApprDocs ci WHERE ci.collectionItemId=:id";
	
	//ItemApprPhotosRep
	public String QRY_DELETE_EXISTING_PHOTOS = "DELETE FROM ItemApprPhotos ci WHERE ci.id=:delItem";
	public String QRY_GET_BY_ITEMID_APP_PHOTOS = "SELECT ci FROM ItemApprPhotos ci WHERE ci.collectionItemId=:id";
	
	//ShowcaseRep
	public String QRY_GET_NAMES = "select sci.collection from ShowcaseCollectionItems sci group by sci.collection";
	public String QRY_FIND_BY_COLLECTION = "select c from ShowcaseCollectionItems c where c.collectionId=:collectionId";
	public String QRY_GET_ITEMBYNAME = "select c from ShowcaseCollectionItems c where c.title = :title and c.collection = :name";
	public String QRY_UPDATEASINACTIVE_SHOWCASE = "Update ShowcaseCollectionItems c set c.visible =:inAct where c.id not in (:idList)";
	public String QRY_GET_ALLSHOWCASEITEMSEARCH = "select * from showcase_collection_items c where c.visible=:isAct and (lower(c.gp_comm_name) like lower(:keyPrefix) or lower(c.gp_comm_name) like lower(:keySuffix) or lower(c.gp_comm_name) like lower(:keyfix))";
	public String QRY_GET_ALLSHOWCASEDETAILS = "select count(c.id) from ShowcaseCollectionItems c where c.collectionId=:collectionId and c.visible=:isAct";
	
	//TmpArchiveItemsRep
	public String QRY_GET_ARCHIVEITEMS = "select a from TmpArchiveItems a where a.userId=:userId";
	public String QRY_DELETE_ARCHIVEITEM = "delete from TmpArchiveItems t where t.userId =:userId";
	
	//UserRep
	public String QRY_COUNT_USERS_UA = "SELECT count(*) from UserAccount";
	public String QRY_FIND_BY_USERNAME_ENTERPRISEID = "SELECT ci FROM UserAccount ci WHERE ci.enterpriseId like :enterpriseId and ci.username like :userName ";

	//VaultRep
	public String QRY_FIND_ITEMS_BY_USERID = "SELECT ci FROM CollectionItems ci where ci.userId = :userId and ci.visible = :isVisible ORDER BY ci.itemName ASC";
	public String QRY_DELETE_FROM_VAULT = "UPDATE CollectionItems ci SET ci.visible = :inAct where ci.id= :itemId and ci.userId = :userId";
	public String QRY_GET_ARCHIVEDITEMS = "SELECT c1.item_id, c1.user_id, c1.created_on, c1.action_type, ci.item_name, ci.product_image_url , ci.is_third_party ,(SELECT pp.image_link FROM collection_item_images pp WHERE c1.created_on IN "
			+ "(SELECT MAX(c2.created_on) FROM collection_item_history c2 WHERE c2.user_id = :userId AND pp.collection_item_id = c2.item_id AND pp.collection_item_id = c1.item_id GROUP BY c2.item_id ) and rownum <= 1) AS prod_url , aq.reason "
			+ "FROM collection_item_history c1, collection_items ci , archive_questionnaire aq WHERE c1.created_on IN (SELECT MAX(c2.created_on) FROM collection_item_history c2 "
			+ "WHERE c2.user_id = :userId AND c2.item_id=c1.item_id  GROUP BY c2.item_id) AND c1.action_type IS NOT NULL AND ci.id IN (c1.item_id) AND c1.action_type = aq.id";
	public String QRY_RESTORE_FROM_VAULT = "UPDATE CollectionItems ci SET ci.visible = :isAct where ci.id in (:itemIds)";
	public String QRY_FIND_COLLECTION = "SELECT c FROM Collection c where c.userId = :userId and c.active=:isAct";
	public String QRY_GET_RECENTLYADDEDITEMS = "SELECT ci FROM CollectionItems ci where ci.userId = :userId and ci.visible = :isVisible AND ci.created_on >= :backDate";

	//VideoRep
	public String QRY_COUNT_VIDEO = "SELECT count(*) from Video";
	public String QRY_FIND_ALL_ACTIVEVIDEOS = "SELECT i FROM Video i where i.active=:isAct";
	public String QRY_FIND_BY_VIDEONAME = "SELECT i FROM Video i where i.title=:title and i.type=:type";
	public String QRY_UPDATE_VIDEO_AS_INACTIVE = "UPDATE Video v SET v.active =:inAct where v.id NOT IN (:idList)";
	public String QRY_GETALLVIDEOSEARCH = "select v from Video v where (lower(v.title) like lower(:keyPrefix) or lower(v.title) like lower(:keySuffix) or lower(v.title) like lower(:keyfix)) and v.active=:isAct ";

	//VideoTypeRep
	public String QRY_FINDBYID = "SELECT i FROM VideoTypes i where i.id=:videoId";
	public String QRY_FIND_ALL_ACTIVE_VIDEOTYPES = "SELECT i FROM VideoTypes i JOIN FETCH i.videos a where i.active=:isAct and i.id=a.type and a.active=:isAct";
	public String QRY_FIND_BY_VIDEOTYPENAME = "SELECT i FROM VideoTypes i where i.title=:title and i.active=:isAct";
	public String QRY_UPDATE_VIDEOTYPE_AS_INACTIVE = "UPDATE VideoTypes v SET v.active = :inAct WHERE v.id NOT IN (:idList)";
	
	//WishlistitemRep
	public String QRY_FIND_WL_BY_USERID = "SELECT i FROM WishList i where i.userId=:userId and i.active=:isAct";
	public String QRY_REMOVE_FROM_WISHLIST = "Update WishList i set i.active=:isAct where i.userId=:userId and i.gemopediaId=:gemopedia_Id and i.active = 'Y'";
	public String QRY_FIND_EXTENDEDBYUSERID = "SELECT i,g FROM WishList i,GemopediaItem g where i.userId=:userId and i.active=:isAct and g.id = i.gemopediaId order by i.created_on desc";
	public String QRY_GET_COUNT = "Select count(w) from WishList w where w.userId=:userId and w.active=:isAct";
	public String QRY_FIND_GEMOPEDIAIDS = "Select w.gemopediaId from WishList w where w.userId=:userId and w.active=:isAct and w.gemopediaId in (:gemList)";
	public String QRY_REMOVE_ITEMS_FROM_WISHLIST = "Update WishList i set i.active=:inAct where i.userId=:userId and i.gemopediaId in (:gemList) and i.active = 'Y'";

	
	
	
	
	//params
	public String ARTICLES = "articles";
	public String ARTICLE = "article";
	public String ARTICLE_ID = "article_id";
	public String IS_HERO_IMAGE = "isHeroImage";
	
	public String IS_ACT = "isAct";
	
	public String KEY_PREFIX = "keyPrefix";
	public String KEY_SUFFIX = "keySuffix";
	public String KEY_FIX = "keyfix";
	public String ARTICLE_LIST = "articleList";
	public String ARTICLE_TYPE = "articletype";

	public String TITLE = "title";
	public String ID = "Id";
	public String ID_LIST = "idList";
	public String IN_ACT = "inAct";
	
	public String ID_ = "id";
	public String DEL_ITEM = "delItem";
	
	public String COLLECTION_ID = "collectionId";
	public String USER_ID = "userId";

	
	public String ITEM_ID = "itemId";
	public String CID = "cid";

	public String COLLECTION = "collection";
	public String NAME = "name";
	public String IS_VISIBLE = "isVisible";
	public String REASON_ID = "reasonId";
	
	public String GEMSTONE = "gemstone";
	public String COMPOSITION = "composition";
	public String OPTICAL = "optical";
	public String GP_NATURE = "gpNature";
	public String GP_COMMNAME = "gpCommName";
	public String OPTICAL_PROPERTY = "opticalProperty";
	
	public String GEMOPEDIA_ID = "gemopediaId";
	public String GEM_IDLIST = "gemIdList";
	
	public String USER_NAME = "userName";
	public String ENTERPRISE_ID = "enterpriseId";
	
	public String ITEMIDS = "itemIds";
	public String BACK_DATE = "backDate";
	
	public String VIDEO_TYPE = "videotype";
	public String TYPE = "type";
	
	public String VIDEO_ID = "videoId";
	
	public String GEMOPEDIAID = "gemopedia_Id";
	public String GEM_LIST = "gemList";
	
	

		


}
