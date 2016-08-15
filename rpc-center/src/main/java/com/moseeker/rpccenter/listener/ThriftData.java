package com.moseeker.rpccenter.listener;

import com.alibaba.fastjson.JSON;

public class ThriftData {

	private int interval;
	private int maxWorkerThreads;
	private int minWorkerThreads;
	private String name;
	private String IP;
	private String path;
	private int multi;

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	private int port;
	private String protocol;
	private String service;

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getMaxWorkerThreads() {
		return maxWorkerThreads;
	}

	public void setMaxWorkerThreads(int maxWorkerThreads) {
		this.maxWorkerThreads = maxWorkerThreads;
	}

	public int getMinWorkerThreads() {
		return minWorkerThreads;
	}

	public void setMinWorkerThreads(int minWorkerThreads) {
		this.minWorkerThreads = minWorkerThreads;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getMulti() {
		return multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}
}
