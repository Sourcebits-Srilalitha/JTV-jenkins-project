package com.vault.jtv.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "collection_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class CollectionItems implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="collectionItems_Id_seq", sequenceName="collectionItems_Id_seq", allocationSize=1)
	private Integer id;	
	@Column(name="ITEM_NAME")
	private String itemName;
	@Column(name="COMMON_NAME")
	private String commonName;  // phase2: gemstone
	/*@Column(name="COLLECTION_ID")
	private Integer collectionId;*/	
	private String variety; 
	private String species; 
	private String sku;
	@Column(name="OPTICAL_PROPERTY")
	private String opticalProperty;	
	@Column(length=5000)
	private String description;	// phase2: summary
	@Column(name="DATE_OF_PURCHASE")
	@Type(type="timestamp")
	private Date dateOfPurchase;
	@Column(name="PURCHASE_PRICE")
	private BigDecimal purchasePrice; 
	private String brand; 
	private String color; 
	private String cut; 
	private String shape;
	//@Column(name="product_size")
	//private String size; 
	private String dimensions; // phase2: this is taken as length*width*height
	private String ctw;
	@Column(name="COUNTRY_OF_ORIGIN")
	private String countryOfOrigin; 
	private String composition; 
	private String treatment; 
	private String phenomenon;
	@Column(name="PRODUCT_IMAGE_URL")
	private String productImageUrl;
	@Column(name="PRODUCT_IMAGE_COUNT")
	private Integer productImageCount;
	@Size(max=1)
	private String visible;
	@Size(max=1)
	private String deleted;	
	@Type(type="timestamp")	
	private Date created_on;	
	@Type(type="timestamp")
	private Date modified_on;
	private String classification; 
	@Column(name="fluorescence_strength")
	private String fluorescenceStrength ; 	
	private boolean calibrated=false;	
	@Column(name="acquisition_type")
	private String acquisitionType;		
	private String vendor;	
	@Column(name="price_per_carat")
	private BigDecimal pricePerCarat; 
	@Column(length=5000)
	private String notes;	
	@Column(name="is_third_party")
	private boolean isThirdParty=false; 
	
	@Column(name="user_id")
	private Integer userId;
	
	
	@OneToMany(mappedBy="collectionItemId", targetEntity=CollectionItemImages.class)
	private List<CollectionItemImages> collectionItemImages;
	
	@OneToMany(mappedBy="collectionItemId", targetEntity=ItemApprDocs.class)
	private List<ItemApprDocs> itemApprDocs;
	
	@OneToMany(mappedBy="collectionItemId", targetEntity=ItemApprPhotos.class)
	private List<ItemApprPhotos> itemApprPhotos;
	
	@OneToMany(mappedBy="collectionItemsId", targetEntity=CollectionItemsLookup.class)
	private List<CollectionItemsLookup> collections;
	
	
	@Transient
	List<Integer> delItemImg=new ArrayList<Integer>();
	
	@Transient
	List<Integer> delAppDoc=new ArrayList<Integer>();
	
	@Transient
	List<Integer> delAppPhoto=new ArrayList<Integer>();
	
		
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}	
	


	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	public String getVariety() {
		return variety;
	}
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}	
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCut() {
		return cut;
	}
	public void setCut(String cut) {
		this.cut = cut;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	/*public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}*/
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	
	public String getCtw() {
		return ctw;
	}
	public void setCtw(String ctw) {
		this.ctw = ctw;
	}
	
	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}
	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(String phenomenon) {
		this.phenomenon = phenomenon;
	}	
	public String getOpticalProperty() {
		return opticalProperty;
	}
	public void setOpticalProperty(String opticalProperty) {
		this.opticalProperty = opticalProperty;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	public Integer getProductImageCount() {
		return productImageCount;
	}
	
	public void setProductImageCount(Integer productImageCount) {
		this.productImageCount = productImageCount;
	}
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	
	public CollectionItems() {
		
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<CollectionItemImages> getCollectionItemImages() {
		return collectionItemImages;
	}
	public void setCollectionItemImages(List<CollectionItemImages> collectionItemImages) {
		this.collectionItemImages = collectionItemImages;
	}

	public String getClassification() {
		return classification;
	}


	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getFluorescenceStrength() {
		return fluorescenceStrength;
	}
	public void setFluorescenceStrength(String fluorescenceStrength) {
		this.fluorescenceStrength = fluorescenceStrength;
	}
	
	public String getAcquisitionType() {
		return acquisitionType;
	}
	public void setAcquisitionType(String acquisitionType) {
		this.acquisitionType = acquisitionType;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public BigDecimal getPricePerCarat() {
		return pricePerCarat;
	}
	public void setPricePerCarat(BigDecimal pricePerCarat) {
		this.pricePerCarat = pricePerCarat;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public List<ItemApprDocs> getItemApprDocs() {
		return itemApprDocs;
	}
	public void setItemApprDocs(List<ItemApprDocs> itemApprDocs) {
		this.itemApprDocs = itemApprDocs;
	}
	public List<ItemApprPhotos> getItemApprPhotos() {
		return itemApprPhotos;
	}
	public void setItemApprPhotos(List<ItemApprPhotos> itemApprPhotos) {
		this.itemApprPhotos = itemApprPhotos;
	}
	public boolean isCalibrated() {
		return calibrated;
	}
	public void setCalibrated(boolean calibrated) {
		this.calibrated = calibrated;
	}
	public boolean isThirdParty() {
		return isThirdParty;
	}
	public void setThirdParty(boolean isThirdParty) {
		this.isThirdParty = isThirdParty;
	}


	public List<CollectionItemsLookup> getCollections() {
		return collections;
	}
	public void setCollections(List<CollectionItemsLookup> collections) {
		this.collections = collections;
	}
	public List<Integer> getDelItemImg() {
		return delItemImg;
	}
	public void setDelItemImg(List<Integer> delItemImg) {
		this.delItemImg = delItemImg;
	}
	public List<Integer> getDelAppDoc() {
		return delAppDoc;
	}
	public void setDelAppDoc(List<Integer> delAppDoc) {
		this.delAppDoc = delAppDoc;
	}
	public List<Integer> getDelAppPhoto() {
		return delAppPhoto;
	}
	public void setDelAppPhoto(List<Integer> delAppPhoto) {
		this.delAppPhoto = delAppPhoto;
	}
	
	

}