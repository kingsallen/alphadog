package com.moseeker.application.domain;

import java.io.Serializable;

/**
 * @author: huangwenjian
 * @desc:
 * @since: 2019-11-24 17:37
 */
public class ChannelEntity implements Serializable {

    /**
     * 渠道编号
     */
    private String code;

    /**
     * 投递来源id
     */
    private Integer sourceId;

    public ChannelEntity() {
    }

    public ChannelEntity(String code, Integer sourceId) {
        this.code = code;
        this.sourceId = sourceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
