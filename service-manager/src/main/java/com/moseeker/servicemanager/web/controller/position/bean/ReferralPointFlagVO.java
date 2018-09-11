package com.moseeker.servicemanager.web.controller.position.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Date: 2018/9/10
 * @Author: JackYang
 */
public class ReferralPointFlagVO implements Serializable{


    private static final long serialVersionUID = 3089549635807750892L;

    private Integer company_id;

    @JSONField(name ="flag")
    private Integer flag;

    public ReferralPointFlagVO(Integer company_id, Integer flag) {
        this.company_id = company_id;
        this.flag = flag;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
