/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.tables.records.TmpAppUserRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TmpAppUser extends TableImpl<TmpAppUserRecord> {

    private static final long serialVersionUID = 1987260826;

    /**
     * The reference instance of <code>jobdb.tmp_app_user</code>
     */
    public static final TmpAppUser TMP_APP_USER = new TmpAppUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TmpAppUserRecord> getRecordType() {
        return TmpAppUserRecord.class;
    }

    /**
     * The column <code>jobdb.tmp_app_user.company_id</code>. company.id，公司表ID
     */
    public final TableField<TmpAppUserRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "company.id，公司表ID");

    /**
     * The column <code>jobdb.tmp_app_user.count(id)</code>.
     */
    public final TableField<TmpAppUserRecord, Long> COUNT_28ID_29 = createField("count(id)", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>jobdb.tmp_app_user.count(distinct(applier_id))</code>.
     */
    public final TableField<TmpAppUserRecord, Long> COUNT_28DISTINCT_28APPLIER_ID_29_29 = createField("count(distinct(applier_id))", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * Create a <code>jobdb.tmp_app_user</code> table reference
     */
    public TmpAppUser() {
        this("tmp_app_user", null);
    }

    /**
     * Create an aliased <code>jobdb.tmp_app_user</code> table reference
     */
    public TmpAppUser(String alias) {
        this(alias, TMP_APP_USER);
    }

    private TmpAppUser(String alias, Table<TmpAppUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private TmpAppUser(String alias, Table<TmpAppUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Jobdb.JOBDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TmpAppUser as(String alias) {
        return new TmpAppUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TmpAppUser rename(String name) {
        return new TmpAppUser(name, null);
    }
}
