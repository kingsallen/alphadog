package com.moseeker.company.service.impl;
import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.entity.PcRevisionEntity;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by zztaiwll on 17/8/14.
 * 用于处理pc2.0的公司详情信息
 */
@Service
public class CompanyPcService {
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrTeamDao hrTeamDao;
    @Autowired
    private JobPositionCityDao jobPositionCityDao;
    @Autowired
    private PcRevisionEntity pcRevisionEntity;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private HrResourceDao hrResourceDao;
    @Autowired
    private HrWxWechatDao hrWxWechatDao;
    @Autowired
    private HrTeamMemberDao hrTeamMemberDao;

    /*
      获取企业详情
     */
    @CounterIface
    public Map<String,Object> getCompanyDetail(int companyId) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        Map<String,Object> companyData=this.handleCompany(companyId);
        if(companyData==null||companyData.isEmpty()){
            return null;
        }
        map.put("company",companyData);
        int parentId= (int) companyData.get("parentId");
        int confCompanyId= (int) companyData.get("id");
        boolean isMother=true;
        if(parentId!=0){
            confCompanyId=parentId;
            isMother=false;
        }
        int newJd=this.judgeJDOrCS(confCompanyId);
        map.put("newJd",newJd);
        Map<String,Object> jdMap=this.handleCompanyJdData(confCompanyId,companyId);
        this.putMapInNewMap(jdMap,map);
        Map<String,Object>teamMap=this.handleTeamInfo(companyId,isMother,1,20);
        this.putMapInNewMap(teamMap,map);
        Map<String,Object>positionMap=this.handleCompanyPositionCity(companyId,isMother);
        this.putMapInNewMap(positionMap,map);
        return map;
    }

    /*
     获取团队列表
     */
    @CounterIface
    public Map<String,Object> getTeamListinfo(int companyId,int page,int pageSize) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        Map<String,Object> companyData=this.handleCompany(companyId);
        if(companyData==null||companyData.isEmpty()){
            return null;
        }
        int parentId= (int) companyData.get("parentId");
        boolean isMother=true;
        if(parentId!=0){
            isMother=false;
        }
        Map<String,Object> teamMap=this.handleTeamInfo(companyId,isMother,page,pageSize);
        this.putMapInNewMap(teamMap,map);
        return map;
    }
    /*
     获取企业头部信息
     */
    @CounterIface
    public Map<String,Object> getCompanyMessage(int companyId) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        Map<String,Object> companyData=this.handleCompany(companyId);
        if(companyData==null||companyData.isEmpty()){
            return null;
        }
        map.put("company",companyData);
        int parentId= (int) companyData.get("parentId");
        int confCompanyId= (int) companyData.get("id");
        boolean isMother=true;
        if(parentId!=0){
            confCompanyId=parentId;
            isMother=false;
        }
        int newJd=this.judgeJDOrCS(confCompanyId);
        map.put("newJd",newJd);
        Map<String,Object> positionMap=this.handleCompanyPositionCity(companyId,isMother);
        this.putMapInNewMap(positionMap,map);
        return map;
    }
    /*
       获取团队详情
     */
    @CounterIface
    public Map<String,Object> getTeamDetails(int teamId,int companyId) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        Map<String,Object> companyData=this.handleCompany(companyId);
        if(companyData==null||companyData.isEmpty()){
            return null;
        }
        map.put("company",companyData);
        boolean isMother=true;
        int parentId= (int) companyData.get("parentId");
        int confCompanyId= (int) companyData.get("id");
        if(parentId!=0){
            confCompanyId=parentId;
            isMother=false;
        }
        Map<String,Object> positionMap=this.handleCompanyPositionCity(companyId,isMother);
        this.putMapInNewMap(positionMap,map);
        Map<String,Object> jdMap=this.handleTeamJdData(confCompanyId,teamId);
        this.putMapInNewMap(jdMap,map);
        int newJd=this.judgeJDOrCS(confCompanyId);
        map.put("newJd",newJd);
        Map<String,Object> otherMap=this.getOtherTeamList(companyId,teamId,isMother,newJd);
        this.putMapInNewMap(otherMap,map);
        Map<String,Object> team=this.getSingleTeamInfo(teamId);
        if(team!=null&&!team.isEmpty()){
            map.put("teamInfo",team);
        }
        return map;
    }
    /*
      将旧的map放入新的map
     */
    private void putMapInNewMap(Map<String,Object> originMap, Map<String,Object> newMap){
        if(originMap!=null&&!originMap.isEmpty()){
            for(String key:originMap.keySet()){
                newMap.put(key,originMap.get(key));
            }
        }
    }
    /*
     获取单个团队的具体信息
     */
    private Map<String,Object> getSingleTeamInfo(int teamId) throws Exception {
        Map<String,Object> team=new HashMap<>();
        List<Integer> teamList=new ArrayList<Integer>();
        teamList.add(teamId);
        List<HrTeamDO> list=hrTeamDao.getTeamList(teamList);
        if(!StringUtils.isEmptyList(list)){
            HrTeamDO DO=list.get(0);
            String hrTeamDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
            Map<String,Object> teamData= JSON.parseObject(hrTeamDOs, Map.class);
            team.put("team",teamData);
            int resId=DO.getResId();
            if(resId>0){
                Query query=new Query.QueryBuilder().where("id",resId).buildQuery();
                HrResourceDO resourceDO=hrResourceDao.getData(query);
                if(resourceDO!=null){
                    String resourceDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(resourceDO);
                    Map<String,Object> resourceData= JSON.parseObject(resourceDOs, Map.class);
                    team.put("teamPic",resourceData);
                }
            }
            List<Integer> teamIdList=new ArrayList<Integer>();
            teamIdList.add(DO.getId());
            Map<Integer,List<Map<String,Object>>> teamMember=pcRevisionEntity.handlerTeamMember(teamIdList);
            if(teamMember!=null&&!teamMember.isEmpty()){
                team.put("teamMember" ,teamMember.get(DO.getId()));
            }

        }
        return team;
    }
    /*
     获取其他团队列表
     */
    private Map<String,Object> getOtherTeamList(int companyId,int teamId,boolean isMother,int newJd) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,Object> result=new HashMap<String,Object>();
        if(newJd>0){
            result=this.handleTeamInfo(companyId,isMother,1,Integer.MAX_VALUE);
        }else{
            result=this.handleTeamInfo(companyId,isMother,1,15);
        }
        List<Map<String,Object>> newList=new ArrayList<Map<String,Object>>();
        if(result!=null&&!result.isEmpty()){
            List<Map<String,Object>> list= (List<Map<String, Object>>) result.get("teamList");
            if(!StringUtils.isEmptyList(list)){
                for(Map<String,Object> item:list){
                    Map<String,Object> team= (Map<String, Object>) item.get("team");
                    int id= (int) team.get("id");
                    if(teamId!=id){
                        newList.add(item);
                    }
                }
            }
        }
        if(!StringUtils.isEmptyList(newList)){
            map.put("teamList",newList);
        }
        return map;
    }
    /*
      处理企业信息
     */
    private Map<String,Object> handleCompany(int companyId)throws Exception{
        HrCompanyDO company=this.getHrCompany(companyId);
        if(company==null){
            return null;
        }
        String companyDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(company);
        Map<String,Object> companyData= JSON.parseObject(companyDOs, Map.class);
        return companyData;
    }
    /*
    判断是否有jd页，或者cs
     */
    private int  judgeJDOrCS(int confCompanyId){
        Map<String,Object> map=new HashMap<String,Object>();

        HrCompanyConfDO hrCompanyConfDO=getHrCompanyConf(confCompanyId);
        if(hrCompanyConfDO!=null){
            int newJdStatus=hrCompanyConfDO.getNewjdStatus();
            if(newJdStatus==2){
                return 1;
            }
        }
        return 0;
    }
    /*
     获取company jd信息
     */
    private Map<String,Object>  handleCompanyJdData(int confCompanyId,int companyId) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        HrCompanyConfDO hrCompanyConfDO=getHrCompanyConf(confCompanyId);
        if(hrCompanyConfDO!=null){
            int newJdStatus=hrCompanyConfDO.getNewjdStatus();
            if(newJdStatus==2){
                List<Integer> jdID=new ArrayList<Integer>();
                jdID.add(companyId);
                List<Map<String,Object>>jdList=pcRevisionEntity.HandleCmsResource(jdID,1);
                if(!StringUtils.isEmptyList(jdList)){
                    Map<String,Object> jdMap=jdList.get(0);
                    if(jdMap!=null&&!jdMap.isEmpty()){
                        map.put("jd",jdMap);
                        HrWxWechatDO hrWxWechatDO=this.getHrWxWechatDO(confCompanyId);
                        if(hrWxWechatDO!=null){
                            String hrWxWechatDOStr=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(hrWxWechatDO);
                            Map<String,Object> hrWxWechatData= JSON.parseObject(hrWxWechatDOStr, Map.class);
                            map.put("weChat",hrWxWechatData);
                        }
                    }
                }
            }
        }
        return map;
    }
    /*
        获取团队的jd页
     */
    private Map<String,Object>  handleTeamJdData(int confCompanyId,int teamId) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        HrCompanyConfDO hrCompanyConfDO=getHrCompanyConf(confCompanyId);
        if(hrCompanyConfDO!=null){
            int newJdStatus=hrCompanyConfDO.getNewjdStatus();
            if(newJdStatus==2) {
                List<Integer> jdID = new ArrayList<Integer>();
                jdID.add(teamId);
                List<Map<String,Object>>jdList=pcRevisionEntity.HandleCmsResource(jdID,2);
                if(!StringUtils.isEmptyList(jdList)){
                    Map<String,Object> jdMap=jdList.get(0);
                    if(jdMap!=null&&!jdMap.isEmpty()){
                        map.put("jd",jdMap);
                    }
                }
            }
        }
        return map;
    }
