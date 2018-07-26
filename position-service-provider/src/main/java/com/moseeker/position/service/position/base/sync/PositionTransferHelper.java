package com.moseeker.position.service.position.base.sync;

import com.moseeker.common.util.StringUtils;

public class PositionTransferHelper {

    /**
     * 以中文占2位，英文数字占1位的策略，截取指定长度标题
     * 并保证英文单词完整性
     * @param title 要截取的字符串
     * @param limitLength 要截取的长度
     * @return
     */
    public static String limitTitle(String title, int limitLength) {
        if (StringUtils.isNotNullOrEmpty(title)) {
            int titleLen = 0;
            char lastChar = ' ';
            StringBuilder result = new StringBuilder();
            // 以中文占2位，英文数字占1位的策略，截取指定长度标题
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

            // 如果截取完的字符串的在原字符串中的下一位是空格，就不需要做后续的"保证英文单词完整性"操作
            if(lastChar == ' '){
                return result.toString();
            }

            // 保证英文单词完整性，
            // 从最后向前遍历截取的字符串
            // 如果先遍历到中文，则不管英文单词完整性
            // 如果先遍历到空格，则截取掉空格到最后的字符
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
