package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/8/7
 */
public class ApplicationException extends CommonException {

    public static final ApplicationException APPLICATION_CUSTOM_POSITION_VALIDATE_FAILED= new ApplicationException(41020,  "未能够正常保存!" );

    public ApplicationException(int code, String message) {
        super(code, message);
    }
}
