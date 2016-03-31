package com.moseeker.servicemanager.web.service;

import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;

/**
 * Created by zzh on 16/3/30.
 */
public class BaseService<T> {

    ClientConfig<T> clientConfig = new ClientConfig<T>();

    private RegistryConfig getRegistryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:4181");
        registryConfig.setNamespace("moseeker");
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
