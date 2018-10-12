package com.vault.jtv.services;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vault.jtv.beans.TokenBean;
import com.vault.jtv.model.UserAccount;

//
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;


@Component
public class KeycloakUtility {
	
	private static Logger logger = LoggerFactory.getLogger(KeycloakUtility.class);
	
	@Value("${keycloak.targetUrl}")
	private String targetUrl;
	
	@Value("${keycloak.username}")
	private String username;
	
	@Value("${keycloak.password}")
	private String password;
	
	@Value("${keycloak.client_id}")
	private String client_id;
	
	@Value("${keycloak.grant_type}")
	private String grant_type;
	
	@Value("${keycloak.clientSecret}")
	private String client_secret;
	
	@Value("${order.targetUrl}")
	private String orderHistoryUrl;
	
	@Value("${order.origin}")
	private String orderOrigin;
	
	@Value("${order.referer}")
	private String orderReferer;
	
	private UserAccount user; 
	
	@Autowired
	private UserService userService;
	

	
	@Inject
	public KeycloakUtility(UserService userService) {
		this.userService = userService;
	}
	
		
	public JsonObject getOrderHistoryJSON(UserAccount user, String token ){
		logger.info("----------getOrderHistoryJSON()-----");
		JsonObject returnJson = null;
		String startDate = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		try {
			//removing as we already have user info
			//UserAccount user = userService.findByJtv_User_Id(enterpriseId);
			if(user != null && user.getLastJTVCall() != null){
				startDate =  format.format(user.getLastJTVCall());
			}else{
				startDate = "";
			}
			returnJson = getGemstoneJSON(user.getEnterpriseId(),startDate ,token);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return returnJson;
	}
	
	private JsonObject getGemstoneJSON(String enterpriseId, String startdate_formatted ,String token){
		//RestTemplate restTemplate = new RestTemplate();	
		RestTemplate restTemplate =null;;
		try {
			restTemplate = restTemplate();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JsonObject jsonResponse = null;
		String bearerToken = "Bearer ";
		Date curDate = new Date();
		String jtvConversationID="WEB123456890";
		try{
		UUID uuid = UUID.randomUUID();
		if(null != uuid){
		jtvConversationID=uuid.toString();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String DateToStr = format.format(curDate);
		String orderHistoryURI = orderHistoryUrl;
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("customer-id",enterpriseId);
			if(startdate_formatted.length() > 0){
				params.add("start-date", startdate_formatted);
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(orderHistoryURI).queryParams(params);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			headers.add("X-JTV-CONVERSATION-ID", jtvConversationID);
			headers.add("Authorization", bearerToken.concat(token));				
			headers.setOrigin(orderOrigin);
			headers.add("Referer", orderReferer);
			headers.add("X-Requested-With", "XMLHttpRequest");
			headers.add("X-JTV-CLIENT-REQUEST-TIME",DateToStr);				
			
			HttpEntity<String> entity = new HttpEntity<String>( headers);			
			ResponseEntity<String> response = restTemplate.exchange(builder.queryParams(params).build().encode().toUri(), HttpMethod.GET, entity, String.class);	
			if(response.getStatusCode().value() == 200 && null !=response.getBody()){
				jsonResponse = (JsonObject) new JsonParser().parse(response.getBody());
				user = userService.findByJtv_User_Id(enterpriseId);
				user.setLastJTVCall(curDate);
				userService.update(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return jsonResponse;
	}	
	
	
	//new login Implementation
	protected TokenBean getUserToken(String userName,String password){	
		logger.info("----------getUserToken()-----");
		JsonObject jsonResponse = null;
		String accessToken = "";
		String refreshToken = "";
		String grantType="password";
		TokenBean tokenBean=null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("client_id", client_id);
			map.add("grant_type", grantType);
			map.add("client_secret", client_secret);
			map.add("username", userName);
			map.add("password", password);
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);			
			ResponseEntity<String> response = new RestTemplate().postForEntity( targetUrl, request , String.class);		
			if(response.getStatusCode().value() == 200 && null !=response.getBody()){
				jsonResponse = (JsonObject) new JsonParser().parse(response.getBody());	
				if(null != jsonResponse){
					accessToken = jsonResponse.get("access_token").getAsString();			
					refreshToken = jsonResponse.get("refresh_token").getAsString();					
					tokenBean = new TokenBean();
					tokenBean.setToken(accessToken);
					tokenBean.setRefreshToken(refreshToken);
				}				
			}
					
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return tokenBean;
	}
	
	
	
	protected UserAccount getOffUserToken(String userName,String password){		
		logger.info("----------getOffUserToken()-----");
		JsonObject jsonResponse = null;	
		String accessToken = "";
		String offlineToken = "";
		String grantType="password";
		String scope="offline_access";
		UserAccount user=null;		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("username", userName);
			map.add("password", password);
			map.add("scope", scope);
			map.add("client_id", client_id);
			map.add("grant_type", grantType);
			map.add("client_secret", client_secret);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,headers);
			ResponseEntity<String> response = new RestTemplate().postForEntity(targetUrl, request, String.class);
			if (response.getStatusCode().value() == 200 && null != response.getBody()) {
				jsonResponse = (JsonObject) new JsonParser().parse(response.getBody());
				if(null !=jsonResponse){
					accessToken = jsonResponse.get("access_token").getAsString();
					offlineToken = jsonResponse.get("refresh_token").getAsString();									
					user=new UserAccount();				
					user.setOfflineToken(offlineToken);									
				}						
			}

		} catch (Exception e) {
			e.printStackTrace();			
		}
		return user;
	}
	
	
	protected TokenBean getRefreshToken(String token){		
		logger.info("----------getRefreshToken()-----");
		JsonObject jsonResponse = null;
		String accessToken = "";
		String refreshToken = "";
		String grantType="refresh_token";
		TokenBean tokenBean=null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("client_id", client_id);
			map.add("grant_type", grantType);
			map.add("client_secret", client_secret);
			map.add("refresh_token", token);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);			
			ResponseEntity<String> response = new RestTemplate().postForEntity( targetUrl, request , String.class);		
			if(response.getStatusCode().value() == 200 && null !=response.getBody()){
				jsonResponse = (JsonObject) new JsonParser().parse(response.getBody());	
				if(null != jsonResponse){
					accessToken = jsonResponse.get("access_token").getAsString();			
					refreshToken = jsonResponse.get("refresh_token").getAsString();					
					tokenBean = new TokenBean();
					tokenBean.setToken(accessToken);
					tokenBean.setRefreshToken(refreshToken);
				}				
			}
					
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return tokenBean;
	}
	
	
	  @Bean
	 public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
   	 HttpComponentsClientHttpRequestFactory requestFactory =new HttpComponentsClientHttpRequestFactory();	    	
   	 HttpClientBuilder b = HttpClientBuilder.create(); 
   	 SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() { public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { return true; }

		@Override
		public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {				
			return true;
		} }).build();
   	 b.setSslcontext( sslContext);
   	 // or SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
   	 HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER; 
   	 SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
   	 Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create() .register("http", PlainConnectionSocketFactory.getSocketFactory()) .register("https", sslSocketFactory) .build(); 
   	 // allows multi-threaded use
   	 PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
   	 b.setConnectionManager( connMgr);
   	 HttpClient httpClient = b.build();
   	 
   	 
   	 requestFactory.setHttpClient(httpClient);
	    
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
	}
 
	
	
}
