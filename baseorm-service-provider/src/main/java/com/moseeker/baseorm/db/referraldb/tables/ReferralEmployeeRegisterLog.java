/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables;


import com.moseeker.baseorm.db.referraldb.Keys;
import com.moseeker.baseorm.db.referraldb.Referraldb;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeRegisterLogRecord;

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
 * 员工认证取消认证操作记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralEmployeeRegisterLog extends TableImpl<ReferralEmployeeRegisterLogRecord> {

    private static final long serialVersionUID = -679540445;

    /**
     * The reference instance of <code>referraldb.referral_employee_register_log</code>
     */
    public static final ReferralEmployeeRegisterLog REFERRAL_EMPLOYEE_REGISTER_LOG = new ReferralEmployeeRegisterLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReferralEmployeeRegisterLogRecord> getRecordType() {
        return ReferralEmployeeRegisterLogRecord.class;
    }

    /**
     * The column <code>referraldb.referral_employee_register_log.id</code>. 主key
     */
    public final TableField<ReferralEmployeeRegisterLogRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>referraldb.referral_employee_register_log.employee_id</code>. 员工编号
     */
    public final TableField<ReferralEmployeeRegisterLogRecord, Integer> EMPLOYEE_ID = createField("employee_id", org.jooq.impl.SQLDataType.INTEGER, this, "员工编号");

    /**
     * The column <code>referraldb.referral_employee_register_log.register</code>. 是否是注册 1表示注册，0表示取消注册
     */
    public final TableField<ReferralEmployeeRegisterLogRecord, Byte> REGISTER = createField("register", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "是否是注册 1表示注册，0表示取消注册");

    /**
     * The column <code>referraldb.referral_employee_register_log.operate_time</code>. 操作时间
     */
    public final TableField<ReferralEmployeeRegisterLogRecord, Timestamp> OPERATE_TIME = createField("operate_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "操作时间");

    /**
     * The column <code>referraldb.referral_employee_register_log.create_time</code>. 创建时间
     */
    public final TableField<ReferralEmployeeRegisterLogRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>referraldb.referral_employee_register_log.update_time</code>. 更新时间
     */
    public final TableField<ReferralEmployeeRegisterLogRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>referraldb.referral_employee_register_log</code> table reference
     */
    public ReferralEmployeeRegisterLog() {
        this("referral_employee_register_log", null);
    }

    /**
     * Create an aliased <code>referraldb.referral_employee_register_log</code> table reference
     */
    public ReferralEmployeeRegisterLog(String alias) {
        this(alias, REFERRAL_EMPLOYEE_REGISTER_LOG);
    }

    private ReferralEmployeeRegisterLog(String alias, Table<ReferralEmployeeRegisterLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReferralEmployeeRegisterLog(String alias, Table<ReferralEmployeeRegisterLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "员工认证取消认证操作记录");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Referraldb.REFERRALDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ReferralEmployeeRegisterLogRecord, Integer> getIdentity() {
        return Keys.IDENTITY_REFERRAL_EMPLOYEE_REGISTER_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ReferralEmployeeRegisterLogRecord> getPrimaryKey() {
        return Keys.KEY_REFERRAL_EMPLOYEE_REGISTER_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ReferralEmployeeRegisterLogRecord>> getKeys() {
        return Arrays.<UniqueKey<ReferralEmployeeRegisterLogRecord>>asList(Keys.KEY_REFERRAL_EMPLOYEE_REGISTER_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLog as(String alias) {
        return new ReferralEmployeeRegisterLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReferralEmployeeRegisterLog rename(String name) {
        return new ReferralEmployeeRegisterLog(name, null);
    }
}
