package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * mobot推荐职位信息
 *
 * @author cjm
 * @date 2018-10-29 11:29
 **/
public class ReferralPositionForm {
    private List<Integer> ids;
    private Integer appid;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }


    @Override
    public String toString() {
        return "ReferralPositionForm{" +
                "ids=" + ids +
                ", appid=" + appid +
                '}';
    }
}
