/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.campaigndb.tables;


import com.moseeker.baseorm.db.campaigndb.Campaigndb;
import com.moseeker.baseorm.db.campaigndb.Keys;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignBaiduUsersRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


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
public class CampaignBaiduUsers extends TableImpl<CampaignBaiduUsersRecord> {

    private static final long serialVersionUID = 197676247;

    /**
     * The reference instance of <code>campaigndb.campaign_baidu_users</code>
     */
    public static final CampaignBaiduUsers CAMPAIGN_BAIDU_USERS = new CampaignBaiduUsers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CampaignBaiduUsersRecord> getRecordType() {
        return CampaignBaiduUsersRecord.class;
    }

    /**
     * The column <code>campaigndb.campaign_baidu_users.id</code>.
     */
    public final TableField<CampaignBaiduUsersRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_baidu_users.user_id</code>.
     */
    public final TableField<CampaignBaiduUsersRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_baidu_users.uid</code>. baidu的uid
     */
    public final TableField<CampaignBaiduUsersRecord, Long> UID = createField("uid", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "baidu的uid");

    /**
     * The column <code>campaigndb.campaign_baidu_users.create_time</code>.
     */
    public final TableField<CampaignBaiduUsersRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>campaigndb.campaign_baidu_users</code> table reference
     */
    public CampaignBaiduUsers() {
        this("campaign_baidu_users", null);
    }

    /**
     * Create an aliased <code>campaigndb.campaign_baidu_users</code> table reference
     */
    public CampaignBaiduUsers(String alias) {
        this(alias, CAMPAIGN_BAIDU_USERS);
    }

    private CampaignBaiduUsers(String alias, Table<CampaignBaiduUsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private CampaignBaiduUsers(String alias, Table<CampaignBaiduUsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "百度用户关联表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Campaigndb.CAMPAIGNDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CampaignBaiduUsersRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CAMPAIGN_BAIDU_USERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CampaignBaiduUsersRecord> getPrimaryKey() {
        return Keys.KEY_CAMPAIGN_BAIDU_USERS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CampaignBaiduUsersRecord>> getKeys() {
        return Arrays.<UniqueKey<CampaignBaiduUsersRecord>>asList(Keys.KEY_CAMPAIGN_BAIDU_USERS_PRIMARY, Keys.KEY_CAMPAIGN_BAIDU_USERS_USER_ID, Keys.KEY_CAMPAIGN_BAIDU_USERS_UID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignBaiduUsers as(String alias) {
        return new CampaignBaiduUsers(alias, this);
    }

    /**
     * Rename this table
     */
    public CampaignBaiduUsers rename(String name) {
        return new CampaignBaiduUsers(name, null);
    }
}
