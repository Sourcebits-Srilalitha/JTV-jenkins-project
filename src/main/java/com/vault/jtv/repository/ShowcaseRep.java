package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vault.jtv.model.ShowcaseCollectionItems;
import com.vault.util.QueryConstants;

public interface ShowcaseRep extends JpaRepository<ShowcaseCollectionItems, Integer>{

	@Query(QueryConstants.QRY_GET_NAMES)
	public List<String> getNames();
	@Query(QueryConstants.QRY_FIND_BY_COLLECTION)
	public List<ShowcaseCollectionItems> findByCollection(@Param(QueryConstants.COLLECTION_ID) Integer collectionId);
	
	public ShowcaseCollectionItems getById(int showcaseitemId);

	@Query(QueryConstants.QRY_GET_ITEMBYNAME)
	public ShowcaseCollectionItems getItemByName(@Param(QueryConstants.TITLE) String title, @Param(QueryConstants.NAME) String name);
	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_UPDATEASINACTIVE_SHOWCASE)
	public int updateAsInactive(@Param(QueryConstants.ID_LIST) List<Integer> idList, @Param(QueryConstants.IN_ACT) String inAct);
	
	@Query(value =QueryConstants.QRY_GET_ALLSHOWCASEITEMSEARCH,nativeQuery = true)	
	public List<ShowcaseCollectionItems> getAllShowcaseItemSearch(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix,@Param(QueryConstants.IS_ACT) String isAct);
	
	@Query(QueryConstants.QRY_GET_ALLSHOWCASEDETAILS)
	public int getAllShowcaseDetails(@Param(QueryConstants.COLLECTION_ID) Integer collectionId, @Param(QueryConstants.IS_ACT) String isAct);
	
	
	
}
