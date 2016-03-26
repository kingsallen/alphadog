package com.moseeker.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.zookeeper.CreateMode;

import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices.Processor;

public class RegisterZKServer {

	private void registerServer(Processor processor, RegisterConf conf) {
		try {
			CuratorFramework zooclient = CuratorFrameworkFactory
					.builder()
					.connectString("127.0.0.1:2181")  
			        .sessionTimeoutMs(30000)  
			        .connectionTimeoutMs(30000)  
			        .canBeReadOnly(false)  
			        .retryPolicy(new ExponentialBackoffRetry(1000, 290))  
			        .namespace("services/companyfollowers")  
			        .defaultData(null)  
			        .build();  
			zooclient.start();	
			
			//byte[] servers = zooclient.getData().forPath("/servers");
			zooclient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/servers/127.0.0.1:9090");


			new Thread(()-> startSimpleServer(processor, 9000)).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void startSimpleServer(Processor processor,
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
