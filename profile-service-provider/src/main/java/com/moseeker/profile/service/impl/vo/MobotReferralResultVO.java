package com.moseeker.profile.service.impl.vo;

/**
 * mobot推荐职位处理结果vo
 *
 * @author cjm
 * @date 2018-11-01 10:59
 **/
public class MobotReferralResultVO {

    private Integer id;
    private Integer positionId;
    private String title;
    private String reason;
    private Boolean success;

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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        return "MobotReferralResultVO{" +
                "id=" + id +
                ", positionId=" + positionId +
                ", title='" + title + '\'' +
                ", reason='" + reason + '\'' +
                ", success=" + success +
                '}';
    }
}
