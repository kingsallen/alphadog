package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileWorkExpService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileWorkExpServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WorkExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.WorkExp;
import com.moseeker.thrift.gen.profile.struct.WorkExp;
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
public class ProfileWorkExpServicesImplTest {

    @Autowired
    ProfileWorkExpServicesImpl service;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }


//    //@Test
    public void postResource() throws TException {
        try {
            WorkExp workExp = new WorkExp();
            workExp.setProfile_id(170);
            workExp.setDescription("resumeFileParser postResource");
            workExp.setPosition_name("resumeFileParser postResource");
            workExp.setCompany_name("resumeFileParser postResource");
            workExp.setJob("resumeFileParser postResource");
            workExp.setStart_date("2017-01-01");
            response = service.postResource(workExp);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void postResources() throws TException {
        try {
            WorkExp workExp = new WorkExp();
            workExp.setProfile_id(170);
            workExp.setDescription("resumeFileParser postResources");
            workExp.setPosition_name("resumeFileParser postResources");
            workExp.setCompany_name("resumeFileParser postResources");
            workExp.setJob("resumeFileParser postResources");
            workExp.setStart_date("2017-01-01");
            response = service.postResources(new ArrayList<WorkExp>(){{add(workExp);add(workExp);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }


//    //@Test
    public void putResource() throws TException {
        try {
            WorkExp workExp = new WorkExp();
            workExp.setId(322261);
            response = service.putResource(workExp);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void putResources() throws TException {
        try {

            WorkExp workExp = new WorkExp();
            workExp.setId(322262);

            WorkExp workExp2 = new WorkExp();
            workExp2.setId(322263);

            response = service.putResources(new ArrayList<WorkExp>(){{add(workExp);add(workExp2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void delResources() throws TException {
        try {
            WorkExp workExp = new WorkExp();
            workExp.setId(322262);
            WorkExp workExp2 = new WorkExp();
            workExp2.setId(322261);
            response = service.delResources(new ArrayList<WorkExp>(){{add(workExp);add(workExp2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void delResource() throws TException {
        try {
            WorkExp workExp = new WorkExp();
            workExp.setId(322263);
            response = service.delResource(workExp);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

//    //@Test
    public void getResources() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
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
            commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
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
            commonQuery.setEqualFilter(new HashMap<String,String>(){{put("id","322261");}});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
