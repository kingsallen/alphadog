package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * 员工选择PC端上传的方式上传简历的通知表单
 * @Author: jack
 * @Date: 2018/9/6
 */
public class PCUploadProfileTypeForm {

    private int appid;
    private int position;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
