package com.moseeker.appbs.surround;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.apps.config.AppConfig;
import com.moseeker.apps.service.ProfileBS;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes =AppConfig.class)
//@Transactional
public class ProfileBSTest {
//	@Mock
//	UseraccountsServices.Iface useraccountsServices;
//	@Mock
//	WholeProfileServices.Iface wholeProfileService;
//	@Mock
//	JobApplicationServices.Iface applicationService;
//	@Rule 
//	public MockitoRule mockitoRule = MockitoJUnit.rule();
//	@Autowired
//	private ProfileBS profileBS;
//	
//	@Before
//	public void init() throws Exception{
//		Mockito.when(useraccountsServices.ifExistProfile(Mockito.anyString())).thenReturn(false);
//		Mockito.when(applicationService.getApplicationByUserIdAndPositionId(Mockito.anyLong(),
//				Mockito.anyLong(),Mockito.anyLong())).thenReturn(ResponseUtils.success(true));
//		Mockito.when(applicationService.postApplication(Mockito.any())).thenReturn(ResponseUtils.success(null));
//		Mockito.when(wholeProfileService.createProfile(Mockito.anyString())).thenReturn(ResponseUtils.success(null));
//		Mockito.when(wholeProfileService.improveProfile(Mockito.anyString())).thenReturn(ResponseUtils.success(true));
//		Mockito.when(useraccountsServices.createRetrieveProfileUser(Mockito.any())).thenReturn(1000);
//		profileBS.setApplicationService(applicationService);
//		profileBS.setUseraccountsServices(useraccountsServices);
//		profileBS.setWholeProfileService(wholeProfileService);
//        
//	}
//	@Test
//	public void retrieveProfileTest(){
//		JSONObject json=new JSONObject();
//		JSONObject user=new JSONObject();
//		user.put("mobile", "111");
//		json.put("user", user);
//		String profile=json.toJSONString();
//		Response result=profileBS.retrieveProfile(124576,profile,3);
//		System.out.println(result);
//	}
}
