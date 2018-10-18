package com.moseeker.useraccounts.service.impl.vo;

import java.util.List;

/**
 * 个人中心奖金列表
 */
public class BonusList {

    private double totalRedpackets;
    private double totalBonus;
    private List<Bonus> bonus;

    public double getTotalRedpackets() {
        return totalRedpackets;
    }

    public void setTotalRedpackets(double totalRedpackets) {
        this.totalRedpackets = totalRedpackets;
    }

    public double getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(double totalBonus) {
        this.totalBonus = totalBonus;
    }

    public List<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(List<Bonus> bonus) {
        this.bonus = bonus;
    }
}
