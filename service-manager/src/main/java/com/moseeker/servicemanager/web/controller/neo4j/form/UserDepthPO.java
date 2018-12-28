package com.moseeker.servicemanager.web.controller.neo4j.form;

/**
 * Created by moseeker on 2018/12/28.
 */
public class UserDepthPO {
    private int userId;
    private int depth;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
