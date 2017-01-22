package com.moseeker.useraccounts.service.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import com.moseeker.useraccounts.service.impl.pojos.ApplicationRecords;

public class UserCenterServiceTest {
	
	@Mock
	private UserCenterBizTools bizTools;
	
	@InjectMocks 
	private UserCenterService userCenterService;
	
	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void init() {
		JobApplication a1 = new JobApplication();
		a1.setId(1);
		a1.setStatus_id(1);
		a1.setApp_tpl_id(1);
		a1.setPosition_id(1);
		a1.set_create_time("2017-01-01 11:11:11");
		JobApplication a2 = new JobApplication();
		a2.setId(2);
		a2.setStatus_id(2);
		a2.setApp_tpl_id(2);
		a2.setPosition_id(2);
		a2.set_create_time("2017-02-02 22:22:22");
		
		List<JobApplication> apps = Arrays.asList(a1,a2);
		try {
			Mockito.when(bizTools.getAppsForUser(1)).thenReturn(apps);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
		
		Position p1 = new Position();
		p1.setId(1);
		p1.setTitle("title1");
		p1.setDepartment("department1");
		
		Position p2 = new Position();
		p2.setId(2);
		p2.setTitle("title2");
		p2.setDepartment("department2");
		List<Position> positions = Arrays.asList(p1,p2);
		try {
			Mockito.when(bizTools.getPositionsByApps(1,2)).thenReturn(positions);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testGetApplication() {
		try {
			List<ApplicationRecords> records = userCenterService.getApplication(1);
			assertEquals(2, records.size());
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testGetApplication2() {
		try {
			List<ApplicationRecords> records = userCenterService.getApplication(1);
			assertEquals(1, records.get(0).getId());
			assertEquals("department1", records.get(0).getDepartment());
			assertEquals("title1", records.get(0).getTitle());
			assertEquals("1", records.get(0).getStatus());
			assertEquals("2017-01-01 11:11:11", records.get(0).getTime());
			
			assertEquals(2, records.get(1).getId());
			assertEquals("department2", records.get(1).getDepartment());
			assertEquals("title2", records.get(1).getTitle());
			assertEquals("2", records.get(1).getStatus());
			assertEquals("2017-02-02 22:22:22", records.get(1).getTime());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception");
		}
	}
}
