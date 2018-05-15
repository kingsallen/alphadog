package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.function.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionSyncConsumerTest {

    @Autowired
    PositionSyncConsumer consumer;

    @Test
    public void positionSyncComplete() throws Exception {
        String json = "{\"data\":{\"channel\":\"3\",\"accountId\":\"741\",\"positionId\":\"1949059\"},\"message\":\"[\\\"\\\\u672a\\\\u77e5\\\\u9519\\\\u8bef\\\"]\",\"operation\":\"publish\",\"status\":2}";
        PositionForSyncResultPojo resultPojo = JSON.parseObject(json,PositionForSyncResultPojo.class);
        consumer.positionSyncComplete(resultPojo);
    }

}