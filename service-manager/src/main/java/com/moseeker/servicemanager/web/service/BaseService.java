package com.moseeker.servicemanager.web.service;

import org.springframework.stereotype.Service;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;

/**
 * 获取Thrift服务实例
 * Created by zzh on 16/3/30.
 */
@Service
public class BaseService<T> {

    public static final String ZOO_CONF_FILE = "classpath:zoo.properties";

    PropertiesConfiguration configuration = PropertiesConfiguration.newInstance(ZOO_CONF_FILE);

    ClientConfig<T> clientConfig = new ClientConfig<T>();

    private RegistryConfig getRegistryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr(configuration.getProperty("registry.connectstr", ""));
        registryConfig.setNamespace(configuration.getProperty("registry.namespace", ""));
        return registryConfig;
    }

    public T getService(final String serviceName, String ifaceName){
        try{
            clientConfig.setService(serviceName);
            clientConfig.setIface(ifaceName);
            return clientConfig.createProxy(getRegistryConfig());
        }catch (Exception e){
            return null;
        }
    }
}
