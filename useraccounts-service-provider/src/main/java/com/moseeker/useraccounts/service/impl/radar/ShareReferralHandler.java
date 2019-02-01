package com.moseeker.useraccounts.service.impl.radar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralRecomEvaluation;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.biz.RadarUtils;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionShareRecordDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
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
        if(!radarSwitchOpen){
            return recom;
        }
        // 找出该申请的一度链路
        CandidateShareChainDO shareChainDO = handleShareChainDO(referralTypeSingleMap, jobApplicationDO);
        // 是否已推荐评价
        int evaluate = handleEvaluate(referralTypeSingleMap, jobApplicationDO);
        // 候选人姓名，简历姓名为空时使用微信昵称
        String nickname = handleCandidateName(referralTypeSingleMap, jobApplicationDO, shareChainDO);
        // 找出是否来自微信群
        boolean clickFromWxGroup = handleClickFromWxGroup(referralTypeSingleMap, shareChainDO);
        recom.put("evaluate", evaluate);
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
        List<Integer> applierIds = shareReferralList.stream().map(JobApplicationDO::getApplierId).distinct().collect(Collectors.toList());
        List<Integer> pid = shareReferralList.stream().map(JobApplicationDO::getPositionId).distinct().collect(Collectors.toList());
        List<ReferralRecomEvaluation> evaluationRecords = evaluationDao.fetchEvaluationRecordsByAppids(employeeRecord.getSysuserId(), applierIds, pid);

//        HrWxWechatDO hrWxWechatDO = wxWechatDao.getHrWxWechatByCompanyId(employeeRecord.getCompanyId());
        List<Integer> sharePids = shareReferralList.stream().map(JobApplicationDO::getPositionId).distinct().collect(Collectors.toList());
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getShareChainsByUserIdAndPosition(employeeRecord.getSysuserId(), sharePids);
        List<Integer> oneDegreeJobApplication = new ArrayList<>();
        // 两度及以上链路对应申请
        JSONObject result = new JSONObject();
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
                        oneDegreeJobApplication.add(jobApplicationDO.getId());
                    }else {
                        oneDegreeShareChainDO = RadarUtils.getShareChainDOByRecurrence(parentId, shareChainDOS);
                    }
                    shareChainIds.add(oneDegreeShareChainDO.getId());
                    appIdShareChainMap.put(jobApplicationDO.getId(), oneDegreeShareChainDO);
                    oneDegreeUserIds.add(oneDegreeShareChainDO.getPresenteeUserId());
                }
            }
        }
        List<UserUserDO> userDOS = userUserDao.fetchDOByIdList(new ArrayList<>(oneDegreeUserIds));
        Map<Integer, UserUserDO> userUserMap = new HashMap<>(1 >> 4);
        userDOS.forEach(userUserDO -> userUserMap.put(userUserDO.getId(), userUserDO));
//        wxUserDOS.forEach(userWxUserDO -> userWxUserDOMap.put(userWxUserDO.getSysuserId(), userWxUserDO));
        List<CandidatePositionShareRecordDO> positionShareRecordDOS =
                positionShareRecordDao.fetchPositionShareByShareChainIds(shareChainIds);
        result.put("oneDegree", JSON.toJSONString(oneDegreeJobApplication));
        result.put("appIdShareChainMap", JSON.toJSONString(appIdShareChainMap));
        result.put("shareRecords", JSON.toJSONString(positionShareRecordDOS));
        result.put("evaluateRecords", JSON.toJSONString(evaluationRecords));
        result.put("userUserMap", JSON.toJSONString(userUserMap));
        return result;
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


    private int handleEvaluate(JSONObject referralTypeSingleMap, JobApplicationDO jobApplicationDO) {
        // 转发推荐类型（2）中的用户求推荐信息
        TypeReference<List<ReferralRecomEvaluation>> evaluateType = new TypeReference<List<ReferralRecomEvaluation>>(){};
        List<ReferralRecomEvaluation> evaluateRecords = JSON.parseObject(referralTypeSingleMap.getString("evaluateRecords"), evaluateType);
        for(ReferralRecomEvaluation evaluationRecord : evaluateRecords){
            if(evaluationRecord.getPostUserId() == jobApplicationDO.getRecommenderUserId()
                    && evaluationRecord.getPresenteeUserId() == jobApplicationDO.getApplierId()
                    && evaluationRecord.getPositionId() == jobApplicationDO.getPositionId()){
                return 1;
            }
        }
        return 0;
    }

    private CandidateShareChainDO handleShareChainDO(JSONObject referralTypeSingleMap, JobApplicationDO jobApplicationDO) {
        // 转发推荐类型（2）中的n度申请
        TypeReference<Map<Integer, CandidateShareChainDO>> moreDegree = new TypeReference<Map<Integer, CandidateShareChainDO>>(){};
        Map<Integer, CandidateShareChainDO> appIdShareChainMap = JSON.parseObject(referralTypeSingleMap.getString("appIdShareChainMap"),moreDegree);
        return appIdShareChainMap.get(jobApplicationDO.getId());
    }

    private boolean handleClickFromWxGroup(JSONObject referralTypeSingleMap, CandidateShareChainDO shareChainDO) {
        boolean clickFromWxGroup = false;
        // share_chain记录
        TypeReference<List<CandidatePositionShareRecordDO>> shareRecordTypeRef = new TypeReference<List<CandidatePositionShareRecordDO>>(){};
        List<CandidatePositionShareRecordDO> shareRecordDOS = JSON.parseObject(referralTypeSingleMap.getString("shareRecords"),shareRecordTypeRef);
        if(shareChainDO != null){
            for(CandidatePositionShareRecordDO shareRecordDO : shareRecordDOS){
                if(shareChainDO.getId() == shareRecordDO.getShareChainId()){
                    if(shareRecordDO.getClickFrom() == 2){
                        clickFromWxGroup = true;
                    }
                    break;
                }
            }
        }
        return clickFromWxGroup;
    }

    private String handleCandidateName(JSONObject referralTypeSingleMap, JobApplicationDO jobApplicationDO, CandidateShareChainDO shareChainDO) {
        String nickname = "";
        // 转发推荐类型（2）中的用户微信信息
        TypeReference<Map<Integer, UserUserDO>> wxUserMapType = new TypeReference<Map<Integer, UserUserDO>>(){};
        Map<Integer, UserUserDO> userMap = JSON.parseObject(referralTypeSingleMap.getString("userUserMap"), wxUserMapType);
        // 转发推荐类型（2）中的一度申请
        TypeReference<List<Integer>> applyTypeRef = new TypeReference<List<Integer>>(){};
        List<Integer> oneDegree = JSON.parseObject(referralTypeSingleMap.getString("oneDegree"), applyTypeRef);
        if(!oneDegree.contains(jobApplicationDO.getId()) && shareChainDO != null){
            UserUserDO oneDegreeUser = userMap.get(shareChainDO.getPresenteeUserId());
            nickname = oneDegreeUser.getName();
            if(StringUtils.isNullOrEmpty(nickname)){
                nickname = oneDegreeUser.getNickname();
            }
        }
        return nickname;
    }


}
