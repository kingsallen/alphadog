/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrHeadhunterProfile;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 猎头上传简历表 猎头上传简历临时存储，hr接收后为正式简历
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHeadhunterProfileRecord extends UpdatableRecordImpl<HrHeadhunterProfileRecord> implements Record10<Integer, Integer, String, String, String, String, Integer, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = 1455243504;

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.id</code>. 主键
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.id</code>. 主键
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.headhunter_id</code>. 猎头顾问ID
     */
    public void setHeadhunterId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.headhunter_id</code>. 猎头顾问ID
     */
    public Integer getHeadhunterId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.user_name</code>. 简历姓名
     */
    public void setUserName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.user_name</code>. 简历姓名
     */
    public String getUserName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.email</code>. 简历邮箱
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.email</code>. 简历邮箱
     */
    public String getEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.mobile</code>. 简历手机号码
     */
    public void setMobile(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.mobile</code>. 简历手机号码
     */
    public String getMobile() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.profile_parsing</code>. 简历信息
     */
    public void setProfileParsing(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.profile_parsing</code>. 简历信息
     */
    public String getProfileParsing() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.candidate_id</code>. hr_headhunter_candidate.id
     */
    public void setCandidateId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.candidate_id</code>. hr_headhunter_candidate.id
     */
    public Integer getCandidateId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_headhunter_profile.status</code>. 简历状态 0 ： 猎头上传， 1 ： hr 接收（正式简历）
     */
    public void setStatus(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_headhunter_profile.status</code>. 简历状态 0 ： 猎头上传， 1 ： hr 接收（正式简历）
     */
    public Byte getStatus() {
        return (Byte) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, String, String, String, Integer, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, String, String, String, Integer, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.HEADHUNTER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.USER_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.PROFILE_PARSING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.CANDIDATE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return HrHeadhunterProfile.HR_HEADHUNTER_PROFILE.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getHeadhunterId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUserName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getProfileParsing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCandidateId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value2(Integer value) {
        setHeadhunterId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value3(String value) {
        setUserName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value4(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value5(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value6(String value) {
        setProfileParsing(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value7(Integer value) {
        setCandidateId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord value10(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHeadhunterProfileRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, Integer value7, Timestamp value8, Timestamp value9, Byte value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrHeadhunterProfileRecord
     */
    public HrHeadhunterProfileRecord() {
        super(HrHeadhunterProfile.HR_HEADHUNTER_PROFILE);
    }

    /**
     * Create a detached, initialised HrHeadhunterProfileRecord
     */
    public HrHeadhunterProfileRecord(Integer id, Integer headhunterId, String userName, String email, String mobile, String profileParsing, Integer candidateId, Timestamp createTime, Timestamp updateTime, Byte status) {
        super(HrHeadhunterProfile.HR_HEADHUNTER_PROFILE);

        set(0, id);
        set(1, headhunterId);
        set(2, userName);
        set(3, email);
        set(4, mobile);
        set(5, profileParsing);
        set(6, candidateId);
        set(7, createTime);
        set(8, updateTime);
        set(9, status);
    }
}