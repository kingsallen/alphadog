package com.moseeker.company.constant;

/**
 * Created by zztaiwll on 18/11/22.
 */
public enum TalentPoolTalentStatus {
    TALENT_POOL_NO_PASS_GDPR(1100003,"按照gdpr规范，公开操作无法执行")
            ;

    private int code;
    private String message;
    private TalentPoolTalentStatus( int code,String message){
        this.code = code;
        this.message=message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
