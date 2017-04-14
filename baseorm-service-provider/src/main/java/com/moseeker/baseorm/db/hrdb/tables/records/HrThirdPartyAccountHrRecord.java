/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 第三方账号和hr表关联表，账号分配表，取消分配将status置为0，重新分配不修改记录而是新加分配记录
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccountHrRecord extends UpdatableRecordImpl<HrThirdPartyAccountHrRecord> implements Record6<Integer, Integer, Integer, Byte, Timestamp, Timestamp> {

	private static final long serialVersionUID = 1967171198;

	/**
	 * Setter for <code>hrdb.hr_third_party_account_hr.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_third_party_account_hr.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_third_party_account_hr.third_party_account_id</code>. 第三方账号ID
	 */
	public void setThirdPartyAccountId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_third_party_account_hr.third_party_account_id</code>. 第三方账号ID
	 */
	public Integer getThirdPartyAccountId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_third_party_account_hr.hr_account_id</code>. hr账号ID
	 */
	public void setHrAccountId(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_third_party_account_hr.hr_account_id</code>. hr账号ID
	 */
	public Integer getHrAccountId() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_third_party_account_hr.status</code>. 绑定状态：0：取消分配，1：已分配
	 */
	public void setStatus(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_third_party_account_hr.status</code>. 绑定状态：0：取消分配，1：已分配
	 */
	public Byte getStatus() {
		return (Byte) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_third_party_account_hr.create_time</code>. 分配账号的时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_third_party_account_hr.create_time</code>. 分配账号的时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_third_party_account_hr.update_time</code>. 取消分配账号的时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_third_party_account_hr.update_time</code>. 取消分配账号的时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(5);
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
	public Row6<Integer, Integer, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Integer, Integer, Byte, Timestamp, Timestamp> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field4() {
		return HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field6() {
		return HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.UPDATE_TIME;
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
		return getThirdPartyAccountId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getHrAccountId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value4() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value6() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord value2(Integer value) {
		setThirdPartyAccountId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord value3(Integer value) {
		setHrAccountId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord value4(Byte value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord value5(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord value6(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHrRecord values(Integer value1, Integer value2, Integer value3, Byte value4, Timestamp value5, Timestamp value6) {
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
	 * Create a detached HrThirdPartyAccountHrRecord
	 */
	public HrThirdPartyAccountHrRecord() {
		super(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR);
	}

	/**
	 * Create a detached, initialised HrThirdPartyAccountHrRecord
	 */
	public HrThirdPartyAccountHrRecord(Integer id, Integer thirdPartyAccountId, Integer hrAccountId, Byte status, Timestamp createTime, Timestamp updateTime) {
		super(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR);

		setValue(0, id);
		setValue(1, thirdPartyAccountId);
		setValue(2, hrAccountId);
		setValue(3, status);
		setValue(4, createTime);
		setValue(5, updateTime);
	}
}
