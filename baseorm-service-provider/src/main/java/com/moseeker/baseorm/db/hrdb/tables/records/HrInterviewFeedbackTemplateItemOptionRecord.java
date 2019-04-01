/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewFeedbackTemplateItemOption;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 面试反馈选项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewFeedbackTemplateItemOptionRecord extends UpdatableRecordImpl<HrInterviewFeedbackTemplateItemOptionRecord> implements Record7<Integer, Integer, String, String, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -196435281;

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.id</code>. 序列ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.id</code>. 序列ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.item_id</code>. hr_interview_feedback_template_item.id
     */
    public void setItemId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.item_id</code>. hr_interview_feedback_template_item.id
     */
    public Integer getItemId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.option_name</code>. 反馈项名称
     */
    public void setOptionName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.option_name</code>. 反馈项名称
     */
    public String getOptionName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.option_value</code>. 反馈项值
     */
    public void setOptionValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.option_value</code>. 反馈项值
     */
    public String getOptionValue() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public void setDisable(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public Integer getDisable() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_interview_feedback_template_item_option.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_feedback_template_item_option.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(6);
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
    public Row7<Integer, Integer, String, String, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.ITEM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.OPTION_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.OPTION_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION.UPDATE_TIME;
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
        return getItemId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getOptionName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getOptionValue();
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
    public Timestamp value6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value2(Integer value) {
        setItemId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value3(String value) {
        setOptionName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value4(String value) {
        setOptionValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value5(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItemOptionRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, Timestamp value6, Timestamp value7) {
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
     * Create a detached HrInterviewFeedbackTemplateItemOptionRecord
     */
    public HrInterviewFeedbackTemplateItemOptionRecord() {
        super(HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION);
    }

    /**
     * Create a detached, initialised HrInterviewFeedbackTemplateItemOptionRecord
     */
    public HrInterviewFeedbackTemplateItemOptionRecord(Integer id, Integer itemId, String optionName, String optionValue, Integer disable, Timestamp createTime, Timestamp updateTime) {
        super(HrInterviewFeedbackTemplateItemOption.HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_OPTION);

        set(0, id);
        set(1, itemId);
        set(2, optionName);
        set(3, optionValue);
        set(4, disable);
        set(5, createTime);
        set(6, updateTime);
    }
}
