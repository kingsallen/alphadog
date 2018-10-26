package com.moseeker.servicemanager.web.controller.useraccounts.form;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/10/22
 */
public class EmployeeExtInfo {

    private int id;
    private List<String> options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
