package com.moseeker.servicemanager.web.controller.useraccounts.form;

import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moseeker on 2017/4/11.
 */
public class UserEmployeeBatch implements Serializable {
    private List<UserEmployeeStruct> data;

    public List<UserEmployeeStruct> getData() {
        return data;
    }

    public void setData(List<UserEmployeeStruct> data) {
        this.data = data;
    }
}
