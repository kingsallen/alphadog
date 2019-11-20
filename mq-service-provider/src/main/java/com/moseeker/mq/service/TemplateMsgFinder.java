package com.moseeker.mq.service;

import com.moseeker.mq.exception.MqException;
import com.moseeker.thrift.gen.mq.struct.MessageBody;

import java.util.List;

/**
 * @ClassName TemplateMsgFinder
 * @Description TODO
 * @Author jack
 * @Date 2019/3/25 12:53 PM
 * @Version 1.0
 */
public interface TemplateMsgFinder {
    /**
     * 查找公司下的模板消息
     * @param wechatId 公众号编号
     * @return 公司配置的模板消息
     * @throws MqException 业务有慈航
     */
    List<MessageBody> listTemplateMsg(int wechatId) throws MqException;
}
