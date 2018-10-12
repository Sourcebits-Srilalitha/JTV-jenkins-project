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
@Table(name = "gemopedia_item_crystallography")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GemopediaCrystallography implements Serializable{
	

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name="gem_item_cry_id_seq", sequenceName="gem_item_cry_id_seq", allocationSize=1)	
	@Column(name="id", unique=true,nullable=false)
	private Integer id;	
	
	@Column(name="gemopedia_id")		
	private Integer itemId; 
	private String chemical_name;
	@Size(max = 1000)
	private String chemical_formula;	
	private String synthesis;
	private String crystal_system;
	private String nature;
	private String crystallinity;	
	private Date created_on;
	private Date modified_on;
	@Size(max = 4000)
	private String Comments;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChemical_formula() {
		return chemical_formula;
	}
	public void setChemical_formula(String chemical_formula) {
		this.chemical_formula = chemical_formula;
	}
	public String getSynthesis() {
		return synthesis;
	}
	public void setSynthesis(String synthesis) {
		this.synthesis = synthesis;
	}
	public String getCrystal_system() {
		return crystal_system;
	}
	public String getChemical_name() {
		return chemical_name;
	}
	public void setChemical_name(String chemical_name) {
		this.chemical_name = chemical_name;
	}
	public void setCrystal_system(String crystal_system) {
		this.crystal_system = crystal_system;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getCrystallinity() {
		return crystallinity;
	}
	public void setCrystallinity(String crystallinity) {
		this.crystallinity = crystallinity;
	}

	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
	public GemopediaCrystallography(){
		
	}
	public GemopediaCrystallography(Integer id, Integer itemId,
			String chemical_name, String chemical_formula, String synthesis,
			String crystal_system, String nature, String crystallinity,
			Date created_on, Date modified_on, String comments) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.chemical_name = chemical_name;
		this.chemical_formula = chemical_formula;
		this.synthesis = synthesis;
		this.crystal_system = crystal_system;
		this.nature = nature;
		this.crystallinity = crystallinity;
		this.created_on = created_on;
		this.modified_on = modified_on;
		Comments = comments;
	}
}
