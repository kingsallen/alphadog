package com.moseeker.useraccounts.constant;

/**
 * 自定义补填字段中类型 0表示下拉选项 1表示文本
 *
 * @Author jack
 * @Date 2019/6/13 2:19 PM
 * @Version 1.0
 */
public enum OptionType {

    /**
     * 下拉项
     */
    Select(0),
    /**
     * 文本
     */
    Content(1);

    OptionType(int value) {
        this.value = (byte)value;
    }

    public byte getValue() {
        return value;
    }

    private byte value;
}
