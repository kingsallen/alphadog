package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommedCompany;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignRecommedCompanyRecord;
import org.springframework.stereotype.Repository;

import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignRecommedCompanyDO;

/**
* @author xxx
* CampaignRecommedCompanyDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignRecommedCompanyDao extends JooqCrudImpl<CampaignRecommedCompanyDO, CampaignRecommedCompanyRecord> {


  public CampaignRecommedCompanyDao() {
      super(CampaignRecommedCompany.CAMPAIGN_RECOMMED_COMPANY, CampaignRecommedCompanyDO.class);
  }
}
