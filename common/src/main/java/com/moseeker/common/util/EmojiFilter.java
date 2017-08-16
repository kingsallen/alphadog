package com.moseeker.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 出处 http://www.jianshu.com/p/c95f83544094
 * Created by jack on 16/08/2017.
 */
public class EmojiFilter {

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param str
     * @return
     */
    public static String filterEmoji1(String str) {

        if(str.trim().isEmpty()){
            return str;
        }
        String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\ud83d\ude1c]|[\ud83d\ude49]|[\ud83d\udea8]|[\ud83d\ude9c]]";
        String reStr="";
        Pattern emoji=Pattern.compile(pattern);
        Matcher emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;

    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param str
     * @return
     */
    public static String filterEmoji(String str) {

        if(str.trim().isEmpty()){
            return str;
        }
        String pattern = "[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]|[\\ud83d\\ude1c]|[\\ud83d\\ude49]|[\\ud83d\\udea8]|[\\ud83d\\ude9c]]";
        String reStr="";
        Pattern emoji=Pattern.compile(pattern);
        Matcher emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;

    }
}
