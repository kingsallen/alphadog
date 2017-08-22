package com.moseeker.useraccounts.pojo;

import java.util.List;

/**
 * Created by YYF
 *
 * Date: 2017/8/3
 *
 * Project_name :alphadog
 */
public class EmployeeRankObj {

    private Integer total;
    private List<EmployeeRank> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<EmployeeRank> getData() {
        return data;
    }

    public void setData(List<EmployeeRank> data) {
        this.data = data;
    }
}
