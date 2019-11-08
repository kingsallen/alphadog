package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;

/**
 * 积分排行榜通知频率
 */
public enum WxMessageFrequency {

    EveryWeek("1w"),
    EveryTwoWeeks("2w"),
    EveryMonth("1m");

    private String frequency ;
    private WxMessageFrequency(String frequency){
        this.frequency = frequency ;
    }

    public static WxMessageFrequency getWxMessageFrequency(String frequency){
        if(StringUtils.isNotNullOrEmpty(frequency)){
            for(WxMessageFrequency frequency1 : values()){
                if(frequency1.frequency.equals(frequency)){
                    return frequency1;
                }
            }
        }
        // default:
        return EveryWeek;
    }

    public String getValue() {
        return frequency;
    }
}
