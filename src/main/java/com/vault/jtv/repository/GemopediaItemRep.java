package com.vault.jtv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.GemopediaItem;
import com.vault.util.QueryConstants;

@RepositoryRestResource(exported = false)
public interface GemopediaItemRep extends PagingAndSortingRepository<GemopediaItem, Integer>  {

	public GemopediaItem findById (Integer Id) ;
	List<GemopediaItem> findAll();
	
	@Query(QueryConstants.QRY_FIND_GEMDETAILS)	
	public Integer findGemDetails(@Param(QueryConstants.GEMSTONE) String gemstone, @Param(QueryConstants.COMPOSITION) String composition, @Param(QueryConstants.OPTICAL) String optical);

	@Query(QueryConstants.QRY_GET_GEM)
	public List<GemopediaItem> getGem(@Param(QueryConstants.GP_NATURE) String gpNature, @Param(QueryConstants.GP_COMMNAME) String gpCommName, @Param(QueryConstants.OPTICAL_PROPERTY) String opticalProperty);
	
	@Query(QueryConstants.QRY_GET_GEMFORSHOWCASE)
	public List<GemopediaItem> getGemForShowcase(@Param(QueryConstants.GP_COMMNAME) String gpCommName);
	

	@Query(QueryConstants.QRY_GET_GEMIDBYCOMMONNAME)
	public Integer getGemIDByCommonName(@Param(QueryConstants.GP_COMMNAME) String gpCommName);
	
	@Query(QueryConstants.QRY_GET_GEMDETAILS)
	public List<GemopediaItem> getGemDetails(@Param(QueryConstants.ID_LIST) List<Integer> idList);
	
	@Query(QueryConstants.QRY_GET_GEMITEM)
	public GemopediaItem getGemItem(@Param(QueryConstants.ID_) Integer id);
	
	@Query(QueryConstants.QRY_GET_ALLGEMS)
	public List<GemopediaItem> getAllGems();

	@Query(QueryConstants.QRY_GET_VARIETY)
	public List<String> getVariety();
	
	@Query(QueryConstants.QRY_GET_SPECIES)
	public List<String> getSpecies();
	
	@Query(QueryConstants.QRY_GET_ALLGEMSEARCH)
	public List<GemopediaItem> getAllGemSearch(@Param(QueryConstants.KEY_PREFIX) String keyPrefix,@Param(QueryConstants.KEY_SUFFIX) String keySuffix,@Param(QueryConstants.KEY_FIX) String keyfix);

}