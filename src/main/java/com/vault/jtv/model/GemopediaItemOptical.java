package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "gemopedia_item_optical")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaItemOptical implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	public static Integer nextId = (int) 0L;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name="gem_item_opt_id_seq", sequenceName="gem_item_opt_id_seq", allocationSize=1)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	@Column(name="gemopedia_id")
	private Integer itemId; 
	private String transparency;
	private String birefringence;
	private String optic_character;
	private String optic_sign;	
	private String refractive_index;
	private String polariscope_reaction;
	@Size(max = 4000)
	private String fluorescence;
	private String ccf_reaction;
	@Size(max = 1000)
	private String pleochroism;
	private String dispersion;
	private String comments;
	private Date created_on;
	private Date modified_on;
	
		
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public String getTransparency() {
		return transparency;
	}


	public void setTransparency(String transparency) {
		this.transparency = transparency;
	}


	public String getBirefringence() {
		return birefringence;
	}


	public void setBirefringence(String birefringence) {
		this.birefringence = birefringence;
	}


	public String getOptic_character() {
		return optic_character;
	}


	public void setOptic_character(String optic_character) {
		this.optic_character = optic_character;
	}
	
	public String getOptic_sign() {
		return optic_sign;
	}


	public void setOptic_sign(String optic_sign) {
		this.optic_sign = optic_sign;
	}
	


	public String getRefractive_index() {
		return refractive_index;
	}


	public void setRefractive_index(String refractive_index) {
		this.refractive_index = refractive_index;
	}


	public String getPolariscope_reaction() {
		return polariscope_reaction;
	}


	public void setPolariscope_reaction(String polariscope_reaction) {
		this.polariscope_reaction = polariscope_reaction;
	}


	public String getFluorescence() {
		return fluorescence;
	}


	public void setFluorescence(String fluorescence) {
		this.fluorescence = fluorescence;
	}


	public String getCcf_reaction() {
		return ccf_reaction;
	}


	public void setCcf_reaction(String ccf_reaction) {
		this.ccf_reaction = ccf_reaction;
	}


	public String getPleochroism() {
		return pleochroism;
	}


	public void setPleochroism(String pleochroism) {
		this.pleochroism = pleochroism;
	}


	public String getDispersion() {
		return dispersion;
	}


	public void setDispersion(String dispersion) {
		this.dispersion = dispersion;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
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
	
	
	public GemopediaItemOptical(){
		
	}


	public GemopediaItemOptical(Integer itemId, String transparency, String birefringence, String opticCharacter,
			String refractiveIndex, String polariscopeReaction, String fluorescence, String ccfReaction,
			String pleochroism, String dispersion, String comments, Date created_on, Date modified_on) {
		this.itemId = itemId;
		this.transparency = transparency;
		this.birefringence = birefringence;
		this.optic_character = opticCharacter;
		this.refractive_index = refractiveIndex;
		this.polariscope_reaction = polariscopeReaction;
		this.fluorescence = fluorescence;
		this.ccf_reaction = ccfReaction;
		this.pleochroism = pleochroism;
		this.dispersion = dispersion;
		this.comments = comments;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}

	
}