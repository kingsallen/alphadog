package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.entity.JobPositionCityEntity;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.thrift.TException;
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
    private JobPositionCityEntity jobPositionCityEntity;
    @Autowired
    private UserHrAccountDao userHrAccountDao;

    public Map<String,Object> getCompanyInfo(int companyId) throws Exception {
        Map<String,Object>map=new HashMap<String,Object>();
        HrCompanyDO company=this.getHrCompany(companyId);
        if(company==null){
            return null;
        }
        map.put("company",company);
        int parentId=company.getParentId();
        int confCompanyId=company.getId();
        boolean isMother=false;
        if(parentId!=0){
            confCompanyId=parentId;
            isMother=true;
        }
        this.handleJdData(confCompanyId,map,companyId);
        this.handleTeamInfo(companyId,isMother,map);
        this.handleCompanyPositionCity(companyId,isMother,map);
        return map;
    }
    /*
     获取jd信息
     */
    private void handleJdData(int confCompanyId,Map<String,Object> map,int companyId) throws Exception {
        map.put("newjd",0);
        HrCompanyConfDO hrCompanyConfDO=getHrCompanyConf(confCompanyId);
        if(hrCompanyConfDO!=null){
            int newJdStatus=hrCompanyConfDO.getNewjdStatus();
            if(newJdStatus==2){
                List<Integer> jdID=new ArrayList<Integer>();
                jdID.add(companyId);
                List<Map<String,Object>>jdList=jobPositionCityEntity.HandleCmsResource(jdID,1);
                if(!StringUtils.isEmptyList(jdList)){
                    Map<String,Object> jdMap=jdList.get(0);
                    if(jdMap!=null&&!jdMap.isEmpty()){
                        map.put("newjd",1);
                        map.put("jd",jdMap);
                    }
                }
            }
        }
    }
    /*
      获取团队信息
     */
    private void handleTeamInfo(int companyId,boolean isMother,Map<String,Object> map){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        if(isMother){
            list=this.handleMotherCompanyTeam(companyId);
        }else{
            list=this.handleSubCompanyTeam(companyId);
        }
        if(!StringUtils.isEmptyList(list)){
            map.put("teamlist",list);
        }

    }
    //处理公司下的职位
    private void handleCompanyPositionCity(int companyId,boolean isMother,Map<String,Object> map){
        Map<Integer,Set<String>> result=new HashMap<>();
        if(isMother){
            result=handleMotherCompanyPositionCity(companyId);

        }else{
            result=hanldeSubCompanyPositionCity(companyId);
        }
        if(result!=null&&!result.isEmpty()){
            Set<String> cityList=result.get(companyId);
            if(cityList!=null&&cityList.size()>0){
                map.put("positioncity",cityList);
            }
        }
    }
    //处理母公司下职位的城市
    private Map<Integer,Set<String>> handleMotherCompanyPositionCity(int companyId){
        List<Integer> list=this.getMotherCompanyPublisher(companyId);
        if(!StringUtils.isEmptyList(list)){
            Map<Integer,List<Integer>> companyPublisher=new HashMap<Integer,List<Integer>>();
            companyPublisher.put(companyId,list);
            Map<Integer,Set<String>> result=jobPositionCityEntity.handlerCompanyPositionCity(companyPublisher);
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
            Map<Integer,Set<String>> result=jobPositionCityEntity.handlerCompanyPositionCity(companyPublisher);
            return result;
        }
        return null;
    }
    //处理母公司的团队信息
    private List<Map<String,Object>> handleMotherCompanyTeam(int companyId){
        List<HrTeamDO> teamList=this.getCompanyTeam(companyId);
        List<Integer>teamIdList=getTeamIdList(teamList);
        Map<Integer,Integer> teamPosition=getTeamPositionNum(teamIdList);
        List<Map<String,Object>> list=this.handleTeamPosition(teamList,teamPosition);
        return list;
    }
    //处理子公司团队的信息
    private List<Map<String,Object>> handleSubCompanyTeam(int companyId){
        List<Integer> teamIdList=getCompanyPublisher(companyId);
        List<HrTeamDO> teamList= hrTeamDao.getTeamList(teamIdList);
        Map<Integer,Integer> teamPosition=getTeamPositionNum(teamIdList);
        List<Map<String,Object>> list=this.handleTeamPosition(teamList,teamPosition);
        return list;
    }
    /*
       处理团队和团队的职位数量之间的关系
     */
    private List<Map<String,Object>> handleTeamPosition(List<HrTeamDO> teamList, Map<Integer,Integer> teamPosition){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        if(StringUtils.isEmptyList(teamList)){
            return null;
        }
        for(HrTeamDO DO:teamList){
            int teamId= DO.getId();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("team",DO);
            for(Integer key:teamPosition.keySet()){
               if(key==teamId){
                   map.put("positionNum",teamPosition.get(key));
               }
            }
            if(map.get("positionNum")==null){
                map.put("positionNum",0);
            }
            list.add(map);
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
    public List<Integer> getSubCompanyTeam(List<Integer> list){
        Query query=new Query.QueryBuilder().select(new Select("team_id", SelectOp.DISTINCT)).where(new Condition("publisher",list.toArray(), ValueOp.IN)).and("status",0).buildQuery();
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
          获取母公司下team的id
     */
    public List<HrTeamDO> getCompanyTeam(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("is_show",1).and("disable",0).orderBy("show_order").buildQuery();
        List<HrTeamDO> list=hrTeamDao.getDatas(query);
        return list;
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
            int num= (int) map.get("id_field");
            if(teamPosition.get(teamId)==null){
                teamPosition.put(teamId,num);
            }
        }
        return teamPosition;
    }




}
