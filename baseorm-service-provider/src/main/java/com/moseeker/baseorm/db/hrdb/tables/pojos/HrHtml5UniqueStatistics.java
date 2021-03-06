/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Generated;


/**
 * 专题传播统计去重信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHtml5UniqueStatistics implements Serializable {

    private static final long serialVersionUID = -904805526;

    private Integer id;
    private Integer topicId;
    private Integer companyId;
    private Integer viewNumUv;
    private Integer recomViewNumUv;
    private Date    createDate;
    private Integer infoType;

    public HrHtml5UniqueStatistics() {}

    public HrHtml5UniqueStatistics(HrHtml5UniqueStatistics value) {
        this.id = value.id;
        this.topicId = value.topicId;
        this.companyId = value.companyId;
        this.viewNumUv = value.viewNumUv;
        this.recomViewNumUv = value.recomViewNumUv;
        this.createDate = value.createDate;
        this.infoType = value.infoType;
    }

    public HrHtml5UniqueStatistics(
        Integer id,
        Integer topicId,
        Integer companyId,
        Integer viewNumUv,
        Integer recomViewNumUv,
        Date    createDate,
        Integer infoType
    ) {
        this.id = id;
        this.topicId = topicId;
        this.companyId = companyId;
        this.viewNumUv = viewNumUv;
        this.recomViewNumUv = recomViewNumUv;
        this.createDate = createDate;
        this.infoType = infoType;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return this.topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getViewNumUv() {
        return this.viewNumUv;
    }

    public void setViewNumUv(Integer viewNumUv) {
        this.viewNumUv = viewNumUv;
    }

    public Integer getRecomViewNumUv() {
        return this.recomViewNumUv;
    }

    public void setRecomViewNumUv(Integer recomViewNumUv) {
        this.recomViewNumUv = recomViewNumUv;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getInfoType() {
        return this.infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrHtml5UniqueStatistics (");

        sb.append(id);
        sb.append(", ").append(topicId);
        sb.append(", ").append(companyId);
        sb.append(", ").append(viewNumUv);
        sb.append(", ").append(recomViewNumUv);
        sb.append(", ").append(createDate);
        sb.append(", ").append(infoType);

        sb.append(")");
        return sb.toString();
    }
}
