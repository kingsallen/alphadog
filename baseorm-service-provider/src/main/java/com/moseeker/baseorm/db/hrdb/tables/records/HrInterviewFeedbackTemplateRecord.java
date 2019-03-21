/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewFeedbackTemplate;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 面试反馈表模板
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewFeedbackTemplateRecord extends UpdatableRecordImpl<HrInterviewFeedbackTemplateRecord> implements Record8<Integer, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 832397738;

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.id</code>. 序列ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.id</code>. 序列ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.template_name</code>. 面试反馈表名称
     */
    public void setTemplateName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.template_name</code>. 面试反馈表名称
     */
    public String getTemplateName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.company_id</code>. 公司ID
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.company_id</code>. 公司ID
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.hr_id</code>. HR ID
     */
    public void setHrId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.hr_id</code>. HR ID
     */
    public Integer getHrId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public void setDisable(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public Integer getDisable() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.default_flag</code>. 是否是默认模表 0不是 1是
     */
    public void setDefaultFlag(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.default_flag</code>. 是否是默认模表 0不是 1是
     */
    public Integer getDefaultFlag() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.TEMPLATE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.DEFAULT_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE.UPDATE_TIME;
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
        return getTemplateName();
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
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getDefaultFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value2(String value) {
        setTemplateName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value3(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value4(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value5(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value6(Integer value) {
        setDefaultFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateRecord values(Integer value1, String value2, Integer value3, Integer value4, Integer value5, Integer value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrInterviewFeedbackTemplateRecord
     */
    public HrInterviewFeedbackTemplateRecord() {
        super(HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE);
    }

    /**
     * Create a detached, initialised HrInterviewFeedbackTemplateRecord
     */
    public HrInterviewFeedbackTemplateRecord(Integer id, String templateName, Integer companyId, Integer hrId, Integer disable, Integer defaultFlag, Timestamp createTime, Timestamp updateTime) {
        super(HrInterviewFeedbackTemplate.HR_INTERVIEW_FEEDBACK_TEMPLATE);

        set(0, id);
        set(1, templateName);
        set(2, companyId);
        set(3, hrId);
        set(4, disable);
        set(5, defaultFlag);
        set(6, createTime);
        set(7, updateTime);
    }
}
