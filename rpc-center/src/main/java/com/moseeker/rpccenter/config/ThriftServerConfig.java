package com.moseeker.rpccenter.config;

public class ThriftServerConfig {

	/** 服务名 */
    private String name;

    /** 服务负责人 */
    private String owner;

    /** 服务名(全称)：命名空间$服务名简称 */
    private String service;

    /** 服务地址，使用这个参数表示使用的直连的方式 */
    private String address;

    /** 重试次数，默认1次 */
    private int retry = 1;

    /** 完整接口名 */
    private String iface;

    /** 协议 */
    private String protocol = "thrift";

    /** 是否开启监控 */
    private boolean monitor;

    /** 监控时间间隔，单位为:s，默认为5min */
    private int interval = 5 * 60;

    /** 负载均衡策略，默认为round，可选：round和random */
    private String loadbalance = "random";

    /** thrift connect 超时时间，单位为ms，默认为3s */
    private int timeout = 100000;

    // 下面的配置项是连接池的基本配置
    /** 最大活跃连接数 */
    private int maxActive = 1024;

    /** 链接池中最大空闲的连接数,默认为100 */
    private int maxIdle = 100;

    /** 连接池中最少空闲的连接数,默认为0 */
    private int minIdle = 0;

    /** 当连接池资源耗尽时，调用者最大阻塞的时间 */
    private int maxWait = 10000;

    /** 空闲链接”检测线程，检测的周期，毫秒数，默认位3min，-1表示关闭空闲检测 */
    private int timeBetweenEvictionRunsMillis = 180000;

    /** 空闲时是否进行连接有效性验证，如果验证失败则移除，默认为false */
    private boolean testWhileIdle = true;

    // 下面的配置项是heartbeat的基本配置
    /** 心跳频率，毫秒。默认10s。 */
    private int heartbeat = 10 * 1000;

    /** 心跳执行的超时时间，单位ms ,默认3s */
    private int heartbeatTimeout = 3000;

    /** 重试次数，默认3次 */
    private int heartbeatTimes = 3;

    /** 重试间隔,单位为ms，默认为3s */
    private int heartbeatInterval = 3000;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public String getIface() {
		return iface;
	}

	public void setIface(String iface) {
		this.iface = iface;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isMonitor() {
		return monitor;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getLoadbalance() {
		return loadbalance;
	}

	public void setLoadbalance(String loadbalance) {
		this.loadbalance = loadbalance;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public int getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}

	public int getHeartbeatTimeout() {
		return heartbeatTimeout;
	}

	public void setHeartbeatTimeout(int heartbeatTimeout) {
		this.heartbeatTimeout = heartbeatTimeout;
	}

	public int getHeartbeatTimes() {
		return heartbeatTimes;
	}

	public void setHeartbeatTimes(int heartbeatTimes) {
		this.heartbeatTimes = heartbeatTimes;
	}

	public int getHeartbeatInterval() {
		return heartbeatInterval;
	}

	public void setHeartbeatInterval(int heartbeatInterval) {
		this.heartbeatInterval = heartbeatInterval;
	}
}
