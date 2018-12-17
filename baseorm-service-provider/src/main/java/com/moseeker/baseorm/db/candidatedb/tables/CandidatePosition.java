/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb.tables;


import com.moseeker.baseorm.db.candidatedb.Candidatedb;
import com.moseeker.baseorm.db.candidatedb.Keys;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 候选人表相关职位表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidatePosition extends TableImpl<CandidatePositionRecord> {

    private static final long serialVersionUID = -1792064987;

    /**
     * The reference instance of <code>candidatedb.candidate_position</code>
     */
    public static final CandidatePosition CANDIDATE_POSITION = new CandidatePosition();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CandidatePositionRecord> getRecordType() {
        return CandidatePositionRecord.class;
    }

    /**
     * The column <code>candidatedb.candidate_position.position_id</code>. hr_position.id
     */
    public final TableField<CandidatePositionRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_position.id");

    /**
     * The column <code>candidatedb.candidate_position.update_time</code>. 修改时间
     */
    public final TableField<CandidatePositionRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>candidatedb.candidate_position.wxuser_id</code>. user_wx_user.id，表示候选人代表的微信账号。已经废弃。微信账号由C端账号代替，请参考user_id
     */
    public final TableField<CandidatePositionRecord, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "user_wx_user.id，表示候选人代表的微信账号。已经废弃。微信账号由C端账号代替，请参考user_id");

    /**
     * The column <code>candidatedb.candidate_position.is_interested</code>. 是否感兴趣
     */
    public final TableField<CandidatePositionRecord, Byte> IS_INTERESTED = createField("is_interested", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否感兴趣");

    /**
     * The column <code>candidatedb.candidate_position.candidate_company_id</code>. candidate_company.id
     */
    public final TableField<CandidatePositionRecord, Integer> CANDIDATE_COMPANY_ID = createField("candidate_company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "candidate_company.id");

    /**
     * The column <code>candidatedb.candidate_position.view_number</code>. 查看次数
     */
    public final TableField<CandidatePositionRecord, Integer> VIEW_NUMBER = createField("view_number", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "查看次数");

    /**
     * The column <code>candidatedb.candidate_position.shared_from_employee</code>.
     */
    public final TableField<CandidatePositionRecord, Byte> SHARED_FROM_EMPLOYEE = createField("shared_from_employee", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>candidatedb.candidate_position.user_id</code>. userdb.user_user.id 候选人代表的C端用户
     */
    public final TableField<CandidatePositionRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_user.id 候选人代表的C端用户");

    /**
     * The column <code>candidatedb.candidate_position.type</code>.
     */
    public final TableField<CandidatePositionRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * Create a <code>candidatedb.candidate_position</code> table reference
     */
    public CandidatePosition() {
        this("candidate_position", null);
    }

    /**
     * Create an aliased <code>candidatedb.candidate_position</code> table reference
     */
    public CandidatePosition(String alias) {
        this(alias, CANDIDATE_POSITION);
    }

    private CandidatePosition(String alias, Table<CandidatePositionRecord> aliased) {
        this(alias, aliased, null);
    }

    private CandidatePosition(String alias, Table<CandidatePositionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "候选人表相关职位表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Candidatedb.CANDIDATEDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CandidatePositionRecord> getPrimaryKey() {
        return Keys.KEY_CANDIDATE_POSITION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CandidatePositionRecord>> getKeys() {
        return Arrays.<UniqueKey<CandidatePositionRecord>>asList(Keys.KEY_CANDIDATE_POSITION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidatePosition as(String alias) {
        return new CandidatePosition(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CandidatePosition rename(String name) {
        return new CandidatePosition(name, null);
    }
}
