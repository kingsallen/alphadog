package com.moseeker.thrift.gen.dao.struct.configdb;

import java.util.Arrays;
import java.util.List;

/**
 * @author ltf
 * 事件信息
 */
public class Event {
	
	private Integer id;
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
	 * 通知渠道
	 */
	private List<String> notifyChannels;
	
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

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	public List<String> getNotifyChannels() {
		return notifyChannels;
	}

	public void setNotifyChannels(List<String> notifyChannels) {
		this.notifyChannels = notifyChannels;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Event(Integer id, String projectAppid, String eventKey, String eventName,
			String eventDesc, int thresholdValue, int thresholdInterval) {
		super();
		this.id = id;
		this.projectAppid = projectAppid;
		this.eventKey = eventKey;
		this.eventName = eventName;
		this.eventDesc = eventDesc;
		this.thresholdValue = thresholdValue;
		this.thresholdInterval = thresholdInterval;
	}
	
	public Event() {

	}
	
	@Override
	public String toString() {
		return String
				.format("Event [projectAppid=%s, eventKey=%s, eventName=%s, eventDesc=%s, thresholdValue=%s, thresholdInterval=%s, notifyChannels=%s members=%s]",
						projectAppid, eventKey, eventName, eventDesc,
						thresholdValue, thresholdInterval, Arrays.toString(notifyChannels.toArray()), Arrays.toString(members.toArray()));
	}
	
}
