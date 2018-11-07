/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HrAppCvConf180726Record;

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
 * 企业申请简历校验配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAppCvConf180726 extends TableImpl<HrAppCvConf180726Record> {

    private static final long serialVersionUID = -332506471;

    /**
     * The reference instance of <code>historydb.hr_app_cv_conf180726</code>
     */
    public static final HrAppCvConf180726 HR_APP_CV_CONF180726 = new HrAppCvConf180726();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrAppCvConf180726Record> getRecordType() {
        return HrAppCvConf180726Record.class;
    }

    /**
     * The column <code>historydb.hr_app_cv_conf180726.id</code>.
     */
    public final TableField<HrAppCvConf180726Record, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.name</code>. 属性含义
     */
    public final TableField<HrAppCvConf180726Record, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "属性含义");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.priority</code>. 排序字段
     */
    public final TableField<HrAppCvConf180726Record, Integer> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "排序字段");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.create_time</code>.
     */
    public final TableField<HrAppCvConf180726Record, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.update_time</code>.
     */
    public final TableField<HrAppCvConf180726Record, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.disable</code>. 是否禁用 0：可用1：不可用
     */
    public final TableField<HrAppCvConf180726Record, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否禁用 0：可用1：不可用");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.company_id</code>. 所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id
     */
    public final TableField<HrAppCvConf180726Record, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.hraccount_id</code>. 账号编号 hr_account.id
     */
    public final TableField<HrAppCvConf180726Record, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "账号编号 hr_account.id");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.required</code>. 是否必填项 0：必填项 1：选填项
     */
    public final TableField<HrAppCvConf180726Record, Integer> REQUIRED = createField("required", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否必填项 0：必填项 1：选填项");

    /**
     * The column <code>historydb.hr_app_cv_conf180726.field_value</code>. 微信端页面标签默认值
     */
    public final TableField<HrAppCvConf180726Record, String> FIELD_VALUE = createField("field_value", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "微信端页面标签默认值");

    /**
     * Create a <code>historydb.hr_app_cv_conf180726</code> table reference
     */
    public HrAppCvConf180726() {
        this("hr_app_cv_conf180726", null);
    }

    /**
     * Create an aliased <code>historydb.hr_app_cv_conf180726</code> table reference
     */
    public HrAppCvConf180726(String alias) {
        this(alias, HR_APP_CV_CONF180726);
    }

    private HrAppCvConf180726(String alias, Table<HrAppCvConf180726Record> aliased) {
        this(alias, aliased, null);
    }

    private HrAppCvConf180726(String alias, Table<HrAppCvConf180726Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "企业申请简历校验配置");
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
    public Identity<HrAppCvConf180726Record, Integer> getIdentity() {
        return Keys.IDENTITY_HR_APP_CV_CONF180726;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrAppCvConf180726Record> getPrimaryKey() {
        return Keys.KEY_HR_APP_CV_CONF180726_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrAppCvConf180726Record>> getKeys() {
        return Arrays.<UniqueKey<HrAppCvConf180726Record>>asList(Keys.KEY_HR_APP_CV_CONF180726_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConf180726 as(String alias) {
        return new HrAppCvConf180726(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrAppCvConf180726 rename(String name) {
        return new HrAppCvConf180726(name, null);
    }
}