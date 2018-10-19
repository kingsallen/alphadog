package com.moseeker.profile.service.impl.talentpoolmvhouse.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseVO;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.ProfileMoveOperationInfoVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 51简历搬家service
 *
 * @author cjm
 * @date 2018-09-06 17:50
 **/
@Service
public class Job51ProfileMoveService extends AbstractProfileMoveService {

    public Job51ProfileMoveService() {
        firstTime = 6L * 31 * 24 * 60 * 60 * 1000;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
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
    public MvHouseVO handleRequestParams(List<ThirdpartyAccountCompanyDO> companyDOS, UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO, Date startDate, Date endDate,
                                         int profileMoveId, boolean isFirstMove) {
        String password = hrThirdPartyAccountDO.getPassword();
        ProfileMoveOperationInfoVO operationInfoVO = new ProfileMoveOperationInfoVO();
        operationInfoVO.setStart_date(new SimpleDateFormat("yyyy-MM-dd").format(startDate));
        operationInfoVO.setEnd_date(new SimpleDateFormat("yyyy-MM-dd").format(endDate));
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
