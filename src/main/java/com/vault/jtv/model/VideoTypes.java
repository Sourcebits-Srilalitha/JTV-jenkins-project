package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "video_types")// this will define the naming of the endpoints and the parameters for the default CRUD functions exposed by spring boot
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoTypes implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="video_type_Id_seq", sequenceName="video_type_Id_seq", allocationSize=1)
	private Integer id;
	
	private String title;
	@Size (max=1)
	private String active;
	private Date created_on;
	private Date modified_on;
	
	private Integer page_index;
	
	@OneToMany(cascade = {CascadeType.ALL},mappedBy="type",targetEntity=Video.class)		
    private List<Video> videos; 
	
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
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
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
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	
	
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public VideoTypes(){
		
	}
	/* ******** Add full constructor here**********	 */
	public VideoTypes(String title, String active, Date created_on,
			Date modified_on) {
		this.title = title;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}
	
	
	public VideoTypes(String title, String active, Date created_on, Date modified_on, List<Video> videos) {
		super();
		this.title = title;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
		this.videos = videos;
	}
	public Integer getPage_index() {
		return page_index;
	}
	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}
	
}
