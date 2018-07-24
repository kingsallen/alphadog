package com.moseeker.useraccounts.exception;

import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import java.util.HashMap;

/**
 * 异常类的简单工厂
 * Created by jack on 03/07/2017.
 */
public class ExceptionFactory extends com.moseeker.common.exception.ExceptionFactory {

    public static CommonException buildException(ExceptionCategory exceptionCategory) throws ParamIllegalException {
        if (exceptionCategory == null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(exceptionCategory.getCode(), exceptionCategory.getMsg());
    }

    private static HashMap<Integer, CommonException> exceptionHashMap = new HashMap<>();  //异常消息池

    public static CommonException buildException(int code, String message) {
        if(exceptionHashMap.containsKey(code) && code != Category.VALIDATE_FAILED.getCode()) {
            return exceptionHashMap.get(code);
        } else {
            CommonException bizException = new CommonException();
            bizException.setCode(code);
            bizException.setMessage(message);
            addException(bizException);
            return exceptionHashMap.get(code);
        }
    }
}
