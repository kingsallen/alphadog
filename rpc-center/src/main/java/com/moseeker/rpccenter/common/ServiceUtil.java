package com.moseeker.rpccenter.common;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;

/**
 * java客户端获取thrift service通用类
 * <p>
 *
 * Created by zzh on 16/4/7.
 */
public class ServiceUtil {

    private static final String ZOO_CONF_FILE = "classpath:zookeeper.properties";

    private static PropertiesConfiguration configuration = PropertiesConfiguration.newInstance(ZOO_CONF_FILE);

    private static RegistryConfig registryConfig = null;

    /**
     * 获取zookeeper配置信息
     * <p>
     *
     * @return
     */
    private static RegistryConfig getRegistryConfig(){
        if (registryConfig == null){
            registryConfig = new RegistryConfig();
            registryConfig.setConnectstr(configuration.getProperty("registry.connectstr", ""));
            registryConfig.setNamespace(configuration.getProperty("registry.namespace", ""));
        }
        return registryConfig;
    }

    /**
     * 获取thrift service实例
     * <p>
     *
     * @param clazz 需要实例的thrift service
     * @param <clazz>
     * @return thrift service实例
     */
    @Deprecated
    public static <clazz> clazz getService(Class<?> clazz){
        try{
            ClientConfig<clazz> clientConfig = new ClientConfig<clazz>();
            //clazz.getSimpleName();
            clientConfig.setService(clazz.getEnclosingClass().getName());
            clientConfig.setIface(clazz.getName());
            return clientConfig.createProxy(getRegistryConfig());
        }catch (Exception e){
            return null;
        }
    }
}
