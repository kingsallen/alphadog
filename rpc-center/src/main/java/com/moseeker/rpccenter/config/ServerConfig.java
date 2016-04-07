package com.moseeker.rpccenter.config;

import java.lang.reflect.Constructor;

import org.apache.commons.lang.StringUtils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.thrift.TProcessor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.rpccenter.server.IServer;
import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.server.thrift.ThriftServer;
import com.moseeker.rpccenter.proxy.DynamicServiceHandler;
import com.moseeker.rpccenter.common.NetUtils;

import com.moseeker.rpccenter.registry.IRegistry;
import com.moseeker.rpccenter.registry.ZkServerRegistry;

/**
 * Created by zzh on 16/3/28.
 */
public class ServerConfig implements IConfigCheck {

    /** LOGGER */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /** 服务名 */
    private String name;

    /** 服务负责人 */
    private String owner;

    /** 端口，默认是19090 */
    private int port = 19090;

    /** 协议 */
    private String protocol = "thrift";

    /** 权重,默认为1 */
    @JSONField(serialize = false)
    private int weight = 1;

    /** 服务ip地址，可以为空，为空时从网卡获取 */
    private String ip;

    /** 服务实实现类 */
    @JSONField(serialize = false)
    private Object ref;

    /** 服务名(全称)：命名空间$服务名简称 */
    private String service;

    /** 监控时间间隔，单位为:s，默认为5min */
    private int interval = 5 * 60;

    /** 最大工作线程数，默认为{@link Integer#MAX_VALUE} */
    private int maxWorkerThreads = Integer.MAX_VALUE;

    /** 最小工作线程数 ,默认为10 */
    private int minWorkerThreads = 10;

    /** {@link IRegistry} */
    private IRegistry registry;

    /** {@link IServer} */
    private IServer server;

    /**
     * 暴露服务
     * <p>
     *
     * @throws ClassNotFoundException
     *             ,RpcException
     */
    public void export(RegistryConfig registryConfig) throws ClassNotFoundException, RpcException {

        this.setService(this.genServiceName());

        // 参数检查
        check();

        // 创建注册中心
        ServerNode serverNode = genServerNode();
        IRegistry registry = null;
        if (registryConfig != null) {
            CuratorFramework zkClient = registryConfig.obtainZkClient();
            registry = new ZkServerRegistry(zkClient, service, serverNode.genAddress(), registryConfig.getAuth());
        }

        // 创建服务
        IServer server = createServer(serverNode);
        server.start();

        if (server.isStarted()) {

            this.server = server;
            this.registry = registry;

            try {
                // 服务注册
                registry.register(genConfigJson());

                // 添加关闭钩子
                addShutdownHook(registry, server);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                server.stop();
            }
        } else {
            server.stop();
        }
    }

    /**
     * 创建服务
     * <p>
     *
     * @param serverNode
     * @return {@link IServer}
     * @throws ClassNotFoundException
     */
    protected IServer createServer(ServerNode serverNode) throws ClassNotFoundException {
        IServer server = null;
        if (StringUtils.equalsIgnoreCase(protocol, "thrift")) {
            TProcessor processor = reflectProcessor(serverNode);
            server = new ThriftServer(processor, serverNode, maxWorkerThreads, minWorkerThreads);
        } else {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "Unsupport protocal,please check the params 'protocal'!");
        }

