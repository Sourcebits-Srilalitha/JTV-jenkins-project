package com.vault.jtv.services;

import java.util.List;

import com.vault.jtv.beans.TokenBean;
import com.vault.jtv.model.UserAccount;

public interface UserService {
	UserAccount create(UserAccount userAccount);
	
	UserAccount update(UserAccount userAccount);
	
	UserAccount findById(Integer id);
	
	UserAccount findByUsername (String username);
	
	UserAccount findByJtv_User_Id (String jtvId);
	
	UserAccount findByCustomerId (String customerId);
	
	UserAccount findByUsernameAndCustomerId (String username, String customerId);
	
	Boolean authenticate (String username, String JtvUserId);
	
	List<UserAccount> findAll();
	
	Integer countUsers();

	TokenBean loginUser(String userName, String password);

	UserAccount addUser(String userName,String password,String keyToken);

	UserAccount findByUsername(String userName, String password,String keyToken);

	
	
}

