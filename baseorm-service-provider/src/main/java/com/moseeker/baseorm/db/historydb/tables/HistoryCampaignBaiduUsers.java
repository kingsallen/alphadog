/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryCampaignBaiduUsersRecord;

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
 * 百度用户关联表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryCampaignBaiduUsers extends TableImpl<HistoryCampaignBaiduUsersRecord> {

    private static final long serialVersionUID = -416240988;

    /**
     * The reference instance of <code>historydb.history_campaign_baidu_users</code>
     */
    public static final HistoryCampaignBaiduUsers HISTORY_CAMPAIGN_BAIDU_USERS = new HistoryCampaignBaiduUsers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryCampaignBaiduUsersRecord> getRecordType() {
        return HistoryCampaignBaiduUsersRecord.class;
    }

    /**
     * The column <code>historydb.history_campaign_baidu_users.id</code>.
     */
    public final TableField<HistoryCampaignBaiduUsersRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_campaign_baidu_users.user_id</code>.
     */
    public final TableField<HistoryCampaignBaiduUsersRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_campaign_baidu_users.uid</code>. baidu的uid
     */
    public final TableField<HistoryCampaignBaiduUsersRecord, Long> UID = createField("uid", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "baidu的uid");

    /**
     * The column <code>historydb.history_campaign_baidu_users.create_time</code>.
     */
    public final TableField<HistoryCampaignBaiduUsersRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>historydb.history_campaign_baidu_users</code> table reference
     */
    public HistoryCampaignBaiduUsers() {
        this("history_campaign_baidu_users", null);
    }

    /**
     * Create an aliased <code>historydb.history_campaign_baidu_users</code> table reference
     */
    public HistoryCampaignBaiduUsers(String alias) {
        this(alias, HISTORY_CAMPAIGN_BAIDU_USERS);
    }

    private HistoryCampaignBaiduUsers(String alias, Table<HistoryCampaignBaiduUsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryCampaignBaiduUsers(String alias, Table<HistoryCampaignBaiduUsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "百度用户关联表");
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
    public Identity<HistoryCampaignBaiduUsersRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HISTORY_CAMPAIGN_BAIDU_USERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryCampaignBaiduUsersRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_CAMPAIGN_BAIDU_USERS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryCampaignBaiduUsersRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryCampaignBaiduUsersRecord>>asList(Keys.KEY_HISTORY_CAMPAIGN_BAIDU_USERS_PRIMARY, Keys.KEY_HISTORY_CAMPAIGN_BAIDU_USERS_USER_ID, Keys.KEY_HISTORY_CAMPAIGN_BAIDU_USERS_UID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryCampaignBaiduUsers as(String alias) {
        return new HistoryCampaignBaiduUsers(alias, this);
    }

    /**
     * Rename this table
     */
    public HistoryCampaignBaiduUsers rename(String name) {
        return new HistoryCampaignBaiduUsers(name, null);
    }
}