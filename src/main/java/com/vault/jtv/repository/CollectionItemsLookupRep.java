package com.vault.jtv.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vault.jtv.model.CollectionItemsLookup;
import com.vault.util.QueryConstants;

public interface CollectionItemsLookupRep extends JpaRepository<CollectionItemsLookup, Integer>{
	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_DELETE_FROM_LOOKUP)
	public int deleteFromLookup(@Param(QueryConstants.CID) Integer cid);

	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_DELETE_COLLECTIONS)
	public void deleteCollections(@Param(QueryConstants.ITEM_ID) int itemId);

}
