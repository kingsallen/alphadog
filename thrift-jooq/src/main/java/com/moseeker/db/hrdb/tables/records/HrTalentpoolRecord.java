/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrTalentpool;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 人才库
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTalentpoolRecord extends UpdatableRecordImpl<HrTalentpoolRecord> implements Record6<Integer, Integer, Integer, Timestamp, Timestamp, Integer> {

	private static final long serialVersionUID = -697392386;

	/**
	 * Setter for <code>hrdb.hr_talentpool.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_talentpool.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_talentpool.hr_account_id</code>. 创建人id(user_hr_account.id)
	 */
	public void setHrAccountId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_talentpool.hr_account_id</code>. 创建人id(user_hr_account.id)
	 */
	public Integer getHrAccountId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_talentpool.applier_id</code>. 候选人id（user_user.id）
	 */
	public void setApplierId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_talentpool.applier_id</code>. 候选人id（user_user.id）
	 */
	public Integer getApplierId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_talentpool.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_talentpool.create_time</code>.
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_talentpool.update_time</code>.
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_talentpool.update_time</code>.
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_talentpool.status</code>. 状态(0：正常，1：删除)
	 */
	public void setStatus(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_talentpool.status</code>. 状态(0：正常，1：删除)
	 */
	public Integer getStatus() {
		return (Integer) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Integer, Integer, Timestamp, Timestamp, Integer> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Integer, Integer, Timestamp, Timestamp, Integer> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrTalentpool.HR_TALENTPOOL.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrTalentpool.HR_TALENTPOOL.HR_ACCOUNT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return HrTalentpool.HR_TALENTPOOL.APPLIER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return HrTalentpool.HR_TALENTPOOL.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return HrTalentpool.HR_TALENTPOOL.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return HrTalentpool.HR_TALENTPOOL.STATUS;
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
		return getHrAccountId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getApplierId();
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
	public Timestamp value5() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord value2(Integer value) {
		setHrAccountId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord value3(Integer value) {
		setApplierId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord value4(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord value5(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord value6(Integer value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTalentpoolRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Timestamp value5, Integer value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrTalentpoolRecord
	 */
	public HrTalentpoolRecord() {
		super(HrTalentpool.HR_TALENTPOOL);
	}

	/**
	 * Create a detached, initialised HrTalentpoolRecord
	 */
	public HrTalentpoolRecord(Integer id, Integer hrAccountId, Integer applierId, Timestamp createTime, Timestamp updateTime, Integer status) {
		super(HrTalentpool.HR_TALENTPOOL);

		setValue(0, id);
		setValue(1, hrAccountId);
		setValue(2, applierId);
		setValue(3, createTime);
		setValue(4, updateTime);
		setValue(5, status);
	}
}
