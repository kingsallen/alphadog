package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/8/3
 */
public class ProfileException extends CommonException {

    public static final ProfileException PROFILE_EMPLOYEE_NOT_EXIST = new ProfileException(30500,"员工信息错误！");
    public static final ProfileException PROFILE_USER_CREATE_FAILED = new ProfileException(30501,"用户已经存在！");

    public ProfileException(int i, String s) {
        super(i,s);
    }
}
