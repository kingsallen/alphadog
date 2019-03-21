/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrInterviewFeedbackTemplateItemRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 面试反馈表反馈项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewFeedbackTemplateItem extends TableImpl<HrInterviewFeedbackTemplateItemRecord> {

    private static final long serialVersionUID = 2076345841;

    /**
     * The reference instance of <code>hrdb.hr_interview_feedback_template_item</code>
     */
    public static final HrInterviewFeedbackTemplateItem HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM = new HrInterviewFeedbackTemplateItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrInterviewFeedbackTemplateItemRecord> getRecordType() {
        return HrInterviewFeedbackTemplateItemRecord.class;
    }

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.id</code>. 序列ID
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "序列ID");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.template_id</code>. hr_interview_feedback_template.id
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Integer> TEMPLATE_ID = createField("template_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "hr_interview_feedback_template.id");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.item_title</code>. 标题/问题
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, String> ITEM_TITLE = createField("item_title", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "标题/问题");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.item_description</code>. 详细描述
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, String> ITEM_DESCRIPTION = createField("item_description", org.jooq.impl.SQLDataType.VARCHAR.length(4000), this, "详细描述");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.item_type</code>. 1 文本 2单选 3 多选 4 打分
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Integer> ITEM_TYPE = createField("item_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "1 文本 2单选 3 多选 4 打分");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.item_order</code>. 排序
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Integer> ITEM_ORDER = createField("item_order", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "排序");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态：0 有效  1 无效 2 逻辑删除");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.create_time</code>. 创建时间
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_interview_feedback_template_item.update_time</code>. 更新时间
     */
    public final TableField<HrInterviewFeedbackTemplateItemRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>hrdb.hr_interview_feedback_template_item</code> table reference
     */
    public HrInterviewFeedbackTemplateItem() {
        this("hr_interview_feedback_template_item", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_interview_feedback_template_item</code> table reference
     */
    public HrInterviewFeedbackTemplateItem(String alias) {
        this(alias, HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM);
    }

    private HrInterviewFeedbackTemplateItem(String alias, Table<HrInterviewFeedbackTemplateItemRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrInterviewFeedbackTemplateItem(String alias, Table<HrInterviewFeedbackTemplateItemRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "面试反馈表反馈项");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Hrdb.HRDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HrInterviewFeedbackTemplateItemRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrInterviewFeedbackTemplateItemRecord> getPrimaryKey() {
        return Keys.KEY_HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrInterviewFeedbackTemplateItemRecord>> getKeys() {
        return Arrays.<UniqueKey<HrInterviewFeedbackTemplateItemRecord>>asList(Keys.KEY_HR_INTERVIEW_FEEDBACK_TEMPLATE_ITEM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackTemplateItem as(String alias) {
        return new HrInterviewFeedbackTemplateItem(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrInterviewFeedbackTemplateItem rename(String name) {
        return new HrInterviewFeedbackTemplateItem(name, null);
    }
}
