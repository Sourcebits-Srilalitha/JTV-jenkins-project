package com.vault.jtv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.CollectionItems;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface CollectionItemRep extends JpaRepository<CollectionItems, Integer>{

	public CollectionItems getById (int itemId);	

	@Query(value = QueryConstants.QRY_GET_CUSTOM_COLLECTION_BY_ID, nativeQuery = true)
	public List<CollectionItems> getCustomCollectionById(@Param(QueryConstants.COLLECTION_ID) int id, @Param(QueryConstants.IS_ACT) String isAct);

	
	@Query(value = QueryConstants.QRY_GET_ALL_COLLECTION_ITEMS,nativeQuery = true)
	public List<CollectionItems> getAllCollectionItems(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix,@Param(QueryConstants.IS_ACT) String isAct, @Param(QueryConstants.USER_ID) int userId);
}
