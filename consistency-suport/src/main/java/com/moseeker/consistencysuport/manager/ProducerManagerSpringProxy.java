package com.moseeker.consistencysuport.manager;

import com.moseeker.consistencysuport.config.MessageRepository;
import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.config.ParamConvertTool;
import com.moseeker.consistencysuport.db.impl.MessageRepositoryImpl;
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

    private MessageHandler messageHandler;                          //消息持久化工具
    private Notification notification;

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
        MessageRepository messageRepository = new MessageRepositoryImpl(create);
        messageHandler = new MessageHandler(messageRepository);
        return this;
    }

    /**
     * 构建消息持久化工具
     * @param messageRepository
     * @return
     */
    public ProducerManagerSpringProxy buildMessageHandler(MessageRepository messageRepository) {
        messageHandler = new MessageHandler(messageRepository);
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
     * 构建一致性客户端工具
     * @return
     */
    public ProducerConsistentManager buildManager() {
        ProducerConsistentManager producerConsistentManager = new ProducerConsistentManager(messageHandler, paramConvertToolMap, notification);
        return producerConsistentManager;
    }
}
