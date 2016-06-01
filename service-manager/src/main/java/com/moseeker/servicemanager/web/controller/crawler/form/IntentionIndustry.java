package com.moseeker.servicemanager.web.controller.crawler.form;

public class IntentionIndustry {

	private int id;
	private int profile_intention_id;
	private int industry_code;
	private String industry_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProfile_intention_id() {
		return profile_intention_id;
	}
	public void setProfile_intention_id(int profile_intention_id) {
		this.profile_intention_id = profile_intention_id;
	}
	public int getIndustry_code() {
		return industry_code;
	}
	public void setIndustry_code(int industry_code) {
		this.industry_code = industry_code;
	}
	public String getIndustry_name() {
		return industry_name;
	}
	public void setIndustry_name(String industry_name) {
		this.industry_name = industry_name;
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
		IntentionIndustry other = (IntentionIndustry) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
