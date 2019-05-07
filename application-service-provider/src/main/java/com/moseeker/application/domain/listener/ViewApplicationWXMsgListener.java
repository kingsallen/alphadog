package com.moseeker.application.domain.listener;

import com.moseeker.application.domain.WXTamplateMsgEntity;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.application.domain.event.ViewApplicationListEvent;
import com.moseeker.application.domain.event.ViewApplicationSource;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * 模板消息 查看简历 监听器
 *
 * Created by jack on 23/01/2018.
 */
@Component
public class ViewApplicationWXMsgListener implements SmartApplicationListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "cacheClient")
    RedisClient redisClient;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    HrWxNoticeMessageDao messageDao;

    @Autowired
    HrWxWechatDao wxWechatDao;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (eventType == ViewApplicationListEvent.class) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        if (sourceType == ViewApplicationSource.class) {
            return true;
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        logger.info("ViewApplicationWXMsgListener onApplicationEvent:{}", event);
        try {
            ViewApplicationSource viewApplicationSource = (ViewApplicationSource) event.getSource();
            logger.info("viewApplicationSource applicationId:{}", viewApplicationSource.getApplicationIdList());
            logger.info("viewApplicationSource hrId:{}", viewApplicationSource.getHrId());
            WXTamplateMsgEntity wxTamplateMsgEntity = new WXTamplateMsgEntity(viewApplicationSource.getApplicationIdList(), applicationRepository, redisClient);
            wxTamplateMsgEntity.sendMsg();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

