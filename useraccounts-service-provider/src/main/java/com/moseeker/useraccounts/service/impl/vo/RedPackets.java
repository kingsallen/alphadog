package com.moseeker.useraccounts.service.impl.vo;

import java.util.List;

/**
 * 个人中心  红包奖金 红包列表
 * @Author: jack
 * @Date: 2018/9/26
 */
public class RedPackets {

    private double totalRedpackets;
    private double totalBonus;
    private List<RedPacket> redpackets;

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

    public List<RedPacket> getRedpackets() {
        return redpackets;
    }

    public void setRedpackets(List<RedPacket> redpackets) {
        this.redpackets = redpackets;
    }
}
