package com.moseeker.entity.Constant;

import com.moseeker.common.constants.ChannelType;

import java.util.HashMap;
import java.util.Map;

/**
 * 简历类型
 * Created by jack on 18/08/2017.
 */
public enum ApplicationSource {

    PC(1, 1), ENTERPRISE(2, 10), GATHER(4, 100), JOB51(8, 1000), ZHILIAN(16, 10000), LIEPIN(32, 100000),
    ALIPAY(64, 1000000), PROXOYAPPLICATION(128, 10000000), DELEGATE(256, 100000000),
    ProgramImport(512, 1000000000), EmailApply(1024, 10000000000l),VERYEAST(2048,100000000000l),JOB1001(4096,1000000000000l)
    ,JOBSDB(8192,10000000000000l),CARNOC(16384,100000000000000l), EMPLOYEE_CHATBOT(32768,1000000000000000l),
    EMPLOYEE_REFERRAL(65536,10000000000000000l),JOB58(131072,100000000000000000L),SEEK_REFERRAL(262144,1000000000000000000l),INVITE_REFERRAL(524288,2^19),
    FORWARD_APPLICATION(1048576, 2^20),EMPLOYEE_REHIRING(2097152,2^21),EMPLOYEE_TRANSFER(4194304,2^22),TW104(33554432,2^25);

    private int value;
    private long flag;

    private static final Map<Integer, ApplicationSource> intToEnum = new HashMap();


    ApplicationSource(int value, long flag) {
        this.value = value;
        this.flag = flag;
    }

    static { // Initialize map from constant name to enum constant
        for (ApplicationSource op : values())
            intToEnum.put(op.getValue(), op);
    }

    /**
     * 根据值生成申请来源类型
     *
     * @param value 申请来源对应的值
     * @return 申请来源
     */
    public static ApplicationSource instaceFromInteger(int value) {
        return intToEnum.get(value);
    }

    /**
     * 查找给定的简历来源数据中是否存在当前的简历来源类型
     *
     * @param sources 简历来源数据
     * @return true 存在；false 不存在
     */
    public boolean exist(int sources) {
        long temp = sources & flag;
        if (temp != 0) {
            return true;
        }
        {
            return false;
        }
    }

    /**
     * 对给定的简历来源数值添加当前来源
     *
     * @param sources 简历来源数值
     * @return 加上当前简历来源之后的简历来源数值
     */
    public int andSource(int sources) {
        return sources | value;
    }

    /**
     * 对给定的简历来源数值添加当前来源
     *
     * @param applicationSource 简历来源类型
     * @return 加上当前简历来源之后的简历来源数值
     */
    public int andSource(ApplicationSource applicationSource) {
        return applicationSource.getValue() | value;
    }

    /**
     * 获取当前的简历来源类型数值
     *
     * @return 简历来源类型数值
     */
    public int getValue() {
        return value;
    }


    /**
     * channleType 转成origin
     *
     * @param channel
     * @return
     */
    public static int channelToOrigin(int channel) {
        int origin = 0;
        ChannelType channelType=ChannelType.instaceFromInteger(channel);
        switch (channelType) {
            case JOB51:
                origin = 8;
                break;
            case ZHILIAN:
                origin = 16;
                break;
            case LIEPIN:
                origin = 32;
                break;
            case ALIPAY:
                origin = 64;
                break;
            case VERYEAST:
                origin = 2048;
                break;
            case JOB1001:
                origin = 4096;
                break;
            case JOBSDB:
                origin = 8192;
                break;
            case CARNOC:
                origin = 16384;
                break;
            case JOB58:
                origin = 131072;
                break;
            case TW104:
                origin = 33554432;
                break;
            default:
        }
        return origin;
    }
}
