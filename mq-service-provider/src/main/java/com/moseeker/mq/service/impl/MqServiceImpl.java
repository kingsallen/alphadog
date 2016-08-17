package com.moseeker.mq.service.impl;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 消息队列服务
 *
 * Created by zzh on 16/8/3.
 */
@Service
public class MqServiceImpl implements MqService.Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisClient redisClient = RedisClientFactory.getCacheClient();

    /**
     * 消息模板通知接口
     *
     * @param messageTemplateNoticeStruct 通知需要的数据
     * @return
     * @throws TException
     */
    @Override
    public Response messageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct) throws TException {

        try {
            Response response = validateMessageTemplateNotice(messageTemplateNoticeStruct);
            if (response.status > 0){
                return response;
            }

            String json = BeanUtils.convertStructToJSON(messageTemplateNoticeStruct);

            Long res = redisClient.lpush(Constant.APPID_ALPHADOG, Constant.REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE, json);
            if (res != null ){
            	return ResponseUtils.success(res);
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("MqServiceImpl messageTemplateNotice error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
    }

    /**
     * 消息模板通知-数据校验
     *
     * @param messageTemplateNoticeStruct
     * @return
     */
    private Response validateMessageTemplateNotice(MessageTemplateNoticeStruct messageTemplateNoticeStruct){
        Response response = new Response(0, "ok");

        if(messageTemplateNoticeStruct.getOpenid() == null || "".equals(messageTemplateNoticeStruct.getOpenid())){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "openid"));
        }
        if(messageTemplateNoticeStruct.getAccess_token() == null || "".equals(messageTemplateNoticeStruct.getAccess_token())){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "access_token"));
        }
        if(messageTemplateNoticeStruct.getSys_template_id() == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "sys_template_id"));
        }
        if(messageTemplateNoticeStruct.getWechat_id() == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "wechat_id"));
        }
        if(messageTemplateNoticeStruct.getData() == null || messageTemplateNoticeStruct.getData().isEmpty()){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "data"));
        }

        return response;
    }
}
