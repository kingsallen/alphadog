package com.moseeker.consistencysuport.producer;

import com.moseeker.consistencysuport.exception.ConsistencyException;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 业务探针，用于查找服务内的所有业务
 *
 * Created by jack on 2018/4/17.
 */
@Component
public class MessageTypeDetectorImpl implements MessageTypeDetector{

    @Autowired
    private ApplicationContext applicationContext;

    private List<MessageTypePojo> messageTypePojoList = new ArrayList<>();

    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
    private static  ReentrantReadWriteLock.WriteLock writeLock=rwl.writeLock();

    @Override
    public List<MessageTypePojo> findMessageTypes() {
        writeLock.lock();
        try {
            if (messageTypePojoList.size() == 0) {
                updateBusinessList();
            }
        } finally {
            writeLock.unlock();
        }
        return messageTypePojoList;
    }

    private void updateBusinessList() {

        ConfigurableListableBeanFactory factory = ((ConfigurableApplicationContext)applicationContext).getBeanFactory();

        for( String name : factory.getBeanDefinitionNames() ) {
            Object object = applicationContext.getBean(name);
            if (object != null) {
                Class<?> clazz = AopProxyUtils.ultimateTargetClass(object);
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    ProducerEntry producerEntry = method.getAnnotation(ProducerEntry.class);
                    if (producerEntry != null) {
                        MessageTypePojo messageTypePojo = new MessageTypePojo();
                        messageTypePojo.setClassName(clazz.getName());
                        messageTypePojo.setMethod(method.getName());
                        messageTypePojo.setName(producerEntry.name());
                        messageTypePojoList.add(messageTypePojo);
                    }
                }
            }
        }

        if (messageTypePojoList.size() == 0) {
            throw ConsistencyException.CONSISTENCY_UNBIND_CONVERTTOOL;
        }
    }
}