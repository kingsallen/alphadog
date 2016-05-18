/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables.records;


import com.moseeker.db.profiledb.tables.ProfileProjectexp;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
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
public class ProfileProjectexpRecord extends UpdatableRecordImpl<ProfileProjectexpRecord> implements Record19<UInteger, UInteger, Date, Date, UByte, String, String, UByte, String, String, String, String, String, String, String, String, String, Timestamp, Timestamp> {

	private static final long serialVersionUID = -1319271284;

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
	public void setEndUntilNow(UByte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.end_until_now</code>. 是否至今 0：否 1：是
	 */
	public UByte getEndUntilNow() {
		return (UByte) getValue(4);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.name</code>. 项目名称
	 */
	public void setName(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.name</code>. 项目名称
	 */
	public String getName() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.company_name</code>. 公司名称
	 */
	public void setCompanyName(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.company_name</code>. 公司名称
	 */
	public String getCompanyName() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.is_it</code>. 是否IT项目, 0:不是 1:是
	 */
	public void setIsIt(UByte value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.is_it</code>. 是否IT项目, 0:不是 1:是
	 */
	public UByte getIsIt() {
		return (UByte) getValue(7);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.dev_tool</code>. 开发工具
	 */
	public void setDevTool(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.dev_tool</code>. 开发工具
	 */
	public String getDevTool() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.hardware</code>. 硬件环境
	 */
	public void setHardware(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.hardware</code>. 硬件环境
	 */
	public String getHardware() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.software</code>. 软件环境
	 */
	public void setSoftware(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.software</code>. 软件环境
	 */
	public String getSoftware() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.url</code>. 项目网址
	 */
	public void setUrl(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.url</code>. 项目网址
	 */
	public String getUrl() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.description</code>. 项目描述
	 */
	public void setDescription(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.description</code>. 项目描述
	 */
	public String getDescription() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.role</code>. 项目角色
	 */
	public void setRole(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.role</code>. 项目角色
	 */
	public String getRole() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.responsibility</code>. 项目职责
	 */
	public void setResponsibility(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.responsibility</code>. 项目职责
	 */
	public String getResponsibility() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.achievement</code>. 项目业绩
	 */
	public void setAchievement(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.achievement</code>. 项目业绩
	 */
	public String getAchievement() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.member</code>. 项目成员
	 */
	public void setMember(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.member</code>. 项目成员
	 */
	public String getMember() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(17);
	}

	/**
	 * Setter for <code>profileDB.profile_projectexp.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>profileDB.profile_projectexp.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(18);
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
	// Record19 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row19<UInteger, UInteger, Date, Date, UByte, String, String, UByte, String, String, String, String, String, String, String, String, String, Timestamp, Timestamp> fieldsRow() {
		return (Row19) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row19<UInteger, UInteger, Date, Date, UByte, String, String, UByte, String, String, String, String, String, String, String, String, String, Timestamp, Timestamp> valuesRow() {
		return (Row19) super.valuesRow();
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
	public Field<UByte> field5() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.END_UNTIL_NOW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.COMPANY_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UByte> field8() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.IS_IT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.DEV_TOOL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.HARDWARE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.SOFTWARE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field13() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field14() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.ROLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field15() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.RESPONSIBILITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field16() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.ACHIEVEMENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field17() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.MEMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field18() {
		return ProfileProjectexp.PROFILE_PROJECTEXP.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field19() {
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
	public UByte value5() {
		return getEndUntilNow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getCompanyName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UByte value8() {
		return getIsIt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getDevTool();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getHardware();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getSoftware();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value13() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value14() {
		return getRole();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value15() {
		return getResponsibility();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value16() {
		return getAchievement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value17() {
		return getMember();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value18() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value19() {
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
	public ProfileProjectexpRecord value5(UByte value) {
		setEndUntilNow(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value6(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value7(String value) {
		setCompanyName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value8(UByte value) {
		setIsIt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value9(String value) {
		setDevTool(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value10(String value) {
		setHardware(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value11(String value) {
		setSoftware(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value12(String value) {
		setUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value13(String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value14(String value) {
		setRole(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value15(String value) {
		setResponsibility(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value16(String value) {
		setAchievement(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value17(String value) {
		setMember(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value18(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord value19(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProjectexpRecord values(UInteger value1, UInteger value2, Date value3, Date value4, UByte value5, String value6, String value7, UByte value8, String value9, String value10, String value11, String value12, String value13, String value14, String value15, String value16, String value17, Timestamp value18, Timestamp value19) {
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
		value13(value13);
		value14(value14);
		value15(value15);
		value16(value16);
		value17(value17);
		value18(value18);
		value19(value19);
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
	public ProfileProjectexpRecord(UInteger id, UInteger profileId, Date start, Date end, UByte endUntilNow, String name, String companyName, UByte isIt, String devTool, String hardware, String software, String url, String description, String role, String responsibility, String achievement, String member, Timestamp createTime, Timestamp updateTime) {
		super(ProfileProjectexp.PROFILE_PROJECTEXP);

		setValue(0, id);
		setValue(1, profileId);
		setValue(2, start);
		setValue(3, end);
		setValue(4, endUntilNow);
		setValue(5, name);
		setValue(6, companyName);
		setValue(7, isIt);
		setValue(8, devTool);
		setValue(9, hardware);
		setValue(10, software);
		setValue(11, url);
		setValue(12, description);
		setValue(13, role);
		setValue(14, responsibility);
		setValue(15, achievement);
		setValue(16, member);
		setValue(17, createTime);
		setValue(18, updateTime);
	}
}
