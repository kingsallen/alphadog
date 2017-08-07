package com.moseeker.useraccounts.pojo;

/**
 * Created by YYF
 *
 * Date: 2017/6/30
 *
 * Project_name :alphadog
 *
 * 集团下认证员和未认证员工数
 */
public class EmployeeStat {

    // 未认证员工数
    private Integer unregcount;
    // 认证员工数
    private Integer regcount;
    // 总数
    private Integer totalCount;

    public Integer getUnregcount() {
        return unregcount;
    }

    public void setUnregcount(Integer unregcount) {
        this.unregcount = unregcount;
    }

    public Integer getRegcount() {
        return regcount;
    }

    public void setRegcount(Integer regcount) {
        this.regcount = regcount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = this.regcount + this.unregcount;
    }
}
