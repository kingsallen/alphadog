package com.moseeker.common.util;

import org.slf4j.LoggerFactory;

/**
 * Created by zhangdi on 2017/7/28.
 */
public class LogUtil {

    public static String filterSenstiveLog(String log, String... senstiveKeys) {
        if (StringUtils.isNullOrEmpty(log)) {
            return log;
        }
        if (senstiveKeys == null || senstiveKeys.length == 0) {
            return log;
        }

        StringBuilder regexBuilder = new StringBuilder();
        for (String senstiveKey : senstiveKeys) {
            regexBuilder.append('|').append("(\"?").append(senstiveKey).append("\\s*\"?:\\s*\"?[^\"]+\"?,?)");
        }

        regexBuilder.delete(0, 1);
        return log.replaceAll(regexBuilder.toString(), "");
    }

    public static String filterPwd(String log) {
        return filterSenstiveLog(log, "password", "pwd");
    }
}
