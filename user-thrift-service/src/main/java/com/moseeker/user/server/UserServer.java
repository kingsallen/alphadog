package com.moseeker.user.server;

import com.moseeker.user.service.UserServiceImpl;
import com.bfd.harpc.main.Server;

import com.bfd.harpc.config.RegistryConfig;
import com.bfd.harpc.config.ServerConfig;

import com.bfd.harpc.common.configure.PropertiesConfiguration;

/**
 * Created by zzh on 16/3/10.
 */

public class UserServer {

    /** 配置文件路径，配置说明参考 {@link Server#Server(String[] , Object )} */
    private static final String CONFIG_FILE_PATH = "classpath:user/user-server.properties";

    public static void main(String[] args){

//        PropertiesConfiguration configuration = PropertiesConfiguration.newInstance(CONFIG_FILE_PATH);
//
//        String address = configuration.getProperty("registry.connectstr");
//        String auth = configuration.getProperty("registry.auth");
//
//        Integer timeout = 5000;
//        String namespace = configuration.getProperty("server.namespace");
//
//        String userService = configuration.getProperty("server.service");
//        Integer port = Integer.valueOf(configuration.getProperty("server.port"));
//
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setConnectstr(address);
//        registryConfig.setAuth(auth);
//        registryConfig.setTimeout(5000);
//        registryConfig.setNamespace(namespace);
//
//        UserServiceImpl serviceImpl = new UserServiceImpl();
//
//        ServerConfig serverConfig = new ServerConfig();
//        serverConfig.setPort(19050);
//        serverConfig.setRef(serviceImpl);
//        serverConfig.setService(userService);
//
//        try {
//            serverConfig.export(registryConfig); // 暴露服务
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String address = "127.0.0.1:4181";
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr(address);
        registryConfig.setAuth("moseeker:moseeker");
        registryConfig.setTimeout(5000);
        registryConfig.setNamespace("moseeker_user");

        UserServiceImpl serviceImpl = new UserServiceImpl();

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(19050);
        serverConfig.setRef(serviceImpl);
        serverConfig.setService("com.moseeker.user.service.user$UserService");

        try {
            serverConfig.export(registryConfig); // 暴露服务
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
