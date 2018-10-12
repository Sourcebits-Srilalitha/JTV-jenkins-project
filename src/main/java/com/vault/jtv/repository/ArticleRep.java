package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.Article;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false,collectionResourceRel=QueryConstants.ARTICLES, path=QueryConstants.ARTICLE)//this will force the endpoint to be called 'article', default would be the table name - 'articles'
public interface ArticleRep extends JpaRepository<Article, Integer> { // extending with jparepsoitory instructs spring boot to create the default CRUD endpoints for this entity

	public Article findById(Integer id);
	
	List<Article> findByType(@Param(QueryConstants.ARTICLE_TYPE) Integer articletype); //this will allow the controller to use the specific jpa function findBy and uses the userid column as the search criteria
	
	@Query(QueryConstants.QRY_COUNT_ARTICLES)
	public int countArticles();
	
	@Query(QueryConstants.QRY_FIND_ALL_ACTIVE_ARTICLES)
	List<Article> findAllActiveArticles(@Param(QueryConstants.IS_ACT) String isAct);
	
	
	
	@Query(QueryConstants.QRY_FIND_CONTENTID)
	List<String> findContentId();
	
	public Article findByContentId(String contentId);
	
	@Modifying	
	@Transactional
	@Query(QueryConstants.QRY_UPDATE_ARTICLES)	
	void updateArticles(@Param(QueryConstants.IS_ACT) String isAct, @Param(QueryConstants.ARTICLE_LIST) List<String> articleList);

	
	@Query(QueryConstants.QRY_GET_ALL_ARTICLE_SEARCH)	
	List<Article> getAllArticleSearch(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix,@Param(QueryConstants.IS_ACT) String isAct);
}
