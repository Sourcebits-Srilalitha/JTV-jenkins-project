
package com.vault.jtv.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.ArchiveQuestionnaire;
import com.vault.jtv.model.Collection;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false,collectionResourceRel=QueryConstants.COLLECTION, path=QueryConstants.COLLECTION)//this will force the endpoint to be called 'collection', default would be the table name - 'collections'
public interface CollectionRep extends JpaRepository<Collection, Integer> { // extending with jparepsoitory instructs spring boot to create the default CRUD endpoints for this entity

	public Collection findById(Integer id);
	
	@Query(QueryConstants.QRY_FIND_BY_USERID)
	List<Collection> findByUserId(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.IS_ACT) String isAct);
	
	@Query(QueryConstants.QRY_COUNT_USERS)
	public int countUsers();// countuser will be exposed as a custom query of this entiy to be consumed by the controller, service abstraction is not necessary at this point

	@Query(QueryConstants.QRY_GET_ALL_COLLECTION)
	public Set<Collection> getAllCollection(@Param(QueryConstants.IS_ACT) String isAct);	
	
	@Query(QueryConstants.QRY_GET_QUESTIONNAIRELIST)
	public List<ArchiveQuestionnaire> getQuestionnaireList();	
	
	@Query(QueryConstants.QRY_GET_COLLECTIONBYID)
	public Collection getCollectionById(@Param(QueryConstants.ID_) int id, @Param(QueryConstants.IS_ACT) String isAct);
	
	@Query(QueryConstants.QRY_GET_COLLECTIONBYNAME)
	public Collection getCollectionByName(@Param(QueryConstants.NAME) String name);
	
	@Query(QueryConstants.QRY_GET_SHOWCASECOLLECTIONS)	
	public Set<Collection> getShowcaseCollections(@Param(QueryConstants.IS_ACT) String isAct, @Param(QueryConstants.IS_VISIBLE) String isVisible);

	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_UPDATEASINACTIVE)
	public int updateAsInactive(@Param(QueryConstants.ID_LIST) List<Integer> idList, @Param(QueryConstants.IN_ACT) String inAct);
	
		
	@Query(value =QueryConstants.QRY_GET_CUSTOMCOLLECTIONDETAILS, nativeQuery = true)
	public int getCustomCollectionDetails(@Param(QueryConstants.IS_ACT) String isAct, @Param(QueryConstants.COLLECTION_ID) int collectionId);

	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_DELETE_COLLECTIONBYID)
	public int deleteCollectionById(@Param(QueryConstants.COLLECTION_ID) int collectionId, @Param(QueryConstants.IN_ACT) String inAct);

	@Query(QueryConstants.QRY_GET_REASONNAME)
	public String getReasonName(@Param(QueryConstants.REASON_ID) int reasonId);
	
	
	@Query(QueryConstants.QRY_GET_ALLUSERCOLLECTIONS)
	public List<Collection> getAllUserCollections(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix,@Param(QueryConstants.IS_ACT) String isAct, @Param(QueryConstants.USER_ID) int userId);

	@Query(QueryConstants.QRY_GET_ALLSHOWCASECOLLECTION)
	public List<Collection> getAllShowcaseCollection(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix,@Param(QueryConstants.IS_ACT) String isAct);
	
	
}
