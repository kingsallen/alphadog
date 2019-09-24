package com.moseeker.profile.exception;

import com.moseeker.common.exception.CommonException;

/**
 * 简历业务异常
 * Created by jack on 07/09/2017.
 */
public class ProfileException extends CommonException {

    public static final ProfileException PROFILE_REPEAT_DATA = new ProfileException(30010, "重复数据！");
    public static final ProfileException PROFILE_DICT_CITY_NOTEXIST = new ProfileException(31011,"城市字典不存在！");
    public static final ProfileException PROFILE_DICT_POSITION_NOTEXIST = new ProfileException(31012,"职位职能字典不存在！");
    public static final ProfileException PROFILE_DICT_INDUSTRY_NOTEXIST = new ProfileException(31013,"行业字典不存在！");
    public static final ProfileException PROFILE_USER_NOTEXIST = new ProfileException(31014,"用户信息不正确！");
    public static final ProfileException PROFILE_ALLREADY_EXIST = new ProfileException(31015,"个人profile已存在！");
    public static final ProfileException PROFILE_ILLEGAL = new ProfileException(31016,"profile数据不正确！");
    public static final ProfileException PROFILE_POSITION_NOTEXIST = new ProfileException(31017,"职位信息不正确！");
    public static final ProfileException PROFILE_DICT_NATIONALITY_NOTEXIST = new ProfileException(31018,"国家字典不存在！");
    public static final ProfileException PROFILE_DATA_NULL = new ProfileException(31019,"数据不存在！");
    public static final ProfileException PROFILE_DICT_COLLEGE_NOTEXIST = new ProfileException(30020,"院校字典不存在！");
    public static final ProfileException PROFILE_DICT_MAJOR_NOTEXIST = new ProfileException(30021,"专业字典不存在！");
    public static final ProfileException PROFILE_OUTPUT_FAILED = new ProfileException(30022,"导出失败！");
    public static final ProfileException PROFILE_NOT_EXIST = new ProfileException(30023,"个人profile不存在！");
    public static final ProfileException VALIDATION_USERNAME_REQUIRED = new ProfileException(30024,"缺少手机号码这个必要参数！");
    public static final ProfileException VALIDATION_USER_ILLEGAL_PARAM = new ProfileException(30025, "用户信息错误错误！");
    public static final ProfileException VALIDATION_SMS_FAILTURE = new ProfileException(30026,"密码发送失败！");
    public static final ProfileException VALIDATION_APPLICATION_UPPER_LIMIT = new ProfileException(30027,"投递达到上线无法继续投递！");
    public static final ProfileException VALIDATION_POSITION_NOT_EXIST = new ProfileException(30028,"职位数据不存在！");
    public static final ProfileException VALIDATION_RETRIEVAL_EXCUTOR_NOT_CUSTOMED = new ProfileException(30029,"未定制任何执行者！");
    public static final ProfileException VALIDATION_RETRIEVAL_CHANNEL_NOT_CUSTOMED = new ProfileException(30030,"渠道信息错误！");
    public static final ProfileException VALIDATION_ALI_USER_ILLEGAL_PARAM = new ProfileException(30031, "用户信息错误错误！");
    public static final ProfileException VALIDATION_PROFILE_RETRIEVAL_USER_ALI_TASK = new ProfileException(30032,"缺少必要参数，无法正常生成阿里用户！");
    public static final ProfileException PROFILE_PARSE_TEXT_FAILED = new ProfileException(30033,"简历信息解析错误！");
    public static final ProfileException PROFILE_EMPLOYEE_NOT_EXIST = new ProfileException(30034,"员工信息不存在！");
    public static final ProfileException PROFILE_FILE_SAVE_FAILED = new ProfileException(30035,"简历文件保存失败！");
    public static final ProfileException REFERRAL_PROFILE_NOT_EXIST = new ProfileException(30036,"简历数据不存在！");
    public static final ProfileException REFERRAL_REPEATE_REFERRAL = new ProfileException(30037,"感谢您的支持，此候选人已存在系统中，此次推荐不成功。期待您继续推荐其他候选人！");
    public static final ProfileException REFERRAL_FILE_TYPE_NOT_SUPPORT = new ProfileException(30038,"文件格式不支持！");
    public static final ProfileException REFERRAL_FILE_NOT_EXIST = new ProfileException(30039,"指定的简历文件不存在！");

    protected ProfileException(int code, String message) {
        super(code, message);
    }
}
