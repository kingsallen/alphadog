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
public class InfoWrongState extends AbstractBindState {
    @Autowired
    EmailNotification emailNotification;

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.DISPATCH_ACCOUNT_INFOWRONG);
    }

    @Override
    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        emailNotification.sendFailureMail(emailNotification.getDevMails(), thirdPartyAccount);
        return super.updateBinding(thirdPartyAccount);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.INFOWRONG;
    }
}
