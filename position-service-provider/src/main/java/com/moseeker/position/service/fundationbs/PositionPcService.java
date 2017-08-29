package com.moseeker.position.service.fundationbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.SelectOp;

import com.moseeker.entity.PcRevisionEntity;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
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
	@Autowired
	private PcRevisionEntity pcRevisionEntity;
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
	//======================================================
	//获取仟寻推荐公司和相关职位信息接口
	@CounterIface
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
	/*
	  获取职位详情
	 */
	@CounterIface
	public Map<String,Object> getPositionDetails(int positionId) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		Query query=new Query.QueryBuilder().where("id",positionId).and("status",0).buildQuery();
		JobPositionDO  DO=jobPositionDao.getData(query);
		if(DO==null){
			return null;
		}
		String DOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
		Map<String,Object> positionData= JSON.parseObject(DOs, Map.class);
		map.put("position",positionData);
		int teamId=DO.getTeamId();
		int publisher=DO.getPublisher();
		HrCompanyDO companyDO= handleCompanyData(publisher);
		String companyDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(companyDO);
		Map<String,Object> companyData= JSON.parseObject(companyDOs, Map.class);
		map.put("company",companyData);
		Map<String,Object> team=this.handleTeamData(teamId);
		map.put("team",team);
		int parentId= (int) companyData.get("parentId");
		int confCompanyId= (int) companyData.get("id");
		if(parentId!=0){
			confCompanyId=parentId;
		}
		this.handlePositionJdData(confCompanyId,map,teamId);
		return map;
	}
	/*
        获取职位的jd页
     */
	private void handlePositionJdData(int confCompanyId,Map<String,Object> map,int teamId) throws Exception {
		map.put("newJd",0);
		HrCompanyConfDO hrCompanyConfDO=getHrCompanyConf(confCompanyId);
		if(hrCompanyConfDO!=null){
			int newJdStatus=hrCompanyConfDO.getNewjdStatus();
			if(newJdStatus==2) {
				List<Integer> jdID = new ArrayList<Integer>();
				jdID.add(teamId);
				List<Map<String,Object>>jdList=pcRevisionEntity.HandleCmsResource(jdID,3);
				if(!StringUtils.isEmptyList(jdList)){
					Map<String,Object> jdMap=jdList.get(0);
					if(jdMap!=null&&!jdMap.isEmpty()){
						map.put("newJd",1);
						map.put("jd",jdMap);
					}
				}
			}
		}
	}
	/*
      获取公司配置
    */
	public HrCompanyConfDO getHrCompanyConf(int companyId){
		Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
		HrCompanyConfDO DO= hrCompanyConfDao.getData(query);
		return DO;
	}
	/*
	    获取发布职位的公司信息
	 */
     public HrCompanyAccountDO getSingleCompanyId(int publisher){
     	Query query=new Query.QueryBuilder().where("account_id",publisher).buildQuery();
     	HrCompanyAccountDO DO=hrCompanyAccountDao.getData(query);
     	return DO;
	 }
     /*
		获取单个企业的信息
      */
     public HrCompanyDO getSingleCompany(int companyId){
		 HrCompanyDO DO=hrCompanyDao.getCompanyById(companyId);
		 return DO;
	 }
	 /*
	   处理职位的公司数据
	  */
	 private HrCompanyDO handleCompanyData(int publisher){
		 HrCompanyAccountDO accountDO=this.getSingleCompanyId(publisher);
		 HrCompanyDO companyDO=this.getSingleCompany(accountDO.getCompanyId());
	 	 return companyDO;
	 }
	/*
	    获取团队信息
	 */
	public HrTeamDO getSingleTeamInfo(int teamId){
		HrTeamDO DO=hrTeamDao.getHrTeam(teamId);
		return DO;
	}
	/*
	   处理团队数据
	 */
	private  Map<String,Object> handleTeamData(int teamId) throws TException{
		Map<String,Object> map=new HashMap<String,Object>();
		HrTeamDO teamDO=getSingleTeamInfo(teamId);
		String teamDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(teamDO);
		Map<String,Object> teamData= JSON.parseObject(teamDOs, Map.class);
		map.put("teamInfo",teamData);
		int resId=teamDO.getResId();
		if(resId>0){
			List<Integer> resIdList=new ArrayList<Integer>();
			resIdList.add(resId);
			List<HrResourceDO> resourceDOList=hrResourceDao.getHrResourceByIdList(resIdList);
			if(!StringUtils.isEmptyList(resourceDOList)){
				HrResourceDO resourceDO=resourceDOList.get(0);
				String resourceDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(resourceDO);
				Map<String,Object> resourceData= JSON.parseObject(resourceDOs, Map.class);
				map.put("teamPic",resourceData);
			}
		}
		List<Integer> teamIdList=new ArrayList<>();
		teamIdList.add(teamId);
		Map<Integer,List<Map<String,Object>>> teamMember=pcRevisionEntity.handlerTeamMember(teamIdList);
		if(teamMember!=null&&!teamMember.isEmpty()){
			List<Map<String,Object>> list=teamMember.get(teamId);
			map.put("teamMember",list);
		}
		return map;
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
			 // 本处如此做是为了过滤掉已经删除的子公司的信息
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
		 List<Map<String,Object>> list=pcRevisionEntity.HandleCmsResource(jdTeamids,type);
	 	return list;
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
		 Map<Integer,List<String>> positionCitys=pcRevisionEntity.handlePositionCity(positionIds);
		 list=this.handleCompanyAndPositionData(positionList,companyList,teamList,publisherAndCompanyId,positionCitys);
		 List<Map<String,Object>> jdpictureList=this.handlePositionJdPic(teamList,this.getPositionCompanyId(positionList),type);
		 this.handleJDAndPosition(list, jdpictureList);
		 return list;
	 }

	private List<Integer> getPositionCompanyId(List<JobPositionDO> positionList){
		if(StringUtils.isEmptyList(positionList)){
			return null;
		}
		List<Integer> list=new ArrayList<Integer>();
		for(JobPositionDO DO:positionList){
			list.add(DO.getCompanyId());
		}
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
		 Map<Integer,List<Integer>> companyPulisher=pcRevisionEntity.handleCompanyPublisher(companyIds);
		 Map<Integer,Integer> mapTeamNum=this.getTeamNum(companyList, companyPulisher);
		 List<Integer> companyids=this.getCompanyIds(companyList);
		 List<Map<String,Object>> jdlist=pcRevisionEntity.HandleCmsResource(companyids,1);
		 Map<Integer,Set<String>> companyPositionCityData=pcRevisionEntity.handlerCompanyPositionCity(companyPulisher);
		 List<Map<String,Object>> list=handleDataForCompanyRecommend(companyList,companyPulisher,mapTeamNum,jdlist,companyPositionCityData);
		 return list;
	 }
	 //获取推荐公司下边团队的数量
	 private Map<Integer,Integer> getTeamNum(List<HrCompanyDO> companyList, Map<Integer,List<Integer>> companyPulisher){
		 if(StringUtils.isEmptyList(companyList)){
			 return new HashMap<Integer,Integer>();
		 }
		 if(companyPulisher==null||companyPulisher.isEmpty()){
			 return new HashMap<Integer,Integer>();
		 }
		 Map<Integer,Integer> motherTeamNum=this.handlerMotherTeamNum(companyList);
		 Map<Integer,Integer> childteamNum=this.getChildTeamNum(companyList,companyPulisher);
		 Map<Integer,Integer> result=new HashMap<Integer,Integer>();
		 if(motherTeamNum!=null&&!motherTeamNum.isEmpty()){
			 result.putAll(motherTeamNum);
		 }
		 if(childteamNum!=null&&!childteamNum.isEmpty()){
			 result.putAll(childteamNum);
		 }
		return result;
	 }
	 //获取母公司的团队数量
	 private  Map<Integer,Integer> handlerMotherTeamNum(List<HrCompanyDO> companyList){
		 if(StringUtils.isEmptyList(companyList)){
			 return new HashMap<Integer,Integer>();
		 }
		 List<Integer> motherCompanyIdList=this.getMotherCompanyIdList(companyList);
		 List<Map<String,Object>> motherTeamList=hrTeamDao.getTeamNum(motherCompanyIdList);
		 if(StringUtils.isEmptyList(motherTeamList)){
			 return new HashMap<Integer,Integer>();
		 }
		 Map<Integer,Integer> result=new HashMap<Integer,Integer>();
		 for(Map<String,Object> map:motherTeamList){
			 int number=(int) map.get("id_count");
			 int companyId=(int) map.get("company_id");
			 result.put(companyId, number);
		 }
		 return result;
		 
	 }
	 //获取子公司的团队数量
	 public Map<Integer,Integer> getChildTeamNum(List<HrCompanyDO> companyList, Map<Integer,List<Integer>> companyPulisher){
		Map<Integer,List<Integer>> childCompanyPublisherMap=this.getChildCompanyIdPulisherMap(companyList, companyPulisher);
		if(childCompanyPublisherMap!=null||!childCompanyPublisherMap.isEmpty()){
			List<Integer> publisherList=pcRevisionEntity.getAllPulisherByCompanyPublisher(childCompanyPublisherMap);
			List<Map<String,Object>> mapList=getChildTeamNumBypublisherList(publisherList);
			Map<Integer,Integer> result=this.handleChildTeamNum(mapList,companyPulisher);
			return result;
		}
		return null;
	 }
	 
	 //从数据库中获取发布人的下的带有team_id的职位
	 public List<Map<String,Object>> getChildTeamNumBypublisherList(List<Integer> publisherList){
		 if(StringUtils.isEmptyList(publisherList)){
			 return null;
		 }
		Query  query=new Query.QueryBuilder().select(new Select("team_id", SelectOp.DISTINCT))
				.select("publisher")
				.where(new Condition("team_id",0,ValueOp.NEQ))
				.and(new Condition("publisher",publisherList,ValueOp.IN))
				.and("status",0)
				.buildQuery();
		List<Map<String,Object>> result=jobPositionDao.getMaps(query);
		return result;
	 }
	 //处理子公司的职位的
	 private Map<Integer,Integer> handleChildTeamNum(List<Map<String,Object>> list,Map<Integer,List<Integer>> companyPulisher){
		 if(StringUtils.isEmptyList(list)){
			 return null;
		 }
		 if(companyPulisher!=null&&companyPulisher.isEmpty()){
			 return null;
		 }
		 Map<Integer,Integer> result=new HashMap<Integer,Integer>();
		 for(Map<String,Object> map:list){
			 int publisher=(int) map.get("publisher");
			 for(Integer key:companyPulisher.keySet()){
				 List<Integer> publisherList=companyPulisher.get(key);
				 if(!StringUtils.isEmptyList(publisherList)){
					 if(publisherList.contains(publisher)){
						 Integer num=result.get(key);
						 if(num==null){
							 result.put(key, 1);
						 }else{
							 result.put(key, num+1);
						 } 
						 break;
					 }
				 }
			 }
			 
		 }
		 
		 return result;
	 }
	 //获取列表中所有母公司的id
	 private List<Integer> getMotherCompanyIdList(List<HrCompanyDO> companyList){
		 List<Integer> list=new ArrayList<Integer>();
		 for(HrCompanyDO DO:companyList){
			 int id=DO.getId();
			 int parentId=DO.getParentId();
			 if(parentId==0){
				 list.add(id);
			 }
		 }
		 return list;
	 }
	 //获取列表中所有母公司的id以及他的accountid的map
	 public Map<Integer,List<Integer>> getChildCompanyIdPulisherMap(List<HrCompanyDO> companyList,Map<Integer,List<Integer>> companyPulisher){
		 Map<Integer,List<Integer>> map=new HashMap<Integer,List<Integer>>();
		 for(HrCompanyDO DO:companyList){
			 int id=DO.getId();
			 int parentId=DO.getParentId();
			 int disable=DO.getDisable();
			 if(parentId!=0&&disable==1){
				 map.put(id, companyPulisher.get(id));
			 }
		 }
		 return map;
	 }

	 
	 //处理企业信息的组合问题
	 public List<Map<String,Object>> handleDataForCompanyRecommend( List<HrCompanyDO> companyList,
			 Map<Integer,List<Integer>> companyPulisher,Map<Integer,Integer> mapTeamNum
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
			 
			 if(!StringUtils.isEmptyList(companyList)&&mapTeamNum!=null&&!mapTeamNum.isEmpty()){
				 Integer teamNum=mapTeamNum.get(companyId);
				 if(teamNum!=null){
					 map.put("teamNum", teamNum);
				 }else{
					 map.put("teamNum", 0);
				 }
			 }else{
				 map.put("teamNum", 0);
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
				 if(disable==1){
					 newList.add(companyDO);
				 }
			 }else{
				 newList.add(companyDO);
			 }
		 }
		 return newList;
	 }


	 




}
