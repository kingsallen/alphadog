package com.moseeker.position.service.appbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.config.AppConfig;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionBSTest {

    @Autowired
    PositionBS positionBS;

    @Test
    public void test() throws Exception {
        String json="{\"appid\":4,\"channels\":[\"{\\\"occupation\\\":[\\\"1066\\\",\\\"1067\\\"],\\\"channel\\\":7,\\\"subsite\\\":\\\"监\n" +
                "理英才网\\\",\\\"salaryBottom\\\":10,\\\"salaryTop\\\":20}\"],\"channelsIterator\":[\"{\\\"occupation\\\":[\\\"1066\\\",\\\"1067\\\"],\\\"channel\\\":7,\\\"subsite\\\":\\\"监理英才网\\\",\\\"salaryBottom\\\":10,\\\"salaryTop\\\":20}\"],\"channelsSize\":1,\"positionId\":10,\"setAppid\":true,\"setChannels\":true,\"setPositionId\":true}\n";
        ThirdPartyPositionForm form= JSON.toJavaObject(JSON.parseObject(json),ThirdPartyPositionForm.class);
        positionBS.syncPositionToThirdParty(form);
    }

}