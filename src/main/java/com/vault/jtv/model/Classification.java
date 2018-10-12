
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
@Table(name = "classifications")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Classification implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="classf_Id_seq", sequenceName="classf_Id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="classification")
	private String classification;
	
	@Size(max=1)
	private String active;

	public Classification(){
		
	}
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static void setNextId(Integer nextId) {
		Classification.nextId = nextId;
	}


	public Classification(String classification) {
		super();		
		this.classification = classification;		
	}


	
}
