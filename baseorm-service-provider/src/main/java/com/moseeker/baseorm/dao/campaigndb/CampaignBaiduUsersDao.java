package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignBaiduUsers;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignBaiduUsersRecord;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignBaiduUsersDO;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* CampaignBaiduUsersDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignBaiduUsersDao extends JooqCrudImpl<CampaignBaiduUsersDO, CampaignBaiduUsersRecord> {


    public CampaignBaiduUsersDao() {
        super(CampaignBaiduUsers.CAMPAIGN_BAIDU_USERS, CampaignBaiduUsersDO.class);
    }
}
