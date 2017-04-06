package com.moseeker.thrift.gen.common.struct;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 异常生成类。用于缓存各种异常信息
 * Created by jack on 06/04/2017.
 */
public abstract class ExceptionUtils {

    private HashMap<Integer, BIZException> exceptionTypeHashMap = new HashMap<>();  //异常消息池

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();                     //读写互斥锁


    public BIZException buildException(int code) {
        return buildException(code, null);
    }

    public BIZException buildException(int code, String message) {
        lock.readLock().lock();
        try {
            if(exceptionTypeHashMap.containsKey(code)) {
                return exceptionTypeHashMap.get(code);
            } else {
                BIZException bizException = new BIZException();
                bizException.setCode(code);
                bizException.setMessage(message);
                addException(bizException);
                return exceptionTypeHashMap.get(code);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean addException(BIZException e) {
        lock.writeLock().lock();
        try {
            boolean flag = false;
            if(e != null  && e.getCode() > 0) {
                if(!exceptionTypeHashMap.containsKey(e.getCode())) {
                    exceptionTypeHashMap.put(e.getCode(), e);
                    flag = true;
                }
            }
            return flag;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean updateException(BIZException e) {
        lock.writeLock().lock();
        try {
            boolean flag = false;
            if(e != null  && e.getCode() > 0) {
                if(!exceptionTypeHashMap.containsKey(e.getCode())) {
                    exceptionTypeHashMap.put(e.getCode(), e);
                    flag = true;
                } else {
                    BIZException exception = exceptionTypeHashMap.get(e.getCode());
                    exception.setMessage(e.getMessage());
                }
            }
            return flag;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean updateException(int code, String msg) {
        BIZException bizException = new BIZException(code, msg);
        return updateException(bizException);
    }
}
