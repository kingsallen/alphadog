package com.moseeker.servicemanager.web.controller.referral.form;

import com.moseeker.servicemanager.web.controller.referral.vo.ReferralBonusStageData;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2018/9/25
 * @Author: JackYang
 */
public class ReferralBonusForm implements Serializable{

    private static final long serialVersionUID = -2266728614120415854L;

    private Integer appid;
    private Integer total_bonus =0;
    private Integer position_id;
    private List<ReferralBonusStageData> stage_data;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getTotal_bonus() {
        return total_bonus;
    }

    public void setTotal_bonus(Integer total_bonus) {
        this.total_bonus = total_bonus;
    }

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public List<ReferralBonusStageData> getStage_data() {
        return stage_data;
    }

    public void setStage_data(List<ReferralBonusStageData> stage_data) {
        this.stage_data = stage_data;
    }
}
