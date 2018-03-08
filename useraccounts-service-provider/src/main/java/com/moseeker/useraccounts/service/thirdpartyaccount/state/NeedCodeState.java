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

@Component
public class NeedCodeState extends AbstractBindState {

    @Override
    public HrThirdPartyAccountDO bind(int hrId, HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.BIND_STATUS_WRONG);
    }

    @Override
    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.BIND_STATUS_WRONG);
    }

    @Override
    public HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.BIND_STATUS_WRONG);
    }

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.BIND_STATUS_WRONG);
    }

    @Override
    public int delete(HrThirdPartyAccountDO thirdPartyAccountDO, UserHrAccountDO hrAccount) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DEL_STATUS_ERROR);
    }

    @Override
    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        throw new UnsupportedOperationException("NeedCodeState can not update into the database!");
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.NEEDCODE;
    }
}
