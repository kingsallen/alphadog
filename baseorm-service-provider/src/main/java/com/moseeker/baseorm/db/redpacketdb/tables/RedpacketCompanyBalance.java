/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables;


import com.moseeker.baseorm.db.redpacketdb.Keys;
import com.moseeker.baseorm.db.redpacketdb.Redpacketdb;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketCompanyBalanceRecord;

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
 * 公司账户
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketCompanyBalance extends TableImpl<RedpacketCompanyBalanceRecord> {

    private static final long serialVersionUID = 120016911;

    /**
     * The reference instance of <code>redpacketdb.redpacket_company_balance</code>
     */
    public static final RedpacketCompanyBalance REDPACKET_COMPANY_BALANCE = new RedpacketCompanyBalance();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RedpacketCompanyBalanceRecord> getRecordType() {
        return RedpacketCompanyBalanceRecord.class;
    }

    /**
     * The column <code>redpacketdb.redpacket_company_balance.id</code>. 记录编号，用于主键
     */
    public final TableField<RedpacketCompanyBalanceRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "记录编号，用于主键");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.company_id</code>. 公司编号
     */
    public final TableField<RedpacketCompanyBalanceRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "公司编号");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.balance</code>. 可用余额
     */
    public final TableField<RedpacketCompanyBalanceRecord, Integer> BALANCE = createField("balance", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "可用余额");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.frozen_capital</code>. 冻结金额
     */
    public final TableField<RedpacketCompanyBalanceRecord, Integer> FROZEN_CAPITAL = createField("frozen_capital", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "冻结金额");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.consumption</code>. 消费金额
     */
    public final TableField<RedpacketCompanyBalanceRecord, Integer> CONSUMPTION = createField("consumption", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "消费金额");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.recharge_total_amount</code>. 充值的总金额
     */
    public final TableField<RedpacketCompanyBalanceRecord, Integer> RECHARGE_TOTAL_AMOUNT = createField("recharge_total_amount", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "充值的总金额");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.create_time</code>. 创建时间
     */
    public final TableField<RedpacketCompanyBalanceRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>redpacketdb.redpacket_company_balance.update_time</code>. 更新时间
     */
    public final TableField<RedpacketCompanyBalanceRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>redpacketdb.redpacket_company_balance</code> table reference
     */
    public RedpacketCompanyBalance() {
        this("redpacket_company_balance", null);
    }

    /**
     * Create an aliased <code>redpacketdb.redpacket_company_balance</code> table reference
     */
    public RedpacketCompanyBalance(String alias) {
        this(alias, REDPACKET_COMPANY_BALANCE);
    }

    private RedpacketCompanyBalance(String alias, Table<RedpacketCompanyBalanceRecord> aliased) {
        this(alias, aliased, null);
    }

    private RedpacketCompanyBalance(String alias, Table<RedpacketCompanyBalanceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "公司账户");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Redpacketdb.REDPACKETDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<RedpacketCompanyBalanceRecord, Integer> getIdentity() {
        return Keys.IDENTITY_REDPACKET_COMPANY_BALANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RedpacketCompanyBalanceRecord> getPrimaryKey() {
        return Keys.KEY_REDPACKET_COMPANY_BALANCE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RedpacketCompanyBalanceRecord>> getKeys() {
        return Arrays.<UniqueKey<RedpacketCompanyBalanceRecord>>asList(Keys.KEY_REDPACKET_COMPANY_BALANCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketCompanyBalance as(String alias) {
        return new RedpacketCompanyBalance(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RedpacketCompanyBalance rename(String name) {
        return new RedpacketCompanyBalance(name, null);
    }
}
