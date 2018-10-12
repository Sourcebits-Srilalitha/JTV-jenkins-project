package com.vault.jtv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.Article;
import com.vault.jtv.model.ArticleImages;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false,collectionResourceRel=QueryConstants.ARTICLES, path=QueryConstants.ARTICLE)//this will force the endpoint to be called 'article', default would be the table name - 'articles'
public interface ArticleImagesRep extends JpaRepository<Article, Integer> { // extending with jparepsoitory instructs spring boot to create the default CRUD endpoints for this entity

	public ArticleImages findById(Integer id);
	
	@Query(QueryConstants.QRY_FINDBYARTICLE_ID)
	List<Article> findByArticleId(@Param(QueryConstants.ARTICLE_ID) Integer articleid); //this will allow the controller to use the specific jpa function findBy and uses the userid column as the search criteria
	
	@Query(QueryConstants.QRY_COUNT_ARTICLE_IMAGES)
	public int countArticleImages();
	
	@Query(QueryConstants.QRY_FINDARTICLEIMAGES_BY_ISHEROIMAGE)
	ArticleImages findArticleImagesByIsHeroImage(@Param(QueryConstants.IS_HERO_IMAGE) Boolean isHeroImage);
	
	@Transactional
	@Modifying
	@Query(QueryConstants.QRY_DELETE_ARTICLE_IMAGES)
	void deleteArticleImages(@Param(QueryConstants.ARTICLE_ID) Integer articleId);
}