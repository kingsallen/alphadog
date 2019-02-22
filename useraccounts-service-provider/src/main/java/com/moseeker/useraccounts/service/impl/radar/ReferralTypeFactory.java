package com.moseeker.useraccounts.service.impl.radar;

import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReferralTypeFactory {

    @Autowired
    private List<AbstractReferralTypeHandler> referralTypeHandlers;

    public AbstractReferralTypeHandler getHandlerByType(int referralType) throws BIZException {
        for(AbstractReferralTypeHandler handler : referralTypeHandlers){
            if(handler.getReferralType().getType() == referralType){
                return handler;
            }
        }
        throw new BIZException(99999, "unsupported referral type");
    }
}
