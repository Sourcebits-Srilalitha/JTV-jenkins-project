package com.vault.jtv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.Video;
import com.vault.jtv.repository.VideoRep;
import com.vault.jtv.repository.VideoTypeRep;
import com.vault.util.MessageConstants;


@Service
public class VideoServiceImp implements VideoService {

	@Autowired
	VideoRep  videoRep;
	
	@Autowired
	VideoTypeRep videoTypeRep;
	
	public VideoServiceImp(){
		
	}
	
	@Override
	public List<Video> getAllVideos() {
		List<Video> videoList = videoRep.findAll(new Sort(Sort.Direction.ASC,MessageConstants.TYPE));
		return videoList;
	}
	
	@Override
	public Video getVideoById(Integer id){
		return videoRep.findById(id);
	}
	
	@Override
	public List<Video> getAllActiveVideos(){
		String isAct = MessageConstants.IS_ACTIVE;
		List<Video> videoList = videoRep.findAllActiveVideos(isAct);		
        return videoList;
	}

	@Override
	public Video saveVideo(Video video) {
		Video vid;
		if(video != null)
		{
			 vid = videoRep.save(video);
		}
		else{
			vid = new Video();
		}
		return vid;
	}
	
	@Override
	public Video getVideoByName(String name, Integer type) {
		Video video = null;
		try {
			video = videoRep.findByName(name, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return video;
	}
	
	@Override
	public int updateAsInactive(List<Integer> idList, String inAct) {
		int updated = 0;
		try {
			updated = videoRep.updateAsInactive(idList, inAct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

}
