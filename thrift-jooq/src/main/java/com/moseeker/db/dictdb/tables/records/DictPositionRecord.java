/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.dictdb.tables.records;


import com.moseeker.db.dictdb.tables.DictPosition;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;

import javax.annotation.Generated;


/**
 * 职能分类字典表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictPositionRecord extends UpdatableRecordImpl<DictPositionRecord> implements Record4<UInteger, String, UInteger, Byte> {

	private static final long serialVersionUID = -1300643251;

	/**
	 * Setter for <code>dictdb.dict_position.code</code>. 字典code
	 */
	public void setCode(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dictdb.dict_position.code</code>. 字典code
	 */
	public UInteger getCode() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>dictdb.dict_position.name</code>. 字典name
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dictdb.dict_position.name</code>. 字典name
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>dictdb.dict_position.parent</code>. 父编码
	 */
	public void setParent(UInteger value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dictdb.dict_position.parent</code>. 父编码
	 */
	public UInteger getParent() {
		return (UInteger) getValue(2);
	}

	/**
	 * Setter for <code>dictdb.dict_position.level</code>. 字典level
	 */
	public void setLevel(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>dictdb.dict_position.level</code>. 字典level
	 */
	public Byte getLevel() {
		return (Byte) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, String, UInteger, Byte> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, String, UInteger, Byte> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return DictPosition.DICT_POSITION.CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return DictPosition.DICT_POSITION.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field3() {
		return DictPosition.DICT_POSITION.PARENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field4() {
		return DictPosition.DICT_POSITION.LEVEL;
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
		return getParent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value4() {
		return getLevel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictPositionRecord value1(UInteger value) {
		setCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictPositionRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictPositionRecord value3(UInteger value) {
		setParent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictPositionRecord value4(Byte value) {
		setLevel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictPositionRecord values(UInteger value1, String value2, UInteger value3, Byte value4) {
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
	 * Create a detached DictPositionRecord
	 */
	public DictPositionRecord() {
		super(DictPosition.DICT_POSITION);
	}

	/**
	 * Create a detached, initialised DictPositionRecord
	 */
	public DictPositionRecord(UInteger code, String name, UInteger parent, Byte level) {
		super(DictPosition.DICT_POSITION);

		setValue(0, code);
		setValue(1, name);
		setValue(2, parent);
		setValue(3, level);
	}
}
