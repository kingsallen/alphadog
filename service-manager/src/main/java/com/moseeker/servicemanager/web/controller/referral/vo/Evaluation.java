package com.moseeker.servicemanager.web.controller.referral.vo;

/**
 * Created by moseeker on 2018/11/23.
 */
public class Evaluation {

    private int appid;
    private int userId;
    private int companyId;
    private int hrId;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }
}
