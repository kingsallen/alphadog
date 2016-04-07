package com.moseeker.servicemanager.web.controller;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import org.springframework.stereotype.Controller;

/**
 * Created by zzh on 16/4/7.
 */
@Controller
public class BaseController<T> {

    public static final String ZOO_CONF_FILE = "classpath:zoo.properties";

    PropertiesConfiguration configuration = PropertiesConfiguration.newInstance(ZOO_CONF_FILE);

    ClientConfig<T> clientConfig = new ClientConfig<T>();

    private RegistryConfig registryConfig;

    private RegistryConfig getRegistryConfig(){
        registryConfig = new RegistryConfig();
        registryConfig.setConnectstr(configuration.getProperty("registry.connectstr", ""));
        registryConfig.setNamespace(configuration.getProperty("registry.namespace", ""));
        return registryConfig;
    }

    public T getService(final String serviceName, final String ifaceName){
        try{
            clientConfig.setService(serviceName);
            clientConfig.setIface(ifaceName);
            return clientConfig.createProxy(getRegistryConfig());
        }catch (Exception e){
            return null;
        }
    }

}
