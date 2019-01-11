package com.moseeker.servicemanager.web.controller.neo4j.form;


/**
 * Created by moseeker on 2018/12/27.
 */
public class EmployeeCompanyPO {

    private int userId;
    private int companyId;

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
}
