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
public class BindConfirmOperation {
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

    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        HrThirdPartyAccountDO account = thirdPartyAccountDao.getAccountById(accountId);
        UserHrAccountDO hrAccount = userHrAccountService.requiresNotNullAccount(hrId);

        if (bindUtil.getCache(account) == null) {
            throw new BIZException(111, "验证码超时了，请重新绑定");
        }

        Map<String, String> extras = bindUtil.getBindExtra(hrAccount, account);
        extras.put("confirm", String.valueOf(confirm));
        try {
            return chaosHandler.bindConfirm(account, extras, confirm);
        } catch (BIZException e) {
            //验证码超时
            if (e.getCode() == 111) {
                bindUtil.removeCache(account);
                thirdPartyAccountDao.deleteData(account);
            }
            throw e;
        } finally {
            if (!confirm) {
                bindUtil.removeCache(account);
            }
        }
    }
}
