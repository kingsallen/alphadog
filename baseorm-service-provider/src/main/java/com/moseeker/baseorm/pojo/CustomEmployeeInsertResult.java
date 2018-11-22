package com.moseeker.baseorm.pojo;

/**
 * 自定义员工认证的待认证添加结果
 * @Author: jack
 * @Date: 2018/11/19
 */
public class CustomEmployeeInsertResult {

    private int id;
    private int execute;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecute() {
        return execute;
    }

    public void setExecute(int execute) {
        this.execute = execute;
    }
}
