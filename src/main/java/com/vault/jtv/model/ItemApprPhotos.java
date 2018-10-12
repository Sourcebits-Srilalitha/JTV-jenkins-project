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
import com.vault.jtv.repository.ItemApprPhotosRep;


@Entity
@Table(name = "item_appr_photos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemApprPhotos implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="item_appr_photo_seq", sequenceName="item_appr_photo_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="collection_item_id")
	private Integer collectionItemId; 
	
	private String image_link;
	
	private String image_name;
	
	@Size(max=1)
	private String active;
	
	private String public_id;
	
	private String secure_url;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage_link() {
		return image_link;
	}
	public void setImage_link(String image_link) {
		this.image_link = image_link;
	}
	
	public Integer getCollectionItemId() {
		return collectionItemId;
	}
	public void setCollectionItemId(Integer collectionItemId) {
		this.collectionItemId = collectionItemId;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public ItemApprPhotos(){
		
	}
	
	public ItemApprPhotos(Integer collectionItemId, String image_link, String image_name, String active) {
		super();
		this.collectionItemId = collectionItemId;
		this.image_link = image_link;
		this.image_name = image_name;
		this.active = active;
	}
	public String getPublic_id() {
		return public_id;
	}
	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}
	public String getSecure_url() {
		return secure_url;
	}
	public void setSecure_url(String secure_url) {
		this.secure_url = secure_url;
	}
	
	public ItemApprPhotos(Integer collectionItemId, String image_link, String image_name, String active,
			String public_id, String secure_url) {
		super();
		this.collectionItemId = collectionItemId;
		this.image_link = image_link;
		this.image_name = image_name;
		this.active = active;
		this.public_id = public_id;
		this.secure_url = secure_url;
	}
	
	

}
