package com.moseeker.rpccenter.client;

import java.util.HashMap;
import java.util.Map.Entry;

import com.moseeker.rpccenter.config.ThriftServerConfig;
import com.moseeker.rpccenter.registry.IRegistry;

public enum ServiceManager {
	
	SERVICEMANAGER;
	
	private ThriftServerConfig config = new ThriftServerConfig();
	@SuppressWarnings("rawtypes")
	private HashMap<String, IfaceFactory> ifaceFactories = new HashMap<>();
	
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
     * 添加关闭钩子 -  无心跳
     * <p>
     *
     * @param registry
     */
    protected void addShutdownHook(final IRegistry registry) {
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
}
