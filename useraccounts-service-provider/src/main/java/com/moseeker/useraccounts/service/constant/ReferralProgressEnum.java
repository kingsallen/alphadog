package com.moseeker.useraccounts.service.constant;

import java.util.HashMap;
import java.util.Map;

public enum  ReferralProgressEnum {

    /**
     * 1 提交简历 10 通过初筛 12 通过面试 3 成功入职 4 拒绝
     */
    APPLYED(1),
    FILTERED(10),
    INTERVIEWED(12),
    ENTRY(3),
    FAILED(4),;

    int progress;

    ReferralProgressEnum(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public ReferralProgressEnum getEnumByProgress(int progress){
        return ENUM_MAP.get(progress);
    }

    private static final Map<Integer, ReferralProgressEnum> ENUM_MAP = new HashMap<>();

    static { // Initialize map from constant name to enum constant
        for (ReferralProgressEnum op : values()){
            ENUM_MAP.put(op.getProgress(), op);
        }
    }
}
