package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUsers;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignEdmUsersRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignEdmUsersDO;
import org.springframework.stereotype.Repository;

/**
 * @author xxx
 * CampaignEdmUsersDao &#x5b9e;&#x73b0;&#x7c7b; &#xff08;groovy &#x751f;&#x6210;&#xff09;
 * 2017-04-12
 */
@Repository
public class CampaignEdmUsersDao extends StructDaoImpl<CampaignEdmUsersDO, CampaignEdmUsersRecord, CampaignEdmUsers> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = CampaignEdmUsers.CAMPAIGN_EDM_USERS;
   }
}
