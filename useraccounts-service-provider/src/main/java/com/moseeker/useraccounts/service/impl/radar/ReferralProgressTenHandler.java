package com.moseeker.useraccounts.service.impl.radar;

import com.moseeker.useraccounts.service.constant.ReferralProgressEnum;
import org.springframework.stereotype.Component;

@Component
public class ReferralProgressTenHandler implements IReferralProgressHandler {
    @Override
    public ReferralProgressEnum getReferralProgressEnum() {
        return ReferralProgressEnum.FILTERED;
    }
}
