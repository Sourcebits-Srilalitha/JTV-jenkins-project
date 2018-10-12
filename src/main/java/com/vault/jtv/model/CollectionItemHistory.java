package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "collection_item_history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionItemHistory implements Serializable{

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="collectionItemsHistory_Id_seq", sequenceName="collectionItemsHistory_Id_seq", allocationSize=1)
	private Integer id;
	
	private Integer userId;
	
	private Integer itemId;
	
	private Integer actionType;
	
	private String actionComment;
	
	private Date created_on;
	private Date modified_on;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public Integer getUserId() {
		return userId;
	}
	public void setUserid(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public String getActionComment() {
		return actionComment;
	}
	public void setActionComment(String actionComment) {
		this.actionComment = actionComment;
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
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	public CollectionItemHistory() {
		
	}
	
	
	public CollectionItemHistory(Integer userId, Integer itemId, Integer actionType, String actionComment,
			Date created_on, Date modified_on) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.actionType = actionType;
		this.actionComment = actionComment;
		this.created_on = created_on;
		this.modified_on = modified_on;

	}	
	

	public CollectionItemHistory(Integer userId, Integer itemId,
			Integer actionType, String actionComment, Date created_on) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.actionType = actionType;
		this.actionComment = actionComment;
		this.created_on = created_on;

	}
		
	
}