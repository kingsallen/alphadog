/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.dictdb.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import com.moseeker.db.dictdb.tables.DictMajor;


/**
 * 专业字典表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictMajorRecord extends UpdatableRecordImpl<DictMajorRecord> implements Record3<String, String, Byte> {

	private static final long serialVersionUID = 664011378;

	/**
	 * Setter for <code>dictdb.dict_major.code</code>. 字典code
	 */
	public void setCode(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dictdb.dict_major.code</code>. 字典code
	 */
	public String getCode() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>dictdb.dict_major.name</code>. 字典name
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dictdb.dict_major.name</code>. 字典name
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>dictdb.dict_major.level</code>. 字典level
	 */
	public void setLevel(Byte value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dictdb.dict_major.level</code>. 字典level
	 */
	public Byte getLevel() {
		return (Byte) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, String, Byte> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, String, Byte> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return DictMajor.DICT_MAJOR.CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return DictMajor.DICT_MAJOR.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field3() {
		return DictMajor.DICT_MAJOR.LEVEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
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
	public Byte value3() {
		return getLevel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictMajorRecord value1(String value) {
		setCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictMajorRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictMajorRecord value3(Byte value) {
		setLevel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictMajorRecord values(String value1, String value2, Byte value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DictMajorRecord
	 */
	public DictMajorRecord() {
		super(DictMajor.DICT_MAJOR);
	}

	/**
	 * Create a detached, initialised DictMajorRecord
	 */
	public DictMajorRecord(String code, String name, Byte level) {
		super(DictMajor.DICT_MAJOR);

		setValue(0, code);
		setValue(1, name);
		setValue(2, level);
	}
}
