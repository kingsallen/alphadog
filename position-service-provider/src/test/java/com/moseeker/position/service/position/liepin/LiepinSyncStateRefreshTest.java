package com.moseeker.position.service.position.liepin;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.position.service.schedule.delay.refresh.LiepinSyncStateRefresh;
import com.moseeker.position.service.schedule.delay.PositionTaskQueueDaemonThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author cjm
 * @date 2018-07-04 16:24
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LiepinSyncStateRefreshTest {

    @Autowired
    PositionTaskQueueDaemonThread delayQueueThread;

    @Test
    public void testSyncStateRefresh() throws InterruptedException {
        PositionSyncStateRefreshBean refreshBean = new PositionSyncStateRefreshBean(3887, 2, 5 * 1000);

        delayQueueThread.put(5*1000, refreshBean);

        Thread.sleep(100000);
    }

}
