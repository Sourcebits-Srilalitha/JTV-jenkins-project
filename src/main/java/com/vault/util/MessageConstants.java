package com.vault.util;

public interface MessageConstants {

	public static final String INVALID_TOKEN = "Invalid Token";
	public static final String INVALID_REQUEST = "Invalid file type";
	public static final String INTERNAL_SERVER_ERROR = "Server Error";
	public static final String INVALID_COLLECTION = "Invalid Collection";
	public static final String INVALID_REQUEST_ID = "Invalid Collection/Item Id";

	// variables
	public static final String JWT_USER = "jwtUser";
	public static final String X_AUTH_TOKEN = "x-auth-token";

	public static final String IS_ACTIVE = "Y";
	// collection type =3 is "custom client collection"
	public static final int COLLECTION_TYPE = 3;
	public static final String COLLECTION_TYPE_DESCRIPTION = "Custom Client Collection";
	public static final String CUSTOM_TYPE = "public";
	public static final String IN_ACTIVE = "N";

	public static final String UNABLE_TO_GET = "Unable To Get";
	public static final String NO_COLLECTION_PRESENT = "No Collection Present";
	public static final String COLLECTION_FAILURE = "Error in getting Collection";
	public static final String SUCCESS = "Success";
	public static final String COLLECTION_SUCCESS = "Getting Collections Successfully";
	public static final String EXCEPTION = "Exception";

	// vault
	public static final String UNABLE_TO_UPDATE_CH = "Unable to Update Collection History";
	public static final String REMOVE_SUCCESS = "Successfully Removed Item : ";
	public static final String NO_RECORDS_REMOVED = "No Records is Removed";

	// Vault

	public static final String ACTION_R = "Remove";
	public static final String ACTION_RE = "Restore";

	// CollectionController
	public static final String ADDITEM = "Add";
	//variables
	public static final String NAME = "name";	
	public static final String ITEMS_ID = "itemsId";
	public static final String FILE = "file";
	public static final String PUBLICID = "publicId";
	public static final String DATE_FMT = "MM-dd-yyyy";
	

	// AuthController
	public static final String TOKEN_HEADER = "Authentication Token Required";
	public static final String UNATHORIZED = "Invalid Token";

	// variables
	public static final String CUSTOM_COLLECTION = "customCollection";
	public static final String WISHLIST = "wishlist";
	public static final String WISHLIST_P = "Wishlist";
	public static final String MASTER_COLLECTION = "masterCollection";
	public static final String MASTER_COLLECTION_P = "MasterCollection";
	public static final String COLLECTION_LIST = "collectionsList";
	public static final String BADGE = "badge";
	public static final String ITEM_COUNT = "numberOfItems";
	public static final String COLLECTION = "collection";
	public static final String IMG = "&img=";

	// variables from cloudinary
	public static final String SECURE_URL = "secure_url";
	public static final String PUBLIC_ID = "public_id";

	// variables vault
	public static final String ORDER_HISTORY = "orderHistory";
	public static final String ITEMS = "items";
	public static final String GEMSTONE = "gemstone";
	public static final String SIZE = "size";
	public static final String ITEM_ID = "itemId";
	public static final String USER_ID = "userId";
	public static final String ARCHIVED_ON = "archivedOn";
	public static final String REASON_ID = "reasonId";
	public static final String ITEM_NAME = "itemName";
	public static final String IMAGE_URL = "imageUrl";
	public static final String REASON_NAME = "reasonName";
	public static final String URL = "url";

	// GemController
	public static final String SHOWCASE_ITEMID = "showcaseitemId";

	// SearchController
	public static final String BAD_REQUEST = "Improper search key";

	// ShowcaseController
	public static final String COLLECTION_ID = "collectionId";

	// UserController
	public static final String USER_NOT_CREATED = "User Not Created";
	public static final String INVALID_UP = "Invalid User Name and Password";

	// variables
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String USER_DATA = "UserData";
	public static final String TOKEN_DATA = "TokenData";

	// VaultController
	public static final String ARCHIVE_REQUEST = "No items to Archive !";
	public static final String INVALID_ITEMID = "Invalid Item Id";
	public static final String INVALID_ITEM_NAME = "Invalid Item Name";
	public static final String INVALID_ITEM = "Invalid Item";
	// variables
	public static final String COLLECTION_ITEMS = "collectionItems";
	public static final String RECENT_ITEMS = "recentItems";
	public static final String ITEMS_IDS = "itemsIds";
	public static final String ITEM = "item";
	public static final String APP_DOC = "appDoc";
	public static final String APP_PHOTO = "appPhoto";

