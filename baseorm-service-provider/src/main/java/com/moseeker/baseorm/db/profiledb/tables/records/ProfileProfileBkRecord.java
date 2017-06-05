/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.profiledb.tables.records;


import com.moseeker.baseorm.db.profiledb.tables.ProfileProfileBk;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


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
public class ProfileProfileBkRecord extends UpdatableRecordImpl<ProfileProfileBkRecord> implements Record9<Integer, String, Byte, Integer, Byte, Integer, Byte, Timestamp, Timestamp> {

	private static final long serialVersionUID = 1548685350;

	/**
	 * Setter for <code>profiledb.profile_profile_bk.id</code>. 主key
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.id</code>. 主key
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.uuid</code>. profile的uuid标识,与主键一一对应
	 */
	public void setUuid(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.uuid</code>. profile的uuid标识,与主键一一对应
	 */
	public String getUuid() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.lang</code>. profile语言 1:chinese 2:english
	 */
	public void setLang(Byte value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.lang</code>. profile语言 1:chinese 2:english
	 */
	public Byte getLang() {
		return (Byte) getValue(2);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.source</code>. Profile的创建来源, 0:未知, 或者mongo合并来的 1:微信企业端(正常), 2:微信企业端(我要投递), 3:微信企业端(我感兴趣), 4:微信聚合端(正常), 5:微信聚合端(我要投递), 6:微信聚合端(我感兴趣), 100:微信企业端Email申请, 101:微信聚合端Email申请, 150:微信企业端导入, 151:微信聚合端导入, 152:PC导入, 200:PC(正常添加) 201:PC(我要投递) 202: PC(我感兴趣)
	 */
	public void setSource(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.source</code>. Profile的创建来源, 0:未知, 或者mongo合并来的 1:微信企业端(正常), 2:微信企业端(我要投递), 3:微信企业端(我感兴趣), 4:微信聚合端(正常), 5:微信聚合端(我要投递), 6:微信聚合端(我感兴趣), 100:微信企业端Email申请, 101:微信聚合端Email申请, 150:微信企业端导入, 151:微信聚合端导入, 152:PC导入, 200:PC(正常添加) 201:PC(我要投递) 202: PC(我感兴趣)
	 */
	public Integer getSource() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.completeness</code>. Profile完整度
	 */
	public void setCompleteness(Byte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.completeness</code>. Profile完整度
	 */
	public Byte getCompleteness() {
		return (Byte) getValue(4);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.user_id</code>. 用户ID
	 */
	public void setUserId(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.user_id</code>. 用户ID
	 */
	public Integer getUserId() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.disable</code>. 是否有效，0：无效 1：有效
	 */
	public void setDisable(Byte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.disable</code>. 是否有效，0：无效 1：有效
	 */
	public Byte getDisable() {
		return (Byte) getValue(6);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(7);
	}

	/**
	 * Setter for <code>profiledb.profile_profile_bk.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>profiledb.profile_profile_bk.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(8);
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
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<Integer, String, Byte, Integer, Byte, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
		return (Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<Integer, String, Byte, Integer, Byte, Integer, Byte, Timestamp, Timestamp> valuesRow() {
		return (Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.UUID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field3() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.LANG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.SOURCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field5() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.COMPLETENESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field7() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.DISABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field8() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field9() {
		return ProfileProfileBk.PROFILE_PROFILE_BK.UPDATE_TIME;
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
	public Integer value4() {
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
	public Byte value7() {
		return getDisable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value8() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value9() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value2(String value) {
		setUuid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value3(Byte value) {
		setLang(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value4(Integer value) {
		setSource(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value5(Byte value) {
		setCompleteness(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value6(Integer value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value7(Byte value) {
		setDisable(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value8(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord value9(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBkRecord values(Integer value1, String value2, Byte value3, Integer value4, Byte value5, Integer value6, Byte value7, Timestamp value8, Timestamp value9) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ProfileProfileBkRecord
	 */
	public ProfileProfileBkRecord() {
		super(ProfileProfileBk.PROFILE_PROFILE_BK);
	}

	/**
	 * Create a detached, initialised ProfileProfileBkRecord
	 */
	public ProfileProfileBkRecord(Integer id, String uuid, Byte lang, Integer source, Byte completeness, Integer userId, Byte disable, Timestamp createTime, Timestamp updateTime) {
		super(ProfileProfileBk.PROFILE_PROFILE_BK);

		setValue(0, id);
		setValue(1, uuid);
		setValue(2, lang);
		setValue(3, source);
		setValue(4, completeness);
		setValue(5, userId);
		setValue(6, disable);
		setValue(7, createTime);
		setValue(8, updateTime);
	}
}