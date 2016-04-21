/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profileDB.tables.records;


import com.moseeker.db.profileDB.tables.Profile;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 用户profile表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileRecord extends UpdatableRecordImpl<ProfileRecord> implements Record8<UInteger, String, Byte, Byte, Byte, Integer, Timestamp, Timestamp> {

	private static final long serialVersionUID = 969695711;

	/**
	 * Setter for <code>profileDB.profile.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>profileDB.profile.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>profileDB.profile.uuid</code>. profile的uuid标识
	 */
	public void setUuid(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>profileDB.profile.uuid</code>. profile的uuid标识
	 */
	public String getUuid() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>profileDB.profile.lang</code>. profile语言 1:chinese 2:english
	 */
	public void setLang(Byte value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>profileDB.profile.lang</code>. profile语言 1:chinese 2:english
	 */
	public Byte getLang() {
		return (Byte) getValue(2);
	}

	/**
	 * Setter for <code>profileDB.profile.source</code>. Profile的创建来源, 1:Moseeker手机 2:PC Profile 3:Email 4:导入
	 */
	public void setSource(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>profileDB.profile.source</code>. Profile的创建来源, 1:Moseeker手机 2:PC Profile 3:Email 4:导入
	 */
	public Byte getSource() {
		return (Byte) getValue(3);
	}

	/**
	 * Setter for <code>profileDB.profile.completeness</code>. Profile完整度
	 */
	public void setCompleteness(Byte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>profileDB.profile.completeness</code>. Profile完整度
	 */
	public Byte getCompleteness() {
		return (Byte) getValue(4);
	}

	/**
	 * Setter for <code>profileDB.profile.user_id</code>. 用户ID
	 */
	public void setUserId(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>profileDB.profile.user_id</code>. 用户ID
	 */
	public Integer getUserId() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>profileDB.profile.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>profileDB.profile.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(6);
	}

	/**
	 * Setter for <code>profileDB.profile.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>profileDB.profile.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(7);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<UInteger> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<UInteger, String, Byte, Byte, Byte, Integer, Timestamp, Timestamp> fieldsRow() {
		return (Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<UInteger, String, Byte, Byte, Byte, Integer, Timestamp, Timestamp> valuesRow() {
		return (Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return Profile.PROFILE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Profile.PROFILE.UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field3() {
		return Profile.PROFILE.LANG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field4() {
		return Profile.PROFILE.SOURCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field5() {
		return Profile.PROFILE.COMPLETENESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return Profile.PROFILE.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field7() {
		return Profile.PROFILE.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field8() {
		return Profile.PROFILE.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getUuid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value3() {
		return getLang();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value4() {
		return getSource();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value5() {
		return getCompleteness();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value7() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value8() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value2(String value) {
		setUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value3(Byte value) {
		setLang(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value4(Byte value) {
		setSource(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value5(Byte value) {
		setCompleteness(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value6(Integer value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value7(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord value8(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileRecord values(UInteger value1, String value2, Byte value3, Byte value4, Byte value5, Integer value6, Timestamp value7, Timestamp value8) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ProfileRecord
	 */
	public ProfileRecord() {
		super(Profile.PROFILE);
	}

	/**
	 * Create a detached, initialised ProfileRecord
	 */
	public ProfileRecord(UInteger id, String uuid, Byte lang, Byte source, Byte completeness, Integer userId, Timestamp createTime, Timestamp updateTime) {
		super(Profile.PROFILE);

		setValue(0, id);
		setValue(1, uuid);
		setValue(2, lang);
		setValue(3, source);
		setValue(4, completeness);
		setValue(5, userId);
		setValue(6, createTime);
		setValue(7, updateTime);
	}
}
