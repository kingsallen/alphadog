package com.moseeker.useraccounts.service.thirdpartyaccount.operation;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ChaosHandler;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.IBindRequest;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ThirdPartyAccountContext;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindCheck;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
    List<IBindRequest> bindRequest;

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

        return sendBindRequest(thirdPartyAccount, extras);
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
        thirdPartyAccountDao.updateData(thirdPartyAccount);
        return thirdPartyAccount;
    }

    private HrThirdPartyAccountDO sendBindRequest(HrThirdPartyAccountDO thirdPartyAccount,Map<String, String> extras) throws Exception {
        for(IBindRequest b:bindRequest){
            if(b.getChannelType().getValue() == thirdPartyAccount.getChannel()){
                return b.bind(thirdPartyAccount,extras);
            }
        }
        // 默认把请求发送给爬虫
        return chaosHandler.bind(thirdPartyAccount,extras);
    }
}
