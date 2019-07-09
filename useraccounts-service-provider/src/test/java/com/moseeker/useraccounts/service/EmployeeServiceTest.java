package com.moseeker.useraccounts.service;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.*;
import org.apache.thrift.TException;
import org.junit.Test;

import java.util.Arrays;

public class EmployeeServiceTest {


	public EmployeeService.Iface service  = ServiceManager.SERVICE_MANAGER.getService(EmployeeService.Iface.class);;

    //@Before
	public void init() {
		service = ServiceManager.SERVICE_MANAGER.getService(EmployeeService.Iface.class);
	}

    ////@Test
	public void test() {
		try {
            EmployeeResponse employee = service.getEmployee(4, 1);
            System.out.println(employee.getEmployee());
        } catch (TException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getEmpConfigTest() {
		try {
			EmployeeVerificationConfResponse conf = service.getEmployeeVerificationConf(30);
			System.out.println(conf);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    ////@Test
	public void unbindTest(){
		try {
			Result unbind = service.unbind(6, 1, 1);
			System.out.println(unbind);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    ////@Test
	public void getEmployeeRewardsTest() {
		try {
			RewardsResponse rewards = service.getEmployeeRewards(14, 39978, 0, 0);
			System.out.println(rewards);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    ////@Test
	public void setEmployeeCustomInfoTest() {
		try {
			Result result = service.setEmployeeCustomInfo(28207607, "[{\"10\":[\"1\"]},{\"8\":[\"\u804c\u4f4d\u4fe1\u606f2\"]}]");
			System.out.println(result.toString());
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    ////@Test
	public void getEmployeeCustomFieldsConfTest() {
		try {
			java.util.List<EmployeeCustomFieldsConf> list = service.getEmployeeCustomFieldsConf(2878);
			System.out.println(Arrays.toString(list.toArray()));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    ////@Test
	public void bindTest() {
		BindingParams bp = new BindingParams();
		bp.setEmail("510340677@qq.com");
		bp.setCompanyId(2878);
		bp.setType(BindType.EMAIL);
		bp.setUserId(2376);
		bp.setName("小飞");
//		bp.setAnswer1("上海");
//		bp.setAnswer2("仟寻");
//		bp.setCompanyId(2878);
//		bp.setType(BindType.QUESTIONS);
//		bp.setUserId(1122611);
		try {
			Result result = service.bind(bp,1);
			System.out.println(result);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    ////@Test
	public void emailActivationTest() {
		try {
			Result result = service.emailActivation("ff24a4dc10362b20",1);
			System.out.println(result);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
