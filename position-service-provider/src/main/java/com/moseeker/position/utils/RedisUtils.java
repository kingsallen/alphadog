package com.moseeker.position.utils;

import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;

public class RedisUtils {

    public static void lock(RedisClient redisClient, String key1, String key2, int lockSeconds, int retry) {
        if (retry == 0) {
            retry = Integer.MAX_VALUE;
        }
        int failCount = 0;
        while (failCount < retry) {
            if (lock(redisClient, key1, key2, lockSeconds)) {
                return;
            } else {
                failCount++;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RedisLockException("to many try");
    }

    public static boolean lock(RedisClient redisClient, String key1, String key2, int lockSeconds) {
        long check = redisClient.incrIfNotExist(Constant.APPID_ALPHADOG, key1, key2);
        if (check > 1) {
            //同步中
            return true;
        }
        redisClient.expire(Constant.APPID_ALPHADOG, key1, key2, lockSeconds);
        return false;
    }

    /**
     * 删除锁
     */
    public static void unLock(RedisClient redisClient, String key1, String key2) {
        redisClient.del(Constant.APPID_ALPHADOG, key1, key2);
    }
}
