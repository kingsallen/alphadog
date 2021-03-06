/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables;


import com.moseeker.baseorm.db.referraldb.Keys;
import com.moseeker.baseorm.db.referraldb.Referraldb;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;

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
 * 人脉连连看链路表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralConnectionChain extends TableImpl<ReferralConnectionChainRecord> {

    private static final long serialVersionUID = 1132224137;

    /**
     * The reference instance of <code>referraldb.referral_connection_chain</code>
     */
    public static final ReferralConnectionChain REFERRAL_CONNECTION_CHAIN = new ReferralConnectionChain();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReferralConnectionChainRecord> getRecordType() {
        return ReferralConnectionChainRecord.class;
    }

    /**
     * The column <code>referraldb.referral_connection_chain.id</code>.
     */
    public final TableField<ReferralConnectionChainRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>referraldb.referral_connection_chain.recom_user_id</code>. 转发人，本次转发人脉连连看的user_user.id
     */
    public final TableField<ReferralConnectionChainRecord, Integer> RECOM_USER_ID = createField("recom_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "转发人，本次转发人脉连连看的user_user.id");

    /**
     * The column <code>referraldb.referral_connection_chain.next_user_id</code>. 本次转发人指向链路中的下一个人的userid
     */
    public final TableField<ReferralConnectionChainRecord, Integer> NEXT_USER_ID = createField("next_user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "本次转发人指向链路中的下一个人的userid");

    /**
     * The column <code>referraldb.referral_connection_chain.parent_id</code>. 本条记录的上级转发记录id，无上级默认为0
     */
    public final TableField<ReferralConnectionChainRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "本条记录的上级转发记录id，无上级默认为0");

    /**
     * The column <code>referraldb.referral_connection_chain.root_parent_id</code>. 链路起始id
     */
    public final TableField<ReferralConnectionChainRecord, Integer> ROOT_PARENT_ID = createField("root_parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "链路起始id");

    /**
     * The column <code>referraldb.referral_connection_chain.state</code>. 这条记录表示的转发连接是否在人脉连连看中连接起来 0 未生效 1 生效
     */
    public final TableField<ReferralConnectionChainRecord, Byte> STATE = createField("state", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "这条记录表示的转发连接是否在人脉连连看中连接起来 0 未生效 1 生效");

    /**
     * The column <code>referraldb.referral_connection_chain.click_time</code>. 点击时间
     */
    public final TableField<ReferralConnectionChainRecord, Timestamp> CLICK_TIME = createField("click_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "点击时间");

    /**
     * The column <code>referraldb.referral_connection_chain.create_time</code>.
     */
    public final TableField<ReferralConnectionChainRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>referraldb.referral_connection_chain.update_time</code>.
     */
    public final TableField<ReferralConnectionChainRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>referraldb.referral_connection_chain</code> table reference
     */
    public ReferralConnectionChain() {
        this("referral_connection_chain", null);
    }

    /**
     * Create an aliased <code>referraldb.referral_connection_chain</code> table reference
     */
    public ReferralConnectionChain(String alias) {
        this(alias, REFERRAL_CONNECTION_CHAIN);
    }

    private ReferralConnectionChain(String alias, Table<ReferralConnectionChainRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReferralConnectionChain(String alias, Table<ReferralConnectionChainRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "人脉连连看链路表");
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
    public Identity<ReferralConnectionChainRecord, Integer> getIdentity() {
        return Keys.IDENTITY_REFERRAL_CONNECTION_CHAIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ReferralConnectionChainRecord> getPrimaryKey() {
        return Keys.KEY_REFERRAL_CONNECTION_CHAIN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ReferralConnectionChainRecord>> getKeys() {
        return Arrays.<UniqueKey<ReferralConnectionChainRecord>>asList(Keys.KEY_REFERRAL_CONNECTION_CHAIN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralConnectionChain as(String alias) {
        return new ReferralConnectionChain(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReferralConnectionChain rename(String name) {
        return new ReferralConnectionChain(name, null);
    }
}
