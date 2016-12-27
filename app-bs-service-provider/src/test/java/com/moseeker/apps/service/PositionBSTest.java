package com.moseeker.apps.service;

import static org.junit.Assert.assertEquals;

import org.apache.http.HttpRequest;
import org.apache.thrift.TException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;

public class PositionBSTest {
	
	@Mock
	ChaosServices.Iface chaosService;
	
	@Mock
	PositionServices.Iface positionServices;
	
	@Mock
	PositionDao.Iface positionDao;

	@Mock
	UserHrAccountService.Iface userHrAccountService ;

	@Mock
	CompanyServices.Iface companyService;

	@Mock
	CompanyDao.Iface CompanyDao;
	
	@Mock 
	HttpRequest request;
	
	@InjectMocks 
	private PositionBS positionBS;
	
	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested. including any @Before or @After methods
	
	@BeforeClass
	public void init() {
		
		//Mockito.when(request.getParams()).thenReturn("");
		
		int positionId = 1;
		int channel = 1;
		ThirdPartyPositionForSynchronizationWithAccount account = new ThirdPartyPositionForSynchronizationWithAccount();
		ThirdPartyPositionForSynchronization position = new ThirdPartyPositionForSynchronization();
		account.setPosition_info(position);
		account.setUser_name("test");
		PositionServices.Iface positionServices = Mockito.mock(PositionServices.Iface.class);
		try {
			Mockito.when(positionServices.createRefreshPosition(positionId, channel)).thenReturn(account);
			Mockito.when(positionServices.ifAllowRefresh(positionId, channel)).thenReturn(false);
			Response response = ResultMessage.SUCCESS.toResponse();
			ThirdPartyPositionData data = new ThirdPartyPositionData();
			data.setSync_time(new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));
			response.setData(JSON.toJSONString(data));
			Mockito.when(chaosService.refreshPosition(account)).thenReturn(response);
			Position job = new Position();
			job.setId(positionId);
			job.setUpdate_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
			Mockito.when(positionDao.updatePosition(job)).thenReturn(1);
			
		} catch (TException e) {
			e.printStackTrace();
		}
		
		positionBS.setChaosService(chaosService);
		positionBS.setCompanyDao(CompanyDao);
		positionBS.setCompanyService(companyService);
		positionBS.setPositionDao(positionDao);
		positionBS.setPositionServices(positionServices);
		positionBS.setUserHrAccountService(userHrAccountService);
		
		try {
			System.out.println("ifAllowRefresh:"+positionServices.ifAllowRefresh(positionId, channel));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIfAllowRefresh() {
		try {
			Mockito.when(positionServices.ifAllowRefresh(0, 0)).thenReturn(true);
			assertEquals(true,positionServices.ifAllowRefresh(0, 0));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRefreshPosition() {
		Response response = positionBS.refreshPosition(1, 1);
		assertEquals(100005, response.getStatus());
	}

	/*@Test
    public void testDao() {
    	TTransport transport = null;
		try {
			transport = new TFastFramedTransport(new TSocket("127.0.0.1", 19089, 60*1000));
			TProtocol protocol = new TCompactProtocol(transport);
			transport.open();
			TMultiplexedProtocol mulProtocol= new TMultiplexedProtocol(protocol, "com.moseeker.thrift.gen.apps.positionbs.service.PositionBS");
			Factory factory = new Factory();
			Client client = factory.getClient(mulProtocol);
			ThirdPartyPositionForm form = new ThirdPartyPositionForm();
			Response response = client.synchronizePositionToThirdPartyPlatform(form);
			System.out.println(response.getData());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(transport != null) {
				transport.close();
			}
		}
    }*/
	
	@Ignore("Test is ignored as a demonstration")
	@Test
	public void testSame() {
		Assert.assertThat(1, is(1));
	}

	private Matcher<Integer> is(int i) {
		return CoreMatchers.equalTo(1);
	}

}
