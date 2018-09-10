package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * 员工选择PC端上传的方式上传简历的通知表单
 * @Author: jack
 * @Date: 2018/9/6
 */
public class PCUploadProfileTypeForm {

    private int appid;
    private int position;
    private int type;           //1.手机文件上传 2. 电脑扫码上传 3. 推荐人才关键信息

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
