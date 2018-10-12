package com.vault.jtv.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vault.jtv.model.Video;
import com.vault.util.QueryConstants;

//this will force the endpoint to be called 'collection', default would be the table name - 'collections'
public interface VideoRep extends JpaRepository<Video, Integer>{
	public Video findById(Integer id);
	

	List<Video> findByType(@Param(QueryConstants.VIDEO_TYPE) Integer videotype); //this will allow the controller to use the specific jpa function findBy and uses the userid column as the search criteria

	
	@Query(QueryConstants.QRY_COUNT_VIDEO)
	public int countVideo();
	
	@Query(QueryConstants.QRY_FIND_ALL_ACTIVEVIDEOS)
	List<Video> findAllActiveVideos(@Param(QueryConstants.IS_ACT) String isAct);
	
	@Query(QueryConstants.QRY_FIND_BY_VIDEONAME)
	Video findByName(@Param(QueryConstants.TITLE) String title, @Param(QueryConstants.TYPE) Integer type);
	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_UPDATE_VIDEO_AS_INACTIVE)
	public int updateAsInactive(@Param(QueryConstants.ID_LIST) List<Integer> idList, @Param(QueryConstants.IN_ACT) String inAct);

	@Query(QueryConstants.QRY_GETALLVIDEOSEARCH)	
	List<Video> getAllVideoSearch(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix,@Param(QueryConstants.IS_ACT) String isAct);
}
