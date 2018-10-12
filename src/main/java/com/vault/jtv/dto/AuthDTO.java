package com.vault.jtv.dto;

public class AuthDTO {

	private String userName;
	private String jtvUserId;
	private String enterpriseId;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJtvUserId() {
		return jtvUserId;
	}
	public void setJtvUserId(String jtvuserid) {
		this.jtvUserId = jtvuserid;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}	
	public AuthDTO(String userName, String jtvUserId, String enterpriseId) {
		super();
		this.userName = userName;
		this.jtvUserId = jtvUserId;
		this.enterpriseId = enterpriseId;
	}
	public AuthDTO(){
		
	}

}
