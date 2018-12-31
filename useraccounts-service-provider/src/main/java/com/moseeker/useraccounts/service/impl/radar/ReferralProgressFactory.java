package com.moseeker.useraccounts.service.impl.radar;

import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReferralProgressFactory {

    @Autowired
    private List<IReferralProgressHandler> progressHandlers;

    public IReferralProgressHandler getHandlerByTypeAndProgress(int progress) throws BIZException {
        for(IReferralProgressHandler handler : progressHandlers){
            if(handler.getReferralProgressEnum().getProgress() == progress){
                return handler;
            }
        }
        throw new BIZException(99999, "unsupported referral progress");
    }
}
