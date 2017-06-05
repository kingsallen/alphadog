package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUserrecommendedPositions;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignEdmUserrecommendedPositionsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignEdmUserrecommendedPositionsDO;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* CampaignEdmUserrecommendedPositionsDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignEdmUserrecommendedPositionsDao extends StructDaoImpl<CampaignEdmUserrecommendedPositionsDO, CampaignEdmUserrecommendedPositionsRecord, CampaignEdmUserrecommendedPositions> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = CampaignEdmUserrecommendedPositions.CAMPAIGN_EDM_USERRECOMMENDED_POSITIONS;
   }
}
