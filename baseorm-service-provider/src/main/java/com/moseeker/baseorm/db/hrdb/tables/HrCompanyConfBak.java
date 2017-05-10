/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfBakRecord;

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
 * 公司级别的配置信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyConfBak extends TableImpl<HrCompanyConfBakRecord> {

    private static final long serialVersionUID = -1851659243;

    /**
     * The reference instance of <code>hrdb.hr_company_conf_bak</code>
     */
    public static final HrCompanyConfBak HR_COMPANY_CONF_BAK = new HrCompanyConfBak();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrCompanyConfBakRecord> getRecordType() {
        return HrCompanyConfBakRecord.class;
    }

    /**
     * The column <code>hrdb.hr_company_conf_bak.company_id</code>.
     */
    public final TableField<HrCompanyConfBakRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_company_conf_bak.theme_id</code>. sys_theme id
     */
    public final TableField<HrCompanyConfBakRecord, Integer> THEME_ID = createField("theme_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sys_theme id");

    /**
     * The column <code>hrdb.hr_company_conf_bak.hb_throttle</code>. 全局每人每次红包活动可以获得的红包金额上限
     */
    public final TableField<HrCompanyConfBakRecord, Integer> HB_THROTTLE = createField("hb_throttle", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("10", org.jooq.impl.SQLDataType.INTEGER)), this, "全局每人每次红包活动可以获得的红包金额上限");

    /**
     * The column <code>hrdb.hr_company_conf_bak.app_reply</code>. 申请提交成功回复信息
     */
    public final TableField<HrCompanyConfBakRecord, String> APP_REPLY = createField("app_reply", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "申请提交成功回复信息");

    /**
     * The column <code>hrdb.hr_company_conf_bak.create_time</code>. 创建时间
     */
    public final TableField<HrCompanyConfBakRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_company_conf_bak.update_time</code>. 更新时间
     */
    public final TableField<HrCompanyConfBakRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>hrdb.hr_company_conf_bak.employee_binding</code>. 员工认证自定义文案
     */
    public final TableField<HrCompanyConfBakRecord, String> EMPLOYEE_BINDING = createField("employee_binding", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "员工认证自定义文案");

    /**
     * The column <code>hrdb.hr_company_conf_bak.recommend_presentee</code>. 推荐候选人自定义文案
     */
    public final TableField<HrCompanyConfBakRecord, String> RECOMMEND_PRESENTEE = createField("recommend_presentee", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐候选人自定义文案");

    /**
     * The column <code>hrdb.hr_company_conf_bak.recommend_success</code>. 推荐成功自定义文案
     */
    public final TableField<HrCompanyConfBakRecord, String> RECOMMEND_SUCCESS = createField("recommend_success", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐成功自定义文案");

    /**
     * The column <code>hrdb.hr_company_conf_bak.forward_message</code>. 转发职位自定义文案
     */
    public final TableField<HrCompanyConfBakRecord, String> FORWARD_MESSAGE = createField("forward_message", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "转发职位自定义文案");

    /**
     * The column <code>hrdb.hr_company_conf_bak.application_count_limit</code>. 一个人在一个公司下每月申请次数限制
     */
    public final TableField<HrCompanyConfBakRecord, Short> APPLICATION_COUNT_LIMIT = createField("application_count_limit", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("3", org.jooq.impl.SQLDataType.SMALLINT)), this, "一个人在一个公司下每月申请次数限制");

    /**
     * The column <code>hrdb.hr_company_conf_bak.job_occupation</code>. 自定义字段名称
     */
    public final TableField<HrCompanyConfBakRecord, String> JOB_OCCUPATION = createField("job_occupation", org.jooq.impl.SQLDataType.VARCHAR.length(30), this, "自定义字段名称");

    /**
     * The column <code>hrdb.hr_company_conf_bak.job_custom_title</code>. 职位自定义字段标题
     */
    public final TableField<HrCompanyConfBakRecord, String> JOB_CUSTOM_TITLE = createField("job_custom_title", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职位自定义字段标题");

    /**
     * The column <code>hrdb.hr_company_conf_bak.teamname_custom</code>. 自定义部门别名
     */
    public final TableField<HrCompanyConfBakRecord, String> TEAMNAME_CUSTOM = createField("teamname_custom", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定义部门别名");

    /**
     * The column <code>hrdb.hr_company_conf_bak.newjd_status</code>. 新jd页去设置状态 0是为开启，1是用户开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0
     */
    public final TableField<HrCompanyConfBakRecord, Integer> NEWJD_STATUS = createField("newjd_status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "新jd页去设置状态 0是为开启，1是用户开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0");

    /**
     * The column <code>hrdb.hr_company_conf_bak.application_time</code>. newjd_status即新的jd页的生效时间，
     */
    public final TableField<HrCompanyConfBakRecord, Timestamp> APPLICATION_TIME = createField("application_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "newjd_status即新的jd页的生效时间，");

    /**
     * The column <code>hrdb.hr_company_conf_bak.hr_chat</code>. IM聊天开关，0：不开启，1：开启
     */
    public final TableField<HrCompanyConfBakRecord, Byte> HR_CHAT = createField("hr_chat", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "IM聊天开关，0：不开启，1：开启");

    /**
     * The column <code>hrdb.hr_company_conf_bak.show_qx_only</code>. 公司信息、团队信息、职位信息等只在仟寻展示，0: 否， 1: 是
     */
    public final TableField<HrCompanyConfBakRecord, Byte> SHOW_QX_ONLY = createField("show_qx_only", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "公司信息、团队信息、职位信息等只在仟寻展示，0: 否， 1: 是");

    /**
     * Create a <code>hrdb.hr_company_conf_bak</code> table reference
     */
    public HrCompanyConfBak() {
        this("hr_company_conf_bak", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_company_conf_bak</code> table reference
     */
    public HrCompanyConfBak(String alias) {
        this(alias, HR_COMPANY_CONF_BAK);
    }

    private HrCompanyConfBak(String alias, Table<HrCompanyConfBakRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrCompanyConfBak(String alias, Table<HrCompanyConfBakRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "公司级别的配置信息表");
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
    public UniqueKey<HrCompanyConfBakRecord> getPrimaryKey() {
        return Keys.KEY_HR_COMPANY_CONF_BAK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrCompanyConfBakRecord>> getKeys() {
        return Arrays.<UniqueKey<HrCompanyConfBakRecord>>asList(Keys.KEY_HR_COMPANY_CONF_BAK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfBak as(String alias) {
        return new HrCompanyConfBak(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrCompanyConfBak rename(String name) {
        return new HrCompanyConfBak(name, null);
    }
}
