/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StPhoneRecord;

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
 * 记录留手机用户信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StPhone extends TableImpl<StPhoneRecord> {

    private static final long serialVersionUID = -272461120;

    /**
     * The reference instance of <code>analytics.st_phone</code>
     */
    public static final StPhone ST_PHONE = new StPhone();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StPhoneRecord> getRecordType() {
        return StPhoneRecord.class;
    }

    /**
     * The column <code>analytics.st_phone.id</code>.
     */
    public final TableField<StPhoneRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.st_phone.create_time</code>.
     */
    public final TableField<StPhoneRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>analytics.st_phone.pid</code>.
     */
    public final TableField<StPhoneRecord, Integer> PID = createField("pid", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.st_phone.recom</code>. 转发人id，对应wx_group_user的id
     */
    public final TableField<StPhoneRecord, Integer> RECOM = createField("recom", org.jooq.impl.SQLDataType.INTEGER, this, "转发人id，对应wx_group_user的id");

    /**
     * The column <code>analytics.st_phone.viewer_id</code>.
     */
    public final TableField<StPhoneRecord, String> VIEWER_ID = createField("viewer_id", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>analytics.st_phone.open_id</code>.
     */
    public final TableField<StPhoneRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * Create a <code>analytics.st_phone</code> table reference
     */
    public StPhone() {
        this("st_phone", null);
    }

    /**
     * Create an aliased <code>analytics.st_phone</code> table reference
     */
    public StPhone(String alias) {
        this(alias, ST_PHONE);
    }

    private StPhone(String alias, Table<StPhoneRecord> aliased) {
        this(alias, aliased, null);
    }

    private StPhone(String alias, Table<StPhoneRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "记录留手机用户信息");
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
    public Identity<StPhoneRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StPhoneRecord> getPrimaryKey() {
        return Keys.KEY_ST_PHONE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StPhoneRecord>> getKeys() {
        return Arrays.<UniqueKey<StPhoneRecord>>asList(Keys.KEY_ST_PHONE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StPhone as(String alias) {
        return new StPhone(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StPhone rename(String name) {
        return new StPhone(name, null);
    }
}
