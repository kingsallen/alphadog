/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.candidatedb.tables.records;


import com.moseeker.db.candidatedb.tables.CandidatePosition;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 候选人表相关职位表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidatePositionRecord extends UpdatableRecordImpl<CandidatePositionRecord> implements Record7<Integer, Timestamp, Integer, Byte, Integer, Integer, Byte> {

	private static final long serialVersionUID = -1743380863;

	/**
	 * Setter for <code>candidateDB.candidate_position.position_id</code>. hr_position.id
	 */
	public void setPositionId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.position_id</code>. hr_position.id
	 */
	public Integer getPositionId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>candidateDB.candidate_position.update_time</code>. 修改时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.update_time</code>. 修改时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(1);
	}

	/**
	 * Setter for <code>candidateDB.candidate_position.wxuser_id</code>. wx_group_user.id
	 */
	public void setWxuserId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.wxuser_id</code>. wx_group_user.id
	 */
	public Integer getWxuserId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>candidateDB.candidate_position.is_interested</code>. 是否感兴趣
	 */
	public void setIsInterested(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.is_interested</code>. 是否感兴趣
	 */
	public Byte getIsInterested() {
		return (Byte) getValue(3);
	}

	/**
	 * Setter for <code>candidateDB.candidate_position.candidate_company_id</code>. candidate_company.id
	 */
	public void setCandidateCompanyId(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.candidate_company_id</code>. candidate_company.id
	 */
	public Integer getCandidateCompanyId() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>candidateDB.candidate_position.view_number</code>. 查看次数
	 */
	public void setViewNumber(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.view_number</code>. 查看次数
	 */
	public Integer getViewNumber() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>candidateDB.candidate_position.shared_from_employee</code>.
	 */
	public void setSharedFromEmployee(Byte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>candidateDB.candidate_position.shared_from_employee</code>.
	 */
	public Byte getSharedFromEmployee() {
		return (Byte) getValue(6);
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
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, Timestamp, Integer, Byte, Integer, Integer, Byte> fieldsRow() {
		return (Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, Timestamp, Integer, Byte, Integer, Integer, Byte> valuesRow() {
		return (Row7) super.valuesRow();
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
	public CandidatePositionRecord values(Integer value1, Timestamp value2, Integer value3, Byte value4, Integer value5, Integer value6, Byte value7) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
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
	public CandidatePositionRecord(Integer positionId, Timestamp updateTime, Integer wxuserId, Byte isInterested, Integer candidateCompanyId, Integer viewNumber, Byte sharedFromEmployee) {
		super(CandidatePosition.CANDIDATE_POSITION);

		setValue(0, positionId);
		setValue(1, updateTime);
		setValue(2, wxuserId);
		setValue(3, isInterested);
		setValue(4, candidateCompanyId);
		setValue(5, viewNumber);
		setValue(6, sharedFromEmployee);
	}
}
