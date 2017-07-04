package com.moseeker.position.service.fundationbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.SelectOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.moseeker.baseorm.dao.campaigndb.CampaignPcRecommendCompanyDao;
import com.moseeker.baseorm.dao.campaigndb.CampaignPcRecommendPositionDao;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Select;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendCompanyDO;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcRecommendPositionDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsMediaDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsModuleDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsPagesDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrResourceDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
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
	@Autowired
	private CampaignPcRecommendCompanyDao campaignPcRecommendCompanyDao;
	@Autowired
	private JobPositionCityDao jobPositionCityDao;
	@Autowired
	private DictCityDao dictCityDao;
	/*
	 * 获取pc首页职位推荐
	 */
	@CounterIface
	public Response getRecommendPositionPC(int page,int pageSize){
		List<CampaignPcRecommendPositionDO>  list=campaignPcRecommendPositionDao.getPcRemmendPositionIdList(page,pageSize);
		if(list==null||list.size()==0){
			return ResponseUtils.success("");
		}
		List<Integer> positionIds=this.getPCRecommendPositionIds(list);
		List<Map<String,Object>> result=handleDataJDAndPosition(positionIds,3);
		Response res=null;
		if(StringUtils.isEmptyList(result)){
			 res= ResponseUtils.success("");
		}else{
			 res= ResponseUtils.success(result);
		}
		return res;
	}
	/*
	 * 根据推荐职位列表获取职位id
	 */
	private List<Integer> getPCRecommendPositionIds(List<CampaignPcRecommendPositionDO> list){
		if(list==null||list.size()==0){
			return null;
		}
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			CampaignPcRecommendPositionDO record=list.get(i);
			result.add(record.getPositionId());
		}
		return result;
	}

 	/*
 		获取publisher的列表
    */
	private List<Integer> getPublisherIdList(List<JobPositionDO> list){
		if(list==null||list.size()==0){
			return null;
		}
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
	private List<Map<String,Integer>> getPublisherCompanyId(List<Integer> list){
		if(list==null||list.size()==0){
			return null;
		}
		List<Map<String,Integer>> result=new ArrayList<Map<String,Integer>>();
		Map<String,Integer> map=null;
		Query query=new Query.QueryBuilder().where(new Condition("account_id",list.toArray(),ValueOp.IN)).buildQuery();
		List<HrCompanyAccountDO> records=hrCompanyAccountDao.getDatas(query);
		if(records!=null&&records.size()>0){
			for(int i=0;i<records.size();i++){
				HrCompanyAccountDO accountDo=records.get(i);
				int publisher=accountDo.getAccountId();
				int companyId=accountDo.getCompanyId();
				map=new HashMap<String,Integer>();
				map.put("companyId",companyId);
				map.put("publisher",publisher);
				result.add(map);
			}
		}
		return result;
	}
	/*
	 * 通过publisher的id列表获取公司的id列表
	 */
	public List<Integer> getHrCompanyIdList(List<Integer> list){
		if(list==null||list.size()==0){
			return null;
		}
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
		获取所有有jd的公司
	 */
	public List<Integer> getJdCompanyIds(List<HrCompanyConfDO>  list){
		if(list==null||list.size()==0){
			return null;
		}
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
		if(list==null||list.size()==0){
			return null;
		}
		Condition condition=new Condition("id",list.toArray(),ValueOp.IN);
		Query query=new Query.QueryBuilder().where(condition).and("disable",0).and("is_show",1).buildQuery();
		List<HrTeamDO> result=hrTeamDao.getDatas(query);
		return result;
	}
	/*
	 * 获取团队数量，通过公司的List<id>
	 */
	public List<Map<String,Object>> getTeamNum(List<Integer> list){
		if(list==null||list.size()==0){
			return null;
		}
		Query query=new Query.QueryBuilder().select(new Select("id", SelectOp.COUNT)).select("company_id")
				.where(new Condition("company_id",list.toArray(),ValueOp.IN))
				.and("disale",0).and("is_show",1)
				.groupBy("company_id").buildQuery();
		List<Map<String,Object>> result=hrTeamDao.getMaps(query);
		return result;
	}
	/*
	   获取团队列表
	 */
	public List<Integer> getTeamIdList(List<JobPositionDO> list){
		if(list==null||list.size()==0){
			return null;
		}
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			JobPositionDO positionDO=list.get(i);
			result.add(positionDO.getTeamId());
		}
		return result;
	}

	/*
	 * 获取所有的HrCmsPagesDO.id列表
	 */
	 private List<Integer> getCmsPageIdList(List<HrCmsPagesDO> list){
		 if(list==null||list.size()==0){
				return null;
			}
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrCmsPagesDO pageDO=list.get(i);
			 result.add(pageDO.getId());
		 }
		 return result;
	 }
	
	 /*
	  * 获取modul.id列表
	  */
	 private List<Integer> getModuleIdList(List<HrCmsModuleDO> list){
		 if(list==null||list.size()==0){
				return null;
			}
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrCmsModuleDO moduleDO=list.get(i);
			 result.add(moduleDO.getId());
		 }
		 return result;
	 }	
	 /*
	  * 获取res.id
	  */
	 private List<Integer> getResIdList(List<HrCmsMediaDO> list){
		 if(list==null||list.size()==0){
				return null;
			}
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrCmsMediaDO mediaDO=list.get(i);
			 result.add(mediaDO.getResId());
		 }
		 return result;
	 }

	 public List<Map<String,Object>> getResourceByPositionId(List<Integer> ids,int type){
		 if(ids==null||ids.size()==0){
			return null;
		 }
		 List<HrCmsPagesDO> list1=hrCmsPagesDao.getHrCmsPagesByIds(ids,type);
		 if(list1==null||list1.size()==0){
			 return null;
		 }
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
		 List<HrCmsModuleDO> list2=hrCmsModuleDao.getHrCmsModuleDOBypageIdList(pageIds);
		 if(list2==null||list2.size()==0){
			 return null;
		 }
		 for(int i=0;i<list2.size();i++){
			 HrCmsModuleDO moduleDO=list2.get(i);
			 int pageId=moduleDO.getPageId();
			 int ModuleId=moduleDO.getId();
			 for(int j=0;j<maps1.size();j++){
				 Map<String,Object> map1=maps1.get(j);
				 Integer originPageId=(Integer)map1.get("pageId");
				 if(originPageId==pageId){
				 	if(map1.get("moudleId")==null) {
						map1.put("moduleId",ModuleId);
						break;
					}
				 }
			 }
		 }
		 List<Integer> moduleIds=this.getModuleIdList(list2);
		 if(moduleIds==null||moduleIds.size()==0){
			 return null;
		 }
		 List<HrCmsMediaDO> list3=hrCmsMediaDao.getHrCmsMediaDOByModuleIdList(moduleIds);
		 if(list3==null||list3.size()==0){
			 return null;
		 }
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
		 if(resIds==null||resIds.size()==0){
			 return null;
		 }
		 List<HrResourceDO> list4=hrResourceDao.getHrCmsResourceByIdList(resIds);
		 if(list4==null||list4.size()==0){
			 return null;
		 }
		 for(int i=0;i<list4.size();i++){
			 HrResourceDO resourceDO=list4.get(i);
			 int id=resourceDO.getId();
			 int resType=resourceDO.getResType();
			 if(resType==0) {
				 for (int j = 0; j < maps1.size(); j++) {
					 Map<String, Object> map3 = maps1.get(j);
					 Integer resId = (Integer)map3.get("resId");
					 if (resId == id) {
						 if (map3.get("imgUrl")==null){
						 	String imgUrl=resourceDO.getResUrl();
						 	map3.put("imgUrl",imgUrl);
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
	 public List<Map<String,Object>> handleCompanyAndPositionData(List<JobPositionDO> positionList, List<HrCompanyDO> companyList,List<HrTeamDO> teamList
			 ,List<Map<String,Integer>> publisherAndCompanyId,Map<String,List<String>> posittionCitys){
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 if(positionList==null||positionList.size()==0){
			 return null;
		 }
		 for(int i=0;i<positionList.size();i++){
			 JobPositionDO positionDo=positionList.get(i);
			 int publisher=positionDo.getPublisher();
			 int teamId=positionDo.getTeamId();
			 int positionId=positionDo.getId();
			 Map<String,Object> map=new HashMap<String,Object>();
			 if(!StringUtils.isEmptyList(companyList)){
				 for(int j=0;j<companyList.size();j++){
					 HrCompanyDO companyDO=companyList.get(j);
					 int companyId=companyDO.getId();
					// 本出如此做是为了过滤掉已经删除的子公司的信息
					 if(!StringUtils.isEmptyList(publisherAndCompanyId)){
						 for(int z=0;z<publisherAndCompanyId.size();z++){
							 Map<String,Integer> maps=publisherAndCompanyId.get(z);
							 Integer oripublisher=maps.get("publisher");
							 Integer oriCompanyid=maps.get("companyId");
							 if(oripublisher!=null&&oripublisher==publisher&&oriCompanyid!=null&&oriCompanyid==companyId){
								 map.put("position",positionDo);
								 map.put("company",companyDO);
								 break;
							 }
						 }
					 }
				 }
				 if(!posittionCitys.isEmpty()){
					 if(posittionCitys.get(positionId+"")!=null){
						 map.put("cityList", posittionCitys.get(positionId+""));
					 }
				 }
			 }
			
			 // 本出如此做是为了过滤掉已经删除的子公司的信息
			 if(!map.isEmpty()){
				 for(HrTeamDO teamDo:teamList){
				 	int id=teamDo.getId();
					 if(teamId==id){
						 map.put("team",teamDo);
						 break;
					 }
				 }
				 list.add(map);
			 }
		 }

		 return list;
	 }
	 /*
	 	获取有jd的团队列表
	  */
	 public List<Integer> getJdTeamIdList(List<Integer> companyIds,List<HrTeamDO> list){
		 List<Integer> result=new ArrayList<Integer>();
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 for(HrTeamDO teamDO :list){
		 	int companyId=teamDO.getCompanyId();
		 	int teamId=teamDO.getId();
		 	if(!StringUtils.isEmptyList(companyIds)){
		 		for(Integer id:companyIds){
			 		if(id==companyId){
						result.add(teamId);
						break;
					}
				}
		 	}
		 	

		 }
		 return result;
	 }
	 /*
	 	处理position 或者 Team jd页数据，获取首张图片
	  */
	 public List<Map<String,Object>> handlePositionJdPic(List<HrTeamDO> teamList,List<Integer> companyIds,int type){
		 List<HrCompanyConfDO> AccountList=hrCompanyConfDao.getHrCompanyConfByCompanyIds(companyIds);
		 List<Integer> jdCompanyids=this.getJdCompanyIds(AccountList);
		 List<Integer> jdTeamids=this.getJdTeamIdList(jdCompanyids,teamList);
		 List<Map<String,Object>> list=getResourceByPositionId(jdTeamids,type);
	 	return list;
	 }
	 //根据positionid列表获取list的jobPositioncity
	 public List<JobPositionCityDO> getJObPositionCity(List<Integer> positionIds){
		 if(StringUtils.isEmptyList(positionIds)){
			 return null;
		 }
		 Query query=new Query.QueryBuilder().where(new Condition("pid",positionIds.toArray(),ValueOp.IN)).buildQuery();
		 return jobPositionCityDao.getDatas(query);
	 }
	 //获取职位的城市格式是map形式，（positionId，List<String>)形式
	 public Map<String,List<String>> handlePositionCity(List<Integer> list){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 List<JobPositionCityDO>jobPositionCityList=this.getJObPositionCity(list);
		 if(StringUtils.isEmptyList(jobPositionCityList)){
			 return null;
		 }
		 List<Integer> codes=this.getPositionCodes(jobPositionCityList);
		 List<DictCityDO> citys=getDictCity(codes);
		 if(StringUtils.isEmptyList(citys)){
			 return null;
		 }
		 Map<String,List<String>> map=new HashMap<String,List<String>>();
		 for(JobPositionCityDO jobDo:jobPositionCityList){
			 List<String> positionCity=new ArrayList<String>();
			 int positionId=jobDo.getPid();
			 int code=jobDo.getCode();
			 for(DictCityDO city:citys){
				 int code1=city.getCode();
				 String name=city.getName();
				 if(code==code1){
					 positionCity.add(name);
				 }
			 }
			 if(!StringUtils.isEmptyList(positionCity)){
				 map.put(positionId+"",positionCity);
			 }
		 }
		 return map;
	 }
	 //获取position的city code
	 public List<Integer> getPositionCodes(List<JobPositionCityDO> list){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 List<Integer> codes=new ArrayList<Integer>();
		 for(JobPositionCityDO jobCity:list){
			 codes.add(jobCity.getCode());
		 }
		 return codes;
	 }
	 //获取list的dictcity数据
	 public List<DictCityDO> getDictCity(List<Integer> codes){
		 if(StringUtils.isEmptyList(codes)){
			 return null;
		 }
		 Query query=new Query.QueryBuilder().where(new Condition("code",codes.toArray(),ValueOp.IN)).buildQuery();
		 return dictCityDao.getDatas(query);
	 }
	/*
	 总体上处理数据
	  */
	 public List<Map<String,Object>> handleDataJDAndPosition(List<Integer> positionIds,int type){
		 if(StringUtils.isEmptyList(positionIds)){
			 return  null;
		 }
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 List<JobPositionDO> positionList=jobPositionDao.getPositionList(positionIds);
		 List<Integer> publisherIds=this.getPublisherIdList(positionList);
		 List<Integer> compantIds=this.getHrCompanyIdList(publisherIds);
		 List<HrCompanyDO> companyList=hrCompanyDao.getHrCompanyByCompanyIds(compantIds);
		 companyList=this.filterCompanyList(companyList);
		 List<Map<String,Integer>> publisherAndCompanyId=getPublisherCompanyId(publisherIds);
		 List<Integer> teamIds=this.getTeamIdList(positionList);
		 List<HrTeamDO> teamList=this.getTeamList(teamIds);
		 Map<String,List<String>> positionCitys=this.handlePositionCity(positionIds);
		 list=this.handleCompanyAndPositionData(positionList,companyList,teamList,publisherAndCompanyId,positionCitys);
		 List<Map<String,Object>> jdpictureList=this.handlePositionJdPic(teamList,compantIds,type);
		
		 if(!StringUtils.isEmptyList(jdpictureList)){
			 for(Map<String,Object> map:jdpictureList){
				 	Integer configId=(Integer)map.get("configId");
				 	String picture=(String)map.get("imgUrl");
				 	for(Map<String,Object> map1:list){
				 		HrTeamDO teamDO=(HrTeamDO)map1.get("team");
				 		int id=teamDO.getId();
				 		if(id==configId){
				 			map1.put("jdPic",picture);
						}
					 }
			 }
		 
		 }
	 	return list;
	 }
	 
	//====================================================== 
	 //获取仟寻推荐公司和相关职位信息接口
	 public Response getQXRecommendCompanyList(){
		 List<CampaignPcRecommendCompanyDO>  CampaignPcRecommendCompanyList=campaignPcRecommendCompanyDao.getCampaignPcRecommendCompanyList();
		 if(StringUtils.isEmptyList(CampaignPcRecommendCompanyList)){
			 return  null;
		 }
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 for(CampaignPcRecommendCompanyDO dO:CampaignPcRecommendCompanyList){
			 Map<String,Object> map=new HashMap<String,Object>();
			 String companyIds=dO.getCompanyIds();
			 map.put("moduleName", dO.getModuleName());
			 map.put("moduleDescription", dO.getModuleDescription());
			 List<Integer> companyIdList=new ArrayList<Integer>();
			 String [] ids=companyIds.split(",");
			 for(int i=0;i<ids.length;i++){
				 companyIdList.add(Integer.parseInt(ids[i]));
			 }
			 List<Map<String,Object>> result=handleRecommendPcCompanyData(companyIdList);
			 map.put("data", result);
			 list.add(map);
		 }
		 Response res= ResponseUtils.success(list);
		 return res;
	 }

	 /*
	  * 获取所推荐公司的publisher列表
	  */
	 public Map<String,List<Integer>> getCompanyAccountListByCompanyIds(List<Integer> companyIds){
		 if(StringUtils.isEmptyList(companyIds)){
			 return  new HashMap<String,List<Integer>>();
		 }
		 Map<String,List<Integer>> map=new HashMap<String,List<Integer>>();
		 Query query=new Query.QueryBuilder().where(new Condition("company_id",companyIds.toArray(),ValueOp.IN)).buildQuery();
		 List<HrCompanyAccountDO> list=hrCompanyAccountDao.getDatas(query);
		 for(HrCompanyAccountDO accountDO:list){
			 int companyId=accountDO.getCompanyId();
			 int pulisher=accountDO.getAccountId();
			 if(map.get(companyId+"")!=null){
				 List<Integer> publisherList=map.get(companyId+"");
				 publisherList.add(pulisher);
			 }else{
				 List<Integer> publisherList=new ArrayList<Integer>();
				 publisherList.add(pulisher);
				 map.put(companyId+"",publisherList);
			 }
		 }
		 return map;
	 }
	 /*
	  * 获取该公司id列表下的职位数量
	  */
	 public int getPositionNum(List<Integer> publisherIds){
		 if(StringUtils.isEmptyList(publisherIds)){
			 return  0;
		 }
		 Query query=new Query.QueryBuilder()
				 .where(new Condition("publisher",publisherIds.toArray(),ValueOp.IN))
				 .and("status",0).buildQuery();
		 int num=jobPositionDao.getCount(query);
		 return num;
	 }
	 /*
	  *  获取companyIds的list集合
	  */
	 public List<Integer> getCompanyIds(List<HrCompanyDO> list){
		 if(StringUtils.isEmptyList(list)){
			 return  null;
		 }
		 List<Integer> result=new ArrayList<Integer>();
			for(int i=0;i<list.size();i++){
				HrCompanyDO companyDO=list.get(i);
				result.add(companyDO.getId());
			}
			return result;
	 }
	 /*
	  * 处理数据获取千寻推荐企业严选数据
	  */
	 public List<Map<String,Object>> handleRecommendPcCompanyData(List<Integer> companyIds){
		 if(StringUtils.isEmptyList(companyIds)){
			 return  null;
		 }
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 List<HrCompanyDO> companyList=hrCompanyDao.getHrCompanyByCompanyIds(companyIds);
		 if(StringUtils.isEmptyList(companyList)){
			 return  null;
		 }
		 Map<String,List<Integer>> companyPulisher=getCompanyAccountListByCompanyIds(companyIds);
		 List<Map<String,Object>> mapTeamNum=getTeamNum(companyIds);
		 companyList=filterCompanyList(companyList);
		 if(StringUtils.isEmptyList(companyList)){
			 return  null;
		 }
		 List<Integer> companyids=this.getCompanyIds(companyList);
		 List<Map<String,Object>> jdlist=getResourceByPositionId(companyids,1);
		 Map<String,Object> map=null;
		 for(int i=0;i<companyList.size();i++){
			 map=new HashMap<String,Object>();
			 HrCompanyDO companyDO=companyList.get(i);
			 int companyId=companyDO.getId();
			 map.put("company", companyDO);
			 List<Integer> publisherIds=companyPulisher.get(companyId+"");
			 if(publisherIds!=null&&publisherIds.size()>0){
				int num=this.getPositionNum(publisherIds);
				map.put("positionNum", num);
			 }else{
				map.put("positionNum", 0);
			 }
			 if(!StringUtils.isEmptyList(companyList)){
				 for(Map teamMap:mapTeamNum){
					 int companyId1=(int) teamMap.get("company_id");
					 if(companyId1==companyId){
						 int teamNum=(int) teamMap.get("id_count");
						 map.put("teamNum", teamNum);
						 break;
					 }
				 }
			 }
			 if(map.get("teamNum")==null){
				 map.put("teamNum",0);
			 }
			 if(!StringUtils.isEmptyList(jdlist)){
				 for(Map<String,Object> jdmap:jdlist){
					 Integer configId=(Integer) jdmap.get("configId");
					 if(companyId==configId){
						 if(jdmap.get("imgUrl")!=null){
							 map.put("jdPic", jdmap.get("imgUrl"));
						 }
						 break;
					 }
				 }
			 }
			 
			 list.add(map);
		 }
		 return list;
	 }
	 
	 /*
	  * 删除已经删除的公司
	  */
	 public List<HrCompanyDO> filterCompanyList(List<HrCompanyDO> list){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 List<HrCompanyDO> newList=new ArrayList<HrCompanyDO>();
		 for(HrCompanyDO companyDO:list){
			 int parentId=companyDO.getParentId();
			 if(parentId!=0){
				 int disable=companyDO.getDisable();
				 if(disable==0){
					 newList.add(companyDO);
				 }
			 }else{
				 newList.add(companyDO);
			 }
		 }
		 return newList;
	 }
}
