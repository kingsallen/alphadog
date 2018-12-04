package com.moseeker.useraccounts.service.thirdpartyaccount.operation;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ChaosHandler;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ThirdPartyAccountContext;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BindMessageOperation {
    @Autowired
    ThirdPartyAccountContext context;

    @Autowired
    ChaosHandler chaosHandler;

    @Autowired
    BindUtil bindUtil;

    @Autowired
    protected HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    UserHrAccountService userHrAccountService;

    public HrThirdPartyAccountDO bindMessage(int hrId, HrThirdPartyAccountDO thirdPartyAccount, String code) throws Exception {
        UserHrAccountDO userHrAccount = userHrAccountService.requiresNotNullAccount(hrId);

        if (thirdPartyAccount == null) {
            throw new BIZException(-1, "无效的第三方帐号");
        }

        if (bindUtil.getCache(thirdPartyAccount) == null) {
            throw new BIZException(111, "验证码超时了，请重新绑定");
        }

        Map<String, String> extras = bindUtil.getBindExtra(userHrAccount, thirdPartyAccount);
        try {
            thirdPartyAccount = chaosHandler.bindMessage(thirdPartyAccount, extras, code);
            return thirdPartyAccount;
        } catch (Exception e) {
            throw e;
        } finally {
            bindUtil.removeCache(thirdPartyAccount);
        }

    }
}
