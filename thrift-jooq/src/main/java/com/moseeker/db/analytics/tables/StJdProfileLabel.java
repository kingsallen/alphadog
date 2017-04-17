/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.analytics.tables;


import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.Keys;
import com.moseeker.db.analytics.tables.records.StJdProfileLabelRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StJdProfileLabel extends TableImpl<StJdProfileLabelRecord> {

    private static final long serialVersionUID = -578244726;

    /**
     * The reference instance of <code>analytics.st_jd_profile_label</code>
     */
    public static final StJdProfileLabel ST_JD_PROFILE_LABEL = new StJdProfileLabel();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StJdProfileLabelRecord> getRecordType() {
        return StJdProfileLabelRecord.class;
    }

    /**
     * The column <code>analytics.st_jd_profile_label.id</code>.
     */
    public final TableField<StJdProfileLabelRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.st_jd_profile_label.jd_id</code>.
     */
    public final TableField<StJdProfileLabelRecord, Integer> JD_ID = createField("jd_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.st_jd_profile_label.profile_id</code>.
     */
    public final TableField<StJdProfileLabelRecord, Integer> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.st_jd_profile_label.is_matched</code>.
     */
    public final TableField<StJdProfileLabelRecord, Byte> IS_MATCHED = createField("is_matched", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>analytics.st_jd_profile_label.is_good_profile</code>.
     */
    public final TableField<StJdProfileLabelRecord, Byte> IS_GOOD_PROFILE = createField("is_good_profile", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>analytics.st_jd_profile_label.is_good_jd</code>.
     */
    public final TableField<StJdProfileLabelRecord, Byte> IS_GOOD_JD = createField("is_good_jd", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * Create a <code>analytics.st_jd_profile_label</code> table reference
     */
    public StJdProfileLabel() {
        this("st_jd_profile_label", null);
    }

    /**
     * Create an aliased <code>analytics.st_jd_profile_label</code> table reference
     */
    public StJdProfileLabel(String alias) {
        this(alias, ST_JD_PROFILE_LABEL);
    }

    private StJdProfileLabel(String alias, Table<StJdProfileLabelRecord> aliased) {
        this(alias, aliased, null);
    }

    private StJdProfileLabel(String alias, Table<StJdProfileLabelRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Analytics.ANALYTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StJdProfileLabelRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_JD_PROFILE_LABEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StJdProfileLabelRecord> getPrimaryKey() {
        return Keys.KEY_ST_JD_PROFILE_LABEL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StJdProfileLabelRecord>> getKeys() {
        return Arrays.<UniqueKey<StJdProfileLabelRecord>>asList(Keys.KEY_ST_JD_PROFILE_LABEL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StJdProfileLabel as(String alias) {
        return new StJdProfileLabel(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StJdProfileLabel rename(String name) {
        return new StJdProfileLabel(name, null);
    }
}
