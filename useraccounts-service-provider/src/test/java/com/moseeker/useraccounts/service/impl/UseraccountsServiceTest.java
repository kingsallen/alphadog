package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.util.AopTargetUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.useraccounts.config.AppConfig;
import org.apache.thrift.TException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucky8987 on 17/5/17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class UseraccountsServiceTest {

    @Autowired
    private UseraccountsService service;

    @Mock
    SmsSender smsSender;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(service), "smsSender", smsSender);
        Mockito.when(smsSender.sendSMS_changemobilecode(Mockito.anyString())).thenReturn(true);
        Mockito.when(smsSender.sendSMS_resetmobilecode(Mockito.anyString())).thenReturn(true);
        Mockito.when(smsSender.sendSMS_passwordforgot(Mockito.anyString())).thenReturn(true);
    }

    //@Test
    @Transactional
    public void postuserlogin() throws Exception {
        Userloginreq userloginreq = new Userloginreq();
        userloginreq.setPassword("12345678");
        userloginreq.setMobile("13020287221");
        Response response = service.postuserlogin(userloginreq);
        System.out.println(response);
    }


    //@Test
    @Transactional
    public void postuserchangepassword() throws Exception {
        Response response = service.postuserchangepassword(1122611, "123456", "123456");
        System.out.println(response);
    }

    //@Test
    public void postuserresetpassword() throws Exception {
        Response response = service.postuserresetpassword("13020287221", "898781415", "4000");
        System.out.println(response);
    }

    //@Test
    public void postusersendpasswordforgotcode() throws Exception {
        Response response = service.postusersendpasswordforgotcode("13020287221");
        System.out.println(response);
    }

    //@Test
    public void postusermobilesignup() throws Exception {
        User user = new User();
        user.setMobile(13020287221L);
        Response response = service.postusermobilesignup(user, "2764");
        System.out.println(response);
    }

    //@Test
    public void getUserFavPositionCountByUserIdAndPositionId() throws Exception {

    }

    //@Test
    public void postUserFavoritePosition() throws Exception {

    }


    //@Test
    public void checkEmail() throws Exception {
        Response response = service.checkEmail("510340677@qq.com");
        System.out.println(response);
    }


    //@Test
    public void ifExistUser() throws Exception {
        UserUserDO userUserDO = service.ifExistUser("13020287221");
        System.out.println(userUserDO);
    }

    //@Test
    public void createRetrieveProfileUser() throws Exception {

    }

    //@Test
    public void ifExistProfile() throws Exception {
        boolean existProfile = service.ifExistProfile("13020287221");
        System.out.println(existProfile);
    }

}