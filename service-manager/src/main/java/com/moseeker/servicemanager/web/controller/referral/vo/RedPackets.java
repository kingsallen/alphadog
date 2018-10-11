package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * 个人中心  红包奖金 红包列表
 * @Author: jack
 * @Date: 2018/9/26
 */
public class RedPackets {

    private String totalRedpackets;
    private String totalBonus;
    private List<RedPacket> redpackets;

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

    public List<RedPacket> getRedpackets() {
        return redpackets;
    }

    public void setRedpackets(List<RedPacket> redpackets) {
        this.redpackets = redpackets;
    }
}
