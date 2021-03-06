/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb.tables.records;


import com.moseeker.baseorm.db.candidatedb.tables.CandidateRemark;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * HR对候选人备注信息信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateRemarkRecord extends UpdatableRecordImpl<CandidateRemarkRecord> implements Record19<Integer, Integer, Integer, Integer, String, String, String, String, String, String, String, Timestamp, Byte, String, Timestamp, Timestamp, Integer, String, Integer> {

    private static final long serialVersionUID = -1038646660;

    /**
     * Setter for <code>candidatedb.candidate_remark.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.hraccount_id</code>. 做候选人标记的账号编号 hr_account.id
     */
    public void setHraccountId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.hraccount_id</code>. 做候选人标记的账号编号 hr_account.id
     */
    public Integer getHraccountId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.wxuser_id</code>. user_wx_user.id 被推荐者微信 ID。已经废弃，微信用户信息由C端账号信息代替
     */
    public void setWxuserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.wxuser_id</code>. user_wx_user.id 被推荐者微信 ID。已经废弃，微信用户信息由C端账号信息代替
     */
    public Integer getWxuserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.gender</code>. 0：未知，1：男，2：女
     */
    public void setGender(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.gender</code>. 0：未知，1：男，2：女
     */
    public Integer getGender() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.age</code>. 年龄
     */
    public void setAge(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.age</code>. 年龄
     */
    public String getAge() {
        return (String) get(4);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.mobile</code>. 联系方式
     */
    public void setMobile(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.mobile</code>. 联系方式
     */
    public String getMobile() {
        return (String) get(5);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.email</code>. 邮箱
     */
    public void setEmail(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.email</code>. 邮箱
     */
    public String getEmail() {
        return (String) get(6);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.current_company</code>. 现处公司
     */
    public void setCurrentCompany(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.current_company</code>. 现处公司
     */
    public String getCurrentCompany() {
        return (String) get(7);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.current_position</code>. 职务
     */
    public void setCurrentPosition(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.current_position</code>. 职务
     */
    public String getCurrentPosition() {
        return (String) get(8);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.education</code>. 毕业院校
     */
    public void setEducation(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.education</code>. 毕业院校
     */
    public String getEducation() {
        return (String) get(9);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.degree</code>. 学历
     */
    public void setDegree(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.degree</code>. 学历
     */
    public String getDegree() {
        return (String) get(10);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.graduate_at</code>. 毕业时间
     */
    public void setGraduateAt(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.graduate_at</code>. 毕业时间
     */
    public Timestamp getGraduateAt() {
        return (Timestamp) get(11);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.is_star</code>. 0: 星标 1: 没有星标
     */
    public void setIsStar(Byte value) {
        set(12, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.is_star</code>. 0: 星标 1: 没有星标
     */
    public Byte getIsStar() {
        return (Byte) get(12);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.remark</code>. 备注
     */
    public void setRemark(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.remark</code>. 备注
     */
    public String getRemark() {
        return (String) get(13);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(14, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(14);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(15, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(15);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.status</code>. 0: 新数据 1: 正常 2:被忽略
     */
    public void setStatus(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.status</code>. 0: 新数据 1: 正常 2:被忽略
     */
    public Integer getStatus() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.name</code>. 候选人姓名
     */
    public void setName(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.name</code>. 候选人姓名
     */
    public String getName() {
        return (String) get(17);
    }

    /**
     * Setter for <code>candidatedb.candidate_remark.user_id</code>. userdb.user_user.id 被推荐者的C端账号
     */
    public void setUserId(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_remark.user_id</code>. userdb.user_user.id 被推荐者的C端账号
     */
    public Integer getUserId() {
        return (Integer) get(18);
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
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, Integer, Integer, Integer, String, String, String, String, String, String, String, Timestamp, Byte, String, Timestamp, Timestamp, Integer, String, Integer> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, Integer, Integer, Integer, String, String, String, String, String, String, String, Timestamp, Byte, String, Timestamp, Timestamp, Integer, String, Integer> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CandidateRemark.CANDIDATE_REMARK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return CandidateRemark.CANDIDATE_REMARK.HRACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return CandidateRemark.CANDIDATE_REMARK.WXUSER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return CandidateRemark.CANDIDATE_REMARK.GENDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return CandidateRemark.CANDIDATE_REMARK.AGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return CandidateRemark.CANDIDATE_REMARK.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return CandidateRemark.CANDIDATE_REMARK.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return CandidateRemark.CANDIDATE_REMARK.CURRENT_COMPANY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return CandidateRemark.CANDIDATE_REMARK.CURRENT_POSITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return CandidateRemark.CANDIDATE_REMARK.EDUCATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return CandidateRemark.CANDIDATE_REMARK.DEGREE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return CandidateRemark.CANDIDATE_REMARK.GRADUATE_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field13() {
        return CandidateRemark.CANDIDATE_REMARK.IS_STAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return CandidateRemark.CANDIDATE_REMARK.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field15() {
        return CandidateRemark.CANDIDATE_REMARK.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field16() {
        return CandidateRemark.CANDIDATE_REMARK.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field17() {
        return CandidateRemark.CANDIDATE_REMARK.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return CandidateRemark.CANDIDATE_REMARK.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field19() {
        return CandidateRemark.CANDIDATE_REMARK.USER_ID;
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
        return getHraccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getWxuserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getGender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAge();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getCurrentCompany();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getCurrentPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getEducation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getDegree();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getGraduateAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value13() {
        return getIsStar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value15() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value16() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value17() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value19() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value2(Integer value) {
        setHraccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value3(Integer value) {
        setWxuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value4(Integer value) {
        setGender(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value5(String value) {
        setAge(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value6(String value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value7(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value8(String value) {
        setCurrentCompany(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value9(String value) {
        setCurrentPosition(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value10(String value) {
        setEducation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value11(String value) {
        setDegree(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value12(Timestamp value) {
        setGraduateAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value13(Byte value) {
        setIsStar(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value14(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value15(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value16(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value17(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value18(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord value19(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateRemarkRecord values(Integer value1, Integer value2, Integer value3, Integer value4, String value5, String value6, String value7, String value8, String value9, String value10, String value11, Timestamp value12, Byte value13, String value14, Timestamp value15, Timestamp value16, Integer value17, String value18, Integer value19) {
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
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CandidateRemarkRecord
     */
    public CandidateRemarkRecord() {
        super(CandidateRemark.CANDIDATE_REMARK);
    }

    /**
     * Create a detached, initialised CandidateRemarkRecord
     */
    public CandidateRemarkRecord(Integer id, Integer hraccountId, Integer wxuserId, Integer gender, String age, String mobile, String email, String currentCompany, String currentPosition, String education, String degree, Timestamp graduateAt, Byte isStar, String remark, Timestamp createTime, Timestamp updateTime, Integer status, String name, Integer userId) {
        super(CandidateRemark.CANDIDATE_REMARK);

        set(0, id);
        set(1, hraccountId);
        set(2, wxuserId);
        set(3, gender);
        set(4, age);
        set(5, mobile);
        set(6, email);
        set(7, currentCompany);
        set(8, currentPosition);
        set(9, education);
        set(10, degree);
        set(11, graduateAt);
        set(12, isStar);
        set(13, remark);
        set(14, createTime);
        set(15, updateTime);
        set(16, status);
        set(17, name);
        set(18, userId);
    }
}
