
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
@Table(name = "treatments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Treatment implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="treat_Id_seq", sequenceName="treat_Id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="treatment")
	private String treatment;
	
	@Size(max=1)
	private String active;

	public Treatment(){
		
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

	
	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static void setNextId(Integer nextId) {
		Treatment.nextId = nextId;
	}


	
	public Treatment(String treatment, String active) {
		super();
		this.treatment = treatment;
		this.active = active;
	}

	
	
}
