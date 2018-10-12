package com.vault.jtv.services;

import java.util.List;

import com.vault.jtv.model.VideoTypes;



public interface VideoTypeService {

	public VideoTypes getVideoTypeById(int videoId) ;

	public List<VideoTypes> getAllActiveVideoTypes();

	public List<VideoTypes> getAllVideoTypes();
	
	public VideoTypes getByName(String name, String isActive);
	
	public VideoTypes save(VideoTypes videoTypes);
	
	public int updateAsInactive(List<Integer> idList, String inactive);
}
