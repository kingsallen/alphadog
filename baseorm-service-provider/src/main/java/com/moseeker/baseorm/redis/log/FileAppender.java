package com.moseeker.baseorm.redis.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by zhangdi on 2017/7/28.
 */
public class FileAppender extends DailyRollingFileAppender {



    @Override
    public synchronized void doAppend(LoggingEvent event) {
        super.doAppend(event);
    }
}
