package com.moseeker.profile.service.impl.retriveprofile.parameters;

/**
 * 阿里用户处理任务参数
 * Created by jack on 19/07/2017.
 */
public class UserAliTaskParam {

    private int userId;             //c端用户编号
    private String uid;             //阿里平台用户编号

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
