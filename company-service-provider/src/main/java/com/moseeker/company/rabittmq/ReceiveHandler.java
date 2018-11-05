package com.moseeker.company.rabittmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.service.impl.CompanyTagService;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Component
@PropertySource("classpath:common.properties")
public class ReceiveHandler {
    private static Logger log = LoggerFactory.getLogger(ReceiveHandler.class);
    @Autowired
    private CompanyTagService companyTagService;

    @RabbitListener(queues = "#{profileCompanyTagQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void profileCompanyTagRecomQueHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            log.info("========获取的数据为===================");
            log.info(msgBody);
            log.info("===========================");
            if(StringUtils.isNotNullOrEmpty(msgBody)&&!"{}".equals(msgBody)){
                JSONObject jsonObject = JSONObject.parseObject(msgBody);
                List<Integer> userIdSet= (List<Integer>)jsonObject.get("user_ids");
                List<Integer> companyIdSet=(List<Integer>)jsonObject.get("company_ids");
                log.info("========userIdSet==========companyIdSet=========");
                log.info(JSON.toJSONString(userIdSet));
                log.info(JSON.toJSONString(companyIdSet));
                log.info("===========================");
                companyTagService.handlerProfileCompanyIds(convert(userIdSet),convert(companyIdSet));
                log.info("========开始个人标签==========");
//                companyTagService.handlerHrAutomaticData(convert(userIdSet));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Set<Integer> convert(List<Integer> list){
        if(StringUtils.isEmptyList(list)){
           return null;
        }
        Set<Integer> result=new HashSet<>();
        for(Integer item:list){
            result.add(item);
        }
        return result;
    }

}