        return server;
    }

    /**
     * 反射TProcessor
     * <p>
     *
     * @param serverNode
     * @return TProcessor
     */
    @SuppressWarnings("rawtypes")
    protected TProcessor reflectProcessor(ServerNode serverNode) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Class serviceClass = getRef().getClass();
        Class<?>[] interfaces = serviceClass.getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException("Service class should implements Iface!");
        }

        // 反射,load "Processor";
        TProcessor processor = null;
        for (Class clazz : interfaces) {
            String cname = clazz.getSimpleName();
            if (!cname.equals("Iface")) {
                continue;
            }
            String pname = clazz.getEnclosingClass().getName() + "$Processor";
            try {
                Class<?> pclass = classLoader.loadClass(pname);
                Constructor constructor = pclass.getConstructor(clazz);
                processor = (TProcessor) constructor.newInstance(getProxy(classLoader, clazz, getRef(), serverNode));
            } catch (Exception e) {
                throw new RpcException("Refact error,please check your thift gen class!", e.getCause());
            }
        }

        if (processor == null) {
            throw new RpcException("Service class should implements $Iface!");
        }
        return processor;
    }

    /**
     * 获取处理类代理
     * <p>
     *
     * @param classLoader
     * @param interfaces
     * @param object
     * @param serverNode
     * @return 处理类代理
     * @throws ClassNotFoundException
     */
    private Object getProxy(ClassLoader classLoader, Class<?> interfaces, Object object, ServerNode serverNode) throws ClassNotFoundException {
        DynamicServiceHandler dynamicServiceHandler = new DynamicServiceHandler();
        return dynamicServiceHandler.bind(classLoader, interfaces, object, serverNode);
    }

    /**
     * 生成服务名称
     * <p>
     *
     * @return 服务名称
     * @throws ClassNotFoundException
     */
    protected String genServiceName() {

        Class serviceClass = getRef().getClass();
        Class<?>[] interfaces = serviceClass.getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException("Service class should implements Iface!");
        }

        for (Class clazz : interfaces) {
            String cname = clazz.getSimpleName();
            if (!cname.equals("Iface")) {
                continue;
            }
            return clazz.getEnclosingClass().getName();
        }

        return null;
    }

    @Override
    public void check() throws RpcException {
        if (StringUtils.isEmpty(service)) {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'service' cannot empty!");
        }
        if (interval < 60) {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'interval' must >= 60!");
        }
    }

    /**
     * 生成 {@link ServerNode}
     * <p>
     *
     * @return {@link ServerNode}
     */
    protected ServerNode genServerNode() {
        String ip = null;
        if (StringUtils.isNotEmpty(getIp())) {
            ip = getIp();
        } else {
            ip = NetUtils.getLocalHost();
        }
        if (ip == null) {
            throw new RpcException("Can't find server ip!");
        }
        return new ServerNode(ip, getPort());
    }

    /**
     * 生成配置文件的json格式
     * <p>
     *
     * @return 配置的json格式
     */
    protected String genConfigJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 添加关闭钩子
     * <p>
     *
     * @param registry
     * @param server
     */
    protected void addShutdownHook(final IRegistry registry, final IServer server) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (registry != null) {
                    registry.unregister();
                }
                if (server != null) {
                    server.stop();
                }
            }
        }));
    }

    /**
     * 销毁资源<br/>
     * 包括：释放注册中心连接、停止服务。
     * <p>
     */
    public void destory() {
        if (registry != null) {
            registry.unregister();
        }
        if (server != null) {
            server.stop();
        }
    }

    /**
     * getter method
     *
     * @see ServerConfig#name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method
     *
     * @see ServerConfig#name
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method
     *
     * @see ServerConfig#owner
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * setter method
     *
     * @see ServerConfig#owner
     * @param owner
     *            the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * getter method
     *
     * @see ServerConfig#port
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * setter method
     *
     * @see ServerConfig#port
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * getter method
     *
     * @see ServerConfig#protocol
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * setter method
     *
     * @see ServerConfig#protocol
     * @param protocol
     *            the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * getter method
     *
     * @see ServerConfig#weight
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * setter method
     *
     * @see ServerConfig#weight
     * @param weight
     *            the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * getter method
     *
     * @see ServerConfig#ip
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * setter method
     *
     * @see ServerConfig#ip
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * getter method
     *
     * @see ServerConfig#ref
     * @return the ref
     */
    public Object getRef() {
        return ref;
    }

    /**
     * setter method
     *
     * @see ServerConfig#ref
     * @param ref
     *            the ref to set
     */
    public void setRef(Object ref) {
        this.ref = ref;
    }

    /**
     * getter method
     *
     * @see ServerConfig#service
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * setter method
     *
     * @see ServerConfig#service
     * @param service
     *            the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * getter method
     *
     * @see ServerConfig#interval
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * setter method
     *
     * @see ServerConfig#interval
     * @param interval
     *            the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * getter method
     *
     * @see ServerConfig#maxWorkerThreads
     * @return the maxWorkerThreads
     */
    public int getMaxWorkerThreads() {
        return maxWorkerThreads;
    }

    /**
     * setter method
     *
     * @see ServerConfig#maxWorkerThreads
     * @param maxWorkerThreads
     *            the maxWorkerThreads to set
     */
    public void setMaxWorkerThreads(int maxWorkerThreads) {
        this.maxWorkerThreads = maxWorkerThreads;
    }

    /**
     * getter method
     *
     * @see ServerConfig#minWorkerThreads
     * @return the minWorkerThreads
     */
    public int getMinWorkerThreads() {
        return minWorkerThreads;
    }

    /**
     * setter method
     *
     * @see ServerConfig#minWorkerThreads
     * @param minWorkerThreads
     *            the minWorkerThreads to set
     */
    public void setMinWorkerThreads(int minWorkerThreads) {
        this.minWorkerThreads = minWorkerThreads;
    }

}