	// WishlistitemController
	public static final String GEM_LIST = "gemList";

	// variables
	public static final String SERVER_DOWN = "Server is Down";
	public static final String METHOD_NOT_ALLOWED = "Method Not Allowed";
	public static final String UNAUTHORIZED_USER = "Unauthorized User";
	public static final String PAGE_NOT_FOUND = "Page Not Found";
	public static final String REQUEST_NOT_PROPER = "Request is not proper";
	public static final String DATA_INTEGRITY_VIOLATION = "Data integrity violation";
	public static final String DATABASE_ERROR = "DatabaseError";

	// logger messages
	public static final String LOGIN_USER = "------------loginUser------------userName::::";
	public static final String IMAGELIST_SIZE = "inside imageList::";
	public static final String SKU = "SKU of item ### ";
	public static final String EXCEPTION_IAI = "Exception in insertArchiveItems ::: ";
	public static final String ERROR_HANDLING = "Handling error with message: {}";
	public static final String IS_THIRD_PARTY = "IsThirdParty Flag :::";
	public static final String DEL_EXIST_IMG = "deleteExistingItemImage";
	public static final String DEL_CLOUD_IMG = "deleteCloudinaryImage";
	public static final String GLOBAL_SEARCH = ":::::globalSearch::::";
	public static final String DIV = "Request raised a DataIntegrityViolationException";
	public static final String REQUEST_RAISED = "Request raised";
	public static final String NO_AUTHENTICATION = "no authentication!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!     ";
	public static final String ADD_COLLECTIONS_ITEM = "--------addCollectionsItem()--------";
	public static final String ADD_ITEMS_COLLECTION = "--------addItemsToCollection()--------";
	public static final String USER_SUMMARY = "---------getuserCollectionSummary()-----------";
	public static final String GET_ORDER_HISTORY = "----------getOrderHistoryJSON()-----";
	public static final String GET_USER_TOKEN = "----------getUserToken()-----";
	public static final String GET_OFF_USER_TOKEN = "---------getOffUserToken()-----------";
	public static final String GET_REFRESH_TOKEN = "----------getRefreshToken()-----";
	public static final String GLOBAL_SEARCH_ = "SearchServiceImpl: globalSearch() ";
	public static final String GLOBAL_SEARCH_VAULT = "SearchServiceImpl: globalSearchVault() ";
	public static final String SORT_SEARCH = "SearchServiceImpl: sortSearch() ";
	public static final String GLOBAL_SEARCH_KEY = "SearchServiceImpl: globalSearchKey() ";	
	public static final String GET_USER = "------------ getUser ------------";
	public static final String VALIDATE_REFRESH_TOKEN = "------------ validateRefreshToken------------";	
	public static final String GET_ENTERPRISE_ID = "------------ getEnterpriseID ------------";
	public static final String EXP_LOG = ":::: exp :::::::::";
	public static final String IAT_LOG = "::::::: iat :::::::";	
	public static final String INSIDE_GET_USER = "::::: inside getUser() :::: ";
	public static final String GET_USER_USERNAME = "getUser() :::: username :::::::::";
	public static final String GET_ENTERPRISE_ID_SUCCESS = "getEnterpriseID() Success:::::::::";
  	public static final String INSIDE_REFRESH_TOKEN = "inside:::::refreshtoken" ;
  	public static final String INSIDE_OFFLINE_TOKEN = "inside:::::offlinetoken" ;
  	public static final String REFRESH_TOKEN_USERNAME = "validateRefreshToken()::::::::username :::::::::";
  	public static final String ADD_USER = "----------addUser()-----" ;
  	public static final String FIND_BY_USERNAME = "----------findByUsername()-----" ;
	public static final String LOGIN_USER_ = "----------loginUser()-----" ;
	public static final String URL_STRING = "urslstring::::";  	
  	public static final String EXP_ARTICLE = "Exception in getArticle() : " ;
	public static final String GET_ARTICLE_JSON = "Inside getArticleJsonContent" ;
	public static final String EXP_GET_ARTICLE_JSON = "Exception in getArticleJsonContent() : ";  	
  	public static final String EXP_CREATE_HM = "Exception in createHashMapFromJsonString() : " ;
  	public static final String SHOWCASE_SCHEDULER = "#### Showcase Scheduler Runs @ ";
	public static final String VIDEO_SCHEDULER = "#### Video Scheduler Runs @ ";
	public static final String GEMOPEDIA_SCHEDULER = "#### Gemopedia Scheduler Runs @ ";
	public static final String ARTICLE_SCHEDULER = "#### Article Scheduler Runs @ ";
	public static final String START_UPD_ARTICLES = "############### Start of updateArticles ######################";
	public static final String UPD_ARTICLES_CONTENTID = "updateArticles::::::ContentID:::::::::";
	public static final String NO_VALID_JSON_ARTICLE_ID = "Could not find valid json for article id '";
	public static final String END_ARTICLES_UPD = " ############## End of updateArticles ################";
	public static final String START_ARTICLES = "getting content of articels to be displayed ::::::::############ Start of newArticles ###################";
	public static final String NEW_ARTICLE_EXP = "New Article Exception=====";
	public static final String SAVE_DEL_ARTICLE = "Save/delete Article_AImages Exception=====";
	public static final String NO_VALID_JSON = "no valid articel json found";
	public static final String EXP_SCH = "Exception=====";
	public static final String END_ARTICLES = "################# End of newArticles #########################";
	public static final String CONTENTORDERMAP = "contentOrderMap:";
	public static final String GEMOPEDIAID = "gemopediaId::";
	public static final String GP_NAME = "gpCommon Name:::";
	public static final String LIST_SIZE = "List Size:";

	
	
  	



