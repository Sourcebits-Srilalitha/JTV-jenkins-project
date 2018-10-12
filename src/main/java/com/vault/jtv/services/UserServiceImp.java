package com.vault.jtv.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.beans.TokenBean;
import com.vault.jtv.model.UserAccount;
import com.vault.jtv.repository.UserRep;
import com.vault.util.MessageConstants;

@Service
/* this service implementation functions as isolation between the api exposure and the database/repository*/
public class UserServiceImp implements UserService{

	@Autowired
	private KeycloakUtility keycloakUtility;
	
	private final UserRep repository;
	
	@Autowired
	private  TokenService tokenService;
	
	@Inject
	public UserServiceImp (final UserRep repository){
		this.repository = repository;
		
	}
	
	
	
	protected Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
	
	@Override
	//@Transactional
	public UserAccount create (final UserAccount userAccount){
		userAccount.setCreated_on(new Date());
		try {
			if(userAccount.getCustomerId() != null && userAccount.getUsername() != null){
				return repository.save(userAccount); //this is autocreated by Springboot
			} else {
				return null;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public UserAccount findById(Integer id) {
        UserAccount result = findUserById(id);
        return result; 
    }
	
	@Override
	public UserAccount findByUsernameAndCustomerId(String username, String customerId) {
        UserAccount result = repository.findByUsernameAndCustomerId(username, customerId);
        return result; 
    }
	
	@Override
	public UserAccount findByUsername (String username) {
		UserAccount result = null;
		try {
			result = repository.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result; 
    }
	
	@Override
    public List<UserAccount> findAll() {
        List<UserAccount> userEntries = repository.findAll();
        return userEntries;
    }

	@Override
	@Transactional
	public UserAccount update (final UserAccount userAccount){
		userAccount.setModified_on(new Date());
		return repository.save(userAccount);
	}
	
	@Override
	public Integer countUsers (){
		return repository.countUsers();
	}
	
	private UserAccount findUserById(Integer id) {
        Optional<UserAccount> result = repository.findById(id);
        return result.orElseThrow(() -> new UserNotFoundException(id));         
    }
	
	@Override
	public Boolean authenticate(String userName, String JtvUserid)
    {
        UserAccount user = repository.findByUsername(userName);
        if (null!=user)
        {
            return user.getEnterpriseId().equals(JtvUserid);
        }
        return false;
    }
	
	@Override
	public UserAccount findByJtv_User_Id(String EnterpriseId) {
		UserAccount result = repository.findByEnterpriseId(EnterpriseId);
        return result;
	}
	
	@Override
	public UserAccount findByCustomerId(String customerId) {
		return repository.findByCustomerId(customerId);
	}

	

	@Override
	public TokenBean loginUser(String userName, String password) {
		logger.info(MessageConstants.LOGIN_USER_);
		TokenBean keyToken=null;
		try{		
			keyToken =keycloakUtility.getUserToken(userName, password);	
		}catch(Exception e){
			e.printStackTrace();
		}		
		return keyToken;
	}

	public UserAccount addUser(String userName,String password,String keyToken) {
		logger.info(MessageConstants.ADD_USER);
		UserAccount userAccount = null;
		try {
			String enterpriseId=tokenService.getEnterpriseID(keyToken);
			//logger.info("enterpriseId"+enterpriseId);
			if(enterpriseId != null && !enterpriseId.isEmpty()){			
				//add the offline token
				userAccount=keycloakUtility.getOffUserToken(userName,password);				
				userAccount.setCreated_on(new Date());	
				userAccount.setUsername(userName);	
				userAccount.setEnterpriseId(enterpriseId);
				return repository.save(userAccount); 		
			}							
		} catch (Exception e) {
			e.printStackTrace();			
		}		
	return userAccount;
	}

	@Override
	public UserAccount findByUsername(String userName, String password, String keyToken) {
		logger.info(MessageConstants.FIND_BY_USERNAME);
		UserAccount userAccount = null;
		try{
			String enterpriseId=tokenService.getEnterpriseID(keyToken);
			//logger.info("enterpriseId"+enterpriseId);
			if(enterpriseId != null && !enterpriseId.isEmpty()){
				//changes for new cust_id 
				userAccount = repository.findByUsername(userName);		
				//add the offline token if not exists 
					UserAccount user=keycloakUtility.getOffUserToken(userName,password);		
					userAccount.setModified_on(new Date());
					userAccount.setOfflineToken(user.getOfflineToken());	
					userAccount.setEnterpriseId(enterpriseId);
					return repository.save(userAccount); 
				
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		return userAccount;
	}

	
}

