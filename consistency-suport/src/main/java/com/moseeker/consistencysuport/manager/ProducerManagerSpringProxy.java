package com.moseeker.consistencysuport.manager;

import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.db.impl.MessageRepositoryImpl;
import com.moseeker.consistencysuport.exception.ConsistencyException;
import com.moseeker.consistencysuport.notification.NotificationImpl;
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

    private MessageRepository messageRepository;                    //消息持久化工具
    private Notification notification;                              //消息通知

    private long initialDelay = 5*1000;                             //延迟启动
    private long period = 5*60*1000;                                //任务时间间隔

    private static final int MIN_PERIOD = 3*1000;                   //时间间隔下限

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
        if (this.initialDelay < 0) {
            this.initialDelay = 0;
        }
        this.period = period;
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
        manager = new ProducerConsistentManager(messageRepository,
                paramConvertToolMap, notification, initialDelay, period);
        manager.startProtectorTask();
        return manager;
    }
}