	// CloudinaryClient Constants
	public static String mCloudName = "jewelry-television";
	public static String mApiKey = "189191438914618";
	public static String mApiSecret = "btLmWuFthJsa-jQTGYfnln4FZFk";
	public static String mCloudinary = "cloudinary://";
	public static String TYPE = "type";
	public static String UPLOAD = "upload";
	public static String MAX_RESULTS = "max_results";
	public static String FIFTY = "50";
	public static String RESOURCES = "resources";
	public static String INVALIDATE = "invalidate";
	public static String RESULT = "result";
	public static String TEMP = "temp";
	public static String TRANSFORMATION = "transformation";
	public static String AUTO = "auto";
	public static String RESOURCE_TYPE = "resource_type";
	public static String RAW = "raw";

	// CorsFilter

	public static String ORIGIN = "origin";
	public static String JTV_COM = "http:www.jtv.com";
	public static String ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static String ALLOW_METHODS = "Access-Control-Allow-Methods";
	public static String ALLOW_HEADERS = "Access-Control-Allow-Headers";
	public static String MAX_AGE = "Access-Control-Max-Age";

	// ImageHelper
	// public static final String IMAGE_PATTERN ="([^\\s]+(\\.(?i)(jpg|png|gif|bmp|jpeg))$)";
	public static final String IMAGE_PATTERN = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|webp|flif|jpe|jpc|jp2|j2k|wdp|jxr|hdp|png|tga|tif|tiff)$";
	public static final String DOC_PATTERN = "(.*/)*.+\\.(doc|docx|rtf|txt|pdf)$";
	public static final String INVALID_IMG_SIZE = "Invalid Image size";
	public static final String INVALID_IMG_TYPE = "Invalid Image Type";
	public static final String INVALID_DOC_TYPE = "Invalid Doc Type";
	public static final String INVALID_DOC_SIZE = "Invalid Doc Size";
	
	//JwtFilter	
	public static final String OPTIONS = "OPTIONS";
	
	public static final String NO_GEM = "No Gem details found with id: <%s>";
	
	//GemServiceImp
	//variables
	public static final String PAGE_INDEX = "pageIndex";
	public static final String GEMOPEDIA_ITEM = "gemopediaItem";
	
	//KeycloakUtility
	//variables
	public static String SIMPLE_DATE_FMT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static String BEARER = "Bearer ";
	public static String CONVERSATION_ID = "WEB123456890";
	public static String CUSTOMER_ID = "customer-id";
	public static String START_DATE = "start-date";
	public static String ACCEPT = "Accept";
	public static String APP_JSON = "application/json";
	public static String JTV_CONVERSATION_ID = "X-JTV-CONVERSATION-ID";
	public static String AUTHORIZATION = "Authorization";
	public static String REFERER = "Referer";
	public static String REQ_WITH = "X-Requested-With";
	public static String REQ_TIME = "X-JTV-CLIENT-REQUEST-TIME";	
	public static String HTTP_REQ = "XMLHttpRequest";
	public static String CLIENT_ID = "client_id";
	public static String GRANT_TYPE = "grant_type";
	public static String CLIENT_SECRET = "client_secret";
	public static String USERNAME = "username";
	public static String ACCESS_TOKEN = "access_token";
	public static String REFRESH_TOKEN = "refresh_token";
	public static String OFFLINE_ACCESS = "offline_access";
	public static String SCOPE = "scope";
	
	//SearchServiceImpl
	public static String GEMS = "gems";
	public static char PERCENTAGE = '%';
	public static String VIDEOS = "videos";
	public static String ARTICLES = "articles";	
	public static String COLLECTION_ITEMS_LIST = "collectionItemsList";
	public static String SHOWCASE_COLLECTION_NAME = "showcaseCollectionName";
	public static String GEMOPEDIA_LABLE = "Gemopedia";
	public static String ARTICLES_LABLE = "Articles";
	public static String VIDEOS_LABLE = "Videos";
	public static String COLLECTION_SHOWCASE_LABLE = "Collection Showcase";
	public static String GEMSTONES_LABLE = "Gemstones";
	public static String COLLECTIONS_LABLE = "Collections";
	
	//TokenService
	public static String CUSTOMERID = "customerId";
	public static String EXP = "exp";	
	public static String IAT = "iat";
	public static String SEPARATOR = "\\.";
	
	
	//UserNotFoundException
	public static String NO_USER = "No user entry found with id: <%s>";
	
	//WishlistitemServiceImpl
	public static String WISHLIST_ITEM = "wishItem";
	public static String COMMON_NAME = "commonName";
	public static String SPECIES = "species";	
	public static String COLORS = "colors";
	public static String VARIETY = "variety";
	public static String IMAGEURL = "imageURL";	
	
	
	//Application
	public static String H2 = "h2";	
	public static String CONSOLE = "/console/*";
	public static String API = "/api/*";
	public static String CORSFILTER = "corsFilter";	
	public static String API_VAULT = "/api/vault/*";
	public static String VAULT_FILTER = "vaultFilter";	
	public static String COMPONENT_SCAN = "com.vault.jtv";	

	
	//SwaggerConfig
	public static String S_DEV = "dev";
	public static String S_PUBLIC_API = "public-api";	
	public static String S_API = "/api.*";
	public static String S_TITLE = "JTV API";
	public static String S_DESC = "JTV Backend API List";	
	
	
	//JtvArticleContent
	public static final String CONTENT_TITLE = "contentTitle";
	public static final String CONTENT = "content";
	public static final String URL_LIST = "urlList";
	public static final String URI = "uri";
	public static final String PAGE_DESCRIPTION = "pageDescription";
	public static final String RELATED_ARTICLES = "Related Articles";
	public static final String BASE_IMAGE_URL = "https://images.jtv.com/";
	public static final String FORMAT_JSON = "?format=JSON";
	public static final String IS_HERO_IMAGE = "N";
	public static final String BY = "by" ;
	public static final String CONTENT_TYPE = "Content-Type";
	
	//JtvArticleIndex	
	public static String PAGE = "page";
	public static String CHILDREN = "children";
	public static String CUSTOM_FIELDS = "custom_fields";
	public static String CONTENT_ID = "contentId";
	public static String ISHEROIMAGE = "isHeroImage";
	public static String MODIFIED = "modified";
	public static String TITLE = "title";
	public static String DATE_FRMT = "yyyy-MM-dd HH:mm:ss";
	
	//JtvGemopediaContent
	public static String GPCOMMNAME = "gpCommName";
	public static String ACTIVE = "active";
	
	//JtvShowcaseContent
	public static String IMAGE_NAME = "imageName";
	public static String VISIBLE = "visible";
	public static String GP_NATURE = "gpNature";
	public static String DELETED = "deleted";
	public static String SUBTITLE = "subtitle";
	public static String COLLECTIONTYPE = "collectionType";
	public static String P = "<p>";
	public static String P_C = "</p>";
	
	//JtvVideoContent
	public static String LINK = "link";
	
	
	
	
}
