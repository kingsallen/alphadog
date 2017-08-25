package com.moseeker.entity.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 简历类型
 * Created by jack on 18/08/2017.
 */
public enum ApplicationSource {

    PC(1, 1), ENTERPRISE(2, 10), GATHER(4, 100), JOB51(8, 1000), ZHILIAN(16, 10000), LIEPIN(32, 100000), ALIPAY(64, 1000000), PROXOYAPPLICATION(128, 10000000), DELEGATE(256, 100000000);

    private int value;
    private int flag;

    private static final Map<Integer, ApplicationSource> intToEnum = new HashMap();


    private ApplicationSource(int value, int flag) {
        this.value = value;
        this.flag = flag;
    }

    static { // Initialize map from constant name to enum constant
        for (ApplicationSource op : values())
            intToEnum.put(op.getValue(), op);
    }

    /**
     * 根据值生成申请来源类型
     * @param value 申请来源对应的值
     * @return 申请来源
     */
    public static ApplicationSource instaceFromInteger(int value) {
        return intToEnum.get(value);
    }

    /**
     * 查找给定的简历来源数据中是否存在当前的简历来源类型
     * @param sources 简历来源数据
     * @return true 存在；false 不存在
     */
    public boolean exist(int sources) {
        int temp = sources & flag;
        if (temp != 0) {
            return true;
        } {
            return false;
        }
    }

    /**
     * 对给定的简历来源数值添加当前来源
     * @param sources 简历来源数值
     * @return 加上当前简历来源之后的简历来源数值
     */
    public int andSource(int sources) {
        return sources | value;
    }

    /**
     * 对给定的简历来源数值添加当前来源
     * @param applicationSource 简历来源类型
     * @return 加上当前简历来源之后的简历来源数值
     */
    public int andSource(ApplicationSource applicationSource) {
        return applicationSource.getValue() | value;
    }

    /**
     * 获取当前的简历来源类型数值
     * @return 简历来源类型数值
     */
    public int getValue() {
        return value;
    }
}
