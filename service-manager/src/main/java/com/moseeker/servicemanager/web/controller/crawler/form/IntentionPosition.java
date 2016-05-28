package com.moseeker.servicemanager.web.controller.crawler.form;

public class IntentionPosition {

	private int id;
	private int profile_intention_id;
	private int position_code;
	private String position_name;
	
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
	public int getPosition_code() {
		return position_code;
	}
	public void setPosition_code(int position_code) {
		this.position_code = position_code;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
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
		IntentionPosition other = (IntentionPosition) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
