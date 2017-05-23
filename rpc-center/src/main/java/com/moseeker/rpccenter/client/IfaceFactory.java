package com.moseeker.rpccenter.client;

import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.config.ThriftServerConfig;
import com.moseeker.rpccenter.listener.ZKPath;
import com.moseeker.rpccenter.pool.TMultiServicePoolFactory;
import com.moseeker.rpccenter.proxy.DynamicClientHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * rpc服务客户端工厂
 * <p>Company: MoSeeker</P>
 * <p>date: Jul 27, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @param <T>
 * @author wjf
 */
public class IfaceFactory<T> {

    private ThriftServerConfig config;                        //配置信息
    private GenericKeyedObjectPool<ZKPath, T> pool = null;    //节点对象池
    private String serverName;

    private Set<Class> chaosRelatedService = new HashSet<>();

    /**
     * 初始化thrift客户端工厂
     *
     * @param config
     */
    public IfaceFactory(ThriftServerConfig config, String serverName) {
        this.config = config;
        this.serverName = serverName;
        chaosRelatedService.add(ChaosServices.Iface.class);
        chaosRelatedService.add(UserHrAccountService.Iface.class);
        chaosRelatedService.add(PositionBS.Iface.class);
        chaosRelatedService.add(PositionBS.Iface.class);
        chaosRelatedService.add(ProfileServices.Iface.class);
    }

    /**
     * 创建thrift具体业务的客户端
     *
     * @param clazz 指定创建客户端 （thrift service下的 iface接口）
     * @return
     */
    public <clazz> clazz createIface(Class<T> clazz, String serverName) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (pool == null) {
                pool = bulidClientPool(classLoader, clazz);
            }
            Invoker invoker = new NodeInvoker<T>(pool, serverName, config.getRetry());
            DynamicClientHandler dynamicClientHandler = new DynamicClientHandler(invoker);
            return dynamicClientHandler.bind(classLoader, clazz);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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
    protected GenericKeyedObjectPool<ZKPath, T> bulidClientPool(ClassLoader classLoader, Class<T> ifaceClass) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        // 设置poolConfig
        GenericKeyedObjectPool.Config poolConfig = new GenericKeyedObjectPool.Config();
        poolConfig.maxActive = config.getMaxActive();
        poolConfig.maxIdle = config.getMaxIdle();
        poolConfig.minIdle = config.getMinIdle();
        poolConfig.maxWait = config.getMaxWait();
        poolConfig.timeBetweenEvictionRunsMillis = config.getTimeBetweenEvictionRunsMillis();
        poolConfig.testWhileIdle = config.isTestWhileIdle();

        // 加载Client.Factory类
        Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader.loadClass(findOutClassName(ifaceClass) + "$Client$Factory");
        TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();
        //几个特殊的和Chaos相关的服务超时时间设置为120s
        int timeout = chaosRelatedService.contains(ifaceClass) ? 120*1000 : config.getTimeout();
        TMultiServicePoolFactory<T> clientPool = new TMultiServicePoolFactory<T>(clientFactory, timeout, config.getInitialBufferCapacity(), config.getMaxLength());

        return new GenericKeyedObjectPool<ZKPath, T>(clientPool, poolConfig);
    }

    /**
     * 获取外部内的类名
     * <p>
     *
     * @return 类名
     */
    private String findOutClassName(Class<T> clazz) {
        String iface = clazz.getName();
        if (iface.contains("$")) {
            return iface.substring(0, iface.indexOf("$"));
        }
        return iface;
    }

    public void clear() {
        pool.clear();
    }

    public String getServerName() {
        return serverName;
    }
}
