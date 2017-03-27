package com.moseeker.profile;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.service.ProfileProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import org.apache.thrift.TException;
import org.junit.Test;

/**
 * Created by moseeker on 2017/3/16.
 */
public class ProfileProfileDaoTest {


    @Test
    public void testCompany() throws TException {
        CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);
        Response response = companyServices.getAllCompanies(new CommonQuery());
        System.out.println(response);
    }

    @Test
    public void testUser() throws TException {

        UserEmployeeService.Iface useremployee = ServiceManager.SERVICEMANAGER.getService(UserEmployeeService.Iface.class);
        Response response = useremployee.getUserEmployees(new CommonQuery());
        System.out.println(response);
    }

    @Test
    public void testProfile() throws TException {

        ProfileServices.Iface profile = ServiceManager.SERVICEMANAGER.getService(ProfileServices.Iface.class);
        Response response = profile.getProfileByApplication(107604,0,0,true,false);
        System.out.println(response);
    }
}
