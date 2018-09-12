/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables;


import com.moseeker.baseorm.db.talentpooldb.Keys;
import com.moseeker.baseorm.db.talentpooldb.Talentpooldb;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveDetailRecord;

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
<<<<<<< HEAD
 * 凡是记录在该表的手机号对应的简历都是已成功入库的简历搬家简历，
=======
 * 凡是记录在该表的手机号对应的简历都是已成功入库的简历搬家简历，
>>>>>>> feature/profile_move
 * 但是如果简历搬家失败时根据status字段标记出哪些简历是搬家失败的，下次搬家时不会因为重新合并一次导致数据不准确
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveDetail extends TableImpl<TalentpoolProfileMoveDetailRecord> {

    private static final long serialVersionUID = -939255850;

    /**
     * The reference instance of <code>talentpooldb.talentpool_profile_move_detail</code>
     */
    public static final TalentpoolProfileMoveDetail TALENTPOOL_PROFILE_MOVE_DETAIL = new TalentpoolProfileMoveDetail();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TalentpoolProfileMoveDetailRecord> getRecordType() {
        return TalentpoolProfileMoveDetailRecord.class;
    }

    /**
     * The column <code>talentpooldb.talentpool_profile_move_detail.id</code>.
     */
    public final TableField<TalentpoolProfileMoveDetailRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_detail.mobile</code>. 手机号
     */
    public final TableField<TalentpoolProfileMoveDetailRecord, Long> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "手机号");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_detail.profile_move_id</code>. talentpool_profile_move.id，对应的是上一次搬家失败的操作id
     */
    public final TableField<TalentpoolProfileMoveDetailRecord, Integer> PROFILE_MOVE_ID = createField("profile_move_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "talentpool_profile_move.id，对应的是上一次搬家失败的操作id");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_detail.profile_move_status</code>. 该手机号对应的简历搬家成功状态 0 失败 1 成功
     */
    public final TableField<TalentpoolProfileMoveDetailRecord, Byte> PROFILE_MOVE_STATUS = createField("profile_move_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "该手机号对应的简历搬家成功状态 0 失败 1 成功");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_detail.create_time</code>.
     */
    public final TableField<TalentpoolProfileMoveDetailRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>talentpooldb.talentpool_profile_move_detail.update_time</code>.
     */
    public final TableField<TalentpoolProfileMoveDetailRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>talentpooldb.talentpool_profile_move_detail</code> table reference
     */
    public TalentpoolProfileMoveDetail() {
        this("talentpool_profile_move_detail", null);
    }

    /**
     * Create an aliased <code>talentpooldb.talentpool_profile_move_detail</code> table reference
     */
    public TalentpoolProfileMoveDetail(String alias) {
        this(alias, TALENTPOOL_PROFILE_MOVE_DETAIL);
    }

    private TalentpoolProfileMoveDetail(String alias, Table<TalentpoolProfileMoveDetailRecord> aliased) {
        this(alias, aliased, null);
    }

    private TalentpoolProfileMoveDetail(String alias, Table<TalentpoolProfileMoveDetailRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "凡是记录在该表的手机号对应的简历都是已成功入库的简历搬家简历，\r\n但是如果简历搬家失败时根据status字段标记出哪些简历是搬家失败的，下次搬家时不会因为重新合并一次导致数据不准确");
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
    public Identity<TalentpoolProfileMoveDetailRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TALENTPOOL_PROFILE_MOVE_DETAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TalentpoolProfileMoveDetailRecord> getPrimaryKey() {
        return Keys.KEY_TALENTPOOL_PROFILE_MOVE_DETAIL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TalentpoolProfileMoveDetailRecord>> getKeys() {
        return Arrays.<UniqueKey<TalentpoolProfileMoveDetailRecord>>asList(Keys.KEY_TALENTPOOL_PROFILE_MOVE_DETAIL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetail as(String alias) {
        return new TalentpoolProfileMoveDetail(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TalentpoolProfileMoveDetail rename(String name) {
        return new TalentpoolProfileMoveDetail(name, null);
    }
}
