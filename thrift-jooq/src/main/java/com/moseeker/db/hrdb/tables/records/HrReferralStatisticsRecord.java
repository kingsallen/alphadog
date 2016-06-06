/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrReferralStatistics;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 内推统计表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrReferralStatisticsRecord extends UpdatableRecordImpl<HrReferralStatisticsRecord> implements Record15<Integer, String, String, Integer, UInteger, Date, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> {

	private static final long serialVersionUID = 1556132389;

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.id</code>. primary key
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.id</code>. primary key
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.position_title</code>. hr_position.title
	 */
	public void setPositionTitle(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.position_title</code>. hr_position.title
	 */
	public String getPositionTitle() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.employee_name</code>. sys_employee.cname
	 */
	public void setEmployeeName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.employee_name</code>. sys_employee.cname
	 */
	public String getEmployeeName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.employee_id</code>. 推荐员工 sys.employee.id
	 */
	public void setEmployeeId(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.employee_id</code>. 推荐员工 sys.employee.id
	 */
	public Integer getEmployeeId() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.company_id</code>. sys_wechat.id
	 */
	public void setCompanyId(UInteger value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.company_id</code>. sys_wechat.id
	 */
	public UInteger getCompanyId() {
		return (UInteger) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.create_date</code>. 创建时间
	 */
	public void setCreateDate(Date value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.create_date</code>. 创建时间
	 */
	public Date getCreateDate() {
		return (Date) getValue(5);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_num</code>. 推荐浏览人次
	 */
	public void setRecomNum(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_num</code>. 推荐浏览人次
	 */
	public Integer getRecomNum() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_fav_num</code>. 推荐感兴趣人次
	 */
	public void setRecomFavNum(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_fav_num</code>. 推荐感兴趣人次
	 */
	public Integer getRecomFavNum() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_mobile_num</code>. 推荐留手机人次
	 */
	public void setRecomMobileNum(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_mobile_num</code>. 推荐留手机人次
	 */
	public Integer getRecomMobileNum() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_apply_num</code>. 推荐投递人次
	 */
	public void setRecomApplyNum(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_apply_num</code>. 推荐投递人次
	 */
	public Integer getRecomApplyNum() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_after_review_num</code>. 推荐评审通过人数
	 */
	public void setRecomAfterReviewNum(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_after_review_num</code>. 推荐评审通过人数
	 */
	public Integer getRecomAfterReviewNum() {
		return (Integer) getValue(10);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_after_interview_num</code>. 推荐面试通过人数
	 */
	public void setRecomAfterInterviewNum(Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_after_interview_num</code>. 推荐面试通过人数
	 */
	public Integer getRecomAfterInterviewNum() {
		return (Integer) getValue(11);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.recom_on_board_num</code>. 推荐入职人数
	 */
	public void setRecomOnBoardNum(Integer value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.recom_on_board_num</code>. 推荐入职人数
	 */
	public Integer getRecomOnBoardNum() {
		return (Integer) getValue(12);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
	 */
	public void setInfoType(Integer value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
	 */
	public Integer getInfoType() {
		return (Integer) getValue(13);
	}

	/**
	 * Setter for <code>hrdb.hr_referral_statistics.publisher</code>.
	 */
	public void setPublisher(Integer value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>hrdb.hr_referral_statistics.publisher</code>.
	 */
	public Integer getPublisher() {
		return (Integer) getValue(14);
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
	// Record15 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row15<Integer, String, String, Integer, UInteger, Date, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fieldsRow() {
		return (Row15) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row15<Integer, String, String, Integer, UInteger, Date, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> valuesRow() {
		return (Row15) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.POSITION_TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.EMPLOYEE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.EMPLOYEE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field5() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field6() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.CREATE_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_FAV_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_MOBILE_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_APPLY_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_AFTER_REVIEW_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field12() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_AFTER_INTERVIEW_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field13() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.RECOM_ON_BOARD_NUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field14() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.INFO_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field15() {
		return HrReferralStatistics.HR_REFERRAL_STATISTICS.PUBLISHER;
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
		return getPositionTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getEmployeeName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getEmployeeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value5() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value6() {
		return getCreateDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getRecomNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getRecomFavNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getRecomMobileNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getRecomApplyNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getRecomAfterReviewNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value12() {
		return getRecomAfterInterviewNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value13() {
		return getRecomOnBoardNum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value14() {
		return getInfoType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value15() {
		return getPublisher();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value2(String value) {
		setPositionTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value3(String value) {
		setEmployeeName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value4(Integer value) {
		setEmployeeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value5(UInteger value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value6(Date value) {
		setCreateDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value7(Integer value) {
		setRecomNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value8(Integer value) {
		setRecomFavNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value9(Integer value) {
		setRecomMobileNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value10(Integer value) {
		setRecomApplyNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value11(Integer value) {
		setRecomAfterReviewNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value12(Integer value) {
		setRecomAfterInterviewNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value13(Integer value) {
		setRecomOnBoardNum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value14(Integer value) {
		setInfoType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord value15(Integer value) {
		setPublisher(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrReferralStatisticsRecord values(Integer value1, String value2, String value3, Integer value4, UInteger value5, Date value6, Integer value7, Integer value8, Integer value9, Integer value10, Integer value11, Integer value12, Integer value13, Integer value14, Integer value15) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrReferralStatisticsRecord
	 */
	public HrReferralStatisticsRecord() {
		super(HrReferralStatistics.HR_REFERRAL_STATISTICS);
	}

	/**
	 * Create a detached, initialised HrReferralStatisticsRecord
	 */
	public HrReferralStatisticsRecord(Integer id, String positionTitle, String employeeName, Integer employeeId, UInteger companyId, Date createDate, Integer recomNum, Integer recomFavNum, Integer recomMobileNum, Integer recomApplyNum, Integer recomAfterReviewNum, Integer recomAfterInterviewNum, Integer recomOnBoardNum, Integer infoType, Integer publisher) {
		super(HrReferralStatistics.HR_REFERRAL_STATISTICS);

		setValue(0, id);
		setValue(1, positionTitle);
		setValue(2, employeeName);
		setValue(3, employeeId);
		setValue(4, companyId);
		setValue(5, createDate);
		setValue(6, recomNum);
		setValue(7, recomFavNum);
		setValue(8, recomMobileNum);
		setValue(9, recomApplyNum);
		setValue(10, recomAfterReviewNum);
		setValue(11, recomAfterInterviewNum);
		setValue(12, recomOnBoardNum);
		setValue(13, infoType);
		setValue(14, publisher);
	}
}