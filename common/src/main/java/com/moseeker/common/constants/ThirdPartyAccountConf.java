package com.moseeker.common.constants;

/**
 * 第三方账号配置枚举类
 */
public enum  ThirdPartyAccountConf {

    REQUIRE_COMPANY(1,0),   //智联同步时页面是否需要选择公司名称，0 不需要，1 需要
    REQUIRE_DEPARTMENT(1,0),    //智联同步时页面是否需要选择部门名称，0 不需要，1 需要
    ;

    ThirdPartyAccountConf(int on, int off) {
        this.ON = (byte) on;
        this.OFF =  (byte)on;
    }

    public byte ON;
    public byte OFF;
}
