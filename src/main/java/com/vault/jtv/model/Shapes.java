
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
@Table(name = "shapes")// this will define the naming of the endpoints and the parameters for the default CRUD functions exposed by spring boot
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shapes implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="shape_Id_seq", sequenceName="shape_Id_seq", allocationSize=1)
	private Integer id;
	
	private String name;
	
	@Size(max=1)
	private String active;
	
	@Column(name="image_url")
	private String imageUrl;
	
	
	public Shapes(){
		
	}
	
	
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public static void setNextId(Integer nextId) {
		Shapes.nextId = nextId;
	}
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}


	public Shapes(Integer id, String name, String active, String imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.imageUrl = imageUrl;
	}
	
	

}
