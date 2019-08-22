package com.moseeker.profile;

import com.moseeker.profile.service.impl.vo.MobotReferralResultVO;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import org.apache.thrift.TException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2017/3/16.
 */
public class ProfileProfileDaoTest {


    ////@Test
    public void testCompany() throws TException {
        CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);
        Response response = companyServices.getAllCompanies(new CommonQuery());
        System.out.println(response);
    }

    ////@Test
    public void testUser() throws TException {

        UserEmployeeService.Iface useremployee = ServiceManager.SERVICE_MANAGER.getService(UserEmployeeService.Iface.class);
        Response response = useremployee.getUserEmployees(new CommonQuery());
        System.out.println(response);
    }

    ////@Test
//    public void testProfile() throws TException {
//
//        ProfileServices.Iface profile = ServiceManager.SERVICE_MANAGER.getService(ProfileServices.Iface.class);
//        Response response = profile.getProfileByApplication(107604,0,0,true,false);
//        System.out.println(response);
//    }


    @Test
    public void test(){
        List<MobotReferralResultVO> referralResultVOS = new ArrayList<>();
//        List<Integer> referralIds = referralResultVOS.stream().map(MobotReferralResultVO::getId).collect(Collectors.toList());
        referralResultVOS = referralResultVOS.stream().filter(e ->  e.getSuccess()==true).collect(Collectors.toList());
        //referralIds.get(0);
    }
}
