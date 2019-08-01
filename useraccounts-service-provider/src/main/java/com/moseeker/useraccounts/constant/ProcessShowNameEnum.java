package com.moseeker.useraccounts.constant;

/**
 * @Author: mdc
 * @Date: 2019-07-18 09:59
 * 老的阶段信息
 */
public enum ProcessShowNameEnum {
    DELIVERY(1,"投递简历"),
    PRIMARY_SCREEN(2, "初筛"),
    INTERVIEW(3,"面试"),
    ENTRY(4, "入职");

    ProcessShowNameEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    Integer code;
    String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(Integer code) {
        ProcessShowNameEnum[] enums = values();
        for (ProcessShowNameEnum item : enums) {
            if (item.getCode().equals(code)) {
                return item.getMessage();
            }
        }
        return "投递简历";
    }
}
