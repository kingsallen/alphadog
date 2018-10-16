package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileWorksService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileWorksServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WorksServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Works;
import com.moseeker.thrift.gen.profile.struct.Works;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class ProfileWorksServicesImplTest {

    @Autowired
    ProfileWorksServicesImpl service;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }

//    //@Test
    public void postResources() throws TException {
        try {
            Works work = new Works();
            work.setProfile_id(170);
            work.setName("resumeFileParser postResources");
            response = service.postResources(new ArrayList<Works>() {{
                add(work);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void putResources() throws TException {
        try {

            Works work = new Works();
            work.setId(60066);
            work.setName("resumeFileParser putResources");

            response = service.putResources(new ArrayList<Works>() {{
                add(work);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void delResources() throws TException {
        try {
            Works work = new Works();
            work.setId(60066);
            response = service.delResources(new ArrayList<Works>() {{
                add(work);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void postResource() throws TException {
        try {
            Works work = new Works();
            work.setProfile_id(170);
            work.setName("resumeFileParser postResource");
            response = service.postResource(work);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void putResource() throws TException {
        try {
            Works work = new Works();
            work.setId(60067);
            work.setName("resumeFileParser putResource");
            response = service.putResource(work);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void delResource() throws TException {
        try {
            Works work = new Works();
            work.setId(60067);
            response = service.delResource(work);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void getResources() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("profile_id", "170");
            }});
            response = service.getResources(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void getPagination() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("profile_id", "170");
            }});
            response = service.getPagination(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void getResource() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("id", "60067");
            }});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
