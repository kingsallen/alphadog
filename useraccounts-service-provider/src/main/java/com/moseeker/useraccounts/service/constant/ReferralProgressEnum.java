package com.moseeker.useraccounts.service.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum  ReferralProgressEnum {

    /**
     * 1 提交简历 10 通过初筛 12 通过面试 3 成功入职 4 拒绝 6 hr查看简历 15 员工上传简历 16 联系内推推荐投递简历
     */
    APPLYED(1, 1),
    FILTERED(10, 2),
    INTERVIEWED(12, 3),
    ENTRY(3, 4),
    FAILED(4, 4),
    VIEW_APPLY(6, 1),
    EMPLOYEE_UPLOAD(15, 1),
    SEEK_APPLY(16, 1),
    ;

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
        if(progress == VIEW_APPLY.getProgress()
                || progress == EMPLOYEE_UPLOAD.getProgress()
                || progress == SEEK_APPLY.getProgress()){
            progress = APPLYED.getProgress();
        }
        return ENUM_MAP.get(progress);
    }

    private static final Map<Integer, ReferralProgressEnum> ENUM_MAP = new HashMap<>();

    private static final List<ReferralProgressEnum> ENUM_LIST = new ArrayList<>();

    static { // Initialize map from constant name to enum constant
        for (ReferralProgressEnum op : values()){
            ENUM_MAP.put(op.getProgress(), op);
        }
        ENUM_LIST.add(APPLYED);
        ENUM_LIST.add(FILTERED);
        ENUM_LIST.add(INTERVIEWED);
        ENUM_LIST.add(ENTRY);
    }

    public static List<ReferralProgressEnum> getEnumList() {
        return ENUM_LIST;
    }
}
