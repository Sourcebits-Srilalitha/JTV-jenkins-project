package com.vault.jtv.services;

import java.util.List;

import com.vault.jtv.model.ArticleTypes;

public interface ArticleTypeService {

	List<ArticleTypes> findAll();

	List<ArticleTypes> getAllActiveArticles();

	ArticleTypes findById(int Id);
	
	ArticleTypes findByTitle(String title);

	public ArticleTypes save(ArticleTypes type);
	
	public int updateInactive(List<Integer> idList, String inAct);
	
}
