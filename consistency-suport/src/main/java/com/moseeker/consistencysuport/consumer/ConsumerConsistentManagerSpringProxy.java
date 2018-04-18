package com.moseeker.consistencysuport.consumer;

import com.moseeker.consistencysuport.config.Notification;
import com.moseeker.consistencysuport.notification.NotificationImpl;
import org.springframework.stereotype.Component;

/**
 *
 * 消费方 数据一致性组件管理类
 *
 * Created by jack on 13/04/2018.
 */
@Component
public class ConsumerConsistentManagerSpringProxy {

    private ConsumerConsistentManager consumerConsistentManager;

    private Notification notification;
    private MessageChannel messageChannel;
    private BusinessDetector businessDetector;      // 业务探针，查找注册的业务

    private long period = 60*1000;                  // 时间间隔
    private long initialDelay = 3*1000;             // 延迟启动
    private byte retriedUpper = 3;                  // 重试上限

    public ConsumerConsistentManagerSpringProxy buildTimeConfig(long period, long initialDelay, byte retriedUpper) {
        if (period > 10*1000 && period < 5*60*60*1000) {
            this.period = period;
        }
        if (initialDelay > 0) {
            this.initialDelay = initialDelay;
        }
        if (this.retriedUpper < 10 && this.retriedUpper > 0) {
            this.retriedUpper = retriedUpper;
        }
        return this;
    }

    public ConsumerConsistentManagerSpringProxy buildNotification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public ConsumerConsistentManagerSpringProxy buildMessageChannel(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
        return this;
    }

    public ConsumerConsistentManagerSpringProxy buildBusinessDetector(BusinessDetector businessDetector) {
        this.businessDetector = businessDetector;
        return this;
    }

    public synchronized ConsumerConsistentManager buildConsumerConsistentManager() {
        if (consumerConsistentManager == null) {
            if (notification == null) {
                notification = new NotificationImpl();
            }
            if (businessDetector == null) {
                businessDetector = new BusinessDetectorImpl();
            }
            if (messageChannel == null) {
                this.messageChannel = new MessageChannelImpl();
            }
            consumerConsistentManager = new ConsumerConsistentManager(period, initialDelay, retriedUpper, messageChannel,
                    businessDetector, notification);
        }
        return consumerConsistentManager;
    }
}
