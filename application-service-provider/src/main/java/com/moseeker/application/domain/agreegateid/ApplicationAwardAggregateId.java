package com.moseeker.application.domain.agreegateid;

import com.moseeker.application.domain.constant.AwardCondition;

import java.util.List;

/**
 * 和招聘进度相关积分触发条件
 * Created by jack on 18/01/2018.
 */
public class ApplicationAwardAggregateId {

    private AwardCondition awardCondition;
    private List<Integer> appIdList;

    public AwardCondition getAwardCondition() {
        return awardCondition;
    }

    public void setAwardCondition(AwardCondition awardCondition) {
        this.awardCondition = awardCondition;
    }

    public List<Integer> getAppIdList() {
        return appIdList;
    }

    public void setAppIdList(List<Integer> appIdList) {
        this.appIdList = appIdList;
    }
}
