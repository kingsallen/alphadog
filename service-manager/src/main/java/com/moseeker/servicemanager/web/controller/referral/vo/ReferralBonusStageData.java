package com.moseeker.servicemanager.web.controller.referral.vo;

/**
 * @Date: 2018/9/26
 * @Author: JackYang
 */
public class ReferralBonusStageData {

    private int bonus = 0;
    private int stage_type = 1;
    private int proportion = 100;
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public int getBonus() {
        return bonus;
    }

    public void setStage_type(int stage_type) {
        this.stage_type = stage_type;
    }
    public int getStage_type() {
        return stage_type;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }
    public int getProportion() {
        return proportion;
    }

}
