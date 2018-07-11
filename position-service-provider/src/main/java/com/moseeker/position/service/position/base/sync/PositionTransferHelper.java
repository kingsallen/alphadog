package com.moseeker.position.service.position.base.sync;

import com.moseeker.common.util.StringUtils;
import org.junit.Test;

public class PositionTransferHelper {

    public static String limitTitle(String title, int limitLength) {
        if (StringUtils.isNotNullOrEmpty(title)) {
            int titleLen = 0;
            char lastChar = ' ';
            StringBuilder result = new StringBuilder();
            for (char c : title.toCharArray()) {
                if (c > 0xff) {     // 中文
                    titleLen += 2;
                } else {
                    titleLen += 1;
                }

                if (titleLen > limitLength) {
                    lastChar = c;
                    break;
                }
                result.append(c);
            }

            if(lastChar == ' '){
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

    @Test
    public void test(){
        String title="Jade Relationship我我我我 Manager Hong Kong 我Plaza我 Sub-branch";
        System.out.println(limitTitle(title,50));
    }
}
