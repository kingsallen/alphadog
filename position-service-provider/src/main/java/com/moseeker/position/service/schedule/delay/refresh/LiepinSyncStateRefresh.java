package com.moseeker.position.service.schedule.delay.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.liepin.LiepinSocialPositionTransfer;
import com.moseeker.position.service.schedule.constant.LiepinPositionAuditState;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-07-02 9:19
 **/
@Component
public class LiepinSyncStateRefresh extends AbstractSyncStateRefresh {

    private Logger logger = LoggerFactory.getLogger(LiepinSyncStateRefresh.class);

    /**
     * 每次任务执行的剩余时间
     */
    public static final long TIMEOUT = 60 * 60 * 1000;

    /**
     * 如果本次刷新距离同步时间已经超过24小时，认定为同步失败
     */
    public static final long EXPIRED_TIME = 24 * 60 * 60 * 1000;

    @Autowired
    private JobPositionLiepinMappingDao liepinMappingDao;

    @Autowired
    private LiepinSocialPositionTransfer socialPositionTransfer;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Override
    public void refresh(PositionSyncStateRefreshBean refreshBean) {

        logger.info("========================职位状态刷新开始refreshBean:{}", refreshBean);
        // 使用第三方职位id刷新职位状态
        short isSynchronization = 2;
        int hrThirdPartyPositionId = refreshBean.getHrThirdPartyPositionId();
        HrThirdPartyPositionDO hrThirdPartyPositionDO = getHrThirdPartyPosition(hrThirdPartyPositionId, isSynchronization);
        logger.info("=========================hrThirdPartyPositionDO{}", hrThirdPartyPositionDO);
        if (hrThirdPartyPositionDO == null) {
            logger.info("=========================无第三方职位信息hrThirdPartyPositionId:{}", hrThirdPartyPositionId);
            return;
        }
        // 获取第三方账号id
        int thirdAccountId = hrThirdPartyPositionDO.getThirdPartyAccountId();
        // 获取职位job_position.id
        int positionId = hrThirdPartyPositionDO.getPositionId();
        // 获取job_position
        JobPositionDO jobPositionDO = jobPositionDao.getJobPositionByPid(positionId);

        if(jobPositionDO == null){
            hrThirdPartyPositionDao.updateBindState(positionId, thirdAccountId, getChannelType().getValue(), 0);
            return;
        }
        double candidateSource = jobPositionDO.getCandidateSource();

        if(candidateSource == 1){
            // 不处理校招职位
            return;
        }
        // 向猎聘发请求的id list，只获取状态为2(审核中)的职位
        List<Integer> requestIds = getRequestIds(positionId);
        if (requestIds == null || requestIds.size() == 0) {
            logger.info("=========================没有需要审核的mappingid, positionId:{}", positionId);
            return;
        }

        try {
            // true表示已经过期
            boolean isExpired = confirmNoExpired(hrThirdPartyPositionDO);
            if(isExpired){
                logger.info("====================审核超过24小时，同步失败hrThirdPartyPositionDO:{}", hrThirdPartyPositionDO);
                hrThirdPartyPositionDao.updateErrmsg(ERRMSG, positionId, getChannelType().getValue(), PositionSync.failed.getValue());
                liepinMappingDao.updateState(requestIds, (byte) 1);
                return;
            }

            // 使用第三方职位id获取token
            String liePinToken = getLiepinToken(thirdAccountId);
            logger.info("=====================thirdAccountId:{},positionId:{},liePinToken:{}", thirdAccountId, positionId, liePinToken);
            if (StringUtils.isNullOrEmpty(liePinToken)) {
                logger.info("=========================token为空thirdAccountId:{}", thirdAccountId);
                return;
            }
            // 如果请求ids中还是存在审核中的职位的话，再次将thirdpartypositionId放到延迟任务中去
            boolean isNeedRefresh = false;
            for (int requestId : requestIds) {

                JSONObject positionInfoDetail = socialPositionTransfer.getPositionAuditState(requestId, liePinToken);
                logger.info("======================positionInfoDetail:{}", positionInfoDetail);
                if (positionInfoDetail == null) {
                    continue;
                }
                String audit = positionInfoDetail.getString("ejob_auditflag");

                // 根据审核状态，如果还是未审核，再把任务加入到队列中，如果已经审核通过，修改数据库同步状态
                if (StringUtils.isNullOrEmpty(audit)) {
                    logger.error("==============================请求失败requestIds:{}", requestIds);
                    continue;
                }
                if (LiepinPositionAuditState.PASS.getValue().equals(audit)) {
                    // 修改同步状态和mapping表state
                    logger.info("======================审核通过hrThirdPartyPositionDO:{}", hrThirdPartyPositionDO);
                    hrThirdPartyPositionDao.updateBindState(positionId, thirdAccountId, getChannelType().getValue(), PositionSync.bound.getValue());
                    liepinMappingDao.updateState(requestId, (byte) 1);
                } else if (LiepinPositionAuditState.NOTPASS.getValue().equals(audit)) {
                    // 修改同步状态为3，设置失败原因
                    logger.info("======================审核不通过hrThirdPartyPositionDO:{}", hrThirdPartyPositionDO);
                    hrThirdPartyPositionDao.updateErrmsg(ERRMSG, positionId, getChannelType().getValue(), PositionSync.failed.getValue());
                    liepinMappingDao.updateState(requestId, (byte) 1);
                } else if (LiepinPositionAuditState.WAITCHECK.getValue().equals(audit)) {
                    // 再次加入队列中，继续等待审核
                    logger.info("======================等待审核hrThirdPartyPositionDO:{}", hrThirdPartyPositionDO);
                    isNeedRefresh = true;
                }
            }
            if(isNeedRefresh){
                // 过期时间加上一个随机数，减少大量职位在同一时间内操作时的服务器压力
                delayQueueThread.put(TIMEOUT + random.nextInt(60 * 1000), refreshBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            emailNotification.sendRefreshSyncStateFailEmail("requestIds:" + requestIds.toString(), e);
        }


    }

    private boolean confirmNoExpired(HrThirdPartyPositionDO hrThirdPartyPositionDO) throws ParseException {
        String syncTime = hrThirdPartyPositionDO.getSyncTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = sdf.parse(syncTime).getTime();
        long currentTime = System.currentTimeMillis();
        return currentTime - time >= EXPIRED_TIME;
    }

    /**
     * 获取jobpositionMapping表中state为2的主键id
     * @param positionId 职位id
     * @author  cjm
     * @date  2018/7/5
     * @return 返回用于请求猎聘的id集合，也就是job_position_mapping的主键
     */
    private List<Integer> getRequestIds(int positionId) {
        List<JobPositionCityDO> jobPositionCityDOS = jobPositionCityDao.getPositionCitysByPid(positionId);
        if (jobPositionCityDOS == null || jobPositionCityDOS.size() == 0) {
            logger.info("=================没有查到职位id对应的发布城市positionId:{}===============", positionId);
            return new ArrayList<>();
        }
        List<Integer> cityCodes = jobPositionCityDOS.stream().map(JobPositionCityDO::getCode).collect(Collectors.toList());

        List<JobPositionLiepinMappingDO> jobPositionLiepinMappingDOS = liepinMappingDao.getMappingDataByPidAndCityCodes(positionId, cityCodes);
        if (jobPositionLiepinMappingDOS == null || jobPositionLiepinMappingDOS.size() == 0) {
            logger.info("=================没有查到职位id/citycode对应的mapping表信息，positionId:{}, cityCodes:{}===============", positionId, cityCodes);
            return new ArrayList<>();
        }
        return jobPositionLiepinMappingDOS.stream().filter(jobPositionLiepinMappingDO -> jobPositionLiepinMappingDO.getState() == 2)
                .map(JobPositionLiepinMappingDO::getId).collect(Collectors.toList());

    }

    private String getLiepinToken(int thirdAccountId) {

        HrThirdPartyAccountDO thirdPartyAccountDO = hrThirdPartyAccountDao.getAccountById(thirdAccountId);

        if (thirdPartyAccountDO == null) {
            logger.info("=================无第三方hr账号数据thirdAccountId:{}===============", thirdAccountId);
            return "";
        }

        return thirdPartyAccountDO.getExt2();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
