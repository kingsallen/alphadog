/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb.tables.records;


import com.moseeker.baseorm.db.candidatedb.tables.CandidatePositionShareRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户分享职位访问记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidatePositionShareRecordRecord extends UpdatableRecordImpl<CandidatePositionShareRecordRecord> implements Record13<Integer, Long, Long, Long, Integer, String, Long, Byte, Timestamp, Timestamp, Long, Byte, Integer> {

    private static final long serialVersionUID = -1584700141;

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.wechat_id</code>. 所属公众号
     */
    public void setWechatId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.wechat_id</code>. 所属公众号
     */
    public Long getWechatId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.position_id</code>. position.id 分享职位ID
     */
    public void setPositionId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.position_id</code>. position.id 分享职位ID
     */
    public Long getPositionId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.recom_id</code>. userdb.user_wx_user.id 分享用户微信ID。现在已经废弃，请参考recom_user_id字段
     */
    public void setRecomId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.recom_id</code>. userdb.user_wx_user.id 分享用户微信ID。现在已经废弃，请参考recom_user_id字段
     */
    public Long getRecomId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.recom_user_id</code>. userdb.user_user.id 转发者的C端账号编号
     */
    public void setRecomUserId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.recom_user_id</code>. userdb.user_user.id 转发者的C端账号编号
     */
    public Integer getRecomUserId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.viewer_id</code>. userdb.user_wx_viewer.id 浏览用户ID
     */
    public void setViewerId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.viewer_id</code>. userdb.user_wx_viewer.id 浏览用户ID
     */
    public String getViewerId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.viewer_ip</code>. 浏览用户IP
     */
    public void setViewerIp(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.viewer_ip</code>. 浏览用户IP
     */
    public Long getViewerIp() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.source</code>. 来源0：企业后台1：聚合平台
     */
    public void setSource(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.source</code>. 来源0：企业后台1：聚合平台
     */
    public Byte getSource() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.presentee_id</code>. 被动求职者,浏览者的微信ID，userdb.user_wx_user.id。现在已经废弃，请参考presentee_user_id
     */
    public void setPresenteeId(Long value) {
        set(10, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.presentee_id</code>. 被动求职者,浏览者的微信ID，userdb.user_wx_user.id。现在已经废弃，请参考presentee_user_id
     */
    public Long getPresenteeId() {
        return (Long) get(10);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.click_from</code>. 来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage) 3
     */
    public void setClickFrom(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.click_from</code>. 来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage) 3
     */
    public Byte getClickFrom() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>candidatedb.candidate_position_share_record.presentee_user_id</code>. userdb.user_user.id 浏览者的C端账号编号
     */
    public void setPresenteeUserId(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>candidatedb.candidate_position_share_record.presentee_user_id</code>. userdb.user_user.id 浏览者的C端账号编号
     */
    public Integer getPresenteeUserId() {
        return (Integer) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Long, Long, Long, Integer, String, Long, Byte, Timestamp, Timestamp, Long, Byte, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Long, Long, Long, Integer, String, Long, Byte, Timestamp, Timestamp, Long, Byte, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.WECHAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.VIEWER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.VIEWER_IP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field11() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.PRESENTEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field12() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.CLICK_FROM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.PRESENTEE_USER_ID;
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
    public Long value2() {
        return getWechatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getRecomId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getRecomUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getViewerId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getViewerIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value11() {
        return getPresenteeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value12() {
        return getClickFrom();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getPresenteeUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value2(Long value) {
        setWechatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value3(Long value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value4(Long value) {
        setRecomId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value5(Integer value) {
        setRecomUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value6(String value) {
        setViewerId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value7(Long value) {
        setViewerIp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value8(Byte value) {
        setSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value9(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value10(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value11(Long value) {
        setPresenteeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value12(Byte value) {
        setClickFrom(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord value13(Integer value) {
        setPresenteeUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePositionShareRecordRecord values(Integer value1, Long value2, Long value3, Long value4, Integer value5, String value6, Long value7, Byte value8, Timestamp value9, Timestamp value10, Long value11, Byte value12, Integer value13) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CandidatePositionShareRecordRecord
     */
    public CandidatePositionShareRecordRecord() {
        super(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD);
    }

    /**
     * Create a detached, initialised CandidatePositionShareRecordRecord
     */
    public CandidatePositionShareRecordRecord(Integer id, Long wechatId, Long positionId, Long recomId, Integer recomUserId, String viewerId, Long viewerIp, Byte source, Timestamp createTime, Timestamp updateTime, Long presenteeId, Byte clickFrom, Integer presenteeUserId) {
        super(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD);

        set(0, id);
        set(1, wechatId);
        set(2, positionId);
        set(3, recomId);
        set(4, recomUserId);
        set(5, viewerId);
        set(6, viewerIp);
        set(7, source);
        set(8, createTime);
        set(9, updateTime);
        set(10, presenteeId);
        set(11, clickFrom);
        set(12, presenteeUserId);
    }
}
