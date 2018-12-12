package com.moseeker.useraccounts.service.impl.vo;

import java.util.List;

public class RadarConnectResult {

    private Integer degree;
    private Integer state;
    private Integer pid;
    private List<RadarUserInfo> radarUserInfos;

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<RadarUserInfo> getRadarUserInfos() {
        return radarUserInfos;
    }

    public void setRadarUserInfos(List<RadarUserInfo> radarUserInfos) {
        this.radarUserInfos = radarUserInfos;
    }
}
