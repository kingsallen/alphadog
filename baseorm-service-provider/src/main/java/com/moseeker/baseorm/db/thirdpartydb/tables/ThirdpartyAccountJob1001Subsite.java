/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.thirdpartydb.tables;


import com.moseeker.baseorm.db.thirdpartydb.Keys;
import com.moseeker.baseorm.db.thirdpartydb.Thirdpartydb;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountJob1001SubsiteRecord;

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
 * 一览人才的第三方发布网站表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ThirdpartyAccountJob1001Subsite extends TableImpl<ThirdpartyAccountJob1001SubsiteRecord> {

    private static final long serialVersionUID = 914074908;

    /**
     * The reference instance of <code>thirdpartydb.thirdparty_account_job1001_subsite</code>
     */
    public static final ThirdpartyAccountJob1001Subsite THIRDPARTY_ACCOUNT_JOB1001_SUBSITE = new ThirdpartyAccountJob1001Subsite();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ThirdpartyAccountJob1001SubsiteRecord> getRecordType() {
        return ThirdpartyAccountJob1001SubsiteRecord.class;
    }

    /**
     * The column <code>thirdpartydb.thirdparty_account_job1001_subsite.id</code>. 主键
     */
    public final TableField<ThirdpartyAccountJob1001SubsiteRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主键");

    /**
     * The column <code>thirdpartydb.thirdparty_account_job1001_subsite.account_id</code>. 第三方渠道账号的编号
     */
    public final TableField<ThirdpartyAccountJob1001SubsiteRecord, Integer> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "第三方渠道账号的编号");

    /**
     * The column <code>thirdpartydb.thirdparty_account_job1001_subsite.text</code>. 第三方账号对应的发布网站名称
     */
    public final TableField<ThirdpartyAccountJob1001SubsiteRecord, String> TEXT = createField("text", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "第三方账号对应的发布网站名称");

    /**
     * The column <code>thirdpartydb.thirdparty_account_job1001_subsite.site</code>. 第三方账号对应的发布网站网址(暂时弃用)
     */
    public final TableField<ThirdpartyAccountJob1001SubsiteRecord, String> SITE = createField("site", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "第三方账号对应的发布网站网址(暂时弃用)");

    /**
     * The column <code>thirdpartydb.thirdparty_account_job1001_subsite.create_time</code>. 创建时间
     */
    public final TableField<ThirdpartyAccountJob1001SubsiteRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>thirdpartydb.thirdparty_account_job1001_subsite.update_time</code>. 更新时间
     */
    public final TableField<ThirdpartyAccountJob1001SubsiteRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>thirdpartydb.thirdparty_account_job1001_subsite</code> table reference
     */
    public ThirdpartyAccountJob1001Subsite() {
        this("thirdparty_account_job1001_subsite", null);
    }

    /**
     * Create an aliased <code>thirdpartydb.thirdparty_account_job1001_subsite</code> table reference
     */
    public ThirdpartyAccountJob1001Subsite(String alias) {
        this(alias, THIRDPARTY_ACCOUNT_JOB1001_SUBSITE);
    }

    private ThirdpartyAccountJob1001Subsite(String alias, Table<ThirdpartyAccountJob1001SubsiteRecord> aliased) {
        this(alias, aliased, null);
    }

    private ThirdpartyAccountJob1001Subsite(String alias, Table<ThirdpartyAccountJob1001SubsiteRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "一览人才的第三方发布网站表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Thirdpartydb.THIRDPARTYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ThirdpartyAccountJob1001SubsiteRecord, Integer> getIdentity() {
        return Keys.IDENTITY_THIRDPARTY_ACCOUNT_JOB1001_SUBSITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ThirdpartyAccountJob1001SubsiteRecord> getPrimaryKey() {
        return Keys.KEY_THIRDPARTY_ACCOUNT_JOB1001_SUBSITE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ThirdpartyAccountJob1001SubsiteRecord>> getKeys() {
        return Arrays.<UniqueKey<ThirdpartyAccountJob1001SubsiteRecord>>asList(Keys.KEY_THIRDPARTY_ACCOUNT_JOB1001_SUBSITE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001Subsite as(String alias) {
        return new ThirdpartyAccountJob1001Subsite(alias, this);
    }

    /**
     * Rename this table
     */
    public ThirdpartyAccountJob1001Subsite rename(String name) {
        return new ThirdpartyAccountJob1001Subsite(name, null);
    }
}
