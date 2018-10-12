package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "videos")// this will define the naming of the endpoints and the parameters for the default CRUD functions exposed by spring boot
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="video_Id_seq", sequenceName="video_Id_seq", allocationSize=1)
	private Integer id;
	
	private String title;	
	@Column(length=1000)
	private String description;
	private String link;
	@Column(name="video_type_id")
	private Integer type;
	
	/*@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "video_type_id" )
	private VideoTypes vdTyps;*/
	
	
	@Size(max=1)
	private String active;
	private Date created_on;
	private Date modified_on;
	
	private Integer page_index;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getVideo_type() {
		return type;
	}
	public void setVideo_type(Integer video_type) {
		this.type = video_type;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	/*public VideoTypes getVdTyps() {
		return vdTyps;
	}
	public void setVdTyps(VideoTypes vdTyps) {
		this.vdTyps = vdTyps;
	}
	*/
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public Video(){
		
	}
	
	public Video(Integer id, String title, String description, String link,
			Integer type, String active, Date created_on, Date modified_on,
			Integer page_index) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.type = type;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
		this.page_index = page_index;
	}
	public Integer getPage_index() {
		return page_index;
	}
	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}
	
	
	

}
