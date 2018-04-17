package com.moseeker.consistencysuport.consumer;

import com.moseeker.consistencysuport.Message;

/**
 *
 * 往消息通道中发送消息
 *
 * Created by jack on 13/04/2018.
 */
public interface MessageChannel {

    /**
     * 发送消息
     * @param message 消息体
     */
    void sendMessage(Message message);

    /**
     * 初始化消息通道
     */
    void initMessageChannel();
}
