package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.job51.Job51PositionTransfer;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
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

    @Autowired
    Job51PositionTransfer job51PositionTransfer;

    @Test
    public void test(){
        JobPositionDO positionDB = new JobPositionDO();
        positionDB.setId(2001946);
        job51PositionTransfer.getCities(positionDB);
    }
}