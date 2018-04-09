package com.moseeker.consistencysuport.manager;

import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.db.impl.MessageRepositoryImpl;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.persistence.MessagePersistenceImpl;
import com.moseeker.consistencysuport.protector.ProtectorTask;
import com.moseeker.consistencysuport.protector.ProtectorTaskConfigImpl;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
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

    private MessageRepository messageRepository;                   //消息持久化工具
    private Notification notification;

    private long initialDelay;                                       //延迟启动
    private long period;                                             //任务时间间隔

    private static ProducerConsistentManager manager;

    @Autowired
    private Map<String, ParamConvertTool> paramConvertToolMap;

    public ProducerManagerSpringProxy(){}

    /**
     * 构建消息持久化工具
     * @param create
     * @return
     */
    public ProducerManagerSpringProxy buildMessageHandler(DefaultDSLContext create) {
        messageRepository = new MessageRepositoryImpl(create);
        return this;
    }

    /**
     * 构建消息持久化工具
     * @param messageRepository
     * @return
     */
    public ProducerManagerSpringProxy buildMessageHandler(MessageRepository messageRepository) {
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
     * 构建保护程序
     * @param initialDelay
     * @param period
     * @return
     */
    public ProducerManagerSpringProxy buildProtectorTimeConfig(long initialDelay, long period) {
        this.initialDelay = initialDelay;
        this.period = period;
        return this;
    }

    /**
     * 构建一致性客户端工具
     * @return
     */
    public ProducerConsistentManager buildManager() throws ConsistencyException {
        ProducerConsistentManager producerConsistentManager = new ProducerConsistentManager(messageRepository,
                paramConvertToolMap, notification, initialDelay, period);
        producerConsistentManager.startProtectorTask();
        return producerConsistentManager;
    }
}
