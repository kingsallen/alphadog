/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrRolePermRecord;

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
 * 角色的操作权限
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserHrRolePerm extends TableImpl<UserHrRolePermRecord> {

    private static final long serialVersionUID = -188346294;

    /**
     * The reference instance of <code>userdb.user_hr_role_perm</code>
     */
    public static final UserHrRolePerm USER_HR_ROLE_PERM = new UserHrRolePerm();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserHrRolePermRecord> getRecordType() {
        return UserHrRolePermRecord.class;
    }

    /**
     * The column <code>userdb.user_hr_role_perm.id</code>.
     */
    public final TableField<UserHrRolePermRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_hr_role_perm.role_id</code>. 角色id
     */
    public final TableField<UserHrRolePermRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "角色id");

    /**
     * The column <code>userdb.user_hr_role_perm.perm_id</code>. 权限id
     */
    public final TableField<UserHrRolePermRecord, Integer> PERM_ID = createField("perm_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "权限id");

    /**
     * The column <code>userdb.user_hr_role_perm.create_time</code>. 创建时间
     */
    public final TableField<UserHrRolePermRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_hr_role_perm.creator</code>. 创建人
     */
    public final TableField<UserHrRolePermRecord, String> CREATOR = createField("creator", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "创建人");

    /**
     * The column <code>userdb.user_hr_role_perm.update_time</code>. 修改时间
     */
    public final TableField<UserHrRolePermRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>userdb.user_hr_role_perm.updator</code>. 修改人
     */
    public final TableField<UserHrRolePermRecord, String> UPDATOR = createField("updator", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "修改人");

    /**
     * Create a <code>userdb.user_hr_role_perm</code> table reference
     */
    public UserHrRolePerm() {
        this("user_hr_role_perm", null);
    }

    /**
     * Create an aliased <code>userdb.user_hr_role_perm</code> table reference
     */
    public UserHrRolePerm(String alias) {
        this(alias, USER_HR_ROLE_PERM);
    }

    private UserHrRolePerm(String alias, Table<UserHrRolePermRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserHrRolePerm(String alias, Table<UserHrRolePermRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "角色的操作权限");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserHrRolePermRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_HR_ROLE_PERM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserHrRolePermRecord> getPrimaryKey() {
        return Keys.KEY_USER_HR_ROLE_PERM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserHrRolePermRecord>> getKeys() {
        return Arrays.<UniqueKey<UserHrRolePermRecord>>asList(Keys.KEY_USER_HR_ROLE_PERM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserHrRolePerm as(String alias) {
        return new UserHrRolePerm(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserHrRolePerm rename(String name) {
        return new UserHrRolePerm(name, null);
    }
}
