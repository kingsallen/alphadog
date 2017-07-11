package com.moseeker.function.service.chaos;

public class PositionForSyncResultPojo {

	private int status;
	private String message;
	private String job_id;
	private String position_id;
	private String channel;
	private int remain_number = -1;
	private int resume_number = -1;
	private String sync_time;
	private String pub_place_name;
	private int account_id;

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
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public int getRemain_number() {
		return remain_number;
	}
	public void setRemain_number(int remain_number) {
		this.remain_number = remain_number;
	}
	public String getSync_time() {
		return sync_time;
	}
	public void setSync_time(String sync_time) {
		this.sync_time = sync_time;
	}
	public String getPub_place_name() {
		return pub_place_name;
	}
	public void setPub_place_name(String pub_place_name) {
		this.pub_place_name = pub_place_name;
	}

	public int getResume_number() {
		return resume_number;
	}

	public void setResume_number(int resume_number) {
		this.resume_number = resume_number;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
}
