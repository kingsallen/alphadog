package com.moseeker.application.infrastructure.wx.tamlatemsg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.pojo.WXTemplateMsgPojo;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.entity.biz.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 *
 * 通过MTP发送微信模板消息
 *
 * MTP 项目 对外提供了一个基于redis队列的模板消息消费服务。我们拼装MTP服务参数，发送模板消息。
 *
 * Created by jack on 23/01/2018.
 */
public abstract class WXMsgNoticeViceMTP {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    protected WXTemplateMsgPojo wxTemplateMsg;
    protected RedisClient redisClient;

    public WXMsgNoticeViceMTP() {
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    /**
     * 发送模板消息
     * @throws CommonException 业务异常 (90014, message)
     */
    public void sendWXTemplateMsg() throws CommonException {
        if (redisClient == null) {
            throw ApplicationException.APPLICATION_REDIS_CLIENT;
        }
        initTemplateMsg();
        try {
            ValidationMessage<String> validationMessage = validateMessageTemplateNotice(wxTemplateMsg);
            if (validationMessage.isPass()) {
                wxTemplateMsg.setId(UUID.randomUUID().toString()+System.currentTimeMillis());
                String json = JSON.toJSONString(wxTemplateMsg, serializeConfig);
                if (wxTemplateMsg.getDelay() > 0) {
                    redisClient.zadd(AppId.APPID_ALPHADOG.getValue(),
                            KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DELAY.toString(),
                            wxTemplateMsg.getDelay()*1000+System.currentTimeMillis(), json);

                } else {
                    redisClient.lpush(Constant.APPID_ALPHADOG,
                            Constant.REDIS_KEY_IDENTIFIER_MQ_MESSAGE_NOTICE_TEMPLATE, json);
                }
            } else {
                logger.info("sendWXTemplateMsg 参数校验不通过：{}", validationMessage.getResult());
                throw ApplicationException.validateFailed(validationMessage.getResult());
            }

        } catch (RedisException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("MqServiceImpl messageTemplateNotice error: ", e);
        } finally {
            // do nothing
        }
    }

    protected abstract void initTemplateMsg();

    /**
     * 消息模板通知-数据校验
     *
     * @param wxTemplateMsgPojo
     * @return
     */
    private ValidationMessage<String> validateMessageTemplateNotice(WXTemplateMsgPojo wxTemplateMsgPojo) {

        ValidationMessage<String> validationMessage = new ValidationMessage<>();

        if (wxTemplateMsgPojo.getUserId() == 0) {
            validationMessage.addFailedElement("user_id", "必填项！");
        }
        if (wxTemplateMsgPojo.getSysTemplateId() == 0) {
            validationMessage.addFailedElement("sys_template_id", "必填项！");
        }
        if (wxTemplateMsgPojo.getCompanyId() == 0) {
            validationMessage.addFailedElement("company_id", "必填项！");
        }
        if (wxTemplateMsgPojo.getData() == null || wxTemplateMsgPojo.getData().isEmpty()) {
            validationMessage.addFailedElement("data", "必填项！");
        }

        return validationMessage;
    }
}
