package com.vault.jtv.services;

import java.util.List;

import java.util.Map;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vault.jtv.model.GemopediaItem;

public interface GemService {

	
	GemopediaItem findById(int Id) throws Exception;

	Page<GemopediaItem> findAllByPage(Pageable pageable);

	GemopediaItem getGemDetails(String gemstone, String composition, String optical) throws Exception;

	GemopediaItem getGemById(Integer showcaseitemId);


	public Integer getGemIDByCommonName(String gpCommonName);

	public List<Map<String, Object>> getGemItems();

	public List<GemopediaItem> getAllGems();

	public List<String> getVariety();

	public List<String> getSpecies();


}
