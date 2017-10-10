package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionExtDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    /*
        如果有则更新，没有则插入
     */
    public int putAlipayPositionResult(int channel,int positionId,int alipayJobId ){
            return jobPositionExtDao.insertOrUpdateData(positionId,alipayJobId);

    }
    /*
        获取同步的职位
     */
    public List<Map<String,Object>> getThridPositionAlipay(int publisher,int companyId,int candidateSource,int page,int pageSize) throws TException {
        List<JobPositionDO> list=this.getPositionList(publisher,companyId,candidateSource);
        List<Integer> pidList=this.getIdByPositionList(list);
        List<JobPositionExtDO> extList=this.getJobPositionExtByPositionIdList(pidList,page,pageSize);
        List<Map<String,Object>> result=this.handleData(list,extList);
        return result;
    }
    /*
     处理JobPositionDO数据和JobPositionExtDO数据，最终获取list《map》数据
     */
    private List<Map<String,Object>> handleData(List<JobPositionDO> list,List<JobPositionExtDO> extList) throws TException {
        List<Map<String,Object>> result=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)&&!StringUtils.isEmptyList(extList)){
            for(JobPositionExtDO extDO:extList){
                int pid=extDO.getPid();
                for(JobPositionDO DO:list){
                    int positionId=DO.getId();
                    if(pid==positionId){
                        String positionDOs=new TSerializer(new TSimpleJSONProtocol.Factory()).toString(DO);
                        Map<String,Object> positionData= JSON.parseObject(positionDOs, Map.class);
                        result.add(positionData);
                    }
                }
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
                 .and(new Condition("ALIPAY_JOB_ID",0,ValueOp.GT)).setPageNum(page)
                 .setPageSize(pageSize).orderBy("update_time").buildQuery();
         List<JobPositionExtDO> list=jobPositionExtDao.getDatas(query);
         return list;
     }

}
