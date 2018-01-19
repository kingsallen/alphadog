package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandler;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZhilianSyncVerifyHandler implements PositionSyncVerifyHandler<String,String>{
    Logger logger= LoggerFactory.getLogger(ZhilianSyncVerifyHandler.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void verifyHandler(String param) throws BIZException{
        JSONObject jsonObject= JSON.parseObject(param);
        String accountId=jsonObject.getString("accountId");
        String channel=jsonObject.getString("channel");
        String mobile=jsonObject.getString("mobile");
        if(StringUtils.isNullOrEmpty(accountId) || StringUtils.isNullOrEmpty(channel) || StringUtils.isNullOrEmpty(mobile)){
            throw new RuntimeException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }

        //发送

    }

    @Override
    public void syncVerifyInfo(String info) throws BIZException{
        if(StringUtils.isNullOrEmpty(info)){
            logger.error("智联验证信息为空，无法发送消息给爬虫端");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"智联验证信息，无法发送消息给爬虫端");
        }
        JSONObject jsonObject= JSON.parseObject(info);
        String accountId=jsonObject.getString("accountId");
        if(StringUtils.isNullOrEmpty(accountId)){
            logger.error("智联验证信息accountId为空，无法发送消息给爬虫端,info : "+info);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"智联验证信息accountId为空，无法发送消息给爬虫端,info : "+info);
        }
        String rountingKey=PositionSyncVerify.MOBILE_VERIFY_RESPONSE_ROUTING_KEY.replace("{}",accountId);
        amqpTemplate.send(
                PositionSyncVerify.MOBILE_VERIFY_EXCHANGE
                , rountingKey
                , MessageBuilder.withBody(info.getBytes()).build());
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
