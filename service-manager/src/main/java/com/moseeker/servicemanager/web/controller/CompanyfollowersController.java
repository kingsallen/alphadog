package com.moseeker.servicemanager.web.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.db.userdb.tables.Companyfollowers.COMPANYFOLLOWERS;
import com.moseeker.db.userdb.tables.records.CompanyfollowersRecord;

import java.sql.*;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorTempFramework;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.Watcher;

import org.apache.curator.utils.CloseableUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.jooq.*;
import org.jooq.impl.*;

@Controller
public class CompanyfollowersController {

	Logger logger = LoggerFactory.getLogger(CompanyfollowersController.class);

	@RequestMapping(value = " /companyfollowers", method = RequestMethod.GET)
	@ResponseBody
	public void getCompanyfollowers(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			
			CompanyfollowerQuery query = new  CompanyfollowerQuery();
			
			if (request.getParameter("userid") != null ){
				int userid = Integer.parseInt(request.getParameter("userid"));
				if (userid>0){
					query.setUserid(userid);
				}				
			}

			if (request.getParameter("limit") != null){
				int limit = Integer.parseInt(request.getParameter("limit"));
				if (limit > 0){
					query.setLimit(limit);
				}else{
					query.setLimit(10);
				}				
			}

			if (request.getParameter("companyid") != null){
				int companyid = Integer.parseInt(request.getParameter("companyid"));
				if (companyid>0){
					query.setCompanyid(companyid);
				}				
			}
			
			query.setAppid(1);
			
			// ....  todo 需要更多支持。
			
			writer = response.getWriter();
			List<Companyfollower> companyfollowers = callThriftServerGetCompanyfollowers(query);
			String jsonString = JSON.toJSONString(companyfollowers);
			writer.write(jsonString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			logger.info("getCompanyfollowers");
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	private List<Companyfollower> callThriftServerGetCompanyfollowers(CompanyfollowerQuery query){
		try {
			
			CuratorFramework zooclient = CuratorFrameworkFactory
					.builder()
					.connectString("127.0.0.1:2181")  
			        .sessionTimeoutMs(30000)  
			        .connectionTimeoutMs(30000)  
			        .canBeReadOnly(false)  
			        .retryPolicy(new ExponentialBackoffRetry(1000, 3))  
			        .namespace("services/companyfollowers")  
			        .defaultData(null)  
			        .build();  
			zooclient.start();	
			

//			CuratorTempFramework zooclient = CuratorFrameworkFactory
//					.builder()
//					.connectString("127.0.0.1:2181")  
//			        .sessionTimeoutMs(30000)  
//			        .connectionTimeoutMs(30000)  
//			        .canBeReadOnly(false)  
//			        .retryPolicy(new ExponentialBackoffRetry(1000, 290))  
//			        .namespace("Companyfollowers")  
//			        .defaultData(null)  
//			        .buildTemp();  
			
			
			
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

			//  start call thrift service;
			System.out.println(query);

			List<Companyfollower> companyfollowers = client
					.getCompanyfollowers(query);

			System.out.print(companyfollowers);			
			//  end 
			

			transport.close();
			
			return companyfollowers;
			
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
		
		return null;
	}

}
