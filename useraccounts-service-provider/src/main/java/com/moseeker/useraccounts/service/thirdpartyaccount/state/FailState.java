package com.moseeker.useraccounts.service.thirdpartyaccount.state;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.EmailNotification;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbastractBindState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FailState extends AbastractBindState {
    @Autowired
    EmailNotification emailNotification;

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.BIND_STATUS_WRONG);
    }

    @Override
    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        emailNotification.sendFailureMail(emailNotification.getDevMails(), thirdPartyAccount);
        return super.updateBinding(thirdPartyAccount);
    }

    @Override
    public BindingStatus status() {
        return BindingStatus.FAIL;
    }
}
