package com.moseeker.rpccenter.common;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;

/**
 * Created by zzh on 16/4/7.
 */
public class ServiceUtil {

    public static final String ZOO_CONF_FILE = "classpath:zookeeper.properties";

    private static PropertiesConfiguration configuration = PropertiesConfiguration.newInstance(ZOO_CONF_FILE);

    private static RegistryConfig registryConfig;

    private static RegistryConfig getRegistryConfig(){
        registryConfig = new RegistryConfig();
        registryConfig.setConnectstr(configuration.getProperty("registry.connectstr", ""));
        registryConfig.setNamespace(configuration.getProperty("registry.namespace", ""));
        return registryConfig;
    }

    public static <clazz> clazz getService(Class<?> clazz){
        try{
            ClientConfig<clazz> clientConfig = new ClientConfig<clazz>();
            clazz.getSimpleName();
            clientConfig.setService(clazz.getEnclosingClass().getName());
            clientConfig.setIface(clazz.getName());
            return clientConfig.createProxy(getRegistryConfig());
        }catch (Exception e){
            return null;
        }
    }
}
