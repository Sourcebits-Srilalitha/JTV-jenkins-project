package com.vault.jtv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vault.jtv.model.Acquisitions;
import com.vault.jtv.model.Classification;
import com.vault.jtv.model.Colors;
import com.vault.jtv.model.Cuts;
import com.vault.jtv.model.Phenomenon;
import com.vault.jtv.model.Shapes;
import com.vault.jtv.model.Treatment;
import com.vault.jtv.repository.UtilityRep;
import com.vault.util.MessageConstants;

@Service
public class UtilityServiceImpl implements UtilityService{
	
	@Autowired
	private UtilityRep utilityRepository;
	
	
	

	@Override
	public List<Colors> getColors() {
		List<Colors> colorList = null;
		try{
			colorList=utilityRepository.getAllColors();			
		}catch(Exception e){
			e.printStackTrace();
		}
		return colorList;
	}


	@Override
	public List<Shapes> getShapes() {
		List<Shapes> shapeList = null;
		try{
			shapeList=utilityRepository.getAllShapes(MessageConstants.IS_ACTIVE);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return shapeList;
	}
	
	@Override
	public List<Cuts> getCuts() {
		List<Cuts> cutsList = null;
		try{
			cutsList=utilityRepository.getAllCuts(MessageConstants.IS_ACTIVE);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cutsList;
	}


	@Override
	public List<Classification> getClassifications() {
		List<Classification> classfList = null;
		try{
			classfList=utilityRepository.getClassifications(MessageConstants.IS_ACTIVE);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return classfList;
	}


	@Override
	public List<Treatment> getTreatments() {
		List<Treatment> treatmentList = null;
		try{
			treatmentList=utilityRepository.getTreatments(MessageConstants.IS_ACTIVE);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return treatmentList;
	}


	@Override
	public List<Phenomenon> getPhenomenons() {
		List<Phenomenon> phenomenonList = null;
		try{
			phenomenonList=utilityRepository.getPhenomenon(MessageConstants.IS_ACTIVE);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return phenomenonList;
	}	

	
	@Override
	public List<Acquisitions> getAcquisitions() {
		List<Acquisitions> acquisitionList = null;
		try{
			acquisitionList=utilityRepository.getAcquisitions(MessageConstants.IS_ACTIVE);			
		}catch(Exception e){
			e.printStackTrace();
		}
		return acquisitionList;
	}	
	

}