/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 申请状态记录（ats北森）
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobApplicationStatusBeisen_20170612 implements Serializable {

    private static final long serialVersionUID = -76741927;

    private Integer   id;
    private Integer   companyId;
    private Long      applierMobile;
    private String    applierName;
    private String    jobnumber;
    private String    jobtitle;
    private Integer   phasecode;
    private String    phasename;
    private Integer   statuscode;
    private String    statusname;
    private Timestamp createTime;
    private Timestamp updateTime;

    public JobApplicationStatusBeisen_20170612() {}

    public JobApplicationStatusBeisen_20170612(JobApplicationStatusBeisen_20170612 value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.applierMobile = value.applierMobile;
        this.applierName = value.applierName;
        this.jobnumber = value.jobnumber;
        this.jobtitle = value.jobtitle;
        this.phasecode = value.phasecode;
        this.phasename = value.phasename;
        this.statuscode = value.statuscode;
        this.statusname = value.statusname;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public JobApplicationStatusBeisen_20170612(
        Integer   id,
        Integer   companyId,
        Long      applierMobile,
        String    applierName,
        String    jobnumber,
        String    jobtitle,
        Integer   phasecode,
        String    phasename,
        Integer   statuscode,
        String    statusname,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.applierMobile = applierMobile;
        this.applierName = applierName;
        this.jobnumber = jobnumber;
        this.jobtitle = jobtitle;
        this.phasecode = phasecode;
        this.phasename = phasename;
        this.statuscode = statuscode;
        this.statusname = statusname;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Long getApplierMobile() {
        return this.applierMobile;
    }

    public void setApplierMobile(Long applierMobile) {
        this.applierMobile = applierMobile;
    }

    public String getApplierName() {
        return this.applierName;
    }

    public void setApplierName(String applierName) {
        this.applierName = applierName;
    }

    public String getJobnumber() {
        return this.jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getJobtitle() {
        return this.jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public Integer getPhasecode() {
        return this.phasecode;
    }

    public void setPhasecode(Integer phasecode) {
        this.phasecode = phasecode;
    }

    public String getPhasename() {
        return this.phasename;
    }

    public void setPhasename(String phasename) {
        this.phasename = phasename;
    }

    public Integer getStatuscode() {
        return this.statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatusname() {
        return this.statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
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
        StringBuilder sb = new StringBuilder("JobApplicationStatusBeisen_20170612 (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(applierMobile);
        sb.append(", ").append(applierName);
        sb.append(", ").append(jobnumber);
        sb.append(", ").append(jobtitle);
        sb.append(", ").append(phasecode);
        sb.append(", ").append(phasename);
        sb.append(", ").append(statuscode);
        sb.append(", ").append(statusname);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
