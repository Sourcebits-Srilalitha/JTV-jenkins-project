package com.vault.jtv.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.Collection;
import com.vault.jtv.model.ShowcaseCollectionItems;
import com.vault.jtv.repository.CollectionRep;
import com.vault.jtv.repository.ShowcaseRep;
import com.vault.util.MessageConstants;

@Service
public class ShowcaseServiceImpl implements ShowcaseService{

	@Autowired
	private ShowcaseRep showcaseRep;
	
	@Autowired
	private CollectionRep collectionRep;
	
	
	
	@Override
	public List<Collection> getShowcaseCollections() {		
		return new ArrayList<>(collectionRep.getShowcaseCollections(MessageConstants.IS_ACTIVE, MessageConstants.IS_ACTIVE));
	}
	
	@Override
	public Map<String, List<ShowcaseCollectionItems>> getBirthstoneCollections(Integer collectionId) {
		Map<String, List<ShowcaseCollectionItems>> birthstoneMap = new HashMap<String, List<ShowcaseCollectionItems>>();
		List<ShowcaseCollectionItems> birthstoneList = null;
		try {
			birthstoneList = showcaseRep.findByCollection(collectionId);
			if(birthstoneList != null && birthstoneList.size() != 0){
				for(ShowcaseCollectionItems item: birthstoneList){
					List<ShowcaseCollectionItems> itemsList = null;
					if(item.getSubtitle() != null){
						if(birthstoneMap.containsKey(item.getSubtitle())){
							itemsList = birthstoneMap.get(item.getSubtitle());
						} else {
							itemsList = new ArrayList<ShowcaseCollectionItems>();
						}
						itemsList.add(item);
						birthstoneMap.put(item.getSubtitle(), itemsList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return birthstoneMap;
	}
	
	@Override
	public ShowcaseCollectionItems getShowcaseItemByName(String itemName, String collectionName) {
		ShowcaseCollectionItems showcaseItem = null;
		try {
			showcaseItem = showcaseRep.getItemByName(itemName, collectionName);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return showcaseItem;
	}
	
	@Override
	public ShowcaseCollectionItems save(ShowcaseCollectionItems item) {
		ShowcaseCollectionItems savedItem = null;
		try {
			savedItem = showcaseRep.save(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedItem;
	}
	
	@Override
	public int updateInactive(List<Integer> idList, String inAct) {
		int updated = 0;
		try {
			updated = showcaseRep.updateAsInactive(idList, inAct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
}
