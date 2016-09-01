package com.moseeker.servicemanager.web.controller.profile.form;

public class DownloadResult {

	private int status;
	private String message;
	private String code;
	private String downloadlink;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDownloadlink() {
		return downloadlink;
	}
	public void setDownloadlink(String downloadlink) {
		this.downloadlink = downloadlink;
	}
}
