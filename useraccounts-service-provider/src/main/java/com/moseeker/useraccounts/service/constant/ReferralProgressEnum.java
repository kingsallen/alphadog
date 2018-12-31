package com.moseeker.useraccounts.service.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  ReferralProgressEnum {

    /**
     * 1 提交简历 10 通过初筛 12 通过面试 3 成功入职 4 拒绝
     */
    APPLYED(1, 1),
    FILTERED(10, 2),
    INTERVIEWED(12, 3),
    ENTRY(3, 4),
    FAILED(4, -1),;

    int progress;
    int order;

    ReferralProgressEnum(int progress, int order) {
        this.progress = progress;
        this.order = order;
    }

    public int getProgress() {
        return progress;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public static ReferralProgressEnum getEnumByProgress(int progress){
        return ENUM_MAP.get(progress);
    }

    private static final Map<Integer, ReferralProgressEnum> ENUM_MAP = new HashMap<>();

    static { // Initialize map from constant name to enum constant
        for (ReferralProgressEnum op : values()){
            ENUM_MAP.put(op.getProgress(), op);
        }
    }
}
