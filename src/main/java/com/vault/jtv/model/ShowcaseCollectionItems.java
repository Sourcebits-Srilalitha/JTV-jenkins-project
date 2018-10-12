package com.vault.jtv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "showcase_collection_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowcaseCollectionItems implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="showcaseCollectionItems_Id_seq", sequenceName="showcaseCollectionItems_Id_seq", allocationSize=1)
	private Integer id;	
	
	@Column(name="collection_name")
	private String collection;
	
	@Column(name="COLLECTION_ID")
	private Integer collectionId;
	
	private String title;	
	@Column(name="description_short")
	private String descriptionShort;
	
	@Column(name="GP_COMM_NAME")
	private String gpCommName;
	@Column(name="gp_nature")
	private String gpNature; 
	
	//This should be added to get gem details
	@Column(name="optical_property")
	private String opticalProperty;
	
	private String subtitle;
	
	@Column(name="image_url")
	private String imageName;
	
	@Size(max=1)
	private String visible;
	
	@Size(max=1)
	private String deleted;
	
	private Integer page_index;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
			
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public Integer getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescriptionShort() {
		return descriptionShort;
	}
	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
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
	
	public String getGpCommName() {
		return gpCommName;
	}
	public void setGpCommName(String gpCommName) {
		this.gpCommName = gpCommName;
	}
	public String getGpNature() {
		return gpNature;
	}
	public void setGpNature(String gpNature) {
		this.gpNature = gpNature;
	}
	public String getOpticalProperty() {
		return opticalProperty;
	}
	public void setOpticalProperty(String opticalProperty) {
		this.opticalProperty = opticalProperty;
	}	
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	
	public ShowcaseCollectionItems() {}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Integer getPage_index() {
		return page_index;
	}
	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}	
	
}
