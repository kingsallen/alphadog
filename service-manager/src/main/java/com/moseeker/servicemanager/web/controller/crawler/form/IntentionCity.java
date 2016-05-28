package com.moseeker.servicemanager.web.controller.crawler.form;

public class IntentionCity {

	private int id;
	private int profile_intention_id;
	private int city_code;
	private String city_name;
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
	public int getCity_code() {
		return city_code;
	}
	public void setCity_code(int city_code) {
		this.city_code = city_code;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
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
		IntentionCity other = (IntentionCity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
