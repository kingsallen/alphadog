package com.moseeker.profile.service.impl.retriveprofile.parameters;

/**
 * 创建密码任务的参数
 * Created by jack on 19/07/2017.
 */
public class CratePasswordTaskParam {

    private int userId;             //c端用户编号，需要根据这个编号修改密码
    private String name;            //用户名称，用于发短信时，称呼对方

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
