package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.operation.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public abstract class AbstractBindState implements BindState{
    Logger logger= LoggerFactory.getLogger(AbstractBindState.class);

    @Autowired
    ThirdPartyAccountContext context;

    @Autowired
    BindOperation bindOperation;
    @Autowired
    BindMessageOperation bindMessageOperation;
    @Autowired
    BindConfirmOperation bindConfirmOperation;
    @Autowired
    DispatchOperation dispatchOperation;
    @Autowired
    DeleteOperation deleteOperation;

    @Autowired
    protected HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    UserHrAccountService userHrAccountService;

    @Autowired
    HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Override
    public HrThirdPartyAccountDO bind(int hrId, HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        HrThirdPartyAccountDO result=bindOperation.bind(hrId, thirdPartyAccount);


        if(result.getBinding()!= BindingStatus.NEEDCODE.getValue()){
            result.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            result.setSyncTime(result.getUpdateTime());
            context.updateBinding(result);


            HrThirdPartyAccountDO bindingAccount = thirdPartyAccountDao.getThirdPartyAccountByUserId(hrId, thirdPartyAccount.getChannel());
            if (bindingAccount == null) {
                try {
                    context.getBindState(result.getId()).dispatch(result.getId(), Arrays.asList(hrId));
                }catch (BIZException e){
                    logger.info("catch BIZException when dispatch after bind finished. exception {}",e);
                    return result;
                }
            }
        }

        return result;
    }

    @Override
    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        return bindConfirmOperation.bindConfirm(hrId, accountId, confirm);
    }

    @Override
    public HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception {
        HrThirdPartyAccountDO result=bindMessageOperation.bindMessage(hrId, accountId, code);
        context.updateBinding(result);
        try {
            context.getBindState(result.getId()).dispatch(result.getId(), Arrays.asList(hrId));
        }catch (BIZException e){
            logger.info("catch BIZException when dispatch after bindMessage finished. exception {}",e);
            return result;
        }
        return result;
    }

    @Override
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        return dispatchOperation.dispatch(accountId, hrIds);
    }

    @Override
    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        return hrThirdPartyAccountDao.updateData(thirdPartyAccount);
    }

    @Override
    public int delete(HrThirdPartyAccountDO thirdPartyAccountDO, UserHrAccountDO hrAccount) throws Exception {
        return deleteOperation.delete(thirdPartyAccountDO,hrAccount);
    }
}
