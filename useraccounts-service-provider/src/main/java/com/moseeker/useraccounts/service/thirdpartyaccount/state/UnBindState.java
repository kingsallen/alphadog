package com.moseeker.useraccounts.service.thirdpartyaccount.state;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractBindState;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 未绑定
 */
@Component
public class UnBindState extends AbstractBindState {

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DISPATCH_ACCOUNT_INVALID);
    }

    @Override
    public int delete(HrThirdPartyAccountDO thirdPartyAccountDO, UserHrAccountDO hrAccount) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DEL_STATUS_ERROR);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.UNBIND;
    }
}
