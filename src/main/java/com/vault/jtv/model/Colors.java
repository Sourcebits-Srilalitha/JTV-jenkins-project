
package com.vault.jtv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "colors")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Colors implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="color_Id_seq", sequenceName="color_Id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="color_name")
	private String colorName;
	private String hexacode;
	
	
	public Colors(){
		
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



	public String getColorName() {
		return colorName;
	}



	public void setColorName(String colorName) {
		this.colorName = colorName;
	}



	public String getHexacode() {
		return hexacode;
	}



	public void setHexacode(String hexacode) {
		this.hexacode = hexacode;
	}



	public static void setNextId(Integer nextId) {
		Colors.nextId = nextId;
	}



	


	public Colors(String colorName, String hexacode) {
		super();		
		this.colorName = colorName;
		this.hexacode = hexacode;
	}


	
}
