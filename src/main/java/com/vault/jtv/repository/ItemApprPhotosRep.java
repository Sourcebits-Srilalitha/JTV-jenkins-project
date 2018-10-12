package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.ItemApprPhotos;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface ItemApprPhotosRep extends JpaRepository<ItemApprPhotos, Integer> {
	
	@Transactional
	@Modifying
	@Query(QueryConstants.QRY_DELETE_EXISTING_PHOTOS)
	public void deleteExistingPhotos(@Param(QueryConstants.DEL_ITEM) int delItem);

	
	public ItemApprPhotos getById(int itemId);


	@Query(QueryConstants.QRY_GET_BY_ITEMID_APP_PHOTOS)
	public List<ItemApprPhotos> getByItemId(@Param(QueryConstants.ID_) int id);

	

}