package com.moseeker.application.service.impl;

/**
 * 用户投递次数
 * Created by jack on 21/12/2017.
 */
public class UserApplyCount {

    private int socialApplyCount;
    private int schoolApplyCount;
    private boolean init;

    public int getSocialApplyCount() {
        return socialApplyCount;
    }

    public void setSocialApplyCount(int socialApplyCount) {
        this.socialApplyCount = socialApplyCount;
    }

    public int getSchoolApplyCount() {
        return schoolApplyCount;
    }

    public void setSchoolApplyCount(int schoolApplyCount) {
        this.schoolApplyCount = schoolApplyCount;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }
}
