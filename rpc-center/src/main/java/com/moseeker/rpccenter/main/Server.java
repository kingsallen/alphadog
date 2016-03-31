package com.moseeker.rpccenter.main;

import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.rpccenter.config.ServerConfig;
import com.moseeker.rpccenter.exception.RpcException;

import com.moseeker.rpccenter.common.configure.PropertiesConfiguration;

/**
 * Created by zzh on 16/3/29.
 */
public class Server {

    /** {@link RegistryConfig} */
    private final RegistryConfig registryConfig;

    /** {@link ServerConfig} */
    private final ServerConfig serverConfig;

    /**
     * 配置文件路径说明：<br>
     * 1. 使用file，classpath和classpath*做路径开头 <br>
     * 2. classpath寻址项目中的文件 <br>
     * 3. classpath*既寻址项目，也寻址jar包中的文件 <br>
     * 4. file寻址文件系统中的文件:如：D:\\config.properties,/etc/config. properties <br>
     * 5. 默认是classpath <br>
     * 6. 例如：classpath*:log/log4j.xml;file:/home/moseeker/abc.sh;classpath:log/log4j.
     * xml
     * <p>
     *
     * @param configFile
     *            配置文件
     * @param impl
     *            接口具体实现类
     * @throws RpcException
     */
    public Server(String configFile, Object impl) throws RpcException {
        PropertiesConfiguration configuration = PropertiesConfiguration.newInstance(configFile);

        // 初始化registry
        registryConfig = new RegistryConfig();
        ConfigHelper.initConfig(registryConfig, "registry.", configuration);

        // 初始化server
        serverConfig = new ServerConfig();
        serverConfig.setRef(impl);
        ConfigHelper.initConfig(serverConfig, "server.", configuration);
    }

    /**
     * 启动服务 <br>
     * <b>注意:</b>本函数属于异步启动，如需要保持服务一直运行，需要主动阻塞主线程。 <br>
     *
     * <pre>
     * 阻塞方法可参考如下方式：
     * <code>
     *  synchronized (ProfileServiceMain.class) {
     *                 while (running) {
     *                     try {
     *                         ProfileServiceMain.class.wait();
     *                     } catch (Throwable e) {
     *                     }
     *                 }
     *             }
     * </code>
     * </pre>
     * <p>
     *
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    public void start() throws ClassNotFoundException, RpcException {
        serverConfig.export(registryConfig);
    }

    /**
     * (显式)关闭服务<br>
     * <b>注意:</b>Server启动时，会在addShutdownHook中添加关闭事件，所以使用kill关闭程序时是不需要调用close的。
     * 这里主要提供给需要在程序中显示关闭服务情形，这时可以显式调用close关闭服务。
     * <p>
     */
    public void close() {
        serverConfig.destory();
    }

}
