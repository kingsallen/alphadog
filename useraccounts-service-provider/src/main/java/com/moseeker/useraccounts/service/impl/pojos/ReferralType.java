package com.moseeker.useraccounts.service.impl.pojos;

/**
 * 推荐类型数据
 * @Author: jack
 * @Date: 2018/9/6
 */
public class ReferralType {

    private int employeeId;
    private int positionId;
    private byte type;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
