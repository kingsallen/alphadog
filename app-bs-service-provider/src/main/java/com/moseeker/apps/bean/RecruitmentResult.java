package com.moseeker.apps.bean;

public class RecruitmentResult {
	private static final long serialVersionUID = 2524325843095279211L;
	
	private String reason;
	private int reward;
	private int status=0;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
}