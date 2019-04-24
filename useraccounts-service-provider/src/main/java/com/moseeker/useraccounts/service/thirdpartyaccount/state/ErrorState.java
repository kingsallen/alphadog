package com.moseeker.useraccounts.service.thirdpartyaccount.state;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractBindState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ErrorState extends AbstractBindState {
    @Autowired
    EmailNotification emailNotification;

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
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DISPATCH_ACCOUNT_ERROR);
    }

    @Override
    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        emailNotification.sendWebBindFailureMail(emailNotification.getMails(), thirdPartyAccount);
        return super.updateBinding(thirdPartyAccount);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.ERROR;
    }
}
