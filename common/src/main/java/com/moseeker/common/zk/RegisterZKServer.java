package com.moseeker.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
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

	private volatile static RegisterZKServer instance = null;
	private RegisterConf conf = null;
	
	private RegisterZKServer(TBaseProcessor<?> processor) throws InstantiationException {
		//registerServer(processor, conf);
		conf = new RegisterConf(processor);
	}
	
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
			TServer server = new TSimpleServer(
					new Args(serverTransport).processor(processor));
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
