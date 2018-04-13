package com.moseeker.consistencysuport.consumer;

import com.moseeker.consistencysuport.Message;

/**
 *
 * 往消息通道中发送消息
 *
 * Created by jack on 13/04/2018.
 */
public interface MessageChannel {

    void sendMessage(Message message);
}
