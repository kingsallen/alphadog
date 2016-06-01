/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables.records;


import com.moseeker.db.profiledb.tables.ProfileAwards;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * Profile的获得奖项表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileAwardsRecord extends UpdatableRecordImpl<ProfileAwardsRecord> implements Record9<UInteger, UInteger, Date, String, String, String, String, Timestamp, Timestamp> {

	private static final long serialVersionUID = -1220729630;

	/**
	 * Setter for <code>profileDB.profile_awards.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.profile_id</code>. profile.id
	 */
	public void setProfileId(UInteger value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.profile_id</code>. profile.id
	 */
	public UInteger getProfileId() {
		return (UInteger) getValue(1);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.reward_date</code>. 获奖日期 yyyy-mm-dd
	 */
	public void setRewardDate(Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.reward_date</code>. 获奖日期 yyyy-mm-dd
	 */
	public Date getRewardDate() {
		return (Date) getValue(2);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.name</code>. 获得奖项名称
	 */
	public void setName(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.name</code>. 获得奖项名称
	 */
	public String getName() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.award_winning_status</code>. 获奖身份
	 */
	public void setAwardWinningStatus(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.award_winning_status</code>. 获奖身份
	 */
	public String getAwardWinningStatus() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.level</code>. 级别
	 */
	public void setLevel(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.level</code>. 级别
	 */
	public String getLevel() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.description</code>. 描述
	 */
	public void setDescription(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.description</code>. 描述
	 */
	public String getDescription() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(7);
	}

	/**
	 * Setter for <code>profileDB.profile_awards.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>profileDB.profile_awards.update_time</code>. 更新时间
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
	public Record1<UInteger> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<UInteger, UInteger, Date, String, String, String, String, Timestamp, Timestamp> fieldsRow() {
		return (Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row9<UInteger, UInteger, Date, String, String, String, String, Timestamp, Timestamp> valuesRow() {
		return (Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return ProfileAwards.PROFILE_AWARDS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field2() {
		return ProfileAwards.PROFILE_AWARDS.PROFILE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field3() {
		return ProfileAwards.PROFILE_AWARDS.REWARD_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return ProfileAwards.PROFILE_AWARDS.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return ProfileAwards.PROFILE_AWARDS.AWARD_WINNING_STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return ProfileAwards.PROFILE_AWARDS.LEVEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return ProfileAwards.PROFILE_AWARDS.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field8() {
		return ProfileAwards.PROFILE_AWARDS.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field9() {
		return ProfileAwards.PROFILE_AWARDS.UPDATE_TIME;
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
	public UInteger value2() {
		return getProfileId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value3() {
		return getRewardDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getAwardWinningStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getLevel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getDescription();
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
	public ProfileAwardsRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value2(UInteger value) {
		setProfileId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value3(Date value) {
		setRewardDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value4(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value5(String value) {
		setAwardWinningStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value6(String value) {
		setLevel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value7(String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value8(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord value9(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileAwardsRecord values(UInteger value1, UInteger value2, Date value3, String value4, String value5, String value6, String value7, Timestamp value8, Timestamp value9) {
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
	 * Create a detached ProfileAwardsRecord
	 */
	public ProfileAwardsRecord() {
		super(ProfileAwards.PROFILE_AWARDS);
	}

	/**
	 * Create a detached, initialised ProfileAwardsRecord
	 */
	public ProfileAwardsRecord(UInteger id, UInteger profileId, Date rewardDate, String name, String awardWinningStatus, String level, String description, Timestamp createTime, Timestamp updateTime) {
		super(ProfileAwards.PROFILE_AWARDS);

		setValue(0, id);
		setValue(1, profileId);
		setValue(2, rewardDate);
		setValue(3, name);
		setValue(4, awardWinningStatus);
		setValue(5, level);
		setValue(6, description);
		setValue(7, createTime);
		setValue(8, updateTime);
	}
}
