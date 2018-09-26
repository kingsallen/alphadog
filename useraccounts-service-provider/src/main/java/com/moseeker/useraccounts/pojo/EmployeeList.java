package com.moseeker.useraccounts.pojo;

import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
public class EmployeeList {

    private Integer total;
    private List<UserEmployeeDO> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<UserEmployeeDO> getData() {
        return data;
    }

    public void setData(List<UserEmployeeDO> data) {
        this.data = data;
    }
}
