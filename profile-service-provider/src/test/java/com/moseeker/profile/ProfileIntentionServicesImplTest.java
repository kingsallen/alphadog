package com.moseeker.profile;

import com.moseeker.profile.conf.AppConfig;
import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.ProfileIntentionService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
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
        try {
            Response response = profileIntentionService.getResources(query);
            System.out.println(response);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
