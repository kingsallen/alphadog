package com.moseeker.common.constants;

public enum  CompanyType {
    PAY(0,"付费用户"),
    FREE(1,"免费用户"),
    OTHER(2,"其他用户")
    ;

    CompanyType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    int code;
    String text;

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
