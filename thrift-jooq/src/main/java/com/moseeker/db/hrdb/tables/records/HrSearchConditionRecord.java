/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrSearchCondition;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record22;
import org.jooq.Row22;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 候选人列表常用筛选项
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrSearchConditionRecord extends UpdatableRecordImpl<HrSearchConditionRecord> implements Record22<Integer, String, Integer, Integer, String, String, String, String, String, String, Integer, Integer, Integer, String, Integer, String, String, Integer, Integer, Timestamp, Integer, Integer> {

	private static final long serialVersionUID = 856042815;

	/**
	 * Setter for <code>hrdb.hr_search_condition.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.name</code>. 常用搜索条件名称，长度不超过12个字符
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.name</code>. 常用搜索条件名称，长度不超过12个字符
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.publisher</code>. 发布人id(user_hr_account.id)
	 */
	public void setPublisher(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.publisher</code>. 发布人id(user_hr_account.id)
	 */
	public Integer getPublisher() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.position_id</code>. 职位id
	 */
	public void setPositionId(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.position_id</code>. 职位id
	 */
	public Integer getPositionId() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.keyword</code>. 关键字
	 */
	public void setKeyword(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.keyword</code>. 关键字
	 */
	public String getKeyword() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.submit_time</code>. 投递时间
	 */
	public void setSubmitTime(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.submit_time</code>. 投递时间
	 */
	public String getSubmitTime() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.work_years</code>. 工作年限、工龄
	 */
	public void setWorkYears(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.work_years</code>. 工作年限、工龄
	 */
	public String getWorkYears() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.city_name</code>. 现居住地
	 */
	public void setCityName(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.city_name</code>. 现居住地
	 */
	public String getCityName() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.degree</code>. 学历
	 */
	public void setDegree(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.degree</code>. 学历
	 */
	public String getDegree() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.past_position</code>. 曾任职务
	 */
	public void setPastPosition(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.past_position</code>. 曾任职务
	 */
	public String getPastPosition() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.in_last_job_search_position</code>. 是否只在最近一份工作中搜索曾任职务(0:否，1:是)
	 */
	public void setInLastJobSearchPosition(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.in_last_job_search_position</code>. 是否只在最近一份工作中搜索曾任职务(0:否，1:是)
	 */
	public Integer getInLastJobSearchPosition() {
		return (Integer) getValue(10);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.min_age</code>. 最小年龄
	 */
	public void setMinAge(Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.min_age</code>. 最小年龄
	 */
	public Integer getMinAge() {
		return (Integer) getValue(11);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.max_age</code>. 最大年龄
	 */
	public void setMaxAge(Integer value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.max_age</code>. 最大年龄
	 */
	public Integer getMaxAge() {
		return (Integer) getValue(12);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.intention_city_name</code>. 期望工作地(城市编码)
	 */
	public void setIntentionCityName(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.intention_city_name</code>. 期望工作地(城市编码)
	 */
	public String getIntentionCityName() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.sex</code>. 性别
	 */
	public void setSex(Integer value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.sex</code>. 性别
	 */
	public Integer getSex() {
		return (Integer) getValue(14);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.intention_salary_code</code>. 期望薪资
	 */
	public void setIntentionSalaryCode(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.intention_salary_code</code>. 期望薪资
	 */
	public String getIntentionSalaryCode() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.company_name</code>. 公司名称
	 */
	public void setCompanyName(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.company_name</code>. 公司名称
	 */
	public String getCompanyName() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.in_last_job_search_company</code>. 是否只在最近一份工作中搜索公司名称（0:否，1:是）
	 */
	public void setInLastJobSearchCompany(Integer value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.in_last_job_search_company</code>. 是否只在最近一份工作中搜索公司名称（0:否，1:是）
	 */
	public Integer getInLastJobSearchCompany() {
		return (Integer) getValue(17);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.hr_account_id</code>. 创建人id(user_hr_account.id)
	 */
	public void setHrAccountId(Integer value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.hr_account_id</code>. 创建人id(user_hr_account.id)
	 */
	public Integer getHrAccountId() {
		return (Integer) getValue(18);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(19);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.update_time</code>. 简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
	 */
	public void setUpdateTime(Integer value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.update_time</code>. 简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
	 */
	public Integer getUpdateTime() {
		return (Integer) getValue(20);
	}

	/**
	 * Setter for <code>hrdb.hr_search_condition.type</code>. 类型（0：候选人列表筛选条件，1：人才库列表筛选条件）
	 */
	public void setType(Integer value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>hrdb.hr_search_condition.type</code>. 类型（0：候选人列表筛选条件，1：人才库列表筛选条件）
	 */
	public Integer getType() {
		return (Integer) getValue(21);
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
	// Record22 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row22<Integer, String, Integer, Integer, String, String, String, String, String, String, Integer, Integer, Integer, String, Integer, String, String, Integer, Integer, Timestamp, Integer, Integer> fieldsRow() {
		return (Row22) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row22<Integer, String, Integer, Integer, String, String, String, String, String, String, Integer, Integer, Integer, String, Integer, String, String, Integer, Integer, Timestamp, Integer, Integer> valuesRow() {
		return (Row22) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrSearchCondition.HR_SEARCH_CONDITION.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return HrSearchCondition.HR_SEARCH_CONDITION.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return HrSearchCondition.HR_SEARCH_CONDITION.PUBLISHER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return HrSearchCondition.HR_SEARCH_CONDITION.POSITION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return HrSearchCondition.HR_SEARCH_CONDITION.KEYWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return HrSearchCondition.HR_SEARCH_CONDITION.SUBMIT_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return HrSearchCondition.HR_SEARCH_CONDITION.WORK_YEARS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return HrSearchCondition.HR_SEARCH_CONDITION.CITY_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return HrSearchCondition.HR_SEARCH_CONDITION.DEGREE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return HrSearchCondition.HR_SEARCH_CONDITION.PAST_POSITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return HrSearchCondition.HR_SEARCH_CONDITION.IN_LAST_JOB_SEARCH_POSITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field12() {
		return HrSearchCondition.HR_SEARCH_CONDITION.MIN_AGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field13() {
		return HrSearchCondition.HR_SEARCH_CONDITION.MAX_AGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field14() {
		return HrSearchCondition.HR_SEARCH_CONDITION.INTENTION_CITY_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field15() {
		return HrSearchCondition.HR_SEARCH_CONDITION.SEX;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field16() {
		return HrSearchCondition.HR_SEARCH_CONDITION.INTENTION_SALARY_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field17() {
		return HrSearchCondition.HR_SEARCH_CONDITION.COMPANY_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field18() {
		return HrSearchCondition.HR_SEARCH_CONDITION.IN_LAST_JOB_SEARCH_COMPANY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field19() {
		return HrSearchCondition.HR_SEARCH_CONDITION.HR_ACCOUNT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field20() {
		return HrSearchCondition.HR_SEARCH_CONDITION.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field21() {
		return HrSearchCondition.HR_SEARCH_CONDITION.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field22() {
		return HrSearchCondition.HR_SEARCH_CONDITION.TYPE;
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
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getPublisher();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getPositionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getKeyword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getSubmitTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getWorkYears();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getCityName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getDegree();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getPastPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getInLastJobSearchPosition();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value12() {
		return getMinAge();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value13() {
		return getMaxAge();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value14() {
		return getIntentionCityName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value15() {
		return getSex();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value16() {
		return getIntentionSalaryCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value17() {
		return getCompanyName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value18() {
		return getInLastJobSearchCompany();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value19() {
		return getHrAccountId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value20() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value21() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value22() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value3(Integer value) {
		setPublisher(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value4(Integer value) {
		setPositionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value5(String value) {
		setKeyword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value6(String value) {
		setSubmitTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value7(String value) {
		setWorkYears(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value8(String value) {
		setCityName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value9(String value) {
		setDegree(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value10(String value) {
		setPastPosition(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value11(Integer value) {
		setInLastJobSearchPosition(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value12(Integer value) {
		setMinAge(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value13(Integer value) {
		setMaxAge(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value14(String value) {
		setIntentionCityName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value15(Integer value) {
		setSex(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value16(String value) {
		setIntentionSalaryCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value17(String value) {
		setCompanyName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value18(Integer value) {
		setInLastJobSearchCompany(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value19(Integer value) {
		setHrAccountId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value20(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value21(Integer value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord value22(Integer value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrSearchConditionRecord values(Integer value1, String value2, Integer value3, Integer value4, String value5, String value6, String value7, String value8, String value9, String value10, Integer value11, Integer value12, Integer value13, String value14, Integer value15, String value16, String value17, Integer value18, Integer value19, Timestamp value20, Integer value21, Integer value22) {
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
		value20(value20);
		value21(value21);
		value22(value22);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrSearchConditionRecord
	 */
	public HrSearchConditionRecord() {
		super(HrSearchCondition.HR_SEARCH_CONDITION);
	}

	/**
	 * Create a detached, initialised HrSearchConditionRecord
	 */
	public HrSearchConditionRecord(Integer id, String name, Integer publisher, Integer positionId, String keyword, String submitTime, String workYears, String cityName, String degree, String pastPosition, Integer inLastJobSearchPosition, Integer minAge, Integer maxAge, String intentionCityName, Integer sex, String intentionSalaryCode, String companyName, Integer inLastJobSearchCompany, Integer hrAccountId, Timestamp createTime, Integer updateTime, Integer type) {
		super(HrSearchCondition.HR_SEARCH_CONDITION);

		setValue(0, id);
		setValue(1, name);
		setValue(2, publisher);
		setValue(3, positionId);
		setValue(4, keyword);
		setValue(5, submitTime);
		setValue(6, workYears);
		setValue(7, cityName);
		setValue(8, degree);
		setValue(9, pastPosition);
		setValue(10, inLastJobSearchPosition);
		setValue(11, minAge);
		setValue(12, maxAge);
		setValue(13, intentionCityName);
		setValue(14, sex);
		setValue(15, intentionSalaryCode);
		setValue(16, companyName);
		setValue(17, inLastJobSearchCompany);
		setValue(18, hrAccountId);
		setValue(19, createTime);
		setValue(20, updateTime);
		setValue(21, type);
	}
}
