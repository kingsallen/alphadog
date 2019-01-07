package com.moseeker.servicemanager.web.controller.useraccounts.vo;

import java.util.List;

/**
 * Created by moseeker on 2019/1/4.
 */
public class EmployeeForwardViewVO {

    public int tatolCount; // optional
    public int page; // optional
    public List<EmployeeForwardViewPageVO> userList; // optional

    public int getTatolCount() {
        return tatolCount;
    }

    public void setTatolCount(int tatolCount) {
        this.tatolCount = tatolCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<EmployeeForwardViewPageVO> getUserList() {
        return userList;
    }

    public void setUserList(List<EmployeeForwardViewPageVO> userList) {
        this.userList = userList;
    }
}
