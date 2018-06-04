package com.moseeker.consistencysuport.consumer;

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
public class BusinessDetectorImpl implements BusinessDetector {

    @Autowired
    private ApplicationContext applicationContext;

    private List<Business> businessNameList = new ArrayList<>();

    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
    private static  ReentrantReadWriteLock.WriteLock writeLock=rwl.writeLock();


    @Override
    public List<Business> findBusiness() {
        writeLock.lock();
        try {
            if (businessNameList.size() == 0) {
                updateBusinessList();
            }
        } finally {
            writeLock.unlock();
        }
        return businessNameList;
    }

    private void updateBusinessList() {

        ConfigurableListableBeanFactory factory = ((ConfigurableApplicationContext)applicationContext).getBeanFactory();

        for( String name : factory.getBeanDefinitionNames() ) {
            Object object = applicationContext.getBean(name);
            if (object != null) {
                Class<?> clazz = AopProxyUtils.ultimateTargetClass(object);
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    ConsumerEntry consumerEntry = method.getAnnotation(ConsumerEntry.class);
                    if (consumerEntry != null) {
                        Business business = new Business();
                        business.setBusinessName(consumerEntry.businessName());
                        business.setMessageName(consumerEntry.messageName());
                        businessNameList.add(business);
                    }
                }
            }
        }
    }
}