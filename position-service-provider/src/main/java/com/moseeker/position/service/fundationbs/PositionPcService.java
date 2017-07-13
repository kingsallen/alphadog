package com.moseeker.position.service.fundationbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.SelectOp;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
	public Response getRecommendPositionPC(int page,int pageSize) throws TException{
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
	private List<Map<String,Integer>> getPublisherCompanyId(List<HrCompanyAccountDO> list){
		if(list==null||list.size()==0){
			return null;
		}
		List<Map<String,Integer>> result=new ArrayList<Map<String,Integer>>();
		Map<String,Integer> map=null;
			for(HrCompanyAccountDO DO:list){
				int publisher=DO.getAccountId();
				int companyId=DO.getCompanyId();
				map=new HashMap<String,Integer>();
				map.put("companyId",companyId);
				map.put("publisher",publisher);
				result.add(map);
			}
		return result;
	}
	//获取hrcompanyAccount列表
	public List<HrCompanyAccountDO> getCompanyAccountList(List<Integer> list){
		Query query=new Query.QueryBuilder().where(new Condition("account_id",list.toArray(),ValueOp.IN)).buildQuery();
		List<HrCompanyAccountDO> records=hrCompanyAccountDao.getDatas(query);
		return records;
	}
	/*
	 * 通过publisher的id列表获取公司的id列表
	 */
	public List<Integer> getHrCompanyIdList(List<HrCompanyAccountDO> list){
		if(list==null||list.size()==0){
			return null;
		}
		List<Integer> result=new ArrayList<Integer>();
		for(HrCompanyAccountDO DO:list){
			result.add(DO.getCompanyId());
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
	 private List<Integer> getResIdListByTeam(List<HrTeamDO> list){
		 if(list==null||list.size()==0){
				return null;
			}
		 List<Integer> result=new ArrayList<Integer>();
		 for(int i=0;i<list.size();i++){
			 HrTeamDO teamDO=list.get(i);
			 result.add(teamDO.getResId());
		 }
		 return result;
	 }
	 public List<Map<String,Object>> HandleCmsResource(List<Integer> ids,int type) throws TException{
		 if(ids==null||ids.size()==0){
			return null;
		 }
		 List<HrCmsPagesDO> list1=hrCmsPagesDao.getHrCmsPagesByIds(ids,type);
		 if(list1==null||list1.size()==0){
			 return null;
		 }
		 List<Map<String,Object>> maps1=new ArrayList<Map<String,Object>>();
		 this.handlePageListData(maps1,list1);
		 List<Integer> pageIds=this.getCmsPageIdList(list1);
		 List<HrCmsModuleDO> list2=hrCmsModuleDao.getHrCmsModuleDOBypageIdList(pageIds);
		 if(list2==null||list2.size()==0){
			 return null;
		 }
		 this.handleModuleListData(maps1, list2);
		 List<Integer> moduleIds=this.getModuleIdList(list2);
		 if(moduleIds==null||moduleIds.size()==0){
			 return null;
		 }
		 List<HrCmsMediaDO> list3=hrCmsMediaDao.getHrCmsMediaDOByModuleIdList(moduleIds);
		 if(list3==null||list3.size()==0){
			 return null;
		 }
		 this.handleMediaListdata(maps1,list3);
		 List<Integer> resIds=this.getResIdList(list3);
		 if(resIds==null||resIds.size()==0){
			 return null;
		 }
		 List<HrResourceDO> list4=hrResourceDao.getHrCmsResourceByIdList(resIds);
		 if(list4==null||list4.size()==0){
			 return null;
		 }
		 this.handleResourceListPic(maps1,list4);
		 this.handleResourceListData(maps1, list4);
		 return maps1;
	 }
	 
	//处理hrcmspages的数据将之处理完放在map当中
	 private void handlePageListData(List<Map<String,Object>> mapList,List<HrCmsPagesDO> list) throws TException{
		 Map<String,Object> map=null;
		 for(int i=0;i<list.size();i++){
			 map=new HashMap<String,Object>();
			 HrCmsPagesDO pagesDO=list.get(i);
			 int configId=pagesDO.getConfigId();
			 int pageId=pagesDO.getId();
			 map.put("pageId",pageId);
			 map.put("configId",configId);
			 String pageDataString=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(pagesDO);
			 Map<String,Object> pageData=JSON.parseObject(pageDataString, Map.class);
			 map.put("data",pageData);
			 mapList.add(map);
		 }
	 }
	 //处理hrcmsmodule的数据将之处理完放在map当中
	 private void handleModuleListData(List<Map<String,Object>> mapList,List<HrCmsModuleDO> list) throws TException{
		 for(int i=0;i<list.size();i++){
			 HrCmsModuleDO moduleDO=list.get(i);
			 int pageId=moduleDO.getPageId();
			 int ModuleId=moduleDO.getId();
			 for(int j=0;j<mapList.size();j++){
				 Map<String,Object> map1=mapList.get(j);
				 Integer originPageId=(Integer)map1.get("pageId");
				 if(originPageId==pageId){
					 Map<String,Object> pageData=(Map<String, Object>) map1.get("data");
					 String moudleDataString=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(moduleDO);
					 Map<String,Object> moudleData=JSON.parseObject(moudleDataString, Map.class);
					 List<Map<String,Object>> moduleDataList=new ArrayList<Map<String,Object>>();
					 List<Integer> moduleIdList=new ArrayList<Integer>();;
				 	 if(map1.get("moduleId")!=null) {
						moduleDataList=(List<Map<String, Object>>) pageData.get("moduleData");
						moduleIdList=(List<Integer>) map1.get("moduleId");
					}
				 	moduleIdList.add(ModuleId);
				 	moduleDataList.add(moudleData);
				 	pageData.put("moduleData", moduleDataList);
				 	map1.put("moduleId",moduleIdList);
				 	break;
				 }
			 }
		 }
	 }
	 //处理hrcmsmedia的信息，将它处理之后放到map理
	 private void handleMediaListdata(List<Map<String,Object>> mapList,List<HrCmsMediaDO> list) throws TException{
		 for(HrCmsMediaDO DO:list){
			 int moduleId=DO.getModuleId();
			 int resId=DO.getResId();
			 for( Map<String,Object> map:mapList){
				List<Integer> moduleIdList=(List<Integer>) map.get("moduleId");
				Map<String,Object> data=(Map<String, Object>) map.get("data");
				List<Map<String,Object>> moduleDataList=(List<Map<String, Object>>) data.get("moduleData");
				for(Map<String,Object> moduleData:moduleDataList){
					int mapModuleId=(int) moduleData.get("id");
					if(mapModuleId==moduleId){
						 String mediaDataString=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
						 Map<String,Object> mediaData=JSON.parseObject(mediaDataString, Map.class);
						List<Map<String,Object>> mediaDataList=new ArrayList<Map<String,Object>>();
						if(moduleData.get("mediaData")!=null){
							mediaDataList=(List<Map<String, Object>>) moduleData.get("mediaData");
						}
						mediaDataList.add(mediaData);
						moduleData.put("mediaData",mediaDataList);
					}
				}
				if(resId!=0){
					 for(Integer mapModuleId:moduleIdList){
							if(mapModuleId==moduleId){
									List<Integer> resIdList=new ArrayList<Integer>();
									if (map.get("resId") != null) {
										resIdList=(List<Integer>) map.get("resId");
									}
									resIdList.add(resId);
									map.put("resId",resIdList);
									break;
								}
							}
					}
				 }
			 }
	 }
	 //处理图片，将第一张图片放在map
	 private void handleResourceListPic(List<Map<String,Object>> mapList,List<HrResourceDO> list) throws TException{
		 for (int j = 0; j < mapList.size(); j++) {
			 Map<String, Object> map3 = mapList.get(j);
			 if(map3.get("imgUrl")==null){
				 List<Integer> resIdList = (List<Integer>)map3.get("resId");
				 if(!StringUtils.isEmptyList(resIdList)){
					 for(Integer resId:resIdList){
						 for(int i=0;i<list.size();i++){
							 HrResourceDO resourceDO=list.get(i);
							 int id=resourceDO.getId();
							 int resType=resourceDO.getResType();
							 if(resType==0) {
								 if (resId == id) {
									 	String imgUrl=resourceDO.getResUrl();
									 	map3.put("imgUrl",imgUrl);
									 	break;
									 } 
							 }
						 }
						 if(map3.get("imgUrl")!=null){
							 break;
						 }
						 
					 } 
				 }
				 
			 }
			 
			 
		 }
	 }
	 //处理hrresource的数据，将之放在map
	 private void handleResourceListData(List<Map<String,Object>> mapList,List<HrResourceDO> list) throws TException{
		 for(int i=0;i<list.size();i++){
			 HrResourceDO resourceDO=list.get(i);
			 int id=resourceDO.getId();
			 for (int j = 0; j < mapList.size(); j++) {
				 Map<String, Object> map = mapList.get(j);
				 if(!map.isEmpty()){
					 Map<String,Object> data=(Map<String, Object>) map.get("data");
					 if(!data.isEmpty()){
						 List<Map<String,Object>> moduledDataList=(List<Map<String, Object>>) data.get("moduleData");
						 if(!StringUtils.isEmptyList(moduledDataList)){
							 for(Map<String,Object> moduledData: moduledDataList){
								 List<Map<String,Object>> mediaList=(List<Map<String, Object>>) moduledData.get("mediaData");
								 if(!StringUtils.isEmptyList(mediaList)){
									 for(Map<String,Object> mediaData:mediaList){
										 int resId=(int) mediaData.get("resId");
										 if(resId==id&&resId!=0){
											 String resourceDataString=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(resourceDO);
											 Map<String,Object> resourceData=JSON.parseObject(resourceDataString, Map.class);
											 mediaData.put("resource", resourceData);
										 }
									 }
								 }
							 }
						 }
					}
				 }
				 
			 }
		 }
	 }
	 /*
	  *处理position和company的数据
	  */
	 public List<Map<String,Object>> handleCompanyAndPositionData(List<JobPositionDO> positionList, List<HrCompanyDO> companyList,List<HrTeamDO> teamList
			 ,List<Map<String,Integer>> publisherAndCompanyId,Map<Integer,List<String>> positionCitys) throws TException{
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
					// 本处如此做是为了过滤掉已经删除的子公司的信息
					 if(!StringUtils.isEmptyList(publisherAndCompanyId)){
						 for(int z=0;z<publisherAndCompanyId.size();z++){
							 Map<String,Integer> maps=publisherAndCompanyId.get(z);
							 Integer oripublisher=maps.get("publisher");
							 Integer oriCompanyid=maps.get("companyId");
							 if(oripublisher!=null&&oripublisher==publisher&&oriCompanyid!=null&&oriCompanyid==companyId){
								 String companyDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(companyDO);
								 Map<String,Object> companyData=JSON.parseObject(companyDOs, Map.class);
								 String positionDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(positionDo);
								 Map<String,Object> positionData=JSON.parseObject(positionDOs, Map.class);
								 map.put("position", positionData);
								 map.put("company",companyData);
								 break;
							 }
						 }
					 }
				 }
				 if(positionCitys!=null&&!positionCitys.isEmpty()&&!map.isEmpty()){
					 if(positionCitys.get(positionId)!=null){
						 map.put("cityList", positionCitys.get(positionId));
					 }
				 }
				 
			 }
			
			 // 本出如此做是为了过滤掉已经删除的子公司的信息
			 if(!map.isEmpty()){
				 for(HrTeamDO teamDo:teamList){
				 	int id=teamDo.getId();
					 if(teamId==id){
						 String teamDos=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(teamDo);
						 Map<String,Object> teamData=JSON.parseObject(teamDos, Map.class);
						 map.put("team",teamData);
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
					}
				}
		 	}

		 }
		 return result;
	 }
	 /*
	 	处理position 或者 Team jd页数据，获取首张图片
	  */
	 public List<Map<String,Object>> handlePositionJdPic(List<HrTeamDO> teamList,List<Integer> companyIds,int type) throws TException{
		 List<HrCompanyConfDO> AccountList=hrCompanyConfDao.getHrCompanyConfByCompanyIds(companyIds);
		 List<Integer> jdCompanyids=this.getJdCompanyIds(AccountList);
		 List<Integer> jdTeamids=this.getJdTeamIdList(jdCompanyids,teamList);
		 List<Map<String,Object>> list=HandleCmsResource(jdTeamids,type);
	 	return list;
	 }
	 //根据positionid列表获取list的jobPositioncity
	 public List<JobPositionCityDO> getJobPositionCity(List<Integer> positionIds){
		 if(StringUtils.isEmptyList(positionIds)){
			 return null;
		 }
		 Query query=new Query.QueryBuilder().where(new Condition("pid",positionIds.toArray(),ValueOp.IN)).buildQuery();
		 return jobPositionCityDao.getDatas(query);
	 }
	 //获取职位的城市格式是map形式，（positionId，List<String>)形式
	 public Map<Integer,List<String>> handlePositionCity(List<Integer> list){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 List<JobPositionCityDO>jobPositionCityList=this.getJobPositionCity(list);
		 if(StringUtils.isEmptyList(jobPositionCityList)){
			 return null;
		 }
		 List<Integer> codes=this.getPositionCodes(jobPositionCityList);
		 List<DictCityDO> citys=getDictCity(codes);
		 if(StringUtils.isEmptyList(citys)){
			 return null;
		 }
		 Map<Integer,List<String>> map=new HashMap<Integer,List<String>>();
		 for(JobPositionCityDO jobDo:jobPositionCityList){
			 int positionId=jobDo.getPid();
			 int code=jobDo.getCode();
			 List<String> positionCity=new ArrayList<String>();
			 if(map.get(positionId)!=null){
				 positionCity=map.get(positionId);
			 }			 
			 for(DictCityDO city:citys){
				 int code1=city.getCode();
				 String name=city.getName();
				 if(code==code1){
					 positionCity.add(name);
				 }
			 }
			 if(!StringUtils.isEmptyList(positionCity)){
				 map.put(positionId,positionCity);
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
	 public List<Map<String,Object>> handleDataJDAndPosition(List<Integer> positionIds,int type) throws TException{
		 if(StringUtils.isEmptyList(positionIds)){
			 return  null;
		 }
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 List<JobPositionDO> positionList=jobPositionDao.getPositionList(positionIds);
		 List<Integer> publisherIds=this.getPublisherIdList(positionList);
		 List<HrCompanyAccountDO> companyAccountList= getCompanyAccountList(publisherIds);
		 List<Integer> compantIds=this.getHrCompanyIdList(companyAccountList);
		 List<HrCompanyDO> companyList=hrCompanyDao.getHrCompanyByCompanyIds(compantIds);
		 companyList=this.filterCompanyList(companyList);
		 List<Map<String,Integer>> publisherAndCompanyId=getPublisherCompanyId(companyAccountList);
		 List<Integer> teamIds=this.getTeamIdList(positionList);
		 List<HrTeamDO> teamList=hrTeamDao.getTeamList(teamIds);
		 Map<Integer,List<String>> positionCitys=this.handlePositionCity(positionIds);
		 list=this.handleCompanyAndPositionData(positionList,companyList,teamList,publisherAndCompanyId,positionCitys);
		 List<Map<String,Object>> jdpictureList=this.handlePositionJdPic(teamList,compantIds,type);
		 this.handleJDAndPosition(list, jdpictureList);
	 	 return list;
	 }
	 //处理jd页和position的信息
	 private void handleJDAndPosition( List<Map<String,Object>> list, List<Map<String,Object>> jdpictureList){
		 if(!StringUtils.isEmptyList(jdpictureList)){
			 for(Map<String,Object> map:jdpictureList){
				 	Integer configId=(Integer)map.get("configId");
				 	String picture=(String)map.get("imgUrl");
				 	for(Map<String,Object> map1:list){
				 		Map<String,Object> teamDO=(Map<String, Object>) map1.get("team");
				 		if(teamDO!=null&&!teamDO.isEmpty()){
				 			int id=(int) teamDO.get("id");
					 		if(id==configId){
					 			map1.put("jdPic",picture);
							}
				 		}
				 		
					 }
			 }
		 
		 }
	 }
	 
	//====================================================== 
	 //获取仟寻推荐公司和相关职位信息接口
	 public Response getQXRecommendCompanyList(int page,int pageSize) throws TException{
		 List<CampaignPcRecommendCompanyDO>  CampaignPcRecommendCompanyList=campaignPcRecommendCompanyDao.getCampaignPcRecommendCompanyList(page,pageSize);
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
	 //获取全部公司
	 public List<Map<String,Object>> getAllCompanyRecommend(int page,int pageSize) throws TException{
		 List<CampaignPcRecommendCompanyDO>  CampaignPcRecommendCompanyList=campaignPcRecommendCompanyDao.getCampaignPcRecommendCompanyList(page,pageSize);
		 if(StringUtils.isEmptyList(CampaignPcRecommendCompanyList)){
			 return  null;
		 }
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 List<Integer> companyIdList=new ArrayList<Integer>();
		 for(CampaignPcRecommendCompanyDO dO:CampaignPcRecommendCompanyList){
			 String companyIds=dO.getCompanyIds();
			 String [] ids=companyIds.split(",");
			 for(int i=0;i<ids.length;i++){
				 companyIdList.add(Integer.parseInt(ids[i]));
			 }
		 }
		 List<Map<String,Object>> result=handleRecommendPcCompanyData(companyIdList);
		 return result;
	 }

	 /*
	  * 获取所推荐公司的publisher列表
	  */
	 public List<HrCompanyAccountDO> getCompanyAccountListByCompanyIds(List<Integer> companyIds){
		 if(StringUtils.isEmptyList(companyIds)){
			 return  null;
		 }
		 Query query=new Query.QueryBuilder().where(new Condition("company_id",companyIds.toArray(),ValueOp.IN)).buildQuery();
		 List<HrCompanyAccountDO> list=hrCompanyAccountDao.getDatas(query);
		 return list;
	 }
	 // 获取所有公司包括子公司
	 public List<HrCompanyDO> getAllCommpany(List<Integer> companyIds){
		 Query query=new Query.QueryBuilder()
				 .where(new Condition("id",companyIds.toArray(),ValueOp.IN))
				 .or(new Condition("parent_id",companyIds.toArray(),ValueOp.IN))
				 .buildQuery();
		 List<HrCompanyDO> list=hrCompanyDao.getDatas(query);
		 return list;
	 }
	 //获取所有的公司信息以及自己的子公司
	 public Map<Integer,Set<Integer>> getCompanySubAndSelf(List<HrCompanyDO> list,List<Integer> companyIds){
		 if(StringUtils.isEmptyList(list)||StringUtils.isEmptyList(companyIds)){
			 return null;
		 }
		 Map<Integer,Set<Integer>> data=new HashMap<Integer,Set<Integer>>();
		 for(Integer companyId:companyIds){
			 for(HrCompanyDO DO:list){
				 int parentId=DO.getParentId();
				 int id=DO.getId();
				 int status=DO.getDisable();
				 if(status==0&&parentId!=0){
					 continue;
				 }
				 if(companyId==id||parentId==companyId){
					 if(data.get(companyId)!=null){
						 data.get(companyId).add(id);
					 }else{
						 Set<Integer> companySet=new HashSet<Integer>();
						 companySet.add(id);
						 data.put(companyId, companySet);
					 }
				 }
			 }
		 }
		 return data;
	 }
	 //获取所有公司
	 public List<Integer> getAllCompanyIds(Map<Integer,Set<Integer>> data){
		 if(data==null||data.isEmpty()){
			 return null;
		 }
		 List<Integer> companyIdList=new ArrayList<Integer>();
		 for(Integer key:data.keySet()){
			 companyIdList.addAll(data.get(key));
		 }
		 return companyIdList;
	 }
	 //获取企业id和publisher的集合
	 public Map<Integer,List<Integer>> handleCompanyPublisher(List<Integer> compantIdsList){
		 if(StringUtils.isEmptyList(compantIdsList)){
			 return null;
		 }
		 List<HrCompanyDO> companyList=getAllCommpany(compantIdsList);
		 if(companyList==null||companyList.isEmpty()){
			 return null;
		 }
		 Map<Integer,Set<Integer>> companySubData=getCompanySubAndSelf(companyList,compantIdsList);
		 if(companySubData==null||companySubData.isEmpty()){
			 return null;
		 }
		 List<HrCompanyAccountDO> accountList=getCompanyAccountListByCompanyIds(compantIdsList);
		 if(StringUtils.isEmptyList(accountList)){
			 return null;
		 }
		 Map<Integer,List<Integer>> map=new HashMap<Integer,List<Integer>>();
		 HandleCompanyPublisher(companySubData,accountList,map);
		 return map;
	 }
	 //将数据处理成Map<companyId,List<pulisher>>的形式
	 public void HandleCompanyPublisher(Map<Integer,Set<Integer>> companySubData,List<HrCompanyAccountDO> accountList, Map<Integer,List<Integer>> map){
		 for(HrCompanyAccountDO DO:accountList){
			 int id=DO.getCompanyId();
			 int accountId=DO.getAccountId();
			 for(Integer companyId:companySubData.keySet()){
				 Set<Integer> data=companySubData.get(companyId);
				 if(data!=null||!data.isEmpty()){
					 if(data.contains(id)){
						 if(map.get(companyId)==null){
							 List<Integer> accountIdList=new ArrayList<Integer>();
							 accountIdList.add(accountId);
							 map.put(companyId, accountIdList);
						 }else{
							 map.get(companyId).add(accountId);
						 }
					 }
				 }
			 }
		 }
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
	 public List<Map<String,Object>> handleRecommendPcCompanyData(List<Integer> companyIds) throws TException{
		 if(StringUtils.isEmptyList(companyIds)){
			 return  null;
		 }
		 List<HrCompanyDO> companyList=hrCompanyDao.getHrCompanyByCompanyIds(companyIds);
		 if(StringUtils.isEmptyList(companyList)){
			 return  null;
		 }
		 companyList=filterCompanyList(companyList);
		 if(StringUtils.isEmptyList(companyList)){
			 return  null;
		 }
		 Map<Integer,List<Integer>> companyPulisher=handleCompanyPublisher(companyIds);
		 List<Map<String,Object>> mapTeamNum=hrTeamDao.getTeamNum(companyIds);
		 List<Integer> companyids=this.getCompanyIds(companyList);
		 List<Map<String,Object>> jdlist=HandleCmsResource(companyids,1);
		 Map<Integer,Set<String>> companyPositionCityData=handlerCompanyPositionCity(companyPulisher);
		 List<Map<String,Object>> list=handleDataForCompanyRecommend(companyList,companyPulisher,mapTeamNum,jdlist,companyPositionCityData);
		 return list;
	 }
	 //处理企业信息的组合问题
	 public List<Map<String,Object>> handleDataForCompanyRecommend( List<HrCompanyDO> companyList,
			 Map<Integer,List<Integer>> companyPulisher,List<Map<String,Object>> mapTeamNum
			 ,List<Map<String,Object>> jdlist,Map<Integer,Set<String>> companyPositionCityData) throws TException{
		 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		 Map<String,Object> map=null;
		 for(int i=0;i<companyList.size();i++){
			 map=new HashMap<String,Object>();
			 HrCompanyDO companyDO=companyList.get(i);
			 int companyId=companyDO.getId();
			 String companyDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(companyDO);
			 Map<String,Object> companyData=JSON.parseObject(companyDOs, Map.class);
			 map.put("company", companyData);
			 if(companyPulisher!=null&&!companyPulisher.isEmpty()){
				 List<Integer> publisherIds=companyPulisher.get(companyId);
				 if(publisherIds!=null&&publisherIds.size()>0){
					int num=this.getPositionNum(publisherIds);
					map.put("positionNum", num);
				 }else{
					map.put("positionNum", 0);
				 }
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
			 if(companyPositionCityData!=null&&!companyPositionCityData.isEmpty()){
				 if(companyPositionCityData.get(companyId)!=null){
					 map.put("cityList", companyPositionCityData.get(companyId));
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
	 /*
	  * 获取公司hr下的职位
	  */
	 public List<JobPositionDO> getPositionByPublisher(List<Integer> publisherList){
		 if(StringUtils.isEmptyList(publisherList)){
			 return null;
		 }
		 Query query=new Query.QueryBuilder()
				    .where(new Condition("publisher",publisherList.toArray(),ValueOp.IN))
				    .and("status",0)
				    .setPageNum(1)
				    .setPageSize(300)
				    .buildQuery();
		 List<JobPositionDO> list=jobPositionDao.getDatas(query);
		 return list;
	 }
	 /*
	  * 处理公司信息，获取公司下的positionId
	  */
	 public Map<Integer,Set<Integer>> getCompanyPositionIds(Map<Integer,List<Integer>> companyPublisher,List<JobPositionDO> positionData){
		 if(StringUtils.isEmptyList(positionData)){
			 return null;
		 }
		 Map<Integer,Set<Integer>> map=new HashMap<Integer,Set<Integer>>();
		 for(JobPositionDO DO:positionData){
			 int publisher=DO.getPublisher();
			 int positionId=DO.getId();
			 for(Integer key:companyPublisher.keySet()){
				List<Integer> publishers=companyPublisher.get(key);
				if(publishers.contains(publisher)){
					if(map.get(key)!=null){
						map.get(key).add(positionId);
					}else{
						Set<Integer> positionIdSet=new HashSet<Integer>();
						positionIdSet.add(positionId);
						map.put(key, positionIdSet);
					}
				}
			 }
		 }
		 return map;
	 }
	 
	 //获取所有的publisher
	 public List<Integer> getAllPulisherByCompanyPublisher(Map<Integer,List<Integer>> companyPublisher){
		 if(companyPublisher==null||companyPublisher.isEmpty()){
			 return null;
		 }
		 List<Integer> list=new ArrayList<Integer>();
		 for(Integer key:companyPublisher.keySet()){
			 List<Integer> publishers=companyPublisher.get(key);
			 list.addAll(publishers);
		 }
		 return list;
	 }
	 //获取positionId，通过职位列表
	 public List<Integer> getIdByPositionList( List<JobPositionDO> list){
		 if(list==null||list.size()==0){
				return null;
			}
			List<Integer> result=new ArrayList<Integer>();
			for(int i=0;i<list.size();i++){
				JobPositionDO record=list.get(i);
				result.add(record.getId());
			}
			return result;
	 }
	 //处理公司下面的地址
	 public Map<Integer,Set<String>> handlerCompanyPositionCity(Map<Integer,List<Integer>> companyPublisher){
		 List<Integer> publisherList=getAllPulisherByCompanyPublisher(companyPublisher);
		 List<JobPositionDO> positionData=getPositionByPublisher(publisherList);
		 List<Integer> positionIdList=this.getIdByPositionList(positionData);
		 Map<Integer,Set<Integer>> companyPosition=getCompanyPositionIds(companyPublisher,positionData);
		 if(companyPosition==null||companyPosition.isEmpty()){
			 return null;
		 }
		 Map<Integer,List<String>> positionCityMap=handlePositionCity(positionIdList);
		 if(positionCityMap==null||positionCityMap.isEmpty()){
			 return null;
		 }
		 Map<Integer,Set<String>> data=handlerDataCompanyCity(companyPosition,positionCityMap);
		 return data;
	 }
	 //处理数据获取Set<companyId,Set<city>>的数据
	 public Map<Integer,Set<String>> handlerDataCompanyCity(Map<Integer,Set<Integer>> companyPosition,Map<Integer,List<String>> positionCityMap){
		
		 Map<Integer,Set<String>> data=new HashMap<Integer,Set<String>>();
		 for(Integer key1:positionCityMap.keySet()){
			 List<String> cities=positionCityMap.get(key1);
			 if(StringUtils.isEmptyList(cities)){
				 for(Integer key2:companyPosition.keySet()){
					 Set<Integer> positionIds=companyPosition.get(key2);
					 if(positionIds.contains(key1)){
						 if(data.get(key2)==null){
							 Set<String> cityData=new HashSet<String>(); 
							 cityData.addAll(cities);
							 data.put(key2, cityData);
						 }else{
							 data.get(key2).addAll(cities) ;
						 }
					 }
				 }
			 }
		 }
		 return data;
	 }
}
