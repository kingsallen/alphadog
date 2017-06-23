//package com.moseeker.surrender;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.thrift.TException;
//import org.joda.time.DateTime;
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
//import com.moseeker.apps.config.AppConfig;
//import com.moseeker.apps.service.PositionBS;
//import com.moseeker.common.providerutils.ResponseUtils;
//import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
//import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
//import com.moseeker.thrift.gen.common.struct.Response;
//import com.moseeker.thrift.gen.company.service.CompanyServices;
//import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
//import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
//import com.moseeker.thrift.gen.position.service.PositionServices;
//import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
//import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
//import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes =AppConfig.class)
//@Transactional
//public class PositionBSTest {
//	@Mock
//	UserHrAccountService.Iface userHrAccountService;
//	@Mock
//	CompanyServices.Iface companyService;
//	@Mock
//	PositionServices.Iface positionServices;
//	@Mock
//	ChaosServices.Iface chaosService;
//	@Rule 
//	public MockitoRule mockitoRule = MockitoJUnit.rule();
//	@Autowired
//	private PositionBS positionBS;
//	@Before
//	public void init(){
//		try {
//			ThirdPartyPositionForSynchronization tp=new ThirdPartyPositionForSynchronization();
//			tp.setTitle("11111111");
//			tp.setChannel(3);
//			tp.setJob_id("124576");
//			tp.setSalary_high("1000000");
//			tp.setSalary_low("1000");
//			tp.setQuantity("1000");
//			List<ThirdPartyPositionForSynchronization> positions=new ArrayList<ThirdPartyPositionForSynchronization>();
//			positions.add(tp);
//			Mockito.when(positionServices
//					.changeToThirdPartyPosition(Mockito.any(), Mockito.any())).thenReturn(positions);
//			List<ThirdPartyPositionForSynchronization> positionds =positionServices.changeToThirdPartyPosition(null, null);
//			System.out.println(positionds);
//			ThirdPartyPositionForSynchronizationWithAccount refreshPosition=new ThirdPartyPositionForSynchronizationWithAccount();
//			refreshPosition.setPosition_info(tp);
//			refreshPosition.setUser_name("zzt");
//			ThirdPartyPositionData p=new ThirdPartyPositionData();
//			p.setChannel((byte)3);
//			p.setPosition_id(124576);
//			p.setIs_refresh((byte)1);
//			p.setRefresh_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
//			Response response=ResponseUtils.success(p);
//			Mockito.when(chaosService.synchronizePosition(Mockito.any())).thenReturn(ResponseUtils.success(null));
//			Mockito.when(positionServices.ifAllowRefresh(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
//			Mockito.when(chaosService.refreshPosition(Mockito.any())).thenReturn(response);
//			Mockito.when(positionServices.createRefreshPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(refreshPosition);
//			positionBS.setChaosService(chaosService);
//			positionBS.setPositionServices(positionServices);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	//@Test
//	public void synchronizePositionToThirdPartyPlatformTest() throws Exception{
//		List<ThirdPartyPosition> list=new ArrayList<ThirdPartyPosition>();
//		ThirdPartyPosition thirdPartyPosition=new ThirdPartyPosition();
//		thirdPartyPosition.setChannel((byte)3);
//		list.add(thirdPartyPosition);
//		ThirdPartyPositionForm position=new ThirdPartyPositionForm();
//		position.setPosition_id(124576);
//		position.setChannels(list);
//		Response result=positionBS.synchronizePositionToThirdPartyPlatform(position);
//		System.out.println(result);
//	}
//	//@Test
//	public void refreshPositionTest() throws Exception{
//		Response result=positionBS.refreshPosition(124576, 3);
//		System.out.println(result);
//	}
//	 @Test
//	public void refreshPositionQXTest() throws TException{
//	 List<Integer> list =new ArrayList<Integer>();
//	 list.add(110484);
//	 list.add(110485);
//	 list.add(110486);
//	 positionBS.refreshPositionQX(list);
//	}
//
//}
