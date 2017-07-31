package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.moseeker.useraccounts.config.AppConfig;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserEmailPositionTest {
	@Autowired
	private UserPositionEmailService service;
	
	@Test
	public void sendValidateEmail() throws TException{
		int userId=1;
		String email="zhangzeteng@moseeker.com";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keyWord","上海" );
		String conditions=JSON.toJSONString(map);
		int result=service.sendEmailvalidation(email, userId, conditions);
		System.out.println("result========="+result);
	}
	
	@Test
	public void postEmailPosition(){
		int userId=1;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keyWord","上海" );
		String conditions=JSON.toJSONString(map);
		int result=service.postUserPositionEmail(userId, conditions);
		System.out.println("result========="+result);
	}
	
	@Test
	public void sendPositionEmail() throws Exception{
		int userId=1;
		int result=service.sendEmailPosition(userId);
		System.out.println("result========="+result);
	}

}
