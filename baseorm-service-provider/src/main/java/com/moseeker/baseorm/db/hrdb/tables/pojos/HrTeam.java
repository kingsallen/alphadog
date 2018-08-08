/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 团队信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTeam implements Serializable {

    private static final long serialVersionUID = -715993310;

    private Integer   id;
    private String    name;
    private String    summary;
    private String    description;
    private Integer   showOrder;
    private String    jdMedia;
    private Integer   companyId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   isShow;
    private String    slogan;
    private Integer   resId;
    private String    teamDetail;
    private Integer   disable;
    private String    subTitle;
    private String    resAttrs;
    private String    link;

    public HrTeam() {}

    public HrTeam(HrTeam value) {
        this.id = value.id;
        this.name = value.name;
        this.summary = value.summary;
        this.description = value.description;
        this.showOrder = value.showOrder;
        this.jdMedia = value.jdMedia;
        this.companyId = value.companyId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.isShow = value.isShow;
        this.slogan = value.slogan;
        this.resId = value.resId;
        this.teamDetail = value.teamDetail;
        this.disable = value.disable;
        this.subTitle = value.subTitle;
        this.resAttrs = value.resAttrs;
        this.link = value.link;
    }

    public HrTeam(
        Integer   id,
        String    name,
        String    summary,
        String    description,
        Integer   showOrder,
        String    jdMedia,
        Integer   companyId,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   isShow,
        String    slogan,
        Integer   resId,
        String    teamDetail,
        Integer   disable,
        String    subTitle,
        String    resAttrs,
        String    link
    ) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.showOrder = showOrder;
        this.jdMedia = jdMedia;
        this.companyId = companyId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isShow = isShow;
        this.slogan = slogan;
        this.resId = resId;
        this.teamDetail = teamDetail;
        this.disable = disable;
        this.subTitle = subTitle;
        this.resAttrs = resAttrs;
        this.link = link;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getShowOrder() {
        return this.showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public String getJdMedia() {
        return this.jdMedia;
    }

    public void setJdMedia(String jdMedia) {
        this.jdMedia = jdMedia;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Integer getIsShow() {
        return this.isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getResId() {
        return this.resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getTeamDetail() {
        return this.teamDetail;
    }

    public void setTeamDetail(String teamDetail) {
        this.teamDetail = teamDetail;
    }

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getResAttrs() {
        return this.resAttrs;
    }

    public void setResAttrs(String resAttrs) {
        this.resAttrs = resAttrs;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrTeam (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(summary);
        sb.append(", ").append(description);
        sb.append(", ").append(showOrder);
        sb.append(", ").append(jdMedia);
        sb.append(", ").append(companyId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(isShow);
        sb.append(", ").append(slogan);
        sb.append(", ").append(resId);
        sb.append(", ").append(teamDetail);
        sb.append(", ").append(disable);
        sb.append(", ").append(subTitle);
        sb.append(", ").append(resAttrs);
        sb.append(", ").append(link);

        sb.append(")");
        return sb.toString();
    }
}
