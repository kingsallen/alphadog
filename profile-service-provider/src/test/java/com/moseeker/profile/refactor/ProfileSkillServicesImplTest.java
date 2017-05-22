package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.conf.AppConfig;
import com.moseeker.profile.service.impl.ProfileSkillService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileSkillServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.SkillServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Skill;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileSkillServicesImplTest {

    @Autowired
    ProfileSkillServicesImpl service;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }


    @Test
    public void postResources() throws TException {
        try {
            response = service.postResources(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void putResources() throws TException {
        try {
            response = service.putResources(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void delResources() throws TException {
        try {
            response = service.delResources(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void postResource() throws TException {
        try {
            response = service.postResource(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void putResource() throws TException {
        try {
            response = service.putResource(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void delResource() throws TException {
        try {
            response = service.delResource(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void getResources(CommonQuery query) throws TException {
        try {
            response = service.getResources(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void getPagination(CommonQuery query) throws TException {
        try {
            response = service.getPagination(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void getResource(CommonQuery query) throws TException {
        try {
            response = service.getResource(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
