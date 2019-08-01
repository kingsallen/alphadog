package com.moseeker.useraccounts.service.constant;

import com.moseeker.entity.Constant.ApplicationSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.moseeker.useraccounts.service.constant.ReferralProgressEnum.*;

/**
 *
 * @author mdc
 * @date 2019/07/23
 */
public enum NewAtsMapReferralEnum {
    /**
     * 老流程     1 提交简历 10 通过初筛 12 通过面试 3 成功入职 4 拒绝 6 hr查看简历 15 员工上传简历 16 联系内推推荐投递简历 （1，6，15，16是简历投递阶段）
     * 新流程 类别 0初筛 1初筛型 2面试型 3服务类型 4offer型 5通用型 6待入职
     * new-old : 1-1,2-10,4-12,5-3(目前待定)
     *
     */
    APPLYED(0, 1),
    FILTERED(1, 10),
    INTERVIEWED(2, 12),
    SERVER(3, 12),
    OFFER(4, 12),
    COMMON(5, 12),
    ENTRY(6, 3);

    int newPhaseType;
    int oldStep;

    NewAtsMapReferralEnum(int newPhaseType, int oldStep) {
        this.newPhaseType = newPhaseType;
        this.oldStep = oldStep;
    }

    public int getNewPhaseType() {
        return newPhaseType;
    }

    public void setNewPhaseType(int newPhaseType) {
        this.newPhaseType = newPhaseType;
    }

    public int getOldStep() {
        return oldStep;
    }

    public void setOldStep(int oldStep) {
        this.oldStep = oldStep;
    }

    public static NewAtsMapReferralEnum getEnumByNewPhaseType(int newPhaseType){
        NewAtsMapReferralEnum[] enums = NewAtsMapReferralEnum.values();
        for(NewAtsMapReferralEnum item : enums){
            if (item.getNewPhaseType() == newPhaseType){
                return item;
            }
        }
        return APPLYED;
    }

}