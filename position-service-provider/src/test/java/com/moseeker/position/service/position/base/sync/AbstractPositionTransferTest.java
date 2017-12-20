package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class AbstractPositionTransferTest {
    public static void main(String arg[]){
        String json="{" +
                "\"address\":\"上海\"," +
                "\"addressName\":{" +
                "\"city\":\"长春市双阳区\"," +
                    "\"address\":\"test\"" +
                "}," +
                "\"channel\":1," +
                "\"companyName\":\"上海大岂网络科技有限公司\"," +
                "\"occupation\":[0, 3100, 3103]," +
                "\"salaryBottom\":0," +
                "\"salaryTop\":0" +
                "}";
        JSONObject jsonObject=JSONObject.parseObject(json);
        ThirdPartyPosition temp=JSONObject.parseObject(json,ThirdPartyPosition.class);
        System.out.println(JSON.toJSONString(temp));
    }
}