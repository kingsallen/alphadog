package com.moseeker.position.service.schedule;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.BindThirdPart;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.PositionFactory;
import com.moseeker.position.service.position.base.sync.verify.MobileEnvironVerifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyReceiver;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PositionSyncVerifyConsumer {
    Logger logger= LoggerFactory.getLogger(PositionSyncVerifyConsumer.class);


    @Autowired
    PositionEmailNotification emailNotification;

    @Autowired
    PositionFactory verifyHandlerFactory;

    @Resource(type = MobileEnvironVerifyHandler.class)
    PositionSyncVerifyReceiver environVerifyHandler;


    @Resource(name = "cacheClient")
    protected RedisClient redisClient;

//    @RabbitListener(queues = {PositionSyncVerify.MOBILE_VERIFY_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void handle(Message message) {
        String json="";

        try {
            json=new String(message.getBody(), "UTF-8");
            logger.info("receive json {}",json );

            JSONObject obj=JSONObject.parseObject(json);

            String operation=obj.getString("operation");

            switch (operation){
                case "bind":    //账号同步验证

                    logger.info("账号同步验证 推送数据到redis中 json：{}",json);
                    // 智联改版前，账号同步需要传100给前台，前台会有验证码窗口。
                    // 改版后，逻辑修改，爬虫端不会发送status，为了复用之前的代码，自行添加一个status
                    obj.put("status",100);
                    redisClient.set(BindThirdPart.APP_ID, BindThirdPart.KEY_IDENTIFIER,obj.getString(BindThirdPart.CHAOS_ACCOUNTID),obj.toJSONString());
                    logger.info("推送数据成功");

                    break;
                case "publish": //职位同步验证
                    logger.info("职位同步验证 json：{}",json);
                    wxVerifyHandler(obj);

                    break;
                case "environ": //刷新验证
                    logger.info("刷新验证 json：{}",json);
                    environVerifyHandler.receive(obj.toJSONString());

                    break;
                default:
                    logger.info("无法识别的验证类型 json:{}",json);
                    break;
            }

            logger.info("handle json success json: {}",json);
        }catch (Exception e){
            logger.error("handle sync verify Error : {}, message :{}",e.getMessage(),json);
            emailNotification.sendVerifyFailureMail(json, null, e);
        }
    }

    public void wxVerifyHandler(JSONObject obj) throws BIZException {

        int channel=obj.getIntValue("channel");

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        PositionSyncVerifyReceiver verifyHandler=verifyHandlerFactory.getVerifyReceiverInstance(channelType);

        verifyHandler.receive(obj.toJSONString());
    }
}
