package com.moseeker.function.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.function.service.chaos.PositionForSyncResultPojo;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduledTaskTest {
    public static void main(String args[]){
        String data="{\n" +
                "  \"status\": 0,\n" +
                "  \"message\": \"[\\\"\\\\u53d1\\\\u5e03\\\\u6210\\\\u529f\\\"]\",\n" +
                "  \"data\": {\n" +
                "    \"accountId\": \"103\",\n" +
                "    \"channel\": \"1\",\n" +
                "    \"positionId\": \"318956\",\n" +
                "    \"jobId\": ['123','1233']\n" +
                "  }\n" +
                "}";
        PositionForSyncResultPojo pojo= JSON.parseObject(data, PositionForSyncResultPojo.class);
        System.out.println(JSONArray.parseArray(pojo.getData().getJobId()).toJSONString());
        System.out.println(JSON.toJSONString(pojo));
    }

}