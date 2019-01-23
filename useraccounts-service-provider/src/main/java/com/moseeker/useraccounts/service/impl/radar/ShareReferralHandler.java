package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralRecomEvaluationRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionShareRecordDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
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
    protected JSONObject initRecomUserInfo(JobApplicationDO jobApplicationDO, JSONObject referralTypeSingleMap, boolean radarSwitchOpen) {
        JSONObject recom = new JSONObject();
        recom.put("type", getReferralType().getType());
        if(radarSwitchOpen){
            return recom;
        }
        // 转发推荐类型（2）中的一度申请
        TypeReference<List<JobApplicationDO>> applyTypeRef = new TypeReference<List<JobApplicationDO>>(){};
        List<JobApplicationDO> oneDegree = JSON.parseObject(referralTypeSingleMap.getString("oneDegree"),applyTypeRef);
        // share_chain记录
        TypeReference<List<CandidatePositionShareRecordDO>> shareRecordTypeRef = new TypeReference<List<CandidatePositionShareRecordDO>>(){};
        List<CandidatePositionShareRecordDO> shareRecordDOS = JSON.parseObject(referralTypeSingleMap.getString("shareRecords"),shareRecordTypeRef);
        // 转发推荐类型（2）中的n度申请
        TypeReference<Map<Integer, CandidateShareChainDO>> moreDegree = new TypeReference<Map<Integer, CandidateShareChainDO>>(){};
        Map<Integer, CandidateShareChainDO> appIdShareChainMap = JSON.parseObject(referralTypeSingleMap.getString("appIdShareChainMap"),moreDegree);
        // 转发推荐类型（2）中的用户微信信息
        TypeReference<Map<Integer, UserWxUserDO>> wxUserMapType = new TypeReference<Map<Integer, UserWxUserDO>>(){};
        Map<Integer, UserWxUserDO> wxUserMap = JSON.parseObject(referralTypeSingleMap.getString("wxUserMap"), wxUserMapType);
        // 转发推荐类型（2）中的用户求推荐信息
        JSONArray evaluateIds = JSONArray.parseArray(referralTypeSingleMap.getString("evaluate"));
        recom.put("evaluate", evaluateIds.contains(jobApplicationDO.getId()) ? 1 : 0);
        CandidateShareChainDO shareChainDO = appIdShareChainMap.get(jobApplicationDO.getId());
        boolean clickFromWxGroup = false;
        String nickname = "";
        if(shareChainDO != null){
            for(CandidatePositionShareRecordDO shareRecordDO : shareRecordDOS){
                if(shareChainDO.getId() == shareRecordDO.getShareChainId()){
                    if(shareRecordDO.getClickFrom() == 2){
                        clickFromWxGroup = true;
                        break;
                    }
                }
            }
            nickname = wxUserMap.get(shareChainDO.getPresenteeUserId()).getNickname();
        }
        for(JobApplicationDO one : oneDegree){
            if(one.getId() == jobApplicationDO.getId()){
                recom.put("from_wx_group", clickFromWxGroup ? 1 : 0);
                return recom;
            }
        }
        recom.put("nickname", nickname);
        recom.put("from_wx_group", clickFromWxGroup ? 1 : 0);
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
    protected JSONObject getReferralTypeMap(UserEmployeeRecord employeeRecord, List<JobApplicationDO> jobApplicationDOS,
                                            List<UserDepthVO> applierDegrees) {
        List<JobApplicationDO> shareReferralList = getApplicationsByReferralType(jobApplicationDOS);
        List<Integer> seekAppids = shareReferralList.stream().map(JobApplicationDO::getId).distinct().collect(Collectors.toList());
        List<ReferralRecomEvaluationRecord> evaluationRecords = evaluationDao.fetchEvaluationRecordsByAppids(seekAppids);
        List<Integer> evaluationIds = evaluationRecords.stream().map(ReferralRecomEvaluationRecord::getAppId).distinct().collect(Collectors.toList());

        HrWxWechatDO hrWxWechatDO = wxWechatDao.getHrWxWechatByCompanyId(employeeRecord.getCompanyId());
        List<Integer> sharePids = shareReferralList.stream().map(JobApplicationDO::getPositionId).distinct().collect(Collectors.toList());
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getShareChainsByUserIdAndPresenteeAndPosition(employeeRecord.getSysuserId(), sharePids);
        List<JobApplicationDO> oneDegreeJobApplication = new ArrayList<>();
        // 两度及以上链路对应申请
        JSONObject result = new JSONObject();
        for(JobApplicationDO jobApplicationDO : shareReferralList){
            boolean flag = true;
            for(int i=0;i<applierDegrees.size()&&flag;i++){
                UserDepthVO userDepthVO = applierDegrees.get(i);
                if(userDepthVO.getUserId() == jobApplicationDO.getApplierId() && userDepthVO.getDepth() == 1){
                    oneDegreeJobApplication.add(jobApplicationDO);
                    flag = false;
                }
            }
        }

        Map<Integer, CandidateShareChainDO>  appIdShareChainMap = new HashMap<>(1 >> 5);
        // 候选人转发链路ID
        Set<Integer> shareChainIds = new HashSet<>();
        // 一度转发人user.id
        Set<Integer> oneDegreeUserIds = new HashSet<>();
        for(JobApplicationDO jobApplicationDO : shareReferralList){
            boolean flag = true;
            for(int i=0;i<shareChainDOS.size()&&flag;i++){
                CandidateShareChainDO shareChainDO = shareChainDOS.get(i);
                if(shareChainDO.getPositionId() == jobApplicationDO.getPositionId() && shareChainDO.getPresenteeUserId() == jobApplicationDO.getApplierId()){
                    flag = false;
                    // 找出一度的sharechainId
                    int parentId = shareChainDO.getParentId();
                    CandidateShareChainDO oneDegreeShareChainDO;
                    if(parentId == 0){
                        oneDegreeShareChainDO = shareChainDO;
                    }else {
                        oneDegreeShareChainDO = getShareChainDOByRecurrence(parentId, shareChainDOS);
                    }
                    shareChainIds.add(oneDegreeShareChainDO.getId());
                    appIdShareChainMap.put(jobApplicationDO.getId(), oneDegreeShareChainDO);
                    oneDegreeUserIds.add(oneDegreeShareChainDO.getPresenteeUserId());
                }
            }
        }

        Map<Integer, UserWxUserDO> userWxUserDOMap = new HashMap<>(1 >> 4);
        List<UserWxUserDO> wxUserDOS = wxUserDao.getWXUsersByUserIds(oneDegreeUserIds, hrWxWechatDO.getId());
        wxUserDOS.forEach(userWxUserDO -> userWxUserDOMap.put(userWxUserDO.getSysuserId(), userWxUserDO));
        List<CandidatePositionShareRecordDO> positionShareRecordDOS =
                positionShareRecordDao.fetchPositionShareByShareChainIds(shareChainIds);
        result.put("oneDegree", JSON.toJSONString(oneDegreeJobApplication));
        result.put("appIdShareChainMap", JSON.toJSONString(appIdShareChainMap));
        result.put("shareRecords", JSON.toJSONString(positionShareRecordDOS));
        result.put("evaluate", JSON.toJSONString(evaluationIds));
        result.put("wxUserMap", JSON.toJSONString(userWxUserDOMap));
        return result;
    }

    private CandidateShareChainDO getShareChainDOByRecurrence(int parentId, List<CandidateShareChainDO> shareChainDOS) {
        for(CandidateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getId() == parentId){
                if(shareChainDO.getParentId() == 0){
                    return shareChainDO;
                }else {
                    return getShareChainDOByRecurrence(shareChainDO.getParentId(), shareChainDOS);
                }
            }
        }
        // 理论上不会到这
        return shareChainDOS.get(0);
    }

    @Override
    public void postProcessAfterCreateCard(JSONObject card, JobApplicationDO jobApplicationDO, List<UserDepthVO> applierDegrees) {
        int degree = 0;
        for(UserDepthVO userDepthVO : applierDegrees){
            if(userDepthVO.getUserId() == jobApplicationDO.getApplierId()){
                degree = userDepthVO.getDepth();
                break;
            }
        }
        card.put("degree", degree);
    }

}
