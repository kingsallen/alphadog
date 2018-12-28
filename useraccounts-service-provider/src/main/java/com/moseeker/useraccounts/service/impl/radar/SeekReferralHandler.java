package com.moseeker.useraccounts.service.impl.radar;

import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class SeekReferralHandler extends AbstractReferralTypeHandler {
    public SeekReferralHandler(IReferralProgressHandler progressHandler) {
        super(progressHandler);
    }

    @Override
    ReferralTypeEnum getReferralType() {
        return ReferralTypeEnum.SEEK_REFERRAL;
    }
}
