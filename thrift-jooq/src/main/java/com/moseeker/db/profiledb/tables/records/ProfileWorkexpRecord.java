/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables.records;


import com.moseeker.db.profiledb.tables.ProfileWorkexp;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;


/**
 * Profile的工作经历
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileWorkexpRecord extends UpdatableRecordImpl<ProfileWorkexpRecord> {

	private static final long serialVersionUID = 1244866205;

	/**
	 * Setter for <code>profiledb.profile_workexp.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.profile_id</code>. profile.id
	 */
	public void setProfileId(UInteger value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.profile_id</code>. profile.id
	 */
	public UInteger getProfileId() {
		return (UInteger) getValue(1);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.start</code>. 起止时间-起 yyyy-mm-dd
	 */
	public void setStart(Date value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.start</code>. 起止时间-起 yyyy-mm-dd
	 */
	public Date getStart() {
		return (Date) getValue(2);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.end</code>. 起止时间-止 yyyy-mm-dd
	 */
	public void setEnd(Date value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.end</code>. 起止时间-止 yyyy-mm-dd
	 */
	public Date getEnd() {
		return (Date) getValue(3);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.end_until_now</code>. 是否至今 0：否 1：是
	 */
	public void setEndUntilNow(UByte value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.end_until_now</code>. 是否至今 0：否 1：是
	 */
	public UByte getEndUntilNow() {
		return (UByte) getValue(4);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.salary_type</code>. 薪资类型，0:没选择, 1:年薪, 2:月薪, 3:日薪, 4:时薪
	 */
	public void setSalaryType(UByte value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.salary_type</code>. 薪资类型，0:没选择, 1:年薪, 2:月薪, 3:日薪, 4:时薪
	 */
	public UByte getSalaryType() {
		return (UByte) getValue(5);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.salary_code</code>. 薪资code
	 */
	public void setSalaryCode(UByte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.salary_code</code>. 薪资code
	 */
	public UByte getSalaryCode() {
		return (UByte) getValue(6);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.industry_code</code>. 行业字典编码
	 */
	public void setIndustryCode(UInteger value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.industry_code</code>. 行业字典编码
	 */
	public UInteger getIndustryCode() {
		return (UInteger) getValue(7);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.industry_name</code>. 行业名称
	 */
	public void setIndustryName(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.industry_name</code>. 行业名称
	 */
	public String getIndustryName() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.company_id</code>. 公司ID, hr_company.id
	 */
	public void setCompanyId(UInteger value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.company_id</code>. 公司ID, hr_company.id
	 */
	public UInteger getCompanyId() {
		return (UInteger) getValue(9);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.department_name</code>. 部门名称
	 */
	public void setDepartmentName(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.department_name</code>. 部门名称
	 */
	public String getDepartmentName() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.position_code</code>. 职能字典编码
	 */
	public void setPositionCode(UInteger value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.position_code</code>. 职能字典编码
	 */
	public UInteger getPositionCode() {
		return (UInteger) getValue(11);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.position_name</code>. 职能字典名称
	 */
	public void setPositionName(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.position_name</code>. 职能字典名称
	 */
	public String getPositionName() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.description</code>. 工作描述
	 */
	public void setDescription(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.description</code>. 工作描述
	 */
	public String getDescription() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.type</code>. 工作类型 0:没选择 1:全职 2:兼职 3:实习
	 */
	public void setType(UByte value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.type</code>. 工作类型 0:没选择 1:全职 2:兼职 3:实习
	 */
	public UByte getType() {
		return (UByte) getValue(14);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.city_code</code>. 工作地点（城市），字典编码
	 */
	public void setCityCode(UInteger value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.city_code</code>. 工作地点（城市），字典编码
	 */
	public UInteger getCityCode() {
		return (UInteger) getValue(15);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.city_name</code>. 工作地点（城市）名称
	 */
	public void setCityName(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.city_name</code>. 工作地点（城市）名称
	 */
	public String getCityName() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.report_to</code>. 汇报对象
	 */
	public void setReportTo(String value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.report_to</code>. 汇报对象
	 */
	public String getReportTo() {
		return (String) getValue(17);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.underlings</code>. 下属人数, 0:没有下属
	 */
	public void setUnderlings(UInteger value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.underlings</code>. 下属人数, 0:没有下属
	 */
	public UInteger getUnderlings() {
		return (UInteger) getValue(18);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.reference</code>. 证明人
	 */
	public void setReference(String value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.reference</code>. 证明人
	 */
	public String getReference() {
		return (String) getValue(19);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.resign_reason</code>. 离职原因
	 */
	public void setResignReason(String value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.resign_reason</code>. 离职原因
	 */
	public String getResignReason() {
		return (String) getValue(20);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.achievement</code>. 主要业绩
	 */
	public void setAchievement(String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.achievement</code>. 主要业绩
	 */
	public String getAchievement() {
		return (String) getValue(21);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(22);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(23);
	}

	/**
	 * Setter for <code>profiledb.profile_workexp.job</code>. 所处职位
	 */
	public void setJob(String value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>profiledb.profile_workexp.job</code>. 所处职位
	 */
	public String getJob() {
		return (String) getValue(24);
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
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ProfileWorkexpRecord
	 */
	public ProfileWorkexpRecord() {
		super(ProfileWorkexp.PROFILE_WORKEXP);
	}

	/**
	 * Create a detached, initialised ProfileWorkexpRecord
	 */
	public ProfileWorkexpRecord(UInteger id, UInteger profileId, Date start, Date end, UByte endUntilNow, UByte salaryType, UByte salaryCode, UInteger industryCode, String industryName, UInteger companyId, String departmentName, UInteger positionCode, String positionName, String description, UByte type, UInteger cityCode, String cityName, String reportTo, UInteger underlings, String reference, String resignReason, String achievement, Timestamp createTime, Timestamp updateTime, String job) {
		super(ProfileWorkexp.PROFILE_WORKEXP);

		setValue(0, id);
		setValue(1, profileId);
		setValue(2, start);
		setValue(3, end);
		setValue(4, endUntilNow);
		setValue(5, salaryType);
		setValue(6, salaryCode);
		setValue(7, industryCode);
		setValue(8, industryName);
		setValue(9, companyId);
		setValue(10, departmentName);
		setValue(11, positionCode);
		setValue(12, positionName);
		setValue(13, description);
		setValue(14, type);
		setValue(15, cityCode);
		setValue(16, cityName);
		setValue(17, reportTo);
		setValue(18, underlings);
		setValue(19, reference);
		setValue(20, resignReason);
		setValue(21, achievement);
		setValue(22, createTime);
		setValue(23, updateTime);
		setValue(24, job);
	}
}
