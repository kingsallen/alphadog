/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrHtml5UniqueStatistics;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 专题传播统计去重信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHtml5UniqueStatisticsRecord extends UpdatableRecordImpl<HrHtml5UniqueStatisticsRecord> implements Record7<Integer, Integer, Integer, Integer, Integer, Date, Integer> {

    private static final long serialVersionUID = 221812593;

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.id</code>. primary key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.id</code>. primary key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.topic_id</code>. wx_topic.id
     */
    public void setTopicId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.topic_id</code>. wx_topic.id
     */
    public Integer getTopicId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.company_id</code>. company.id
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.company_id</code>. company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.view_num_uv</code>. 浏览人数
     */
    public void setViewNumUv(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.view_num_uv</code>. 浏览人数
     */
    public Integer getViewNumUv() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.recom_view_num_uv</code>. 推荐浏览人数
     */
    public void setRecomViewNumUv(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.recom_view_num_uv</code>. 推荐浏览人数
     */
    public Integer getRecomViewNumUv() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.create_date</code>. 创建日期
     */
    public void setCreateDate(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.create_date</code>. 创建日期
     */
    public Date getCreateDate() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_html5_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public void setInfoType(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_html5_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public Integer getInfoType() {
        return (Integer) get(6);
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
    public Row7<Integer, Integer, Integer, Integer, Integer, Date, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, Integer, Integer, Date, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.TOPIC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.VIEW_NUM_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.RECOM_VIEW_NUM_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.CREATE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.INFO_TYPE;
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
        return getTopicId();
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
        return getViewNumUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getRecomViewNumUv();
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
        return getInfoType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value2(Integer value) {
        setTopicId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value3(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value4(Integer value) {
        setViewNumUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value5(Integer value) {
        setRecomViewNumUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value6(Date value) {
        setCreateDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord value7(Integer value) {
        setInfoType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatisticsRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Date value6, Integer value7) {
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
     * Create a detached HrHtml5UniqueStatisticsRecord
     */
    public HrHtml5UniqueStatisticsRecord() {
        super(HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS);
    }

    /**
     * Create a detached, initialised HrHtml5UniqueStatisticsRecord
     */
    public HrHtml5UniqueStatisticsRecord(Integer id, Integer topicId, Integer companyId, Integer viewNumUv, Integer recomViewNumUv, Date createDate, Integer infoType) {
        super(HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS);

        set(0, id);
        set(1, topicId);
        set(2, companyId);
        set(3, viewNumUv);
        set(4, recomViewNumUv);
        set(5, createDate);
        set(6, infoType);
    }
}
