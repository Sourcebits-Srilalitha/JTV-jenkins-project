package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "gemopedia_item")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name="gem_Id_seq", sequenceName="gem_Id_seq", allocationSize=1)	
	@Column(name="id", unique=true,nullable=false)
	private Integer id;	

	private String commonName;
	@Column(name="TYPE")
	private String gemType;
	private String categoryPath;
	private String categoryName;
	private String colors;
	private String container;	
	private String organization;
	private String parentid;
	private String externalId;	
	private String species;
	private String variety;
	@Column(length=4000)
	private String altName;
	private String gemstoneGroup;	
	@Column(length=4000)
	private String keySeparations; 
	@Column(length =4000)
	private String comments;
	private Date created_on;
	private Date modified_on;
	
	//place holder for Entity Metadata//Marketing Description
	@Column(length =5000)
	private String description;		

	/*@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(commonname = "id", referencedColumnName = "item_id")
    private GemopediaItemClassification gemopediaItemClassification; */
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gemopedia_id", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
    @Column(nullable= false)
    private List<GemopediaItemImages> gemopediaItemImages;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemId", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
    @Column(nullable= false)
    private List<GemopediaCrystallography> gemopediaCrystallography;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemId", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
    @Column(nullable= false)
    private List<GemopediaItemOptical> gemopediaItemOptical;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "itemId", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
    @Column(nullable= false)
    private List<GemopediaItemPhysics> gemopediaItemPhysics;
	
	
	/*@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "id",referencedColumnName = "gemopedia_id")
	private GemopediaItemImages gemopediaItemImages;*/
	
	/*
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "id")
	private GemopediaCrystallography gemopediaCrystallography;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "id")
	private GemopediaItemOptical gemopediaItemOptical;
	

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "id")
	private GemopediaItemPhysics gemopediaItemPhysics;
*/

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCommonName() {
		return commonName;
	}


	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}


	public String getGemType() {
		return gemType;
	}


	public void setGemType(String gemType) {
		this.gemType = gemType;
	}


	public String getCategoryPath() {
		return categoryPath;
	}


	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getColors() {
		return colors;
	}


	public void setColors(String colors) {
		this.colors = colors;
	}


	public String getContainer() {
		return container;
	}


	public void setContainer(String container) {
		this.container = container;
	}


	public String getOrganization() {
		return organization;
	}


	public void setOrganization(String organization) {
		this.organization = organization;
	}


	public String getParentid() {
		return parentid;
	}


	public void setParentid(String parentId) {
		this.parentid = parentId;
	}


	public String getExternalId() {
		return externalId;
	}


	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}


	public String getSpecies() {
		return species;
	}


	public void setSpecies(String species) {
		this.species = species;
	}


	public String getVariety() {
		return variety;
	}


	public void setVariety(String variety) {
		this.variety = variety;
	}


	public String getAltName() {
		return altName;
	}


	public void setAltName(String altName) {
		this.altName = altName;
	}


	public String getGemstoneGroup() {
		return gemstoneGroup;
	}


	public void setGemstoneGroup(String gemstoneGroup) {
		this.gemstoneGroup = gemstoneGroup;
	}


	public String getKeySeparations() {
		return keySeparations;
	}


	public void setKeySeparations(String keySeparations) {
		this.keySeparations = keySeparations;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Date getCreated_on() {
		return created_on;
	}


	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}


	public Date getModified_on() {
		return modified_on;
	}


	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<GemopediaItemImages> getGemopediaItemImages() {
		return gemopediaItemImages;
	}


	public void setGemopediaItemImages(List<GemopediaItemImages> gemopediaItemImages) {
		this.gemopediaItemImages = gemopediaItemImages;
	}


	


	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	
	public GemopediaItem(){
		
	}

	


	public GemopediaItem(Integer id, String commonName, String gemType,
			String categoryPath, String categoryName, String colors,
			String container, String organization, String parentid,
			String externalId, String species, String variety, String altName,
			String gemstoneGroup, String keySeparations, String comments,
			Date created_on, Date modified_on, String description,
			List<GemopediaItemImages> gemopediaItemImages,
			List<GemopediaCrystallography> gemopediaCrystallography,
			List<GemopediaItemOptical> gemopediaItemOptical,
			List<GemopediaItemPhysics> gemopediaItemPhysics) {
		super();
		this.id = id;
		this.commonName = commonName;
		this.gemType = gemType;
		this.categoryPath = categoryPath;
		this.categoryName = categoryName;
		this.colors = colors;
		this.container = container;
		this.organization = organization;
		this.parentid = parentid;
		this.externalId = externalId;
		this.species = species;
		this.variety = variety;
		this.altName = altName;
		this.gemstoneGroup = gemstoneGroup;
		this.keySeparations = keySeparations;
		this.comments = comments;
		this.created_on = created_on;
		this.modified_on = modified_on;
		this.description = description;
		this.gemopediaItemImages = gemopediaItemImages;
		this.gemopediaCrystallography = gemopediaCrystallography;
		this.gemopediaItemOptical = gemopediaItemOptical;
		this.gemopediaItemPhysics = gemopediaItemPhysics;
	}


	public List<GemopediaCrystallography> getGemopediaCrystallography() {
		return gemopediaCrystallography;
	}


	public void setGemopediaCrystallography(
			List<GemopediaCrystallography> gemopediaCrystallography) {
		this.gemopediaCrystallography = gemopediaCrystallography;
	}


	public List<GemopediaItemOptical> getGemopediaItemOptical() {
		return gemopediaItemOptical;
	}


	public void setGemopediaItemOptical(
			List<GemopediaItemOptical> gemopediaItemOptical) {
		this.gemopediaItemOptical = gemopediaItemOptical;
	}


	public List<GemopediaItemPhysics> getGemopediaItemPhysics() {
		return gemopediaItemPhysics;
	}


	public void setGemopediaItemPhysics(
			List<GemopediaItemPhysics> gemopediaItemPhysics) {
		this.gemopediaItemPhysics = gemopediaItemPhysics;
	}
}