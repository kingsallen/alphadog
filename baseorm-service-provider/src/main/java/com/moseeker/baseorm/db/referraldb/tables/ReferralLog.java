/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables;


import com.moseeker.baseorm.db.referraldb.Keys;
import com.moseeker.baseorm.db.referraldb.Referraldb;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralLogRecord;

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
 * 内推记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralLog extends TableImpl<ReferralLogRecord> {

    private static final long serialVersionUID = -501774116;

    /**
     * The reference instance of <code>referraldb.referral_log</code>
     */
    public static final ReferralLog REFERRAL_LOG = new ReferralLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReferralLogRecord> getRecordType() {
        return ReferralLogRecord.class;
    }

    /**
     * The column <code>referraldb.referral_log.id</code>. 主key
     */
    public final TableField<ReferralLogRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>referraldb.referral_log.employee_id</code>. 员工编号
     */
    public final TableField<ReferralLogRecord, Integer> EMPLOYEE_ID = createField("employee_id", org.jooq.impl.SQLDataType.INTEGER, this, "员工编号");

    /**
     * The column <code>referraldb.referral_log.reference_id</code>. 被推荐人编号
     */
    public final TableField<ReferralLogRecord, Integer> REFERENCE_ID = createField("reference_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "被推荐人编号");

    /**
     * The column <code>referraldb.referral_log.position_id</code>. 职位编号
     */
    public final TableField<ReferralLogRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职位编号");

    /**
     * The column <code>referraldb.referral_log.type</code>. 推荐方式 1 手机文件上传，2 电脑扫码上传简历 3 推荐人才关键信息
     */
    public final TableField<ReferralLogRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "推荐方式 1 手机文件上传，2 电脑扫码上传简历 3 推荐人才关键信息");

    /**
     * The column <code>referraldb.referral_log.referral_time</code>. 推荐时间
     */
    public final TableField<ReferralLogRecord, Timestamp> REFERRAL_TIME = createField("referral_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "推荐时间");

    /**
     * The column <code>referraldb.referral_log.claim</code>. 是否认领。0 未认领， 1 已经认领
     */
    public final TableField<ReferralLogRecord, Byte> CLAIM = createField("claim", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否认领。0 未认领， 1 已经认领");

    /**
     * The column <code>referraldb.referral_log.claim_time</code>. 认领时间
     */
    public final TableField<ReferralLogRecord, Timestamp> CLAIM_TIME = createField("claim_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "认领时间");

    /**
     * The column <code>referraldb.referral_log.create_time</code>. 创建时间
     */
    public final TableField<ReferralLogRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>referraldb.referral_log.update_time</code>. 更新时间
     */
    public final TableField<ReferralLogRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>referraldb.referral_log.attement_id</code>. 简历附件编号
     */
    public final TableField<ReferralLogRecord, Integer> ATTEMENT_ID = createField("attement_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "简历附件编号");

    /**
     * The column <code>referraldb.referral_log.old_reference_id</code>. 简历附件编号
     */
    public final TableField<ReferralLogRecord, Integer> OLD_REFERENCE_ID = createField("old_reference_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "简历附件编号");

    /**
     * Create a <code>referraldb.referral_log</code> table reference
     */
    public ReferralLog() {
        this("referral_log", null);
    }

    /**
     * Create an aliased <code>referraldb.referral_log</code> table reference
     */
    public ReferralLog(String alias) {
        this(alias, REFERRAL_LOG);
    }

    private ReferralLog(String alias, Table<ReferralLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReferralLog(String alias, Table<ReferralLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "内推记录");
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
    public Identity<ReferralLogRecord, Integer> getIdentity() {
        return Keys.IDENTITY_REFERRAL_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ReferralLogRecord> getPrimaryKey() {
        return Keys.KEY_REFERRAL_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ReferralLogRecord>> getKeys() {
        return Arrays.<UniqueKey<ReferralLogRecord>>asList(Keys.KEY_REFERRAL_LOG_PRIMARY, Keys.KEY_REFERRAL_LOG_REFERRAL_LOG_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLog as(String alias) {
        return new ReferralLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReferralLog rename(String name) {
        return new ReferralLog(name, null);
    }
}
