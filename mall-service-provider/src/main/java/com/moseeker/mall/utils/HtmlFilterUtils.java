package com.moseeker.mall.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html过滤工具
 *
 * @author cjm
 * @date 2018-10-22 9:43
 **/
public class HtmlFilterUtils {

    private static final String[] REGIX_STR = { "meta", "script", "object", "embed" };

    private static final String[] REGIX_STR1 = { "function", "window\\.", "javascript:", "script",
            "js:", "about:", "file:", "document\\.", "vbs:", "frame",
            "cookie", "onclick", "onfinish", "onmouse", "onexit=",
            "onerror", "onclick", "onkey", "onload", "onfocus", "onblur" };


    /**
     * 过滤富文本中的html标签，防止xss攻击
     * @param htmlStr html字符串
     * @author  cjm
     * @date  2018/10/22
     * @return 过滤后的字符串
     */
    public static String filterSafe(String htmlStr){
        // 正则表达式
        Pattern p;
        // 操作的字符串
        Matcher m;
        StringBuffer tmp;
        String str;
        boolean isHave = false;
        if (htmlStr == null || !(htmlStr.length() > 0)) {
            return "";
        }
        str = htmlStr.toLowerCase();
        for (String regix : REGIX_STR) {
            p = Pattern.compile("<" + regix + "(.[^>])*>");
            m = p.matcher(str);
            tmp = new StringBuffer();
            if (m.find()) {
                m.appendReplacement(tmp, "<" + regix + ">");
                while (m.find()) {

                    m.appendReplacement(tmp, "<" + regix + ">");
                }
                isHave = true;
            }
            m.appendTail(tmp);
            str = tmp.toString();
            p = Pattern.compile("</" + regix + "(.[^>])*>");
            m = p.matcher(str);
            tmp = new StringBuffer();
            if (m.find()) {
                m.appendReplacement(tmp, "</" + regix + ">");
                while (m.find()) {
                    m.appendReplacement(tmp, "</" + regix + ">");
                }
                isHave = true;
            }
            m.appendTail(tmp);
            str = tmp.toString();

        }
        for (String regix : REGIX_STR1) {
            p = Pattern.compile("<([^<>])*" + regix + "([^<>])*>([^<>])*</([^<>])*>");

            m = p.matcher(str);
            tmp = new StringBuffer();
            if (m.find()) {
                m.appendReplacement(tmp, "");
                while (m.find()) {
                    m.appendReplacement(tmp, "");
                }
                isHave = true;
            }
            m.appendTail(tmp);
            str = tmp.toString();
        }
        if (isHave) {
            htmlStr = str;
        }
        htmlStr = htmlStr.replaceAll("%3C", "<");
        htmlStr = htmlStr.replaceAll("%3E", ">");
        htmlStr = htmlStr.replaceAll("%2F", "");
        htmlStr = htmlStr.replaceAll("&#", "<b>&#</b>");
        return htmlStr;
    }
}
