package com.moseeker.common.log;

import com.moseeker.common.util.LogUtil;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by zhangdi on 2017/7/28.
 */
public class PatternLayout extends org.apache.log4j.PatternLayout {
    @Override
    public String format(LoggingEvent event) {
        return LogUtil.filterPwd(super.format(event));
    }
}
