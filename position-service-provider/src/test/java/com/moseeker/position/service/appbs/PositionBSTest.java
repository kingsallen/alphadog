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
        String json="{\"appid\":4,\"channels\":[\"{\\\"companyId\\\":31,\\\"occupation\\\":[\\\"5100\\\",\\\"5120\\\"],\\\"companyName\\\":\\\"上海大岂网络科技有限公司\\\",\\\"channel\\\":1,\\\"addressName\\\":\\\"长春市双阳区test\\\",\\\"salaryBottom\\\":10,\\\"salaryTop\\\":20,\\\"addressId\\\":152}\"],\"channelsIterator\":[\"{\\\"companyId\\\":31,\\\"occupation\\\":[\\\"5100\\\",\\\"5120\\\"],\\\"companyName\\\":\\\"上海大岂网络科技有限公司\\\",\\\"channel\\\":1,\\\"addressName\\\":\\\"长春市双阳区test\\\",\\\"salaryBottom\\\":10,\\\"salaryTop\\\":20,\\\"addressId\\\":152}\"],\"channelsSize\":1,\"positionId\":10,\"requestType\":1,\"setAppid\":true,\"setChannels\":true,\"setPositionId\":true,\"setRequestType\":true}\n";
        ThirdPartyPositionForm form= JSON.toJavaObject(JSON.parseObject(json),ThirdPartyPositionForm.class);
        positionBS.syncPositionToThirdParty(form);

//        String json="{\"paramId\":\"86125fe7-7254-4fd6-a1f9-7c9cc665743a\"}";
//
//        positionBS.getVerifyParam(json);
    }

}