package com.moseeker.company.utils;

import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.bean.ValidateTalentBean;
import com.moseeker.entity.TalentPoolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zztaiwll on 17/12/29.
 */
@Service
public class ValidateTalent {
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    /*
    处理批量传输的人才，获取其中有效的和无效的
    */
    public ValidateTalentBean handlerApplierId(int hrId, Set<Integer> userIdList, int companyId){
        ValidateTalentBean bean=new ValidateTalentBean();
        int flag= talentPoolEntity.valiadteMainAccount(hrId,companyId);
        Set<Integer> unUsedApplierIdList=new HashSet<>();
        Set<Integer> applierIdList=new HashSet<>();
        if(flag==0){
            List<JobApplicationRecord> list=this.getJobApplicationByPublisherAndApplierId(userIdList,hrId);
            applierIdList=this.getIdListByApplicationList(list);
        }else{
            List<JobApplicationRecord> list=talentPoolEntity.getJobApplicationByCompanyIdAndApplierId(userIdList,companyId);
            applierIdList=this.getIdListByApplicationList(list);
        }
        unUsedApplierIdList= this.filterIdList(userIdList,applierIdList);
        bean.setUnUseUserIdSet(unUsedApplierIdList);
        bean.setUserIdSet(applierIdList);
        return bean;
    }

    /*
     获取userIdList在这个hr下所有的申请
    */
    private List<JobApplicationRecord> getJobApplicationByPublisherAndApplierId(Set<Integer> userIdList,int hrId){
        Set<Integer> pidList=talentPoolEntity.getPositionIdByPublisher(hrId);
        if(StringUtils.isEmptySet(pidList)){
            return null;
        }
        List<JobApplicationRecord> list=talentPoolEntity.getJobApplication(userIdList,pidList);
        return list;
    }
    /*
      获取applierId
     */
    private Set<Integer> getIdListByApplicationList(List<JobApplicationRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Set<Integer> result=new HashSet<>();
        for(JobApplicationRecord record:list){
            result.add(record.getApplierId());
        }
        return result;
    }
    /*
      过滤出来不符合操作权限的applier_id
     */
    private Set<Integer> filterIdList(Set<Integer> userIdList,Set<Integer> list){
        if(StringUtils.isEmptySet(list)){
            return userIdList;
        }
        Set<Integer> result=new HashSet<>();
        for(Integer userId:userIdList){
            int flag=1;
            for(Integer id:list){
                if(userId.equals(id)){
                    flag=0;
                    break;
                }

            }
            if(flag==1){
                result.add(userId);
            }else{
                continue;
            }
        }

        return result;
    }

    /*
     通过TalentpoolHrTalentRecord 的集合获取User_id的list
     */
    private Set<Integer> getIdListByTalentpoolHrTalentList(List<Map<String,Object>> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Set<Integer> UserIdList=new HashSet<>();
        for(Map<String,Object> record:list){
            UserIdList.add((int)record.get("user_id"));
        }
        return UserIdList;
    }
}
