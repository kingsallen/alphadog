package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileProjectExpService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileProjectExpServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
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
public class ProfileProjectExpServicesImplTest {

    @Autowired
    ProfileProjectExpServicesImpl service;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }


    ////@Test
    public void postResources() throws TException {
        try {
            ProjectExp projectExp = new ProjectExp();
            projectExp.setProfile_id(170);
            projectExp.setStart_date("2017-01-01");
            projectExp.setName("resumeFileParser postResources");
            response = service.postResources(new ArrayList<ProjectExp>(){{add(projectExp);add(projectExp);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResources() throws TException {
        try {

            ProjectExp projectExp = new ProjectExp();
            projectExp.setId(140556);
            projectExp.setName("resumeFileParser putResources");

            ProjectExp projectExp2 = new ProjectExp();
            projectExp2.setId(140557);
            projectExp2.setName("resumeFileParser putResources2");

            response = service.putResources(new ArrayList<ProjectExp>(){{add(projectExp);add(projectExp2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResources() throws TException {
        try {
            ProjectExp projectExp = new ProjectExp();
            projectExp.setId(140556);
            ProjectExp projectExp2 = new ProjectExp();
            projectExp2.setId(140557);
            response = service.delResources(new ArrayList<ProjectExp>(){{add(projectExp);add(projectExp2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResource() throws TException {
        try {
            ProjectExp projectExp = new ProjectExp();
            projectExp.setProfile_id(170);
            projectExp.setStart_date("2017-01-01");
            projectExp.setName("resumeFileParser postResource");
            response = service.postResource(projectExp);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResource() throws TException {
        try {
            ProjectExp projectExp = new ProjectExp();
            projectExp.setId(140558);
            projectExp.setName("resumeFileParser putResource");
            response = service.putResource(projectExp);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResource() throws TException {
        try {
            ProjectExp projectExp = new ProjectExp();
            projectExp.setId(140558);
            response = service.delResource(projectExp);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
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

    ////@Test
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

    ////@Test
    public void getResource() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String,String>(){{put("id","140558");}});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
