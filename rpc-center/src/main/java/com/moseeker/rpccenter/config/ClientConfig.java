package com.moseeker.rpccenter.config;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;

import com.alibaba.fastjson.JSON;

import com.moseeker.rpccenter.common.NetUtils;
import com.moseeker.rpccenter.registry.IRegistry;
import com.moseeker.rpccenter.common.ServerNode;
import com.moseeker.rpccenter.exception.RpcException;

import com.moseeker.rpccenter.registry.ZkClientRegistry;
import com.moseeker.rpccenter.client.Invoker;
import com.moseeker.rpccenter.client.DefaultInvoker;
import com.moseeker.rpccenter.proxy.DynamicClientHandler;

import com.moseeker.rpccenter.loadbalance.common.DynamicHostSet;
import com.moseeker.rpccenter.pool.TServiceClientPoolFactory;

/**
 * Created by zzh on 16/3/30.
 */
public class ClientConfig<T> implements IConfigCheck{

    /** 服务名 */
    private String name;

    /** 服务负责人 */
    private String owner;

    /** 服务名(全称)：命名空间$服务名简称 */
    private String service;

    /** 服务地址，使用这个参数表示使用的直连的方式 */
    private String address;

    /** 重试次数，默认1次 */
    private int retry = 1;

    /** 完整接口名 */
    private String iface;

    /** 协议 */
    private String protocol = "thrift";

    /** 是否开启监控 */
    private boolean monitor;

    /** 监控时间间隔，单位为:s，默认为5min */
    private int interval = 5 * 60;

    /** 负载均衡策略，默认为round，可选：round和random */
    private String loadbalance = "round";

    // 下面的配置项是连接池的基本配置
    /** 超时时间，单位为ms，默认为3s */
    private int timeout = 3000;

    /** 最大活跃连接数 */
    private int maxActive = 1024;

    /** 链接池中最大空闲的连接数,默认为100 */
    private int maxIdle = 100;

    /** 连接池中最少空闲的连接数,默认为0 */
    private int minIdle = 0;

    /** 当连接池资源耗尽时，调用者最大阻塞的时间 */
    private int maxWait = 2000;

    /** 空闲链接”检测线程，检测的周期，毫秒数，默认位3min，-1表示关闭空闲检测 */
    private int timeBetweenEvictionRunsMillis = 180000;

    /** 空闲时是否进行连接有效性验证，如果验证失败则移除，默认为false */
    private boolean testWhileIdle = false;

    /** {@link IRegistry} */
    private IRegistry registry;

    /** {@link GenericKeyedObjectPool} */
    private GenericKeyedObjectPool<ServerNode, T> pool;

    /**
     * 创建代理
     * <p>
     *
     * @param registryConfig
     * @return {client代理}
     * @throws Exception
     */
    public T createProxy(RegistryConfig registryConfig) throws Exception {
        check(); // 参数检查

        // 注册
        IRegistry registry;
        ServerNode clientNode = new ServerNode(NetUtils.getLocalHost(), 0);
        if (registryConfig != null) {
            registry = new ZkClientRegistry(service, registryConfig.obtainZkClient(), clientNode);
        } else {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'addess' and '<registry>' node cannot all unexist!");
        }

        registry.register(genConfigJson());


        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 加载Iface接口
        Class<?> objectClass = classLoader.loadClass(iface);

        // 创建ThriftClient连接池
        GenericKeyedObjectPool<ServerNode, T> pool = bulidClientPool(classLoader, objectClass);
        DynamicHostSet hostSet = registry.findAllService();

        this.registry = registry;
        this.pool = pool;

        // 添加ShutdownHook
        addShutdownHook(registry);

        Invoker invoker = new DefaultInvoker<T>(clientNode, pool, retry, hostSet);
        DynamicClientHandler dynamicClientHandler = new DynamicClientHandler(invoker);
        return dynamicClientHandler.<T> bind(classLoader, objectClass);
    }

    /**
     * 构建client对象池
     * <p>
     *
     * @param classLoader
     * @param ifaceClass
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    protected GenericKeyedObjectPool<ServerNode, T> bulidClientPool(ClassLoader classLoader, Class<?> ifaceClass) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        // 设置poolConfig
        GenericKeyedObjectPool.Config poolConfig = new GenericKeyedObjectPool.Config();
        poolConfig.maxActive = maxActive;
        poolConfig.maxIdle = maxIdle;
        poolConfig.minIdle = minIdle;
        poolConfig.maxWait = maxWait;
        poolConfig.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        poolConfig.testWhileIdle = testWhileIdle;

        if (StringUtils.equalsIgnoreCase(protocol, "thrift")) {
            // 加载Client.Factory类
            Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader.loadClass(findOutClassName() + "$Client$Factory");
            TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();
            TServiceClientPoolFactory<T> clientPool = new TServiceClientPoolFactory<T>(clientFactory, timeout);

            return new GenericKeyedObjectPool<ServerNode, T>(clientPool, poolConfig);
        } else {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "Unsupport protocal,please check the params 'protocal'!");
        }
    }

    /**
     * 获取外部内的类名
     * <p>
     *
     * @return 类名
     */
    private String findOutClassName() {
        if (iface.contains("$")) {
            return iface.substring(0, iface.indexOf("$"));
        }
        return iface;
    }

