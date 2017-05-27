//package com.moseeker.surrender;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.moseeker.apps.config.AppConfig;
//import com.moseeker.apps.service.ProfileProcessBS;
//import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
//import com.moseeker.common.providerutils.ResponseUtils;
//import com.moseeker.thrift.gen.common.struct.Response;
//import com.moseeker.thrift.gen.company.service.CompanyServices;
//import com.moseeker.thrift.gen.mq.service.MqService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes =AppConfig.class)
//@Transactional
//public class ProfileProcessBSTest {
//	@Mock
//	MqService.Iface mqService;
//	@Mock
//	CompanyServices.Iface companyService;
//	@Rule 
//	public MockitoRule mockitoRule = MockitoJUnit.rule();
//	@Autowired
//	private ProfileProcessBS profileProcessBS;
//	
//	@Before
//	public void init() throws Exception{
//		HrWxWechatRecord record=new HrWxWechatRecord();
//		record.setId(278);
//		record.setCompanyId(40104);
//		record.setType((byte)1);
//		record.setSignature("NjM2YjY3OWEzZjIxY2ZiM2JkOTJmOWZiZTY3YmVlYmY5NGEwZDBlOA==");
//		record.setName("乐山路4Helper");
//		record.setAppid("wx4eb438db0b7a28cf");
//		Mockito.when(companyService.getWechat(Mockito.anyLong(),Mockito.anyLong())).thenReturn(ResponseUtils.success(record.intoMap()));
//		Mockito.when(mqService.messageTemplateNotice(Mockito.any())).thenReturn(ResponseUtils.success(null));
//		profileProcessBS.setCompanyService(companyService);
//		profileProcessBS.setMqService(mqService);
//	}
//	@Test
//	public void processProfileAtsTest(){
//		Response result=profileProcessBS.processProfileAts(10, "627216844");
//		System.out.println(result);
//	}
//}
