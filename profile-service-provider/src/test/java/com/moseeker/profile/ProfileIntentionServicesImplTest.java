package com.moseeker.profile;

import com.moseeker.profile.conf.AppConfig;
import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.ProfileIntentionService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Intention;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Profile 求职意向 客户端 测试类
 *
 * Created by zzh on 16/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProfileIntentionServicesImplTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProfileIntentionService profileIntentionService;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    @Test
    public void testGetResources() {
        CommonQuery query = new CommonQuery();
        Map<String, String> equelFilter = new HashMap<>();
        equelFilter.put("profile_id", "3");
        query.setEqualFilter(equelFilter);
        try {
            Response response = profileIntentionService.getResources(query);
            System.out.println(response);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testGetPagination() {
        CommonQuery query = new CommonQuery();
        query.setPage(2);
        try {
            Response response = profileIntentionService.getPagination(query);
            System.out.println(response);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testPostResource() {
        Intention intention = new Intention();
        intention.setProfile_id(3);
        intention.setCities(new HashMap<String, Integer>(){{
            this.put("石家庄1", 130100);
            this.put("秦皇岛1", 130300);
            this.put("邢台1", 130500);
        }});
        intention.setIndustries(new HashMap<String ,Integer>(){{
            this.put("计算机软件1", 1101);
            this.put("通信/电信/网络设备1", 1104);
            this.put("网络游戏1", 1107);
        }});
        intention.setPositions(new HashMap<String, Integer>(){{
            this.put("高级软件工程师1", 110101);
            this.put("系统工程师1", 110106);
        }});
        intention.setSalary_code(3);
        intention.setSalary_type((short)0);

        try {
            Response response = profileIntentionService.postResource(intention);
            System.out.println(response);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testPutResource() {
        Intention intention = new Intention();
        intention.setProfile_id(3);
        intention.setCities(new HashMap<String, Integer>(){{
            this.put("石家庄1", 130100);
            this.put("秦皇岛1", 130300);
            this.put("邢台1", 130500);
        }});
        intention.setIndustries(new HashMap<String ,Integer>(){{
            this.put("计算机软件1", 1101);
            this.put("通信/电信/网络设备1", 1104);
            this.put("网络游戏1", 1107);
        }});
        intention.setPositions(new HashMap<String, Integer>(){{
            this.put("高级软件工程师1", 110101);
            this.put("系统工程师1", 110106);
        }});
        intention.setSalary_code(3);
        intention.setSalary_type((short)0);

        try {
            Response response = profileIntentionService.postResource(intention);
            System.out.println(response);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
