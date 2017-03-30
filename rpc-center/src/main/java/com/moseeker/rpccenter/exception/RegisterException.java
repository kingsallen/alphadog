package com.moseeker.rpccenter.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册或者取消注册时，发生异常
 * Created by jack on 06/02/2017.
 */
public class RegisterException extends RuntimeException {

    private int errorCode;
    private String errorMsg;

    private static Map<String, Object>  map = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

    public RegisterException() {
        super((String) map.get("message"));
        this.errorCode = (Integer) map.get("status");
        this.errorMsg = (String) map.get("message");
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
