package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.db.campaigndb.tables.CampaignBaiduUsers;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignBaiduUsersRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignBaiduUsersDO;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* CampaignBaiduUsersDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignBaiduUsersDao extends StructDaoImpl<CampaignBaiduUsersDO, CampaignBaiduUsersRecord, CampaignBaiduUsers> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS;
   }
}
