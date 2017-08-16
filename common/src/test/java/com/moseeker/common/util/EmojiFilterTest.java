package com.moseeker.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 16/08/2017.
 */
public class EmojiFilterTest {
    @Test
    public void filterEmoji() throws Exception {
        String str = EmojiFilter.refineHexString("\\xF0\\x9F\\x99\\x88\\xF0\\x9F...");
        System.out.println(str);
        System.out.println(EmojiFilter.refineHexString(content));
        //System.out.println(content.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
        //assertEquals(true, EmojiFilter.containsWord(str));
    }

    private static String content = "{'profile': {'educations': [{'majorName': '\\xF0\\x9F\\x98\\x8A\\xF0\\x9F...', 'collegeName': '北京工业大学', 'startDate': '2013-09-01', 'endDate': '2017-06-01', 'degree': 5}], 'credentials': [{'getDate': '', 'url': '', 'score': '', 'code': '', 'organization': '', 'name': '计算机四级'}, {'getDate': '', 'url': '', 'score': '', 'code': '', 'organization': '', 'name': '国家奖学金'}, {'getDate': '', 'url': '', 'score': '', 'code': '', 'organization': '', 'name': '学校奖学金'}], 'user': {'mobile': '15313088281', 'uid': '2088502132734948', 'name': '李延龙', 'email': '952999815@qq.com'}, 'workexps': [], 'intentions': [{'worktype': 1, 'workstate': 5, 'positions': [{'positionName': '行政'}], 'cities': [{'cityName': '天津市'}], 'industries': [{'industryName': '电子电气'}], 'salary_code': 8}], 'languages': [{'level': 0, 'name': '日语'}, {'level': 0, 'name': '英语'}, {'level': 0, 'name': '其他语种'}], 'projectexps': [], 'skills': [], 'basic': {'cityName': '北京市', 'selfIntroduction': '修改修改', 'birth': '1988-08-01', 'name': '李延龙', 'gender': '1'}}, 'channel': 5, 'positionId': 1621160, 'jobResumeOther': {'schooljob': [{'describe': '校内工作经历3\uD83D\uDE13修改', 'end': '2017-08-11', 'position': '修改校内职务3', 'start': '2017-08-11'}], 'internship': [{'describe': '内容3\uD83D\uDE04修改一下', 'end': '2017-08-11', 'position': '职位3', 'start': '2017-08-11', 'company': '公司3'}]}}";

}