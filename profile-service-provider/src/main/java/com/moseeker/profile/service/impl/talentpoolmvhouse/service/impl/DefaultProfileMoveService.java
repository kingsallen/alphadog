package com.moseeker.profile.service.impl.talentpoolmvhouse.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.talentpoolmvhouse.service.AbstractProfileMoveService;
import org.springframework.stereotype.Service;

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
}
