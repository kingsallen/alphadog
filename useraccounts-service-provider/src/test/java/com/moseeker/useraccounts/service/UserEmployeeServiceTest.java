package com.moseeker.useraccounts.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zztaiwll on 18/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserEmployeeServiceTest {
    @Autowired
    private UserEmployeeServiceImpl userEmployeeService;

    @Test
    public void getUserEmployeeTest(){
        int companyId=39978;
        String email="1";
        List<UserEmployeeDO> list=userEmployeeService.getUserEmployeeEmailValidate(companyId,email);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void getPastUserEmployee(){
        int companyId=39978;
        List<UserEmployeeDO> list =userEmployeeService.getPastUserEmployeeEmail(companyId);
        System.out.println(JSON.toJSONString(list));
    }
}
