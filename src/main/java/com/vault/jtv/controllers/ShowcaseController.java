package com.vault.jtv.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.model.Collection;
import com.vault.jtv.model.ShowcaseCollectionItems;
import com.vault.jtv.services.ShowcaseService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;


@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class ShowcaseController {
	
	@Autowired
	private ShowcaseService showcaseService;

	/**
	 * To get all the collection showcase details for the discover screen.
	 * Need to change once we get complete collection data from JTV
	 * @return
	 */
	@RequestMapping(value = Constants.REQMAP_SHOWCASE_COLLECTIONS, method= RequestMethod.GET)
	public List<Collection> getShowcaseCollectionName(){
		return showcaseService.getShowcaseCollections();
	}
	
	/**
	 * To get the collection showcase details of a particular showcaseitem
	 * Need to change once we get complete collection data from JTV
	 * @return
	 */
	//@RequestMapping(value = "/showcaseCollections/{collectionId}", method= RequestMethod.GET)
	public Map<String, List<ShowcaseCollectionItems>> getBirthstoneCollections(@PathVariable(MessageConstants.COLLECTION_ID) int collectionId){
		return showcaseService.getBirthstoneCollections(collectionId);
	}
}
