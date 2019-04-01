/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrInterviewFeedbackResultAnswerSheetRecord;

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
 * 面试反馈结果
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewFeedbackResultAnswerSheet extends TableImpl<HrInterviewFeedbackResultAnswerSheetRecord> {

    private static final long serialVersionUID = -910636496;

    /**
     * The reference instance of <code>hrdb.hr_interview_feedback_result_answer_sheet</code>
     */
    public static final HrInterviewFeedbackResultAnswerSheet HR_INTERVIEW_FEEDBACK_RESULT_ANSWER_SHEET = new HrInterviewFeedbackResultAnswerSheet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrInterviewFeedbackResultAnswerSheetRecord> getRecordType() {
        return HrInterviewFeedbackResultAnswerSheetRecord.class;
    }

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.id</code>. 序列ID
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "序列ID");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.interviewer_id</code>. 面试官ID
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> INTERVIEWER_ID = createField("interviewer_id", org.jooq.impl.SQLDataType.INTEGER, this, "面试官ID");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.applier_id</code>. 面试者id
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> APPLIER_ID = createField("applier_id", org.jooq.impl.SQLDataType.INTEGER, this, "面试者id");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.application_id</code>. 申请ID
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> APPLICATION_ID = createField("application_id", org.jooq.impl.SQLDataType.INTEGER, this, "申请ID");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.interview_concrete_id</code>. 面试安排ID,hr_interview_concrete.id 
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> INTERVIEW_CONCRETE_ID = createField("interview_concrete_id", org.jooq.impl.SQLDataType.INTEGER, this, "面试安排ID,hr_interview_concrete.id ");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.interview_template_id</code>. 面试模版ID,hr_interview_feedback_template.id 
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> INTERVIEW_TEMPLATE_ID = createField("interview_template_id", org.jooq.impl.SQLDataType.INTEGER, this, "面试模版ID,hr_interview_feedback_template.id ");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.passed</code>. 是否通过面试 1 通过 0 不通过 2未参加面试
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> PASSED = createField("passed", org.jooq.impl.SQLDataType.INTEGER, this, "是否通过面试 1 通过 0 不通过 2未参加面试");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.evaluation</code>. 综合评价
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, String> EVALUATION = createField("evaluation", org.jooq.impl.SQLDataType.VARCHAR.length(4000), this, "综合评价");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态：0 有效  1 无效 2 逻辑删除");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.create_time</code>. 创建时间
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_interview_feedback_result_answer_sheet.update_time</code>. 更新时间
     */
    public final TableField<HrInterviewFeedbackResultAnswerSheetRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>hrdb.hr_interview_feedback_result_answer_sheet</code> table reference
     */
    public HrInterviewFeedbackResultAnswerSheet() {
        this("hr_interview_feedback_result_answer_sheet", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_interview_feedback_result_answer_sheet</code> table reference
     */
    public HrInterviewFeedbackResultAnswerSheet(String alias) {
        this(alias, HR_INTERVIEW_FEEDBACK_RESULT_ANSWER_SHEET);
    }

    private HrInterviewFeedbackResultAnswerSheet(String alias, Table<HrInterviewFeedbackResultAnswerSheetRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrInterviewFeedbackResultAnswerSheet(String alias, Table<HrInterviewFeedbackResultAnswerSheetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "面试反馈结果");
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
    public Identity<HrInterviewFeedbackResultAnswerSheetRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_INTERVIEW_FEEDBACK_RESULT_ANSWER_SHEET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrInterviewFeedbackResultAnswerSheetRecord> getPrimaryKey() {
        return Keys.KEY_HR_INTERVIEW_FEEDBACK_RESULT_ANSWER_SHEET_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrInterviewFeedbackResultAnswerSheetRecord>> getKeys() {
        return Arrays.<UniqueKey<HrInterviewFeedbackResultAnswerSheetRecord>>asList(Keys.KEY_HR_INTERVIEW_FEEDBACK_RESULT_ANSWER_SHEET_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewFeedbackResultAnswerSheet as(String alias) {
        return new HrInterviewFeedbackResultAnswerSheet(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrInterviewFeedbackResultAnswerSheet rename(String name) {
        return new HrInterviewFeedbackResultAnswerSheet(name, null);
    }
}
