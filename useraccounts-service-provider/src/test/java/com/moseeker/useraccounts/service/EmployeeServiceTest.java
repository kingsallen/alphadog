//package com.moseeker.useraccounts.service;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.internal.matchers.Any;
//import org.mockito.junit.MockitoJUnitRunner;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertSame;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//
//import com.alibaba.fastjson.JSONObject;
//import com.moseeker.common.redis.RedisClient;
//import com.moseeker.common.redis.cache.CacheClient;
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.common.struct.CommonQuery;
//import com.moseeker.thrift.gen.common.struct.Response;
//import com.moseeker.thrift.gen.dao.service.ConfigDBDao;
//import com.moseeker.thrift.gen.dao.service.HrDBDao;
//import com.moseeker.thrift.gen.dao.service.JobDBDao;
//import com.moseeker.thrift.gen.dao.service.UserDBDao;
//import com.moseeker.thrift.gen.dao.service.WxUserDao;
//import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
//import com.moseeker.thrift.gen.employee.struct.BindType;
//import com.moseeker.thrift.gen.employee.struct.BindingParams;
//import com.moseeker.thrift.gen.employee.struct.Employee;
//import com.moseeker.thrift.gen.employee.struct.EmployeeCustomFieldsConf;
//import com.moseeker.thrift.gen.employee.struct.EmployeeResponse;
//import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;
//import com.moseeker.thrift.gen.employee.struct.Result;
//import com.moseeker.thrift.gen.employee.struct.RewardsResponse;
//import com.moseeker.thrift.gen.mq.service.MqService;
//import com.moseeker.useraccounts.dao.UserDao;
//import com.moseeker.useraccounts.service.impl.EmployeeService;
//
//@RunWith(MockitoJUnitRunner.class)
//public class EmployeeServiceTest {
//	
//	@InjectMocks
//	public EmployeeService service;
//	
//	@Mock
//	public UserDBDao.Iface userDbDao;
//	
//	@Mock
//	public WxUserDao.Iface wxUserDao;
//	
//	@Mock
//	public HrDBDao.Iface hrDbDao;
//	
//	@Mock
//	public ConfigDBDao.Iface configDbDao;
//	
//	@Mock
//	public JobDBDao.Iface jobDbDao;
//	
//	@Mock
//	public MqService.Iface mqService;
//	
//	@Mock
//	public RedisClient client;
//
//	@Test
//	public void getEmployeeTest() {
//		try {
//			when(userDbDao.getEmployee(any())).thenReturn(null, new UserEmployeeDO(), new UserEmployeeDO().setId(10).setEmployeeid("10010"));
//			when(wxUserDao.getResource(any())).thenReturn(new Response(0, null).setData("{\"id\":20}"), new Response(9999, null));
//			assertEquals(service.getEmployee(0, 0).exists, false);
//			assertEquals(service.getEmployee(10, 2878).exists, false);
//			assertEquals(service.getEmployee(10, 2878).getEmployee().getWxuserId(), 20);
//			assertEquals(service.getEmployee(10, 2878).getEmployee().getWxuserId(), 0);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
////	public EmployeeService.Iface service = null;
////
////	@Before
////	public void init() {
////		service = ServiceManager.SERVICEMANAGER.getService(EmployeeService.Iface.class);
////	}
////	
////	@Test
////	public void test() {
////		try {
////			service.getEmployee(4,  32);
////		} catch (TException e) {
////			e.printStackTrace();
////		}
////	}
////	
////	@Test
////	public void getEmpConfigTest() {
////		try {
////			EmployeeVerificationConfResponse conf = service.getEmployeeVerificationConf(2878);
////			System.out.println(conf);
////		} catch (TException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////	
////	@Test
////	public void getEmployeeRewardsTest() {
////		try {
////			RewardsResponse rewards = service.getEmployeeRewards(52, 8);
////			System.out.println(rewards);
////		} catch (TException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////	
////	@Test
////	public void setEmployeeCustomInfoTest() {
////		try {
////			Result result = service.setEmployeeCustomInfo(28207607, "[{\"10\":[\"1\"]},{\"8\":[\"\u804c\u4f4d\u4fe1\u606f2\"]}]");
////			System.out.println(result.toString());
////		} catch (TException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////	
////	@Test
////	public void getEmployeeCustomFieldsConfTest() {
////		try {
////			List<EmployeeCustomFieldsConf> list = service.getEmployeeCustomFieldsConf(2878);
////			System.out.println(Arrays.toString(list.toArray()));
////		} catch (TException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////	
////	@Test
////	public void bindTest() {
////		BindingParams bp = new BindingParams();
////		bp.setEmail("510340677@qq.com");
////		bp.setCompanyId(2878);
////		bp.setType(BindType.EMAIL);
////		try {
////			Result result = service.bind(bp);
////			System.out.println(result);
////		} catch (TException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////	
////	@Test
////	public void emailActivationTest() {
////		try {
////			Result result = service.emailActivation("ff24a4dc10362b20");
////			System.out.println(result);
////		} catch (TException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//}
