/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.SysUserRecord;

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
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysUser extends TableImpl<SysUserRecord> {

    private static final long serialVersionUID = -318585173;

    /**
     * The reference instance of <code>analytics.sys_user</code>
     */
    public static final SysUser SYS_USER = new SysUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysUserRecord> getRecordType() {
        return SysUserRecord.class;
    }

    /**
     * The column <code>analytics.sys_user.id</code>.
     */
    public final TableField<SysUserRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.sys_user.username</code>.
     */
    public final TableField<SysUserRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "");

    /**
     * The column <code>analytics.sys_user.password</code>.
     */
    public final TableField<SysUserRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>analytics.sys_user.token</code>.
     */
    public final TableField<SysUserRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * Create a <code>analytics.sys_user</code> table reference
     */
    public SysUser() {
        this("sys_user", null);
    }

    /**
     * Create an aliased <code>analytics.sys_user</code> table reference
     */
    public SysUser(String alias) {
        this(alias, SYS_USER);
    }

    private SysUser(String alias, Table<SysUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private SysUser(String alias, Table<SysUserRecord> aliased, Field<?>[] parameters) {
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
    public Identity<SysUserRecord, Integer> getIdentity() {
        return Keys.IDENTITY_SYS_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SysUserRecord> getPrimaryKey() {
        return Keys.KEY_SYS_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SysUserRecord>> getKeys() {
        return Arrays.<UniqueKey<SysUserRecord>>asList(Keys.KEY_SYS_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysUser as(String alias) {
        return new SysUser(alias, this);
    }

    /**
     * Rename this table
     */
    public SysUser rename(String name) {
        return new SysUser(name, null);
    }
}
