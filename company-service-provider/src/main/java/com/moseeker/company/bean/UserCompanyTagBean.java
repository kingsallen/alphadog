package com.moseeker.company.bean;

import java.util.Set;

/**
 * Created by zztaiwll on 18/4/13.
 *用户下的companytag列表
 *
 */
public class UserCompanyTagBean {
    private Integer userId;
    private Set<Integer> companytagidList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Integer> getCompanytagidList() {
        return companytagidList;
    }

    public void setCompanytagidList(Set<Integer> companytagidList) {
        this.companytagidList = companytagidList;
    }
}
