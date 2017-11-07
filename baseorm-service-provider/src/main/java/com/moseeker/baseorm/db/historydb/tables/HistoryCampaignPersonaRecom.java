/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryCampaignPersonaRecomRecord;

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
 * 推送职位历史表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryCampaignPersonaRecom extends TableImpl<HistoryCampaignPersonaRecomRecord> {

    private static final long serialVersionUID = 538638932;

    /**
     * The reference instance of <code>historydb.history_campaign_persona_recom</code>
     */
    public static final HistoryCampaignPersonaRecom HISTORY_CAMPAIGN_PERSONA_RECOM = new HistoryCampaignPersonaRecom();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryCampaignPersonaRecomRecord> getRecordType() {
        return HistoryCampaignPersonaRecomRecord.class;
    }

    /**
     * The column <code>historydb.history_campaign_persona_recom.id</code>.
     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_campaign_persona_recom.user_id</code>. 用户id user_user.id
     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户id user_user.id");

    /**
     * The column <code>historydb.history_campaign_persona_recom.position_id</code>. 职位id job_position.id
     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位id job_position.id");

    /**
     * The column <code>historydb.history_campaign_persona_recom.create_time</code>. 创建时间
     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>historydb.history_campaign_persona_recom.send_time</code>. 推送时间
     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Timestamp> SEND_TIME = createField("send_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "推送时间");

    /**
     * The column <code>historydb.history_campaign_persona_recom.is_send</code>. 是否发送，0是未发送，1是已发送
     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Byte> IS_SEND = createField("is_send", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否发送，0是未发送，1是已发送");

    /**
     * The column <code>historydb.history_campaign_persona_recom.update_time</code>. 更新时间

     */
    public final TableField<HistoryCampaignPersonaRecomRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间\n");

    /**
     * Create a <code>historydb.history_campaign_persona_recom</code> table reference
     */
    public HistoryCampaignPersonaRecom() {
        this("history_campaign_persona_recom", null);
    }

    /**
     * Create an aliased <code>historydb.history_campaign_persona_recom</code> table reference
     */
    public HistoryCampaignPersonaRecom(String alias) {
        this(alias, HISTORY_CAMPAIGN_PERSONA_RECOM);
    }

    private HistoryCampaignPersonaRecom(String alias, Table<HistoryCampaignPersonaRecomRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryCampaignPersonaRecom(String alias, Table<HistoryCampaignPersonaRecomRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "推送职位历史表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Historydb.HISTORYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HistoryCampaignPersonaRecomRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HISTORY_CAMPAIGN_PERSONA_RECOM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryCampaignPersonaRecomRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_CAMPAIGN_PERSONA_RECOM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryCampaignPersonaRecomRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryCampaignPersonaRecomRecord>>asList(Keys.KEY_HISTORY_CAMPAIGN_PERSONA_RECOM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryCampaignPersonaRecom as(String alias) {
        return new HistoryCampaignPersonaRecom(alias, this);
    }

    /**
     * Rename this table
     */
    public HistoryCampaignPersonaRecom rename(String name) {
        return new HistoryCampaignPersonaRecom(name, null);
    }
}
