package com.moseeker.company.thrift;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;
import org.apache.thrift.TException;

import java.util.List;

public class CompanyThriftServiceTest {

	CompanyServices.Iface companyService = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);


	//@Test
	public void testGetGroupCompanies() {
		try {
			List<CompanyForVerifyEmployee> companyForVerifyEmployeeList = companyService.getGroupCompanies(16);
			System.out.println(companyForVerifyEmployeeList);
		} catch (TException e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void testIsGroupCompanies() {
		try {
			boolean result = companyService.isGroupCompanies(3);
			System.out.println(result);
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
