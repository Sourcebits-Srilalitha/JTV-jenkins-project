package com.vault.jtv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "gemopedia_product_lookup")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaProductLookup implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="id", unique=true,nullable=false)
	private Integer id;	
	@Column(name="gemopedia_id")
	private Integer itemId;	
	private String gemstone;
	private String composition;
	private String opticalProperty;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getGemstone() {
		return gemstone;
	}
	public void setGemstone(String gemstone) {
		this.gemstone = gemstone;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public String getOpticalProperty() {
		return opticalProperty;
	}
	public void setOpticalProperty(String opticalProperty) {
		this.opticalProperty = opticalProperty;
	}
	
	
	public GemopediaProductLookup(){
		
	}
	
	public GemopediaProductLookup(Integer id, Integer itemId, String gemstone, String composition,
			String opticalProperty) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.gemstone = gemstone;
		this.composition = composition;
		this.opticalProperty = opticalProperty;
	}
	

}
