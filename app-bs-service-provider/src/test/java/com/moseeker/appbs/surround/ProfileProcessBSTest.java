package com.moseeker.appbs.surround;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.moseeker.apps.service.ProfileProcessBS;
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
        acac.scan("com.moseeker.common.aop.iface");
        acac.scan("com.moseeker.baseorm");
        acac.refresh();
        return acac;
	}
	@Before
	public void init() throws Exception{
		AnnotationConfigApplicationContext acac=initSpring();
		profileProcessBS=acac.getBean(ProfileProcessBS.class);
		Mockito.when(companyService.getWechat(Mockito.anyLong(),Mockito.anyLong())).thenReturn(ResponseUtils.success(null));
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
