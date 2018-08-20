package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.dao.struct.userdb.UserRecommendRefusalDO;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserRecommendRefusalServiceTest {

    @Autowired
    UserRecommendRefusalService userRecommendRefusalService;

    @Test
    public void refuseRecommend() throws Exception {
        UserRecommendRefusalDO userRecommendRefusalDO = new UserRecommendRefusalDO();

        userRecommendRefusalDO.setUserId(1);
        userRecommendRefusalDO.setWechatId(1);

        userRecommendRefusalService.refuseRecommend(userRecommendRefusalDO);
    }

}