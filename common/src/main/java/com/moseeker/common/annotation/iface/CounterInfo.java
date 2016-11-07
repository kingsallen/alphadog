package com.moseeker.common.annotation.iface;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ltf
 * 统计信息
 * 2016年11月2日
 */
public class CounterInfo {
	/**
	 * 类名
	 */
	private String className;
	/**
	 * 方法
	 */
	private String method;
	/**
	 * 参数
	 */
	private String args;
	/**
	 * 状态 success or fail
	 */
	private String status;
	/**
	 * 开始时间
	 */
	@JSONField(serialize=false)
	private long startTime;
	/**
	 * 结束时间
	 */
	@JSONField(serialize=false)
	private long endTime;
	/**
	 * 耗时(ms)
	 */
	private long time;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public CounterInfo(String className, String method, long startTime) {
		super();
		this.className = className;
		this.method = method;
		this.startTime = startTime;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public CounterInfo(String className, String method, String status,
			long time) {
		super();
		this.className = className;
		this.method = method;
		this.status = status;
		this.time = time;
	}
	
	public CounterInfo(){}
	
	@Override
	public String toString() {
		return String
				.format("CounterInfo [className=%s, method=%s, status=%s, time=%s]",
						className, method, status, time);
	}
}
