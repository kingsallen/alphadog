package com.moseeker.useraccounts.service.thirdpartyaccount.state;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractBindState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnDispatchState extends AbstractBindState {

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DISPATCH_ACCOUNT_INVALID);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.UNDISPATCH;
    }
}
