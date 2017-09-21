package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.profile.thrift.ProfileOtherThriftServiceImpl;
import com.moseeker.profile.thrift.ProfileProjectExpServicesImpl;
import com.moseeker.profile.thrift.ProfileServicesImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Profile;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileServicesImplTest {

    @Autowired
    ProfileServicesImpl service;

    @Autowired
    ProfileService profileService;

    Object response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }


    ////@Test
    public void getResource() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("id", "170");
            }});
            response = service.getResource(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void getResources() throws TException {
        try {
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("completeness", "100");
            }});
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
            commonQuery.setEqualFilter(new HashMap<String, String>() {{
                put("completeness", "80");
            }});
            response = service.getPagination(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResource() throws TException {
        try {
            //165890	5d311bd8-53fa-4064-8324-eac6299675d4	1	0	80	2113186	1	2017-05-15 19:30:03	2017-05-15 19:29:47	1000000000000000
            Profile profile = new Profile();
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4");
            profile.setLang(1);
            profile.setSource(0);
            profile.setCompleteness(80);
            profile.setUser_id(2113186);
            profile.setDisable(new Integer(1).shortValue());
            response = service.postResource(profile);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void postResources() throws TException {
        try {
            Profile profile = new Profile();
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4");
            profile.setLang(1);
            profile.setSource(0);
            profile.setCompleteness(80);
            profile.setUser_id(2113186);
            profile.setDisable(new Integer(1).shortValue());
            response = service.postResources(new ArrayList<Profile>() {{
                add(profile);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void getCompleteness() throws TException {
        try {
            response = service.getCompleteness(2113186, null, 165992);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void reCalculateUserCompleteness() throws TException {
        try {
            response = service.reCalculateUserCompleteness(2, null);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void reCalculateUserCompletenessBySettingId() throws TException {
        try {
            response = service.reCalculateUserCompletenessBySettingId(2);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void getProfileByApplication() throws TException {
        try {

        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResources() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165992);
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4----");
            response = service.putResources(new ArrayList<Profile>() {{
                add(profile);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void putResource() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165992);
            profile.setUuid("5d311bd8-53fa-4064-8324-eac6299675d4");
            response = service.putResource(profile);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResources() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165992);
            response = service.delResources(new ArrayList<Profile>() {{
                add(profile);
            }});
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    ////@Test
    public void delResource() throws TException {
        try {
            Profile profile = new Profile();
            profile.setId(165993);
            response = service.delResource(profile);
        } catch (Exception e) {
            e.printStackTrace();

            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Test
    public void getProfileOtherTest() {
        Response response = profileService.getProfileOther("[{'positionId': 134295, 'profileId': 63999}, {'positionId': 127311, 'profileId': 63999}, {'positionId': 134129, 'profileId': 63999}]");
        System.out.println(response);
    }

    @Test
    public void profileCheckTest() {
        Response response = profileService.checkProfileOther(676202,11896);
        System.out.println(response);
    }

}
