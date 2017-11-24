package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.common.constants.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ThirdPartyAccountContext{
    Logger logger= LoggerFactory.getLogger(ThirdPartyAccountContext.class);

    private ConcurrentHashMap<HrThirdPartyAccountDO,BindState> stateMap=new ConcurrentHashMap<>();

    @Autowired
    private List<BindState> list;

    @Autowired
    UserHrAccountService userHrAccountService;

    @Autowired
    protected HRThirdPartyAccountDao thirdPartyAccountDao;

    public BindState getBindState(int accountId) throws BIZException {
        HrThirdPartyAccountDO thirdPartyAccountDO=thirdPartyAccountDao.getAccountById(accountId);
        return getBindState(thirdPartyAccountDO);
    }


    public BindState getBindState(HrThirdPartyAccountDO thirdPartyAccountDO) throws BIZException {
        if(thirdPartyAccountDO==null || thirdPartyAccountDO.getId()==0){
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "null HrThirdPartyAccountDO!");
        }
        logger.info("try to getBindState {}",thirdPartyAccountDO);
        return getBindStateByBindingState(thirdPartyAccountDO.getBinding());
    }

    private BindState getBindStateByBindingState(int bindstate) throws BIZException {
        if(list==null || list.isEmpty()) {
            logger.info("no BindState!");
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "no BindState!");
        }

        Optional<BindState> bindState=list.stream().filter(b->b.status().getValue()==bindstate).findFirst();
        if(!bindState.isPresent()) {
            logger.info("no matched BindState {}!",bindstate);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "no matched BindState!");
        }
        return bindState.get();
    }

    public HrThirdPartyAccountDO bind(int hrId,HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        return getBindState(thirdPartyAccount).bind(hrId,thirdPartyAccount);
    }

    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        return getBindState(accountId).bindConfirm(hrId, accountId, confirm);
    }

    public HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception {
        return getBindState(accountId).bindMessage(hrId, accountId, code);
    }

    public HrThirdPartyAccountDO bindChaosHandle(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        return null;
    }

    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        return getBindState(accountId).dispatch(accountId, hrIds);
    }

    public int updateBinding(HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        if(thirdPartyAccount==null || thirdPartyAccount.getId()==0){
            throw new IllegalArgumentException("cannot update null thirdPartyAccount");
        }
        return getBindStateByBindingState(thirdPartyAccount.getBinding()).updateBinding(thirdPartyAccount);
    }

}
