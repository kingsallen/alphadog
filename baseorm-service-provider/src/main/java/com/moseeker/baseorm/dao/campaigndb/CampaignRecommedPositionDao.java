package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommedPosition;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignRecommedPositionRecord;
import org.springframework.stereotype.Repository;

import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignRecommedPositionDO;

/**
* @author xxx
* CampaignRecommedPositionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignRecommedPositionDao extends JooqCrudImpl<CampaignRecommedPositionDO, CampaignRecommedPositionRecord> {

    public CampaignRecommedPositionDao() {
        super(CampaignRecommedPosition.CAMPAIGN_RECOMMED_POSITION, CampaignRecommedPositionDO.class);
    }
}
