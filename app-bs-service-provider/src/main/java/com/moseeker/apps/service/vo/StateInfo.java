package com.moseeker.apps.service.vo;

/**
 * @Author: jack
 * @Date: 2018/9/28
 */
public class StateInfo {
    private int id;
    private int state;
    private int preState;
    private int recruitOrder;
    private int preRecruitOrder;
    private int applierId;
    private int positionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPreState() {
        return preState;
    }

    public void setPreState(int preState) {
        this.preState = preState;
    }

    public int getRecruitOrder() {
        return recruitOrder;
    }

    public void setRecruitOrder(int recruitOrder) {
        this.recruitOrder = recruitOrder;
    }

    public int getPreRecruitOrder() {
        return preRecruitOrder;
    }

    public int getApplierId() {
        return applierId;
    }

    public void setApplierId(int applierId) {
        this.applierId = applierId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setPreRecruitOrder(int preRecruitOrder) {
        this.preRecruitOrder = preRecruitOrder;

    }
}
