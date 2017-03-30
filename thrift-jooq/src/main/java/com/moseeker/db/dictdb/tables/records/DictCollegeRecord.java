/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.dictdb.tables.records;


import com.moseeker.db.dictdb.tables.DictCollege;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;

import javax.annotation.Generated;


/**
 * 学校字典表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictCollegeRecord extends UpdatableRecordImpl<DictCollegeRecord> implements Record4<UInteger, String, UInteger, String> {

	private static final long serialVersionUID = -1776453922;

	/**
	 * Setter for <code>dictdb.dict_college.code</code>. 字典code
	 */
	public void setCode(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dictdb.dict_college.code</code>. 字典code
	 */
	public UInteger getCode() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>dictdb.dict_college.name</code>. 字典name
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dictdb.dict_college.name</code>. 字典name
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>dictdb.dict_college.province</code>. 院校所在地
	 */
	public void setProvince(UInteger value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dictdb.dict_college.province</code>. 院校所在地
	 */
	public UInteger getProvince() {
		return (UInteger) getValue(2);
	}

	/**
	 * Setter for <code>dictdb.dict_college.logo</code>. 院校logo
	 */
	public void setLogo(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>dictdb.dict_college.logo</code>. 院校logo
	 */
	public String getLogo() {
		return (String) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, String, UInteger, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, String, UInteger, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return DictCollege.DICT_COLLEGE.CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return DictCollege.DICT_COLLEGE.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field3() {
		return DictCollege.DICT_COLLEGE.PROVINCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return DictCollege.DICT_COLLEGE.LOGO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value3() {
		return getProvince();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getLogo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictCollegeRecord value1(UInteger value) {
		setCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictCollegeRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictCollegeRecord value3(UInteger value) {
		setProvince(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictCollegeRecord value4(String value) {
		setLogo(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictCollegeRecord values(UInteger value1, String value2, UInteger value3, String value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DictCollegeRecord
	 */
	public DictCollegeRecord() {
		super(DictCollege.DICT_COLLEGE);
	}

	/**
	 * Create a detached, initialised DictCollegeRecord
	 */
	public DictCollegeRecord(UInteger code, String name, UInteger province, String logo) {
		super(DictCollege.DICT_COLLEGE);

		setValue(0, code);
		setValue(1, name);
		setValue(2, province);
		setValue(3, logo);
	}
}
