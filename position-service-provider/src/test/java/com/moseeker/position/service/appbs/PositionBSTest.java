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

import java.util.List;

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

    String json = "[{\"positionId\":19509969,\"appid\":4,\"channels\":[{\"addressId\":19217,\"addressName\":\"海南省项目部现场\",\"channel\":1,\"companyId\":1303,\"companyName\":\"上海大岂网络科技有限公司\",\"occupation\":[[\"0400\",\"0450\"],[\"0400\",\"0401\"],[\"0400\",\"0445\"]],\"departmentName\":\"CS\",\"salaryBottom\":\"10\",\"salaryTop\":\"11\",\"internship\":true}]},{\"positionId\":19512099,\"appid\":4,\"channels\":[{\"addressId\":19217,\"addressName\":\"海南省项目部现场\",\"channel\":1,\"companyId\":1303,\"companyName\":\"上海大岂网络科技有限公司\",\"occupation\":[[\"0400\",\"0450\"],[\"0400\",\"0401\"],[\"0400\",\"0445\"]],\"departmentName\":\"CS\",\"salaryBottom\":\"10\",\"salaryTop\":\"11\",\"internship\":true}]}]";

    @Test
    public void syncPositionToThirdParty() throws Exception {
        List<ThirdPartyPositionForm> positionForms = JSON.parseArray(json,ThirdPartyPositionForm.class);
        System.out.println(JSON.toJSONString(positionBS.syncPositionToThirdParty(positionForms)));
    }

}