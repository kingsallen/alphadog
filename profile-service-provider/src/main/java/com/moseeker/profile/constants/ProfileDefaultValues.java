package com.moseeker.profile.constants;

import com.moseeker.common.constants.DegreeConvertUtil;
import org.joda.time.DateTime;

/**
 * 简历数据默认值
 * @Author: jack
 * @Date: 2018/8/14
 */
public class ProfileDefaultValues {

    public static long defaultStartTime;

    static {
        DateTime dateTime1 = new DateTime("1900-01-01 01:01:01");
        defaultStartTime = dateTime1.getMillis();
    }

    public static String defaultName = "未填写";

    public static String defaultWorkExpJob = "未填写";

    public static long defaultWorkExpStartDate = defaultStartTime;

    public static byte defaultWorkExpUntilNow = 1;

    public static String defaultWorkExpDestription = "未填写次份工作的详细描述";

    public static String defaultEducationSchoolName = "未填写";

    public static DegreeConvertUtil defaultEducationDegree = DegreeConvertUtil.UNDERGRADUATE;

    public static String defaultEducationMajorName = "未填写";

    public static long defaultEducationStartDate = defaultStartTime;

    public static byte defaultEducationUntilNow = 1;

    public static String defaultEducationDescription = "未填写此项教育背景的详细描述";

    public static String defaultProjectName = "未填写";

    public static long defaultProjectStartDate = defaultStartTime;

    public static byte defaultProjectUntilNow = 1;
}
