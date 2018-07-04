package com.moseeker.position.service.schedule.delay;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.liepin.LiepinSocialPositionTransfer;
import com.moseeker.position.service.schedule.constant.LiepinPositionAuditState;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjm
 * @date 2018-07-02 9:19
 **/
public class LiepinSyncStateRefresh extends AbstractSyncStateRefresh{

    private Logger logger = LoggerFactory.getLogger(LiepinSyncStateRefresh.class);

    public long timeout = 1 * 1* 60 *1000;

    @Autowired
    private JobPositionLiepinMappingDao liepinMappingDao;

    @Autowired
    private LiepinSocialPositionTransfer socialPositionTransfer;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    public LiepinSyncStateRefresh(int hrThirdPartyPositionId) {
        super(hrThirdPartyPositionId);
    }

    @Override
    public void run() {
        // 使用第三方职位id刷新职位状态
        short isSynchronization = 2;
        HrThirdPartyPositionDO hrThirdPartyPositionDO = getHrThirdPartyPosition(hrThirdPartyPositionId, isSynchronization);
        if(hrThirdPartyPositionDO == null){
            logger.info("=========================无第三方职位信息hrThirdPartyPositionId:{}", hrThirdPartyPositionId);
            return;
        }
        // 获取第三方账号id
        int thirdAccountId = hrThirdPartyPositionDO.getThirdPartyAccountId();
        // 获取职位job_position.id
        int positionId = hrThirdPartyPositionDO.getPositionId();
        // 使用第三方职位id获取token
        String liePinToken = getLiepinToken(thirdAccountId);
        if(StringUtils.isNullOrEmpty(liePinToken)){
            logger.info("=========================token为空thirdAccountId:{}", thirdAccountId);
            return;
        }
        // 向猎聘发请求的id list
        List<Integer> requestIds = getRequestIds(positionId);
        if(requestIds == null || requestIds.size() == 0){
            logger.info("=========================猎聘请求ids为空, positionId:{}", positionId);
            return;
        }

        // 调接口获取职位信息
        try {
            for(int requestId : requestIds){

                JSONObject positionInfoDetail = socialPositionTransfer.getPositionAuditState(requestId, liePinToken, positionId, getChannelType().getValue());

                if(positionInfoDetail == null){
                    continue;
                }
                String audit = positionInfoDetail.getString("ejob_auditflag");

                // 根据审核状态，如果还是未审核，再把任务加入到队列中，如果已经审核通过，修改数据库同步状态
                if(StringUtils.isNullOrEmpty(audit)){
                    logger.error("==============================请求失败requestIds:{}", requestIds);
                    continue;
                }
                if(LiepinPositionAuditState.PASS.getValue().equals(audit)){
                    // 修改同步状态和mapping表state
                    hrThirdPartyPositionDao.updateBindState(positionId, thirdAccountId, getChannelType().getValue(), PositionSync.bound.getValue());
                    liepinMappingDao.updateState(requestId, (byte)1);
                } else if(LiepinPositionAuditState.NOTPASS.getValue().equals(audit)){
                    // 修改同步状态为3，设置失败原因
                    hrThirdPartyPositionDao.updateErrmsg(errMsg, positionId, getChannelType().getValue(), PositionSync.failed.getValue());
                } else if(LiepinPositionAuditState.WAITCHECK.getValue().equals(audit)){
                    // 再次加入队列中，继续等待审核
                    delayQueueThread.put(timeout + random.nextInt(5 * 60 * 1000), new LiepinSyncStateRefresh(hrThirdPartyPositionId));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            emailNotification.sendRefreshSyncStateFailEmail("requestIds:" + requestIds.toString(), e);
        }


    }

    private List<Integer> getRequestIds(int positionId) {
        List<JobPositionCityDO> jobPositionCityDOS = jobPositionCityDao.getPositionCitysByPid(positionId);
        if(jobPositionCityDOS == null || jobPositionCityDOS.size() == 0){
            logger.info("=================没有查到职位id对应的发布城市positionId:{}===============", positionId);
            return new ArrayList<>();
        }
        List<Integer> cityCodes = jobPositionCityDOS.stream().map(jobPositionCityDO -> jobPositionCityDO.getCode()).collect(Collectors.toList());

        List<JobPositionLiepinMappingDO> jobPositionLiepinMappingDOS = liepinMappingDao.getMappingDataByPidAndCityCodes(positionId, cityCodes);
        if(jobPositionLiepinMappingDOS == null || jobPositionLiepinMappingDOS.size() == 0){
            logger.info("=================没有查到职位id/citycode对应的mapping表信息，positionId:{}, cityCodes:{}===============", positionId, cityCodes);
            return new ArrayList<>();
        }
        // todo 已修改filter=1
        return jobPositionLiepinMappingDOS.stream().filter(jobPositionLiepinMappingDO -> jobPositionLiepinMappingDO.getState() != 1)
                .map(jobPositionLiepinMappingDO -> jobPositionLiepinMappingDO.getId()).collect(Collectors.toList());

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
