package com.moseeker.useraccounts.service.impl.pojos;

import java.util.List;

/**
 * Created by moseeker on 2019/1/4.
 */
public class EmployeeForwardViewVO {

    public int tatolCount; // optional
    public int page; // optional
    public List<EmployeeForwardViewVO> userList; // optional

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

    public List<EmployeeForwardViewVO> getUserList() {
        return userList;
    }

    public void setUserList(List<EmployeeForwardViewVO> userList) {
        this.userList = userList;
    }
}
