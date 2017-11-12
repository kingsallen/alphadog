package com.moseeker.position;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.schedule.VeryEastParamRefresh;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class VeryEastParamRefreshTest {

    @Autowired
    VeryEastParamRefresh refresh;

    @Test
    public void test(){
        refresh.send();
    }
}
