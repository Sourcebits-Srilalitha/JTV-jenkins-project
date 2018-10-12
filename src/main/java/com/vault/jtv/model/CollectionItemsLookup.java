package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "collection_items_lookup")
public class CollectionItemsLookup implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="collectionItemsLookup_Id_seq", sequenceName="collectionItemsLookup_Id_seq", allocationSize=1)
	private Integer id;
	
	private Integer collectionId;
	private Integer collectionItemsId;
	private String collectionName;	
	@Size(max=1)
	private String active;
	private Date created_on;
	private Date modified_on;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}
	public Integer getCollectionItemsId() {
		return collectionItemsId;
	}
	public void setCollectionItemsId(Integer collectionItemsId) {
		this.collectionItemsId = collectionItemsId;
	}	
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
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
	
	public CollectionItemsLookup() {
		// Default Constructor
	}
	public CollectionItemsLookup(Integer collectionId,
			Integer collectionItemsId, String collectionName, String active,
			Date created_on) {
		super();
		this.collectionId = collectionId;
		this.collectionItemsId = collectionItemsId;
		this.collectionName = collectionName;
		this.active = active;
		this.created_on = created_on;
	}
	
	public Date getModified_on() {
		return modified_on;
	}
	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}	
	
}
