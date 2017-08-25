package com.moseeker.profile.service.impl.retriveprofile.parameters;

/**
 * 申请任务参数
 * Created by jack on 19/07/2017.
 */
public class ApplicationTaskParam {

    private int userId;
    private int positionId;
    private int origin;

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
}
