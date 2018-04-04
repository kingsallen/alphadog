package com.moseeker.position.service.position.base.sync.verify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class MobileVeifyHandlerTest extends MobileVeifyHandler{

    @Test
    public void test() throws BIZException {
        String json = "{\"accountId\":\"746\",\"positionId\":\"1925265\",\"channel\":\"1\",\"mobile\":\"13701892505\",\"operation\":\"publish\"}";
        JSONObject obj = JSON.parseObject(json);
        receive(obj);
    }
}