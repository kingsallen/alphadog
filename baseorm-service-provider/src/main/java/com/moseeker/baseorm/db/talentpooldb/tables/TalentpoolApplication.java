/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables;


import com.moseeker.baseorm.db.talentpooldb.Keys;
import com.moseeker.baseorm.db.talentpooldb.Talentpooldb;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolApplicationRecord;

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
 * hr申请人才库记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolApplication extends TableImpl<TalentpoolApplicationRecord> {

    private static final long serialVersionUID = -1515837417;

    /**
     * The reference instance of <code>talentpooldb.talentpool_application</code>
     */
    public static final TalentpoolApplication TALENTPOOL_APPLICATION = new TalentpoolApplication();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TalentpoolApplicationRecord> getRecordType() {
        return TalentpoolApplicationRecord.class;
    }

    /**
     * The column <code>talentpooldb.talentpool_application.hr_id</code>. hr主账号编号
     */
    public final TableField<TalentpoolApplicationRecord, Integer> HR_ID = createField("hr_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr主账号编号");

    /**
     * The column <code>talentpooldb.talentpool_application.company_id</code>. 公司id
     */
    public final TableField<TalentpoolApplicationRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "公司id");

    /**
     * The column <code>talentpooldb.talentpool_application.create_time</code>. 创建时间
     */
    public final TableField<TalentpoolApplicationRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>talentpooldb.talentpool_application.update_time</code>.
     */
    public final TableField<TalentpoolApplicationRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>talentpooldb.talentpool_application.type</code>.  0开启普通人才库，1开启高端人才库
     */
    public final TableField<TalentpoolApplicationRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, " 0开启普通人才库，1开启高端人才库");

    /**
     * Create a <code>talentpooldb.talentpool_application</code> table reference
     */
    public TalentpoolApplication() {
        this("talentpool_application", null);
    }

    /**
     * Create an aliased <code>talentpooldb.talentpool_application</code> table reference
     */
    public TalentpoolApplication(String alias) {
        this(alias, TALENTPOOL_APPLICATION);
    }

    private TalentpoolApplication(String alias, Table<TalentpoolApplicationRecord> aliased) {
        this(alias, aliased, null);
    }

    private TalentpoolApplication(String alias, Table<TalentpoolApplicationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "hr申请人才库记录表");
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
    public UniqueKey<TalentpoolApplicationRecord> getPrimaryKey() {
        return Keys.KEY_TALENTPOOL_APPLICATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TalentpoolApplicationRecord>> getKeys() {
        return Arrays.<UniqueKey<TalentpoolApplicationRecord>>asList(Keys.KEY_TALENTPOOL_APPLICATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolApplication as(String alias) {
        return new TalentpoolApplication(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TalentpoolApplication rename(String name) {
        return new TalentpoolApplication(name, null);
    }
}
