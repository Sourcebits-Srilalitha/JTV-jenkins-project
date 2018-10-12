package com.vault.jtv.services;

import java.util.List;
import java.util.Map;

import com.vault.jtv.model.Collection;
import com.vault.jtv.model.ShowcaseCollectionItems;

public interface ShowcaseService {

	public List<Collection> getShowcaseCollections();
	public Map<String, List<ShowcaseCollectionItems>> getBirthstoneCollections(Integer collectionId);
	public ShowcaseCollectionItems getShowcaseItemByName(String itemName, String collectionName);
	public ShowcaseCollectionItems save(ShowcaseCollectionItems item);
	public int updateInactive(List<Integer> idList, String inAct);
}
