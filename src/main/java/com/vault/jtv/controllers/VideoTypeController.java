package com.vault.jtv.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.model.VideoTypes;
import com.vault.jtv.services.VideoTypeService;
import com.vault.util.Constants;

@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class VideoTypeController {

	
	private final VideoTypeService videoTypeService;
	
	
	@Inject
	public VideoTypeController(VideoTypeService videoTypeService ) {
		this.videoTypeService = videoTypeService;		
	}
	
	

	/**	Get all the active videotype details
	 * @param videoId
	 * @return list of  VideoTypes
	 */
	@RequestMapping(value = Constants.REQMAP_GET_VIDEOTYPES, method= RequestMethod.GET)
	public List<VideoTypes> getAllActiveVideoTypes(){
		return videoTypeService.getAllActiveVideoTypes();
	}

	
	
	
}
