package com.moseeker.rpccenter.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
	
	SERVICEMANAGER;

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
        	IfaceFactory<clazz>  ifaceFactory = null;
        	if(ifaceFactories.containsKey(clazz.getName())) {
        		ifaceFactory = ifaceFactories.get(clazz.getName());
        	} else {
        		ifaceFactory = new IfaceFactory<clazz>(config);
        		ifaceFactories.put(clazz.getName(), ifaceFactory);
        	}
            return ifaceFactory.createIface(clazz);
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
            IfaceFactory<clazz>  ifaceFactory = null;
            if(ifaceFactories.containsKey(serviceName)) {
                ifaceFactory = ifaceFactories.get(serviceName);
            } else {
                ifaceFactory = new IfaceFactory<clazz>(config);
                ifaceFactories.put(serviceName, ifaceFactory);
            }
            return ifaceFactory.createIface(clazz);
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

    private void init() {
        try {
            configUtils.loadResource("services.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int connectionTimeOut = configUtils.get("thrift.client.timeout", Integer.class, 60000);
        int initialBufferCapacity = configUtils.get("initialBufferCapacity", Integer.class, 1024);
        int maxLength = configUtils.get("maxLength", Integer.class, 1024*1024*1024);
        int maxActive = configUtils.get("maxActive", Integer.class, 1024);
        int maxIdle = configUtils.get("maxIdle", Integer.class, 100);
        int minIdle = configUtils.get("minIdle", Integer.class, 1);
        int maxWait = configUtils.get("maxWait", Integer.class, 10000);
        int timeBetweenEvictionRunsMillis = configUtils.get("timeBetweenEvictionRunsMillis", Integer.class, 180000);
        boolean testWhileIdle = configUtils.get("testWhileIdle", Boolean.class, false);
        int retry = configUtils.get("retry", Integer.class, 3);

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
                    IfaceFactory<Class>  ifaceFactory = new IfaceFactory<>(config);
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
    private Map<String, String> createServicesByConf(ConfigPropertiesUtil configUtils) {
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

    public static void main(String[] args) {
        String strTest = "servername.attachmentservices";
        System.out.println(strTest.substring(strTest.indexOf(".")+1));
    }
}
