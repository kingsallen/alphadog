package com.moseeker.profile.refactor;

import com.alibaba.fastjson.JSON;
import com.moseeker.profile.conf.AppConfig;
import com.moseeker.profile.thrift.ProfileAwardsServicesImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Awards;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileAwardsServicesImplTest {

    @Autowired
    ProfileAwardsServicesImpl service;

    Response response;

    @After
    public void printResponse() {
        System.out.println(JSON.toJSONString(response));
    }

    //@Test
    public void postResources() throws TException {
        Awards awards = new Awards();
        awards.setName("testAwards-");
        awards.setDescription("testAwardsDescription");
        awards.setLevel("testLevle");
        awards.setProfile_id(170);
        response = service.postResources(new ArrayList<Awards>(){{add(awards);}});
    }

    //@Test
    public void putResources() throws TException {
        Awards awards = new Awards();
        awards.setName("testAwards-----");
        awards.setDescription("testAwardsDescription----");
        awards.setLevel("testLevle---");
        awards.setId(20266);

        Awards awards1 = new Awards();
        awards1.setName("testAwards-----");
        awards1.setDescription("testAwardsDescription----");
        awards1.setLevel("testLevle---");
        awards1.setId(20267);

        response = service.putResources(new ArrayList<Awards>(){{add(awards);add(awards1);}});
    }

    //@Test
    public void delResources() throws TException {
        Awards awards = new Awards();
        awards.setId(20266);
        Awards awards1 = new Awards();
        awards1.setId(20267);
        response = service.delResources(new ArrayList<Awards>(){{add(awards);add(awards1);}});
    }

    //@Test
    public void postResource() throws TException {
        Awards awards = new Awards();
        awards.setName("testAwards");
        awards.setDescription("testAwardsDescription");
        awards.setLevel("testLevle");
        awards.setProfile_id(170);
        response = service.postResource(awards);
    }

    //@Test
    public void putResource() throws TException {
        Awards awards = new Awards();
        awards.setName("testAwards---");
        awards.setDescription("testAwardsDescription---");
        awards.setLevel("testLevle---");
        awards.setId(20268);
        response = service.putResource(awards);
    }

    //@Test
    public void delResource() throws TException {
        Awards awards1 = new Awards();
        awards1.setId(20268);
        response = service.delResource(awards1);
    }

    //@Test
    public void getResources() throws TException {
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setEqualFilter(new HashMap<>());
        commonQuery.getEqualFilter().put("profile_id","-1");
        response = service.getResources(commonQuery);
    }

    //@Test
    public void getPagination() throws TException {
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setEqualFilter(new HashMap<>());
        commonQuery.getEqualFilter().put("profile_id","11");
        response = service.getPagination(commonQuery);
    }

    //@Test
    public void getResource() throws TException {
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setEqualFilter(new HashMap<>());
        commonQuery.getEqualFilter().put("profile_id","11");
        response = service.getResource(commonQuery);
    }
}
