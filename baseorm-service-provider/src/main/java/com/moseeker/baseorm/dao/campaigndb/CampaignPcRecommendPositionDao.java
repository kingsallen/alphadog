package com.moseeker.baseorm.dao.campaigndb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPcRecommendPosition;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPcRecommendPositionRecord;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendPositionDO;
@Repository
public class CampaignPcRecommendPositionDao extends JooqCrudImpl<CampaignPcRecommendPositionDO, CampaignPcRecommendPositionRecord> {

	public CampaignPcRecommendPositionDao() {
		super(CampaignPcRecommendPosition.CAMPAIGN_PC_RECOMMEND_POSITION, CampaignPcRecommendPositionDO.class);
		// TODO Auto-generated constructor stub
	}
	public CampaignPcRecommendPositionDao(TableImpl<CampaignPcRecommendPositionRecord> table, Class<CampaignPcRecommendPositionDO> campaignPcRecommendPositionDOClass) {
		super(table, campaignPcRecommendPositionDOClass);
	}
}
