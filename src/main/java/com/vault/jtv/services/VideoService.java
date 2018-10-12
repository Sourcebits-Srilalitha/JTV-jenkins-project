package com.vault.jtv.services;

import java.util.List;

import com.vault.jtv.model.Video;

public interface VideoService {

	List<Video> getAllVideos();

	//void addVideos();

	Video getVideoById(Integer id);

	List<Video> getAllActiveVideos();

	Video saveVideo(Video video);
	
	Video getVideoByName(String name, Integer type);
	
	public int updateAsInactive(List<Integer> idList, String inactive);
	
}
