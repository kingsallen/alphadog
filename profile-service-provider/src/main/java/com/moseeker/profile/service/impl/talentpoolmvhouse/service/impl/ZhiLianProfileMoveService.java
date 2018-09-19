package com.moseeker.profile.service.impl.talentpoolmvhouse.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseVO;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.ProfileMoveOperationInfoVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 智联简历搬家service
 *
 * @author cjm
 * @date 2018-09-06 17:46
 **/
@Service
public class ZhiLianProfileMoveService extends AbstractProfileMoveService {


    public ZhiLianProfileMoveService() {
        firstTime = 3L * 30 * 24 * 60 * 60 * 1000;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }

    /**
     * @param userHrAccountDO       hr账号do
     * @param hrThirdPartyAccountDO hr第三方账号do
     * @param startDate             简历搬家起始时间
     * @param endDate               简历搬家结束时间
     * @return 简历搬家请求vo
     * @author cjm
     * @date 2018/9/9
     */
    @Override
    public MvHouseVO handleRequestParams(UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO, Date startDate, Date endDate,
                                         int profileMoveId, boolean isFirstMove) {
        String password = hrThirdPartyAccountDO.getPassword();
        ProfileMoveOperationInfoVO operationInfoVO = new ProfileMoveOperationInfoVO();
        String startDateStr = "";
        String endDateStr = "";
        if(!isFirstMove){
            // 智联简历搬家已下载简历是从3年前开始，主动投递从3个月前开始，页面显示主动投递的3个月时间即可，第一次搬家不传起止时间，由chaos去默认生成3年和3个月，入库起始时间按照3个月
            startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
        }
        operationInfoVO.setStart_date(startDateStr);
        operationInfoVO.setEnd_date(endDateStr);
        MvHouseVO mvHouseVO = new MvHouseVO();
        mvHouseVO.setAccount_id(userHrAccountDO.getId());
        mvHouseVO.setChannel(hrThirdPartyAccountDO.getChannel());
        mvHouseVO.setMember_name(hrThirdPartyAccountDO.getExt());
        mvHouseVO.setMobile(userHrAccountDO.getMobile());
        mvHouseVO.setOperation_id(profileMoveId);
        mvHouseVO.setUser_name(hrThirdPartyAccountDO.getUsername());
        mvHouseVO.setPassword(password);
        mvHouseVO.setOperation_info(operationInfoVO);
        return mvHouseVO;
    }
}
