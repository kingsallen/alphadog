/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrdbHrHtml5UniqueStatistics implements Serializable {

    private static final long serialVersionUID = -183369972;

    private Long   topicId;
    private Long   viewNumUv;
    private Double recomViewNumUv;
    private Long   companyId;
    private Date   createDate;
    private String infoType;

    public HrdbHrHtml5UniqueStatistics() {}

    public HrdbHrHtml5UniqueStatistics(HrdbHrHtml5UniqueStatistics value) {
        this.topicId = value.topicId;
        this.viewNumUv = value.viewNumUv;
        this.recomViewNumUv = value.recomViewNumUv;
        this.companyId = value.companyId;
        this.createDate = value.createDate;
        this.infoType = value.infoType;
    }

    public HrdbHrHtml5UniqueStatistics(
        Long   topicId,
        Long   viewNumUv,
        Double recomViewNumUv,
        Long   companyId,
        Date   createDate,
        String infoType
    ) {
        this.topicId = topicId;
        this.viewNumUv = viewNumUv;
        this.recomViewNumUv = recomViewNumUv;
        this.companyId = companyId;
        this.createDate = createDate;
        this.infoType = infoType;
    }

    public Long getTopicId() {
        return this.topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getViewNumUv() {
        return this.viewNumUv;
    }

    public void setViewNumUv(Long viewNumUv) {
        this.viewNumUv = viewNumUv;
    }

    public Double getRecomViewNumUv() {
        return this.recomViewNumUv;
    }

    public void setRecomViewNumUv(Double recomViewNumUv) {
        this.recomViewNumUv = recomViewNumUv;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getInfoType() {
        return this.infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrdbHrHtml5UniqueStatistics (");

        sb.append(topicId);
        sb.append(", ").append(viewNumUv);
        sb.append(", ").append(recomViewNumUv);
        sb.append(", ").append(companyId);
        sb.append(", ").append(createDate);
        sb.append(", ").append(infoType);

        sb.append(")");
        return sb.toString();
    }
}
