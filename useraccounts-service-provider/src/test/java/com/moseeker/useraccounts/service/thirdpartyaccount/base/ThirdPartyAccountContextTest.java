package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.redis.cache.CacheClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountContextTest {

    @Autowired
    ThirdPartyAccountContext context;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Resource(type = CacheClient.class)
    RedisClient redisClient;

    @Autowired
    BindUtil bindUtil;

    @Test
    public void test() throws Exception {
        int hrId = 82690;
        int accountId = 763;

        HrThirdPartyAccountDO account = thirdPartyAccountDao.getAccountById(accountId);

        account.setPassword("123");
        String cacheKey = bindUtil.getCacheKey(account);
        redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_ACCOUNT_BINDING.toString(), cacheKey,"1");

        context.bind(hrId,account);
        context.bindMessage(hrId,accountId,"123");

    }

}