package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.entity.pojos.EmployeeRadarData;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.useraccounts.constant.ForwardSourceType;
import com.moseeker.useraccounts.service.impl.pojos.RadarUserVO;
import com.moseeker.useraccounts.service.impl.pojos.UserDepthVO;
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
}
