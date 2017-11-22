package com.moseeker.useraccounts.service.thirdpartyaccount.operation;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.*;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindCheck;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BindOperation {
    Logger logger= LoggerFactory.getLogger(BindOperation.class);

    @Autowired
    protected HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    UserHrAccountService userHrAccountService;

    @Autowired
    ThirdPartyAccountContext context;

    @Autowired
    ChaosHandler chaosHandler;

    @Autowired
    BindUtil bindUtil;

    public HrThirdPartyAccountDO bind(int hrId,HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        logger.info("-------bindThirdAccount--------{}:{}", hrId, JSON.toJSONString(thirdPartyAccount));
        // 判断Channel是否合法
        BindCheck.checkChannel(thirdPartyAccount.getChannel());

        UserHrAccountDO hrAccount = userHrAccountService.requiresNotNullAccount(hrId);

        thirdPartyAccount.setCompanyId(hrAccount.getCompanyId());

        Map<String, String> extras = bindUtil.getBindExtra(hrAccount, thirdPartyAccount);

        bindUtil.alreadyInRedis(thirdPartyAccount);  //验证是否正在绑定

        try {
            thirdPartyAccount.setBinding((short)8);
//            thirdPartyAccount.setBinding((short)9);
//            thirdPartyAccount.setBinding((short)6);
            HrThirdPartyAccountDO result = thirdPartyAccount;//chaosHandler.bind(thirdPartyAccount, extras);
            if (result.getBinding() != 100) {
                bindUtil.removeCache(thirdPartyAccount);
            }
            return result;
        } catch (Exception e) {
            bindUtil.removeCache(thirdPartyAccount);
            throw e;
        }
    }

    public boolean isAlreadyBindOtherAccount(int userId,int channel){
        HrThirdPartyAccountDO boundAccount = thirdPartyAccountDao.getThirdPartyAccountByUserId(userId, channel);
        return BindCheck.isNotNullAccount(boundAccount)  /*&& boundAccount.getBinding() != BindingStatus.UNBIND.getValue()*/;
    }

    //将这次绑定记录到数据库,并返回新的账号
    public HrThirdPartyAccountDO useNewThirdPartyAccount(HrThirdPartyAccountDO thirdPartyAccount){
        thirdPartyAccount.setBinding((short) BindingStatus.UNBIND.getValue());
        thirdPartyAccount = thirdPartyAccountDao.addData(thirdPartyAccount);
        logger.info("插入新绑定的账号数据: {}",thirdPartyAccount);
        return thirdPartyAccount;
    }

    public HrThirdPartyAccountDO reuseOldThirdPartyAccount(HrThirdPartyAccountDO thirdPartyAccount,HrThirdPartyAccountDO oldAccount) throws BIZException {
        thirdPartyAccount.setId(oldAccount.getId());
        thirdPartyAccount.setBinding(oldAccount.getBinding());
        return thirdPartyAccount;
    }


}
