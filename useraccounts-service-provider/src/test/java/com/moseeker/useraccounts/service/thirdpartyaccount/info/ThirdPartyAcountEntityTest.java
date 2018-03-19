package com.moseeker.useraccounts.service.thirdpartyaccount.info;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.entity.pojos.ThirdPartyInfoData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAcountEntityTest {

    @Autowired
    ThirdPartyAcountEntity thirdPartyAcountEntity;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Test
    public void test(){
        String department="{\n" +
                "  \"accountId\": 538,\n" +
                "  \"cities\": [\n" +
                "    {\n" +
                "      \"amount\": 48,\n" +
                "      \"area\": \"北京\",\n" +
                "      \"expiryDate\": \"2018-06-30\",\n" +
                "      \"jobType\": \"社会职位\",\n" +
                "      \"jobTypeInt\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"amount\": 240,\n" +
                "      \"area\": \"上海\",\n" +
                "      \"expiryDate\": \"2018-05-31\",\n" +
                "      \"jobType\": \"社会职位\",\n" +
                "      \"jobTypeInt\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"amount\": 30,\n" +
                "      \"area\": \"实习\",\n" +
                "      \"expiryDate\": \"2018-06-21\",\n" +
                "      \"jobType\": \"实习职位\",\n" +
                "      \"jobTypeInt\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"operationType\": 2\n" +
                "}";

        ThirdPartyInfoData data = JSON.parseObject(department,ThirdPartyInfoData.class);

        HrThirdPartyAccountDO accountDO = thirdPartyAccountDao.getAccountById(538);
        thirdPartyAcountEntity.saveAccountExt(data,accountDO);

    }
}