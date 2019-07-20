package com.moseeker.servicemanager.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.LoggerFactory;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 *
 * response body的数据结构
 *
 * Created by jack on 2018/5/17.
 */
public class Result {
    // 序列化配置对象
    private SerializeConfig config = new SerializeConfig();

    public static final String SUCCESS = new Result(0,"success").toString();

    private int status;
    private String message;
    private Object data;

    public Result() {
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    public Result(Object obj) {
        this.status = 0;
        this.message= "success";
        this.data = obj;
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    public String toJson() {
        return JSONObject.toJSONString(this, config, WriteNullListAsEmpty, WriteNullStringAsEmpty, WriteNullNumberAsZero, WriteNullBooleanAsFalse, WriteMapNullValue, WriteNullNumberAsZero);
    }

    public String toJsonStr(){
        return JSONObject.toJSONString(this);
    }

    public int getStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result PROGRAM_EXCEPTION = new Result(CommonException.PROGRAM_EXCEPTION.getCode(), CommonException.PROGRAM_EXCEPTION.getMessage());

    public static Result fail(String message) {

        return fail(message, null);
    }

    public static Result fail(String message, Integer code) {
        try {
            Result result = new Result();
            if (code != null) {
                result.status = CommonException.PROGRAM_EXCEPTION.getCode();
            } else {
                result.status = CommonException.PROGRAM_EXCEPTION.getCode();
            }
            result.message = message;
            return result;
        } catch (Exception e) {
            LoggerFactory.getLogger(Result.class).warn(e.getMessage());
        }
        return Result.PROGRAM_EXCEPTION;
    }

    public static Result fail(MessageType message) {
        try {
            Result result = new Result(message.getCode(), message.getMsg());
            return result;
        } catch (Exception e) {
            LoggerFactory.getLogger(Result.class).warn(e.getMessage());
        }
        return Result.PROGRAM_EXCEPTION;
    }

    public static Result excetionToResult(Exception e){
        Result result = null;
        if(e instanceof BIZException) {
            BIZException exception = (BIZException)e;
            result = new Result(exception.getCode(), exception.getMessage());
        }else{
            result = new Result(1, "发生异常，请稍候再试!");
        }
        return result;
    }


    public static Result fail(int code, String message) {
        Result result = new Result(code, message);
        return result;
    }

    public static Result validateFailed(String message) {
        Result result = new Result(MessageType.PROGRAM_PARAM_NOTEXIST.getCode(), message);
        return result;
    }

    public static Result success() {
        return new Result(0, "success");
    }

    public static Result success(Object data) {
        return new Result(data);
    }

    public Result(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }
}
