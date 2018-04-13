package com.moseeker.consistencysuport.protector;

import com.moseeker.common.thread.ThreadPool;
import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.db.Message;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 守护任务
 *
 * Created by jack on 04/04/2018.
 */
public class ProtectorTaskConfigImpl implements ProtectorTask, Runnable {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile boolean start = false;         //守护任务是否启动

    private Notification notification;              //通知
    private MessageRepository messageRepository;    //消息持久化操作
    private Map<String, ParamConvertTool> paramConvertToolMap;      //参数转换工具

    private long period = 5*60*1000;                //时间间隔
    private long initialDelay = 3*1000;             //延迟启动
    private byte retriedUpper = 3;                  // 重试上限

    private InvokeHandler invokeHandler;  //Spring容器

    ThreadPool threadPool = ThreadPool.Instance;

    public ProtectorTaskConfigImpl(long initialDelay, long period, byte retriedUpper, Notification notification,
                                   MessageRepository messageRepository,
                                   Map<String, ParamConvertTool> paramConvertToolMap, InvokeHandler invokeHandler) {
        this.period = period;
        this.initialDelay = initialDelay;
        this.retriedUpper = retriedUpper;
        this.notification = notification;
        this.messageRepository = messageRepository;
        this.paramConvertToolMap = paramConvertToolMap;
        this.invokeHandler = invokeHandler;
    }

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
    public synchronized void startProtectorTask() {
        if (!start) {
            start = true;
            schedual.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.MILLISECONDS);
        }
    }

    /**
     *
     * 查找未完成消息
     *
     * @return
     */
    private List<Message> fetchNotFinishedMessage() {

        long time = System.currentTimeMillis()-period;

        return messageRepository.fetchUnFinishMessageBySpecifiedSecond(time);
    }

    @Override
    public void run() {
        logger.debug("ProtectorTaskConfigImpl scheduleAtFixedRate time:{}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
        List<Message> messageList = fetchNotFinishedMessage();
        if (messageList != null && messageList.size() > 0) {
            messageList.forEach(message -> {
                if (message.getRetried() < retriedUpper) {
                    message.setRetried(message.getRetried()+1);
                    threadPool.startTast(() -> {
                        try {
                            invokeHandler.invoke(message);
                            return true;
                        } catch (ConsistencyException e) {
                            logger.error(e.getMessage(), e);
                            return false;
                        }
                    });

                } else {
                    threadPool.startTast(() -> {
                        notification.noticeForException(ConsistencyException.CONSISTENCY_PRODUCER_RETRY_OVER_LIMIT);
                        return true;
                    });
                }
            });
            try {
                messageRepository.updateRetried(messageList);
            } catch (ConsistencyException e) {
                notification.noticeForException(e);
            }
        }
    }
}
