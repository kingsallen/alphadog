/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppCvConfRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 企业申请简历校验配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAppCvConf extends TableImpl<HrAppCvConfRecord> {

    private static final long serialVersionUID = 993909306;

    /**
     * The reference instance of <code>hrdb.hr_app_cv_conf</code>
     */
    public static final HrAppCvConf HR_APP_CV_CONF = new HrAppCvConf();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrAppCvConfRecord> getRecordType() {
        return HrAppCvConfRecord.class;
    }

    /**
     * The column <code>hrdb.hr_app_cv_conf.id</code>.
     */
    public final TableField<HrAppCvConfRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_app_cv_conf.name</code>. 属性含义
     */
    public final TableField<HrAppCvConfRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "属性含义");

    /**
     * The column <code>hrdb.hr_app_cv_conf.priority</code>. 排序字段
     */
    public final TableField<HrAppCvConfRecord, Integer> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "排序字段");

    /**
     * The column <code>hrdb.hr_app_cv_conf.create_time</code>.
     */
    public final TableField<HrAppCvConfRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_app_cv_conf.update_time</code>.
     */
    public final TableField<HrAppCvConfRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_app_cv_conf.disable</code>. 是否禁用 0：可用1：不可用
     */
    public final TableField<HrAppCvConfRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否禁用 0：可用1：不可用");

    /**
     * The column <code>hrdb.hr_app_cv_conf.company_id</code>. 所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id
     */
    public final TableField<HrAppCvConfRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id");

    /**
     * The column <code>hrdb.hr_app_cv_conf.hraccount_id</code>. 账号编号 hr_account.id
     */
    public final TableField<HrAppCvConfRecord, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "账号编号 hr_account.id");

    /**
     * The column <code>hrdb.hr_app_cv_conf.required</code>. 是否必填项 0：必填项 1：选填项
     */
    public final TableField<HrAppCvConfRecord, Integer> REQUIRED = createField("required", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否必填项 0：必填项 1：选填项");

    /**
     * The column <code>hrdb.hr_app_cv_conf.field_value</code>. 微信端页面标签默认值
     */
    public final TableField<HrAppCvConfRecord, String> FIELD_VALUE = createField("field_value", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "微信端页面标签默认值");

    /**
     * Create a <code>hrdb.hr_app_cv_conf</code> table reference
     */
    public HrAppCvConf() {
        this("hr_app_cv_conf", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_app_cv_conf</code> table reference
     */
    public HrAppCvConf(String alias) {
        this(alias, HR_APP_CV_CONF);
    }

    private HrAppCvConf(String alias, Table<HrAppCvConfRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrAppCvConf(String alias, Table<HrAppCvConfRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "企业申请简历校验配置");
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
    public Identity<HrAppCvConfRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_APP_CV_CONF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrAppCvConfRecord> getPrimaryKey() {
        return Keys.KEY_HR_APP_CV_CONF_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrAppCvConfRecord>> getKeys() {
        return Arrays.<UniqueKey<HrAppCvConfRecord>>asList(Keys.KEY_HR_APP_CV_CONF_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConf as(String alias) {
        return new HrAppCvConf(alias, this);
    }

    /**
     * Rename this table
     */
    public HrAppCvConf rename(String name) {
        return new HrAppCvConf(name, null);
    }
}
