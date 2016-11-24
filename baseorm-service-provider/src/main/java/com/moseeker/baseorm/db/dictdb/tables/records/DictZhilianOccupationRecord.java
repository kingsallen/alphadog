/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;
import org.jooq.types.UShort;


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
public class DictZhilianOccupationRecord extends UpdatableRecordImpl<DictZhilianOccupationRecord> implements Record7<Integer, UInteger, String, Integer, UShort, UShort, Timestamp> {

	private static final long serialVersionUID = -1083916414;

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.code</code>. 职能id
	 */
	public void setCode(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.code</code>. 职能id
	 */
	public Integer getCode() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.parent_Id</code>. 父Id，上一级职能的ID
	 */
	public void setParentId(UInteger value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.parent_Id</code>. 父Id，上一级职能的ID
	 */
	public UInteger getParentId() {
		return (UInteger) getValue(1);
	}

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.name</code>. 职能名称
	 */
	public void setName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.name</code>. 职能名称
	 */
	public String getName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.code_other</code>.
	 */
	public void setCodeOther(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.code_other</code>.
	 */
	public Integer getCodeOther() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.level</code>. 职能级别 1是一级2是
	 */
	public void setLevel(UShort value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.level</code>. 职能级别 1是一级2是
	 */
	public UShort getLevel() {
		return (UShort) getValue(4);
	}

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.status</code>. 只能状态 0 是无效 1是有效
	 */
	public void setStatus(UShort value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.status</code>. 只能状态 0 是无效 1是有效
	 */
	public UShort getStatus() {
		return (UShort) getValue(5);
	}

	/**
	 * Setter for <code>dictdb.dict_zhilian_occupation.createTime</code>. 创建时间
	 */
	public void setCreatetime(Timestamp value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>dictdb.dict_zhilian_occupation.createTime</code>. 创建时间
	 */
	public Timestamp getCreatetime() {
		return (Timestamp) getValue(6);
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
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, UInteger, String, Integer, UShort, UShort, Timestamp> fieldsRow() {
		return (Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<Integer, UInteger, String, Integer, UShort, UShort, Timestamp> valuesRow() {
		return (Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field2() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.PARENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE_OTHER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UShort> field5() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.LEVEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UShort> field6() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field7() {
		return DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CREATETIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value2() {
		return getParentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getCodeOther();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UShort value5() {
		return getLevel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UShort value6() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value7() {
		return getCreatetime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value1(Integer value) {
		setCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value2(UInteger value) {
		setParentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value3(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value4(Integer value) {
		setCodeOther(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value5(UShort value) {
		setLevel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value6(UShort value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord value7(Timestamp value) {
		setCreatetime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DictZhilianOccupationRecord values(Integer value1, UInteger value2, String value3, Integer value4, UShort value5, UShort value6, Timestamp value7) {
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
	 * Create a detached DictZhilianOccupationRecord
	 */
	public DictZhilianOccupationRecord() {
		super(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION);
	}

	/**
	 * Create a detached, initialised DictZhilianOccupationRecord
	 */
	public DictZhilianOccupationRecord(Integer code, UInteger parentId, String name, Integer codeOther, UShort level, UShort status, Timestamp createtime) {
		super(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION);

		setValue(0, code);
		setValue(1, parentId);
		setValue(2, name);
		setValue(3, codeOther);
		setValue(4, level);
		setValue(5, status);
		setValue(6, createtime);
	}
}
