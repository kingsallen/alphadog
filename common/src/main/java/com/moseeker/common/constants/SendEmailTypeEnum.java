package com.moseeker.common.constants;/**
 * Created by zztaiwll on 18/12/4.
 */

/**
 * @version 1.0
 * @className SendEmailTypeEnum
 * @Description TODO 转发建立的类型 1是职位配置的转发简历 5是人才库转发简历
 * @Author zztaiwll
 * @DATE 18/12/4 下午2:49
 **/
public enum SendEmailTypeEnum {
    POSITION_INVATE_EMAIL(1),
    TALENT_INVATE_EMAIL(5) ;
    private int value;
    private SendEmailTypeEnum(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
