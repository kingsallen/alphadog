package com.moseeker.thrift.client;

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

// Generated code

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.ExceptionResponse;
import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;

import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class CompanyfollowersClient extends BaseThriftClient {
	public static void main(String[] args) {
			
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
				
				List<String> iplist =  zooclient.getChildren().forPath("/servers");
				String thriftserver = iplist.get(0); // 临时用第一个;
				System.out.println("thriftserver : " + thriftserver);
				String[] ipport = thriftserver.split(":");
				String ip = ipport[0];
				int port = Integer.parseInt(ipport[1]);

				TTransport transport;
				transport = new TSocket(ip, port);
				transport.open();

				TProtocol protocol = new TBinaryProtocol(transport);
				CompanyfollowerServices.Client client = new CompanyfollowerServices.Client(
						protocol);

				perform(client,true);

				transport.close();
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TTransportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			


//		try {
//			TTransport transport;
//			transport = new TSocket("127.0.0.1", 9090);
//			transport.open();
//
//			TProtocol protocol = new TBinaryProtocol(transport);
//			CompanyfollowerServicesInterface.Client client = new CompanyfollowerServicesInterface.Client(
//					protocol);
//
//			perform(client,false);
//
//			transport.close();
//		} catch (TException x) {
//			x.printStackTrace();
//		}
	}

	private static void perform(CompanyfollowerServices.Client client, boolean run)
			throws TException {
		
		if ( !run ){
			return;
		}
		

		
		int ret = client.delCompanyfollowers(1, 1);
		System.out.println("delCompanyfollowers()");

		System.out.println(ret);

		ret = client.postCompanyfollowers(15, 18);
		System.out.println("postCompanyfollowers()");

		System.out.println(ret);

		CompanyfollowerQuery query = new CompanyfollowerQuery();
		query.setAppid(1);
		query.setUserid(1);


		System.out.println(query);

		List<Companyfollower> companyfollowers = client
				.getCompanyfollowers(query);

		System.out.print(companyfollowers);
	}

	@Override
	public List callThriftServerGet(Object query) {
		// TODO Auto-generated method stub
		try {

			String thriftserver = this.getThriftServer();
			String[] ipport = thriftserver.split(":");
			
			String ip = ipport[0];
			int port = Integer.parseInt(ipport[1]);

			TTransport transport;
			transport = new TSocket(ip, port);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			CompanyfollowerServices.Client client = new CompanyfollowerServices.Client(
					protocol);

			List<Companyfollower> companyfollowers = client
					.getCompanyfollowers((CompanyfollowerQuery)query);

			transport.close();

			return companyfollowers;

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int callThriftServerPost(Object query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int callThriftServerPut(Object query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int callThriftServerDelete(Object query) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String getZooKeeperNamespace() {
		return "companyfollowers";
	}
}
