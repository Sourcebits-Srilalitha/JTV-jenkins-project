package com.vault.jtv.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vault.jtv.model.VideoTypes;
import com.vault.util.QueryConstants;



public interface VideoTypeRep extends JpaRepository<VideoTypes, Integer> {

	@Query(QueryConstants.QRY_FINDBYID)
	VideoTypes findById(@Param(QueryConstants.VIDEO_ID) Integer videoId);
	
	@Query(QueryConstants.QRY_FIND_ALL_ACTIVE_VIDEOTYPES)	
	Set<VideoTypes> findAllActiveVideoTypes(@Param(QueryConstants.IS_ACT) String isAct);
	
	List<VideoTypes> findAll();
	
	@Query(QueryConstants.QRY_FIND_BY_VIDEOTYPENAME)
	VideoTypes findByName(@Param(QueryConstants.TITLE) String title, @Param(QueryConstants.IS_ACT) String isAct);
	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_UPDATE_VIDEOTYPE_AS_INACTIVE)
	public int updateAsInactive(@Param(QueryConstants.ID_LIST) List<Integer> idList, @Param(QueryConstants.IN_ACT) String inAct);

}
