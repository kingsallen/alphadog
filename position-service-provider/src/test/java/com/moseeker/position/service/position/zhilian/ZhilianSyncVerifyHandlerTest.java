package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ZhilianSyncVerifyHandlerTest {
    @Autowired
    ZhilianSyncVerifyHandler zhilianSyncVerifyHandler;

    @Test
    public void test() throws BIZException {
        JSONObject jsonObject= JSONObject.parseObject("{\"accountId\":\"522\",\"positionId\":\"1910934\",\"appid\":\"7\",\"channel\":\"3\",\"paramId\":\"86125fe7-7254-4fd6-a1f9-7c9cc665743a\",\"info\":\"dfdf\"}\n");
//        zhilianSyncVerifyHandler.syncVerifyInfo(jsonObject);
    }
}