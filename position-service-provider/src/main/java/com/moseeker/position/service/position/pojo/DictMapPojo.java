package com.moseeker.position.service.position.pojo;

/**
 * 城市字典
 * @author wjf
 *
 */
public class DictMapPojo {

	private int id;
	private int code;
	private int code_other;
	private int channel;
	private int status;
	private String create_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getCode_other() {
		return code_other;
	}
	public void setCode_other(int code_other) {
		this.code_other = code_other;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
