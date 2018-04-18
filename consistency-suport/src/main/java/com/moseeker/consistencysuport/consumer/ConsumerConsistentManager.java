package com.moseeker.consistencysuport.consumer;

import com.moseeker.consistencysuport.Message;
import com.moseeker.consistencysuport.MessageType;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.exception.ConsistencyException;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 消费方
 *
 * Created by jack on 13/04/2018.
 */
public class ConsumerConsistentManager  {

    private volatile boolean register = false;

    private Notification notification;
    private MessageChannel messageChannel;
    private BusinessDetector businessDetector;      //业务探针，查找注册的业务

    private long period = 60*1000;                  //时间间隔
    private long initialDelay = 3*1000;             //延迟启动
    private byte retriedUpper = 3;                  // 重试上限

    private HeartBeatTask heartBeatTask;

    public ConsumerConsistentManager(long period, long initialDelay, byte retriedUpper, MessageChannel messageChannel,
                                     BusinessDetector businessDetector, Notification notification) {
        this.period = period;
        this.initialDelay = initialDelay;
        this.retriedUpper = retriedUpper;
        this.messageChannel = messageChannel;
        this.businessDetector = businessDetector;
        this.notification = notification;
        this.heartBeatTask = new HeartBeatTask(this);

        startHeartBeat();
        startRegister();
    }

    // 定时任务
    private final static ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        private AtomicInteger atoInteger = new AtomicInteger(0);
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("ConsumerConsistentManager-Thread "+ atoInteger.getAndIncrement());
            return t;
        }
    });

    /**
     * 消息执行完成
     * @param messageId 消息编号
     * @param name 业务名称
     */
    public void finishTask(String messageId, String name) {
        Message message = new Message();
        message.setMessageId(messageId);
        message.setBusinessName(name);
        message.setMessageType(MessageType.Finish);
        messageChannel.sendMessage(message);
    }

    public void notification(ConsistencyException e) {
        notification.noticeForException(e);
    }

    /**
     * 开启心跳定时任务
     */
    protected void startHeartBeat() {
        schedule.scheduleAtFixedRate(heartBeatTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 心跳任务
     */
    protected void heartBeatTask() {
        List<Business> businessList = businessDetector.findBusiness();
        if (businessList != null) {
            businessList.forEach(business -> {
                Message message = new Message();
                message.setBusinessName(business.getBusinessName());
                message.setMessageName(business.getMessageName());
                message.setMessageType(MessageType.HeartBeat);
                messageChannel.sendMessage(message);
            });
        }
    }

    /**
     * 开启业务注册
     */
    protected synchronized void startRegister() {
        if (!register) {
            register = true;
            List<Business> businessList = businessDetector.findBusiness();
            if (businessList != null) {
                businessList.forEach(business -> {
                    Message message = new Message();
                    message.setBusinessName(business.getBusinessName());
                    message.setMessageName(business.getMessageName());
                    message.setMessageType(MessageType.Register);
                    messageChannel.sendMessage(message);
                });
            }
        }
    }

    class HeartBeatTask implements Runnable {

        private ConsumerConsistentManager consumerConsistentManager;

        public HeartBeatTask(ConsumerConsistentManager consumerConsistentManager) {
            this.consumerConsistentManager = consumerConsistentManager;
        }

        @Override
        public void run() {
            consumerConsistentManager.heartBeatTask();
        }
    }
}
