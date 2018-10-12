package com.vault.jtv.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.ArticleTypes;
import com.vault.jtv.repository.ArticleTypeRep;
import com.vault.util.MessageConstants;


@Transactional
@Service
public class ArticleTypeServiceImp implements ArticleTypeService {
	
	
	@Autowired
	ArticleTypeRep articleTypeRep;

	
		
	@Override
	public List<ArticleTypes> getAllActiveArticles() {
		String isAct = MessageConstants.IS_ACTIVE;	
		List<ArticleTypes> articleList = null;
		Set<ArticleTypes> articleType=articleTypeRep.findAllActiveArticles(isAct);
		if(null != articleType && articleType.size()>0){
		 articleList = new ArrayList<>(articleType);
		}
		if(articleList != null && articleList.size() != 0){			
			Iterator<ArticleTypes> itr = articleList.iterator();			
			while(itr.hasNext()){
				ArticleTypes at = (ArticleTypes) itr.next();					
				if(null != at.getArticles() && at.getArticles().size() == 0){
					itr.remove();
				}				
			}
		} else {
			articleList = new ArrayList<>();
		}
		return articleList;
	}
	
	

	@Override
	public List<ArticleTypes> findAll() {		
		List<ArticleTypes> articleList = articleTypeRep.findAll();
		if(articleList != null && articleList.size() != 0){
			Iterator<ArticleTypes> itr = articleList.iterator();
			while(itr.hasNext()){
				ArticleTypes at = (ArticleTypes) itr.next();				
				if(null != at.getArticles() && at.getArticles().size() ==0){
					itr.remove();
				}
			}
		} else {
			articleList = new ArrayList<>();
		}
		return articleList;
	}

	@Override
	public ArticleTypes findById(int Id) {		
		return articleTypeRep.findById(Id);
	}

	@Override
	public ArticleTypes findByTitle(String title) {
		ArticleTypes type = null;
		try {
			type = articleTypeRep.findByTitle(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	@Override
	public ArticleTypes save(ArticleTypes type) {
		ArticleTypes savedType = null;
		try {
			savedType = articleTypeRep.save(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedType;
	}

	@Override
	public int updateInactive(List<Integer> idList, String inAct) {
		int updated = 0;
		try {
			updated = articleTypeRep.updateAsInactive(idList, inAct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
	
}
