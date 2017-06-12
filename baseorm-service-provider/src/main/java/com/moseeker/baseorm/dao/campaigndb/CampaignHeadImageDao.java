package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.db.campaigndb.tables.CampaignHeadImage;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignHeadImageRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignHeadImageDO;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* CampaignHeadImageDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class CampaignHeadImageDao extends StructDaoImpl<CampaignHeadImageDO, CampaignHeadImageRecord, CampaignHeadImage> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = CampaignHeadImage.CAMPAIGN_HEAD_IMAGE;
   }
}
