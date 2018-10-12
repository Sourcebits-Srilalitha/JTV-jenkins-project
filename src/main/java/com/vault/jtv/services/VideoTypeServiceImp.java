package com.vault.jtv.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.VideoTypes;
import com.vault.jtv.repository.VideoTypeRep;
import com.vault.util.MessageConstants;



@Service
public class VideoTypeServiceImp implements VideoTypeService {

	@Autowired
	VideoTypeRep videoTypeRep;
	
		
	@Override
	public VideoTypes getVideoTypeById(int videoId) {		
		return videoTypeRep.findById(videoId);
	}


	@Override
	public List<VideoTypes> getAllActiveVideoTypes() {	
		String isAct = MessageConstants.IS_ACTIVE;
		List<VideoTypes> videosList=null;
		Set<VideoTypes> videosetList=videoTypeRep.findAllActiveVideoTypes(isAct);
		if(null != videosetList && videosetList.size() >0){
			videosList=new ArrayList<>(videosetList);
		}
		if(videosList != null && videosList.size() != 0){
			Iterator<VideoTypes> itr = videosList.iterator();
			while(itr.hasNext()){
				VideoTypes vt = (VideoTypes) itr.next();
				if(null !=vt.getVideos() && vt.getVideos().size() ==0){
					itr.remove();
				}
			}
		} else {
			videosList = new ArrayList<>();
		}
		return videosList;
	}
	


	@Override
	public List<VideoTypes> getAllVideoTypes() {		
		List<VideoTypes> videosList= videoTypeRep.findAll();
		if(videosList != null && videosList.size() != 0){
			Iterator<VideoTypes> itr = videosList.iterator();
			while(itr.hasNext()){
				VideoTypes vt = (VideoTypes) itr.next();
				if(null !=vt.getVideos() && vt.getVideos().size() ==0){
					itr.remove();
				}
			}
		} else {
			videosList = new ArrayList<>();
		}
		return videosList;
	}
	
	@Override
	public VideoTypes getByName(String name, String isActive) {
		VideoTypes savedType = null;
		String isAct = MessageConstants.IS_ACTIVE;
		try {
			savedType = videoTypeRep.findByName(name, isAct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedType;
	}
	
	@Override
	public VideoTypes save(VideoTypes videoTypes) {
		VideoTypes savedType = null;
		try {
			savedType = videoTypeRep.save(videoTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedType;
	}
	
	@Override
	public int updateAsInactive(List<Integer> idList, String inAct) {
		int updated = 0;
		try {
			updated = videoTypeRep.updateAsInactive(idList, inAct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

}
