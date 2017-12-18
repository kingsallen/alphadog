package com.moseeker.position.service.position.job51;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.config.AppConfig;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class Job51PositionTransferTest {

    @Autowired
    Job51PositionTransfer job51PositionTransfer;

    @Autowired
    JobPositionDao jobPositionDao;

    @Test
    public void test(){
        String json="{\n" +
                "  \"appid\": 4,\n" +
                "  \"channels\": [\n" +
                "    {\n" +
                "      \"addressId\": 38327,\n" +
                "      \"addressName\": \"淮北市淮北\",\n" +
                "      \"channel\": 1,\n" +
                "      \"companyId\": 2454,\n" +
                "      \"companyName\": \"上海和黄药业有限公司\",\n" +
                "      \"count\": 0,\n" +
                "      \"departmentId\": 0,\n" +
                "      \"feedbackPeriod\": 0,\n" +
                "      \"occupation\": [\n" +
                "        \"0\",\n" +
                "        \"200\",\n" +
                "        \"203\"\n" +
                "      ],\n" +
                "      \"occupationSize\": 3,\n" +
                "      \"practicePerWeek\": 0,\n" +
                "      \"practiceSalary\": 0,\n" +
                "      \"practiceSalaryUnit\": 0,\n" +
                "      \"salaryBottom\": 4,\n" +
                "      \"salaryDiscuss\": false,\n" +
                "      \"salaryMonth\": 0,\n" +
                "      \"salaryTop\": 8,\n" +
                "      \"thirdPartyAccountId\": 0,\n" +
                "      \"useCompanyAddress\": false\n" +
                "    }\n" +
                "  ],\n" +
                "\n" +
                "  \"positionId\": 1915084\n" +
                "}";
        ThirdPartyPositionForm form= JSON.toJavaObject(JSON.parseObject(json),ThirdPartyPositionForm.class);
        Query qu = new Query.QueryBuilder().where("id", form.positionId).buildQuery();
        JobPositionDO jobPositionDO = jobPositionDao.getData(qu);
        job51PositionTransfer.changeToThirdPartyPosition(form.getChannels().get(0),jobPositionDO);
    }
}