package com.moseeker.demo1.config;

import com.moseeker.consistencysuport.consumer.ConsumerConsistentManager;
import com.moseeker.consistencysuport.consumer.ConsumerConsistentManagerSpringProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * Spring 容器初始化完成之后
 *
 * Created by jack on 2018/4/19.
 */
@Component
public class InitConsistencyConsumer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ConsumerConsistentManagerSpringProxy consumerConsistentManagerSpringProxy;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ConsumerConsistentManager consumerConsistentManager = consumerConsistentManagerSpringProxy.buildConsumerConsistentManager();
        consumerConsistentManager.startRegister();
        consumerConsistentManager.startHeartBeat();
    }
}
