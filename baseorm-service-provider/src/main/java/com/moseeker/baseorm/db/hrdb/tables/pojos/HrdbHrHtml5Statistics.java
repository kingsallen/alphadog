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
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrdbHrHtml5Statistics implements Serializable {

    private static final long serialVersionUID = 903390771;

    private Long   topicId;
    private Long   viewNumPv;
    private Double recomViewNumPv;
    private Long   companyId;
    private Date   createDate;

    public HrdbHrHtml5Statistics() {}

    public HrdbHrHtml5Statistics(HrdbHrHtml5Statistics value) {
        this.topicId = value.topicId;
        this.viewNumPv = value.viewNumPv;
        this.recomViewNumPv = value.recomViewNumPv;
        this.companyId = value.companyId;
        this.createDate = value.createDate;
    }

    public HrdbHrHtml5Statistics(
        Long   topicId,
        Long   viewNumPv,
        Double recomViewNumPv,
        Long   companyId,
        Date   createDate
    ) {
        this.topicId = topicId;
        this.viewNumPv = viewNumPv;
        this.recomViewNumPv = recomViewNumPv;
        this.companyId = companyId;
        this.createDate = createDate;
    }

    public Long getTopicId() {
        return this.topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getViewNumPv() {
        return this.viewNumPv;
    }

    public void setViewNumPv(Long viewNumPv) {
        this.viewNumPv = viewNumPv;
    }

    public Double getRecomViewNumPv() {
        return this.recomViewNumPv;
    }

    public void setRecomViewNumPv(Double recomViewNumPv) {
        this.recomViewNumPv = recomViewNumPv;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrdbHrHtml5Statistics (");

        sb.append(topicId);
        sb.append(", ").append(viewNumPv);
        sb.append(", ").append(recomViewNumPv);
        sb.append(", ").append(companyId);
        sb.append(", ").append(createDate);

        sb.append(")");
        return sb.toString();
    }
}