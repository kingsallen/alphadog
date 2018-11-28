package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.IBindRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author cjm
 * @date 2018-11-21 14:13
 **/
@Component
public class Job58UserAccountBindHandler implements IBindRequest {

    @Override
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) {
        hrThirdPartyAccount.setBinding((short) BindingStatus.BOUND.getValue());
        return hrThirdPartyAccount;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }
}
