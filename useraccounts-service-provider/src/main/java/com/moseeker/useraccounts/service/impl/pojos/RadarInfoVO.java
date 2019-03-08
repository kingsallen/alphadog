package com.moseeker.useraccounts.service.impl.pojos;

import java.util.List;

/**
 * Created by moseeker on 2018/12/29.
 */
public class RadarInfoVO {

    public int totalCount;
    public int page;
    public List<RadarUserVO> userList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<RadarUserVO> getUserList() {
        return userList;
    }

    public void setUserList(List<RadarUserVO> userList) {
        this.userList = userList;
    }
}
