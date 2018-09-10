package com.moseeker.profile.service.impl.talentpoolmvhouse.schedule;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.profile.utils.ProfileMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 简历搬家刷新搬家状态刷新定时任务
 *
 * @author cjm
 * @date 2018-07-19 16:09
 **/
@Component
@EnableScheduling
public class ProfileMoveShedule {

    private Logger logger = LoggerFactory.getLogger(ProfileMoveShedule.class);

    private final TalentPoolProfileMoveDao profileMoveDao;

    private final ProfileMailUtil mailUtil;

    private final long timeOut = 5 * 60 * 1000;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    public ProfileMoveShedule(TalentPoolProfileMoveDao profileMoveDao, ProfileMailUtil mailUtil) {
        this.profileMoveDao = profileMoveDao;
        this.mailUtil = mailUtil;
    }

    /**
     * 每两个小时刷新一次
     * {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
     *
     * @author cjm
     * @date 2018/7/9
     */
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void refreshEmailNum() {
        try {
            Date date = new Date();
            long timeout = 2 * 60 * 60 * 1000;
            Timestamp timestamp = new Timestamp(date.getTime() - timeout);
            // 获取距当前时间超过两个小时并且status为正在进行搬家的数据
            List<TalentPoolProfileMoveDO> profileMoveDOS = profileMoveDao.getProfileMoveByStatusAndDate(ProfileMoveStateEnum.MOVING.getValue(), timestamp);
            logger.info("=====================刷新简历搬家状态开始");
            List<Integer> successIdList = new ArrayList<>();
            List<Integer> failedIdList = new ArrayList<>();
            for (TalentPoolProfileMoveDO profileMoveDO : profileMoveDOS) {
                // 当验证码错误时会创建一条总邮件为0，当前邮件为0的数据，这条数据认为搬家失败
                if (profileMoveDO.getCurrentEmailNum() == 0 && profileMoveDO.getTotalEmailNum() == 0) {
                    failedIdList.add(profileMoveDO.getId());
                } else {
                    // 当前email数等于总email数更新为简历搬家操作成功，否则认为失败
                    if (profileMoveDO.getTotalEmailNum() == profileMoveDO.getCurrentEmailNum()) {
                        successIdList.add(profileMoveDO.getId());
                    } else {
                        failedIdList.add(profileMoveDO.getId());
                    }
                }
            }
            if (successIdList.size() > 0) {
                logger.info("========================简历搬家状态刷新successIdList:{}", successIdList);
                profileMoveDao.batchUpdateStatus(successIdList, ProfileMoveStateEnum.SUCCESS.getValue());
            }
            if (failedIdList.size() > 0) {
                logger.info("========================简历搬家状态刷新failedIdList:{}", failedIdList);
                profileMoveDao.batchUpdateStatus(failedIdList, ProfileMoveStateEnum.FAILED.getValue());
            }
        } catch (Exception e) {
            mailUtil.sendMvHouseFailedEmail(e, "定时任务刷新简历搬家状态时发生异常");
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * [{
     *     time:""
     * },
     * {
     *     time:""
     * }
     * ]
     */
//    @Scheduled(cron = "0 0/2 * * * ?")
    public void refreshMoveHouseState() {
        // todo 从redis中取出time，opt_id，如果比当前时间小5分钟以上，更改简历搬家状态，删除reidis中对应的缓存
        String redisKey = "profile_move";
        String value = redisClient.get(0, redisKey, null);
        JSONObject jsonObject = JSONObject.parseObject(value);
        String time = "";
        long currentTime = System.currentTimeMillis();
        long cacheTime = 0;
        List<Integer> ids = new ArrayList<>();
        try {
            cacheTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(currentTime - cacheTime > timeOut){
            // todo 将操作id加入list
            redisClient.del(0, redisKey, null);
        }
        // 批量更新
        try {
            profileMoveDao.batchUpdateStatus(ids, ProfileMoveStateEnum.FAILED.getValue());
        } catch (BIZException e) {
            e.printStackTrace();
        }

    }
}
