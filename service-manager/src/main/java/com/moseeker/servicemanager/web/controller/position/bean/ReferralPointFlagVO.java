package com.moseeker.servicemanager.web.controller.position.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Date: 2018/9/10
 * @Author: JackYang
 */
public class ReferralPointFlagVO implements Serializable{


    private static final long serialVersionUID = 3089549635807750892L;

    @JSONField(name = "company_id")
    private Integer companyId;

    @JSONField(name ="flag")
    private Integer positionPointsFlag;


    public ReferralPointFlagVO(Integer companyId, Integer positionPointsFlag) {
        this.companyId = companyId;
        this.positionPointsFlag = positionPointsFlag;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getPositionPointsFlag() {
        return positionPointsFlag;
    }

    public void setPositionPointsFlag(Integer positionPointsFlag) {
        this.positionPointsFlag = positionPointsFlag;
    }
}
