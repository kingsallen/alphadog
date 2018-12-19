package com.moseeker.servicemanager.web.neo4j.form;

/**
 * Created by moseeker on 2018/12/18.
 */
public class ForwardInsertForm {
    private int appid;
    private int startUserId;
    private int endUserId;
    private int shareChainId;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(int startUserId) {
        this.startUserId = startUserId;
    }

    public int getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(int endUserId) {
        this.endUserId = endUserId;
    }

    public int getShareChainId() {
        return shareChainId;
    }

    public void setShareChainId(int shareChainId) {
        this.shareChainId = shareChainId;
    }
}
