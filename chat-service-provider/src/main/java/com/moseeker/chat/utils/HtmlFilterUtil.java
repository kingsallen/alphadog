package com.moseeker.chat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFilterUtil {

    private final static String HTML_TAG = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    /**
     * 是否包含"<"开头以">"结尾的标签
     *
     * @param str
     * @return
     */
    public static boolean isContainsHtmlTag(String str) {
        Pattern pattern = Pattern.compile(HTML_TAG);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}
