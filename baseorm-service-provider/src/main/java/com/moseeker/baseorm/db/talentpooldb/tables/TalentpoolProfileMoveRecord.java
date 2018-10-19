/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables;


import com.moseeker.baseorm.db.talentpooldb.Keys;
import com.moseeker.baseorm.db.talentpooldb.Talentpooldb;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;

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
 * 简历搬家chaos请求操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveRecord extends TableImpl<TalentpoolProfileMoveRecordRecord> {

    private static final long serialVersionUID = -678794509;

    /**
     * The reference instance of <code>talentpooldb.talentpool_profile_move_record</code>
     */
    public static final TalentpoolProfileMoveRecord TALENTPOOL_PROFILE_MOVE_RECORD = new TalentpoolProfileMoveRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TalentpoolProfileMoveRecordRecord> getRecordType() {
        return TalentpoolProfileMoveRecordRecord.class;
    }

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.id</code>.
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.profile_move_id</code>. profile_move.id
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Integer> PROFILE_MOVE_ID = createField("profile_move_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "profile_move.id");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.crawl_type</code>. 简历类型 0.主动投递简历  1.已下载简历
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Byte> CRAWL_TYPE = createField("crawl_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "简历类型 0.主动投递简历  1.已下载简历");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.crawl_num</code>. 本次简历搬家获取简历数
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Integer> CRAWL_NUM = createField("crawl_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "本次简历搬家获取简历数");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.status</code>. 0.获取失败 1.已完成 2.获取中
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("2", org.jooq.impl.SQLDataType.TINYINT)), this, "0.获取失败 1.已完成 2.获取中");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.current_email_num</code>. 当前执行的简历搬家的第n封邮件
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Integer> CURRENT_EMAIL_NUM = createField("current_email_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "当前执行的简历搬家的第n封邮件");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.total_email_num</code>. 本次简历搬家的邮件总数
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Integer> TOTAL_EMAIL_NUM = createField("total_email_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "本次简历搬家的邮件总数");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.create_time</code>.
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_record.update_time</code>.
     */
    public final TableField<TalentpoolProfileMoveRecordRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>talentpooldb.talentpool_profile_move_record</code> table reference
     */
    public TalentpoolProfileMoveRecord() {
        this("talentpool_profile_move_record", null);
    }

    /**
     * Create an aliased <code>talentpooldb.talentpool_profile_move_record</code> table reference
     */
    public TalentpoolProfileMoveRecord(String alias) {
        this(alias, TALENTPOOL_PROFILE_MOVE_RECORD);
    }

    private TalentpoolProfileMoveRecord(String alias, Table<TalentpoolProfileMoveRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private TalentpoolProfileMoveRecord(String alias, Table<TalentpoolProfileMoveRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "简历搬家chaos请求操作记录表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Talentpooldb.TALENTPOOLDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TalentpoolProfileMoveRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TALENTPOOL_PROFILE_MOVE_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TalentpoolProfileMoveRecordRecord> getPrimaryKey() {
        return Keys.KEY_TALENTPOOL_PROFILE_MOVE_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TalentpoolProfileMoveRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<TalentpoolProfileMoveRecordRecord>>asList(Keys.KEY_TALENTPOOL_PROFILE_MOVE_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord as(String alias) {
        return new TalentpoolProfileMoveRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TalentpoolProfileMoveRecord rename(String name) {
        return new TalentpoolProfileMoveRecord(name, null);
    }
}
