package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandler;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZhilianSyncVerifyHandler implements PositionSyncVerifyHandler<String,String>{
    Logger logger= LoggerFactory.getLogger(ZhilianSyncVerifyHandler.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void verifyHandler(String param) {
        JSONObject jsonObject= JSON.parseObject(param);
        String accountId=jsonObject.getString("accountId");
        String channel=jsonObject.getString("channel");
        String mobile=jsonObject.getString("mobile");
        if(StringUtils.isNullOrEmpty(accountId) || StringUtils.isNullOrEmpty(channel) || StringUtils.isNullOrEmpty(mobile)){
            throw new RuntimeException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }



    }

    @Override
    public void syncVerifyInfo(String info) {
//        JSONObject jsonObject= JSON.parseObject()
//        amqpTemplate.send();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
