package com.moseeker.profile.service.impl.talentpoolmvhouse.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import org.springframework.stereotype.Service;

/**
 * 51简历搬家service
 *
 * @author cjm
 * @date 2018-09-06 17:50
 **/
@Service
public class Job51ProfileMoveService extends AbstractProfileMoveService {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
