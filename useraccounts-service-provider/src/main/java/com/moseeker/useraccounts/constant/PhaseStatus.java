package com.moseeker.useraccounts.constant;

/**
 * @Author: mdc
 * @Date: 2019-07-18 09:59
 * 阶段状态
 */
public enum PhaseStatus {
    PASS("1", "通过", 1),
    NOT_PASS("2", "不通过",2),
    TO_DO("3", "待进行", 0);

    PhaseStatus(String code, String message, Integer mapInt){
        this.code = code;
        this.message = message;
        this.mapInt = mapInt;
    }

    String code;
    String message;
    Integer mapInt;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getMapInt() {
        return mapInt;
    }

    public static String getCode(Integer mapInt) {
        PhaseStatus[] enums = values();
        for (PhaseStatus item : enums) {
            if (item.getMapInt().equals(mapInt)) {
                return item.getCode();
            }
        }
        return "";
    }

    public static String getMessageByMapInt(Integer mapInt) {
        PhaseStatus[] enums = values();
        for (PhaseStatus item : enums) {
            if (item.getMapInt().equals(mapInt)) {
                return item.getMessage();
            }
        }
        return "";
    }

    public static String getMessage(String code) {
        PhaseStatus[] enums = values();
        for (PhaseStatus item : enums) {
            if (item.getCode().equals(code)) {
                return item.getMessage();
            }
        }
        return "";
    }
}