/*
    获取企业微信号配置
     */
    private HrWxWechatDO  getHrWxWechatDO(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrWxWechatDO DO=hrWxWechatDao.getData(query);
        return DO;
    }
    /*
      获取团队信息
     */
    private Map<String,Object>  handleTeamInfo(int companyId,boolean isMother,int page,int pageSize) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object> result=new HashMap<>();
        if(isMother){
            result=this.handleMotherCompanyTeam(companyId,page,pageSize);
        }else{
            result=this.handleSubCompanyTeam(companyId,page,pageSize);
        }
        if(result!=null&&!result.isEmpty()){
            list=(List<Map<String,Object>>)result.get("teamPosition");
            Integer hasPic= (Integer) result.get("hasPic");
            if(hasPic!=null){
                map.put("hasPic",hasPic);
            }
            Integer teamNum=(Integer) result.get("teamNum");
            map.put("teamNum",teamNum);
        }
        if(!StringUtils.isEmptyList(list)){
            map.put("teamList",list);
        }
        return map;
    }
    //处理公司下的职位
    private Map<String,Object> handleCompanyPositionCity(int companyId,boolean isMother){
        Map<String,Object> map=new HashMap<String,Object>();
        Map<Integer,Set<String>> result=new HashMap<>();
        if(isMother){
            result=handleMotherCompanyPositionCity(companyId);

        }else{
            result=hanldeSubCompanyPositionCity(companyId);
        }
        if(result!=null&&!result.isEmpty()){
            Set<String> cityList=result.get(companyId);
            if(cityList!=null&&cityList.size()>0){
                map.put("positionCity",cityList);
            }
        }
        return map;
    }
    //处理母公司下职位的城市
    private Map<Integer,Set<String>> handleMotherCompanyPositionCity(int companyId){
        List<Integer> list=this.getMotherCompanyPublisher(companyId);
        if(!StringUtils.isEmptyList(list)){
            Map<Integer,List<Integer>> companyPublisher=new HashMap<Integer,List<Integer>>();
            companyPublisher.put(companyId,list);
            Map<Integer,Set<String>> result=pcRevisionEntity.handlerCompanyPositionCity(companyPublisher);
            return result;
        }
        return null;
    }
    //处理子公司下职位的城市
    private Map<Integer,Set<String>> hanldeSubCompanyPositionCity(int companyId){
        List<Integer> accountList=this.getCompanyPublisher(companyId);
        if(!StringUtils.isEmptyList(accountList)){
            Map<Integer,List<Integer>> companyPublisher=new HashMap<Integer,List<Integer>>();
            companyPublisher.put(companyId,accountList);
            Map<Integer,Set<String>> result=pcRevisionEntity.handlerCompanyPositionCity(companyPublisher);
            return result;
        }
        return null;
    }
    //处理母公司的团队信息
    private Map<String,Object> handleMotherCompanyTeam(int companyId,int page,int pageSize) throws Exception {
        List<HrTeamDO> teamList=this.getCompanyTeam(companyId,page,pageSize);
        int num=this.getCompanyTeamNum(companyId);
        List<Integer>teamIdList=getTeamIdList(teamList);
        Map<Integer,Integer> teamPosition=getTeamPositionNum(teamIdList);
        Map<Integer,List<Map<String,Object>>> teamMember=pcRevisionEntity.handlerTeamMember(teamIdList);
        Map<String,Object> map=this.handleTeamPosition(teamList,teamPosition,teamMember,num);
        return map;
    }
    //处理子公司团队的信息
    private Map<String,Object> handleSubCompanyTeam(int companyId,int page,int pageSize) throws Exception {
        List<Integer> publisherList=getCompanyPublisher(companyId);
        List<Integer> teamIdList=this.getSubCompanyTeam(publisherList,page,pageSize);
        int num=this.getSubCompanyTeam(publisherList);
        List<HrTeamDO> teamList= hrTeamDao.getTeamList(teamIdList);
        Map<Integer,Integer> teamPosition=getTeamPositionNum(teamIdList);
        Map<Integer,List<Map<String,Object>>> teamMember=pcRevisionEntity.handlerTeamMember(teamIdList);
        Map<String,Object> map=this.handleTeamPosition(teamList,teamPosition,teamMember,num);
        return map;
    }


    /*
       处理团队和团队的职位数量之间的关系
     */
    private Map<String,Object> handleTeamPosition(List<HrTeamDO> teamList, Map<Integer,Integer> teamPosition,Map<Integer,List<Map<String,Object>>> teamMember,int num) throws Exception {
        Map<String,Object> result=new HashMap<String,Object>();
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        result.put("teamNum",num);
        if(StringUtils.isEmptyList(teamList)){
            return result;
        }
        List<Integer>resIdList=this.getResdListId(teamList);

        boolean hasPic=true;//团队是否有图片
        result.put("hasPic",1);
        if(StringUtils.isEmptyList(resIdList)){
            hasPic=false;
            result.put("hasPic",0);
        }
        List<HrResourceDO> resourceList=hrResourceDao.getHrResourceByIdList(resIdList);
        if(StringUtils.isEmptyList(resourceList)){
            hasPic=false;
            result.put("hasPic",0);
        }

        for(HrTeamDO DO:teamList){
            int teamId= DO.getId();
            Map<String,Object> map=new HashMap<String,Object>();
            String hrTeamDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
            Map<String,Object> teamData= JSON.parseObject(hrTeamDOs, Map.class);
            map.put("team",teamData);
            if(hasPic){
                int resId=DO.getResId();
                for(HrResourceDO hrResourceDO:resourceList){
                    int id=hrResourceDO.getId();
                    if(id==resId){
                        String hrResourceDOStr=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(hrResourceDO);
                        Map<String,Object> hrResourceData= JSON.parseObject(hrResourceDOStr, Map.class);
                        map.put("teamPic",hrResourceData);
                        break;
                    }
                }
            }
            if(teamPosition!=null&&!teamPosition.isEmpty()){
                for(Integer key:teamPosition.keySet()){
                    if(key==teamId){
                        map.put("positionNum",teamPosition.get(key));
                    }
                }
            }
            if(map.get("positionNum")==null){
                map.put("positionNum",0);
            }
            if(teamMember!=null&&!teamMember.isEmpty()){
                for(Integer key:teamMember.keySet()){
                    if(teamId==key){
                        map.put("teamMember",teamMember.get(key));
                        break;
                    }
                }
            }
            list.add(map);
        }
        result.put("teamPosition",list);
        return result;
    }
    /*
    获取res.id
     */
    private List<Integer> getResdListId(List<HrTeamDO> teamDOList){
        if(StringUtils.isEmptyList(teamDOList)){
            return null;
        }
        List<Integer> list=new ArrayList<Integer>();
        for(HrTeamDO DO:teamDOList){
            int resId=DO.getResId();
            if(resId!=0){
                list.add(resId);
            }
        }
        return list;
    }
    /*
     获取team.id list
     */
    private List<Integer> getTeamIdList(List<HrTeamDO> teamList){
        if(StringUtils.isEmptyList(teamList)){
            return null;
        }
        List<Integer> list=new ArrayList<Integer>();
        for(HrTeamDO DO:teamList){
            list.add(DO.getId());
        }
        return list;
    }
    /*
       获取公司信息
     */
    private HrCompanyDO getHrCompany(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyDO DO= hrCompanyDao.getData(query);

        return DO;
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
        那么获取子公司的publisher
     */
    public List<Integer> getCompanyPublisher(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        List<HrCompanyAccountDO> list=hrCompanyAccountDao.getDatas(query);
        List<Integer> result=new ArrayList<Integer>();
        if(StringUtils.isEmptyList(list)){
            return result;
        }
        for (HrCompanyAccountDO DO:list){
            result.add(DO.getAccountId());
        }
        return result;
    }
    //获取母公司下所有的账号
    public List<Integer> getMotherCompanyPublisher(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        List<UserHrAccountDO> list=userHrAccountDao.getDatas(query);
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<Integer>();
        for(UserHrAccountDO DO:list){
            result.add(DO.getId());
        }
        return result;
    }
    /*
        获取子公司下的team_id
     */
    public List<Integer> getSubCompanyTeam(List<Integer> list,int page,int pageSize){
        if(StringUtils.isEmptyList(list)){
            return new ArrayList<Integer>();
        }
        Query query=new Query.QueryBuilder().select(new Select("team_id", SelectOp.DISTINCT)).where(new Condition("publisher",list.toArray(), ValueOp.IN)).and("status",0).setPageNum(page).setPageSize(pageSize).buildQuery();
        List<Map<String,Object>> result=jobPositionDao.getMaps(query);
        List<Integer> teamList=new ArrayList<Integer>();
        if(StringUtils.isEmptyList(result)){
            return new ArrayList<Integer>();
        }
        for(Map<String,Object> map:result){
            int teamId= (int) map.get("team_id");
            teamList.add(teamId);
        }
        return teamList;
    }
    /*
        获取子公司下团队的数量
     */
    public int getSubCompanyTeam(List<Integer> list){
        if(StringUtils.isEmptyList(list)){
            return 0;
        }
        Query query=new Query.QueryBuilder().select(new Select("team_id", SelectOp.DISTINCT)).where(new Condition("publisher",list.toArray(), ValueOp.IN)).and("status",0).buildQuery();
        int num=jobPositionDao.getCount(query);
        return num;
    }
    /*
          获取母公司下team的id
     */
    public List<HrTeamDO> getCompanyTeam(int companyId,int page,int pageSize){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("is_show",1).and("disable",0).orderBy("show_order").setPageNum(page).setPageSize(pageSize).buildQuery();
        List<HrTeamDO> list=hrTeamDao.getDatas(query);
        return list;
    }
    /*
      获取母公司下团队的数量
     */
    public int getCompanyTeamNum(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("is_show",1).and("disable",0).orderBy("show_order").buildQuery();
        int num=hrTeamDao.getCount(query);
        return num;
    }
    /*
        获取公司下团队职位数量
     */
    public Map<Integer,Integer> getTeamPositionNum(List<Integer> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Query query=new Query.QueryBuilder().select(new Select("id", SelectOp.COUNT)).select("team_id").where(new Condition("team_id",list.toArray(), ValueOp.IN)).and("status",0).groupBy("team_id").buildQuery();
        List<Map<String,Object>> result=jobPositionDao.getMaps(query);
        if(StringUtils.isEmptyList(result)){
            return null;
        }
        Map<Integer,Integer> teamPosition=new HashMap<>();
        for(Map<String,Object> map:result){
            int teamId= (int) map.get("team_id");
            int num= (int) map.get("id_count");
            if(teamPosition.get(teamId)==null){
                teamPosition.put(teamId,num);
            }
        }
        return teamPosition;
    }

}
