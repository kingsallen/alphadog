/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewProcessRound;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 面试阶段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewProcessRoundRecord extends UpdatableRecordImpl<HrInterviewProcessRoundRecord> implements Record8<Integer, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -459438228;

    /**
     * Setter for <code>hrdb.hr_interview_process_round.id</code>. 序列ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.id</code>. 序列ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.round_name</code>. 面试阶段名称
     */
    public void setRoundName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.round_name</code>. 面试阶段名称
     */
    public String getRoundName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.interview_process_id</code>. 面试流程ID
     */
    public void setInterviewProcessId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.interview_process_id</code>. 面试流程ID
     */
    public Integer getInterviewProcessId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.disabled</code>. 状态： 0 有效  1 无效 
     */
    public void setDisabled(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.disabled</code>. 状态： 0 有效  1 无效 
     */
    public Integer getDisabled() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.deleted</code>. 逻辑删除 1 删除 0 未删除
     */
    public void setDeleted(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.deleted</code>. 逻辑删除 1 删除 0 未删除
     */
    public Integer getDeleted() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.round_order</code>. 排序
     */
    public void setRoundOrder(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.round_order</code>. 排序
     */
    public Integer getRoundOrder() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process_round.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process_round.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.ROUND_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.INTERVIEW_PROCESS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.DISABLED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.DELETED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.ROUND_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND.UPDATE_TIME;
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
    public String value2() {
        return getRoundName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getInterviewProcessId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getDisabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getDeleted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getRoundOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value2(String value) {
        setRoundName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value3(Integer value) {
        setInterviewProcessId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value4(Integer value) {
        setDisabled(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value5(Integer value) {
        setDeleted(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value6(Integer value) {
        setRoundOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRoundRecord values(Integer value1, String value2, Integer value3, Integer value4, Integer value5, Integer value6, Timestamp value7, Timestamp value8) {
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
     * Create a detached HrInterviewProcessRoundRecord
     */
    public HrInterviewProcessRoundRecord() {
        super(HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND);
    }

    /**
     * Create a detached, initialised HrInterviewProcessRoundRecord
     */
    public HrInterviewProcessRoundRecord(Integer id, String roundName, Integer interviewProcessId, Integer disabled, Integer deleted, Integer roundOrder, Timestamp createTime, Timestamp updateTime) {
        super(HrInterviewProcessRound.HR_INTERVIEW_PROCESS_ROUND);

        set(0, id);
        set(1, roundName);
        set(2, interviewProcessId);
        set(3, disabled);
        set(4, deleted);
        set(5, roundOrder);
        set(6, createTime);
        set(7, updateTime);
    }
}
