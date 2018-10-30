package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jack on 15/03/2017.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class ProfileOtherThriftServiceImplTest {

    @Autowired
    ProfileOtherThriftServiceImpl service;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }

    ////@Test
    public void getResources() throws TException {
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
        response = service.getResources(commonQuery);
    }

    ////@Test
    public void getResource() throws TException {
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setEqualFilter(new HashMap<String,String>(){{put("profile_id","170");}});
        response = service.getResource(commonQuery);
    }

    ////@Test
    public void postResources() throws TException {
        ProfileOtherDO profileOtherDO = new ProfileOtherDO();
        profileOtherDO.setProfileId(171);
        profileOtherDO.setOther("resumeFileParser postResources");

        response = service.postResources(new ArrayList<ProfileOtherDO>(){{add(profileOtherDO);}});

    }

    ////@Test
    public void postResource() throws TException {
        ProfileOtherDO profileOtherDO = new ProfileOtherDO();
        profileOtherDO.setProfileId(170);
        profileOtherDO.setOther("resumeFileParser postResource");

        response = service.postResource(profileOtherDO);
    }

    ////@Test
    public void putResources() throws TException {
        ProfileOtherDO profileOtherDO = new ProfileOtherDO();
        profileOtherDO.setProfileId(170);
        profileOtherDO.setOther("resumeFileParser putResources");
        response = service.putResources(new ArrayList<ProfileOtherDO>(){{add(profileOtherDO);}});
    }

    ////@Test
    public void putResource() throws TException {
        ProfileOtherDO profileOtherDO = new ProfileOtherDO();
        profileOtherDO.setProfileId(170);
        profileOtherDO.setOther("resumeFileParser putResource");
        response = service.putResource(profileOtherDO);
    }

    ////@Test
    public void delResources() throws TException {
        ProfileOtherDO profileOtherDO = new ProfileOtherDO();
        profileOtherDO.setProfileId(170);
        response = service.delResources(new ArrayList<ProfileOtherDO>(){{add(profileOtherDO);}});
    }

    ////@Test
    public void delResource() throws TException {
        ProfileOtherDO profileOtherDO = new ProfileOtherDO();
        profileOtherDO.setProfileId(171);
        response = service.delResource(profileOtherDO);
    }
}
