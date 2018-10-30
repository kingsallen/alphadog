package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.thrift.ProfileLanguageServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Language;

import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class ProfileLanguageServicesImplTest {

    @Autowired
    ProfileLanguageServicesImpl service;

    Response response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }

    ////@Test
    public void postResources() throws TException {
        try {
            Language language = new Language();
            language.setProfile_id(170);
            language.setLevel(Short.valueOf("1"));
            language.setName("resumeFileParser postResources");
            response = service.postResources(new ArrayList<Language>(){{add(language);add(language);}});
        } catch (Exception e) {
            e.printStackTrace();
            
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResources() throws TException {
        try {

            Language language = new Language();
            language.setId(108611);
            language.setName("resumeFileParser putResources");

            Language language2 = new Language();
            language2.setId(108612);
            language2.setName("resumeFileParser putResources2");

            response = service.putResources(new ArrayList<Language>(){{add(language);add(language2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResources() throws TException {
        try {
            Language language = new Language();
            language.setId(108611);
            Language language2 = new Language();
            language2.setId(108612);
            response = service.delResources(new ArrayList<Language>(){{add(language);add(language2);}});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResource() throws TException {
        try {
            Language language = new Language();
            language.setProfile_id(170);
            language.setLevel(Short.valueOf("1"));
            language.setName("resumeFileParser postResource");
            response = service.postResource(language);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResource() throws TException {
        try {
            Language language = new Language();
            language.setId(108613);
            language.setName("resumeFileParser putResource");
            response = service.putResource(language);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResource() throws TException {
        try {
            Language language = new Language();
            language.setId(108613);
            response = service.delResource(language);
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
            commonQuery.setEqualFilter(new HashMap<String,String>(){{put("id","108616");}});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}