package com.moseeker.profile.service.impl.talentpoolmvhouse.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import com.moseeker.profile.service.impl.talentpoolmvhouse.vo.MvHouseVO;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 简历搬家如果没有根据渠道实现自己的方法时的默认处理
 *
 * @author cjm
 * @date 2018-09-06 18:22
 **/
@Service
public class DefaultProfileMoveService extends AbstractProfileMoveService {
    @Override
    public ChannelType getChannelType() {
        return ChannelType.NONE;
    }

    @Override
    public MvHouseVO handleRequestParams(List<ThirdpartyAccountCompanyDO> companyDOS, UserHrAccountDO userHrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccountDO,
                                         Date startDate, Date endDate, int profileMoveId, boolean isFirstMove) throws BIZException {
        throw new BIZException(99999, "该渠道不支持简历搬家");
    }
}
