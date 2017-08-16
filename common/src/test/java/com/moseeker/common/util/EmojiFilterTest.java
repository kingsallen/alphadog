package com.moseeker.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 16/08/2017.
 */
public class EmojiFilterTest {
    @Test
    public void filterEmoji() throws Exception {
        System.out.println(content1);
        String str = EmojiFilter.filterEmoji1(content1);
        System.out.println(str);
        //System.out.println(EmojiFilter.refineHexString(content));
        //System.out.println(content.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
        //assertEquals(true, EmojiFilter.containsWord(str));
    }

    private static String content1 = "{\"appid\": 202, \"channel\": 5, \"jobResumeOther\": {\"schooljob\": [{\"end\": \"2017-08-16\", \"position\": \"\u2764\ufe0f\ud83d\ude03\u6d4b\u8bd5\", \"start\": \"2017-08-15\", \"describe\": \"\u51b3\u5b9a\u5c31\u7761\u89c9\u7761\u89c9\u7761\u89c9\u7761\u89c9\u7761\u89c9\ud83d\ude2d\"}], \"internship\": [{\"end\": \"2017-08-15\", \"position\": \"\u5f00\u53d1\u5de5\u7a0b\u5e08\u263a\ufe0f\", \"start\": \"2017-08-15\", \"company\": \"\u5c71\u4e1c\u5b66\u516c\u53f8\ud83d\udc4d\ud83c\udffb\", \"describe\": \"\u7ec6\u8282\u8bbe\u8ba1\u6280\u672f\u7761\u89c9\u7761\u89c9\u5c31\u7761\u89c9\ud83d\ude14\"}]}, \"profile\": {\"basic\": {\"gender\": \"1\", \"cityName\": \"\u4e0a\u6d77\u5e02\", \"selfIntroduction\": \"\u597d\u7761\u89c9\u7761\u89c9\u65f6\u7684\u57fa\u7763\u6559\u548c\u59d0\u59d0\u5bb6\u6253\u5bb6\u52ab\u820d\ud83d\ude48\ud83d\ude29\ud83d\ude18\ud83d\ude18\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\ud83d\ude29\", \"name\": \"\u88f4\u539a\u5e7f\", \"birth\": \"1981-02-02\"}, \"projectexps\": [], \"educations\": [{\"startDate\": \"2013-09-01\", \"majorName\": \"\ud83d\ude1c\u56de\u5bb6\", \"degree\": 5, \"collegeName\": \"\u5317\u4eac\u5de5\u4e1a\u5927\u5b66\", \"endDate\": \"2017-06-01\"}], \"user\": {\"mobile\": \"18217206525\", \"name\": \"\u88f4\u539a\u5e7f\", \"email\": \"peihouguang521@163.com\", \"uid\": \"2088422800732697\"}, \"credentials\": [{\"score\": \"\", \"code\": \"\", \"getDate\": \"\", \"organization\": \"\", \"name\": \"\u8ba1\u7b97\u673a\u4e8c\u7ea7\", \"url\": \"\"}, {\"score\": \"\", \"code\": \"\", \"getDate\": \"\", \"organization\": \"\", \"name\": \"\u56fd\u5bb6\u5956\u5b66\u91d1\", \"url\": \"\"}], \"workexps\": [], \"intentions\": [{\"cities\": [{\"cityName\": \"\u5929\u6d25\u5e02\"}], \"workstate\": 5, \"salary_code\": 7, \"worktype\": 1, \"industries\": [{\"industryName\": \"\u91d1\u878d\u4e1a\"}], \"positions\": [{\"positionName\": \"\u8f6f\u4ef6\"}]}], \"skills\": [], \"languages\": [{\"level\": 0, \"name\": \"\u6cd5\u8bed\"}, {\"level\": 0, \"name\": \"\u65e5\u8bed\"}, {\"level\": 0, \"name\": \"\u6cf0\u8bed\"}]}, \"positionId\": 1626375}";

    private static String content = "{'profile': {'educations': [{'majorName': '\\xF0\\x9F\\x98\\x8A\\xF0\\x9F...', 'collegeName': '北京工业大学', 'startDate': '2013-09-01', 'endDate': '2017-06-01', 'degree': 5}], 'credentials': [{'getDate': '', 'url': '', 'score': '', 'code': '', 'organization': '', 'name': '计算机四级'}, {'getDate': '', 'url': '', 'score': '', 'code': '', 'organization': '', 'name': '国家奖学金'}, {'getDate': '', 'url': '', 'score': '', 'code': '', 'organization': '', 'name': '学校奖学金'}], 'user': {'mobile': '15313088281', 'uid': '2088502132734948', 'name': '李延龙', 'email': '952999815@qq.com'}, 'workexps': [], 'intentions': [{'worktype': 1, 'workstate': 5, 'positions': [{'positionName': '行政'}], 'cities': [{'cityName': '天津市'}], 'industries': [{'industryName': '电子电气'}], 'salary_code': 8}], 'languages': [{'level': 0, 'name': '日语'}, {'level': 0, 'name': '英语'}, {'level': 0, 'name': '其他语种'}], 'projectexps': [], 'skills': [], 'basic': {'cityName': '北京市', 'selfIntroduction': '修改修改', 'birth': '1988-08-01', 'name': '李延龙', 'gender': '1'}}, 'channel': 5, 'positionId': 1621160, 'jobResumeOther': {'schooljob': [{'describe': '校内工作经历3\uD83D\uDE13修改', 'end': '2017-08-11', 'position': '修改校内职务3', 'start': '2017-08-11'}], 'internship': [{'describe': '内容3\uD83D\uDE04修改一下', 'end': '2017-08-11', 'position': '职位3', 'start': '2017-08-11', 'company': '公司3'}]}}";

}