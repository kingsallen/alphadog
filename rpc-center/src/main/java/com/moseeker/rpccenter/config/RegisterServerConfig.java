package com.moseeker.rpccenter.config;

/**
 * ZK服务注册的配置信息
 *
 * @author jack
 * date : Feb 3, 2017
 * email: wengjianfei@moseeker.com
 */
public class RegisterServerConfig {
	
	private String name;				//服务名称，用于区分不同的服务。唯一
	private String ip;					//服务IP地址。如果没有配置，从网卡中获取。
	private int port;					//服务端口。如果没有配置，1000以后，未使用的端口号中取一个。
	private String protocol = "thrift";	//协议名称。默认使用thrift
	private int weight;					//服务权重。和客户端的服务使用策略配合使用
	private int interval;				//时间间隔。
	private int selector;				//selector的数量
	private int worker;					//工作线程的数量
	private boolean multiFlag;			//是否启用混合协议

}
