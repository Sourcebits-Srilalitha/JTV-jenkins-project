package com.vault.jtv.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItems;
import com.vault.util.QueryConstants;



public interface VaultRep extends JpaRepository<Collection, Integer>{
	
	Set<Collection> findByUserId(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.IS_ACT) String isAct, @Param(QueryConstants.IS_VISIBLE) String isVisible);
	
	// phase2: changes for new collection item flow
	@Query(QueryConstants.QRY_FIND_ITEMS_BY_USERID)
	Set<CollectionItems> findItemsByUserId(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.IS_VISIBLE) String isVisible);
		
	// phase2: changes for new collection item flow
	@Modifying
	@Transactional	
	@Query(QueryConstants.QRY_DELETE_FROM_VAULT)
	public int deleteFromVault(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.IN_ACT) String inAct, @Param(QueryConstants.ITEM_ID) Integer itemId);
	
	
	List<Collection> findByUserIdAndActive(Integer userId, String active);
	
	//sql	
	@Query(value ="SELECT c1.item_id, c1.user_id, c1.created_on, c1.action_type, ci.item_name, ci.product_image_url , ci.is_third_party ,(SELECT pp.image_link FROM collection_item_images pp WHERE c1.created_on IN "
			+ "(SELECT MAX(c2.created_on) FROM collection_item_history c2 WHERE c2.user_id = :userId AND pp.collection_item_id = c2.item_id AND pp.collection_item_id = c1.item_id GROUP BY c2.item_id ) LIMIT 1) AS prod_url ,aq.reason "
			+ "FROM collection_item_history c1, collection_items ci, archive_questionnaire aq WHERE c1.created_on IN (SELECT MAX(c2.created_on) FROM collection_item_history c2 "
			+ "WHERE c2.user_id = :userId AND c2.item_id=c1.item_id  GROUP BY c2.item_id) AND c1.action_type IS NOT NULL AND ci.id IN (c1.item_id) AND c1.action_type = aq.id", nativeQuery = true)
	
	//oracle
	//@Query(value =QueryConstants.QRY_GET_ARCHIVEDITEMS, nativeQuery = true)	
	List<Object> getArchiveditems(@Param(QueryConstants.USER_ID) Integer userId);
	

	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_RESTORE_FROM_VAULT)
	public int restoreFromVault(@Param(QueryConstants.ITEMIDS) List<Integer> itemIds, @Param(QueryConstants.IS_ACT) String isAct);
	

	@Query(QueryConstants.QRY_FIND_COLLECTION)
	Set<Collection> findCollection(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.IS_ACT) String isAct);

	@Query(QueryConstants.QRY_GET_RECENTLYADDEDITEMS)
	public List<CollectionItems> getRecentlyAddedItems(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.BACK_DATE) Date backDate, @Param(QueryConstants.IS_VISIBLE) String isVisible);
	
	

}