package com.moseeker.useraccounts.service.thirdpartyaccount.state;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbastractBindState;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 绑定成功
 */
@Component
public class BoundState extends AbastractBindState {

    @Override
    public HrThirdPartyAccountDO bind(int hrId, HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
    }

    @Override
    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
    }

    @Override
    public HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
    }

    @Override
    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        return super.updateBinding(thirdPartyAccount);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.BOUND;
    }
}
