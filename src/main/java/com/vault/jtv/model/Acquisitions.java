
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
@Table(name = "acquisitions")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Acquisitions implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="acq_Id_seq", sequenceName="acq_Id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="acquisition")
	private String acquisition;
	
	@Size(max=1)
	private String active;

	public Acquisitions(){
		
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


	public String getAcquisition() {
		return acquisition;
	}

	public void setAcquisition(String acquisition) {
		this.acquisition = acquisition;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static void setNextId(Integer nextId) {
		Acquisitions.nextId = nextId;
	}


	
	public Acquisitions(String acquisition, String active) {
		super();
		this.acquisition = acquisition;
		this.active = active;
	}

	
	
}
