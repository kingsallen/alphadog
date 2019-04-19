package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.validation.sensitivewords.SpecialCharCheck;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.mq.config.AppConfig;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by lucky8987 on 17/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmailProducerTest {

    @Autowired
    private MessageTemplateEntity messageTemplateEntity;

//    @Test
//    public void getTemplate(){
//        MessageTemplateNoticeStruct messageTemplateNoticeStruct=messageTemplateEntity.handlerTemplate(2192148,39978,57,3,
//                "https://platform-t.dqprism.com/m/employee/ai-recom/{recomPushId}?wechat_signature={}&companyId={companyId}");
//        System.out.println(JSON.toJSONString(messageTemplateNoticeStruct));
//    }


}