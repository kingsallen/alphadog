//package com.moseeker.useraccounts.service;
//
//import java.awt.List;
//import java.util.Arrays;
//
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.employee.service.EmployeeService;
//import com.moseeker.thrift.gen.employee.struct.BindType;
//import com.moseeker.thrift.gen.employee.struct.BindingParams;
//import com.moseeker.thrift.gen.employee.struct.EmployeeCustomFieldsConf;
//import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;
//import com.moseeker.thrift.gen.employee.struct.Result;
//import com.moseeker.thrift.gen.employee.struct.RewardsResponse;
//
//public class EmployeeServiceTest {
//	
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
//	public void unbindTest(){
//		try {
//			Result unbind = service.unbind(6, 1, 1);
//			System.out.println(unbind);
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
//			java.util.List<EmployeeCustomFieldsConf> list = service.getEmployeeCustomFieldsConf(2878);
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
//		bp.setEmail("510340677@qq.com");
//		bp.setCompanyId(2878);
//		bp.setType(BindType.EMAIL);
////		bp.setAnswer1("上海");
////		bp.setAnswer2("仟寻");
////		bp.setCompanyId(2878);
////		bp.setType(BindType.QUESTIONS);
////		bp.setUserId(1122611);
//		try {
//			Result result = service.bind(bp);
//			System.out.println(result);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void emailActivationTest() {
//		try {
//			Result result = service.emailActivation("ff24a4dc10362b20");
//			System.out.println(result);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
