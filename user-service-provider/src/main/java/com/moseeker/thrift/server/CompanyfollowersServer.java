package com.moseeker.thrift.server;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.zookeeper.CreateMode;

import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices.Processor;
import com.moseeker.thrift.service.impl.CompanyfollowerServicesImpl;

public class CompanyfollowersServer {

	public static CompanyfollowerServicesImpl handler;

	public static CompanyfollowerServices.Processor<CompanyfollowerServicesImpl> processor;

	public static void main(String[] args) {
		try {
			handler = new CompanyfollowerServicesImpl();
			processor = new Processor<CompanyfollowerServicesImpl>(handler);
			
			
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


			new Thread(new Runnable() {
				public void run() {
					startSimpleServer(processor, 9090);
				}
			}).start();

		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void startSimpleServer(Processor processor,
			int port) {
		try {
			TServerTransport serverTransport = new TServerSocket(port);
			TServer server = new TSimpleServer(
					new Args(serverTransport).processor(processor));

			// Use this for a multithreaded server
			// TServer server = new TThreadPoolServer(new
			// TThreadPoolServer.Args(serverTransport).processor(processor));

			// System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
