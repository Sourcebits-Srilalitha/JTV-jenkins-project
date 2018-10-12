package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.ItemApprDocs;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface ItemApprDocsRep extends JpaRepository<ItemApprDocs, Integer> {
	
	@Transactional
	@Modifying
	@Query(QueryConstants.QRY_DELETE_EXISTING_DOCS)
	public void deleteExistingDocs(@Param(QueryConstants.DEL_ITEM) int delItem);

	public ItemApprDocs getById(int itemId);

	@Query(QueryConstants.QRY_GET_BY_ITEMID_APPDOCS)
	public List<ItemApprDocs> getByItemId(@Param(QueryConstants.ID_) int id);

	

}
