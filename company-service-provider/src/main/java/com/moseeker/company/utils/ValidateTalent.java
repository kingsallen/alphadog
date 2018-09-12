package com.moseeker.company.utils;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.bean.ValidateTalentBean;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zztaiwll on 17/12/29.
 */
@Service
public class ValidateTalent {
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    @Autowired
    private ProfileProfileDao profileProfileDao;
    /*
    处理批量传输的人才，获取其中有效的和无效的
    */
    public ValidateTalentBean handlerApplierId(int hrId, Set<Integer> userIdList, int companyId){
        ValidateTalentBean bean=new ValidateTalentBean();
        int flag= talentPoolEntity.valiadteMainAccount(hrId,companyId);
        Set<Integer> unUsedApplierIdList=new HashSet<>();
        Set<Integer> applierIdList=new HashSet<>();
         /*
         上传的简历没有任何求职申请，所以肯定不在这种逻辑之内，所以在取消收藏时自动过滤
         */
        if(flag==0){
            List<JobApplicationRecord> list=this.getJobApplicationByPublisherAndApplierId(userIdList,hrId);
            applierIdList=this.getIdListByApplicationList(list);
        }else{

            List<JobApplicationRecord> list=talentPoolEntity.getJobApplicationByCompanyIdAndApplierId(userIdList,companyId);
            applierIdList=this.getIdListByApplicationList(list);
        }
        // todo 新增，通过简历搬家收藏的人才不能取消收藏
        applierIdList = filterMvHouseApplierId(applierIdList);
        unUsedApplierIdList= this.filterIdList(userIdList,applierIdList);
        bean.setUnUseUserIdSet(unUsedApplierIdList);
        bean.setUserIdSet(applierIdList);
        return bean;
    }
    /**
     * 过滤简历搬家的人才id
     * @param   userIdList userIdList
     * @author  cjm
     * @date  2018/9/11
     * @return  过滤后的userIdList
     */
    private Set<Integer> filterMvHouseApplierId(Set<Integer> userIdList){
        List<ProfileProfileDO> profileProfileDOS = profileProfileDao.getProfileByUidList(userIdList);
        userIdList = profileProfileDOS.stream().filter(profileProfileDO ->
                (!ChannelType.MVHOUSEJOB51DOWNLOAD.getOrigin("").equals(profileProfileDO.getOrigin())
                        && !ChannelType.MVHOUSEJOB51UPLOAD.getOrigin("").equals(profileProfileDO.getOrigin())
                        && !ChannelType.MVHOUSEZHILIANDOWNLOAD.getOrigin("").equals(profileProfileDO.getOrigin())
                        && !ChannelType.MVHOUSEZHILIANUPLOAD.getOrigin("").equals(profileProfileDO.getOrigin())
                ))
                .map(ProfileProfileDO::getUserId).collect(Collectors.toSet());
        return userIdList;
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
