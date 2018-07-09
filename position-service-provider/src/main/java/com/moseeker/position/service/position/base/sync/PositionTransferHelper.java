package com.moseeker.position.service.position.base.sync;

import com.moseeker.common.util.StringUtils;

public class PositionTransferHelper {

    public static String limitTitle(String title, int limitLength) {
        if (StringUtils.isNotNullOrEmpty(title)) {
            int titleLen = 0;
            StringBuilder result = new StringBuilder();
            for (char c : title.toCharArray()) {
                if (c > 0xff) {     // 中文
                    titleLen += 2;
                } else {
                    titleLen += 1;
                }

                if (titleLen > limitLength) {
                    break;
                }
                result.append(c);
            }

            if(title.charAt(limitLength)==' '){
                return result.toString();
            }

            for (int i = result.length() - 1; i >= 0; i--) {
                char c = result.charAt(i);
                if (c > 0xff) {
                    break;
                }
                if (c == ' ') {
                    return result.substring(0, i);
                }
            }
            return result.toString();
        }
        return title;
    }
}
