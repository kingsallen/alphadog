package com.moseeker.servicemanager.web.controller.useraccounts.vo;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class LeaderBoardType {

    private int id;
    private int companyId;
    private int type;

    public static LeaderBoardType instanceFrom(com.moseeker.thrift.gen.employee.struct.LeaderBoardType type) {
        if (type != null) {
            LeaderBoardType leaderBoardType = new LeaderBoardType();
            leaderBoardType.setId(type.getId());
            leaderBoardType.setCompanyId(type.getCompany_id());
            leaderBoardType.setType(type.getType());
            return leaderBoardType;
        } else {
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
