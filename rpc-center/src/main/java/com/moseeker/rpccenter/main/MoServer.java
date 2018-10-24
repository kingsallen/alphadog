package com.moseeker.rpccenter.main;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.config.ServerData;
import com.moseeker.rpccenter.config.ThriftConfig;
import com.moseeker.rpccenter.config.ZKConfig;
import com.moseeker.rpccenter.exception.IncompleteException;
import com.moseeker.rpccenter.exception.RegisterException;
import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.registry.ServerRegistry;
import com.moseeker.rpccenter.server.IServer;
import com.moseeker.rpccenter.server.thrift.ThriftServerRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * 服务管理中心。主要提供thrift服务创建和服务注册功能
 * Created by jack on 06/02/2017.
 */
public class MoServer {

    //Instance(String configName, Class... entities) throws ;
    Logger logger = LoggerFactory.getLogger(MoServer.class);

    /* 配置信息 */
    private ThriftConfig thriftConfig;                          //thrift服务配置信息
    private ZKConfig zkConfig;                                  //zk配置
    private ServerData serverData;                              //节点信息

    private IServer server;                                     //thrift服务
    private ServerRegistry serverRegistry;                      //zookeeper节点注册服务

    private ConfigHelper configHelper = new ConfigHelper();
    private String configName = "service.properties";           //配置文件名称
    private Object[] impls;                                     //服务实现类

    public MoServer(AnnotationConfigApplicationContext acac) throws ClassNotFoundException, IncompleteException,
            RpcException, BeansException {
        initConfig(acac);
    };

    public MoServer(AnnotationConfigApplicationContext acac, String configName, Object... impls)
            throws ClassNotFoundException, IncompleteException, RpcException, BeansException {
        if(StringUtils.isNotNullOrEmpty(configName)) {
            this.configName = configName;
        }
        this.impls = impls;
        initConfig(acac);
    }

    /**
     * 启动Alphadog服务
     * @throws IncompleteException 配置文件定义的类不存在
     * @throws RpcException 传输通道建立失败
     * @throws RegisterException 连接zookeeper失败
     */
    public void startServer() throws IncompleteException, RpcException, RegisterException {
        createServer();
        registerServer();
    }

    /**
     * 是否启动Alphadog服务
     * @return true 启动; false 未启动
     */
    public boolean isStart() {
        return server.isStarted();
    }

    /**
     * 停止服务并释放资源
     */
    public void stopServer() {
        logger.info("stopServer start unRegisterServer");
        unRegisterServer();
        logger.info("stopServer end unRegisterServer");
        try {
            logger.info("stopServer start sleep 30s");
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            logger.info("stopServer sleep ok");
        }

        logger.info("stopServer start destroyServer");
        destroyServer();
        logger.info("stopServer end destroyServer");


    }

    /**
     * 创建thrift服务
     * @throws IncompleteException 配置信息不全，可能会引起服务无法正常初始化
     * @throws RpcException 传输通道建立失败
     */
    private void createServer() throws IncompleteException, RpcException {
        if(server == null) {
            server = new ThriftServerRegister(this.thriftConfig);
        }
        if(!server.isStarted()) {
            server.start();
        }
        logger.info("Server is started! IP : {} Port : {}", thriftConfig.getIP(), thriftConfig.getPort());
    }

    private void destroyServer() {
        if(server != null) {
            server.stop();
        }
        server = null;
    }

    /**
     * 注册zookeeper服务
     * @throws RegisterException 连接zookeeper失败
     * @throws IncompleteException 配置信息不全，可能会引起服务无法正常初始化
     */
    private void registerServer() throws RegisterException, IncompleteException {
        if(serverRegistry == null) {
            serverRegistry = new ServerRegistry(zkConfig, serverData);
        }
        serverRegistry.register();
    }

    /**
     * 删除服务节点并删除分支
     */
    private void unRegisterServer() {
        if(serverRegistry != null) {
            serverRegistry.unRegister();
        }
        serverRegistry = null;
    }

    /**
     * 初始化配置信息
     * @throws ClassNotFoundException 配置文件定义的类不存在
     * @throws IncompleteException 无法正常加载server.properties配置文件
     * @throws org.springframework.beans.BeansException 类找不着
     */
    private void initConfig(AnnotationConfigApplicationContext acac) throws ClassNotFoundException,
            IncompleteException, org.springframework.beans.BeansException {
        //List<Object> clazzs = Arrays.asList(impls).stream().map(obj -> obj.getClass()).collect(Collectors.toList());
        configHelper.initConfig(acac, configName, Arrays.asList(impls));
        this.thriftConfig = configHelper.getThriftConfig();
        this.zkConfig = configHelper.getZkConfig();
        this.serverData = configHelper.getServerData();
    }

    /**
     * 添加一个钩子，在进程被停止时关闭资源
     */
    public void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(()-> {
            logger.info("----shutDownHook----");
            stopServer();
        }));
    }
}
