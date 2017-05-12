package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.useraccounts.dao.UserHrDao;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserHrAccountServiceTest {


    @Mock
    private UserHrDao userHrDao;

    @InjectMocks
    private UserHrAccountService userHrAccountService;


    @Test
    public void testUserHrAccount() {
        try {
            Response response = userHrAccountService.userHrAccount(1, 1, 1, 20);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
