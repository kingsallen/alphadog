/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profileDB.tables.records;


import com.moseeker.db.profileDB.tables.ProfileProjectexp;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * Profile的项目经验
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileProjectexpRecord extends UpdatableRecordImpl<ProfileProjectexpRecord> implements Record12<UInteger, UInteger, Date, Date, Byte, String, String, String, String, String, Timestamp, Timestamp> {

	private static final long serialVersionUID = 1681513611;

	/**
	 * Setter for <code>profileDB.profile_projectexp.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.profile_id</code>. profile.id
	 */
	public void setProfileId(UInteger value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.profile_id</code>. profile.id
	 */
	public UInteger getProfileId() {
		return (UInteger) getValue(1);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.start</code>. 起止时间-起 yyyy-mm-dd
	 */
	public void setStart(Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.start</code>. 起止时间-起 yyyy-mm-dd
	 */
	public Date getStart() {
		return (Date) getValue(2);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.end</code>. 起止时间-止 yyyy-mm-dd
	 */
	public void setEnd(Date value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.end</code>. 起止时间-止 yyyy-mm-dd
	 */
	public Date getEnd() {
		return (Date) getValue(3);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.end_until_now</code>. 是否至今 0：否 1：是
	 */
	public void setEndUntilNow(Byte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.end_until_now</code>. 是否至今 0：否 1：是
	 */
	public Byte getEndUntilNow() {
		return (Byte) getValue(4);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.company</code>. 公司名称
	 */
	public void setCompany(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.company</code>. 公司名称
	 */
	public String getCompany() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.name</code>. 项目名称
	 */
	public void setName(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.name</code>. 项目名称
	 */
	public String getName() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.role</code>. 项目角色
	 */
	public void setRole(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.role</code>. 项目角色
	 */
	public String getRole() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.work_desc</code>. 工作描述
	 */
	public void setWorkDesc(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.work_desc</code>. 工作描述
	 */
	public String getWorkDesc() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.project_desc</code>. 项目描述
	 */
	public void setProjectDesc(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.project_desc</code>. 项目描述
	 */
	public String getProjectDesc() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(10);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(11);
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
	// Record12 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<UInteger, UInteger, Date, Date, Byte, String, String, String, String, String, Timestamp, Timestamp> fieldsRow() {
		return (Row12) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<UInteger, UInteger, Date, Date, Byte, String, String, String, String, String, Timestamp, Timestamp> valuesRow() {
		return (Row12) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field2() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field3() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.START;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field4() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.END;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field5() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.END_UNTIL_NOW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.COMPANY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.ROLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.WORK_DESC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.PROJECT_DESC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field11() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field12() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.UPDATE_TIME;
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
		return getStart();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value4() {
		return getEnd();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value5() {
		return getEndUntilNow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getCompany();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getRole();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getWorkDesc();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getProjectDesc();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value11() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value12() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value2(UInteger value) {
		setProfileId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value3(Date value) {
		setStart(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value4(Date value) {
		setEnd(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value5(Byte value) {
		setEndUntilNow(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value6(String value) {
		setCompany(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value7(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value8(String value) {
		setRole(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value9(String value) {
		setWorkDesc(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value10(String value) {
		setProjectDesc(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value11(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value12(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord values(UInteger value1, UInteger value2, Date value3, Date value4, Byte value5, String value6, String value7, String value8, String value9, String value10, Timestamp value11, Timestamp value12) {
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
		value12(value12);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ProfileProjectexpRecord
	 */
	public ProfileProjectexpRecord() {
		super(ProfileProjectexp.PROFILE_PROJECTEXP);
	}

	/**
	 * Create a detached, initialised ProfileProjectexpRecord
	 */
	public ProfileProjectexpRecord(UInteger id, UInteger profileId, Date start, Date end, Byte endUntilNow, String company, String name, String role, String workDesc, String projectDesc, Timestamp createTime, Timestamp updateTime) {
		super(ProfileProjectexp.PROFILE_PROJECTEXP);

		setValue(0, id);
		setValue(1, profileId);
		setValue(2, start);
		setValue(3, end);
		setValue(4, endUntilNow);
		setValue(5, company);
		setValue(6, name);
		setValue(7, role);
		setValue(8, workDesc);
		setValue(9, projectDesc);
		setValue(10, createTime);
		setValue(11, updateTime);
	}
}
