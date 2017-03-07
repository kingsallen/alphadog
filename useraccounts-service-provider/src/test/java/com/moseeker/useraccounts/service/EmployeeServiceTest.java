//package com.moseeker.useraccounts.service;
//
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.employee.service.EmployeeService;
//import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;
//import com.moseeker.thrift.gen.employee.struct.RewardsResponse;
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
//}
