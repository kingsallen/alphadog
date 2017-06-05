/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrdbHrHtml5Statistics;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrdbHrHtml5StatisticsRecord extends TableRecordImpl<HrdbHrHtml5StatisticsRecord> implements Record5<Long, Long, Double, Long, Date> {

    private static final long serialVersionUID = -1403491302;

    /**
     * Setter for <code>hrdb.hrdb.hr_html5_statistics.topic_id</code>.
     */
    public void setTopicId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hrdb.hr_html5_statistics.topic_id</code>.
     */
    public Long getTopicId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>hrdb.hrdb.hr_html5_statistics.view_num_pv</code>.
     */
    public void setViewNumPv(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hrdb.hr_html5_statistics.view_num_pv</code>.
     */
    public Long getViewNumPv() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>hrdb.hrdb.hr_html5_statistics.recom_view_num_pv</code>.
     */
    public void setRecomViewNumPv(Double value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hrdb.hr_html5_statistics.recom_view_num_pv</code>.
     */
    public Double getRecomViewNumPv() {
        return (Double) get(2);
    }

    /**
     * Setter for <code>hrdb.hrdb.hr_html5_statistics.company_id</code>.
     */
    public void setCompanyId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hrdb.hr_html5_statistics.company_id</code>.
     */
    public Long getCompanyId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>hrdb.hrdb.hr_html5_statistics.create_date</code>.
     */
    public void setCreateDate(Date value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hrdb.hr_html5_statistics.create_date</code>.
     */
    public Date getCreateDate() {
        return (Date) get(4);
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, Double, Long, Date> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, Long, Double, Long, Date> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS.TOPIC_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS.VIEW_NUM_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field3() {
        return HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS.RECOM_VIEW_NUM_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field5() {
        return HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS.CREATE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getTopicId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getViewNumPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value3() {
        return getRecomViewNumPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value5() {
        return getCreateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrdbHrHtml5StatisticsRecord value1(Long value) {
        setTopicId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrdbHrHtml5StatisticsRecord value2(Long value) {
        setViewNumPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrdbHrHtml5StatisticsRecord value3(Double value) {
        setRecomViewNumPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrdbHrHtml5StatisticsRecord value4(Long value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrdbHrHtml5StatisticsRecord value5(Date value) {
        setCreateDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrdbHrHtml5StatisticsRecord values(Long value1, Long value2, Double value3, Long value4, Date value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrdbHrHtml5StatisticsRecord
     */
    public HrdbHrHtml5StatisticsRecord() {
        super(HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS);
    }

    /**
     * Create a detached, initialised HrdbHrHtml5StatisticsRecord
     */
    public HrdbHrHtml5StatisticsRecord(Long topicId, Long viewNumPv, Double recomViewNumPv, Long companyId, Date createDate) {
        super(HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS);

        set(0, topicId);
        set(1, viewNumPv);
        set(2, recomViewNumPv);
        set(3, companyId);
        set(4, createDate);
    }
}