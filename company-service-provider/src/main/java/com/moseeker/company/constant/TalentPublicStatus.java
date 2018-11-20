package com.moseeker.company.constant;

/**
 * Created by zztaiwll on 18/11/16.
 */
public enum TalentPublicStatus {
    TALENT_PUBLIC_IS_ERROR(1000001,"无法满足操作条件"),
    TALENT_PUBLIC_HAS_DO(1000002,"在公开的人员中存在已公开的人员"),
    TALENT_PUBLIC_NO_PASS_GDPR(1000003,"按照gdpr规范，公开操作无法执行"),
    ;

    private int code;
    private String message;
    private TalentPublicStatus( int code,String message){
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
