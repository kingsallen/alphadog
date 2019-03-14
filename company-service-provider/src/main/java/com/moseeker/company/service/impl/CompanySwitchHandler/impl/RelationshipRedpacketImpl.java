package com.moseeker.company.service.impl.CompanySwitchHandler.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.company.constant.OmsSwitchEnum;
import com.moseeker.company.service.impl.CompanySwitchHandler.AbstractCompanySwitchHandler;
import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;

public class RelationshipRedpacketImpl extends AbstractCompanySwitchHandler {

    private Logger logger = LoggerFactory.getLogger(RelationshipRedpacketImpl.class);

    @Override
    public void rabbitmq(CompanySwitchVO companySwitchVO) {
        logger.info("=========companySwitchVO:{}", companySwitchVO);
        MessageProperties mp = new MessageProperties();
        amqpTemplate.send("company_switch_exchange", "handle_redpacket_switch",
                MessageBuilder.withBody(JSONObject.toJSONString(companySwitchVO).getBytes()).andProperties(mp).build());
    }

    @Override
    public OmsSwitchEnum switchType() {
        return OmsSwitchEnum.红包活动;
    }
}
