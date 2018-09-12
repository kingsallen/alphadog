/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb.tables;


import com.moseeker.baseorm.db.candidatedb.Candidatedb;
import com.moseeker.baseorm.db.candidatedb.Keys;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChainRecord;

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
 * 链路信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateShareChain extends TableImpl<CandidateShareChainRecord> {

    private static final long serialVersionUID = -496798018;

    /**
     * The reference instance of <code>candidatedb.candidate_share_chain</code>
     */
    public static final CandidateShareChain CANDIDATE_SHARE_CHAIN = new CandidateShareChain();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CandidateShareChainRecord> getRecordType() {
        return CandidateShareChainRecord.class;
    }

    /**
     * The column <code>candidatedb.candidate_share_chain.id</code>. id
     */
    public final TableField<CandidateShareChainRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "id");

    /**
     * The column <code>candidatedb.candidate_share_chain.position_id</code>. jobdb.job_position.id,相关职位 id
     */
    public final TableField<CandidateShareChainRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "jobdb.job_position.id,相关职位 id");

    /**
     * The column <code>candidatedb.candidate_share_chain.root_recom_user_id</code>. userdb.user_user.id,最初转发人的 user_user.id
     */
    public final TableField<CandidateShareChainRecord, Integer> ROOT_RECOM_USER_ID = createField("root_recom_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_user.id,最初转发人的 user_user.id");

    /**
     * The column <code>candidatedb.candidate_share_chain.root2_recom_user_id</code>. userdb.user_user.id,最初转发人后一个的 user_user.id
     */
    public final TableField<CandidateShareChainRecord, Integer> ROOT2_RECOM_USER_ID = createField("root2_recom_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_user.id,最初转发人后一个的 user_user.id");

    /**
     * The column <code>candidatedb.candidate_share_chain.recom_user_id</code>. userdb.user_user.id,转发人的 user_user.id
     */
    public final TableField<CandidateShareChainRecord, Integer> RECOM_USER_ID = createField("recom_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_user.id,转发人的 user_user.id");

    /**
     * The column <code>candidatedb.candidate_share_chain.presentee_user_id</code>. userdb.user_user.id,点击者 user_id
     */
    public final TableField<CandidateShareChainRecord, Integer> PRESENTEE_USER_ID = createField("presentee_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_user.id,点击者 user_id");

    /**
     * The column <code>candidatedb.candidate_share_chain.depth</code>. 第几层关系, 默认从 1 开始,当 presentee_id 是员工时,depth 为 0,表示该员工把链路截断了
     */
    public final TableField<CandidateShareChainRecord, Integer> DEPTH = createField("depth", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "第几层关系, 默认从 1 开始,当 presentee_id 是员工时,depth 为 0,表示该员工把链路截断了");

    /**
     * The column <code>candidatedb.candidate_share_chain.parent_id</code>. candidatedb.candidate_share_chain.id,上一条 share_chain.id
     */
    public final TableField<CandidateShareChainRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "candidatedb.candidate_share_chain.id,上一条 share_chain.id");

    /**
     * The column <code>candidatedb.candidate_share_chain.click_time</code>. candidatedb.candidate_position_share_record.click_time,点击时间
     */
    public final TableField<CandidateShareChainRecord, Timestamp> CLICK_TIME = createField("click_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "candidatedb.candidate_position_share_record.click_time,点击时间");

    /**
     * The column <code>candidatedb.candidate_share_chain.create_time</code>. 创建时间
     */
    public final TableField<CandidateShareChainRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * Create a <code>candidatedb.candidate_share_chain</code> table reference
     */
    public CandidateShareChain() {
        this("candidate_share_chain", null);
    }

    /**
     * Create an aliased <code>candidatedb.candidate_share_chain</code> table reference
     */
    public CandidateShareChain(String alias) {
        this(alias, CANDIDATE_SHARE_CHAIN);
    }

    private CandidateShareChain(String alias, Table<CandidateShareChainRecord> aliased) {
        this(alias, aliased, null);
    }

    private CandidateShareChain(String alias, Table<CandidateShareChainRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "链路信息表");
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
    public Identity<CandidateShareChainRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CANDIDATE_SHARE_CHAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CandidateShareChainRecord> getPrimaryKey() {
        return Keys.KEY_CANDIDATE_SHARE_CHAIN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CandidateShareChainRecord>> getKeys() {
        return Arrays.<UniqueKey<CandidateShareChainRecord>>asList(Keys.KEY_CANDIDATE_SHARE_CHAIN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CandidateShareChain as(String alias) {
        return new CandidateShareChain(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CandidateShareChain rename(String name) {
        return new CandidateShareChain(name, null);
    }
}
