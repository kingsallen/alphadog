package com.moseeker.baseorm.pojo;

import java.io.Serializable;

/**
 * Created by zztaiwll on 17/11/3.
 */
public class HistoryCampaignPersonaRecomPojo implements Serializable {
    private static final long serialVersionUID = 10L;
    private int id;
    private int userId;
    private int positionId;
    private String createTime;
    private byte isSend;
    private String sendTime;
    private String updateTime;
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getPositionId() {
        return positionId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public byte getIsSend() {
        return isSend;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setIsSend(byte isSend) {
        this.isSend = isSend;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "HistoryCampaignPersonaRecomDao{" +
                "id=" + id +
                ", userId=" + userId +
                ", positionId=" + positionId +
                ", createTime='" + createTime + '\'' +
                ", isSend=" + isSend +
                ", sendTime='" + sendTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
