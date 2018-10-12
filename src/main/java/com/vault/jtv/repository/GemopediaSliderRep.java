package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.GemopediaSliderItems;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface GemopediaSliderRep extends PagingAndSortingRepository<GemopediaSliderItems, Integer>  {

	
	
	@Query(QueryConstants.QRY_GET_BY_GEMID_GEMSLIDER)	
	public GemopediaSliderItems getByGemID(@Param(QueryConstants.GEMOPEDIA_ID) int gemopediaId, @Param(QueryConstants.IS_ACT) String isAct);

	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_UPDATEASINACTIVE_GEMSLIDER)
	public int updateAsInactive(@Param(QueryConstants.GEM_IDLIST) List<Integer> gemIdList, @Param(QueryConstants.IN_ACT) String inAct);


	@Query(QueryConstants.QRY_GET_ALLGEMS_GEMSLIDER)	
	public List<GemopediaSliderItems> getAllGems(@Param(QueryConstants.IS_ACT) String isAct);
	
}