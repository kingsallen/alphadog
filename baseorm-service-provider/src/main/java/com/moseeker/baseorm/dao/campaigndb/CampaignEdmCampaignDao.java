package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmCampaign;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignEdmCampaignRecord;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignEdmCampaignDO;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* CampaignEdmCampaignDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignEdmCampaignDao extends JooqCrudImpl<CampaignEdmCampaignDO, CampaignEdmCampaignRecord> {


    public CampaignEdmCampaignDao() {
        super(CampaignEdmCampaign.CAMPAIGN_EDM_CAMPAIGN, CampaignEdmCampaignDO.class);
    }
}
