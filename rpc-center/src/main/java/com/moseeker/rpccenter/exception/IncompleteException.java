package com.moseeker.rpccenter.exception;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;

import java.util.Map;

/**
 * 配置信息丢失异常
 * Created by jack on 06/02/2017.
 */
public class IncompleteException extends RuntimeException {

    private int errorCode;
    private String errorMsg;

    private static Map<String, Object> map = JSONObject.parseObject(ConstantErrorCodeMessage.PROGRAM_CONFIG_INCOMPLETE);

    public IncompleteException() {
        super((String) map.get("message"));
        this.errorCode = (Integer) map.get("status");
        this.errorMsg = (String) map.get("message");
    }

    public IncompleteException(String message) {
        super(message);
        this.errorCode = (Integer) map.get("status");
        this.errorMsg = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
