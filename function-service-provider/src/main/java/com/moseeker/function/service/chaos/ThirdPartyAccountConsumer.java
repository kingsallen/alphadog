package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 第三方帐号同步刷新监听队列
 *
 * @author wjf
 */
@Component
public class ThirdPartyAccountConsumer {

    private static Logger logger = LoggerFactory.getLogger(ThirdPartyAccountConsumer.class);

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @PostConstruct
    public void startTask() {
        new Thread(() -> {
            while (true) {
                task();
            }
        }).start();
    }

    private void task() {
        try {
            String sync = fetchAccount();
            if (StringUtils.isNotNullOrEmpty(sync)) {
                PositionForSyncResultPojo pojo = JSONObject.parseObject(sync, PositionForSyncResultPojo.class);
                writeBack(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
    }

    /**
     * 读取帐号
     * @return
     */
    private String fetchAccount() {
        List<String> result = redisClient.brpop(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLETED_QUEUE.toString());
        if (result != null && result.size() > 0) {
            logger.info("completed queue :" + result.get(1));
            return result.get(1);
        } else {
            return null;
        }
    }

    /**
     * 回写数据
     *
     * @param pojo
     */
    @CounterIface
    private void writeBack(PositionForSyncResultPojo pojo) {

    }
}