    @Override
    public void check() throws RpcException {
        if (StringUtils.isEmpty(service)) {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'service' cannot empty!");
        }
        if (StringUtils.isEmpty(iface)) {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'iface' cannot empty!");
        }
        if (interval < 60) {
            throw new RpcException(RpcException.CONFIG_EXCEPTION, "The params 'interval' must >= 60!");
        }
    }

    /**
     * 销毁资源<br/>
     * 包括：释放注册中心连接、heartbeat等资源。
     * <p>
     */
    public void destory() {
        if (registry != null) {
            registry.unregister();
        }
        if (pool != null) {
            pool.clear();
        }
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
     */
    protected void addShutdownHook(final IRegistry registry) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (registry != null) {
                    registry.unregister();
                }
                if (pool != null) {
                    pool.clear();
                }
            }
        }));
    }

    /**
     * getter method
     *
     * @see ClientConfig#name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method
     *
     * @see ClientConfig#name
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method
     *
     * @see ClientConfig#owner
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * setter method
     *
     * @see ClientConfig#owner
     * @param owner
     *            the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * getter method
     *
     * @see ClientConfig#service
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * setter method
     *
     * @see ClientConfig#service
     * @param service
     *            the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * getter method
     *
     * @see ClientConfig#address
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter method
     *
     * @see ClientConfig#address
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter method
     *
     * @see ClientConfig#retry
     * @return the retry
     */
    public int getRetry() {
        return retry;
    }

    /**
     * setter method
     *
     * @see ClientConfig#retry
     * @param retry
     *            the retry to set
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }

    /**
     * getter method
     *
     * @see ClientConfig#iface
     * @return the iface
     */
    public String getIface() {
        return iface;
    }

    /**
     * setter method
     *
     * @see ClientConfig#iface
     * @param iface
     *            the iface to set
     */
    public void setIface(String iface) {
        this.iface = iface;
    }

    /**
     * getter method
     *
     * @see ClientConfig#protocol
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * setter method
     *
     * @see ClientConfig#protocol
     * @param protocol
     *            the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * getter method
     *
     * @see ClientConfig#monitor
     * @return the monitor
     */
    public boolean isMonitor() {
        return monitor;
    }

    /**
     * setter method
     *
     * @see ClientConfig#monitor
     * @param monitor
     *            the monitor to set
     */
    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    /**
     * getter method
     *
     * @see ClientConfig#interval
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * setter method
     *
     * @see ClientConfig#interval
     * @param interval
     *            the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * getter method
     *
     * @see ClientConfig#loadbalance
     * @return the loadbalance
     */
    public String getLoadbalance() {
        return loadbalance;
    }

    /**
     * setter method
     *
     * @see ClientConfig#loadbalance
     * @param loadbalance
     *            the loadbalance to set
     */
    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    /**
     * getter method
     *
     * @see ClientConfig#timeout
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * setter method
     *
     * @see ClientConfig#timeout
     * @param timeout
     *            the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * getter method
     *
     * @see ClientConfig#maxActive
     * @return the maxActive
     */
    public int getMaxActive() {
        return maxActive;
    }

    /**
     * setter method
     *
     * @see ClientConfig#maxActive
     * @param maxActive
     *            the maxActive to set
     */
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    /**
     * getter method
     *
     * @see ClientConfig#maxIdle
     * @return the maxIdle
     */
    public int getMaxIdle() {
        return maxIdle;
    }

    /**
     * setter method
     *
     * @see ClientConfig#maxIdle
     * @param maxIdle
     *            the maxIdle to set
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * getter method
     *
     * @see ClientConfig#minIdle
     * @return the minIdle
     */
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * setter method
     *
     * @see ClientConfig#minIdle
     * @param minIdle
     *            the minIdle to set
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * getter method
     *
     * @see ClientConfig#maxWait
     * @return the maxWait
     */
    public int getMaxWait() {
        return maxWait;
    }

    /**
     * setter method
     *
     * @see ClientConfig#maxWait
     * @param maxWait
     *            the maxWait to set
     */
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    /**
     * getter method
     *
     * @see ClientConfig#timeBetweenEvictionRunsMillis
     * @return the timeBetweenEvictionRunsMillis
     */
    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    /**
     * setter method
     *
     * @see ClientConfig#timeBetweenEvictionRunsMillis
     * @param timeBetweenEvictionRunsMillis
     *            the timeBetweenEvictionRunsMillis to set
     */
    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * getter method
     *
     * @see ClientConfig#testWhileIdle
     * @return the testWhileIdle
     */
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    /**
     * setter method
     *
     * @see ClientConfig#testWhileIdle
     * @param testWhileIdle
     *            the testWhileIdle to set
     */
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

}
