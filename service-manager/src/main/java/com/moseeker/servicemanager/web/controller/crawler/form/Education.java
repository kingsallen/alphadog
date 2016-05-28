package com.moseeker.servicemanager.web.controller.crawler.form;

public class Education {

	private int id;
	private String college_name;
	private int college_code;
	private String college_logo;
	private String major_name;
	private int major_code;
	private int degree;
	private String degree_name;
	private String start_date;
	private String end_date;
	private int end_until_now;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	public int getCollege_code() {
		return college_code;
	}
	public void setCollege_code(int college_code) {
		this.college_code = college_code;
	}
	public String getCollege_logo() {
		return college_logo;
	}
	public void setCollege_logo(String college_logo) {
		this.college_logo = college_logo;
	}
	public String getMajor_name() {
		return major_name;
	}
	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
	public int getMajor_code() {
		return major_code;
	}
	public void setMajor_code(int major_code) {
		this.major_code = major_code;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public String getDegree_name() {
		return degree_name;
	}
	public void setDegree_name(String degree_name) {
		this.degree_name = degree_name;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
		Education other = (Education) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
