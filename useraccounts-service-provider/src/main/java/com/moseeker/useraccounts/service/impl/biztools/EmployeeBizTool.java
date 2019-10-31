package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.EmployeeCardViewData;
import com.moseeker.entity.pojos.EmployeeRadarData;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.useraccounts.constant.ForwardSourceType;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import com.moseeker.useraccounts.service.impl.pojos.EmployeeForwardViewPageVO;
import com.moseeker.useraccounts.service.impl.pojos.RadarUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/27
 */
public class EmployeeBizTool {

    private static Logger logger = LoggerFactory.getLogger(EmployeeBizTool.class);

    public static RadarUserVO packageRadarUser(EmployeeRadarData data, List<UserDepthVO> depthList, Integer userId){
        RadarUserVO radar = new RadarUserVO();
        UserUserRecord userUserRecord = data.getUserRecordList().get(userId);
        if(userUserRecord == null){
            return radar;
        }
        radar.setUserId(userUserRecord.getId());
        UserWxUserRecord wxUserRecord = data.getWxUserRecordList().get(userId);
        if(wxUserRecord != null){
            radar.setNickname(wxUserRecord.getNickname());
            radar.setHeadimgurl(wxUserRecord.getHeadimgurl());
        }
        radar.setSeekRecommend(false);
        if(data.getRecommendUserSet().contains(userId)){
            radar.setSeekRecommend(true);
            radar.setReferralId(data.getRecommendMap().get(userId));
        }
        for(UserDepthVO depth : depthList){
            if(depth.getUserId() == userId) {
                radar.setDepth(depth.getDepth());
                break;
            }
        }
        JobPositionDO positionDO = data.getPositionMap().get(userId);
        if(positionDO != null){
            radar.setPositionId(positionDO.getId());
            radar.setPositionTitle(positionDO.getTitle());
        }
        radar.setViewCount(0);
        if(data.getPositionView().get(userId) != null){
            radar.setViewCount(data.getPositionView().get(userId));
        }
        UserUserRecord root2User = data.getRoot2UserMap().get(userId);
        if(root2User != null){
            radar.setForwardName(root2User.getName());
        }

        if(data.getTimeMap().get(userId) != null) {
            String time = DateUtils.dateToMinuteDate(data.getTimeMap().get(userId));
            radar.setClickTime(time);
        }
        Byte forward = data.getUserFromMap().get(userId);
        radar.setForwardSourceWx(false);
        if(forward != null && forward== ForwardSourceType.Singlemessage.getValue()){
            radar.setForwardSourceWx(true);
        }
        return radar;
    }

