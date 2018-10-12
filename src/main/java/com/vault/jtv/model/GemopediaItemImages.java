package com.vault.jtv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "GEMOPEDIA_ITEM_IMAGES")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaItemImages implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name="gem_item_id_seq", sequenceName="gem_item_id_seq", allocationSize=1)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	
	@Column(name="gemopedia_id")
	private Integer gemopedia_id; 
	
	private String image_link;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGemopediaId() {
		return gemopedia_id;
	}
	public void setGemopediaId(Integer itemId) {
		this.gemopedia_id = itemId;
	}
	public String getImage_link() {
		return image_link;
	}
	public void setImage_link(String image_link) {
		this.image_link = image_link;
	}
	
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public GemopediaItemImages(){
		
	}

}
