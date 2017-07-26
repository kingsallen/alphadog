package com.moseeker.useraccounts.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moseeker.useraccounts.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserPositionEmailServiceTest {
	@Autowired
	private UserPositionEmailService userPositionEmailService;
	
	@Test
	public void postUserPositionEmailTest(){
		userPositionEmailService.postUserPositionEmail(1, "33333");
	}
}
