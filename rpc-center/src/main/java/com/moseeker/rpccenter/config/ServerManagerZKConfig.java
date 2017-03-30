package com.moseeker.rpccenter.config;

import java.io.File;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.rpccenter.common.Constants;

/**
 * 
 * zookeeper监控节点的配置信息 
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 21, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum ServerManagerZKConfig {
	
	config;

    /** 链接字符串 */
    private String IP;

    private int port;

    private String root = Constants.ZK_NAMESPACE_ROOT;

	private String servers = Constants.ZK_NAMESPACE_SERVERS;

	private String zkSeparator = Constants.ZK_SEPARATOR_DEFAULT;

	/** 会话超时时间 */
	private int sessionTimeOut = 30000;

	/** 连接超时时间 */
	private int connectionTimeOut = 15000;

    /** 重试次数，默认重试为1次 */
    private int retry = 2;

    /** 共享一个zk链接，默认为true */
    private boolean singleton = true;

    /** 授权字符串(server端配置，client端不用设置) */
    private String auth;

    private String connectstr;

    private int baseSleepTimeMS;
    
    private ServerManagerZKConfig() {

        ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
        try {
        	
        	configUtil.loadResource("service.properties");
			this.IP = configUtil.get("zookeeper.ZKIP", String.class, "127.0.0.1");
			this.port = configUtil.get("zookeeper.ZKport", Integer.class, 0);
			this.root = configUtil.get("zookeeper.root", String.class, Constants.ZK_NAMESPACE_ROOT);
			this.servers = configUtil.get("zookeeper.servers", String.class, Constants.ZK_NAMESPACE_SERVERS);
			this.zkSeparator = configUtil.get("zookeeper.zkSeparator", String.class, Constants.ZK_SEPARATOR_DEFAULT);
			this.sessionTimeOut = configUtil.get("zookeeper.sessionTimeOut", Integer.class, 30000);
			this.connectionTimeOut = configUtil.get("zookeeper.connectionTimeOut", Integer.class, 15000);
			this.retry = configUtil.get("thrift.client.retry", Integer.class, 2);
			this.baseSleepTimeMS = configUtil.get("zookeeper.baseSleepTimeMS", Integer.class, 1000);
			this.connectstr = this.getIP()+":"+this.port;
			//configUtil.loadAbsoluteResource(confPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public String getConnectstr() {
		return connectstr;
	}

	public int getBaseSleepTimeMS() {
		return baseSleepTimeMS;
	}

	public String getIP() {
		return IP;
	}

	public int getPort() {
		return port;
	}

	public String getRoot() {
		return root;
	}

	public String getServers() {
		return servers;
	}

	public String getZkSeparator() {
		return zkSeparator;
	}

	public int getSessionTimeOut() {
		return sessionTimeOut;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public int getRetry() {
		return retry;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public String getAuth() {
		return auth;
	}
}
