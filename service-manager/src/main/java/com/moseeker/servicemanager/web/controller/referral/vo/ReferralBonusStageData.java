package com.moseeker.servicemanager.web.controller.referral.vo;

import com.moseeker.common.constants.Constant;

/**
 * @Date: 2018/9/26
 * @Author: JackYang
 */
public class ReferralBonusStageData {

    private String bonus = "0";
    private int stage_type = Constant.RECRUIT_STATUS_HIRED;//入职
    private int proportion = 100;

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public int getStage_type() {
        return stage_type;
    }

    public void setStage_type(int stage_type) {
        this.stage_type = stage_type;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }
}
