package com.vault.jtv.controllers;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.beans.TokenBean;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.services.UserNotFoundException;
import com.vault.jtv.services.UserService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;

@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class UserController {

	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;

	@Inject
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleTodoNotFound(UserNotFoundException ex) {
		logger.error(MessageConstants.ERROR_HANDLING, ex.getMessage());
	}

	/**
	 * New Login implementation Req: username and password
	 */

	@RequestMapping(value = Constants.REQMAP_LOGIN_USER, method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> loginUser(@FormParam(MessageConstants.USER_NAME) String userName, @FormParam(MessageConstants.PASSWORD) String password) {
		logger.info(MessageConstants.LOGIN_USER + userName);
		UserAccount loginUser = null;
		HashMap<String, Object> userInfo = null;
		TokenBean keyToken = null;
		if (null != userName && null != password && !userName.isEmpty() && !password.isEmpty()) {
			keyToken = userService.loginUser(userName, password);
			if (null != keyToken) {
				loginUser = userService.findByUsername(userName, password, keyToken.getToken());
				if (null != loginUser) {
					userInfo = new HashMap<String, Object>();
					userInfo.put(MessageConstants.USER_DATA, loginUser);
					userInfo.put(MessageConstants.TOKEN_DATA, keyToken);
					return ResponseEntity.ok(userInfo);
				} else {
					loginUser = userService.addUser(userName, password, keyToken.getToken());
					if (loginUser != null) {
						userInfo = new HashMap<String, Object>();
						userInfo.put(MessageConstants.USER_DATA, loginUser);
						userInfo.put(MessageConstants.TOKEN_DATA, keyToken);
						return ResponseEntity.ok(userInfo);
					} else {
						return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.USER_NOT_CREATED,
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value() + "-" + MessageConstants.UNATHORIZED,
						HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.INVALID_UP, HttpStatus.BAD_REQUEST);
		}
	}

}