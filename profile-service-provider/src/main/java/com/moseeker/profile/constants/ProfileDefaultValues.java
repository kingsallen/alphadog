package com.moseeker.profile.constants;

import com.moseeker.common.constants.DegreeConvertUtil;

import java.time.LocalDate;

/**
 * 简历数据默认值
 * @Author: jack
 * @Date: 2018/8/14
 */
public class ProfileDefaultValues {

    public static String defaultName = "未填写";

    public static String defaultWorkExpJob = "未填写";

    public static LocalDate defaultWorkExpStartDate = LocalDate.now().withYear(1900).withMonth(1).withDayOfMonth(1);

    public static byte defaultWorkExpUntilNow = 1;

    public static String defaultWorkExpDestription = "未填写次份工作的详细描述";

    public static String defaultEducationSchoolName = "未填写";

    public static DegreeConvertUtil defaultEducationDegree = DegreeConvertUtil.UNDERGRADUATE;

    public static String defaultEducationMajorName = "未填写";

    public static LocalDate defaultEducationStartDate = LocalDate.now().withYear(1900).withMonth(1).withDayOfMonth(1);

    public static byte defaultEducationUntilNow = 1;

    public static String defaultEducationDescription = "未填写此项教育背景的详细描述";

    public static String defaultProjectName = "未填写";

    public static LocalDate defaultProjectStartDate = LocalDate.now().withYear(1900).withMonth(1).withDayOfMonth(1);

    public static byte defaultProjectUntilNow = 1;
}
