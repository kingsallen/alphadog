package com.moseeker.servicemanager.web.controller.crawler.form;

public class ProjectExp {

	private int id;
	private String name;
	private String company_name;
	private String start_date;
	private String end_date;
	private int end_until_now;
	private String end_until_now_name;
	private String description;
	private String member;
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getEnd_until_now() {
		return end_until_now;
	}
	public void setEnd_until_now(int end_until_now) {
		this.end_until_now = end_until_now;
	}
	public String getEnd_until_now_name() {
		return end_until_now_name;
	}
	public void setEnd_until_now_name(String end_until_now_name) {
		this.end_until_now_name = end_until_now_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectExp other = (ProjectExp) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
