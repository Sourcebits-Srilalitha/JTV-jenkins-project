
package com.vault.jtv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

import com.vault.jtv.model.Acquisitions;
import com.vault.jtv.model.Classification;
import com.vault.jtv.model.Colors;
import com.vault.jtv.model.Cuts;
import com.vault.jtv.model.Phenomenon;
import com.vault.jtv.model.Shapes;
import com.vault.jtv.model.Treatment;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface UtilityRep extends JpaRepository<Colors, Integer> { 
	
	@Query(QueryConstants.QRY_GET_COLORS)
	public List<Colors> getAllColors();
	
	@Query(QueryConstants.QRY_GET_SHAPES)
	public List<Shapes> getAllShapes(@Param(QueryConstants.IS_ACT) String isAct);
	
	@Query(QueryConstants.QRY_GET_CUTS)
	public List<Cuts> getAllCuts(@Param(QueryConstants.IS_ACT) String isAct);

	@Query(QueryConstants.QRY_GET_CLASSIFICATION)
	public List<Classification> getClassifications(@Param(QueryConstants.IS_ACT) String isAct);

	@Query(QueryConstants.QRY_GET_TREATMENT)
	public List<Treatment> getTreatments(@Param(QueryConstants.IS_ACT) String isAct);

	@Query(QueryConstants.QRY_GET_PHENOMENON)
	public List<Phenomenon> getPhenomenon(@Param(QueryConstants.IS_ACT) String isAct);

	@Query(QueryConstants.QRY_GET_AQUISITIONS)
	public List<Acquisitions> getAcquisitions(@Param(QueryConstants.IS_ACT) String isAct);	

	
}
