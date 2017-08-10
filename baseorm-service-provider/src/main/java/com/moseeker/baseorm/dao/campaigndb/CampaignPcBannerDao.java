package com.moseeker.baseorm.dao.campaigndb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPcBanner;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPcBannerRecord;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcBannerDO;
@Repository
public class CampaignPcBannerDao extends JooqCrudImpl<CampaignPcBannerDO, CampaignPcBannerRecord> {
	public CampaignPcBannerDao() {
	        super(CampaignPcBanner.CAMPAIGN_PC_BANNER, CampaignPcBannerDO.class);
	    }
	public CampaignPcBannerDao(TableImpl<CampaignPcBannerRecord> table, Class<CampaignPcBannerDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}

  
}
