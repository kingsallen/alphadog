package com.moseeker.common.exception;

/**
 * Created by zhangdi on 2017/3/7.
 * 数据库ORM框架异常
 */
public class OrmException extends CommonException{
    String message;
    public OrmException(String messgae){
        this.message = messgae;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
