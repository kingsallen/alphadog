package com.moseeker.consistencysuport.echo;

import com.moseeker.consistencysuport.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

/**
 *
 * 消息通道
 *
 * Created by jack on 09/04/2018.
 */
public interface MessageChannel {

    /**
     * 初始化消息通道
     * @throws ConsistencyException
     */
    void initChannel() throws ConsistencyException;

    /**
     * 接收消息
     * @param  content 消息内容
     * @return 消息体
     * @throws ConsistencyException
     */
    Message receiveMessage(String content) throws ConsistencyException;
}
