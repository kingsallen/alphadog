package com.moseeker.profile.service.impl.talentpoolmvhouse.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import org.springframework.stereotype.Service;

/**
 * 智联简历搬家service
 *
 * @author cjm
 * @date 2018-09-06 17:46
 **/
@Service
public class ZhiLianProfileMoveService extends AbstractProfileMoveService {
    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
