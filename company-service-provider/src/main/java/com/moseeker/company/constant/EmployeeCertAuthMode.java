package com.moseeker.company.constant;

/**
 * 员工认证方式
 *
 * Created by huangxia on 2019/8/1.
 */
public enum EmployeeCertAuthMode {

    // 认证方式，0:不启用员工认证, 1:邮箱认证, 2:自定义认证, 3:姓名手机号认证, 4:邮箱自定义两种认证,5:问答,6:邮箱与问答 7:企业微信
    NONE(0),
    EMAIL(1),
    USER_DEFINED(2),
    NAME_MOBILE(3),
    EMAIL_USER_DEFINED(4),
    QUESTION(5),
    EMAIL_QUESTION(6),
    WORK_WECHAT(7);

    private int value ;
    private EmployeeCertAuthMode(int value){
        this.value = value;
    }
    public int value(){
        return value ;
    }

    public static boolean includeUserDefinedMode(int mode){
        return USER_DEFINED.value == mode || EMAIL_USER_DEFINED.value == mode ;
    }
}
