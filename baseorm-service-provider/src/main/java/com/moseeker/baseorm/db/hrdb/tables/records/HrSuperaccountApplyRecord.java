/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrSuperaccountApplyRecord extends UpdatableRecordImpl<HrSuperaccountApplyRecord> implements Record11<Integer, Integer, byte[], Timestamp, Timestamp, Integer, Integer, String, String, Timestamp, Integer> {

	private static final long serialVersionUID = 132528435;

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.company_id</code>. hr_company.id
	 */
	public void setCompanyId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.company_id</code>. hr_company.id
	 */
	public Integer getCompanyId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.licence</code>. 营业执照
	 */
	public void setLicence(byte[] value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.licence</code>. 营业执照
	 */
	public byte[] getLicence() {
		return (byte[]) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.update_time</code>. 修改时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.update_time</code>. 修改时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.operate</code>. config_sys_administrator.id
	 */
	public void setOperate(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.operate</code>. config_sys_administrator.id
	 */
	public Integer getOperate() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.status</code>. 申请状态 0表示已经通过，1表示未处理，2表示未通过
	 */
	public void setStatus(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.status</code>. 申请状态 0表示已经通过，1表示未处理，2表示未通过
	 */
	public Integer getStatus() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.message</code>. 审核留言
	 */
	public void setMessage(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.message</code>. 审核留言
	 */
	public String getMessage() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.child_company_id</code>. 合并的其他公司的编号：[1,2,3]
	 */
	public void setChildCompanyId(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.child_company_id</code>. 合并的其他公司的编号：[1,2,3]
	 */
	public String getChildCompanyId() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.migrate_time</code>. 迁移时间
	 */
	public void setMigrateTime(Timestamp value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.migrate_time</code>. 迁移时间
	 */
	public Timestamp getMigrateTime() {
		return (Timestamp) getValue(9);
	}

	/**
	 * Setter for <code>hrdb.hr_superaccount_apply.account_limit</code>. 子账号数量限制
	 */
	public void setAccountLimit(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>hrdb.hr_superaccount_apply.account_limit</code>. 子账号数量限制
	 */
	public Integer getAccountLimit() {
		return (Integer) getValue(10);
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
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Integer, byte[], Timestamp, Timestamp, Integer, Integer, String, String, Timestamp, Integer> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Integer, byte[], Timestamp, Timestamp, Integer, Integer, String, String, Timestamp, Integer> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<byte[]> field3() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.LICENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.OPERATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.MESSAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.CHILD_COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field10() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.MIGRATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.ACCOUNT_LIMIT;
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
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value3() {
		return getLicence();
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
		return getOperate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getMessage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getChildCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value10() {
		return getMigrateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getAccountLimit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value2(Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value3(byte[] value) {
		setLicence(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value4(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value5(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value6(Integer value) {
		setOperate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value7(Integer value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value8(String value) {
		setMessage(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value9(String value) {
		setChildCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value10(Timestamp value) {
		setMigrateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord value11(Integer value) {
		setAccountLimit(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSuperaccountApplyRecord values(Integer value1, Integer value2, byte[] value3, Timestamp value4, Timestamp value5, Integer value6, Integer value7, String value8, String value9, Timestamp value10, Integer value11) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrSuperaccountApplyRecord
	 */
	public HrSuperaccountApplyRecord() {
		super(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY);
	}

	/**
	 * Create a detached, initialised HrSuperaccountApplyRecord
	 */
	public HrSuperaccountApplyRecord(Integer id, Integer companyId, byte[] licence, Timestamp createTime, Timestamp updateTime, Integer operate, Integer status, String message, String childCompanyId, Timestamp migrateTime, Integer accountLimit) {
		super(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY);

		setValue(0, id);
		setValue(1, companyId);
		setValue(2, licence);
		setValue(3, createTime);
		setValue(4, updateTime);
		setValue(5, operate);
		setValue(6, status);
		setValue(7, message);
		setValue(8, childCompanyId);
		setValue(9, migrateTime);
		setValue(10, accountLimit);
	}
}
