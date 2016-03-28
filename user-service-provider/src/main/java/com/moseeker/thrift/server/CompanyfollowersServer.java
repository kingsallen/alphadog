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

import com.moseeker.common.zk.RegisterZKServer;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices.Processor;
import com.moseeker.thrift.service.impl.CompanyfollowerServicesImpl;

/**
 * 
 * 服务启动入口。服务启动依赖所需的配置文件serviceprovider.properties中的配置信息。务必保证配置信息正确
 *  
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class CompanyfollowersServer {

	public static CompanyfollowerServicesImpl handler;

	public static CompanyfollowerServices.Processor<CompanyfollowerServicesImpl> processor;

	public static void main(String[] args) {
		try {
			handler = new CompanyfollowerServicesImpl();
			processor = new Processor<CompanyfollowerServicesImpl>(handler);
			RegisterZKServer registerZKServer = RegisterZKServer.getInstance(processor);
			registerZKServer.registerServer();

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
