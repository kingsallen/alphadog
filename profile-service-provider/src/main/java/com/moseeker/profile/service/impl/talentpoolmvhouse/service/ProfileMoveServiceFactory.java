package com.moseeker.profile.service.impl.talentpoolmvhouse.service;

import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjm
 * @date 2018-09-06 17:58
 **/
@Service
public class ProfileMoveServiceFactory {

    private final List<AbstractProfileMoveService> services;

    @Autowired
    public ProfileMoveServiceFactory(List<AbstractProfileMoveService> services) {
        this.services = services;
    }

    public AbstractProfileMoveService getSerivce(int channel) throws BIZException {
        for(AbstractProfileMoveService profileMoveService : services){
            if (profileMoveService.getChannelType().getValue() == channel) {
                return profileMoveService;
            }
        }
        throw new BIZException(99999, "该渠道不支持简历搬家");
    }
}
