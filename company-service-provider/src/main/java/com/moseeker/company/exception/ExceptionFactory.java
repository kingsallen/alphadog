package com.moseeker.company.exception;

import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.ParamIllegalException;

/**
 * 异常类的简单工厂。已经废弃，改用ProfileException
 * @see CompanyException
 * Created by jack on 03/07/2017.
 */
@Deprecated
public class ExceptionFactory extends com.moseeker.common.exception.ExceptionFactory {

    public static CommonException buildException(ExceptionCategory exceptionCategory) throws ParamIllegalException {
        if(exceptionCategory == null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(exceptionCategory.getCode(), exceptionCategory.getMsg());
    }
}
