package com.moseeker.profile.service.impl.vo;

/**
 * @author cjm
 * @date 2018-11-02 9:34
 **/
public class PositionIdTitleDTO {

    private Integer positionId;
    private String title;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PositionIdTitleDTO() {
    }

    public PositionIdTitleDTO(Integer positionId, String title) {
        this.positionId = positionId;
        this.title = title;
    }
}
