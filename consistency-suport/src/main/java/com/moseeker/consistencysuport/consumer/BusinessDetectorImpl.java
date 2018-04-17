package com.moseeker.consistencysuport.consumer;

import com.moseeker.consistencysuport.ConsumerEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 业务探针，用于查找服务内的所有业务
 *
 * Created by jack on 2018/4/17.
 */
@Component
public class BusinessDetectorImpl implements BusinessDetector{

    @Autowired
    private ApplicationContext applicationContext;

    private List<Business> businessNameList = new ArrayList<>();

    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
    private static  ReentrantReadWriteLock.WriteLock writeLock=rwl.writeLock();


    @Override
    public List<Business> findBusiness() {
        writeLock.lock();
        try {
            if (businessNameList == null) {
                updateBusinessList();
            }
        } finally {
            writeLock.unlock();
        }
        return businessNameList;
    }

    private void updateBusinessList() {

        Map<String, Object> map = applicationContext.getBeansWithAnnotation(ConsumerEntry.class);
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            for (Method m: entry.getValue().getClass().getMethods()) {
                ConsumerEntry consumerEntry = m.getAnnotation(ConsumerEntry.class);
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