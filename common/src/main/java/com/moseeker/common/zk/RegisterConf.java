package com.moseeker.common.zk;

import org.apache.thrift.TBaseProcessor;
import org.apache.zookeeper.CreateMode;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.Constant;

/**
 * 
 * ZooKeeper配置信息 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class RegisterConf {

	private int zookeeperPort = Constant.ZOOKEEPER_PORT;			//
	private String zookeeperAddress = Constant.ZOOKEEPER_ADDRESS;
	private int sessionTimeOut = 3000;
	private int connectionTimeOut = 3000;
	private int baseSleepTime = 1000;
	private int maxRetry = 290;
	private String serviceName;
	private boolean canBeReadOnly = false;
	private CreateMode createMode = CreateMode.EPHEMERAL;
	private String privatePath = "/servers/127.0.0.1:9090";
	private int servicePort = 9000;
	private TBaseProcessor<?> processor;
	
	public RegisterConf(TBaseProcessor<?> processor) throws InstantiationException {
		if(processor == null) {
			throw new InstantiationException();
		}
		ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
		this.processor = processor;
		String serviceName = configUtil.get("service_name", String.class);
		if(serviceName != null) {
			this.serviceName = serviceName;
		}
		String privatePath = configUtil.get("private_path", String.class);
		if(privatePath != null) {
			this.privatePath = privatePath;
		}
		Integer servicePort = configUtil.get("service_port", Integer.class);
		if(servicePort != null) {
			this.servicePort = servicePort;
		}
	}
	
	public int getZookeeperPort() {
		return zookeeperPort;
	}
	public void setZookeeperPort(int zookeeperPort) {
		this.zookeeperPort = zookeeperPort;
	}
	public String getZookeeperAddress() {
		return zookeeperAddress;
	}
	public void setZookeeperAddress(String zookeeperAddress) {
		this.zookeeperAddress = zookeeperAddress;
	}
	public int getSessionTimeOut() {
		return sessionTimeOut;
	}
	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}
	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}
	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}
	public int getBaseSleepTime() {
		return baseSleepTime;
	}
	public void setBaseSleepTime(int baseSleepTime) {
		this.baseSleepTime = baseSleepTime;
	}
	public int getMaxRetry() {
		return maxRetry;
	}
	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getConnectionAddress() {
		return zookeeperAddress+":"+zookeeperPort;
	}
	public boolean isCanBeReadOnly() {
		return canBeReadOnly;
	}
	public void setCanBeReadOnly(boolean canBeReadOnly) {
		this.canBeReadOnly = canBeReadOnly;
	}
	public CreateMode getCreateMode() {
		return createMode;
	}
	public void setCreateMode(CreateMode createMode) {
		this.createMode = createMode;
	}
	public String getPrivatePath() {
		return privatePath;
	}
	public void setPrivatePath(String privatePath) {
		this.privatePath = privatePath;
	}
	public int getServicePort() {
		return servicePort;
	}
	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}
	public TBaseProcessor<?> getProcessor() {
		return processor;
	}
	public void setProcessor(TBaseProcessor<?> processor) {
		this.processor = processor;
	}
}
