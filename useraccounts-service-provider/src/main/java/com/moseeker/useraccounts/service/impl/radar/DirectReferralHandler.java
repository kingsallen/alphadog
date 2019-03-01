package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DirectReferralHandler extends AbstractReferralTypeHandler{

    @Override
    ReferralTypeEnum getReferralType() {
        return ReferralTypeEnum.DIRECT_REFERRAL;
    }

    @Override
    protected JSONObject initRecomUserInfo(JobApplicationDO jobApplicationDO, JSONObject appIdClaimMap, boolean radarSwitchOpen) {
        JSONObject recom = new JSONObject();
        recom.put("type", getReferralType().getType());
        Object claim = appIdClaimMap.get(jobApplicationDO.getId()+"");
        if(claim == null){
            recom.put("claim", 0);
        }else {
            ReferralLog referralLog = (ReferralLog)claim;
            recom.put("claim", referralLog.getClaim());
            recom.put("rkey", referralLog.getId());
        }
        return recom;
    }

    @Override
    protected List<JobApplicationDO> getApplicationsByReferralType(List<JobApplicationDO> jobApplicationDOS) {
        List<JobApplicationDO> directReferralList = new ArrayList<>();
        for(JobApplicationDO jobApplicationDO : jobApplicationDOS){
            ReferralTypeEnum referralTypeEnum = ReferralTypeEnum.getReferralTypeByApplySource(jobApplicationDO.getOrigin());
            if(referralTypeEnum.getType() == ReferralTypeEnum.DIRECT_REFERRAL.getType()){
                directReferralList.add(jobApplicationDO);
            }
        }
        return directReferralList;
    }

    @Override
    protected JSONObject getReferralTypeMap(UserEmployeeRecord employeeRecord, List<JobApplicationDO> jobApplicationDOS, List<UserDepthVO> applierDegrees) {
        List<JobApplicationDO> directReferralList = getApplicationsByReferralType(jobApplicationDOS);
        List<Integer> referenceIds = directReferralList.stream().map(JobApplicationDO::getApplierId).distinct().collect(Collectors.toList());
        List<Integer> referencePids = directReferralList.stream().map(JobApplicationDO::getPositionId).distinct().collect(Collectors.toList());
        List<ReferralLog> referralLogs = referralLogDao.fetchByEmployeeIdAndReferenceIds(employeeRecord.getId(), referenceIds, referencePids);
        JSONObject claimApplyMap = new JSONObject();
        for(JobApplicationDO jobApplicationDO : directReferralList){
            boolean flag = true;
            for(int i=0;i<referralLogs.size()&&flag;i++){
                ReferralLog referralLog = referralLogs.get(i);
                if(jobApplicationDO.getApplierId() == referralLog.getReferenceId() && jobApplicationDO.getPositionId() == referralLog.getPositionId()){
                    claimApplyMap.put(jobApplicationDO.getId() + "", referralLog);
                    flag = false;
                }
            }
        }
        return claimApplyMap;
    }

    @Override
    public void postProcessAfterCreateCard(JSONObject card, JobApplicationDO jobApplicationDO, List<UserDepthVO> applierDegrees) {

    }
}
