package com.moseeker.baseorm.exception;

import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;

import java.util.HashMap;

/**
 * Created by jack on 10/07/2017.
 */
public class ExceptionConvertUtil {

    private static HashMap<Integer, BIZException> exceptionHashMap = new HashMap<>();  //异常消息池

    static {
        exceptionHashMap.put(90017, new BIZException(90017, "错误异常"));
    }

    public static BIZException convertCommonException(CommonException exception) throws BIZException {
        if (exception != null) {
            if (exception.getCode() != Category.VALIDATE_FAILED.getCode()) {
                BIZException bizException = new BIZException(exception.getCode(), exception.getMessage());
                return bizException;
            }
            if (exceptionHashMap.get(exception.getCode()) != null) {
                return exceptionHashMap.get(exception.getCode());
            } else {
                BIZException bizException = new BIZException(exception.getCode(), exception.getMessage());
                exceptionHashMap.put(bizException.getCode(), bizException);
                return bizException;
            }

        } else {
            throw exceptionHashMap.get(90017);
        }
    }
}
