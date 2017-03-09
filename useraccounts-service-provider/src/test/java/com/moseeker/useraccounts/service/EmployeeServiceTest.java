//package com.moseeker.useraccounts.service;
//
//import java.sql.Array;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.common.struct.Response;
//import com.moseeker.thrift.gen.employee.service.EmployeeService;
//import com.moseeker.thrift.gen.employee.struct.BindType;
//import com.moseeker.thrift.gen.employee.struct.BindingParams;
//import com.moseeker.thrift.gen.employee.struct.EmployeeCustomFieldsConf;
//import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;
//import com.moseeker.thrift.gen.employee.struct.Result;
//import com.moseeker.thrift.gen.employee.struct.RewardsResponse;
//import com.moseeker.thrift.gen.mq.service.MqService;
//import com.moseeker.thrift.gen.mq.service.MqService.Iface;
//
//public class EmployeeServiceTest {
//	
//	public EmployeeService.Iface service = null;
//
//	@Before
//	public void init() {
//		service = ServiceManager.SERVICEMANAGER.getService(EmployeeService.Iface.class);
//	}
//	
//	@Test
//	public void test() {
//		try {
//			service.getEmployee(4,  32);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void getEmpConfigTest() {
//		try {
//			EmployeeVerificationConfResponse conf = service.getEmployeeVerificationConf(2878);
//			System.out.println(conf);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void getEmployeeRewardsTest() {
//		try {
//			RewardsResponse rewards = service.getEmployeeRewards(52, 8);
//			System.out.println(rewards);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void setEmployeeCustomInfoTest() {
//		try {
//			Result result = service.setEmployeeCustomInfo(28207607, "[{\"10\":[\"1\"]},{\"8\":[\"\u804c\u4f4d\u4fe1\u606f2\"]}]");
//			System.out.println(result.toString());
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void getEmployeeCustomFieldsConfTest() {
//		try {
//			List<EmployeeCustomFieldsConf> list = service.getEmployeeCustomFieldsConf(2878);
//			System.out.println(Arrays.toString(list.toArray()));
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void bindTest() {
//		BindingParams bp = new BindingParams();
//		bp.setAnswer1("仟寻");
//		bp.setAnswer2("上海");
//		bp.setCompanyId(2878);
//		bp.setType(BindType.QUESTIONS);
//		try {
//			Result result = service.bind(bp);
//			System.out.println(result);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
////	@Test
////	public void sendAuthMail() throws TException {
////		Iface service = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
////		Response response = service.sendAuthEMail(new HashMap<String, String>(){{put("#employee_name#", "vincent");}}, 2, "510340677@qq.com", "发送");
////		System.out.println(response);
////	}
//
//}
