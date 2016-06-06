/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb.tables.records;


import com.moseeker.db.userdb.tables.CandidateVJobPositionRecom;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.TableRecordImpl;


/**
 * VIEW
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateVJobPositionRecomRecord extends TableRecordImpl<CandidateVJobPositionRecomRecord> implements Record5<Long, Long, Long, Timestamp, String> {

	private static final long serialVersionUID = 1310193378;

	/**
	 * Setter for <code>userdb.candidate_v_job_position_recom.position_id</code>. position.id 分享职位ID
	 */
	public void setPositionId(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>userdb.candidate_v_job_position_recom.position_id</code>. position.id 分享职位ID
	 */
	public Long getPositionId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>userdb.candidate_v_job_position_recom.recom_id</code>. wx_group_user.id 分享用户微信ID
	 */
	public void setRecomId(Long value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>userdb.candidate_v_job_position_recom.recom_id</code>. wx_group_user.id 分享用户微信ID
	 */
	public Long getRecomId() {
		return (Long) getValue(1);
	}

	/**
	 * Setter for <code>userdb.candidate_v_job_position_recom.presentee_id</code>. 被动求职者,浏览者的微信ID，wx_group_user.id
	 */
	public void setPresenteeId(Long value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>userdb.candidate_v_job_position_recom.presentee_id</code>. 被动求职者,浏览者的微信ID，wx_group_user.id
	 */
	public Long getPresenteeId() {
		return (Long) getValue(2);
	}

	/**
	 * Setter for <code>userdb.candidate_v_job_position_recom.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>userdb.candidate_v_job_position_recom.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>userdb.candidate_v_job_position_recom.nickname</code>. 用户昵称
	 */
	public void setNickname(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>userdb.candidate_v_job_position_recom.nickname</code>. 用户昵称
	 */
	public String getNickname() {
		return (String) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, Long, Long, Timestamp, String> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Long, Long, Long, Timestamp, String> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM.POSITION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM.RECOM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM.PRESENTEE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM.NICKNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getPositionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value2() {
		return getRecomId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getPresenteeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getNickname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateVJobPositionRecomRecord value1(Long value) {
		setPositionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateVJobPositionRecomRecord value2(Long value) {
		setRecomId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateVJobPositionRecomRecord value3(Long value) {
		setPresenteeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateVJobPositionRecomRecord value4(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateVJobPositionRecomRecord value5(String value) {
		setNickname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CandidateVJobPositionRecomRecord values(Long value1, Long value2, Long value3, Timestamp value4, String value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CandidateVJobPositionRecomRecord
	 */
	public CandidateVJobPositionRecomRecord() {
		super(CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM);
	}

	/**
	 * Create a detached, initialised CandidateVJobPositionRecomRecord
	 */
	public CandidateVJobPositionRecomRecord(Long positionId, Long recomId, Long presenteeId, Timestamp createTime, String nickname) {
		super(CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM);

		setValue(0, positionId);
		setValue(1, recomId);
		setValue(2, presenteeId);
		setValue(3, createTime);
		setValue(4, nickname);
	}
}