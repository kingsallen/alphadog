package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.useraccounts.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserHrAccountServiceTest {

    @Autowired
    private UserHrAccountService userHrAccountService;


    //@Test
    public void testUserHrAccount() {
        try {
            Response response = userHrAccountService.userHrAccount(1, 1, 1, 20);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getListNum() {
        try {
            Response response = userHrAccountService.getListNum("Fz,", 3);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
