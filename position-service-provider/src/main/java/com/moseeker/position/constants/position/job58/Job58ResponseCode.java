package com.moseeker.position.constants.position.job58;

import java.util.HashMap;
import java.util.Map;

/**
 * 58响应码
 * @author cjm
 * @date 2018-11-27 15:50
 **/
public enum Job58ResponseCode {
    BIZ_ERROR(-1, "同步失败"),
    FAIL(1, "同步失败"),
    METHOD_AUTHZ_FAILED(2, "同步失败"),
    UNKNOWN_METHOD(3, "同步失败"),
    REQUEST_TOO_FAST(4, "同步异常，请重试"),
    REQUEST_EXCEED_DAILY_QUOTA(5, "当天同步次数超限，请明日重试"),
    REQUEST_EXCEED_DAILY_API_QUOTA(6, "当天同步次数超限，请明日重试"),
    OFFLINE(7, "同步失败"),
    ALREADY_EXIST(8, "同步失败"),
    USER_NOT_AUTH(9, "同步失败"),
    TIME_OUT(10, "同步失败"),
    LACKOF_REQUIRED_PARAM(100, "同步失败"),
    LACKOF_APPKEY(101, "同步失败"),
    INVALID_APPKEY(102, "同步失败"),
    LACKOF_SIG(103, "同步失败"),
    INVALID_SIG(104, "同步失败"),
    LACKOF_TIMESTAMP(105, "同步失败"),
    INVALID_TIMESTAMP(106, "同步失败"),
    LACKOF_ACCESSTOKEN(107, "同步失败"),
    INVALID_ACCESSTOKEN(108, "账号信息过期，请重新绑定账号"),
    INVALID_REFRESHTOKEN(109, "账号信息过期，请重新绑定账号"),
    LACKOF_OPENID(110, "同步失败"),
    INVALID_OPENID(111, "同步失败"),
    INVALID_REDIRECT_URI(112, "同步失败"),
    INVALID_CODE(113, "同步失败"),
    INVALID_INFOID(114, "同步失败"),
    APP_UNAVAILABLE(115, "同步失败"),
    INVALID_CATEID(116, "同步失败"),
    ELSE(1000, "同步失败"),
    ;


    int code;
    String qianxunMsg;

    Job58ResponseCode(int code, String qianxunMsg) {
        this.code = code;
        this.qianxunMsg = qianxunMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getQianxunMsg() {
        return qianxunMsg;
    }

    public void setQianxunMsg(String qianxunMsg) {
        this.qianxunMsg = qianxunMsg;
    }

    public static Job58ResponseCode getResponseMsg(int moseekerDegreeNo){
        return intToEnum.get(moseekerDegreeNo);
    }
    private static Map<Integer, Job58ResponseCode> intToEnum = new HashMap<>();
    static { // Initialize map from constant name to enum constant
        for (Job58ResponseCode op : values()){
            intToEnum.put(op.getCode(), op);
        }
    }
}
