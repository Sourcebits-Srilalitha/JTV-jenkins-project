package com.vault.jtv.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.model.GemopediaSliderItems;
import com.vault.jtv.model.ShowcaseCollectionItems;
import com.vault.jtv.repository.GemopediaItemRep;
import com.vault.jtv.repository.GemopediaSliderRep;
import com.vault.jtv.repository.ShowcaseRep;
import com.vault.util.MessageConstants;


@Service
public class GemServiceImp implements GemService{
	
	@Autowired
	private ShowcaseRep showcaseRep;
	
	private final GemopediaItemRep  repository;
	
	@Autowired
	private GemopediaSliderRep gemopediaSliderRep;
	
	
	
	
	@Inject
	public GemServiceImp (final GemopediaItemRep repository){
		this.repository = repository;
		
	}
	

	@Override	
	public GemopediaItem findById(int Id) throws Exception{		
		return repository.findById(Id);
	}


	@Override
	public Page<GemopediaItem> findAllByPage(Pageable pageable) {		
		return repository.findAll(pageable);			
	}


	@Override
	public GemopediaItem getGemDetails(String gemstone, String composition, String optical) throws Exception {
		GemopediaItem g= null;
		Integer itemId= repository.findGemDetails(gemstone, composition, optical);
		if(null != itemId){
			 g= findById(itemId);
		}
		return g;
	}


	@Override
	public GemopediaItem getGemById(Integer showcaseitemId) {
		ShowcaseCollectionItems item = showcaseRep.getById(showcaseitemId);
		List<GemopediaItem> gemList = null;
		if(item != null){
			gemList = repository.getGemForShowcase(item.getGpCommName());
			if(gemList != null && gemList.size() != 0){
				return gemList.get(0);
			}			
		}
		return null;		
	}


	@Override
	public Integer getGemIDByCommonName(String gpCommonName) {	
		return repository.getGemIDByCommonName(gpCommonName);
	}


	@Override
	public List<Map<String, Object>> getGemItems() {
		List<Map<String, Object>> gemItemList=new ArrayList<>();
		Map<String,Object> gemMap= null;			
		try{
		List<GemopediaSliderItems> gemSliderItems=gemopediaSliderRep.getAllGems(MessageConstants.IS_ACTIVE);
		if(gemSliderItems != null && gemSliderItems.size() > 0){		
			for(GemopediaSliderItems eachGem:gemSliderItems){
				gemMap= new HashMap<String,Object>();
				if(null != eachGem.getPage_index() && eachGem.getPage_index() != 0 && null != eachGem.getGemopediaId() && eachGem.getGemopediaId() != 0){
					GemopediaItem gemItem=repository.getGemItem(eachGem.getGemopediaId());
					if(null != gemItem){
						gemMap.put(MessageConstants.PAGE_INDEX, eachGem.getPage_index());
						gemMap.put(MessageConstants.GEMOPEDIA_ITEM, gemItem);						
					}	
					gemItemList.add(gemMap);
				}	
				
			}
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return gemItemList;
	}


	@Override
	public List<GemopediaItem> getAllGems() {
		 List<GemopediaItem> gemList=new ArrayList<>();
		 gemList=repository.getAllGems();		
		 return gemList;
	}

	public List<String> getVariety() {
		List<String> varietyList = new ArrayList<String>();
		varietyList=repository.getVariety();
		return varietyList;
		
	}
	
	@Override
	public List<String> getSpecies() {
		List<String> speciesList = new ArrayList<String>();
		speciesList=repository.getSpecies();
		return speciesList;
		
	}

}
