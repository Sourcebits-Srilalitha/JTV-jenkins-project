package com.vault.jtv.exception;

public class GlobalException extends Exception{	
	
	private static final long serialVersionUID = -9066771620L;
	
	private Integer errorCode;
	private String errorMsg;
	private Integer localErrorCode;
	private String localErrorMsg;
	
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getLocalErrorCode() {
		return localErrorCode;
	}
	public void setLocalErrorCode(Integer localErrorCode) {
		this.localErrorCode = localErrorCode;
	}
	public String getLocalErrorMsg() {
		return localErrorMsg;
	}
	public void setLocalErrorMsg(String localErrorMsg) {
		this.localErrorMsg = localErrorMsg;
	}
	
	public GlobalException(Integer errorCode, String errorMsg,
			Integer localErrorCode, String localErrorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.localErrorCode = localErrorCode;
		this.localErrorMsg = localErrorMsg;
	}
	
	

}