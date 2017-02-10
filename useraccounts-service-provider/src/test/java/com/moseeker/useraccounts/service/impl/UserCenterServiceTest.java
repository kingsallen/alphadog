package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.AwardConfigTpl;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionDTO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		a1.setCompany_id(1);
		a1.set_create_time("2017-01-01 11:11:11");
		JobApplication a2 = new JobApplication();
		a2.setId(2);
		a2.setStatus_id(2);
		a2.setApp_tpl_id(2);
		a2.setPosition_id(2);
		a2.setCompany_id(2);
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
		p1.setCity("上海");
		p1.setSalary_bottom(4);
		p1.setSalary_top(6);
		
		Position p2 = new Position();
		p2.setId(2);
		p2.setTitle("title2");
		p2.setDepartment("department2");
		p2.setSalary_bottom(5);
		p2.setSalary_top(10);
		p2.setUpdate_time("2017-02-02 22:22:22");
		List<Position> positions = Arrays.asList(p1,p2);
		try {
			Mockito.when(bizTools.getPositions(1,2)).thenReturn(positions);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
		
		AwardConfigTpl tpl1 = new AwardConfigTpl();
		tpl1.setId(1);
		tpl1.setRecruitOrder(11);
		AwardConfigTpl tpl2 = new AwardConfigTpl();
		tpl2.setId(2);
		tpl2.setRecruitOrder(12);
		List<AwardConfigTpl> tpls = Arrays.asList(tpl1, tpl2);
		try {
			Mockito.when(bizTools.getAwardConfigTpls()).thenReturn(tpls);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
		
		Hrcompany company1 = new Hrcompany();
		company1.setId(1);
		company1.setAbbreviation("公司1");
		
		Hrcompany company2 = new Hrcompany();
		company2.setId(2);
		company2.setAbbreviation("公司2");
		List<Hrcompany> companies = Arrays.asList(company1, company2);
		try {
			Mockito.when(bizTools.getCompanies(1,2)).thenReturn(companies);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
		
		UserFavPositionDTO favP1 = new UserFavPositionDTO();
		favP1.setSysuserId(1);
		favP1.setPositionId(1);
		
		UserFavPositionDTO favP2 = new UserFavPositionDTO();
		favP2.setSysuserId(1);
		favP2.setPositionId(2);
		List<UserFavPositionDTO> favPs = Arrays.asList(favP1, favP2);
		try {
			Mockito.when(bizTools.getFavPositions(1,1)).thenReturn(favPs);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testGetApplication() {
		try {
			List<ApplicationRecordsForm> records = userCenterService.getApplication(1);
			assertEquals(2, records.size());
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testGetApplication2() {
		try {
			List<ApplicationRecordsForm> records = userCenterService.getApplication(1);
			assertEquals(1, records.get(0).getId());
			assertEquals("公司1", records.get(0).getDepartment());
			assertEquals("title1", records.get(0).getTitle());
			assertEquals(11, records.get(0).getStatus());
			assertEquals("2017-01-01 11:11:11", records.get(0).getTime());
			
			assertEquals(2, records.get(1).getId());
			assertEquals("公司2", records.get(1).getDepartment());
			assertEquals("title2", records.get(1).getTitle());
			assertEquals(12, records.get(1).getStatus());
			assertEquals("2017-02-02 22:22:22", records.get(1).getTime());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception");
		}
	}
	
//	@Test
//	public void testGetFavPositions() {
//		try {
//			List<FavPositionForm> forms = userCenterService.getFavPositions(1);
//			assertEquals(2, forms.size());
//
//			assertEquals(1, forms.get(0).getId());
//			assertEquals("title1", forms.get(0).getTitle());
//			assertEquals("department1", forms.get(0).getDepartment());
//			assertEquals("上海", forms.get(0).getCity());
//			assertEquals(4, forms.get(0).getSalary_bottom());
//			assertEquals(6, forms.get(0).getSalary_top());
//			assertEquals(null, forms.get(0).getUpdate_time());
//
//			assertEquals(2, forms.get(1).getId());
//			assertEquals("title2", forms.get(1).getTitle());
//			assertEquals("department2", forms.get(1).getDepartment());
//			assertEquals(null, forms.get(1).getCity());
//			assertEquals(5, forms.get(1).getSalary_bottom());
//			assertEquals(10, forms.get(1).getSalary_top());
//			assertEquals("2017-02-02 22:22:22", forms.get(1).getUpdate_time());
//		} catch (TException e) {
//			e.printStackTrace();
//			fail("Exception");
//		}
//	}
}
