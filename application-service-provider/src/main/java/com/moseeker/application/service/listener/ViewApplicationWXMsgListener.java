package com.moseeker.application.service.listener;

import com.moseeker.application.domain.WXTamplateMsgEntity;
import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.baseorm.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 模板消息 查看简历 监听器
 *
 * Created by jack on 23/01/2018.
 */
@Component
public class ViewApplicationWXMsgListener implements SmartApplicationListener {

    @Resource(name = "cacheClient")
    RedisClient redisClient;

    @Autowired
    DaoManagement daoManagement;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        List<Integer> applicationIdList = (List<Integer>) event.getSource();
        WXTamplateMsgEntity wxTamplateMsgEntity = new WXTamplateMsgEntity(applicationIdList, daoManagement, redisClient);
        wxTamplateMsgEntity.sendMsg();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
