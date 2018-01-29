package com.moseeker.position.service.schedule;

import com.moseeker.position.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyPositionParamRefreshTest {

    @Autowired
    ThirdPartyPositionParamRefresh refresh;

    @Test
    public void init() throws Exception {
        refresh.init();
    }

}