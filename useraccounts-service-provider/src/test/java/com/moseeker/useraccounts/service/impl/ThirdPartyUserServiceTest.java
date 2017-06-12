package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import com.moseeker.useraccounts.thrift.ThirdPartyUserServiceImpl;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by eddie on 2017/3/8.
 */
public class ThirdPartyUserServiceTest {


//    ThirdPartyUserServiceImpl service;
//
//    public void init(){
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.scan("com.moseeker.useraccounts");
//        context.refresh();
//        service = context.getBean(ThirdPartyUserServiceImpl.class);
//    }
//
//    //@Test
//    public void testUpdateUser() throws TException {
//        init();
//        ThirdPartyUser user = new ThirdPartyUser();
//        user.setId(1);
//        user.setUsername("testUn1");
//        Response response = service.updateUser(user);
//        System.out.println(response);
//        Assert.assertEquals(response.getData(),"1");
//
//        user.setId(0);
//        user.setUsername("testUn1");
//        response = service.updateUser(user);
//        System.out.println(response);
//        Assert.assertEquals(response.getData(),"0");
//
//        response = service.updateUser(new ThirdPartyUser());
//        System.out.println(response);
//        Assert.assertEquals(response.getData(),"0");
//    }

}

