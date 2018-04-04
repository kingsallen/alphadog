package com.moseeker.consistencysuport.protector;

import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.db.Message;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 轮训机制
 * todo 定时查找超时未完成的任务
 * todo 根据轮训次数执行消息发送或者报警
 * todo 可配置的定时处理
 *
 * Created by jack on 04/04/2018.
 */
public class ProtectorTaskConfigImpl implements ProtectorTask {

    private Notification notification;              //通知
    private MessageRepository messageRepository;    //消息持久化操作

    // 定时任务
    private final static ScheduledThreadPoolExecutor schedual = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        private AtomicInteger atoInteger = new AtomicInteger(0);
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("ProtectorTaskConfig-Thread "+ atoInteger.getAndIncrement());
            return t;
        }
    });

    @Override
    public void startProtectorTask() {

    }

    private List<Message> fetchNotFinishedMessage() {
        messageRepository.fetchUnfinishMessageBySpecifiedSecond();
    }
}
