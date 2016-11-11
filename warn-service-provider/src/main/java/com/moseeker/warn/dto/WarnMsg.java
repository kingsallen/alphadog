package com.moseeker.warn.dto;

/**
 * @author ltf
 * 预警消息体
 * 2016年11月11日
 */
public class WarnMsg {
	/**
	 * 项目id
	 */
	private String appid;
	/**
	 * 出错位置
	 */
	private String location;
	/**
	 * 事件类型
	 */
	private String eventKey;
	/**
	 * 描述
	 */
	private String desc;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public WarnMsg(String appid, String location, String eventKey, String desc) {
		super();
		this.appid = appid;
		this.location = location;
		this.eventKey = eventKey;
		this.desc = desc;
	}
	
	public WarnMsg(){
		
	}
	
	@Override
	public String toString() {
		return String.format("项目id=%s, 出错位置=%s, 事件类型=%s, 错误描述=%s", appid,
				location, eventKey, desc);
	}
	
}
