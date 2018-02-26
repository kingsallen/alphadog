package com.moseeker.useraccounts.service.thirdpartyaccount.util;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class BindUtil {
    Logger logger= LoggerFactory.getLogger(BindUtil.class);

    @Resource(name = "cacheClient")
    protected RedisClient redisClient;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    /**
     * 获取绑定帐号需要的额外参数
     *
     * @param userHrAccount
     * @param account
     * @return
     * @throws Exception
     */
    public Map<String, String> getBindExtra(UserHrAccountDO userHrAccount, HrThirdPartyAccountDO account) throws BIZException {
        Map<String, String> extras = new HashMap<>();
        ChannelType channelType=ChannelType.instaceFromInteger(account.getChannel());
        switch (channelType){
            case ZHILIAN:
                logger.info("zhilian set mobile : {}",userHrAccount.getMobile());
                extras.put("mobile",userHrAccount.getMobile());
                break;
            case JOB51:
                logger.info("job51 set mobile : {}",userHrAccount.getMobile());
                extras.put("mobile",userHrAccount.getMobile());
                break;
            default:
                break;
        }
        logger.info("getBindExtra extras : {}", JSON.toJSONString(extras));
        return extras;
    }

    /**
     * 获取缓存
     *
     * @param bindingAccount
     * @throws BIZException
     */
    public String getCache(HrThirdPartyAccountDO bindingAccount) throws BIZException {
        String cacheKey = getCacheKey(bindingAccount);
        return redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING.toString(), cacheKey);
    }

    /**
     * 绑定完成或失败清除缓存
     *
     * @param bindingAccount
     */
    public void removeCache(HrThirdPartyAccountDO bindingAccount) {
        String cacheKey = getCacheKey(bindingAccount);
        String cache = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING.toString(), cacheKey);
        if (cache != null) {
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING.toString(), cacheKey);
        }
    }

    public String getCacheKey(HrThirdPartyAccountDO account) {
        Objects.requireNonNull(account);
        //以一个公司下的一个渠道只能绑定一个相同帐号的原则构建key
        return account.getCompanyId() + "_" + account.getChannel() + "_" + account.getUsername();
    }

    private void checkDispatch(HrThirdPartyAccountDO thirdPartyAccount, int hrId) {
        HrThirdPartyAccountDO bindingAccount = thirdPartyAccountDao.getThirdPartyAccountByUserId(hrId, thirdPartyAccount.getChannel());
        if (bindingAccount == null) {
            thirdPartyAccountDao.dispatchAccountToHr(thirdPartyAccount, hrId);
        }
    }
}
