/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyWorkwxConfRecord;

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
 * 企业微信配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyWorkwxConf extends TableImpl<HrCompanyWorkwxConfRecord> {

    private static final long serialVersionUID = 1909319917;

    /**
     * The reference instance of <code>hrdb.hr_company_workwx_conf</code>
     */
    public static final HrCompanyWorkwxConf HR_COMPANY_WORKWX_CONF = new HrCompanyWorkwxConf();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrCompanyWorkwxConfRecord> getRecordType() {
        return HrCompanyWorkwxConfRecord.class;
    }

    /**
     * The column <code>hrdb.hr_company_workwx_conf.id</code>.
     */
    public final TableField<HrCompanyWorkwxConfRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.company_id</code>. 关联hr_company.id
     */
    public final TableField<HrCompanyWorkwxConfRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "关联hr_company.id");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.corpid</code>. 企业微信corpid，来源于企业微信后台配置
     */
    public final TableField<HrCompanyWorkwxConfRecord, String> CORPID = createField("corpid", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false), this, "企业微信corpid，来源于企业微信后台配置");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.secret</code>. 企业微信corpsecret，来源于企业微信后台配置
     */
    public final TableField<HrCompanyWorkwxConfRecord, String> SECRET = createField("secret", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false), this, "企业微信corpsecret，来源于企业微信后台配置");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.access_token</code>. access_token
     */
    public final TableField<HrCompanyWorkwxConfRecord, String> ACCESS_TOKEN = createField("access_token", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "access_token");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.token_update_time</code>. access_token最近获取时间
     */
    public final TableField<HrCompanyWorkwxConfRecord, Timestamp> TOKEN_UPDATE_TIME = createField("token_update_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "access_token最近获取时间");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.token_expire_time</code>. access_token过期时间
     */
    public final TableField<HrCompanyWorkwxConfRecord, Timestamp> TOKEN_EXPIRE_TIME = createField("token_expire_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "access_token过期时间");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.error_code</code>. 获取token失败
     */
    public final TableField<HrCompanyWorkwxConfRecord, Integer> ERROR_CODE = createField("error_code", org.jooq.impl.SQLDataType.INTEGER, this, "获取token失败");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.error_msg</code>. 获取token失败
     */
    public final TableField<HrCompanyWorkwxConfRecord, String> ERROR_MSG = createField("error_msg", org.jooq.impl.SQLDataType.CLOB, this, "获取token失败");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.create_time</code>.
     */
    public final TableField<HrCompanyWorkwxConfRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.update_time</code>.
     */
    public final TableField<HrCompanyWorkwxConfRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.jsapi_ticket</code>. H5应用调用企业微信JS接口的临时票据
     */
    public final TableField<HrCompanyWorkwxConfRecord, String> JSAPI_TICKET = createField("jsapi_ticket", org.jooq.impl.SQLDataType.VARCHAR.length(600), this, "H5应用调用企业微信JS接口的临时票据");

    /**
     * The column <code>hrdb.hr_company_workwx_conf.disable</code>. 是否有效 1:无效 0：有效
     */
    public final TableField<HrCompanyWorkwxConfRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否有效 1:无效 0：有效");

    /**
     * Create a <code>hrdb.hr_company_workwx_conf</code> table reference
     */
    public HrCompanyWorkwxConf() {
        this("hr_company_workwx_conf", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_company_workwx_conf</code> table reference
     */
    public HrCompanyWorkwxConf(String alias) {
        this(alias, HR_COMPANY_WORKWX_CONF);
    }

    private HrCompanyWorkwxConf(String alias, Table<HrCompanyWorkwxConfRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrCompanyWorkwxConf(String alias, Table<HrCompanyWorkwxConfRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "企业微信配置");
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
    public Identity<HrCompanyWorkwxConfRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_COMPANY_WORKWX_CONF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrCompanyWorkwxConfRecord> getPrimaryKey() {
        return Keys.KEY_HR_COMPANY_WORKWX_CONF_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrCompanyWorkwxConfRecord>> getKeys() {
        return Arrays.<UniqueKey<HrCompanyWorkwxConfRecord>>asList(Keys.KEY_HR_COMPANY_WORKWX_CONF_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyWorkwxConf as(String alias) {
        return new HrCompanyWorkwxConf(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrCompanyWorkwxConf rename(String name) {
        return new HrCompanyWorkwxConf(name, null);
    }
}
