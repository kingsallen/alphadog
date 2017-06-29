package com.moseeker.position.service.fundationbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.SelectOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moseeker.baseorm.dao.campaigndb.CampaignPcRecommendPositionDao;
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
	@Autowired
	private HrTeamDao hrTeamDao;

	/*
	 * 获取pc首页职位推荐
	 */
	@CounterIface
	public Response getRecommendPositionPC(int page,int pageSize){
		List<CampaignPcRecommendPositionDO>  list=getPcRemmendPositionIdList(page,pageSize);
		List<Integer> positionids=this.getPCRecommendPositionIds(list);
		List<Map<String,Object>> result=handleDataJDAndPosition(positionids,3);
		Response res= ResponseUtils.success(result);
		return res;
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
	private List<Integer> getPCRecommendPositionIds(List<CampaignPcRecommendPositionDO> list){
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			CampaignPcRecommendPositionDO record=list.get(i);
			result.add(record.getPositionId());
		}
		return result;
	}
	/*
	 * 根据职位id列表获取职位列表
	 */
	public List<JobPositionDO> getPositionList(List<Integer> list){
		Condition condition=new Condition("id",list.toArray(),ValueOp.IN);
		Query query=new Query.QueryBuilder().where(condition).and("status",0).buildQuery();
		List<JobPositionDO> result=jobPositionDao.getDatas(query);
		return result;
	}
 	/*
 		获取publisher的列表
    */
	private List<Integer> getPublisherIdList(List<JobPositionDO> list){
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			JobPositionDO positionDO=list.get(i);
			result.add(positionDO.getPublisher());
		}
		return result;
	}
	/*
	获取publisher和companyId的对应关系集合
	 */
	private Map<String,Integer> getPublisherCompanyId(List<Integer> list){
		Map<String,Integer> map=new HashMap<String,Integer>();
		Query query=new Query.QueryBuilder().where(new Condition("account_id",list.toArray(),ValueOp.IN)).buildQuery();
		List<HrCompanyAccountDO> records=hrCompanyAccountDao.getDatas(query);
		if(records!=null&&records.size()>0){
			for(int i=0;i<records.size();i++){
				HrCompanyAccountDO accountDo=records.get(i);
				int publisher=accountDo.getAccountId();
				int companyId=accountDo.getCompanyId();
				map.put(companyId+"",publisher);
			}
		}
		return map;
	}
	/*
	 * 通过publisher的id列表获取公司的id列表
	 */
	public List<Integer> getHrCompanyIdList(List<Integer> list){
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
	/*
	获取公司信息的列表
	 */
	public List<HrCompanyDO> getHrCompanyByCompanyIds(List<Integer> ids){
		Query query=new Query.QueryBuilder().where(new Condition("id",ids.toArray(),ValueOp.IN)).buildQuery();
		List<HrCompanyDO> list=hrCompanyDao.getDatas(query);
		return list;

	}
	/*
	获取hrcompanyConf的列表
	 */
	public List<HrCompanyConfDO> getHrCompanyConfByCompanyIds(List<Integer> ids){
		Query query=new Query.QueryBuilder().where(new Condition("company_id",ids.toArray(),ValueOp.IN)).and("newjd_status",2)
				.buildQuery();
		 List<HrCompanyConfDO> list=hrCompanyConfDao.getDatas(query);
		return list;
		
	}
	/*
		获取所有有jd的公司
	 */
	public List<Integer> getJdCompanyIds(List<HrCompanyConfDO>  list){
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			HrCompanyConfDO confDO=list.get(i);
			result.add(confDO.getCompanyId());
		}
		return result;
	}
	/*
 	* 获取团队
 	*/
	public List<HrTeamDO> getTeamList(List<Integer> list){
		Condition condition=new Condition("id",list.toArray(),ValueOp.IN);
		Query query=new Query.QueryBuilder().where(condition).and("disable",0).and("is_show",1).buildQuery();
		List<HrTeamDO> result=hrTeamDao.getDatas(query);
		return result;
	}
	/*
	 * 获取团队数量，通过公司的List<id>
	 */
	public List<Map> getTeamNum(List<Integer> list){
		Query query=new Query.QueryBuilder().select("id", SelectOp.COUNT).select("id")
				.where(new Condition("id",list.toArray(),ValueOp.IN))
				.and("disale",0).and("is_show",1)
				.groupBy("id").buildQuery();
		List<Map> result=hrTeamDao.getDatas(query, Map.class);
		return result;
	}
	/*
	   获取团队列表
	 */
	public List<Integer> getTeamIdList(List<JobPositionDO> list){
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			JobPositionDO positionDO=list.get(i);
			result.add(positionDO.getId());
		}
		return result;
	}
	/*
	 * 获取所有的职位配置表
	 */
	public List<HrCmsPagesDO> getHrCmsPagesByIds(List<Integer> ids,int type){
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
	 private List<Integer> getResIdList(List<HrCmsMediaDO> list){
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
	 public List<Map<String,Object>> getResourceByPositionId(List<Integer> ids,int type){
		 List<HrCmsPagesDO> list1=this.getHrCmsPagesByIds(ids,type);
		 List<Map<String,Object>> maps1=new ArrayList<Map<String,Object>>();
		 Map<String,Object> map=null;
		 for(int i=0;i<list1.size();i++){
			 HrCmsPagesDO pagesDO=list1.get(i);
			 int configId=pagesDO.getConfigId();
			 int pageId=pagesDO.getId();
			 map=new HashMap<String,Object>();
			 map.put("pageId",pageId);
			 map.put("configId",configId);
			 maps1.add(map);
		 }
		 List<Integer> pageIds=this.getCmsPageIdList(list1);
		 List<HrCmsModuleDO> list2=this.getHrCmsModuleDOBypageIdList(pageIds);
		 for(int i=0;i<list2.size();i++){
			 HrCmsModuleDO moduleDO=list2.get(i);
			 int pageId=moduleDO.getPageId();
			 int ModuleId=moduleDO.getId();
			 for(int j=0;j<maps1.size();j++){
				 Map<String,Object> map1=maps1.get(j);
				 Integer originPageId=(Integer)map1.get("pageId");
				 if(originPageId==pageId){
				 	if(map1.get("moudleId")==null) {
						map1.put("moudleId",ModuleId);
						break;
					}
				 }
			 }
		 }
		 List<Integer> moduleIds=this.getModuleIdList(list2);
		 List<HrCmsMediaDO> list3=this.getHrCmsMediaDOByModuleIdList(moduleIds);
		 for(int i=0;i<list3.size();i++){
			 HrCmsMediaDO mediaDO=list3.get(i);
			 int moduleId=mediaDO.getModuleId();
			 int id=mediaDO.getId();
			 int resId=mediaDO.getResId();
			 for(int j=0;j<maps1.size();j++){
			 	Map<String,Object> map2=maps1.get(j);
			 	Integer originModuleId=(Integer)map2.get("moduleId");
			 	if(originModuleId==moduleId) {
					if (map2.get("mediaId") == null) {
						map2.put("mediaId",id);
						map2.put("resId",resId);
						break;
					}
				}
			 }
		 }
		 List<Integer> resIds=this.getResIdList(list3);
		 List<HrResourceDO> list4=this.getHrCmsResourceByIdList(resIds);
		 for(int i=0;i<list4.size();i++){
			 HrResourceDO resourceDO=list4.get(i);
			 int id=resourceDO.getId();
			 int resType=resourceDO.getResType();
			 if(resType==0) {
				 for (int j = 0; j < maps1.size(); j++) {
					 Map<String, Object> map3 = maps1.get(j);
					 Integer resId = (Integer)map3.get("resId");
					 if (resId == id) {
						 if (map3.get("resId")==null){
						 	String imgUrl=resourceDO.getResUrl();
						 	map.put("imgUrl",imgUrl);
						 }
					 }
				 }
			 }
		 }

		 return maps1;
	 }
	 /*
	 处理position和company的数据
	  */
	 public List<Map<String,Object>> handleCompanyAndPositionData(List<JobPositionDO> positionList, List<HrCompanyDO> companyList,List<HrTeamDO> teamList,Map<String,Integer> publisherAndCompanyId){
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 for(int i=0;i<positionList.size();i++){
			 JobPositionDO positionDo=positionList.get(i);
			 int publisher=positionDo.getPublisher();
			 int teamId=positionDo.getTeamId();
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("position",positionDo);
			 for(int j=0;j<companyList.size();j++){
				 HrCompanyDO companyDO=companyList.get(j);
				 int companyId=companyDO.getId();
				 Integer oripublisher=publisherAndCompanyId.get(companyId+"");
				 if(oripublisher!=null&&oripublisher==publisher){
					 map.put("company",companyDO);
					 break;
				 }
			 }
			 for(HrTeamDO teamDo:teamList){
			 	int id=teamDo.getId();
				 if(teamId==id){
					 map.put("team",teamDo);
					 break;
				 }
			 }
			 list.add(map);
		 }

		 return list;
	 }
	 /*
	 	获取有jd的团队列表
	  */
	 public List<Integer> getJdTeamIdList(List<Integer> companyIds,List<HrTeamDO> list){
		 List<Integer> result=new ArrayList<Integer>();
		 for(HrTeamDO teamDO :list){
		 	int companyId=teamDO.getCompanyId();
		 	int teamId=teamDO.getId();
		 	for(Integer id:companyIds){
		 		if(id==companyId){
					result.add(teamId);
					break;
				}
			}

		 }
		 return result;
	 }
	 /*
	 	处理jd页数据，获取首张图片
	  */
	 public List<Map<String,Object>> handleJdPic(List<HrTeamDO> teamList,List<Integer> companyIds,int type){
		 List<HrCompanyConfDO> AccountList=this.getHrCompanyConfByCompanyIds(companyIds);
		 List<Integer> jdCompanyids=this.getJdCompanyIds(AccountList);
		 List<Integer> jdTeamids=this.getJdTeamIdList(jdCompanyids,teamList);
		 List<Map<String,Object>> list=getResourceByPositionId(jdTeamids,type);
	 	return list;
	 }
	/*
	 总体上处理数据
	  */
	 public List<Map<String,Object>> handleDataJDAndPosition(List<Integer> positionIds,int type){
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 List<JobPositionDO> positionList=this.getPositionList(positionIds);
		 List<Integer> publisherIds=this.getPublisherIdList(positionList);
		 List<Integer> compantIds=this.getHrCompanyIdList(publisherIds);
		 List<HrCompanyDO> companyList=this.getHrCompanyByCompanyIds(compantIds);
		 Map<String,Integer> publisherAndCompanyId=getPublisherCompanyId(publisherIds);
		 List<Integer> teamIds=this.getTeamIdList(positionList);
		 List<HrTeamDO> teamList=this.getTeamList(teamIds);
		 list=this.handleCompanyAndPositionData(positionList,companyList,teamList,publisherAndCompanyId);
		 List<Map<String,Object>> jdpictureList=this.handleJdPic(teamList,compantIds,type);
		 for(Map<String,Object> map:jdpictureList){
		 	Integer configId=(Integer)map.get("configId");
		 	String picture=(String)map.get("imgUrl");
		 	for(Map<String,Object> map1:list){
		 		HrTeamDO teamDO=(HrTeamDO)map1.get("team");
		 		int id=teamDO.getId();
		 		if(id==configId){
		 			map.put("jdPic",picture);
				}
			 }
		 }
	 	return list;
	 }

}
