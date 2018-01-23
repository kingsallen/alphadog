package com.moseeker.useraccounts.context.aggregate;

import com.moseeker.useraccounts.context.constant.AwardEvent;

import java.util.List;

/**
 *
 * 申请相关的加积分业务编号
 *
 * Created by jack on 20/01/2018.
 */
public class ApplicationsAggregateId {

    List<Integer> applicationIdList;           //申请编号集合
    private AwardEvent awardEvent;             //积分添加事件

    public ApplicationsAggregateId(List<Integer> applicationIdList, AwardEvent awardEvent) {
        this.applicationIdList = applicationIdList;
        this.awardEvent = awardEvent;
    }

    public List<Integer> getApplicationIdList() {
        return applicationIdList;
    }

    public AwardEvent getAwardEvent() {
        return awardEvent;
    }
}
