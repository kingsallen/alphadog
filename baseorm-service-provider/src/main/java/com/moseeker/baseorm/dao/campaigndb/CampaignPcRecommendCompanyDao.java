package com.moseeker.baseorm.dao.campaigndb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPcRecommendCompany;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPcRecommendCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendCompanyDO;

@Repository
public class CampaignPcRecommendCompanyDao extends JooqCrudImpl<CampaignPcRecommendCompanyDO, CampaignPcRecommendCompanyRecord> {

	public CampaignPcRecommendCompanyDao(){
		super(CampaignPcRecommendCompany.CAMPAIGN_PC_RECOMMEND_COMPANY, CampaignPcRecommendCompanyDO.class);
	}
	public CampaignPcRecommendCompanyDao(TableImpl<CampaignPcRecommendCompanyRecord> table,
			Class<CampaignPcRecommendCompanyDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}

}
