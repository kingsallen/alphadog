package com.moseeker.profile.service.impl.talentpoolmvhouse.service;

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

    public AbstractProfileMoveService getSerivce(int channel){
        for(AbstractProfileMoveService profileMoveService : services){
            if (profileMoveService.getChannelType().getValue() == channel) {
                return profileMoveService;
            }
        }
        return null;
    }
}
