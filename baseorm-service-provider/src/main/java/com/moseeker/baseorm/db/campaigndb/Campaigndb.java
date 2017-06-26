/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.campaigndb;


import com.moseeker.baseorm.db.campaigndb.tables.CampaignCompanySurvey;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmCampaign;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUserrecommendedPositions;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUsers;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignEmailAgentdelivery;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignHeadImage;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignNominateCompany;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignNominatePosition;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommendCompany;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommendPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Campaigndb extends SchemaImpl {

    private static final long serialVersionUID = 1655583146;

    /**
     * The reference instance of <code>campaigndb</code>
     */
    public static final Campaigndb CAMPAIGNDB = new Campaigndb();

    /**
     * The table <code>campaigndb.campaign_company_survey</code>.
     */
    public final CampaignCompanySurvey CAMPAIGN_COMPANY_SURVEY = com.moseeker.baseorm.db.campaigndb.tables.CampaignCompanySurvey.CAMPAIGN_COMPANY_SURVEY;

    /**
     * The table <code>campaigndb.campaign_edm_campaign</code>.
     */
    public final CampaignEdmCampaign CAMPAIGN_EDM_CAMPAIGN = com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN;

    /**
     * The table <code>campaigndb.campaign_edm_userrecommended_positions</code>.
     */
    public final CampaignEdmUserrecommendedPositions CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS = com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS;

    /**
     * The table <code>campaigndb.campaign_edm_users</code>.
     */
    public final CampaignEdmUsers CAMPAIGN_EDM_USERS = com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUsers.CAMPAIGN_EDM_USERS;

    /**
     * The table <code>campaigndb.campaign_email_agentdelivery</code>.
     */
    public final CampaignEmailAgentdelivery CAMPAIGN_EMAIL_AGENTDELIVERY = com.moseeker.baseorm.db.campaigndb.tables.CampaignEmailAgentdelivery.CAMPAIGN_EMAIL_AGENTDELIVERY;

    /**
     * 头部图片(职位列表页)
     */
    public final CampaignHeadImage CAMPAIGN_HEAD_IMAGE = com.moseeker.baseorm.db.campaigndb.tables.CampaignHeadImage.CAMPAIGN_HEAD_IMAGE;

    /**
     * 企业严选
     */
    public final CampaignNominateCompany CAMPAIGN_NOMINATE_COMPANY = com.moseeker.baseorm.db.campaigndb.tables.CampaignNominateCompany.CAMPAIGN_NOMINATE_COMPANY;

    /**
     * 职位严选
     */
    public final CampaignNominatePosition CAMPAIGN_NOMINATE_POSITION = com.moseeker.baseorm.db.campaigndb.tables.CampaignNominatePosition.CAMPAIGN_NOMINATE_POSITION;

    /**
     * 推荐公司
     */
    public final CampaignRecommendCompany CAMPAIGN_RECOMMEND_COMPANY = com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY;

    /**
     * 推荐职位
     */
    public final CampaignRecommendPosition CAMPAIGN_RECOMMEND_POSITION = com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommendPosition.CAMPAIGN_RECOMMEND_POSITION;

    /**
     * No further instances allowed
     */
    private Campaigndb() {
        super("campaigndb", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            CampaignCompanySurvey.CAMPAIGN_COMPANY_SURVEY,
            CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN,
            CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS,
            CampaignEdmUsers.CAMPAIGN_EDM_USERS,
            CampaignEmailAgentdelivery.CAMPAIGN_EMAIL_AGENTDELIVERY,
            CampaignHeadImage.CAMPAIGN_HEAD_IMAGE,
            CampaignNominateCompany.CAMPAIGN_NOMINATE_COMPANY,
            CampaignNominatePosition.CAMPAIGN_NOMINATE_POSITION,
            CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY,
            CampaignRecommendPosition.CAMPAIGN_RECOMMEND_POSITION);
    }
}
