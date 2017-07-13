package com.moseeker.common.exception;

import java.util.HashMap;

/**
 * 异常生成类。用于缓存各种异常信息
 * Created by jack on 06/04/2017.
 */
public abstract class ExceptionFactory {

    private static HashMap<Integer, CommonException> exceptionHashMap = new HashMap<>();  //异常消息池

    public static CommonException buildException(int code) {
        return buildException(code, null);
    }

    public static CommonException buildException(int code, String message) {
        if(exceptionHashMap.containsKey(code)) {
            return exceptionHashMap.get(code);
        } else {
            CommonException bizException = new CommonException();
            bizException.setCode(code);
            bizException.setMessage(message);
            addException(bizException);
            return exceptionHashMap.get(code);
        }
    }

    protected static boolean addException(CommonException e) {
        boolean flag = false;
        if(e != null  && e.getCode() > 0) {
            if(!exceptionHashMap.containsKey(e.getCode())) {
                exceptionHashMap.put(e.getCode(), e);
                flag = true;
            }
        }
        return flag;
    }

    protected synchronized boolean updateException(CommonException e) {
        boolean flag = false;
        if(e != null  && e.getCode() > 0) {
            if(!exceptionHashMap.containsKey(e.getCode())) {
                exceptionHashMap.put(e.getCode(), e);
                flag = true;
            } else {
                CommonException exception = exceptionHashMap.get(e.getCode());
                exception.setMessage(e.getMessage());
            }
        }
        return flag;
    }

    protected static boolean exist(int code) {
        return exceptionHashMap.containsKey(code);
    }

    public boolean updateException(int code, String msg) {
        CommonException bizException = new CommonException(code, msg);
        return updateException(bizException);
    }

    public static CommonException buildException(Category category) throws ParamIllegalException {
        if(category == null) {
            throw new ParamIllegalException("异常类型不存在");
        }
        return buildException(category.getCode(), category.getMsg());
    }
}
