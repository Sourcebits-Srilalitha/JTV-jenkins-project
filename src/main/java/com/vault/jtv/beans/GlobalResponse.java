package com.vault.jtv.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalResponse {

	private Integer httpStatusCode;
	private String httpMsg;
	private Object responseData;
	private Integer statusCode;
	private String localMsg;	
	
	
	public GlobalResponse(Integer httpStatusCode, String httpMsg,
			Integer statusCode, String localMsg) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.httpMsg = httpMsg;
		this.statusCode = statusCode;
		this.localMsg = localMsg;
	}
	public GlobalResponse(Integer httpStatusCode, String httpMsg,
			Object responseData, Integer statusCode, String localMsg) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.httpMsg = httpMsg;
		this.responseData = responseData;
		this.statusCode = statusCode;
		this.localMsg = localMsg;
	}
	
	
	public GlobalResponse(Integer httpStatusCode, String httpMsg,
			String localMsg) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.httpMsg = httpMsg;
		this.localMsg = localMsg;
	}
	public String getHttpMsg() {
		return httpMsg;
	}
	public void setHttpMsg(String httpMsg) {
		this.httpMsg = httpMsg;
	}
	public String getLocalMsg() {
		return localMsg;
	}
	public void setLocalMsg(String localMsg) {
		this.localMsg = localMsg;
	}
	public Object getResponseData() {		
		return responseData;
	}
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	
}