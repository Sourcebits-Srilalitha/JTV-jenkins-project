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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Integer nextId = (int) 0L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="users_Id_seq", sequenceName="users_Id_seq", allocationSize=1)
	private Integer id;
	
	private String username; 
	private String first_name;
	private String last_name;
	@Column(name="enterprise_user_id")
	private String enterpriseId;
	private String customerId;
	private String email;
	private Date created_on;
	private Date modified_on;
	private Date lastJTVCall;
	
	@JsonIgnore
	@Column(length=5000)
	private String offlineToken;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String EnterpriseId) {
		enterpriseId = EnterpriseId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public UserAccount(){
		
	}
	/* ******** Add full constructor here**********	 */	
	
	
	public Date getLastJTVCall() {
		return lastJTVCall;
	}
	public UserAccount(Integer id, String username, String first_name,
			String last_name, String enterpriseId, String customerId,
			String email, Date created_on, Date modified_on, Date lastJTVCall, String offlineToken ) {
		super();
		this.id = id;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.enterpriseId = enterpriseId;
		this.customerId = customerId;
		this.email = email;
		this.created_on = created_on;
		this.modified_on = modified_on;
		this.lastJTVCall = lastJTVCall;
		this.offlineToken=offlineToken;
		
	}
	public void setLastJTVCall(Date lastJTVCall) {
		this.lastJTVCall = lastJTVCall;
	}
	public String getOfflineToken() {
		return offlineToken;
	}
	public void setOfflineToken(String offlineToken) {
		this.offlineToken = offlineToken;
	}
	
	
	
}

