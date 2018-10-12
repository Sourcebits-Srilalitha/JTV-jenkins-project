package com.vault.jtv.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.beans.TokenBean;
import com.vault.jtv.dto.AuthDTO;
import com.vault.jtv.services.TokenService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;

@RestController
public class AuthController {
	protected Logger logger = LoggerFactory.getLogger(AuthController.class);	

	
	
	
	@Autowired
	TokenService keyCloackTokenService;
	
	
	
	//keycloack new Implementation RefreshToken	
	
	@RequestMapping(value = Constants.REQMAP_REFRESHTOKEN, method = RequestMethod.POST)
    public ResponseEntity<?> jtvRefreshToken(HttpServletRequest request,@RequestBody AuthDTO auth) {
		TokenBean tokenBean = null;
		try {
			if(null != request.getHeader(MessageConstants.X_AUTH_TOKEN) && null != auth && null != auth.getEnterpriseId()){			
				tokenBean = keyCloackTokenService.validateRefreshToken(request.getHeader(MessageConstants.X_AUTH_TOKEN), auth.getEnterpriseId());
				if(null != tokenBean){
					return ResponseEntity.ok(tokenBean);
				}else{
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.UNATHORIZED, HttpStatus.UNAUTHORIZED);	
				}
			}else{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value()+"-"+MessageConstants.TOKEN_HEADER, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value()+"-"+MessageConstants.UNATHORIZED, HttpStatus.UNAUTHORIZED);	
    }

}

