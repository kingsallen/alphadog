/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables;


import com.moseeker.baseorm.db.talentpooldb.Keys;
import com.moseeker.baseorm.db.talentpooldb.Talentpooldb;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolEmailRecord;

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
 * 人才库邮件模板表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolEmail extends TableImpl<TalentpoolEmailRecord> {

    private static final long serialVersionUID = 1523035443;

    /**
     * The reference instance of <code>talentpooldb.talentpool_email</code>
     */
    public static final TalentpoolEmail TALENTPOOL_EMAIL = new TalentpoolEmail();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TalentpoolEmailRecord> getRecordType() {
        return TalentpoolEmailRecord.class;
    }

    /**
     * The column <code>talentpooldb.talentpool_email.id</code>.
     */
    public final TableField<TalentpoolEmailRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>talentpooldb.talentpool_email.disable</code>. 状态 0是不开启 1是开启 2 关闭
     */
    public final TableField<TalentpoolEmailRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "状态 0是不开启 1是开启 2 关闭");

    /**
     * The column <code>talentpooldb.talentpool_email.inscribe</code>. 邮件自定义落款
     */
    public final TableField<TalentpoolEmailRecord, String> INSCRIBE = createField("inscribe", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "邮件自定义落款");

    /**
     * The column <code>talentpooldb.talentpool_email.context</code>. 邮件自定义文案
     */
    public final TableField<TalentpoolEmailRecord, String> CONTEXT = createField("context", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "邮件自定义文案");

    /**
     * The column <code>talentpooldb.talentpool_email.config_id</code>. 邮件配置模板id
     */
    public final TableField<TalentpoolEmailRecord, Integer> CONFIG_ID = createField("config_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "邮件配置模板id");

    /**
     * The column <code>talentpooldb.talentpool_email.create_time</code>.
     */
    public final TableField<TalentpoolEmailRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>talentpooldb.talentpool_email.update_time</code>.
     */
    public final TableField<TalentpoolEmailRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>talentpooldb.talentpool_email.company_id</code>. 公司编号
     */
    public final TableField<TalentpoolEmailRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "公司编号");

    /**
     * Create a <code>talentpooldb.talentpool_email</code> table reference
     */
    public TalentpoolEmail() {
        this("talentpool_email", null);
    }

    /**
     * Create an aliased <code>talentpooldb.talentpool_email</code> table reference
     */
    public TalentpoolEmail(String alias) {
        this(alias, TALENTPOOL_EMAIL);
    }

    private TalentpoolEmail(String alias, Table<TalentpoolEmailRecord> aliased) {
        this(alias, aliased, null);
    }

    private TalentpoolEmail(String alias, Table<TalentpoolEmailRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "人才库邮件模板表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Talentpooldb.TALENTPOOLDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TalentpoolEmailRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TALENTPOOL_EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TalentpoolEmailRecord> getPrimaryKey() {
        return Keys.KEY_TALENTPOOL_EMAIL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TalentpoolEmailRecord>> getKeys() {
        return Arrays.<UniqueKey<TalentpoolEmailRecord>>asList(Keys.KEY_TALENTPOOL_EMAIL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmail as(String alias) {
        return new TalentpoolEmail(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TalentpoolEmail rename(String name) {
        return new TalentpoolEmail(name, null);
    }
}
