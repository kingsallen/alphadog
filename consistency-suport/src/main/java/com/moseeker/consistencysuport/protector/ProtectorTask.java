package com.moseeker.consistencysuport.protector;

import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;

/**
 *
 * 守护任务
 * 通过定时查找未处理完毕的消息业务，并重新执行调用，以防止调用未被调用方正确接收，
 * 并在重试次数达到上线后发送报警，请求人工介入来保证请求一定会被正确执行。以这种方式来保护请求不会被丢失。
 *
 * Created by jack on 04/04/2018.
 */
public interface ProtectorTask {

    /**
     * 启动守护任务
     */
    void startProtectorTask();

    /**
     * 重新执行调用方法
     * @param message 消息
     */
    void reHandler(Message message) throws ConsistencyException;
}
