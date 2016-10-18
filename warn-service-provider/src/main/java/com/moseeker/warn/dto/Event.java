package com.moseeker.warn.dto;

import java.util.List;

/**
 * @author lucky8987
 * 事件信息
 */
public class Event {
	/**
	 * 项目appid
	 */
	private String projectAppid;
	
	/**
	 * 事件key
	 */
	private String eventKey;

	/**
	 * 事件名称
	 */
	private String eventName;
	
	/**
	 * 事件描述
	 */
	private String eventDesc;
	
	/**
	 * 触发通知的次数
	 */
	private int thresholdValue;
	
	/**
	 * 发送的时间间隔(单位：秒)
	 */
	private int thresholdInterval;
	
	/**
	 * 邮件通知
	 */
	private boolean notifyByEmail;
	
	/**
	 * 短信通知
	 */
	private boolean notifyBySms;
	
	/**
	 * 微信通知
	 */
	private boolean notifyByWechat;
	
	/**
	 * 发送人员
	 */
	private List<Member> members;

	public String getProjectAppid() {
		return projectAppid;
	}

	public void setProjectAppid(String projectAppid) {
		this.projectAppid = projectAppid;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public int getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(int thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public int getThresholdInterval() {
		return thresholdInterval;
	}

	public void setThresholdInterval(int thresholdInterval) {
		this.thresholdInterval = thresholdInterval;
	}

	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}

	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}

	public boolean isNotifyBySms() {
		return notifyBySms;
	}

	public void setNotifyBySms(boolean notifyBySms) {
		this.notifyBySms = notifyBySms;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	public String getEventKey() {
		return eventKey;
	}

	public boolean isNotifyByWechat() {
		return notifyByWechat;
	}

	public void setNotifyByWechat(boolean notifyByWechat) {
		this.notifyByWechat = notifyByWechat;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	public Event(String projectAppid, String eventKey, String eventName,
			String eventDesc, int thresholdValue, int thresholdInterval,
			boolean notifyByEmail, boolean notifyBySms, boolean notifyByWechat) {
		super();
		this.projectAppid = projectAppid;
		this.eventKey = eventKey;
		this.eventName = eventName;
		this.eventDesc = eventDesc;
		this.thresholdValue = thresholdValue;
		this.thresholdInterval = thresholdInterval;
		this.notifyByEmail = notifyByEmail;
		this.notifyBySms = notifyBySms;
		this.notifyByWechat = notifyByWechat;
	}
	
	public Event() {}
	
	@Override
	public String toString() {
		return String
				.format("Event [projectAppid=%s, eventKey=%s, eventName=%s, eventDesc=%s, thresholdValue=%s, thresholdInterval=%s, notifyByEmail=%s, notifyBySms=%s, members=%s]",
						projectAppid, eventKey, eventName, eventDesc,
						thresholdValue, thresholdInterval, notifyByEmail,
						notifyBySms, members);
	}
	
}
