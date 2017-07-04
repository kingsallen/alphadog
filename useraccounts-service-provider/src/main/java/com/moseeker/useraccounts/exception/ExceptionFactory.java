package com.moseeker.useraccounts.exception;

import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * 异常类的简单工厂
 * Created by jack on 03/07/2017.
 */
public class ExceptionFactory extends com.moseeker.common.exception.ExceptionFactory {

    public static BIZException buildException(ExceptionCategory exceptionCategory) throws ParamIllegalException {
        if (exceptionCategory == null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(exceptionCategory.getCode(), exceptionCategory.getMsg());
    }
}
