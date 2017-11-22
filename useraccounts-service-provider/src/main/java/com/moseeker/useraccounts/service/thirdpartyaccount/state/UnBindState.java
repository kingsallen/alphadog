package com.moseeker.useraccounts.service.thirdpartyaccount.state;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbastractBindState;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.BindState;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 未绑定
 */
@Component
public class UnBindState extends AbastractBindState{

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DISPATCH_ACCOUNT_INVALID);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.UNBIND;
    }
}
