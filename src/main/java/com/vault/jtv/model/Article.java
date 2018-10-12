package com.vault.jtv.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "articles")// this will define the naming of the endpoints and the parameters for the default CRUD functions exposed by spring boot
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "article_Id_seq")
	@SequenceGenerator(name="article_Id_seq", sequenceName="Id_seq", allocationSize=1)
	private Integer id;
	
	private String title;
	@Transient
	@Column(length=1000)
	private String description;
	@Column(length=5000)
	private String body;
	@Column(name="article_type_id")
	private Integer type;
	
	@Column(length=5000)
	private String series;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "article_id", cascade = { CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE })
	@Column(nullable= false)
	private List<ArticleImages> articleImages;
	public List<ArticleImages> getArticleImages() {return articleImages;}	
	public void setArticleImages(List<ArticleImages> articelImages){this.articleImages = articelImages;} 

	@Size (max=1)
	private String active;
	private Date created_on;
	private Date modified_on;
	
	private String contentId;

	private Integer page_index;
	
	private String published;
	 
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}

	
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Integer getArticletype() {
		return type;
	}
	public void setArticletype(Integer article_type) {
		this.type = article_type;
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
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	
	protected static Integer getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	public Article(){
		
	}
	public Article(String title, String link, Integer type,String series, String active, Date created_on, Date modified_on) {
		super();
		this.title = title;
		this.body = link;
		this.series = series;
		this.type = type;
		this.active = active;
		this.created_on = created_on;
		this.modified_on = modified_on;
	}
	public Integer getPage_index() {
		return page_index;
	}
	public void setPage_index(Integer page_index) {
		this.page_index = page_index;
	}
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	
	
	
	

}
