package com.moseeker.baseorm.constant;

/**
 * 微信公众号 授权状态
 * Created by jack on 20/12/2017.
 */
public enum WechatAuthorized {

    IRRELEVANCE(0, "不相关"), AUTHORIZED(1, "授权"), Disauthorization(2, "解除授权");

    private WechatAuthorized(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
