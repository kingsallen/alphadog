package com.moseeker.baseorm.dao.campaigndb;

import java.util.List;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPcRecommendCompany;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPcRecommendCompanyRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendCompanyDO;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendPositionDO;

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
	
	 /*
	  * 获取所有的千寻推荐公司
	  */
	 public List<CampaignPcRecommendCompanyDO> getCampaignPcRecommendCompanyList(int page,int pageSize){
		 Query query=new Query.QueryBuilder().where("disable",0).setPageNum(page).setPageSize(pageSize).buildQuery();
		 List<CampaignPcRecommendCompanyDO> list=this.getDatas(query);
		 return list;
	 }
	
}
