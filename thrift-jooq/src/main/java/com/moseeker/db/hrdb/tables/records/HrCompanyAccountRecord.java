/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrCompanyAccount;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 账号公司关联记录
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyAccountRecord extends UpdatableRecordImpl<HrCompanyAccountRecord> implements Record2<Integer, Integer> {

	private static final long serialVersionUID = -227341934;

	/**
	 * Setter for <code>hrdb.hr_company_account.company_id</code>. hr_company.id
	 */
	public void setCompanyId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_company_account.company_id</code>. hr_company.id
	 */
	public Integer getCompanyId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_company_account.account_id</code>. user_hr_account.id
	 */
	public void setAccountId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_company_account.account_id</code>. user_hr_account.id
	 */
	public Integer getAccountId() {
		return (Integer) getValue(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrCompanyAccount.HR_COMPANY_ACCOUNT.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getAccountId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCompanyAccountRecord value1(Integer value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCompanyAccountRecord value2(Integer value) {
		setAccountId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCompanyAccountRecord values(Integer value1, Integer value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrCompanyAccountRecord
	 */
	public HrCompanyAccountRecord() {
		super(HrCompanyAccount.HR_COMPANY_ACCOUNT);
	}

	/**
	 * Create a detached, initialised HrCompanyAccountRecord
	 */
	public HrCompanyAccountRecord(Integer companyId, Integer accountId) {
		super(HrCompanyAccount.HR_COMPANY_ACCOUNT);

		setValue(0, companyId);
		setValue(1, accountId);
	}
}
