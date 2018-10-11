package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * 个人中心奖金列表
 */
public class BonusList {

    private String totalRedpackets;
    private String totalBonus;
    private List<Bonus> bonus;

    public String getTotalRedpackets() {
        return totalRedpackets;
    }

    public void setTotalRedpackets(String totalRedpackets) {
        this.totalRedpackets = totalRedpackets;
    }

    public String getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(String totalBonus) {
        this.totalBonus = totalBonus;
    }

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }
}
