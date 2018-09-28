package com.moseeker.entity.application;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户投递次数
 * Created by jack on 21/12/2017.
 */
public class UserApplyCount {
    static Logger logger = LoggerFactory.getLogger(UserApplyCount.class);
    private int socialApplyCount;   //社招职位的投递次数
    private int schoolApplyCount;   //校招职位投递的次数
    private boolean init;           //当前这个是new 出来的还是redis反射产生。true是刚创建，false是redis反射产生。

    public int getSocialApplyCount() {
        return socialApplyCount;
    }

    public void setSocialApplyCount(int socialApplyCount) {
        this.socialApplyCount = socialApplyCount;
    }

    public int getSchoolApplyCount() {
        return schoolApplyCount;
    }

    public void setSchoolApplyCount(int schoolApplyCount) {
        this.schoolApplyCount = schoolApplyCount;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    /**
     * 根据redis存储的内容，初始化用户投递次数
     * @param redis redis中存储的内容
     * @return 当前对象
     */
    public static UserApplyCount initFromRedis(String redis) {
        UserApplyCount userApplyCount;
        if (redis != null) {
            logger.info("redis:{}", redis);
            if (redis.startsWith("{")) {
                logger.info("============={");
                userApplyCount = JSONObject.parseObject(redis, UserApplyCount.class);
            } else {
                logger.info("redisInteger");
                userApplyCount = new UserApplyCount();
                userApplyCount.setSocialApplyCount(Integer.parseInt(redis));
            }
        } else {
            userApplyCount = new UserApplyCount();
            userApplyCount.setInit(true);
        }
        logger.info("userApplyCount:{}", userApplyCount);
        return userApplyCount;
    }

    @Override
    public String toString() {
        return "UserApplyCount{" +
                "socialApplyCount=" + socialApplyCount +
                ", schoolApplyCount=" + schoolApplyCount +
                ", init=" + init +
                '}';
    }
}
