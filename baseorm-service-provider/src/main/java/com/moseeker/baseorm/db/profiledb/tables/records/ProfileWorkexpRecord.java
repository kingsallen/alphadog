/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables.records;


import com.moseeker.baseorm.db.profiledb.tables.ProfileWorkexp;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * Profile的工作经历
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileWorkexpRecord extends UpdatableRecordImpl<ProfileWorkexpRecord> {

    private static final long serialVersionUID = -391009500;

    /**
     * Setter for <code>profiledb.profile_workexp.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.profile_id</code>. profile.id
     */
    public void setProfileId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.profile_id</code>. profile.id
     */
    public Integer getProfileId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.start</code>. 起止时间-起 yyyy-mm-dd
     */
    public void setStart(Date value) {
        set(2, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.start</code>. 起止时间-起 yyyy-mm-dd
     */
    public Date getStart() {
        return (Date) get(2);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.end</code>. 起止时间-止 yyyy-mm-dd
     */
    public void setEnd(Date value) {
        set(3, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.end</code>. 起止时间-止 yyyy-mm-dd
     */
    public Date getEnd() {
        return (Date) get(3);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.end_until_now</code>. 是否至今 0：否 1：是
     */
    public void setEndUntilNow(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.end_until_now</code>. 是否至今 0：否 1：是
     */
    public Byte getEndUntilNow() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.salary_code</code>. 薪资code
     */
    public void setSalaryCode(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.salary_code</code>. 薪资code
     */
    public Byte getSalaryCode() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.industry_code</code>. 行业字典编码
     */
    public void setIndustryCode(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.industry_code</code>. 行业字典编码
     */
    public Integer getIndustryCode() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.industry_name</code>. 行业名称
     */
    public void setIndustryName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.industry_name</code>. 行业名称
     */
    public String getIndustryName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.company_id</code>. 公司ID, hr_company.id
     */
    public void setCompanyId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.company_id</code>. 公司ID, hr_company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.department_name</code>. 部门名称
     */
    public void setDepartmentName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.department_name</code>. 部门名称
     */
    public String getDepartmentName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.position_code</code>. 职能字典编码
     */
    public void setPositionCode(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.position_code</code>. 职能字典编码
     */
    public Integer getPositionCode() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.position_name</code>. 职能字典名称
     */
    public void setPositionName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.position_name</code>. 职能字典名称
     */
    public String getPositionName() {
        return (String) get(11);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.description</code>. 职位描述
     */
    public void setDescription(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.description</code>. 职位描述
     */
    public String getDescription() {
        return (String) get(12);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.type</code>. 工作类型 0:没选择 1:全职 2:兼职 3:实习
     */
    public void setType(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.type</code>. 工作类型 0:没选择 1:全职 2:兼职 3:实习
     */
    public Byte getType() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.city_code</code>. 工作地点（城市），字典编码
     */
    public void setCityCode(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.city_code</code>. 工作地点（城市），字典编码
     */
    public Integer getCityCode() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.city_name</code>. 工作地点（城市）名称
     */
    public void setCityName(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.city_name</code>. 工作地点（城市）名称
     */
    public String getCityName() {
        return (String) get(15);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.report_to</code>. 汇报对象
     */
    public void setReportTo(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.report_to</code>. 汇报对象
     */
    public String getReportTo() {
        return (String) get(16);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.underlings</code>. 下属人数, 0:没有下属
     */
    public void setUnderlings(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.underlings</code>. 下属人数, 0:没有下属
     */
    public Integer getUnderlings() {
        return (Integer) get(17);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.reference</code>. 证明人
     */
    public void setReference(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.reference</code>. 证明人
     */
    public String getReference() {
        return (String) get(18);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.resign_reason</code>. 离职原因
     */
    public void setResignReason(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.resign_reason</code>. 离职原因
     */
    public String getResignReason() {
        return (String) get(19);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.achievement</code>. 主要业绩
     */
    public void setAchievement(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.achievement</code>. 主要业绩
     */
    public String getAchievement() {
        return (String) get(20);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(21);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(22, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(22);
    }

    /**
     * Setter for <code>profiledb.profile_workexp.job</code>. 所处职位
     */
    public void setJob(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>profiledb.profile_workexp.job</code>. 所处职位
     */
    public String getJob() {
        return (String) get(23);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProfileWorkexpRecord
     */
    public ProfileWorkexpRecord() {
        super(ProfileWorkexp.PROFILE_WORKEXP);
    }

    /**
     * Create a detached, initialised ProfileWorkexpRecord
     */
    public ProfileWorkexpRecord(Integer id, Integer profileId, Date start, Date end, Byte endUntilNow, Byte salaryCode, Integer industryCode, String industryName, Integer companyId, String departmentName, Integer positionCode, String positionName, String description, Byte type, Integer cityCode, String cityName, String reportTo, Integer underlings, String reference, String resignReason, String achievement, Timestamp createTime, Timestamp updateTime, String job) {
        super(ProfileWorkexp.PROFILE_WORKEXP);

        set(0, id);
        set(1, profileId);
        set(2, start);
        set(3, end);
        set(4, endUntilNow);
        set(5, salaryCode);
        set(6, industryCode);
        set(7, industryName);
        set(8, companyId);
        set(9, departmentName);
        set(10, positionCode);
        set(11, positionName);
        set(12, description);
        set(13, type);
        set(14, cityCode);
        set(15, cityName);
        set(16, reportTo);
        set(17, underlings);
        set(18, reference);
        set(19, resignReason);
        set(20, achievement);
        set(21, createTime);
        set(22, updateTime);
        set(23, job);
    }
}
