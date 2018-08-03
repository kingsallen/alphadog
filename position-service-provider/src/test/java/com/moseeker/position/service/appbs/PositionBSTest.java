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
        String json="{\n" +
                "  \"appid\": 0,\n" +
                "  \"channels\": [\n" +
                "    \"{\\\"departmentName\\\":\\\"\\\",\\\"companyId\\\":39978,\\\"limitTitle\\\":true,\\\"occupation\\\":[[\\\"2200\\\",\\\"2221\\\"]],\\\"companyName\\\":\\\"寻仟信息科技(上海)有限公司\\\",\\\"channel\\\":1,\\\"addressName\\\":{\\\"address\\\":\\\"上海\\\",\\\"city\\\":\\\"上海市\\\"},\\\"salaryBottom\\\":8,\\\"salaryTop\\\":10,\\\"addressId\\\":0,\\\"internship\\\":false}\"\n" +
                "  ],\n" +
                "  \"channelsSize\": 1,\n" +
                "  \"positionId\": 19495726,\n" +
                "  \"requestType\": 2,\n" +
                "}";
        ThirdPartyPositionForm form= JSON.toJavaObject(JSON.parseObject(json),ThirdPartyPositionForm.class);
        positionBS.syncPositionToThirdParty(form);

//        String json="{\"paramId\":\"86125fe7-7254-4fd6-a1f9-7c9cc665743a\"}";
//
//        positionBS.getVerifyParam(json);
    }

}