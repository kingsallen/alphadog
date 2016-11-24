package com.moseeker.function.service.choas;

public class PositionForSyncResultPojo {

	private int status;
	private String message;
	private String job_id;
	private String position_id;
	private String channel;
	private int remain_number;
	private String sync_time;
	
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
}
