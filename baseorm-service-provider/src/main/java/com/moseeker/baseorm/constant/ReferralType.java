package com.moseeker.baseorm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/7
 */
public enum ReferralType {

    Wechat(1, "手机文件上传"), PC(2, "电脑扫码上传"), PostInfo(3, "推荐人才关键信息");

    private int value;
    private String description;

    private static Map<Integer, ReferralType> storage = new HashMap<>();

    static {
        for (ReferralType referralType : values()) {
            storage.put(referralType.getValue(), referralType);
        }
    }

    ReferralType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ReferralType instanceFromValue(int value) {
        return storage.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
