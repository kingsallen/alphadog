package com.moseeker.profile.service.impl.talentpoolmvhouse.schedule;

import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDetailDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveRecordDao;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.profile.utils.ProfileMailUtil;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveRecordDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private final TalentPoolProfileMoveRecordDao profileMoveRecordDao;

    private final TalentPoolProfileMoveDetailDao poolProfileMoveDetailDao;

    private final ProfileMailUtil mailUtil;

    @Autowired
    public ProfileMoveShedule(TalentPoolProfileMoveRecordDao profileMoveRecordDao, ProfileMailUtil mailUtil, TalentPoolProfileMoveDetailDao poolProfileMoveDetailDao) {
        this.profileMoveRecordDao = profileMoveRecordDao;
        this.poolProfileMoveDetailDao = poolProfileMoveDetailDao;
        this.mailUtil = mailUtil;
    }

    /**
     * 每两个小时刷新一次
     * {秒数} {分钟} {小时} {日期} {月份} {星期} {年份(可为空)}
     *
     * @author cjm
     * @date 2018/7/9
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void refreshEmailNum() {
        List<Integer> successIdList = new ArrayList<>();
        List<Integer> failedIdList = new ArrayList<>();
        try {
            Date date = new Date();
            long timeout = 2 * 60 * 60 * 1000;
            Timestamp timestamp = new Timestamp(date.getTime() - timeout);
            // 获取距当前时间超过两个小时并且status为正在进行搬家的数据
            List<TalentPoolProfileMoveRecordDO> profileMoveDOS = profileMoveRecordDao.getProfileMoveByStatusAndDate(ProfileMoveStateEnum.MOVING.getValue(), timestamp);
            logger.info("=====================刷新简历搬家状态开始");
            for (TalentPoolProfileMoveRecordDO profileMoveDO : profileMoveDOS) {
                // 当验证码错误时会创建一条总邮件为0，当前邮件为0的数据，这条数据认为搬家成功
                if (profileMoveDO.getCurrentEmailNum() == 0 && profileMoveDO.getTotalEmailNum() == 0) {
                    if(profileMoveDO.getCreateTime().equals(profileMoveDO.getUpdateTime())){
                        failedIdList.add(profileMoveDO.getId());
                    }else {
                        successIdList.add(profileMoveDO.getId());
                    }
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
                profileMoveRecordDao.batchUpdateStatus(successIdList, ProfileMoveStateEnum.SUCCESS.getValue());
            }
            if (failedIdList.size() > 0) {
                // 刷新时将detail表的状态一并刷新
                profileMoveRecordDao.batchUpdateStatus(failedIdList, ProfileMoveStateEnum.FAILED.getValue());
                List<TalentPoolProfileMoveRecordDO> recordDOS = profileMoveRecordDao.getListByMoveIds(failedIdList);
                List<Integer> detailFailIds = recordDOS.stream().map(TalentPoolProfileMoveRecordDO::getId).collect(Collectors.toList());
                poolProfileMoveDetailDao.batchUpdateStatus(detailFailIds, ProfileMoveStateEnum.FAILED.getValue());
            }
        } catch (Exception e) {
            mailUtil.sendMvHouseFailedEmail(e, "定时任务刷新简历搬家状态时发生异常successIdList:" + successIdList.toString() + ",failedIdList:{}" + failedIdList.toString());
            logger.error(e.getMessage(), e);
        }
    }

}
