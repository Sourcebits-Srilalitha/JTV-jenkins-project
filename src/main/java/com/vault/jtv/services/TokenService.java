package com.vault.jtv.services;


import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vault.jtv.beans.TokenBean;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.repository.UserRep;
import com.vault.util.MessageConstants;
 
@Service
public class TokenService {
	
	private static Logger logger = LoggerFactory.getLogger(TokenService.class);
	
	@Autowired
	private  UserService userService;
	
	@Autowired
	KeycloakUtility keycloakUtility;
	
	@Autowired 
	UserRep userRepository;
	 
	 
	 
	    public UserAccount getUser(String jwtToken)
	    {
	    	UserAccount securityUser = null;
	    	try {
	    		logger.info(MessageConstants.GET_USER);
		        String[] split_string = jwtToken.split(MessageConstants.SEPARATOR);		        
		        String base64EncodedBody = split_string[1];
		        Base64 base64Url = new Base64(true);
		        String body = new String(base64Url.decode(base64EncodedBody));
		        
		        JsonObject jsonResponse = (JsonObject) new JsonParser().parse(body);
		        if(null != jsonResponse && null != jsonResponse.get(MessageConstants.CUSTOMERID) && null != jsonResponse.get(MessageConstants.EXP) && null != jsonResponse.get(MessageConstants.IAT)){
		        	Date exp = new Date(((Integer) jsonResponse.get(MessageConstants.EXP).getAsInt()).longValue()*1000);
			        Date iat = new Date(((Integer) jsonResponse.get(MessageConstants.IAT).getAsInt()).longValue()*1000);
			        logger.info(MessageConstants.EXP_LOG +exp +MessageConstants.IAT_LOG+iat);
			        Date currentDate = new Date();
			        if(currentDate.before(exp) && currentDate.after(iat)){	
			        	logger.info(MessageConstants.INSIDE_GET_USER);
				        String id = jsonResponse.get(MessageConstants.CUSTOMERID).getAsString();	        
				        securityUser = userService.findByJtv_User_Id(id);
				        logger.info(MessageConstants.GET_USER_USERNAME +securityUser.getUsername());
			        }
		        }
	    				        	
			} catch (Exception e) {
				e.printStackTrace();
			}	                
	        return securityUser;
	    }
	 
	    
	    protected String getEnterpriseID(String jwtToken)
	    {
	    	String enterpriseID = null;
	    	try {
	    		logger.info(MessageConstants.GET_ENTERPRISE_ID);
		        String[] split_string = jwtToken.split(MessageConstants.SEPARATOR);		        
		        String base64EncodedBody = split_string[1];
		        Base64 base64Url = new Base64(true);		  
		        String body = new String(base64Url.decode(base64EncodedBody));		       
		        
		        JsonObject jsonResponse = (JsonObject) new JsonParser().parse(body);
		        if(null != jsonResponse && null != jsonResponse.get(MessageConstants.CUSTOMERID)){		        	 	
		        	   enterpriseID = jsonResponse.get(MessageConstants.CUSTOMERID).getAsString();	  
		        	   logger.info(MessageConstants.GET_ENTERPRISE_ID_SUCCESS);
		        	   }
			               	
			} catch (Exception e) {
				e.printStackTrace();
			}	                
	        return enterpriseID;
	    }
	 
	
		public TokenBean validateRefreshToken(String refreshToken, String enterpriseID) {
			TokenBean tokenBean  = null;
			UserAccount securityUser = null;
	        try {	        	
	        	logger.info(MessageConstants.VALIDATE_REFRESH_TOKEN);
		        String[] split_string = refreshToken.split(MessageConstants.SEPARATOR);		        
		        String base64EncodedBody = split_string[1];
		        Base64 base64Url = new Base64(true);		       
		        String body = new String(base64Url.decode(base64EncodedBody));
		        
		        JsonObject jsonResponse = (JsonObject) new JsonParser().parse(body);
		        if(null != jsonResponse){	
			        //if refresh token is not expired - get a new token  
			        //if(currentDate.before(exp) && currentDate.after(iat)){}  			    	
		        	logger.info(MessageConstants.INSIDE_REFRESH_TOKEN );
		        	tokenBean=keycloakUtility.getRefreshToken(refreshToken);
		        	if(null == tokenBean){
		        		//if expired then send offline token and get new token
			        	logger.info(MessageConstants.INSIDE_OFFLINE_TOKEN );
			        	 securityUser = userService.findByJtv_User_Id(enterpriseID);
			        	 if(null != securityUser && null != securityUser.getOfflineToken()){
			        		 logger.info(MessageConstants.REFRESH_TOKEN_USERNAME +securityUser.getUsername());
			        		 tokenBean=keycloakUtility.getRefreshToken(securityUser.getOfflineToken());
			        		 if(null != tokenBean){
			        			 //save the refresh token as offline token			        			
			        			 securityUser.setModified_on(new Date());
				        		 securityUser.setOfflineToken(tokenBean.getRefreshToken()); 
				        		 userRepository.save(securityUser);
			        		 }
			        		
			        	 }
		        	}
		        
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return tokenBean;
		}
		
	
}
