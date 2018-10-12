package com.vault.jtv.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.ArticleTypes;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface ArticleTypeRep extends JpaRepository<ArticleTypes, Integer> {

	@Query(QueryConstants.QRY_FIND_ALL_ACTIVE_ARTICLETYPES)	
	Set<ArticleTypes> findAllActiveArticles(@Param(QueryConstants.IS_ACT) String isAct);

	List<ArticleTypes> findAll();
	
	@Query(QueryConstants.QRY_FIND_BY_ARTICLETYPE_TITLE)
	ArticleTypes findByTitle(@Param(QueryConstants.TITLE) String series);
	
	ArticleTypes findById(@Param(QueryConstants.ID) Integer Id);
	
	@Modifying
	@Transactional
	@Query(QueryConstants.QRY_UPDATE_ARTICLES_INACT)
	public int updateAsInactive(@Param(QueryConstants.ID_LIST) List<Integer> idList, @Param(QueryConstants.IN_ACT) String inAct);
}
