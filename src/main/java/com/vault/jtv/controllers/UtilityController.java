package com.vault.jtv.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.model.Acquisitions;
import com.vault.jtv.model.Classification;
import com.vault.jtv.model.Colors;
import com.vault.jtv.model.Cuts;
import com.vault.jtv.model.Phenomenon;
import com.vault.jtv.model.Shapes;
import com.vault.jtv.model.Treatment;
import com.vault.jtv.services.UtilityService;
import com.vault.util.Constants;

/**
 * Phase2
 * @author Srilalitha Jana
 *
 */

@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class UtilityController {

	@Autowired
	private UtilityService utilityService;	
	

	/** This api will get all colors 
	 * @return list of colors
	 */
	@RequestMapping(value = Constants.REQMAP_GET_COLORS, method = RequestMethod.GET)
	public List<Colors> colors(){		
		return utilityService.getColors();
	}
	
	
	/** This api will get all cuts 
	 * @return list of cuts
	 */
	@RequestMapping(value = Constants.REQMAP_GET_CUTS, method = RequestMethod.GET)
	public List<Cuts> cuts(){		
		return utilityService.getCuts();
	}
	
	
	/** This api will get all shapes 
	 * @return list of shapes
	 */
	@RequestMapping(value = Constants.REQMAP_GET_SHAPES, method = RequestMethod.GET)
	public List<Shapes> shapes(){		
		return utilityService.getShapes();
	}
	
	/** This api will get all classifications 
	 * @return list of classifications
	 */
	@RequestMapping(value = Constants.REQMAP_GET_CLASSIFICATIONS, method = RequestMethod.GET)
	public List<Classification> classifications(){		
		return utilityService.getClassifications();
	}
	
	
	/** This api will get all treatments 
	 * @return list of treatments
	 */
	@RequestMapping(value = Constants.REQMAP_GET_TREATMENTS, method = RequestMethod.GET)
	public List<Treatment> treatments(){		
		return utilityService.getTreatments();
	}
	
	
	/** This api will get all phenomenos 
	 * @return list of phenomenos
	 */
	@RequestMapping(value = Constants.REQMAP_GET_PHENOMENOS, method = RequestMethod.GET)
	public List<Phenomenon> phenomenos(){		
		return utilityService.getPhenomenons();
	}
	
	/** This api will get all Acquisitions 
	 * @return list of Acquisitions
	 */
	@RequestMapping(value = Constants.REQMAP_GET_ACQUISITIONS, method = RequestMethod.GET)
	public List<Acquisitions> acquisitions(){		
		return utilityService.getAcquisitions();
	}
	
	

	
}