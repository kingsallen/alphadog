package com.moseeker.application.context.listener;

import com.moseeker.application.context.event.ViewApplicationListEvent;
import com.moseeker.application.infrastructure.DaoManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * HR浏览申请积分添加监听器
 * Created by jack on 17/01/2018.
 */
@Component
public class ViewApplicationAwardListener implements SmartApplicationListener {

    @Autowired
    DaoManagement daoManagement;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (eventType == ViewApplicationListEvent.class) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        if (sourceType == String.class) {
            return true;
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
