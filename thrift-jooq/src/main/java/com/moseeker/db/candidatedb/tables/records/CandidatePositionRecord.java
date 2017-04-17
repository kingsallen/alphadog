/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.candidatedb.tables.records;


import com.moseeker.db.candidatedb.tables.CandidatePosition;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 候选人表相关职位表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidatePositionRecord extends UpdatableRecordImpl<CandidatePositionRecord> implements Record8<Integer, Timestamp, Integer, Byte, Integer, Integer, Byte, Integer> {

    private static final long serialVersionUID = -301609986;

    /**
     * Setter for <code>candidatedb.candidate_position.position_id</code>. hr_position.id
     */
    public void setPositionId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.position_id</code>. hr_position.id
     */
    public Integer getPositionId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.wxuser_id</code>. user_wx_user.id，表示候选人代表的微信账号。已经废弃。微信账号由C端账号代替，请参考user_id
     */
    public void setWxuserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.wxuser_id</code>. user_wx_user.id，表示候选人代表的微信账号。已经废弃。微信账号由C端账号代替，请参考user_id
     */
    public Integer getWxuserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.is_interested</code>. 是否感兴趣
     */
    public void setIsInterested(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.is_interested</code>. 是否感兴趣
     */
    public Byte getIsInterested() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.candidate_company_id</code>. candidate_company.id
     */
    public void setCandidateCompanyId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.candidate_company_id</code>. candidate_company.id
     */
    public Integer getCandidateCompanyId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.view_number</code>. 查看次数
     */
    public void setViewNumber(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.view_number</code>. 查看次数
     */
    public Integer getViewNumber() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.shared_from_employee</code>.
     */
    public void setSharedFromEmployee(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.shared_from_employee</code>.
     */
    public Byte getSharedFromEmployee() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>candidatedb.candidate_position.user_id</code>. userdb.user_user.id 候选人代表的C端用户
     */
    public void setUserId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position.user_id</code>. userdb.user_user.id 候选人代表的C端用户
     */
    public Integer getUserId() {
        return (Integer) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Timestamp, Integer, Byte, Integer, Integer, Byte, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Timestamp, Integer, Byte, Integer, Integer, Byte, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CandidatePosition.CANDIDATE_POSITION.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return CandidatePosition.CANDIDATE_POSITION.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return CandidatePosition.CANDIDATE_POSITION.WXUSER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return CandidatePosition.CANDIDATE_POSITION.CANDIDATE_COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return CandidatePosition.CANDIDATE_POSITION.SHARED_FROM_EMPLOYEE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return CandidatePosition.CANDIDATE_POSITION.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value2() {
        return getUpdateTime();
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
    public Byte value4() {
        return getIsInterested();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCandidateCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getViewNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getSharedFromEmployee();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value1(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value2(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value3(Integer value) {
        setWxuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value4(Byte value) {
        setIsInterested(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value5(Integer value) {
        setCandidateCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value6(Integer value) {
        setViewNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value7(Byte value) {
        setSharedFromEmployee(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord value8(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionRecord values(Integer value1, Timestamp value2, Integer value3, Byte value4, Integer value5, Integer value6, Byte value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CandidatePositionRecord
     */
    public CandidatePositionRecord() {
        super(CandidatePosition.CANDIDATE_POSITION);
    }

    /**
     * Create a detached, initialised CandidatePositionRecord
     */
    public CandidatePositionRecord(Integer positionId, Timestamp updateTime, Integer wxuserId, Byte isInterested, Integer candidateCompanyId, Integer viewNumber, Byte sharedFromEmployee, Integer userId) {
        super(CandidatePosition.CANDIDATE_POSITION);

        set(0, positionId);
        set(1, updateTime);
        set(2, wxuserId);
        set(3, isInterested);
        set(4, candidateCompanyId);
        set(5, viewNumber);
        set(6, sharedFromEmployee);
        set(7, userId);
    }
}
