/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.campaigndb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignPcBanner implements Serializable {

    private static final long serialVersionUID = 1166146544;

    private Integer   id;
    private String    href;
    private String    imgUrl;
    private Byte      disable;
    private Timestamp createTime;
    private Timestamp updateTime;

    public CampaignPcBanner() {}

    public CampaignPcBanner(CampaignPcBanner value) {
        this.id = value.id;
        this.href = value.href;
        this.imgUrl = value.imgUrl;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public CampaignPcBanner(
        Integer   id,
        String    href,
        String    imgUrl,
        Byte      disable,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.href = href;
        this.imgUrl = imgUrl;
        this.disable = disable;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Byte getDisable() {
        return this.disable;
    }

    public void setDisable(Byte disable) {
        this.disable = disable;
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
        StringBuilder sb = new StringBuilder("CampaignPcBanner (");

        sb.append(id);
        sb.append(", ").append(href);
        sb.append(", ").append(imgUrl);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
