package com.moseeker.rpccenter.client;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.config.ThriftServerConfig;
import com.moseeker.rpccenter.listener.ZKPath;
import com.moseeker.rpccenter.pool.TServicePoolFactory;
import com.moseeker.rpccenter.proxy.DynamicClientHandler;

/**
 * 
 * rpc服务客户端工厂 
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 * @param <T>
 */
public class IfaceFactory<T> {
	
	private ThriftServerConfig config;						//配置信息
	private GenericKeyedObjectPool<ZKPath, T> pool = null;	//节点对象池
	
	/**
	 * 初始化thrift客户端工厂
	 * @param config
	 */
	public IfaceFactory(ThriftServerConfig config) {
		this.config = config;
	}
	
	/**
	 * 创建thrift具体业务的客户端
	 * @param clazz 指定创建客户端 （thrift service下的 iface接口）
	 * @return
	 */
	public <clazz> clazz createIface(Class<T> clazz) {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if(pool == null) {
				pool = bulidClientPool(classLoader, clazz);
			}
			Invoker invoker = new NodeInvoker<T>(pool, BeanUtils.findOutClassName(clazz), config.getRetry());
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
        TServicePoolFactory<T> clientPool = new TServicePoolFactory<T>(clientFactory, config.getTimeout());

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
}
