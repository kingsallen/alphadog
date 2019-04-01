package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.useraccounts.struct.ApplicationDetailVO;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.useraccounts.struct.FavPositionForm;
import com.moseeker.thrift.gen.useraccounts.struct.RecommendationVO;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserCenterServiceTest {

    @Autowired
    private UserCenterService service;

    //@Test
    public void getApplication() throws Exception {
        List<ApplicationRecordsForm> list = service.getApplication(1122611);
        System.out.println(Arrays.toString(list.toArray()));
    }

    //@Test
    public void getFavPositions() throws Exception {
        List<FavPositionForm> favPositions = service.getFavPositions(675796);
        System.out.println(Arrays.toString(favPositions.toArray()));
    }

    //@Test
    public void getRecommendations() throws Exception {
        RecommendationVO recommendationVO = service.getRecommendations(677438, (byte) 0, (byte) 1, (byte) 10);
        System.out.println(recommendationVO);
    }

    @Test
    public void getApplicationDetail() throws Exception {
        ApplicationDetailVO applicationDetail = service.getApplicationDetail(2195768, 473166);
        System.out.println(applicationDetail);
    }

	/*@Mock
	private UserCenterBizTools bizTools;
	
	@InjectMocks 
	private UserCenterService userCenterService;
	
	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Before
	public void init() {
		JobApplicationDO a1 = new JobApplicationDO();
		a1.setId(1);
		a1.setStatusId(1);
		a1.setAppTplId(1);
		a1.setPositionId(1);
		a1.setCompanyId(1);
		a1.setCreateTime("2017-01-01 11:11:11");
		JobApplicationDO a2 = new JobApplicationDO();
		a2.setId(2);
		a2.setStatusId(2);
		a2.setAppTplId(2);
		a2.setPositionId(2);
		a2.setCompanyId(2);
		a2.setCreateTime("2017-02-02 22:22:22");
		
		List<JobApplicationDO> apps = Arrays.asList(a1,a2);
		try {
			Mockito.when(bizTools.getAppsForUser(1)).thenReturn(apps);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}

		JobPositionDO p1 = new JobPositionDO();
		p1.setId(1);
		p1.setTitle("title1");
		p1.setDepartment("department1");
		p1.setCity("上海");
		p1.setSalaryBottom(4);
		p1.setSalaryTop(6);

		JobPositionDO p2 = new JobPositionDO();
		p2.setId(2);
		p2.setTitle("title2");
		p2.setDepartment("department2");
		p2.setSalaryBottom(5);
		p2.setSalaryTop(10);
		p2.setUpdateTime("2017-02-02 22:22:22");
		List<JobPositionDO> positions = Arrays.asList(p1,p2);
		try {
			Mockito.when(bizTools.getPositions(1,2)).thenReturn(positions);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}

		ConfigSysPointConfTplDO tpl1 = new ConfigSysPointConfTplDO();
		tpl1.setId(1);
		tpl1.setRecruitOrder(11);
		ConfigSysPointConfTplDO tpl2 = new ConfigSysPointConfTplDO();
		tpl2.setId(2);
		tpl2.setRecruitOrder(12);
		List<ConfigSysPointConfTplDO> tpls = Arrays.asList(tpl1, tpl2);
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
		
		UserFavPositionDO favP1 = new UserFavPositionDO();
		favP1.setSysuserId(1);
		favP1.setPositionId(1);
		
		UserFavPositionDO favP2 = new UserFavPositionDO();
		favP2.setSysuserId(1);
		favP2.setPositionId(2);
		List<UserFavPositionDO> favPs = Arrays.asList(favP1, favP2);
		try {
			Mockito.when(bizTools.getFavPositions(1,0)).thenReturn(favPs);
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	//@Test
	public void testGetApplication() {
		try {
			List<ApplicationRecordsForm> records = userCenterService.getApplication(1);
			assertEquals(2, records.size());
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	//@Test
	public void testGetApplication2() {
		try {
			List<ApplicationRecordsForm> records = userCenterService.getApplication(1);
			assertEquals(1, records.get(0).getId());
			assertEquals("公司1", records.get(0).getCompany_name());
			assertEquals("title1", records.get(0).getPosition_title());
			//assertEquals("11", records.get(0).getStatus_name());
			assertEquals("2017-01-01 11:11:11", records.get(0).getTime());
			
			assertEquals(2, records.get(1).getId());
			assertEquals("公司2", records.get(1).getCompany_name());
			assertEquals("title2", records.get(1).getPosition_title());
			//assertEquals(12, records.get(1).getStatus_name());
			assertEquals("2017-02-02 22:22:22", records.get(1).getTime());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception");
		}
	}
	//@Test
	public void testGetFavPositions() {
		try {
			List<FavPositionForm> forms = userCenterService.getFavPositions(1);
			assertEquals(2, forms.size());

			assertEquals(1, forms.get(0).getId());
			assertEquals("title1", forms.get(0).getTitle());
			assertEquals("department1", forms.get(0).getDepartment());
			assertEquals("上海", forms.get(0).getCity());
			assertEquals(4, forms.get(0).getSalary_bottom());
			assertEquals(6, forms.get(0).getSalary_top());
			assertEquals(null, forms.get(0).getUpdate_time());

			assertEquals(2, forms.get(1).getId());
			assertEquals("title2", forms.get(1).getTitle());
			assertEquals("department2", forms.get(1).getDepartment());
			assertEquals(null, forms.get(1).getCity());
			assertEquals(5, forms.get(1).getSalary_bottom());
			assertEquals(10, forms.get(1).getSalary_top());
			assertEquals("2017-02-02 22:22:22", forms.get(1).getUpdate_time());
		} catch (TException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}*/
}
