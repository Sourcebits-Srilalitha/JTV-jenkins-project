
package com.vault.jtv.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "collections")// this will define the naming of the endpoints and the parameters for the default CRUD functions exposed by spring boot
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Collection implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="collection_Id_seq", sequenceName="collection_Id_seq", allocationSize=1)
	private Integer id;
	
	private String name;
	@Column(length=2000)
	private String description;
	/**
	 * Collection type : 1- master
	 * 	`				 2 - showcase
	 * 					 3 - custom client collection
	 ***/
	
	@Column(name="COLLECTION_TYPE")
	private Integer collectionType; 

	@Column(name="enterprise_user_id")/*this forces the column name to be 'user_id" which is the default naming of the paramters when spring boot is creating the endpoints and parameters */
	private Integer userId;
	@Size(max=1)
	private String active;
	private Date created_on;
	private Date modified_on;
	@Column(name="image_url")
	private String imageUrl;
	
	private Integer page_index;
	
	//custom collection
	private String customType;
	private String imageName;
	private String public_id;

	
	@OneToMany(mappedBy="userId", targetEntity=CollectionItems.class) //mapping item with user id
	private List<CollectionItems> collectionItems;
	
	@OneToMany(mappedBy="collectionId", targetEntity = ShowcaseCollectionItems.class)
	private List<ShowcaseCollectionItems> showcaseCollectionItems;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public Integer getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(Integer collectionType) {
		this.collectionType = collectionType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
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
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public Collection(){
		
	}
	/* ******** Add full constructor here**********	 */
	public Collection(String name, String description, Integer collectionType, Integer user_Id, String active,
			Date created_on, Date modified_on) {
		this.name = name;
		this.description = description;
		this.collectionType = collectionType;
		this.userId = user_Id;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;		
	}
	
	
	public Collection(String name, String description, Integer collectionType, Integer user_Id, String active,
			Date created_on, Date modified_on, String customType ,String imageName,String imageUrl) {
		this.name = name;
		this.description = description;
		this.collectionType = collectionType;
		this.userId = user_Id;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
		this.customType = customType;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
	}
	
	public Collection(String name, String description, Integer collectionType, Integer userId, String active,
			Date created_on, Date modified_on, String imageUrl, Integer page_index, String customType,String imageName,
			List<CollectionItems> collectionItems, List<ShowcaseCollectionItems> showcaseCollectionItems) {
		super();
		this.name = name;
		this.description = description;
		this.collectionType = collectionType;
		this.userId = userId;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
		this.imageUrl = imageUrl;
		this.page_index = page_index;
		this.customType = customType;
		this.imageName = imageName;
		this.collectionItems = collectionItems;
		this.showcaseCollectionItems = showcaseCollectionItems;
	}
	
	public List<CollectionItems> getCollectionItems() {
		return collectionItems;
	}
	public void setCollectionItems(List<CollectionItems> collectionItems) {
		this.collectionItems = collectionItems;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public List<ShowcaseCollectionItems> getShowcaseCollectionItems() {
		return showcaseCollectionItems;
	}
	public void setShowcaseCollectionItems(
			List<ShowcaseCollectionItems> showcaseCollectionItems) {
		this.showcaseCollectionItems = showcaseCollectionItems;
	}	
	public Integer getPage_index() {
		return page_index;
	}
	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}
	public String getCustomType() {
		return customType;
	}
	public void setCustomType(String customType) {
		this.customType = customType;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getPublic_id() {
		return public_id;
	}
	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

}
