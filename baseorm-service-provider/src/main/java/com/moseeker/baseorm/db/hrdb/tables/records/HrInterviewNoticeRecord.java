/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewNotice;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 面试提醒表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewNoticeRecord extends UpdatableRecordImpl<HrInterviewNoticeRecord> implements Record12<Integer, Integer, Integer, Integer, Integer, Timestamp, Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1186347885;

    /**
     * Setter for <code>hrdb.hr_interview_notice.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.interview_id</code>. 面试的id
     */
    public void setInterviewId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.interview_id</code>. 面试的id
     */
    public Integer getInterviewId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.receive_id</code>. 接收人id
     */
    public void setReceiveId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.receive_id</code>. 接收人id
     */
    public Integer getReceiveId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.receive_type</code>. 接收人类型0 面试官 1求职者
     */
    public void setReceiveType(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.receive_type</code>. 接收人类型0 面试官 1求职者
     */
    public Integer getReceiveType() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.notice_type</code>. 消息类型 0 是消息模板 1是邮件 2是短信
     */
    public void setNoticeType(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.notice_type</code>. 消息类型 0 是消息模板 1是邮件 2是短信
     */
    public Integer getNoticeType() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.notice_time</code>. 提醒时间
     */
    public void setNoticeTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.notice_time</code>. 提醒时间
     */
    public Timestamp getNoticeTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.send</code>. 是否已经发送 0 未发送 1已发送
     */
    public void setSend(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.send</code>. 是否已经发送 0 未发送 1已发送
     */
    public Integer getSend() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.fail_count</code>. 发送失败次数
     */
    public void setFailCount(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.fail_count</code>. 发送失败次数
     */
    public Integer getFailCount() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.notice_scene</code>. 通知场景 1: 面试通知，2：取消面试通知
     */
    public void setNoticeScene(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.notice_scene</code>. 通知场景 1: 面试通知，2：取消面试通知
     */
    public Integer getNoticeScene() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public void setDisable(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public Integer getDisable() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_interview_notice.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_notice.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(11);
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
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Integer, Integer, Integer, Integer, Integer, Timestamp, Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Integer, Integer, Integer, Integer, Integer, Timestamp, Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.INTERVIEW_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.RECEIVE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.RECEIVE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.NOTICE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.NOTICE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.SEND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.FAIL_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.NOTICE_SCENE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field12() {
        return HrInterviewNotice.HR_INTERVIEW_NOTICE.UPDATE_TIME;
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
        return getInterviewId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getReceiveId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getReceiveType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getNoticeType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getNoticeTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getSend();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getFailCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getNoticeScene();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value12() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value2(Integer value) {
        setInterviewId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value3(Integer value) {
        setReceiveId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value4(Integer value) {
        setReceiveType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value5(Integer value) {
        setNoticeType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value6(Timestamp value) {
        setNoticeTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value7(Integer value) {
        setSend(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value8(Integer value) {
        setFailCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value9(Integer value) {
        setNoticeScene(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value10(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value11(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord value12(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNoticeRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Timestamp value6, Integer value7, Integer value8, Integer value9, Integer value10, Timestamp value11, Timestamp value12) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrInterviewNoticeRecord
     */
    public HrInterviewNoticeRecord() {
        super(HrInterviewNotice.HR_INTERVIEW_NOTICE);
    }

    /**
     * Create a detached, initialised HrInterviewNoticeRecord
     */
    public HrInterviewNoticeRecord(Integer id, Integer interviewId, Integer receiveId, Integer receiveType, Integer noticeType, Timestamp noticeTime, Integer send, Integer failCount, Integer noticeScene, Integer disable, Timestamp createTime, Timestamp updateTime) {
        super(HrInterviewNotice.HR_INTERVIEW_NOTICE);

        set(0, id);
        set(1, interviewId);
        set(2, receiveId);
        set(3, receiveType);
        set(4, noticeType);
        set(5, noticeTime);
        set(6, send);
        set(7, failCount);
        set(8, noticeScene);
        set(9, disable);
        set(10, createTime);
        set(11, updateTime);
    }
}