package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictAlipaycampusCityDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionExtDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictAlipaycampusCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 17/10/10.
 */
@Service
public class PositionThridService {
    @Autowired
    private JobPositionExtDao jobPositionExtDao;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private JobPositionCityDao jobPositionCityDao;
    @Autowired
    private DictAlipaycampusCityDao dictAlipaycampusCityDao;
    /*
        如果有则更新，没有则插入
     */
    public int putAlipayPositionResult(int channel,int positionId,int alipayJobId ){
            return jobPositionExtDao.insertOrUpdateData(positionId,alipayJobId);

    }
    /*
        获取同步的职位
     */
    public Map<String,Object> getThridPositionAlipay(int publisher,int companyId,int candidateSource,int page,int pageSize) throws TException {
        Map<String,Object> map=new HashMap<String,Object>();
        List<JobPositionDO> list=this.getPositionList(publisher,companyId,candidateSource);
        List<Integer> pidList=this.getIdByPositionList(list);
        List<JobPositionExtDO> extList=this.getJobPositionExtByPositionIdList(pidList,page,pageSize);
        List<Map<String,Object>> result=this.handleData(list,extList);
        if(!StringUtils.isEmptyList(result)){
            map.put("positions",result);
            map.put("company",this.getCompanyData(publisher,companyId));
            map.put("positionNum",getJobPositionExtNum(pidList));
        }
        return map;
    }
    /*
     处理JobPositionDO数据和JobPositionExtDO数据，最终获取list《map》数据
     */
    private List<Map<String,Object>> handleData(List<JobPositionDO> list,List<JobPositionExtDO> extList) throws TException {
        List<Map<String,Object>> result=new ArrayList<>();
        List<JobPositionDO> positionList=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)&&!StringUtils.isEmptyList(extList)){
            for(JobPositionExtDO extDO:extList){
                int pid=extDO.getPid();
                for(JobPositionDO DO:list){
                    int positionId=DO.getId();
                    if(pid==positionId){
                        positionList.add(DO);

                    }
                }
            }
        }
        //处理获取所有职位的城市
        Map<Integer,String> cityMap=this.handlePositionCityForAlipay(positionList);
        if(cityMap!=null&&!cityMap.isEmpty()){
           for(JobPositionDO DO:positionList){
               int pid=DO.getId();
               for(Integer key:cityMap.keySet()){
                    if(pid==key){
                        DO.setCity(cityMap.get(key));
                        break;
                    }
               }
               String positionDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
               Map<String,Object> positionData= JSON.parseObject(positionDOs, Map.class);
               result.add(positionData);
            }
        }
        return result;
    }
    private List<JobPositionDO> getPositionList(int publisher,int companyId,int candidateSource){
        List<JobPositionDO> list=new ArrayList<>();
        if(publisher!=0){
            list=this.getpositionByPublisherAndCandidateSource(publisher,candidateSource);
        }else{
            list=this.getPositionByCompanyIdAndCandidateSource(companyId,candidateSource);
        }
        return list;
    }

    /*
            根据publisher和candidateSource获取position列表
     */
    private List<JobPositionDO> getpositionByPublisherAndCandidateSource(int publisher, int candidateSource){
        Query query=new Query.QueryBuilder().where("source",0).and("candidate_source",candidateSource)
                .and("publisher",publisher).buildQuery();
        List<JobPositionDO> list=jobPositionDao.getDatas(query);
        return list;
    }
    /*
        根据companyId和candidateSource获取position列表
    */
    private List<JobPositionDO> getPositionByCompanyIdAndCandidateSource(int companyId,int candidateSource){
        Query query=new Query.QueryBuilder().where("source",0).and("candidate_source",candidateSource)
                .and("company_id",companyId).buildQuery();
        List<JobPositionDO> list=jobPositionDao.getDatas(query);
        return list;
    }
    /*
      获取id
     */
     public List<Integer> getIdByPositionList(List<JobPositionDO> list){
         if(StringUtils.isEmptyList(list)){
             return null;
         }
         List<Integer> result=new ArrayList<>();
         for(JobPositionDO DO:list){
             result.add(DO.getId());
         }
         return result;
     }
     /*
        获取jobPositionExt
      */
     public List<JobPositionExtDO> getJobPositionExtByPositionIdList(List<Integer> pidList,int page,int pageSize){
         if(StringUtils.isEmptyList(pidList)){
             return null;
         }
         Query query=new Query.QueryBuilder().where(new Condition("pid",pidList.toArray(), ValueOp.IN))
                 .and(new Condition("alipay_job_id",0,ValueOp.GT)).setPageNum(page)
                 .setPageSize(pageSize).orderBy("update_time").buildQuery();
         List<JobPositionExtDO> list=jobPositionExtDao.getDatas(query);
         return list;
     }
    /*
        获取jobPositionExt的总数
     */
    public int getJobPositionExtNum(List<Integer> pidList){
        if(StringUtils.isEmptyList(pidList)){
            return 0;
        }
        Query query=new Query.QueryBuilder().where(new Condition("pid",pidList.toArray(), ValueOp.IN))
                .and(new Condition("alipay_job_id",0,ValueOp.GT)).buildQuery();
        int num=jobPositionExtDao.getCount(query);
        return num;
    }
     //获取公司
    public Map<String,Object> getCompanyData(int publisher,int companyId) throws TException {
         HrCompanyDO DO=null;
        if(publisher==0){
            DO=this.getCompanyDObyPublisher(publisher);
        }else{
            DO=this.getCompanyDOById(companyId);;
        }
        if(DO==null){
            return null;
        }
        String CompanyDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
        Map<String,Object> companyData= JSON.parseObject(CompanyDOs, Map.class);
        return companyData;
    }
    //根据publisher获取公司信息

    public HrCompanyDO getCompanyDObyPublisher(int publisher){
        HrCompanyAccountDO account=this.getCompanyAccountByPublisher(publisher);
        if(account==null){
            return null;
        }
        int companyId=account.getCompanyId();
        HrCompanyDO  DO=this.getCompanyDOById(companyId);
        return DO;

    }
    //根据publisher获取companyId
    private HrCompanyAccountDO  getCompanyAccountByPublisher(int publisher){
        Query query=new Query.QueryBuilder().where("account_id",publisher).buildQuery();
        HrCompanyAccountDO account=hrCompanyAccountDao.getData(query);
        return account;
    }
    //根据companyid获取companyDO
    private HrCompanyDO getCompanyDOById(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyDO DO=hrCompanyDao.getData(query);
        return DO;
    }
    //处理获取所有职位的城市
    public Map<Integer,String > handlePositionCityForAlipay(List<JobPositionDO> list){
        List<Integer> pidList=this.getIdByPositionList(list);
        List<JobPositionCityDO> positionCitylist=this.getJobPositionCityByPidList(pidList);
        Map<Integer,Integer> jobPositionCityMap=handleJobPositionCityData(positionCitylist,pidList);
        List<Integer> codeList=this.getCodelist(jobPositionCityMap);
        List<DictAlipaycampusCityDO> dictAlipaycampusCityDOList=this.getAlipayCityList(codeList);
        Map<Integer,Object> alipayCityMap=this.handleAlipayCity(dictAlipaycampusCityDOList);
        Map<Integer,String> level3CityMap=handleLevel3Data(alipayCityMap);
        Map<Integer,String> CodeCityMap=handleCodeCityName(alipayCityMap,level3CityMap);
        Map<Integer,String> result=handlePositionCityName(jobPositionCityMap,CodeCityMap);
        return result;
    }

    //根据pid获取job_position_city
    private List<JobPositionCityDO> getJobPositionCityByPidList(List<Integer> pidList){
        if(StringUtils.isEmptyList(pidList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("pid",pidList.toArray(),ValueOp.IN)).buildQuery();
        List<JobPositionCityDO> list=jobPositionCityDao.getDatas(query);
        return list;
    }
    //组装数据，仅仅获取<pid,code>的格式
    private Map<Integer,Integer> handleJobPositionCityData(List<JobPositionCityDO> list,List<Integer> pidList){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Map<Integer,Integer> map=new HashMap<>();
        for(Integer positionId:pidList){
            if(map.get(positionId)==null){
                for(JobPositionCityDO DO:list){
                    int pid=DO.getPid();
                    int code=DO.getCode();
                    if(positionId==pid){
                        map.put(positionId,code);
                        break;
                    }
                }
            }

        }
        return map;
    }
    //获取codeList
    public List<Integer> getCodelist( Map<Integer,Integer> map){
        if(map==null||map.isEmpty()){
            return null;
        }
        List<Integer> codeList=new ArrayList<>();
        for(Integer key:map.keySet()){
            codeList.add(map.get(key));
        }
        return codeList;
    }
    //根据code获取支付宝城市的对应数据
    private List<DictAlipaycampusCityDO> getAlipayCityList(List<Integer> codeList){
        if(StringUtils.isEmptyList(codeList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("id",codeList.toArray(),ValueOp.IN)).buildQuery();
        List<DictAlipaycampusCityDO> list=dictAlipaycampusCityDao.getDatas(query);
        return list;
    }

    //组装数据，获取不同级别的map，用于进一步回溯城市
    private Map<Integer,Object> handleAlipayCity(List<DictAlipaycampusCityDO> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Map<Integer,Object> map=new HashMap<>();
        List<DictAlipaycampusCityDO> cityLevel3list=new ArrayList<>();
        List<Map<Integer,String>> cityLevellist=new ArrayList<>();
        for(DictAlipaycampusCityDO DO:list){
            if(DO.getId()!=3){
                Map<Integer,String> mapCity=new HashMap<>();
                String name=DO.getName();
                int code=DO.getId();
                mapCity.put(code,name);
                cityLevellist.add(mapCity);
            }else{
                cityLevel3list.add(DO);
            }
        }
        map.put(1,cityLevellist);
        map.put(2,cityLevel3list);
        return map;
    }
    //获取Alipaycity的pid列表
    private List<Integer> getDictAlipaycampusCityDOIdList(List<DictAlipaycampusCityDO> cityLevel3list){
        if(StringUtils.isEmptyList(cityLevel3list)){
            return null;
        }
        List<Integer> list=new ArrayList<>();
        for(DictAlipaycampusCityDO DO:cityLevel3list){
            list.add(DO.getPid());
        }
        return list;
    }

    private Map<Integer,String> handleLevel3Data(Map<Integer,Object> map){
        if(map==null||map.isEmpty()){
            return null;
        }
        List<DictAlipaycampusCityDO> cityLevel3list= (List<DictAlipaycampusCityDO>) map.get(2);
        Map<Integer,Integer> codeMap=this.getlevel3Codelist(map);
        List<Integer> pidList=this.getDictAlipaycampusCityDOIdList(cityLevel3list);
        Map<Integer,String> result=getUpLevelCityData(pidList,codeMap);
        return result;
    }
    //处理支付宝城市中三级城市的id和pid对应的map数据
    private Map<Integer,Integer> getlevel3Codelist( Map<Integer,Object> map){
        if(map==null||map.isEmpty()){
            return null;
        }
        List<DictAlipaycampusCityDO> cityLevel3list= (List<DictAlipaycampusCityDO>) map.get(2);
        Map<Integer,Integer> result=new HashMap<>();
        for(DictAlipaycampusCityDO DO:cityLevel3list){
            result.put(DO.getId(),DO.getPid());
        }
        return result;
    }
    //对city为level的数据回溯到上一级
    private Map<Integer,String> getUpLevelCityData(List<Integer> idList,Map<Integer,Integer> map){
        Map<Integer,String> result=new HashMap<>();
        if(StringUtils.isEmptyList(idList)||map!=null||map.isEmpty()){
            return null;
        }
        List<DictAlipaycampusCityDO> list=this.getAlipayCityList(idList);
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        for(Integer key:map.keySet()){
            int pid=map.get(key);
            for(DictAlipaycampusCityDO DO:list){
                int id=DO.getId();
                String name=DO.getName();
                if(pid==id){
                    result.put(key,name);
                    break;
                }
            }
        }
        return result;
    }
    //获取map<code,String>,即获取code和城市名称的map
    public Map<Integer,String> handleCodeCityName(Map<Integer,Object> levelCityMap,Map<Integer,String> level3CityMap){
        if(levelCityMap==null||levelCityMap.isEmpty()){
            return null;
        }
        Map<Integer,String> result=new HashMap<>();
        List<Map<Integer,String>> list= (List<Map<Integer, String>>) levelCityMap.get(1);
        if(!StringUtils.isEmptyList(list)){
            for(Map<Integer,String> map:list){
                result.putAll(map);
            }
        }
        if(level3CityMap!=null&&!level3CityMap.isEmpty()){
            result.putAll(level3CityMap);
        }
        return result;
    }
    //处理map<code,String>数据，将之改为Map<Position,String>
    public Map<Integer,String> handlePositionCityName(Map<Integer,Integer> positionCodeMap,Map<Integer,String> codeCityMap){
        if(positionCodeMap==null||positionCodeMap.isEmpty()||codeCityMap==null||codeCityMap.isEmpty()){
            return null;
        }
        Map<Integer,String> result=new HashMap<>();
        for(Integer key:positionCodeMap.keySet()){
            int code=positionCodeMap.get(key);
            for(Integer alipayCityId:codeCityMap.keySet()){
                String name=codeCityMap.get(alipayCityId);
                if(code==alipayCityId){
                    result.put(key,name);
                    break;
                }
            }
        }
        return result;
    }
}
