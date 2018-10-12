package com.vault.jtv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.GemopediaSliderItems;
import com.vault.jtv.repository.GemopediaSliderRep;
import com.vault.util.MessageConstants;


@Service
public class GemopediaSliderServiceImp implements GemopediaSliderService{
	
	@Autowired
	private GemopediaSliderRep gemopediaSliderRep;
	


	@Override
	public GemopediaSliderItems save(GemopediaSliderItems gemItems) {
		return gemopediaSliderRep.save(gemItems);
	}

	@Override
	public GemopediaSliderItems findByGemID(int gemopediaId) {
		return gemopediaSliderRep.getByGemID(gemopediaId, MessageConstants.IS_ACTIVE);
	}

	@Override
	public int updateAsInactive(List<Integer> gemIdList, String inactive) {
		int updated = 0;
		try {
			updated = gemopediaSliderRep.updateAsInactive(gemIdList, inactive);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	
	}

	
	
	

	

}
