package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moseeker.useraccounts.config.AppConfig;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserPositionEmailServiceTest {
	@Autowired
	private UserPositionEmailService userPositionEmailService;
	
	@Test
	public void postUserPositionEmailTest(){
		userPositionEmailService.postUserPositionEmail(1, "33333");
	}

	@Test
	public void sendvalidateEmailTest(){
		int userId=1;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keyWord","上海");
		String conditions= JSON.toJSONString(map);
		try {
			int result=userPositionEmailService.sendEmailvalidation("zhangzeteng@moseeker.com",userId,conditions,"www.baidu.com");
			System.out.println("result========="+result);
		} catch (TException e) {
			e.printStackTrace();
		}

	}
	@Test
	public void sendPositionEmail() throws Exception {
		int userid=2191502;
		int result=userPositionEmailService.sendEmailPosition(userid);
	}
}
