package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionShareRecordDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ShareReferralHandler extends AbstractReferralTypeHandler {

    @Override
    ReferralTypeEnum getReferralType() {
        return ReferralTypeEnum.SHARE_REFERRAL;
    }

    @Override
    protected JSONObject initRecomUserInfo(JobApplicationDO jobApplicationDO, JSONObject referralTypeSingleMap) {
        TypeReference<List<JobApplicationDO>> applyTypeRef = new TypeReference<List<JobApplicationDO>>(){};
        List<JobApplicationDO> oneDegree = JSON.parseObject(referralTypeSingleMap.getString("oneDegree"),applyTypeRef);
        TypeReference<List<CandidatePositionShareRecordDO>> shareRecordTypeRef = new TypeReference<List<CandidatePositionShareRecordDO>>(){};
        List<CandidatePositionShareRecordDO> shareRecordDOS = JSON.parseObject(referralTypeSingleMap.getString("shareRecords"),shareRecordTypeRef);
        TypeReference<Map<Integer, CandidateShareChainDO>> moreDegree = new TypeReference<Map<Integer, CandidateShareChainDO>>(){};
        Map<Integer, CandidateShareChainDO> moreDegreeMap = JSON.parseObject(referralTypeSingleMap.getString("moreDegree"),moreDegree);
        TypeReference<Map<Integer, UserWxUserDO>> wxUserMapType = new TypeReference<Map<Integer, UserWxUserDO>>(){};
        Map<Integer, UserWxUserDO> wxUserMap = JSON.parseObject(referralTypeSingleMap.getString("wxUserMap"), wxUserMapType);
        JSONObject recom = new JSONObject();
        for(JobApplicationDO one : oneDegree){
            if(one.getId() == jobApplicationDO.getId()){
                recom.put("type", getReferralType().getType());
                return recom;
            }
        }
        boolean clickFromWxGroup = false;
        CandidateShareChainDO shareChainDO = moreDegreeMap.get(jobApplicationDO.getId());
        for(CandidatePositionShareRecordDO shareRecordDO : shareRecordDOS){
            if(shareChainDO.getId() == shareRecordDO.getShareChainId()){
                if(shareRecordDO.getClickFrom() == 1){
                    clickFromWxGroup = true;
                    break;
                }
            }
        }
        recom.put("nickname", wxUserMap.get(shareChainDO.getPresenteeUserId()).getNickname());
        recom.put("from_wx_group", clickFromWxGroup ? 1 : 0);
        recom.put("type", getReferralType().getType());
        return recom;
    }

    @Override
    protected List<JobApplicationDO> getApplicationsByReferralType(List<JobApplicationDO> jobApplicationDOS) {
        List<JobApplicationDO> shareReferralList = new ArrayList<>();
        for(JobApplicationDO jobApplicationDO : jobApplicationDOS){
            ReferralTypeEnum referralTypeEnum = ReferralTypeEnum.getReferralTypeByApplySource(jobApplicationDO.getOrigin());
            if(referralTypeEnum.getType() == ReferralTypeEnum.SHARE_REFERRAL.getType()){
                shareReferralList.add(jobApplicationDO);
            }
        }
        return shareReferralList;
    }

    @Override
    protected JSONObject getReferralTypeMap(UserEmployeeRecord employeeRecord, List<JobApplicationDO> jobApplicationDOS) {
        List<JobApplicationDO> shareReferralList = getApplicationsByReferralType(jobApplicationDOS);
        HrWxWechatDO hrWxWechatDO = wxWechatDao.getHrWxWechatByCompanyId(employeeRecord.getCompanyId());
        List<Integer> shareUserIds = shareReferralList.stream().map(JobApplicationDO::getApplierId).distinct().collect(Collectors.toList());
        List<Integer> sharePids = shareReferralList.stream().map(JobApplicationDO::getPositionId).distinct().collect(Collectors.toList());
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getShareChainsByUserIdAndPresenteeAndPosition(employeeRecord.getSysuserId(), shareUserIds, sharePids);
        List<JobApplicationDO> oneDegreeJobApplication = new ArrayList<>();
        // 两度及以上链路对应申请
        JSONObject result = new JSONObject();

        for(JobApplicationDO jobApplicationDO : shareReferralList){
            boolean flag = true;
            for(int i=0;i<shareChainDOS.size()&&flag;i++){
                CandidateShareChainDO shareChainDO = shareChainDOS.get(i);
                if(shareChainDO.getRecomUserId() == employeeRecord.getSysuserId() && shareChainDO.getPresenteeUserId() == jobApplicationDO.getApplierId()
                && shareChainDO.getPositionId() == jobApplicationDO.getPositionId()){
                    oneDegreeJobApplication.add(jobApplicationDO);
                    flag = false;
                }
            }
        }

        Map<Integer, CandidateShareChainDO>  appIdShareChainMap = new HashMap<>();
        Set<Integer> shareChainIds = new HashSet<>();
        Set<Integer> oneDegreeUserIds = new HashSet<>();
        for(JobApplicationDO jobApplicationDO : shareReferralList){
            boolean flag = true;
            for(int i=0;i<shareChainDOS.size()&&flag;i++){
                CandidateShareChainDO shareChainDO = shareChainDOS.get(i);
                if(shareChainDO.getRecomUserId() == employeeRecord.getSysuserId() && shareChainDO.getRoot2RecomUserId() == 0
                        && shareChainDO.getPositionId() == jobApplicationDO.getPositionId()
                        && shareChainDO.getPresenteeUserId() != jobApplicationDO.getApplierId()){
                    appIdShareChainMap.put(jobApplicationDO.getId(), shareChainDO);
                    oneDegreeUserIds.add(shareChainDO.getPresenteeUserId());
                    shareChainIds.add(shareChainDO.getId());
                    flag = false;
                }
            }
        }

        Map<Integer, UserWxUserDO> userWxUserDOMap = new HashMap<>(1 >> 4);
        List<UserWxUserDO> wxUserDOS = wxUserDao.getWXUsersByUserIds(oneDegreeUserIds, hrWxWechatDO.getId());
        wxUserDOS.forEach(userWxUserDO -> userWxUserDOMap.put(userWxUserDO.getSysuserId(), userWxUserDO));
        List<CandidatePositionShareRecordDO> positionShareRecordDOS =
                positionShareRecordDao.fetchPositionShareByShareChainIds(shareChainIds);
        result.put("oneDegree", JSON.toJSONString(oneDegreeJobApplication));
        result.put("moreDegree", JSON.toJSONString(appIdShareChainMap));
        result.put("shareRecords", JSON.toJSONString(positionShareRecordDOS));
        result.put("wxUserMap", JSON.toJSONString(userWxUserDOMap));
        return result;
    }

    @Override
    public void postProcessAfterCreateCard(JSONObject card, Map<String, Object> applierDegrees) {
        card.put("degree", 0);
    }

}
