package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.redis.cache.CacheClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
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

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractBindState implements BindState {
    Logger logger = LoggerFactory.getLogger(AbstractBindState.class);

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

    @Resource(type = CacheClient.class)
    RedisClient redisClient;

    @Override
    public HrThirdPartyAccountDO bind(int hrId, HrThirdPartyAccountDO thirdPartyAccount) throws Exception {
        HrThirdPartyAccountDO result = bindOperation.bind(hrId, thirdPartyAccount);


        if (result.getBinding() != BindingStatus.NEEDCODE.getValue()) {
            result.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            result.setSyncTime(result.getUpdateTime());
            context.updateBinding(result);


            HrThirdPartyAccountDO bindingAccount = thirdPartyAccountDao.getThirdPartyAccountByUserId(hrId, thirdPartyAccount.getChannel());
            if (bindingAccount == null) {
                try {
                    context.getBindState(result.getId()).dispatch(result.getId(), Arrays.asList(hrId));
                } catch (BIZException e) {
                    logger.info("catch BIZException when dispatch after bind finished. exception {}", e);
                    return result;
                }
            }
        } else {
            redisClient.set(AppId.APPID_ALPHADOG.getValue()
                    , KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING_MESSAGE_UNIQUE.toString()
                    , String.valueOf(hrId)
                    , String.valueOf(thirdPartyAccount.getId())
                    , JSON.toJSONString(thirdPartyAccount));
        }

        return result;
    }

    @Override
    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        return bindConfirmOperation.bindConfirm(hrId, accountId, confirm);
    }

    @Override
    public HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception {
        String accountJson = redisClient.get(AppId.APPID_ALPHADOG.getValue()
                , KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING_MESSAGE_UNIQUE.toString()
                , String.valueOf(hrId)
                , String.valueOf(accountId));
        if(StringUtils.isNullOrEmpty(accountJson)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_BINDING_TIMEOUT);
        }
        HrThirdPartyAccountDO thirdPartyAccount = JSON.parseObject(accountJson,HrThirdPartyAccountDO.class);

        HrThirdPartyAccountDO result = bindMessageOperation.bindMessage(hrId, thirdPartyAccount, code);
        result.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        result.setSyncTime(result.getUpdateTime());
        context.updateBinding(result);
        try {
            context.getBindState(result.getId()).dispatch(result.getId(), Arrays.asList(hrId));
        } catch (BIZException e) {
            logger.info("catch BIZException when dispatch after bindMessage finished. exception {}", e);
            return result;
        } finally {
            redisClient.del(AppId.APPID_ALPHADOG.getValue()
                    , KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING_MESSAGE_UNIQUE.toString()
                    , String.valueOf(hrId)
                    , String.valueOf(accountId));
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
        return deleteOperation.delete(thirdPartyAccountDO, hrAccount);
    }
}
