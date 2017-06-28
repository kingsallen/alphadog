package com.moseeker.position.service.fundationbs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moseeker.baseorm.dao.campaigndb.CampaignPcRecommendPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCmsMediaDao;
import com.moseeker.baseorm.dao.hrdb.HrCmsModuleDao;
import com.moseeker.baseorm.dao.hrdb.HrCmsPagesDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrResourceDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsMediaDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsModuleDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsPagesDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrResourceDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

/*
 * create by zzt
 * time 2017/6/28
 * 
 */
@Service
@Transactional
public class PositionPcService {
	@Autowired
	private CampaignPcRecommendPositionDao campaignPcRecommendPositionDao;
	@Autowired
	private JobPositionDao jobPositionDao;
	@Autowired
	private HrCompanyDao hrCompanyDao;
	@Autowired
	private HrCompanyAccountDao hrCompanyAccountDao;
	@Autowired
	private HrCmsPagesDao hrCmsPagesDao;
	@Autowired
	private HrCmsModuleDao hrCmsModuleDao;
	@Autowired
	private HrCmsMediaDao hrCmsMediaDao;
	@Autowired
	private HrResourceDao  hrResourceDao;
	@Autowired
	private HrCompanyConfDao hrCompanyConfDao;
	/*
	 * 获取pc首页职位推荐
	 */
	@CounterIface
	public Response getRecommendPositionC(){
		
		return null;
	}
	/*
	 * 分页获取推荐职位列表
	 */
	public  List<CampaignPcRecommendPositionDO> getPcRemmendPositionIdList(int page,int pageSize){
		Query query=new Query.QueryBuilder().setPageNum(page).setPageSize(pageSize).buildQuery();
		List<CampaignPcRecommendPositionDO> list=campaignPcRecommendPositionDao.getDatas(query);
		return list;
	}
	
	/*
	 * 根据推荐职位列表获取职位id
	 */
	private List<Integer> getPCRecommendIds(List<CampaignPcRecommendPositionDO> list){
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			CampaignPcRecommendPositionDO record=list.get(i);
			result.add(record.getPositionId());
		}
		return null;
	}
	/*
	 * 根据职位id列表获取职位列表
	 */
	private List<JobPositionDO> getPositionList(List<Integer> list){
		Condition condition=new Condition("id",list.toArray(),ValueOp.IN);
		Query query=new Query.QueryBuilder().where(condition).buildQuery();
		List<JobPositionDO> result=jobPositionDao.getDatas(query);
		return result;
	}
	
	/*
	 * 通过publisher的id列表获取公司的id列表
	 */
	public List<Integer> getHrCompanyAccountList(List<Integer> list){
		List<Integer> result=new ArrayList<Integer>();
		Query query=new Query.QueryBuilder().where(new Condition("account_id",list.toArray(),ValueOp.IN)).buildQuery();
		List<HrCompanyAccountDO> records=hrCompanyAccountDao.getDatas(query);
		if(records!=null&&records.size()>0){
			for(int i=0;i<records.size();i++){
				HrCompanyAccountDO accountDo=records.get(i);
				result.add(accountDo.getCompanyId());
			}
		}
		return result;
	}
	public List<HrCompanyConfDO> getHrCompanyConfByCompanyIds(List<Integer> ids){
		Query query=new Query.QueryBuilder().where(new Condition("company_id",ids.toArray(),ValueOp.IN)).and("newjd_status",2)
				.buildQuery();
		 List<HrCompanyConfDO> list=hrCompanyConfDao.getDatas(query);
		return list;
		
	}
	
	/*
	 * 获取所有的职位配置表
	 */
	public List<HrCmsPagesDO> getHrCmsPagesDOByPositionIds(List<Integer> ids,int type){
		Query query=new Query.QueryBuilder().where(new Condition("config_id",ids.toArray(),ValueOp.IN)).and("disable",0)
				.and("type",type).buildQuery();
		 List<HrCmsPagesDO> list=hrCmsPagesDao.getDatas(query);
		return list;
	}
	/*
	 * 获取所有的HrCmsPagesDO.id列表
	 */
	 private List<Integer> getCmsPageIdList(List<HrCmsPagesDO> list){
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrCmsPagesDO pageDO=list.get(i);
			 result.add(pageDO.getId());
		 }
		 return result;
	 }
	 
	 /*
	  * 根据page的列表，获取hrcmsmodule列表
	  */
	 public List<HrCmsModuleDO> getHrCmsModuleDOBypageIdList(List<Integer> ids){
		 Query query=new Query.QueryBuilder().where(new Condition("page_id",ids.toArray(),ValueOp.IN)).and("disable",0)
					.orderBy("page_id").orderBy("order").buildQuery();
			 List<HrCmsModuleDO> list=hrCmsModuleDao.getDatas(query);
			return list;
	 }
	 /*
	  * 获取modul.id列表
	  */
	 private List<Integer> getModuleIdList(List<HrCmsModuleDO> list){
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrCmsModuleDO moduleDO=list.get(i);
			 result.add(moduleDO.getId());
		 }
		 return result;
	 }
	 /*
	  * 根据module.id的列表获取HrCmsMdeia列表
	  */
	 public List<HrCmsMediaDO> getHrCmsMediaDOByModuleIdList(List<Integer> ids){
		 Query query=new Query.QueryBuilder().where(new Condition("module_id",ids.toArray(),ValueOp.IN)).and("disable",0).and("is_show",0)
					.orderBy("module_id").orderBy("order").buildQuery();
			 List<HrCmsMediaDO> list=hrCmsMediaDao.getDatas(query);
			return list;
	 }
	
	 /*
	  * 获取res.id
	  */
	 private List<Integer> getMediaIdList(List<HrCmsMediaDO> list){
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrCmsMediaDO mediaDO=list.get(i);
			 result.add(mediaDO.getResId());
		 }
		 return result;
	 }
	 /*
	  * 通过res.id列表获取资源列表
	  */
	 public List<HrResourceDO> getHrCmsResourceByIdList(List<Integer> ids){
			Query query=new Query.QueryBuilder().where(new Condition("id",ids.toArray(),ValueOp.IN)).and("disable",0).buildQuery();
		    List<HrResourceDO> list=hrResourceDao.getDatas(query);
			return list;
	 }
	 
}
