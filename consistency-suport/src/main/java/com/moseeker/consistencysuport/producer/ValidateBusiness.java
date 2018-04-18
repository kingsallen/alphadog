package com.moseeker.consistencysuport.producer;

import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.exception.ConsistencyException;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 定时检查注册的业务是否有效
 *
 * Created by jack on 13/04/2018.
 */
public class ValidateBusiness implements Runnable {

    private volatile boolean start = false;                         //是否启动

    private Notification notification;                              //异常通知
    private MessageRepository messageRepository;                    //消息持久化工具

    private long initialDelay = 5*1000;                             //延迟启动
    private long period = 5*60*1000;                                //任务时间间隔
    private long lostTime = 10*60*1000;                             //认定业务消失的时间

    public ValidateBusiness(long initialDelay, long period, long lostTime, Notification notification, MessageRepository messageRepository) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.lostTime = lostTime;
        this.notification = notification;
        this.messageRepository = messageRepository;
    }

    // 定时任务
    private final static ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        private AtomicInteger atoInteger = new AtomicInteger(0);
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("ProtectorTaskConfig-Thread "+ atoInteger.getAndIncrement());
            return t;
        }
    });

    public synchronized void startValidateBusinessTask() {
        if (!start) {
            start = true;
            schedule.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void run() {
        try {
            messageRepository.disableBusinessTypeBySpecifiedShakeHandTime(lostTime);
        } catch (ConsistencyException e) {
            notification.noticeForException(e);
        } catch (Exception e) {
            notification.noticeForError(e);
        }
    }
}
