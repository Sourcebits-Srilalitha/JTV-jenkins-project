package com.vault.jtv.controllers;


import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.model.ArticleTypes;
import com.vault.jtv.services.ArticleTypeService;
import com.vault.util.Constants;

@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class ArticleTypeController {

	
	private final ArticleTypeService articleTypeService;
	
	@Inject
	public ArticleTypeController(ArticleTypeService articleTypeService ) {
		this.articleTypeService = articleTypeService;	
	}

	
	/**
	 * This api is to get all the active articlestypes with their respective article details
	 * @return list of all active articletypes with articles
	 */
	@RequestMapping(value = Constants.REQMAP_GET_ARTICLETYPES, method = RequestMethod.GET)
	public List<ArticleTypes> getAllActiveArticles(){
		return articleTypeService.getAllActiveArticles();
	}
	

}
