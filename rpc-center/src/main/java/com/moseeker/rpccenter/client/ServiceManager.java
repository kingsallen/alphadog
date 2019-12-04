package com.moseeker.rpccenter.client;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.rpccenter.config.ThriftServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 服务中心的业务管理工具（iface管理工具）
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum ServiceManager {

    /**
     * 服务管理对象
     */
	SERVICE_MANAGER;

	private Logger logger = LoggerFactory.getLogger(ServiceManager.class);
	
	private ThriftServerConfig config = new ThriftServerConfig();
	@SuppressWarnings("rawtypes")
	private HashMap<String, IfaceFactory> ifaceFactories = new HashMap<>();
	private ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();

	private ServiceManager() {
        init();
    } 


    /**
     * 获取thrift service实例
     * <p>
     *
     * @param clazz 需要实例的thrift service
     * @param <clazz>
     * @return thrift service实例
     */
    @SuppressWarnings("unchecked")
	public <clazz> clazz getService(Class<clazz> clazz){
        try{
        	IfaceFactory<clazz>  ifaceFactory = getIfaceFactory(clazz);
        	if(ifaceFactory == null) {
                ifaceFactory = new IfaceFactory<>(config, clazz.getName());
                ifaceFactories.put(clazz.getName(), ifaceFactory);
            }
            return ifaceFactory.createIface(clazz, getServerName(clazz));
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }
    }



    /**
     * 获取thrift client实例
     * @param clazz 服务接口
     * @param serviceName 服务名称
     * @param <clazz> 实例类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <clazz> clazz getService(Class<clazz> clazz, String serviceName){

        try{
            IfaceFactory<clazz>  ifaceFactory = getIfaceFactory(serviceName);
            if(ifaceFactory == null) {
                ifaceFactory = new IfaceFactory<clazz>(config, clazz.getName());
                ifaceFactories.put(serviceName, ifaceFactory);
            }
            return ifaceFactory.createIface(clazz, serviceName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 清空IfaceFactory中rpc客户端对象池的数据
     */
    protected void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (ifaceFactories != null && ifaceFactories.size() > 0) {
                	for(@SuppressWarnings("rawtypes") Entry<String, IfaceFactory> entry : ifaceFactories.entrySet()) {
                		entry.getValue().clear();
                	}
                }
            }
        }));
    }

    /**
     * 使用thrift定义的名字小写作为服务名称
     * @param clazz
     * @return
     */
    private String getServerName(Class clazz) {
        String iface = clazz.getName();
        if (iface.contains("$")) {
            return iface.substring(iface.lastIndexOf(".")+1, iface.indexOf("$")).toLowerCase();
        }
        return iface;
    }

    private IfaceFactory getIfaceFactory(Class clazz) {
        IfaceFactory ifaceFactory = null;
        for(Entry<String, IfaceFactory> entry : ifaceFactories.entrySet()) {
            if(clazz.getName().equals(entry.getValue().getServerName())) {
                ifaceFactory = entry.getValue();
                break;
            }
        }
        return ifaceFactory;
    }

    private IfaceFactory getIfaceFactory(String serverName) {
        IfaceFactory ifaceFactory = null;
        for(Entry<String, IfaceFactory> entry : ifaceFactories.entrySet()) {
            if(serverName.equals(entry.getValue().getServerName())) {
                ifaceFactory = entry.getValue();
                break;
            }
        }
        return ifaceFactory;
    }

    private void init() {
        try {
            configUtils.loadResource("service.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int connectionTimeOut = configUtils.get("thrift.timeout", Integer.class, 60000);
        int initialBufferCapacity = configUtils.get("thrift.initialBufferCapacity", Integer.class, 1024);
        int retry = configUtils.get("thrift.retry", Integer.class, 3);
        int maxLength = configUtils.get("thrift.maxLength", Integer.class, 1024*1024*1024);

        int maxActive = configUtils.get("objectpool.maxActive", Integer.class, 1024);
        int maxIdle = configUtils.get("objectpool.maxIdle", Integer.class, 100);
        int minIdle = configUtils.get("objectpool.minIdle", Integer.class, 1);
        int maxWait = configUtils.get("objectpool.maxWait", Integer.class, 10000);
        int timeBetweenEvictionRunsMillis = configUtils.get("objectpool.timeBetweenEvictionRunsMillis", Integer.class, 180000);
        boolean testWhileIdle = configUtils.get("objectpool.testWhileIdle", Boolean.class, false);

        config.setRetry(retry);
        config.setTimeout(connectionTimeOut);
        config.setInitialBufferCapacity(initialBufferCapacity);
        config.setMaxLength(maxLength);
        config.setMaxActive(maxActive);
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxWait(maxWait);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        config.setTestWhileIdle(testWhileIdle);
        Map<String, String> services = createServicesByConf(configUtils);
        if(services != null && services.size() > 0) {
            services.forEach((serverName, className) -> {
                if(!ifaceFactories.containsKey(serverName)) {
                    IfaceFactory ifaceFactory = new IfaceFactory<>(config, className);
                    ifaceFactories.put(serverName, ifaceFactory);
                }
            });
        }

    }

    /**
     * 通过配置信息生成 服务名称和服务实现类对应关系
     * @param configUtils
     * @return 服务名称和服务实现类对应关系
     */
    private Map<String, String>  createServicesByConf(ConfigPropertiesUtil configUtils) {
        Map<String, String> servers = new HashMap<>();

        Set<Object> keys = configUtils.returnKeys();
        if(keys != null && keys.size() > 0) {
            keys = keys.stream().filter(key -> ((String)key).startsWith("servername.")).collect(Collectors.toSet());
            for(Object obj : keys) {
                String className = configUtils.get((String)obj, String.class);
                servers.put((String)obj, className);
            }
        }

        return servers;
    }
}
