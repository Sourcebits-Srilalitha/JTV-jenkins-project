package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "gemopedia_slider_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaSliderItems implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="gemopedia_slider_Id_seq", sequenceName="gemopedia_slider_Id_seq", allocationSize=1)
	private Integer id;	
	
	@Column(name="gemopedia_id")
	private Integer gemopediaId;
	
	private Integer page_index;
		
	@Size(max=1)
	private String active;
	
	private Date created_on;
	private Date modified_on;
	
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	
	public GemopediaSliderItems() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGemopediaId() {
		return gemopediaId;
	}

	public void setGemopediaId(Integer gemopediaId) {
		this.gemopediaId = gemopediaId;
	}

	public Integer getPage_index() {
		return page_index;
	}

	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
}
