package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.TmpArchiveItems;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface TmpArchiveItemsRep extends JpaRepository<TmpArchiveItems, Integer>{
	
	@Query(QueryConstants.QRY_GET_ARCHIVEITEMS)	
	public List<TmpArchiveItems> getArchiveItems(@Param(QueryConstants.USER_ID) int userId);

	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_DELETE_ARCHIVEITEM)
	public void deleteArchiveItem(@Param(QueryConstants.USER_ID) Integer userId);

	}
