package com.vault.jtv.services;

import java.util.List;

import com.vault.jtv.model.Acquisitions;
import com.vault.jtv.model.Classification;
import com.vault.jtv.model.Colors;
import com.vault.jtv.model.Cuts;
import com.vault.jtv.model.Phenomenon;
import com.vault.jtv.model.Shapes;
import com.vault.jtv.model.Treatment;

public interface UtilityService {	

	public List<Colors> getColors();
	public List<Shapes> getShapes();
	public List<Cuts> getCuts();
	public List<Classification> getClassifications();
	public List<Treatment> getTreatments();
	public List<Phenomenon> getPhenomenons();
	public List<Acquisitions> getAcquisitions();

}