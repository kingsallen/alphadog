package com.moseeker.useraccounts.service.impl.vo;

import com.moseeker.entity.pojos.RadarUserInfo;
import java.util.List;

public class RadarConnectResult {

    private Integer degree;
    private Integer state;
    private Integer pid;
    private Integer parent_id;
    private List<RadarUserInfo> chain;

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

    public List<RadarUserInfo> getChain() {
        return chain;
    }

    public void setChain(List<RadarUserInfo> chain) {
        this.chain = chain;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
}
