
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

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "tmp_archive_items")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TmpArchiveItems implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="arc_Id_seq", sequenceName="arc_Id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="enterprise_id")
	private Integer enterpriseId;	
	
	private String sku;
	
	@Column(name="archive_type")
	private Integer archiveType;
	
	@Column(name="date_of_purchase")
	@Type(type="timestamp")
	private Date dateOfPurchase;
	
	@Column(name="archived_on")
	@Type(type="timestamp")
	private Date archivedOn;
	
	

	public TmpArchiveItems(){
		
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(Integer archiveType) {
		this.archiveType = archiveType;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Date getArchivedOn() {
		return archivedOn;
	}

	public void setArchivedOn(Date archivedOn) {
		this.archivedOn = archivedOn;
	}

	

	public static void setNextId(Integer nextId) {
		TmpArchiveItems.nextId = nextId;
	}

	public TmpArchiveItems(Integer userId, Integer enterpriseId, String sku, Integer archiveType, Date dateOfPurchase,
			Date archivedOn) {
		super();
		this.userId = userId;
		this.enterpriseId = enterpriseId;
		this.sku = sku;
		this.archiveType = archiveType;
		this.dateOfPurchase = dateOfPurchase;
		this.archivedOn = archivedOn;
	}

	
}
