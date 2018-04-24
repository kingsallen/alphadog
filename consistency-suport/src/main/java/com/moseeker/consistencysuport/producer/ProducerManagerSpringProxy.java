package com.moseeker.consistencysuport.producer;

import com.moseeker.consistencysuport.common.MessageRepository;
import com.moseeker.consistencysuport.common.Notification;
import com.moseeker.consistencysuport.common.ParamConvertTool;
import com.moseeker.consistencysuport.producer.db.impl.MessageRepositoryImpl;
import com.moseeker.consistencysuport.producer.echo.EchoHandler;
import com.moseeker.consistencysuport.producer.echo.EchoHandlerImpl;
import com.moseeker.consistencysuport.producer.echo.MessageChannel;
import com.moseeker.consistencysuport.producer.echo.MessageChannelImpl;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.notification.NotificationImpl;
import com.moseeker.consistencysuport.producer.protector.InvokeHandler;
import com.moseeker.consistencysuport.producer.protector.InvokeHandlerImpl;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * 预计 @ProducerConsistentManager 类的初始化会比较复杂，使用构造者模式创建。
 * ProducerManagerConfig 用于创建@ProducerConsistentManager类的构造者
 *
 * Created by jack on 03/04/2018.
 */
@Component
public class ProducerManagerSpringProxy {

    @Autowired
    private MessageRepository messageRepository;                    //消息持久化工具

    private Notification notification;                              //消息通知

    private long initialDelay = 5*1000;                             //延迟启动
    private long period = 5*60*1000;                                //任务时间间隔
    private byte retriedUpper = 3;                                  //重试次数上限

    private static final int MIN_PERIOD = 3*1000;                   //时间间隔下限

    private long heartBeatTimeout = 2*60*60*1000;                   //心跳超时时间

    private InvokeHandler invokeHandler;                            //重试调用方式

    private MessageChannel messageChannel;                          //消息通道
    private EchoHandler echoHandler;                                //消息处理

    private static ProducerConsistentManager manager;

    @Autowired
    private Map<String, ParamConvertTool> paramConvertToolMap;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @Autowired
    MessageTypeDetector messageTypeDetector;

    public ProducerManagerSpringProxy(){}

    /**
     * 构建消息持久化工具
     * @param messageRepository
     * @return
     */
    public ProducerManagerSpringProxy buildMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        return this;
    }

    /**
     * 构建通知工具
     * @param notification
     * @return
     */
    public ProducerManagerSpringProxy buildNotification(Notification notification) {
        this.notification = notification;
        return this;
    }

    /**
     * 构建重试调用方法
     * @param invokeHandler 消息重试调用方法
     * @return
     */
    public ProducerManagerSpringProxy buildInvokeHandler(InvokeHandler invokeHandler) {
        this.invokeHandler = invokeHandler;
        return this;
    }

    public ProducerManagerSpringProxy buildEchoHandler(EchoHandler echoHandler) {
        this.echoHandler = echoHandler;
        return this;
    }

    /**
     * 构建消息类型探针
     * @param messageTypeDetector 消息类型探针
     * @return
     */
    public ProducerManagerSpringProxy buildMessageTypeDetector(MessageTypeDetector messageTypeDetector) {
        this.messageTypeDetector = messageTypeDetector;
        return this;
    }

    /**
     * 构建保护程序
     * @param initialDelay
     * @param period
     * @return
     */
    public ProducerManagerSpringProxy buildProtectorTimeConfig(long initialDelay, long period, byte retriedUpper) {
        this.initialDelay = initialDelay;
        if (this.initialDelay < 0) {
            this.initialDelay = 0;
        }
        this.retriedUpper = retriedUpper;
        if (this.retriedUpper <= 0 || this.retriedUpper > 10) {
            this.retriedUpper = 3;
        }
        this.period = period;
        return this;
    }

    public ProducerManagerSpringProxy buildHeartBeatTimeout(long heartBeatTimeout) {
        this.heartBeatTimeout = heartBeatTimeout;
        return this;
    }

    /**
     * 构建一致性管理工具
     * @return 一致性管理工具
     * @throws ConsistencyException ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_REPOSITORY_NOT_FOUND;
     * ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_PERIOD_ERROR; ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_NOT_FOUND_ERROR_EMAIL;
     * ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_NOT_FOUND_EXCEPTION_EMAIL;ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_NOTIFACATION_ERROR;
     *
     */
    public synchronized ProducerConsistentManager buildManager() throws ConsistencyException {
        if (manager != null) {
            return manager;
        }
        if (messageRepository == null) {
            throw ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_REPOSITORY_NOT_FOUND;
        }
        if (period < MIN_PERIOD) {
            throw ConsistencyException.CONSISTENCY_PRODUCER_CONFIGURATION_PERIOD_ERROR;
        }
        if (notification == null) {
            notification = new NotificationImpl();
        }
        if (invokeHandler == null) {
            this.invokeHandler = new InvokeHandlerImpl(applicationContext, paramConvertToolMap, notification);
        }
        if (echoHandler == null) {
            echoHandler = new EchoHandlerImpl(messageRepository, notification);
        }
        this.messageChannel = new MessageChannelImpl(applicationContext, env, echoHandler);
        manager = new ProducerConsistentManager(messageRepository,
                paramConvertToolMap, notification, invokeHandler, initialDelay, period, heartBeatTimeout, retriedUpper,
                messageChannel, messageTypeDetector);
        return manager;
    }
}
