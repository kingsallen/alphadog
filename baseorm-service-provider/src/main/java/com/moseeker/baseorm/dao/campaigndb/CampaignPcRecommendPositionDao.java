package com.moseeker.baseorm.dao.campaigndb;

import java.util.List;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPcRecommendPosition;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPcRecommendPositionRecord;
import com.moseeker.common.util.query.Query;
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
	/*
	 * 分页获取推荐职位列表
	 */
	public  List<CampaignPcRecommendPositionDO> getPcRemmendPositionIdList(int page,int pageSize){
		Query query=new Query.QueryBuilder().where("disable",0).setPageNum(page).setPageSize(pageSize).buildQuery();
		List<CampaignPcRecommendPositionDO> list=this.getDatas(query);
		return list;
	}
}
