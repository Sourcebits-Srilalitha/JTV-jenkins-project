package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.CollectionItemImages;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface CollectionItemImageRep extends JpaRepository<CollectionItemImages, Integer>{

	public CollectionItemImages getById (int itemId);
	
	@Transactional
	@Modifying
	@Query(QueryConstants.QRY_DELETE_EXISTING_IMAGES)
	public void deleteExistingImages(@Param(QueryConstants.ID_) int id);

	@Transactional
	@Modifying
	@Query(QueryConstants.QRY_DELETE_EXISTING_ITEM_IMAGES)
	public void deleteExistingItemImages(@Param(QueryConstants.DEL_ITEM) int delItem);

	@Query(QueryConstants.QRY_GET_BY_ITEMID)
	public List<CollectionItemImages> getByItemId(@Param(QueryConstants.ID_) int id);
}
