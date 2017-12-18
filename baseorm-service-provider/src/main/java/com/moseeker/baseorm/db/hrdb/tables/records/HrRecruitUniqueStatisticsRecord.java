/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrRecruitUniqueStatistics;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 招聘数据去重信息统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrRecruitUniqueStatisticsRecord extends UpdatableRecordImpl<HrRecruitUniqueStatisticsRecord> implements Record13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Date, Integer> {

    private static final long serialVersionUID = 228244112;

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.id</code>. primary key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.id</code>. primary key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.position_id</code>. hr_position.id
     */
    public void setPositionId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.position_id</code>. hr_position.id
     */
    public Integer getPositionId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.company_id</code>. company.id
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.company_id</code>. company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.jd_uv</code>. JD 页浏览人数
     */
    public void setJdUv(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.jd_uv</code>. JD 页浏览人数
     */
    public Integer getJdUv() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.recom_jd_uv</code>. JD 页推荐浏览人数
     */
    public void setRecomJdUv(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.recom_jd_uv</code>. JD 页推荐浏览人数
     */
    public Integer getRecomJdUv() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.fav_num</code>. 感兴趣的人数
     */
    public void setFavNum(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.fav_num</code>. 感兴趣的人数
     */
    public Integer getFavNum() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.recom_fav_num</code>. 推荐感兴趣的人数
     */
    public void setRecomFavNum(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.recom_fav_num</code>. 推荐感兴趣的人数
     */
    public Integer getRecomFavNum() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.mobile_num</code>. 留手机的人数
     */
    public void setMobileNum(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.mobile_num</code>. 留手机的人数
     */
    public Integer getMobileNum() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.recom_mobile_num</code>. 推荐感兴趣留手机的人数
     */
    public void setRecomMobileNum(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.recom_mobile_num</code>. 推荐感兴趣留手机的人数
     */
    public Integer getRecomMobileNum() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.apply_num</code>. 投递人数
     */
    public void setApplyNum(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.apply_num</code>. 投递人数
     */
    public Integer getApplyNum() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.recom_apply_num</code>. 推荐投递人数
     */
    public void setRecomApplyNum(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.recom_apply_num</code>. 推荐投递人数
     */
    public Integer getRecomApplyNum() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.create_date</code>. 创建日期
     */
    public void setCreateDate(Date value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.create_date</code>. 创建日期
     */
    public Date getCreateDate() {
        return (Date) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_recruit_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public void setInfoType(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_recruit_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public Integer getInfoType() {
        return (Integer) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Date, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Date, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.JD_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.RECOM_JD_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.FAV_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.RECOM_FAV_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.MOBILE_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.RECOM_MOBILE_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.APPLY_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.RECOM_APPLY_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field12() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.CREATE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.INFO_TYPE;
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
    public Integer value2() {
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getJdUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getRecomJdUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getFavNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getRecomFavNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getMobileNum();
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
        return getApplyNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getRecomApplyNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value12() {
        return getCreateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getInfoType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value2(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value3(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value4(Integer value) {
        setJdUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value5(Integer value) {
        setRecomJdUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value6(Integer value) {
        setFavNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value7(Integer value) {
        setRecomFavNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value8(Integer value) {
        setMobileNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value9(Integer value) {
        setRecomMobileNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value10(Integer value) {
        setApplyNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value11(Integer value) {
        setRecomApplyNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value12(Date value) {
        setCreateDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord value13(Integer value) {
        setInfoType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRecruitUniqueStatisticsRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6, Integer value7, Integer value8, Integer value9, Integer value10, Integer value11, Date value12, Integer value13) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrRecruitUniqueStatisticsRecord
     */
    public HrRecruitUniqueStatisticsRecord() {
        super(HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS);
    }

    /**
     * Create a detached, initialised HrRecruitUniqueStatisticsRecord
     */
    public HrRecruitUniqueStatisticsRecord(Integer id, Integer positionId, Integer companyId, Integer jdUv, Integer recomJdUv, Integer favNum, Integer recomFavNum, Integer mobileNum, Integer recomMobileNum, Integer applyNum, Integer recomApplyNum, Date createDate, Integer infoType) {
        super(HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS);

        set(0, id);
        set(1, positionId);
        set(2, companyId);
        set(3, jdUv);
        set(4, recomJdUv);
        set(5, favNum);
        set(6, recomFavNum);
        set(7, mobileNum);
        set(8, recomMobileNum);
        set(9, applyNum);
        set(10, recomApplyNum);
        set(11, createDate);
        set(12, infoType);
    }
}
