package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSON;
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
        TypeReference<Map<Integer, List<CandidateShareChainDO>>> moreDegree = new TypeReference< Map<Integer, List<CandidateShareChainDO>>>(){};
        Map<Integer, List<CandidateShareChainDO>> moreDegreeMap = JSON.parseObject(referralTypeSingleMap.getString("moreDegree"),moreDegree);
        JSONObject recom = new JSONObject();
        if(oneDegree != null && oneDegree.size() > 0){
            for(JobApplicationDO one : oneDegree){
                if(one.getId() == jobApplicationDO.getId()){

                }
            }
        }else {
            List<CandidateShareChainDO> shareChainDOS = moreDegreeMap.get(jobApplicationDO.getId());
            if(shareChainDOS == null || shareChainDOS.size() == 0){
                recom.put("nickname", "");
                recom.put("from_wx_group", 0);
            }else {
                for(CandidateShareChainDO shareChainDO : shareChainDOS){
                    // todo 找不到是否来自微信群，让玲玲想办法
                }
            }

        }

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
        Map<Integer, UserWxUserDO> userWxUserDOMap = getUserWxUserMap(shareChainDOS, hrWxWechatDO);
        // 两度及以上链路对应申请
        Map<Integer, List<CandidateShareChainDO>> appidShareChainsMap = new HashMap<>();
        List<Integer> recomUserIds = new ArrayList<>();
        List<Integer> beRecomUserIds = new ArrayList<>();
        JSONObject result = new JSONObject();
        for(JobApplicationDO jobApplicationDO : shareReferralList){
            boolean flag = true;
            for(int i=0;i<shareChainDOS.size()&&flag;i++){
                CandidateShareChainDO shareChainDO = shareChainDOS.get(i);
                if(shareChainDO.getRecomUserId() == employeeRecord.getSysuserId() && shareChainDO.getPresenteeUserId() == jobApplicationDO.getApplierId()
                && shareChainDO.getPositionId() == jobApplicationDO.getPositionId()){
                    oneDegreeJobApplication.add(jobApplicationDO);
                    flag = false;
                }else if(shareChainDO.getPositionId() == jobApplicationDO.getPositionId() && shareChainDO.getPresenteeUserId() == jobApplicationDO.getApplierId()
                && shareChainDO.getRootRecomUserId() == employeeRecord.getSysuserId()){
                    if(appidShareChainsMap.get(jobApplicationDO.getId()) == null){
                        List<CandidateShareChainDO> oneShareChains = new ArrayList<>();
                        oneShareChains.add(shareChainDO);
                        appidShareChainsMap.put(jobApplicationDO.getId(), oneShareChains);
                    }else {
                        appidShareChainsMap.get(jobApplicationDO.getId()).add(shareChainDO);
                    }
                    recomUserIds.add(shareChainDO.getRecomUserId());
                    beRecomUserIds.add(shareChainDO.getPresenteeUserId());
                }
            }
        }
        result.put("oneDegree", oneDegreeJobApplication);
        result.put("moreDegree", appidShareChainsMap);
        List<CandidatePositionShareRecordDO> positionShareRecordDOS = positionShareRecordDao.fetchPositionShareByUserIdsAndBeRecomUserIds(recomUserIds, beRecomUserIds, hrWxWechatDO.getId());
        result.put("shareRecords", positionShareRecordDOS);
        return result;
    }

    @Override
    public void postProcessAfterCreateCard(JSONObject card, Map<String, Object> applierDegrees) {
        card.put("degree", 0);
    }

    private Map<Integer, UserWxUserDO> getUserWxUserMap(List<CandidateShareChainDO> shareChainDOS, HrWxWechatDO hrWxWechatDO){
        Set<Integer> recomUserIds = shareChainDOS.stream().map(CandidateShareChainDO::getRecomUserId).collect(Collectors.toSet());
        List<UserWxUserDO> wxUserDOS = wxUserDao.getWXUsersByUserIds(recomUserIds, hrWxWechatDO.getId());
        Map<Integer, UserWxUserDO> userWxUserDOMap = new HashMap<>();
        wxUserDOS.forEach(userWxUserDO -> userWxUserDOMap.put(userWxUserDO.getSysuserId(), userWxUserDO));
        return userWxUserDOMap;
    }
}
