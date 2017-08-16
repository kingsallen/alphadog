package com.moseeker.common.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by jack on 16/08/2017.
 */
public class EmojiFilterTest {
    @Test
    public void filterEmoji() throws Exception {

        System.out.println(content1);
        String str  = EmojiFilter.filterEmoji1(content1);
        System.out.println(str);
        System.out.println(content1);
        //System.out.println(EmojiFilter.refineHexString(content));
        //System.out.println(content.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));
        //assertEquals(true, EmojiFilter.containsWord(str));
    }

    private static String content1 = "{\"appid\": 202, \"channel\": 5, \"jobResumeOther\": {\"schooljob\": [{\"end\": \"2017-08-16\", \"position\": \"\u2764\ufe0f\ud83d\ude03\u6d4b\u8bd5\", \"start\": \"2017-08-15\", \"describe\": \"\u51b3\u5b9a\u5c31\u7761\u89c9\u7761\u89c9\u7761\ud83e\udd3c\u200d\u2642\ufe0f\ud83e\udd4a\ud83c\udfd1\ud83c\udfb1\ud83c\udfc8\u89c9\u7761\u89c9\u7761\u89c9\ud83d\ude2d\"}], \"internship\": [{\"end\": \"2017-08-15\", \"position\": \"\u5f00\u53d1\u5de5\u7a0b\u5e08\u263a\ufe0f\", \"start\": \"2017-08-15\", \"company\": \"\u5c71\u4e1c\u5b66\u516c\u53f8\ud83d\udc4d\ud83c\udffb\", \"describe\": \"\u7ec6\u8282\u8bbe\u8ba1\u6280\u672f\u7761\ud83c\udfda\ud83d\udcfa\ud83d\uded0\ud83c\udde7\ud83c\uddf8\ud83d\udcde\ud83d\ude1c\ud83c\udfc9\u89c9\u7761\u89c9\u5c31\u7761\u89c9\ud83d\ude14\"}]}, \"profile\": {\"basic\": {\"gender\": \"1\", \"cityName\": \"\u4e0a\u6d77\u5e02\", \"selfIntroduction\": \"\u597d\u7761\u89c9\u7761\u89c9\u65f6\u7684\u57fa\u7763\u6559\u548c\u59d0\u59d0\u5bb6\u6253\u5bb6\u52ab\u820d\ud83d\ude48\ud83d\ude29\ud83d\ude18\ud83d\ude18\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\", \"name\": \"\u88f4\u539a\u5e7f\", \"birth\": \"1981-02-02\"}, \"projectexps\": [], \"educations\": [{\"startDate\": \"2013-09-01\", \"majorName\": \"\ud83d\ude1c\u56de\u5bb6\ud83d\ude49\ud83d\udea8\ud83d\ude9c\", \"degree\": 5, \"collegeName\": \"\u5317\u4eac\u5de5\u4e1a\u5927\u5b66\", \"endDate\": \"2017-06-01\"}], \"user\": {\"mobile\": \"18217206525\", \"name\": \"\u88f4\u539a\u5e7f\", \"email\": \"peihouguang521@163.com\", \"uid\": \"2088422800732697\"}, \"credentials\": [{\"score\": \"\", \"code\": \"\", \"getDate\": \"\", \"organization\": \"\", \"name\": \"\u8ba1\u7b97\u673a\u4e8c\u7ea7\", \"url\": \"\"}, {\"score\": \"\", \"code\": \"\", \"getDate\": \"\", \"organization\": \"\", \"name\": \"\u56fd\u5bb6\u5956\u5b66\u91d1\", \"url\": \"\"}], \"workexps\": [], \"intentions\": [{\"cities\": [{\"cityName\": \"\u5929\u6d25\u5e02\"}], \"workstate\": 5, \"salary_code\": 7, \"worktype\": 1, \"industries\": [{\"industryName\": \"\u91d1\u878d\u4e1a\"}], \"positions\": [{\"positionName\": \"\u8f6f\u4ef6\"}]}], \"skills\": [], \"languages\": [{\"level\": 0, \"name\": \"\u6cd5\u8bed\"}, {\"level\": 0, \"name\": \"\u65e5\u8bed\"}, {\"level\": 0, \"name\": \"\u6cf0\u8bed\"}]}, \"positionId\": 1601773}";


}