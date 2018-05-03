package com.moseeker.consistencysuport.producer.echo;

import com.moseeker.consistencysuport.exception.ConsistencyException;

/**
 *
 * 消息通道
 *
 * 初始化消息通道，并且创建消息监听，将返回的消息信息转交给EchoHandler处理
 *
 * Created by jack on 09/04/2018.
 */
public interface MessageChannel {

    /**
     * 初始化消息通道
     * @throws ConsistencyException
     */
    void initChannel() throws ConsistencyException;
}
