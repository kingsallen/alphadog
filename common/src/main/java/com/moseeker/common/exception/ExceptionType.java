package com.moseeker.common.exception;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 异常类型
 * Created by jack on 06/04/2017.
 */
public class ExceptionType {

    protected static HashMap<Integer, String> exceptionTypePool = new HashMap<>();  //异常消息池
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    static {
        exceptionTypePool.put(-1, "系统繁忙，请稍候再试!");
        exceptionTypePool.put(99999, "发生异常，请稍候再试!");
        exceptionTypePool.put(90010, "请求数据为空！");
        exceptionTypePool.put(90014, "{0}是必填项！");
        exceptionTypePool.put(90011, "添加失败！");
        exceptionTypePool.put(90012, "保存失败!");
        exceptionTypePool.put(90013, "删除失败!");
        exceptionTypePool.put(90014, null);
        exceptionTypePool.put(90015, "参数不正确!");
        exceptionTypePool.put(90016, "配置信息丢失!");
        exceptionTypePool.put(90016, "配置信息丢失!");
    }

    private int code;       //状态码
    private String msg;     //错误消息

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean addException(int code, String msg) {
        lock.writeLock().lock();
        try {
            boolean flag = false;
            if(code > 0) {
                if(!exceptionTypePool.containsKey(code)) {
                    exceptionTypePool.put(code, msg);
                    flag = true;
                }
            }
            return flag;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean updateException(int code, String msg) {
        lock.writeLock().lock();
        try {
            boolean flag = false;
            if(code > 0) {
                exceptionTypePool.put(code, msg);
            }
            return flag;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public HashMap<Integer, String> getExceptionTypePool() {
        return exceptionTypePool;
    }
}
