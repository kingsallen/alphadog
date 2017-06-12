package com.moseeker.function.service.hraccount;

import com.moseeker.function.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by lucky8987 on 17/5/11.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class HRAccountServiceTest {

//    @Autowired
    private HRAccountService service;

    //@Test
    public void allowBind() throws Exception {
        Response response = service.allowBind(81654, 0, (byte) 3);
        System.out.println(response);
    }

    //@Test
    @Transactional
    public void createThirdPartyAccount() throws Exception {
        BindAccountStruct struct = new BindAccountStruct();
        struct.setUsername("dqwl805");
        struct.setCompany_id(8);
        struct.setPassword("2892c63f12e0e8849f2a7dd981375331");
        struct.setBinding(0);
        Response response = service.createThirdPartyAccount(struct);
        System.out.println(response);
    }

}