package com.moseeker.servicemanager.web.controller.crawler.form;

import java.util.List;

public class MessageBean {

	private int status;
	private String message;
	List<Resume> resumes;
	
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
	public List<Resume> getResumes() {
		return resumes;
	}
	public void setResumes(List<Resume> resumes) {
		this.resumes = resumes;
	}
}
