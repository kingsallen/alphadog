/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.campaigndb.tables;


import com.moseeker.baseorm.db.campaigndb.Campaigndb;
import com.moseeker.baseorm.db.campaigndb.Keys;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignEdmUserrecommendedPositionsRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


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
public class CampaignEdmUserrecommendedPositions extends TableImpl<CampaignEdmUserrecommendedPositionsRecord> {

    private static final long serialVersionUID = 1613532608;

    /**
     * The reference instance of <code>campaigndb.campaign_edm_userrecommended_positions</code>
     */
    public static final CampaignEdmUserrecommendedPositions CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS = new CampaignEdmUserrecommendedPositions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CampaignEdmUserrecommendedPositionsRecord> getRecordType() {
        return CampaignEdmUserrecommendedPositionsRecord.class;
    }

    /**
     * The column <code>campaigndb.campaign_edm_userrecommended_positions.id</code>.
     */
    public final TableField<CampaignEdmUserrecommendedPositionsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_edm_userrecommended_positions.campaign_id</code>.
     */
    public final TableField<CampaignEdmUserrecommendedPositionsRecord, Integer> CAMPAIGN_ID = createField("campaign_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_edm_userrecommended_positions.user_id</code>.
     */
    public final TableField<CampaignEdmUserrecommendedPositionsRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_edm_userrecommended_positions.position_id</code>.
     */
    public final TableField<CampaignEdmUserrecommendedPositionsRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_edm_userrecommended_positions.score</code>. 推荐分
     */
    public final TableField<CampaignEdmUserrecommendedPositionsRecord, Double> SCORE = createField("score", org.jooq.impl.SQLDataType.FLOAT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.FLOAT)), this, "推荐分");

    /**
     * The column <code>campaigndb.campaign_edm_userrecommended_positions.create_time</code>.
     */
    public final TableField<CampaignEdmUserrecommendedPositionsRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>campaigndb.campaign_edm_userrecommended_positions</code> table reference
     */
    public CampaignEdmUserrecommendedPositions() {
        this("campaign_edm_userrecommended_positions", null);
    }

    /**
     * Create an aliased <code>campaigndb.campaign_edm_userrecommended_positions</code> table reference
     */
    public CampaignEdmUserrecommendedPositions(String alias) {
        this(alias, CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS);
    }

    private CampaignEdmUserrecommendedPositions(String alias, Table<CampaignEdmUserrecommendedPositionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private CampaignEdmUserrecommendedPositions(String alias, Table<CampaignEdmUserrecommendedPositionsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<CampaignEdmUserrecommendedPositionsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CampaignEdmUserrecommendedPositionsRecord> getPrimaryKey() {
        return Keys.KEY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CampaignEdmUserrecommendedPositionsRecord>> getKeys() {
        return Arrays.<UniqueKey<CampaignEdmUserrecommendedPositionsRecord>>asList(Keys.KEY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUserrecommendedPositions as(String alias) {
        return new CampaignEdmUserrecommendedPositions(alias, this);
    }

    /**
     * Rename this table
     */
    public CampaignEdmUserrecommendedPositions rename(String name) {
        return new CampaignEdmUserrecommendedPositions(name, null);
    }
}