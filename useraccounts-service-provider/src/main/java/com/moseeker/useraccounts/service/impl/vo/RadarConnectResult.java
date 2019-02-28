package com.moseeker.useraccounts.service.impl.vo;

import com.moseeker.entity.pojos.RadarUserInfo;
import java.util.List;
import java.util.Set;

public class RadarConnectResult {

    private Integer degree;
    private Integer state;
    private Integer pid;
    private Integer parent_id;
    private List<RadarUserInfo> chain;
    private Set<Integer> enable_viewer;

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

    public Set<Integer> getEnable_viewer() {
        return enable_viewer;
    }

    public void setEnable_viewer(Set<Integer> enable_viewer) {
        this.enable_viewer = enable_viewer;
    }
}
