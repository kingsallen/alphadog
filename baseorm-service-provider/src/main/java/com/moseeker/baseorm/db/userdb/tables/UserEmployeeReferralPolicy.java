/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeReferralPolicyRecord;

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
 * 员工想要了解内推政策点击次数表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeeReferralPolicy extends TableImpl<UserEmployeeReferralPolicyRecord> {

    private static final long serialVersionUID = -1680606998;

    /**
     * The reference instance of <code>userdb.user_employee_referral_policy</code>
     */
    public static final UserEmployeeReferralPolicy USER_EMPLOYEE_REFERRAL_POLICY = new UserEmployeeReferralPolicy();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserEmployeeReferralPolicyRecord> getRecordType() {
        return UserEmployeeReferralPolicyRecord.class;
    }

    /**
     * The column <code>userdb.user_employee_referral_policy.id</code>.
     */
    public final TableField<UserEmployeeReferralPolicyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_employee_referral_policy.employee_id</code>. user_employee.id
     */
    public final TableField<UserEmployeeReferralPolicyRecord, Integer> EMPLOYEE_ID = createField("employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "user_employee.id");

    /**
     * The column <code>userdb.user_employee_referral_policy.count</code>. 想要了解内推政策点击次数
     */
    public final TableField<UserEmployeeReferralPolicyRecord, Integer> COUNT = createField("count", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "想要了解内推政策点击次数");

    /**
     * Create a <code>userdb.user_employee_referral_policy</code> table reference
     */
    public UserEmployeeReferralPolicy() {
        this("user_employee_referral_policy", null);
    }

    /**
     * Create an aliased <code>userdb.user_employee_referral_policy</code> table reference
     */
    public UserEmployeeReferralPolicy(String alias) {
        this(alias, USER_EMPLOYEE_REFERRAL_POLICY);
    }

    private UserEmployeeReferralPolicy(String alias, Table<UserEmployeeReferralPolicyRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserEmployeeReferralPolicy(String alias, Table<UserEmployeeReferralPolicyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "员工想要了解内推政策点击次数表");
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
    public Identity<UserEmployeeReferralPolicyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_EMPLOYEE_REFERRAL_POLICY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserEmployeeReferralPolicyRecord> getPrimaryKey() {
        return Keys.KEY_USER_EMPLOYEE_REFERRAL_POLICY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserEmployeeReferralPolicyRecord>> getKeys() {
        return Arrays.<UniqueKey<UserEmployeeReferralPolicyRecord>>asList(Keys.KEY_USER_EMPLOYEE_REFERRAL_POLICY_PRIMARY, Keys.KEY_USER_EMPLOYEE_REFERRAL_POLICY_USER_EMPLOYEE_REFERRAL_POLICY_EMPLOYEE_ID_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeReferralPolicy as(String alias) {
        return new UserEmployeeReferralPolicy(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserEmployeeReferralPolicy rename(String name) {
        return new UserEmployeeReferralPolicy(name, null);
    }
}
