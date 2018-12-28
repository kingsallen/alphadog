package com.moseeker.useraccounts.service.impl.radar;

import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class DirectReferralHandler extends AbstractReferralTypeHandler{
    public DirectReferralHandler(IReferralProgressHandler progressHandler) {
        super(progressHandler);
    }

    @Override
    ReferralTypeEnum getReferralType() {
        return ReferralTypeEnum.DIRECT_REFERRAL;
    }
}
