package com.moseeker.apps.service;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
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

	
	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	private void init() {
		int positionId = 0;
		int channel = 0;
		ThirdPartyPositionForSynchronizationWithAccount account = new ThirdPartyPositionForSynchronizationWithAccount();
		PositionServices.Iface positionServices = Mockito.mock(PositionServices.Iface.class);
		try {
			Mockito.when(positionServices.createRefreshPosition(positionId, channel)).thenReturn(account);
			
		} catch (TException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRefreshPosition() {
		
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
}
