/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryJobApplicationRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 申请记录归档表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryJobApplication extends TableImpl<HistoryJobApplicationRecord> {

    private static final long serialVersionUID = 1841693902;

    /**
     * The reference instance of <code>historydb.history_job_application</code>
     */
    public static final HistoryJobApplication HISTORY_JOB_APPLICATION = new HistoryJobApplication();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryJobApplicationRecord> getRecordType() {
        return HistoryJobApplicationRecord.class;
    }

    /**
     * The column <code>historydb.history_job_application.id</code>.
     */
    public final TableField<HistoryJobApplicationRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_job_application.position_id</code>. job_position.id, 职位ID
     */
    public final TableField<HistoryJobApplicationRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "job_position.id, 职位ID");

    /**
     * The column <code>historydb.history_job_application.recommender_id</code>. user_wx_user.id, 微信ID
     */
    public final TableField<HistoryJobApplicationRecord, Integer> RECOMMENDER_ID = createField("recommender_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "user_wx_user.id, 微信ID");

    /**
     * The column <code>historydb.history_job_application.l_application_id</code>. 对应他系统ATS申请的ID
     */
    public final TableField<HistoryJobApplicationRecord, Integer> L_APPLICATION_ID = createField("l_application_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "对应他系统ATS申请的ID");

    /**
     * The column <code>historydb.history_job_application.user_id</code>. 用户ID， user_user.id
     */
    public final TableField<HistoryJobApplicationRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户ID， user_user.id");

    /**
     * The column <code>historydb.history_job_application.ats_status</code>. ATS处理状态，0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified
     */
    public final TableField<HistoryJobApplicationRecord, Integer> ATS_STATUS = createField("ats_status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "ATS处理状态，0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified");

    /**
     * The column <code>historydb.history_job_application.disable</code>. 是否有效，0：有效，1：无效
     */
    public final TableField<HistoryJobApplicationRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否有效，0：有效，1：无效");

    /**
     * The column <code>historydb.history_job_application.routine</code>. 申请来源 0:微信企业端 1:微信聚合端 10:pc端
     */
    public final TableField<HistoryJobApplicationRecord, Integer> ROUTINE = createField("routine", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "申请来源 0:微信企业端 1:微信聚合端 10:pc端");

    /**
     * The column <code>historydb.history_job_application.is_viewed</code>. 该申请是否被浏览，0：已浏览，1：未浏览
     */
    public final TableField<HistoryJobApplicationRecord, Byte> IS_VIEWED = createField("is_viewed", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "该申请是否被浏览，0：已浏览，1：未浏览");

    /**
     * The column <code>historydb.history_job_application.view_count</code>. profile浏览次数
     */
    public final TableField<HistoryJobApplicationRecord, Integer> VIEW_COUNT = createField("view_count", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "profile浏览次数");

    /**
     * The column <code>historydb.history_job_application.not_suitable</code>. 是否不合适，0：合适，1：不合适
     */
    public final TableField<HistoryJobApplicationRecord, Byte> NOT_SUITABLE = createField("not_suitable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否不合适，0：合适，1：不合适");

    /**
     * The column <code>historydb.history_job_application.company_id</code>. 母公司 hr_company.id，公司表ID
     */
    public final TableField<HistoryJobApplicationRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "母公司 hr_company.id，公司表ID");

    /**
     * The column <code>historydb.history_job_application.app_tpl_id</code>. 申请状态, config_sys_points_conf_tpl.id
     */
    public final TableField<HistoryJobApplicationRecord, Integer> APP_TPL_ID = createField("app_tpl_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "申请状态, config_sys_points_conf_tpl.id");

    /**
     * The column <code>historydb.history_job_application.proxy</code>. 是否是仟寻代投 0：否，1：是
     */
    public final TableField<HistoryJobApplicationRecord, Byte> PROXY = createField("proxy", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否是仟寻代投 0：否，1：是");

    /**
     * The column <code>historydb.history_job_application.apply_type</code>. 投递区分， 0：profile投递， 1：email投递
     */
    public final TableField<HistoryJobApplicationRecord, Integer> APPLY_TYPE = createField("apply_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "投递区分， 0：profile投递， 1：email投递");

    /**
     * The column <code>historydb.history_job_application.email_status</code>. Email投递状态， 0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；8，特殊字符；9，提取邮件失败
     */
    public final TableField<HistoryJobApplicationRecord, Integer> EMAIL_STATUS = createField("email_status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "Email投递状态， 0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；8，特殊字符；9，提取邮件失败");

    /**
     * The column <code>historydb.history_job_application.submit_time</code>. 申请提交时间
     */
    public final TableField<HistoryJobApplicationRecord, Timestamp> SUBMIT_TIME = createField("submit_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "申请提交时间");

    /**
     * The column <code>historydb.history_job_application.create_time</code>. 创建时间
     */
    public final TableField<HistoryJobApplicationRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>historydb.history_job_application.update_time</code>. 更新时间
     */
    public final TableField<HistoryJobApplicationRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>historydb.history_job_application</code> table reference
     */
    public HistoryJobApplication() {
        this("history_job_application", null);
    }

    /**
     * Create an aliased <code>historydb.history_job_application</code> table reference
     */
    public HistoryJobApplication(String alias) {
        this(alias, HISTORY_JOB_APPLICATION);
    }

    private HistoryJobApplication(String alias, Table<HistoryJobApplicationRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryJobApplication(String alias, Table<HistoryJobApplicationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "申请记录归档表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Historydb.HISTORYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryJobApplicationRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_JOB_APPLICATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryJobApplicationRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryJobApplicationRecord>>asList(Keys.KEY_HISTORY_JOB_APPLICATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryJobApplication as(String alias) {
        return new HistoryJobApplication(alias, this);
    }

    /**
     * Rename this table
     */
    public HistoryJobApplication rename(String name) {
        return new HistoryJobApplication(name, null);
    }
}
