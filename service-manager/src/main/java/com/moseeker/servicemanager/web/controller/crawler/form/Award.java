package com.moseeker.servicemanager.web.controller.crawler.form;

public class Award {

	private int id;
	private int profile_id;
	private String reward_date;
	private String name;
	private String award_winning_status;
	private String level;
	private String description;
	private String create_time;
	private String update_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProfile_id() {
		return profile_id;
	}
	public void setProfile_id(int profile_id) {
		this.profile_id = profile_id;
	}
	public String getReward_date() {
		return reward_date;
	}
	public void setReward_date(String reward_date) {
		this.reward_date = reward_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAward_winning_status() {
		return award_winning_status;
	}
	public void setAward_winning_status(String award_winning_status) {
		this.award_winning_status = award_winning_status;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}
