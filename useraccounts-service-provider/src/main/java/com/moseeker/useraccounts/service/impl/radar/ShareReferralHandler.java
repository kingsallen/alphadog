package com.moseeker.useraccounts.service.impl.radar;

import com.moseeker.useraccounts.service.constant.ReferralTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class ShareReferralHandler extends AbstractReferralTypeHandler {
    public ShareReferralHandler(IReferralProgressHandler progressHandler) {
        super(progressHandler);
    }

    @Override
    ReferralTypeEnum getReferralType() {
        return ReferralTypeEnum.SHARE_REFERRAL;
    }
}
