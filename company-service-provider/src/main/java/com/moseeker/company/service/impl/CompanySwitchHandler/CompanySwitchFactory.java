package com.moseeker.company.service.impl.CompanySwitchHandler;

import com.moseeker.company.constant.OmsSwitchEnum;
import com.moseeker.company.exception.CompanySwitchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanySwitchFactory {


    private final List<AbstractCompanySwitchHandler> services;

    @Autowired
    public CompanySwitchFactory(List<AbstractCompanySwitchHandler> services) {
        this.services = services;
    }

    public AbstractCompanySwitchHandler getService(OmsSwitchEnum type){
        for(AbstractCompanySwitchHandler abstractCompanySwitchHandler :services){
            if(abstractCompanySwitchHandler.switchType().equals(type)){
                return abstractCompanySwitchHandler;
            }
        }
        return null;
    }
}
