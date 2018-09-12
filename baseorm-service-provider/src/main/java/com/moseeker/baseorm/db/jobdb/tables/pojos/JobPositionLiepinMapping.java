/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 职位发布到猎聘时，由于不同地区、职位名称在猎聘需用不同的id，而在仟寻只有一个id，所以此表用来生成向猎聘发布职位时需要的id
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionLiepinMapping implements Serializable {

    private static final long serialVersionUID = 759631685;

    private Integer   id;
    private Integer   positionId;
    private Integer   liepinJobId;
    private Integer   cityCode;
    private String    jobTitle;
    private String    errMsg;
    private Byte      state;
    private Integer   liepinUserId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public JobPositionLiepinMapping() {}

    public JobPositionLiepinMapping(JobPositionLiepinMapping value) {
        this.id = value.id;
        this.positionId = value.positionId;
        this.liepinJobId = value.liepinJobId;
        this.cityCode = value.cityCode;
        this.jobTitle = value.jobTitle;
        this.errMsg = value.errMsg;
        this.state = value.state;
        this.liepinUserId = value.liepinUserId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public JobPositionLiepinMapping(
        Integer   id,
        Integer   positionId,
        Integer   liepinJobId,
        Integer   cityCode,
        String    jobTitle,
        String    errMsg,
        Byte      state,
        Integer   liepinUserId,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.positionId = positionId;
        this.liepinJobId = liepinJobId;
        this.cityCode = cityCode;
        this.jobTitle = jobTitle;
        this.errMsg = errMsg;
        this.state = state;
        this.liepinUserId = liepinUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return this.positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getLiepinJobId() {
        return this.liepinJobId;
    }

    public void setLiepinJobId(Integer liepinJobId) {
        this.liepinJobId = liepinJobId;
    }

    public Integer getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Byte getState() {
        return this.state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getLiepinUserId() {
        return this.liepinUserId;
    }

    public void setLiepinUserId(Integer liepinUserId) {
        this.liepinUserId = liepinUserId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("JobPositionLiepinMapping (");

        sb.append(id);
        sb.append(", ").append(positionId);
        sb.append(", ").append(liepinJobId);
        sb.append(", ").append(cityCode);
        sb.append(", ").append(jobTitle);
        sb.append(", ").append(errMsg);
        sb.append(", ").append(state);
        sb.append(", ").append(liepinUserId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
