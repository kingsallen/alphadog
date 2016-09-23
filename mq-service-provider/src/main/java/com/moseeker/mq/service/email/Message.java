package com.moseeker.mq.service.email;

import java.util.HashMap;

/**
 * 
 * 消息通知 
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 23, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class Message {

	private HashMap<String, String> params = new HashMap<>();	//替换消息模版的变量参数
	private int eventType;										//消息类型
	private int location;										//触发地点
	private String template;									//指定的消息模版的位置
	
	public HashMap<String, String> getParams() {
		return params;
	}
	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
