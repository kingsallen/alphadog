package com.moseeker.company.service.impl.CompanySwitchHandler.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.company.constant.OmsSwitchEnum;
import com.moseeker.company.service.impl.CompanySwitchHandler.AbstractCompanySwitchHandler;
import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationshipRadarImpl extends AbstractCompanySwitchHandler{

    private Logger logger = LoggerFactory.getLogger(RelationshipRadarImpl.class);

    @Override
    public OmsSwitchEnum switchType() {
        return OmsSwitchEnum.人脉雷达;
}

    @Override
    public void rabbitmq(CompanySwitchVO companySwitchVO) {
        logger.info("=========companySwitchVO:{}", companySwitchVO);
        MessageProperties mp = new MessageProperties();
        amqpTemplate.send("company_switch_exchange", "handle_*_switch",
                MessageBuilder.withBody(JSONObject.toJSONString(companySwitchVO).getBytes()).andProperties(mp).build());
    }
}
