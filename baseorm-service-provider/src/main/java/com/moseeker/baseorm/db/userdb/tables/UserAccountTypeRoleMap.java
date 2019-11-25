/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserAccountTypeRoleMapRecord;

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
 * 账号类型-角色映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAccountTypeRoleMap extends TableImpl<UserAccountTypeRoleMapRecord> {

    private static final long serialVersionUID = -1957487643;

    /**
     * The reference instance of <code>userdb.user_account_type_role_map</code>
     */
    public static final UserAccountTypeRoleMap USER_ACCOUNT_TYPE_ROLE_MAP = new UserAccountTypeRoleMap();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserAccountTypeRoleMapRecord> getRecordType() {
        return UserAccountTypeRoleMapRecord.class;
    }

    /**
     * The column <code>userdb.user_account_type_role_map.id</code>.
     */
    public final TableField<UserAccountTypeRoleMapRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_account_type_role_map.account_type</code>. 账号类型 0： 超级账号；1：子账号; 2：普通账号 3:面试官 4:招聘负责人 5:高级用人经理  6:用人经理
     */
    public final TableField<UserAccountTypeRoleMapRecord, Integer> ACCOUNT_TYPE = createField("account_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "账号类型 0： 超级账号；1：子账号; 2：普通账号 3:面试官 4:招聘负责人 5:高级用人经理  6:用人经理");

    /**
     * The column <code>userdb.user_account_type_role_map.role_type</code>. 角色类型 1:超级管理员 2:管理员 3:招聘负责人 4:高级用人经理  5:用人经理 6:面试官
     */
    public final TableField<UserAccountTypeRoleMapRecord, Byte> ROLE_TYPE = createField("role_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "角色类型 1:超级管理员 2:管理员 3:招聘负责人 4:高级用人经理  5:用人经理 6:面试官");

    /**
     * Create a <code>userdb.user_account_type_role_map</code> table reference
     */
    public UserAccountTypeRoleMap() {
        this("user_account_type_role_map", null);
    }

    /**
     * Create an aliased <code>userdb.user_account_type_role_map</code> table reference
     */
    public UserAccountTypeRoleMap(String alias) {
        this(alias, USER_ACCOUNT_TYPE_ROLE_MAP);
    }

    private UserAccountTypeRoleMap(String alias, Table<UserAccountTypeRoleMapRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserAccountTypeRoleMap(String alias, Table<UserAccountTypeRoleMapRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "账号类型-角色映射表");
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
    public Identity<UserAccountTypeRoleMapRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_ACCOUNT_TYPE_ROLE_MAP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserAccountTypeRoleMapRecord> getPrimaryKey() {
        return Keys.KEY_USER_ACCOUNT_TYPE_ROLE_MAP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserAccountTypeRoleMapRecord>> getKeys() {
        return Arrays.<UniqueKey<UserAccountTypeRoleMapRecord>>asList(Keys.KEY_USER_ACCOUNT_TYPE_ROLE_MAP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAccountTypeRoleMap as(String alias) {
        return new UserAccountTypeRoleMap(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAccountTypeRoleMap rename(String name) {
        return new UserAccountTypeRoleMap(name, null);
    }
}
