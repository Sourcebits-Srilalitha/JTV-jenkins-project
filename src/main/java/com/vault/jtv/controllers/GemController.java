package com.vault.jtv.controllers;


import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.services.GemNotFoundException;
import com.vault.jtv.services.GemService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;

@RequestMapping(value=Constants.REQMAP_API)
@RestController
public class GemController {
	
	
	protected Logger logger = Logger.getLogger(GemController.class.getName());
	
	private final GemService gemService;
	
		
	@Inject
	public GemController(GemService gemService) {
		this.gemService = gemService;
	}
	
	/**
	 * This api is used to get the gemdetails of a specific gem
	 * @param Id
	 * @return GemopediaItem with all respective child table details
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_GET_GEMDETAILS, method= RequestMethod.GET)
	public GemopediaItem getById(@PathVariable(MessageConstants.ITEM_ID) int Id) throws Exception{	
		return gemService.findById(Id);		
	}
	
	/**
	 * This api is for getting all the gem's details
	 * Can use size, page and sort properties 
	 * @param pageable
	 * @return pageable GemopediaItem 
	 */
	@RequestMapping(value = Constants.REQMAP_GET_ALLGEMDETAILS, method= RequestMethod.GET)
	public Page<GemopediaItem> gemCollection(Pageable pageable){	
		Page<GemopediaItem> gemopediaitems =  gemService.findAllByPage(pageable);
		return gemopediaitems;
	}
	

	/**
	 * This api is called from collection showcase to get the gem details of particular showcase item using the lookup table 
	 * @param showcaseitemId
	 * @return GemopediaItem with all respective details
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_GET_SHOWCASE_GEM_ITEM, method= RequestMethod.GET)
	public GemopediaItem getGemById(@PathVariable(MessageConstants.SHOWCASE_ITEMID) Integer showcaseitemId) throws Exception{	
		return  gemService.getGemById(showcaseitemId);		
	}
		
	@ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleTodoNotFound(GemNotFoundException ex) {
		logger.info(MessageConstants.ERROR_HANDLING+ ex.getMessage());
    }
	
	

	/** Gemopedia Slider
	 * To get all the gemopedia items details for the discover screen.
	 * which are coming from wordpress
	 * @return
	 */
	@RequestMapping(value = Constants.REQMAP_GET_GEMDETAILS_SLIDER, method= RequestMethod.GET)
	public List<Map<String, Object>> getGemItems(){			
			return gemService.getGemItems();
	}	
	
	/**
	 * This api is for getting all the gem's details in a sorted on common name	 * 	 *
	 * @return List of GemopediaItems 
	 */
	@RequestMapping(value = Constants.REQMAP_GET_ALLGEMS, method= RequestMethod.GET)
	public List<GemopediaItem> gemAllGems(){	
		List<GemopediaItem> gemopediaitems =  gemService.getAllGems();
		return gemopediaitems;
	}
	

	//Phase2
	
	/**
	 * This api to get the list of variety
	 * @return List of variety
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_GET_VARIETY, method= RequestMethod.GET)
	public List<String> getVariety() throws Exception{	
		return  gemService.getVariety();		
	}
	
	
	/**
	 * This api to get the list of variety
	 * @return List of variety
	 * @throws Exception
	 */
	@RequestMapping(value = Constants.REQMAP_GET_SPECIES, method= RequestMethod.GET)
	public List<String> getSpecies() throws Exception{	
		return  gemService.getSpecies();		
	}

}
