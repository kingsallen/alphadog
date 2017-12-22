/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 微信端新jd模块具体内容项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCmsMedia implements Serializable {

    private static final long serialVersionUID = -634289029;

    private Integer   id;
    private Integer   moduleId;
    private String    longtexts;
    private String    attrs;
    private String    title;
    private String    subTitle;
    private String    link;
    private Integer   resId;
    private Integer   orders;
    private Integer   isShow;
    private Integer   disable;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrCmsMedia() {}

    public HrCmsMedia(HrCmsMedia value) {
        this.id = value.id;
        this.moduleId = value.moduleId;
        this.longtexts = value.longtexts;
        this.attrs = value.attrs;
        this.title = value.title;
        this.subTitle = value.subTitle;
        this.link = value.link;
        this.resId = value.resId;
        this.orders = value.orders;
        this.isShow = value.isShow;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrCmsMedia(
        Integer   id,
        Integer   moduleId,
        String    longtexts,
        String    attrs,
        String    title,
        String    subTitle,
        String    link,
        Integer   resId,
        Integer   orders,
        Integer   isShow,
        Integer   disable,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.moduleId = moduleId;
        this.longtexts = longtexts;
        this.attrs = attrs;
        this.title = title;
        this.subTitle = subTitle;
        this.link = link;
        this.resId = resId;
        this.orders = orders;
        this.isShow = isShow;
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

    public Integer getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getLongtexts() {
        return this.longtexts;
    }

    public void setLongtexts(String longtexts) {
        this.longtexts = longtexts;
    }

    public String getAttrs() {
        return this.attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getResId() {
        return this.resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getOrders() {
        return this.orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getIsShow() {
        return this.isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
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
        StringBuilder sb = new StringBuilder("HrCmsMedia (");

        sb.append(id);
        sb.append(", ").append(moduleId);
        sb.append(", ").append(longtexts);
        sb.append(", ").append(attrs);
        sb.append(", ").append(title);
        sb.append(", ").append(subTitle);
        sb.append(", ").append(link);
        sb.append(", ").append(resId);
        sb.append(", ").append(orders);
        sb.append(", ").append(isShow);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
