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
@Table(name = "gemopedia_item_physics")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaItemPhysics implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name="gem_item_phy_id_seq", sequenceName="gem_item_phy_id_seq", allocationSize=1)
	@Column(name="id", unique=true,nullable=false)
	private Integer id;
	@Column(name="gemopedia_id")
	private Integer itemId; 
	private String hardness;	
	private String toughness;
	private String streak;
	private String specific_gravity;
	@Column(length=4000)
	private String inclusion;
	
	private String luster;
	
	private String stability;
	private String fracture;
	private String cleavage;
	@Size(max = 4000)
	private String comments;
	private Date created_on;
	private Date modified_on;


	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public GemopediaItemPhysics(){
		
	}
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
	public String getHardness_low() {
		return hardness;
	}
	public void setHardness_low(String hardness_low) {
		this.hardness = hardness_low;
	}
	public String getHardness_high() {
		return toughness;
	}
	public void setHardness_high(String hardness_high) {
		this.toughness = hardness_high;
	}
	public String getStreak() {
		return streak;
	}
	public void setStreak(String streak) {
		this.streak = streak;
	}
	public String getGravity_low() {
		return specific_gravity;
	}
	public void setGravity_low(String gravity_low) {
		this.specific_gravity = gravity_low;
	}
	public String getGravity_high() {
		return inclusion;
	}
	public void setGravity_high(String gravity_high) {
		this.inclusion = gravity_high;
	}
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date CREATED_ON) {
		this.created_on = CREATED_ON;
	}
	public Date getModified_on() {
		return modified_on;
	}
	public void setModified_on(Date MODIFIED_ON) {
		this.modified_on = MODIFIED_ON;
	}
	public String getLuster() {
		return luster;
	}
	public void setLuster(String luster) {
		this.luster = luster;
	}
	public String getStability() {
		return stability;
	}
	public void setStability(String stability) {
		this.stability = stability;
	}
	public String getFracture() {
		return fracture;
	}
	public void setFracture(String fracture) {
		this.fracture = fracture;
	}
	public String getCleavage() {
		return cleavage;
	}
	public void setCleavage(String cleavage) {
		this.cleavage = cleavage;
	}
	public String getComment() {
		return comments;
	}
	public void setComment(String comment) {
		this.comments = comment;
	}
	public GemopediaItemPhysics(Integer itemId, String hardness, String toughness, String streak, String gravity,
			String inclusion, String luster, String stability, String fracture, String cleavage, String comment,
			Date created_on, Date modified_on) {
		this.itemId = itemId;
		this.hardness = hardness;
		this.toughness = toughness;
		this.streak = streak;
		this.specific_gravity = gravity;
		this.inclusion = inclusion;
		this.luster = luster;
		this.stability = stability;
		this.fracture = fracture;
		this.cleavage = cleavage;
		this.comments = comment;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}
	
	


}