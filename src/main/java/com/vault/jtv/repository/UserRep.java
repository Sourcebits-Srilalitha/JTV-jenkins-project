package com.vault.jtv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.UserAccount;
import com.vault.util.QueryConstants;
/**
 * Repository for UserRep data implemented using Spring Data JPA.
 * 
 * @author Christian Rauch
 */
@RepositoryRestResource(exported = false)
public interface UserRep extends JpaRepository<UserAccount, Integer > {

	void delete(UserAccount userAccount);
	
	Optional<UserAccount> findById (Integer Id);
	
	UserAccount findByUsername (String username);
	
	List<UserAccount> findAll();
	
	UserAccount findByUsernameAndCustomerId (String username, String customerId);
	
	@Query(QueryConstants.QRY_COUNT_USERS_UA)
	public int countUsers();

	UserAccount findByEnterpriseId(String enterpriseId);
	
	UserAccount findByCustomerId(String customerId);

	@Query(QueryConstants.QRY_FIND_BY_USERNAME_ENTERPRISEID)	
	UserAccount findByUsernameAndEnterpriseId(@Param(QueryConstants.USER_NAME) String userName, @Param(QueryConstants.ENTERPRISE_ID) String enterpriseId);

}
