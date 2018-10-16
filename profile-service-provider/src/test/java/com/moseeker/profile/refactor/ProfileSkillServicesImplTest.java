package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileSkillService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileSkillServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.SkillServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Skill;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class ProfileSkillServicesImplTest {

    @Autowired
    ProfileSkillServicesImpl service;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }


    ////@Test
    public void postResources() throws TException {
        try {
            Skill skill = new Skill();
            skill.setProfile_id(170);
            skill.setLevel(Short.valueOf("1"));
            skill.setName("resumeFileParser postResources");
            response = service.postResources(new ArrayList<Skill>(){{add(skill);add(skill);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResources() throws TException {
        try {

            Skill skill = new Skill();
            skill.setId(90121);
            skill.setName("resumeFileParser putResources");

            Skill skill2 = new Skill();
            skill2.setId(90122);
            skill2.setName("resumeFileParser putResources2");

            response = service.putResources(new ArrayList<Skill>(){{add(skill);add(skill2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResources() throws TException {
        try {
            Skill skill = new Skill();
            skill.setId(90121);
            Skill skill2 = new Skill();
            skill2.setId(90122);
            response = service.delResources(new ArrayList<Skill>(){{add(skill);add(skill2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResource() throws TException {
        try {
            Skill skill = new Skill();
            skill.setProfile_id(170);
            skill.setLevel(Short.valueOf("1"));
            skill.setName("resumeFileParser postResource");
            response = service.postResource(skill);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResource() throws TException {
        try {
            Skill skill = new Skill();
            skill.setId(90123);
            skill.setName("resumeFileParser putResource");
            response = service.putResource(skill);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResource() throws TException {
        try {
            Skill skill = new Skill();
            skill.setId(90123);
            response = service.delResource(skill);
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
            commonQuery.setEqualFilter(new HashMap<String,String>(){{put("id","90123");}});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
