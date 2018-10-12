package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.WishList;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported=false)
public interface WishlistitemRep extends JpaRepository<WishList, Integer > {
	
	List<WishList> findAll();
	
	@Query(QueryConstants.QRY_FIND_WL_BY_USERID)
	List<WishList> findByUserId(@Param(QueryConstants.USER_ID) Integer userId,@Param(QueryConstants.IS_ACT) String isAct);

	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_REMOVE_FROM_WISHLIST)
	int removeFromWishList(@Param(QueryConstants.USER_ID) Integer userId, @Param(QueryConstants.GEMOPEDIAID) Integer gemopedia_Id, @Param(QueryConstants.IS_ACT) String isAct);
	
	WishList findByUserIdAndGemopediaIdAndActive(int userId, int gemopedia_Id, String active);
	
	
	@Query(QueryConstants.QRY_FIND_EXTENDEDBYUSERID)
	List<Object> findExtendedByUserId(@Param(QueryConstants.USER_ID) Integer userId,@Param(QueryConstants.IS_ACT) String isAct);
	
	@Query(QueryConstants.QRY_GET_COUNT)
	public int getCount(@Param(QueryConstants.USER_ID) Integer userId,@Param(QueryConstants.IS_ACT) String isAct);

	
	@Query(QueryConstants.QRY_FIND_GEMOPEDIAIDS)
	List<Integer> findGemopediaIds(@Param(QueryConstants.USER_ID) int userId, @Param(QueryConstants.GEM_LIST) List<Integer> gemList, @Param(QueryConstants.IS_ACT) String isAct);

	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_REMOVE_ITEMS_FROM_WISHLIST)
	int removeItemsFromWishList(@Param(QueryConstants.USER_ID) int userId, @Param(QueryConstants.GEM_LIST) List<Integer> gemList, @Param(QueryConstants.IN_ACT) String inAct); 
}

