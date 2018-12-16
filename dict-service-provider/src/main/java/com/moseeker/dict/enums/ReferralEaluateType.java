package com.moseeker.dict.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moseeker on 2018/11/26.
 */
public enum ReferralEaluateType {

    OTHERS(0,1), EX_SUPERIOR(1,2), EX_SUBORDINATE(2,4), EX_COLLEAGUE(3,8),
    ALUMNS(4,16),FRIEND(5,32);

    private int code;
    private int value;

    ReferralEaluateType(int code, int value) {
        this.code = code;
        this.value = value;
    }

    private static Map<Integer, ReferralEaluateType> map = new HashMap<>();

    static {
        for (ReferralEaluateType csat : values()) {
            map.put(csat.getCode(), csat);
        }
    }


    public int getCode() {
        return code;
    }

    public int getValue() {
        return value;
    }


    public static ReferralEaluateType instanceFromByte(Integer value) {
        if (value != null && map.get(value.intValue()) != null) {
            return map.get(value.intValue());
        } else {
            return ReferralEaluateType.OTHERS;
        }
    }

}
