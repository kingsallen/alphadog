/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.campaigndb;


import com.moseeker.baseorm.db.campaigndb.tables.*;
import com.moseeker.baseorm.db.campaigndb.tables.records.*;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships between tables of the <code>campaigndb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<CampaignBaiduUsersRecord, Integer> IDENTITY_CAMPAIGN_BAIDU_USERS = Identities0.IDENTITY_CAMPAIGN_BAIDU_USERS;
    public static final Identity<CampaignEdmCampaignRecord, Integer> IDENTITY_CAMPAIGN_EDM_CAMPAIGN = Identities0.IDENTITY_CAMPAIGN_EDM_CAMPAIGN;
    public static final Identity<CampaignEdmUserrecommendedPositionsRecord, Integer> IDENTITY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS = Identities0.IDENTITY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS;
    public static final Identity<CampaignEdmUsersRecord, Integer> IDENTITY_CAMPAIGN_EDM_USERS = Identities0.IDENTITY_CAMPAIGN_EDM_USERS;
    public static final Identity<CampaignHeadImageRecord, Integer> IDENTITY_CAMPAIGN_HEAD_IMAGE = Identities0.IDENTITY_CAMPAIGN_HEAD_IMAGE;
    public static final Identity<CampaignRecommedCompanyRecord, Integer> IDENTITY_CAMPAIGN_RECOMMED_COMPANY = Identities0.IDENTITY_CAMPAIGN_RECOMMED_COMPANY;
    public static final Identity<CampaignRecommedPositionRecord, Integer> IDENTITY_CAMPAIGN_RECOMMED_POSITION = Identities0.IDENTITY_CAMPAIGN_RECOMMED_POSITION;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CampaignBaiduUsersRecord> KEY_CAMPAIGN_BAIDU_USERS_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_BAIDU_USERS_PRIMARY;
    public static final UniqueKey<CampaignBaiduUsersRecord> KEY_CAMPAIGN_BAIDU_USERS_USER_ID = UniqueKeys0.KEY_CAMPAIGN_BAIDU_USERS_USER_ID;
    public static final UniqueKey<CampaignBaiduUsersRecord> KEY_CAMPAIGN_BAIDU_USERS_UID = UniqueKeys0.KEY_CAMPAIGN_BAIDU_USERS_UID;
    public static final UniqueKey<CampaignEdmCampaignRecord> KEY_CAMPAIGN_EDM_CAMPAIGN_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_EDM_CAMPAIGN_PRIMARY;
    public static final UniqueKey<CampaignEdmUserrecommendedPositionsRecord> KEY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS_PRIMARY;
    public static final UniqueKey<CampaignEdmUsersRecord> KEY_CAMPAIGN_EDM_USERS_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_EDM_USERS_PRIMARY;
    public static final UniqueKey<CampaignHeadImageRecord> KEY_CAMPAIGN_HEAD_IMAGE_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_HEAD_IMAGE_PRIMARY;
    public static final UniqueKey<CampaignRecommedCompanyRecord> KEY_CAMPAIGN_RECOMMED_COMPANY_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_RECOMMED_COMPANY_PRIMARY;
    public static final UniqueKey<CampaignRecommedPositionRecord> KEY_CAMPAIGN_RECOMMED_POSITION_PRIMARY = UniqueKeys0.KEY_CAMPAIGN_RECOMMED_POSITION_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<CampaignBaiduUsersRecord, Integer> IDENTITY_CAMPAIGN_BAIDU_USERS = createIdentity(CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS, CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS.ID);
        public static Identity<CampaignEdmCampaignRecord, Integer> IDENTITY_CAMPAIGN_EDM_CAMPAIGN = createIdentity(CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN, CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN.ID);
        public static Identity<CampaignEdmUserrecommendedPositionsRecord, Integer> IDENTITY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS = createIdentity(CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS, CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS.ID);
        public static Identity<CampaignEdmUsersRecord, Integer> IDENTITY_CAMPAIGN_EDM_USERS = createIdentity(CampaignEdmUsers.CAMPAIGN_EDM_USERS, CampaignEdmUsers.CAMPAIGN_EDM_USERS.ID);
        public static Identity<CampaignHeadImageRecord, Integer> IDENTITY_CAMPAIGN_HEAD_IMAGE = createIdentity(CampaignHeadImage.CAMPAIGN_HEAD_IMAGE, CampaignHeadImage.CAMPAIGN_HEAD_IMAGE.ID);
        public static Identity<CampaignRecommedCompanyRecord, Integer> IDENTITY_CAMPAIGN_RECOMMED_COMPANY = createIdentity(CampaignRecommedCompany.CAMPAIGN_RECOMMED_COMPANY, CampaignRecommedCompany.CAMPAIGN_RECOMMED_COMPANY.ID);
        public static Identity<CampaignRecommedPositionRecord, Integer> IDENTITY_CAMPAIGN_RECOMMED_POSITION = createIdentity(CampaignRecommedPosition.CAMPAIGN_RECOMMED_POSITION, CampaignRecommedPosition.CAMPAIGN_RECOMMED_POSITION.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<CampaignBaiduUsersRecord> KEY_CAMPAIGN_BAIDU_USERS_PRIMARY = createUniqueKey(CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS, "KEY_campaign_baidu_users_PRIMARY", CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS.ID);
        public static final UniqueKey<CampaignBaiduUsersRecord> KEY_CAMPAIGN_BAIDU_USERS_USER_ID = createUniqueKey(CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS, "KEY_campaign_baidu_users_user_id", CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS.USER_ID);
        public static final UniqueKey<CampaignBaiduUsersRecord> KEY_CAMPAIGN_BAIDU_USERS_UID = createUniqueKey(CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS, "KEY_campaign_baidu_users_uid", CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS.UID);
        public static final UniqueKey<CampaignEdmCampaignRecord> KEY_CAMPAIGN_EDM_CAMPAIGN_PRIMARY = createUniqueKey(CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN, "KEY_campaign_edm_campaign_PRIMARY", CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN.ID);
        public static final UniqueKey<CampaignEdmUserrecommendedPositionsRecord> KEY_CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS_PRIMARY = createUniqueKey(CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS, "KEY_campaign_edm_userrecommended_positions_PRIMARY", CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS.ID);
        public static final UniqueKey<CampaignEdmUsersRecord> KEY_CAMPAIGN_EDM_USERS_PRIMARY = createUniqueKey(CampaignEdmUsers.CAMPAIGN_EDM_USERS, "KEY_campaign_edm_users_PRIMARY", CampaignEdmUsers.CAMPAIGN_EDM_USERS.ID);
        public static final UniqueKey<CampaignHeadImageRecord> KEY_CAMPAIGN_HEAD_IMAGE_PRIMARY = createUniqueKey(CampaignHeadImage.CAMPAIGN_HEAD_IMAGE, "KEY_campaign_head_image_PRIMARY", CampaignHeadImage.CAMPAIGN_HEAD_IMAGE.ID);
        public static final UniqueKey<CampaignRecommedCompanyRecord> KEY_CAMPAIGN_RECOMMED_COMPANY_PRIMARY = createUniqueKey(CampaignRecommedCompany.CAMPAIGN_RECOMMED_COMPANY, "KEY_campaign_recommed_company_PRIMARY", CampaignRecommedCompany.CAMPAIGN_RECOMMED_COMPANY.ID);
        public static final UniqueKey<CampaignRecommedPositionRecord> KEY_CAMPAIGN_RECOMMED_POSITION_PRIMARY = createUniqueKey(CampaignRecommedPosition.CAMPAIGN_RECOMMED_POSITION, "KEY_campaign_recommed_position_PRIMARY", CampaignRecommedPosition.CAMPAIGN_RECOMMED_POSITION.ID);
    }
}