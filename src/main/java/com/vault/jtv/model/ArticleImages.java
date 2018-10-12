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
@Table (name= "article_images")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleImages implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="article_image_Id_seq", sequenceName="article_image_Id_seq", allocationSize=1)
	private Integer id;
	@Column(name="IMAGE_URL")
	private String imageurl;
	@Size(max=1)
	private String is_hero_image; 
	
	private Integer article_id; 
	
	/*@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn(name = "article_Id", nullable = false )
	private Article article;
	public Article getParent() { return article; }	
	public void setArticle(Article article){
		this.article = article;
	}	
	*/
	
	public Integer getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getIsHeroImage() {
		return is_hero_image;
	}
	public void setIsHeroImage(String isHeroImage) {
		this.is_hero_image = isHeroImage;
	}
	
	
	
	public ArticleImages() {
	}
	
	public ArticleImages(String imageurl, String isHeroImage, Integer article_id) {
		this.imageurl = imageurl;
		this.is_hero_image = isHeroImage;
		this.article_id = article_id;
	}	
	
}
