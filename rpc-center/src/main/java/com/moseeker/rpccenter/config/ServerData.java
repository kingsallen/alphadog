package com.moseeker.rpccenter.config;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.exception.RpcException;

/**
 * ZK服务注册的配置信息
 *
 * @author jack
 * date : Feb 3, 2017
 * email: wengjianfei@moseeker.com
 */
public class ServerData implements ConfigCheck {
	
	private String service;				//服务名称，用于区分不同的服务。唯一
	private String ip;					//服务IP地址。如果没有配置，从网卡中获取。
	private int port;					//服务端口。如果没有配置，1000以后，未使用的端口号中取一个。
	private String protocol = "thrift";	//协议名称。默认使用thrift
	private int weight = 10;			//服务权重。和客户端的服务使用策略配合使用
	private int interval;				//时间间隔。
	private int selector;				//selector的数量
	private int worker;					//工作线程的数量
	private int multi;					//是否启用混合协议
	private String owner;				//维护人员
	private String language;			//开发语言
	private String server_type;			//服务类型

	@Override
	public boolean check() throws RpcException {
		if(StringUtils.isNotNullOrEmpty(ip) && StringUtils.isNotNullOrEmpty(service) && port > 0) {
			return true;
		}
		return false;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getServer_type() {
		return server_type;
	}

	public void setServer_type(String server_type) {
		this.server_type = server_type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getSelector() {
		return selector;
	}

	public void setSelector(int selector) {
		this.selector = selector;
	}

	public int getWorker() {
		return worker;
	}

	public void setWorker(int worker) {
		this.worker = worker;
	}

	public int getMulti() {
		return multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}
}
