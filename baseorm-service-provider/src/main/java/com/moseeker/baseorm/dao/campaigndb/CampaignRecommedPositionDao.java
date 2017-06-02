package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommedPosition;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignRecommedPositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignRecommedPositionDO;

/**
* @author xxx
* CampaignRecommedPositionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignRecommedPositionDao extends StructDaoImpl<CampaignRecommedPositionDO, CampaignRecommedPositionRecord, CampaignRecommedPosition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = CampaignRecommedPosition.CAMPAIGN_RECOMMED_POSITION;
   }
}
