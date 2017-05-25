package com.moseeker.appbs.surround;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.apps.service.ProfileProcessBS;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.mq.service.MqService;

public class ProfileProcessBSTest {
	@Mock
	MqService.Iface mqService;
	@Mock
	CompanyServices.Iface companyService;
	
	private ProfileProcessBS profileProcessBS;
	
	public AnnotationConfigApplicationContext initSpring(){
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
		acac.scan("com.moseeker.apps");
		acac.scan("com.moseeker.common.aop.iface"); //开启接口统计
		acac.scan("com.moseeker.common.aop.notify");
		acac.scan("com.moseeker.baseorm");
        acac.refresh();
        return acac;
	}
	@Before
	public void init() throws Exception{
		AnnotationConfigApplicationContext acac=initSpring();
		profileProcessBS=acac.getBean(ProfileProcessBS.class);
		HrWxWechatRecord record=new HrWxWechatRecord();
		record.setId(278);
		record.setCompanyId(40104);
		record.setType((byte)1);
		record.setSignature("NjM2YjY3OWEzZjIxY2ZiM2JkOTJmOWZiZTY3YmVlYmY5NGEwZDBlOA==");
		record.setName("乐山路4Helper");
		record.setAppid("wx4eb438db0b7a28cf");
//		Mockito.when(companyService.getWechat(Mockito.anyLong(),Mockito.anyLong())).thenReturn(ResponseUtils.success(record.intoMap()));
		Mockito.when(mqService.messageTemplateNotice(Mockito.any())).thenReturn(ResponseUtils.success(null));
		profileProcessBS.setCompanyService(companyService);
		profileProcessBS.setMqService(mqService);
	}
	@Test
	public void processProfileAtsTest(){
		Response result=profileProcessBS.processProfileAts(12, "200");
		System.out.println(result);
	}
}
