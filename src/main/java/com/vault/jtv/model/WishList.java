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
@Table(name = "wishlist")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishList implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="wish_Id_seq", sequenceName="wish_Id_seq", allocationSize=1)
	private Integer id;
	@Column(name="gemopedia_id")
	private Integer gemopediaId;
	
	@Column(name="user_id")
	private Integer userId;
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
	public Integer getGemopediaId() {
		return gemopediaId;
	}
	public void setGemopediaId(Integer gemopedia_Id) {
		this.gemopediaId = gemopedia_Id;
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
	public WishList() {
		
	}

	public WishList(Integer gemopedia_Id, Integer userId, String active, Date created_on , Date modified_on) {
		super();
		this.gemopediaId = gemopedia_Id;
		this.userId = userId;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}
}