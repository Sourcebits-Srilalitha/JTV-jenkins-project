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
@Table(name = "archive_questionnaire")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveQuestionnaire implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="ques_Id_seq", sequenceName="ques_Id_seq", allocationSize=1)
	private Integer id;
	
	private String name;
	private String description;
	private String reason;
	private Date created_on;
	private Date modified_on;
	
	
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public ArchiveQuestionnaire(){
		
	}
	/* ******** Add full constructor here**********	 */
	public ArchiveQuestionnaire(String name, String description) {
		this.name = name;
		this.description = description;
		
	}
	public ArchiveQuestionnaire(String name, String description, String reason, Date created_on, Date modified_on) {
		super();
		this.name = name;
		this.description = description;
		this.reason = reason;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}
	
	

	

}
