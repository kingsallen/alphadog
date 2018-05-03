package com.moseeker.servicemanager.consistency;

import com.moseeker.consistencysuport.producer.ProducerConsistentManager;
import com.moseeker.consistencysuport.producer.ProducerEntry;
import com.moseeker.consistencysuport.producer.ProducerManagerSpringProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * Spring 容器初始化完成之后
 *
 * Created by jack on 2018/4/19.
 */
@Component
public class InitConsistencyProducer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ProducerManagerSpringProxy producerManagerSpringProxy;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ProducerConsistentManager producerConsistentManager = producerManagerSpringProxy.buildManager();
        producerConsistentManager.initDB();
        producerConsistentManager.registerMessageType();
        producerConsistentManager.initChannel();
        producerConsistentManager.startProtectorTask();
        producerConsistentManager.startValidateBusiness();
    }
}
