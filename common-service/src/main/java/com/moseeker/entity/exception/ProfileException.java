package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/8/3
 */
public class ProfileException extends CommonException {

    public static final ProfileException PROFILE_EMPLOYEE_NOT_EXIST = new ProfileException(30500,"员工信息错误！");
    public static final ProfileException PROFILE_USER_CREATE_FAILED = new ProfileException(30501,"用户已经存在！");
    public static final ProfileException PROFILE_POSITION_NOTEXIST = new ProfileException(31017,"职位信息不正确！");
    public static final ProfileException PROFILE_PARSING_ERROR = new ProfileException(31039,"调用简历解析服务错误！");

    public ProfileException(int i, String s) {
        super(i,s);
    }
}
