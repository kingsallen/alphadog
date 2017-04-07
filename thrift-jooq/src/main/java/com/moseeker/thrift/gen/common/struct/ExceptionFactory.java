package com.moseeker.thrift.gen.common.struct;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 异常生成类。用于缓存各种异常信息
 * Created by jack on 06/04/2017.
 */
public abstract class ExceptionFactory {

    private static HashMap<Integer, BIZException> exceptionHashMap = new HashMap<>();  //异常消息池


    public static BIZException buildException(int code) {
        return buildException(code, null);
    }

    public static BIZException buildException(int code, String message) {
        if(exceptionHashMap.containsKey(code)) {
            return exceptionHashMap.get(code);
        } else {
            BIZException bizException = new BIZException();
            bizException.setCode(code);
            bizException.setMessage(message);
            addException(bizException);
            return exceptionHashMap.get(code);
        }
    }

    protected static boolean addException(BIZException e) {
        boolean flag = false;
        if(e != null  && e.getCode() > 0) {
            if(!exceptionHashMap.containsKey(e.getCode())) {
                exceptionHashMap.put(e.getCode(), e);
                flag = true;
            }
        }
        return flag;
    }

    protected boolean updateException(BIZException e) {
        boolean flag = false;
        if(e != null  && e.getCode() > 0) {
            if(!exceptionHashMap.containsKey(e.getCode())) {
                exceptionHashMap.put(e.getCode(), e);
                flag = true;
            } else {
                BIZException exception = exceptionHashMap.get(e.getCode());
                exception.setMessage(e.getMessage());
            }
        }
        return flag;
    }

    public boolean updateException(int code, String msg) {
        BIZException bizException = new BIZException(code, msg);
        return updateException(bizException);
    }
}