    public static EmployeeForwardViewPageVO packageEmployeeForwardViewVO(EmployeeCardViewData data, CandidateRecomRecordRecord record, List<UserDepthVO> depthList){
        EmployeeForwardViewPageVO result = new EmployeeForwardViewPageVO();
        int userId= record.getPresenteeUserId();
        result.setUserId(userId);
        UserWxUserRecord wxUserRecord = data.getWxUserRecordList().get(userId);
        if(wxUserRecord!=null){
            result.setNickname(wxUserRecord.getNickname());
            result.setHeadimgurl(wxUserRecord.getHeadimgurl());
        }
        JobPositionDO position = data.getPositionMap().get(record.getPositionId().intValue());
        if(position!=null){
            result.setPositionId(record.getPositionId().intValue());
            result.setPositionTitle(position.getTitle());
            result.setStatus((int)position.getStatus());
        }
        if(record.getClickTime() != null){
            String time = DateUtils.dateToMinuteDate(record.getClickTime());
            result.setClickTime(time);
        }

        if(!StringUtils.isEmptyList(data.getShareChainList())){
            for(CandidateShareChainDO shareChain : data.getShareChainList()){
                if(shareChain.getPositionId() == record.getPositionId().intValue() && shareChain.getPresenteeUserId() == userId){
                    UserUserRecord root2User = data.getRoot2UserMap().get(shareChain.getRoot2RecomUserId());
                    String name = "";
                    if(root2User != null){
                        if(StringUtils.isNotNullOrEmpty(root2User.getName())){
                            name = root2User.getName();
                        }else {
                            name = root2User.getNickname();
                        }
                    }
                    result.setForwardName(name);
                    result.setForwardSourceWx(false);
                    if(data.getUserFromMap().get(shareChain.getId()) != null
                            && data.getUserFromMap().get(shareChain.getId()) ==ForwardSourceType.Singlemessage.getValue()){
                        result.setForwardSourceWx(true);
                    }
                    result.setInvitationStatus(shareChain.getType()==1?1:0);
                    break;
                }
            }
        }
        if(!StringUtils.isEmptyList(data.getConnectionLogList())){
            for(ReferralConnectionLogRecord logRecord : data.getConnectionLogList()){
                if(logRecord.getEndUserId().intValue() == userId && logRecord.getPositionId().intValue() == record.getPositionId().intValue()){
                    result.setConnection(logRecord.getState());
                    result.setChain(data.getConnectionMap().get(logRecord.getRootChainId()));
                    result.setChainStatus(data.getChainStatus().get(logRecord.getRootChainId()));
                    break;
                }
            }
        }
        result.setDepth(1);
        for(UserDepthVO depth : depthList){
            if(depth.getUserId() == userId) {
                result.setDepth(depth.getDepth());
                break;
            }
        }
        if(!StringUtils.isEmptyList(data.getCandidatePositionRecords())){
            for(CandidatePositionRecord positionRecord : data.getCandidatePositionRecords()){
                if(positionRecord.getUserId().intValue() == userId && positionRecord.getPositionId().intValue() == record.getPositionId().intValue()){
                    result.setViewCount(positionRecord.getViewNumber());
                    break;
                }            }
        }
        return result;

    }

    public static RadarUserVO packageEmployeeSeekRecommendVO(EmployeeCardViewData data, ReferralSeekRecommendRecord record, List<UserDepthVO> depthList){
        RadarUserVO result = new RadarUserVO();
        int userId= record.getPresenteeId();
        result.setUserId(userId);
        result.setReferralId(record.getId());
        UserWxUserRecord wxUserRecord = data.getWxUserRecordList().get(userId);
        if(wxUserRecord!=null){
            result.setHeadimgurl(wxUserRecord.getHeadimgurl());
        }
        UserUserRecord userRecord = data.getUserRecordList().get(userId);
        if(userRecord!=null){
            result.setNickname(userRecord.getNickname());
        }
        JobPositionDO position = data.getPositionMap().get(record.getPositionId().intValue());
        if(position!=null){
            result.setPositionId(record.getPositionId());
            result.setPositionTitle(position.getTitle());
            result.setStatus((int)position.getStatus());
        }
        String time = DateUtils.dateToMinuteDate(record.getRecommendTime());
        result.setClickTime(time);
        if(!StringUtils.isEmptyList(data.getShareChainList())){
            for(CandidateShareChainDO shareChain : data.getShareChainList()){
                if(shareChain.getPositionId() == record.getPositionId().intValue() && shareChain.getPresenteeUserId() == userId){
                    UserUserRecord root2User = data.getRoot2UserMap().get(shareChain.getRoot2RecomUserId());
                    result.setForwardName(root2User!=null?root2User.getName():"");
                    result.setForwardSourceWx(false);
                    if(data.getUserFromMap().get(shareChain.getId()) != null
                            && data.getUserFromMap().get(shareChain.getId()) ==ForwardSourceType.Singlemessage.getValue()){
                        result.setForwardSourceWx(true);
                    }
                    break;
                }
            }
        }
        for(UserDepthVO depth : depthList){
            if(depth.getUserId() == userId) {
                result.setDepth(depth.getDepth());
                break;
            }
        }
        if(!StringUtils.isEmptyList(data.getCandidatePositionRecords())){
            for(CandidatePositionRecord positionRecord : data.getCandidatePositionRecords()){
                if(positionRecord.getUserId().intValue() == userId && positionRecord.getPositionId().intValue() == record.getPositionId().intValue()){
                    result.setViewCount(positionRecord.getViewNumber());
                    break;
                }
            }
        }
        return result;
    }


}
