package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.crud.JooqCrudImpl;

import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecomPositionlist;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignRecomPositionlistRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/11/21.
 */
@Service
public class CampaignRecomPositionlistDao extends JooqCrudImpl<com.moseeker.baseorm.db.campaigndb.tables.pojos.CampaignRecomPositionlist,CampaignRecomPositionlistRecord> {
    public CampaignRecomPositionlistDao(){
        super(CampaignRecomPositionlist.CAMPAIGN_RECOM_POSITIONLIST,com.moseeker.baseorm.db.campaigndb.tables.pojos.CampaignRecomPositionlist.class);
    }
    public CampaignRecomPositionlistDao(TableImpl<CampaignRecomPositionlistRecord> table, Class<com.moseeker.baseorm.db.campaigndb.tables.pojos.CampaignRecomPositionlist> campaignRecomPositionlistClass) {
        super(table, campaignRecomPositionlistClass);
    }
}
