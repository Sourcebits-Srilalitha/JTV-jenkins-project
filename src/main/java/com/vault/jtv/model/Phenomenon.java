
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
@Table(name = "phenomenon")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phenomenon implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="phen_Id_seq", sequenceName="phen_Id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="phenomenon")
	private String phenomenon;
	
	@Size(max=1)
	private String active;

	public Phenomenon(){
		
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

	
	public String getPhenomenon() {
		return phenomenon;
	}

	public void setPhenomenon(String phenomenon) {
		this.phenomenon = phenomenon;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public static void setNextId(Integer nextId) {
		Phenomenon.nextId = nextId;
	}


	
	public Phenomenon(String phenomenon, String active) {
		super();
		this.phenomenon = phenomenon;
		this.active = active;
	}

	
	
}
