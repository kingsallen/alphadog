package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.hrdb.HrCmsMediaDao;
import com.moseeker.baseorm.dao.hrdb.HrCmsModuleDao;
import com.moseeker.baseorm.dao.hrdb.HrCmsPagesDao;
import com.moseeker.baseorm.dao.hrdb.HrResourceDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zztaiwll on 17/8/15.
 */
@Service
public class JobPositionCityEntity {
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private JobPositionCityDao jobPositionCityDao;
    @Autowired
    private DictCityDao dictCityDao;
    @Autowired
    private HrCmsPagesDao hrCmsPagesDao;
    @Autowired
    private HrCmsModuleDao  hrCmsModuleDao;
    @Autowired
    private HrCmsMediaDao hrCmsMediaDao;
    @Autowired
    private HrResourceDao hrResourceDao;
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
    //获取所有的publisher
    public List<Integer> getAllPulisherByCompanyPublisher(Map<Integer,List<Integer>> companyPublisher){
        if(companyPublisher==null||companyPublisher.isEmpty()){
            return null;
        }
        List<Integer> list=new ArrayList<Integer>();
        for(Integer key:companyPublisher.keySet()){
            List<Integer> publishers=companyPublisher.get(key);
            if(!StringUtils.isEmptyList(publishers)){
                list.addAll(publishers);
            }
        }
        return list;
    }
    /*
     * 获取公司hr下的职位
     */
    public List<JobPositionDO> getPositionByPublisher(List<Integer> publisherList){
        if(StringUtils.isEmptyList(publisherList)){
            return null;
        }
        Query query=new Query.QueryBuilder()
                .where(new Condition("publisher",publisherList.toArray(), ValueOp.IN))
                .and("status",0)
                .setPageNum(1)
                .setPageSize(300)
                .buildQuery();
        List<JobPositionDO> list=jobPositionDao.getDatas(query);
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
    /*
     * 处理公司信息，获取公司下的positionId
     */
    public Map<Integer,Set<Integer>> getCompanyPositionIds(Map<Integer,List<Integer>> companyPublisher, List<JobPositionDO> positionData){
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

    //处理数据获取Set<companyId,Set<city>>的数据
    public Map<Integer,Set<String>> handlerDataCompanyCity(Map<Integer,Set<Integer>> companyPosition,Map<Integer,List<String>> positionCityMap){

        Map<Integer,Set<String>> data=new HashMap<Integer,Set<String>>();
        for(Integer key1:positionCityMap.keySet()){
            List<String> cities=positionCityMap.get(key1);
            if(!StringUtils.isEmptyList(cities)){
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
    //根据positionid列表获取list的jobPositioncity
    public List<JobPositionCityDO> getJobPositionCity(List<Integer> positionIds){
        if(StringUtils.isEmptyList(positionIds)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("pid",positionIds.toArray(),ValueOp.IN)).buildQuery();
        return jobPositionCityDao.getDatas(query);
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
    //获取jd页
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
        List<HrResourceDO> list4=hrResourceDao.getHrResourceByIdList(resIds);
        if(list4==null||list4.size()==0){
            return null;
        }
        this.handleResourceListPic(maps1,list4);
        this.handleResourceListData(maps1, list4);
        return maps1;
    }

    //处理hrcmspages的数据将之处理完放在map当中
    private void handlePageListData(List<Map<String,Object>> mapList,List<HrCmsPagesDO> list) throws TException {
        Map<String,Object> map=null;
        for(int i=0;i<list.size();i++){
            map=new HashMap<String,Object>();
            HrCmsPagesDO pagesDO=list.get(i);
            int configId=pagesDO.getConfigId();
            int pageId=pagesDO.getId();
            map.put("pageId",pageId);
            map.put("configId",configId);
            String pageDataString=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(pagesDO);
            Map<String,Object> pageData= JSON.parseObject(pageDataString, Map.class);
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
}
