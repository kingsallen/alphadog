package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.AopTargetUtils;
import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.useraccounts.config.AppConfig;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucky8987 on 17/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeServiceTest {

    @Autowired
    EmployeeService service;

    @Mock
    MqService.Iface mqService;

    @Mock
    RedisClient client;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        // EmployeeService 是通过aop代理的bean对象，所以要通过AopTargetUtils获取bean本身然后将mock的对象设置进去
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(service), "mqService", mqService);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(service), "client", client);
        Mockito.when(mqService.sendAuthEMail(Mockito.anyMap(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(ResponseUtils.success("success"));
        Mockito.when(client.set(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn("insert success");
        Mockito.when(client.get(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    }

    //@Test
    public void getEmployee() throws Exception {
        EmployeeResponse employee = service.getEmployee(4, 32);
        System.out.println(employee);
    }

    //@Test
    public void getEmployeeVerificationConf() throws Exception {
        EmployeeVerificationConfResponse response = service.getEmployeeVerificationConf(27);
        System.out.println(response);
    }

    //@Test
    @Transactional
    public void bind() throws Exception {
        BindingParams bp = new BindingParams();
        bp.setEmail("510340677@qq.com");
        bp.setCompanyId(2878);
        bp.setType(BindType.EMAIL);
        bp.setUserId(2376);
        bp.setName("小飞");
        Result result = service.bind(bp);
        System.out.println(result);
    }

    //@Test
    @Transactional
    public void unbind() throws Exception {
        Result result = service.unbind(12528, 14, 143999);
        System.out.println(result);
    }

    //@Test
    public void getEmployeeCustomFieldsConf() throws Exception {
        EmployeeVerificationConfResponse response = service.getEmployeeVerificationConf(650);
        System.out.println(response);
    }

    //@Test
    public void getEmployeeRewards() throws Exception {
        RewardsResponse response = service.getEmployeeRewards(14, 39978);
        System.out.println(response);
    }

    //@Test
    @Transactional
    public void setEmployeeCustomInfo() throws Exception {
        Result result = service.setEmployeeCustomInfo(28002227, "[{\"10\":[\"1\"]},{\"8\":[\"\u804c\u4f4d\u4fe1\u606f2\"]}]");
        System.out.println(result);
    }

    //@Test
    @Transactional
    public void emailActivation() throws Exception {
        //Result result = service.emailActivation("1d6006dbb2296da2");
        //System.out.println(result);
    }

//    @Test
    public void awardRankingTest() {
        List<EmployeeAward> response = service.awardRanking(142104, 32, "2017");
        System.out.println(response);
    }
}