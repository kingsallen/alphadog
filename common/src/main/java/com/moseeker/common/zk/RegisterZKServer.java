package com.moseeker.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * 
 * ZooKeeper服务节点注册帮助类
 *  
 * @date Mar 26, 2016
 * @company moseeker
 * @author wjf
 * @email wjf2255@gmail.com
 */
public class RegisterZKServer {

	private volatile static RegisterZKServer instance = null; //ZooKeeper服务节点注册帮助类
	private RegisterConf conf = null;							//注册信息
	
	/**
	 * 创建服务节点注册帮助类
	 * 
	 * @param processor 服务
	 * @throws InstantiationException 初始化失败异常
	 */
	private RegisterZKServer(TBaseProcessor<?> processor) throws InstantiationException {
		//registerServer(processor, conf);
		conf = new RegisterConf(processor);
	}
	
	/**
	 * 初始化服务节点帮助类
	 * @param processor 具体的服务
	 * @return RegisterZKServer 获取唯一的服务注册帮助类。如果还未生成，创建一个新的对象。
	 * @throws InstantiationException 初始化失败异常。通常由于配置文件信息配置有误或未找到配置文件导致
	 */
	public static RegisterZKServer getInstance(TBaseProcessor<?> processor) throws InstantiationException {
		if (instance == null) {
			synchronized (RegisterZKServer.class) {
				if (instance == null) {
					instance = new RegisterZKServer(processor);
				}
			}
		}
		return instance;
	}  
	
	/**
	 * 注册服务
	 */
	public void registerServer() {
		try {
			CuratorFramework zooclient = CuratorFrameworkFactory
					.builder()
					.connectString(conf.getConnectionAddress())  
			        .sessionTimeoutMs(conf.getSessionTimeOut())  
			        .connectionTimeoutMs(conf.getConnectionTimeOut())  
			        .canBeReadOnly(conf.isCanBeReadOnly())  
			        .retryPolicy(new ExponentialBackoffRetry(1000, 290))  
			        //.namespace("services/companyfollowers")  
			        .namespace(conf.getServiceName())
			        .defaultData(null)  
			        .build();  
			zooclient.start();	
			
			//byte[] servers = zooclient.getData().forPath("/servers");
			zooclient.create().creatingParentsIfNeeded().withMode(conf.getCreateMode()).forPath(conf.getPrivatePath());


			new Thread(()-> startSimpleServer(conf.getProcessor(), conf.getServicePort())).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void startSimpleServer(TBaseProcessor<?> processor,
			int port) {
		try {
			TServerTransport serverTransport = new TServerSocket(port);
			TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
