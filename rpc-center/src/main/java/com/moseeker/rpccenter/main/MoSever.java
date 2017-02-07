package com.moseeker.rpccenter.main;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ThriftConfig;
import com.moseeker.rpccenter.config.ZKConfig;
import com.moseeker.rpccenter.registry.ServerRegistry;
import com.moseeker.rpccenter.server.IServer;

/**
 * 服务管理中心
 * Created by jack on 06/02/2017.
 */
public enum MoSever {

    INSTANCE;

    /* 配置信息 */
    private ThriftConfig thriftConfig;          //thrift服务配置信息
    private ZKConfig zkConfig;                  //zk配置
    private ServerData serverData;              //节点信息

    private IServer server;                     //thrift服务
    private ServerRegistry serverRegistry;      //zookeeper节点注册服务


    private void initConfig() {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("server.properties");

            //String
            ThriftConfig.Builder thriftBuilder = new ThriftConfig.Builder();
            //thriftBuilder.
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
