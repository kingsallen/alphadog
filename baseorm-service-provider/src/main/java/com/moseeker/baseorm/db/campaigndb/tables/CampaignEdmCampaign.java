/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.campaigndb.tables;


import com.moseeker.baseorm.db.campaigndb.Campaigndb;
import com.moseeker.baseorm.db.campaigndb.Keys;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignEdmCampaignRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignEdmCampaign extends TableImpl<CampaignEdmCampaignRecord> {

    private static final long serialVersionUID = -667620962;

    /**
     * The reference instance of <code>campaigndb.campaign_edm_campaign</code>
     */
    public static final CampaignEdmCampaign CAMPAIGN_EDM_CAMPAIGN = new CampaignEdmCampaign();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CampaignEdmCampaignRecord> getRecordType() {
        return CampaignEdmCampaignRecord.class;
    }

    /**
     * The column <code>campaigndb.campaign_edm_campaign.id</code>.
     */
    public final TableField<CampaignEdmCampaignRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_edm_campaign.name</code>. 活动名称
     */
    public final TableField<CampaignEdmCampaignRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "活动名称");

    /**
     * The column <code>campaigndb.campaign_edm_campaign.desc</code>. 活动描述
     */
    public final TableField<CampaignEdmCampaignRecord, String> DESC = createField("desc", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false), this, "活动描述");

    /**
     * The column <code>campaigndb.campaign_edm_campaign.send_time</code>.
     */
    public final TableField<CampaignEdmCampaignRecord, Timestamp> SEND_TIME = createField("send_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>campaigndb.campaign_edm_campaign.create_time</code>.
     */
    public final TableField<CampaignEdmCampaignRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>campaigndb.campaign_edm_campaign</code> table reference
     */
    public CampaignEdmCampaign() {
        this("campaign_edm_campaign", null);
    }

    /**
     * Create an aliased <code>campaigndb.campaign_edm_campaign</code> table reference
     */
    public CampaignEdmCampaign(String alias) {
        this(alias, CAMPAIGN_EDM_CAMPAIGN);
    }

    private CampaignEdmCampaign(String alias, Table<CampaignEdmCampaignRecord> aliased) {
        this(alias, aliased, null);
    }

    private CampaignEdmCampaign(String alias, Table<CampaignEdmCampaignRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
    public Identity<CampaignEdmCampaignRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CAMPAIGN_EDM_CAMPAIGN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CampaignEdmCampaignRecord> getPrimaryKey() {
        return Keys.KEY_CAMPAIGN_EDM_CAMPAIGN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CampaignEdmCampaignRecord>> getKeys() {
        return Arrays.<UniqueKey<CampaignEdmCampaignRecord>>asList(Keys.KEY_CAMPAIGN_EDM_CAMPAIGN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmCampaign as(String alias) {
        return new CampaignEdmCampaign(alias, this);
    }

    /**
     * Rename this table
     */
    public CampaignEdmCampaign rename(String name) {
        return new CampaignEdmCampaign(name, null);
    }
}
