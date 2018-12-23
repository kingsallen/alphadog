package com.moseeker.mq.service.impl;

import com.moseeker.mq.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moseeker on 2017/12/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
public class ResumeDeliveryServiceTest {

//    @Autowired
    private ResumeDeliveryService delivery;

    @Autowired
    private TemplateMsgHttp http;

//    @Test
    public void sendMessageAndEmailTest(){
//        delivery.sendMessageAndEmail(460910);
    }

    @Test
    public void testSaveTenMinute() throws BIZException, ConnectException {
        List<Integer> positionIds = new ArrayList<>();
        positionIds.add(19501370);
        int visitnum = 5;
        int companyId = 39978;
        int employeeId = 884223;
        http.sendTenMinuteTemplate(positionIds, visitnum, companyId, employeeId);
    }
}
