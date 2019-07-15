package com.moseeker.profile.service.impl.vo;

/**
 * mobot推荐职位处理结果vo
 *
 * @author cjm
 * @date 2018-11-01 10:59
 **/
public class MobotReferralResultVO {

    private Integer id;
    private Integer position_id;
    private String title;
    private String reason;
    private int errorCode ;
    private Boolean success;
    private double reward;

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "MobotReferralResultVO{" +
                "id=" + id +
                ", position_id=" + position_id +
                ", title='" + title + '\'' +
                ", reason='" + reason + '\'' +
                ", errorCode=" + errorCode +
                ", success=" + success +
                '}';
    }
}
