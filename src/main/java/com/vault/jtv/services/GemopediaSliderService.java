package com.vault.jtv.services;

import java.util.List;

import com.vault.jtv.model.GemopediaSliderItems;

public interface GemopediaSliderService {	

	public GemopediaSliderItems save(GemopediaSliderItems gemItems);

	public GemopediaSliderItems findByGemID(int gemopediaId);

	public int updateAsInactive(List<Integer> gemIdList,String inactive);

	
	
	
	

}
