package com.moseeker.rpccenter.config;

import java.io.File;

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
    private String connectstr;

    /** 会话超时时间 */
    private int timeout = 35000;
    
    /** 会话超时时间 */
    private int connectionTimeout = 35000;

    /** 重试次数，默认重试为1次 */
    private int retry = 2;

    /** 共享一个zk链接，默认为true */
    private boolean singleton = true;

    /** 全局path前缀,常用来区分不同的应用 */
    private String namespace = Constants.ZK_NAMESPACE_ROOT;

    /** 授权字符串(server端配置，client端不用设置) */
    private String auth;
    
    private ServerManagerZKConfig() {
    	String path = System.getProperty("user.dir");  
        System.out.println("System.getProperty(\"user.dir\") = [" + path + "]");  
  
    	String confPath = path.concat(File.separator).concat("src")  
                .concat(File.separator).concat("main").concat(File.separator) 
                .concat("resources").concat(File.separator).concat("debug")  
                .concat(File.separator).concat(Constants.ZOO_CONF_FILE);  
        System.out.println(confPath);
        ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
        try {
			configUtil.loadAbsoluteResource(confPath);
			setConnectstr(configUtil.get("registry.connectstr", String.class));
	    	setNamespace(configUtil.get("registry.namespace", String.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public String getConnectstr() {
		return connectstr;
	}

	public void setConnectstr(String connectstr) {
		this.connectstr = connectstr;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
}
