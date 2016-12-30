package com.moseeker.apps.service;

import static org.junit.Assert.assertEquals;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;

public class PositionBS1Test {
	
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
	
	@InjectMocks 
	private PositionBS positionBS;
	
	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void init() {
		
		Position job = new Position();
		job.setId(1);
		job.setUpdate_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
		try {
			Mockito.when(positionDao.updatePosition(job)).thenReturn(0);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		positionBS.setPositionDao(positionDao);
		//Mockito.when(positionServices.createRefreshPosition(positionId, channel)).thenReturn(account);
	}
	
	@Test
	public void testRefreshPosition() {
		Position job = new Position();
		job.setId(1);
		job.setUpdate_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
		try {
			assertEquals(0, positionDao.updatePosition(job));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
