package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.pojos.EmployeeCardViewData;
import com.moseeker.entity.pojos.EmployeeRadarData;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.useraccounts.constant.ForwardSourceType;
import com.moseeker.useraccounts.service.impl.pojos.EmployeeForwardViewPageVO;
import com.moseeker.useraccounts.service.impl.pojos.EmployeeForwardViewVO;
import com.moseeker.useraccounts.service.impl.pojos.RadarUserVO;
import com.moseeker.useraccounts.pojo.neo4j.UserDepthVO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        radar.setViewCount(data.getPositionView().get(userId));
        UserUserRecord root2User = data.getRoot2UserMap().get(userId);
        if(root2User != null){
            radar.setForwardName(root2User.getName());
        }
        radar.setClickTime(data.getTimeMap().get(userId));
        Byte forward = data.getUserFromMap().get(userId);
        radar.setForwardSourceWx(false);
        if(forward== ForwardSourceType.Groupmessage.getValue()){
            radar.setForwardSourceWx(true);
        }
        return radar;
    }

    public static EmployeeForwardViewPageVO packageEmployeeForwardViewVO(EmployeeCardViewData data, CandidateRecomRecordDO record){
        EmployeeForwardViewPageVO result = new EmployeeForwardViewPageVO();
        int userId= record.getPresenteeUserId();
        result.setUserId(userId);
        UserWxUserRecord wxUserRecord = data.getWxUserRecordList().get(userId);
        if(wxUserRecord!=null){
            result.setNickname(wxUserRecord.getNickname());
            result.setHeadimgurl(wxUserRecord.getHeadimgurl());
        }
        int positionId = record.getPositionId();
        JobPositionDO position = data.getPositionMap().get(positionId);
        if(position!=null){
            result.setPositionId(positionId);
            result.setPositionTitle(position.getTitle());
        }
        result.setClickTime(record.getClickTime());
        if(!StringUtils.isEmptyList(data.getShareChainList())){
            for(CandidateShareChainDO shareChain : data.getShareChainList()){
                if(shareChain.getPositionId() == positionId && shareChain.getPresenteeUserId() == userId){
                    UserUserRecord root2User = data.getRoot2UserMap().get(shareChain.getRoot2RecomUserId());
                    result.setForwardName(root2User!=null?root2User.getName():"");
                    result.setForwardSourceWx(false);
                    if(data.getUserFromMap().get(shareChain.getId()) != null
                            && data.getUserFromMap().get(shareChain.getId()) ==ForwardSourceType.Groupmessage.getValue()){
                        result.setForwardSourceWx(true);
                    }
                    result.setInvitationStatus(shareChain.getType()==1?1:0);
                    break;
                }
            }
        }
        if(!StringUtils.isEmptyList(data.getConnectionLogList())){
            for(ReferralConnectionLogRecord logRecord : data.getConnectionLogList()){
                if(logRecord.getEndUserId() == userId && logRecord.getPositionId() == positionId){
                    result.setConnection(logRecord.getState());
                    result.setChain(data.getConnectionMap().get(logRecord.getRootChainId()));
                    break;
                }
            }
        }
        if(!StringUtils.isEmptyList(data.getCandidatePositionRecords())){
            for(CandidatePositionRecord positionRecord : data.getCandidatePositionRecords()){
                if(positionRecord.getUserId() == userId && positionRecord.getPositionId() == positionId){
                    result.setViewCount(positionRecord.getViewNumber());
                    break;
                }
            }
        }

        return result;
    }


}
