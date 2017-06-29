package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 监听同步完成队列
 *
 * @author wjf
 */
@Component
public class PositionSyncConsumer extends RedisConsumer<PositionForSyncResultPojo> {

    private static Logger logger = LoggerFactory.getLogger(PositionSyncConsumer.class);

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;


    @PostConstruct
    public void startTask() {
        loopTask(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLETED_QUEUE.toString());
    }

    @Override
    protected PositionForSyncResultPojo convertData(String redisString) {
        return JSON.parseObject(redisString, PositionForSyncResultPojo.class);
    }

    /**
     * 同步信息回写到数据库
     *
     * @param pojo
     */
    @CounterIface
    @Override
    protected void onComplete(PositionForSyncResultPojo pojo) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        data.setChannel(Byte.valueOf(pojo.getChannel()));
        data.setPositionId(Integer.valueOf(pojo.getPosition_id()));
        data.setThirdPartPositionId(pojo.getJob_id());
        data.setThirdPartyAccountId(pojo.getAccount_id());
        if (pojo.getStatus() == 0) {
            data.setIsSynchronization((byte) PositionSync.bound.getValue());
            data.setSyncTime(pojo.getSync_time());
        } else {
            data.setIsSynchronization((byte) PositionSync.failed.getValue());
            if (pojo.getStatus() == 2) {
                data.setSyncFailReason(Constant.POSITION_SYNCHRONIZATION_FAILED);
            } else {
                if (StringUtils.isNotNullOrEmpty(pojo.getPub_place_name())) {
                    data.setSyncFailReason(pojo.getMessage().replace("{}", pojo.getPub_place_name()));
                } else {
                    data.setSyncFailReason(pojo.getMessage());
                }
            }
        }

        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", pojo.getPosition_id());
        JobPositionDO positionDO = positionDao.getData(qu.buildQuery());
        if (positionDO == null || positionDO.getId() < 1) {
            logger.warn("同步完成队列中包含不存在的职位:{}", pojo.getPosition_id());
            return;
        }
        thirdpartyPositionDao.upsertThirdPartyPosition(data);
        if (pojo.getStatus() == 0 && pojo.getResume_number() > -1 && pojo.getRemain_number() > -1) {
            HrThirdPartyAccountDO thirdPartyAccount = new HrThirdPartyAccountDO();
            thirdPartyAccount.setRemainNum(pojo.getRemain_number());
            thirdPartyAccount.setRemainProfileNum(pojo.getResume_number());
            thirdPartyAccount.setChannel(Short.valueOf(pojo.getChannel().trim()));
            thirdPartyAccount.setSyncTime(pojo.getSync_time());
            thirdPartyAccount.setId(pojo.getAccount_id());
            logger.info("同步完成队列中更新第三方帐号信息:{}", JSON.toJSONString(thirdPartyAccount));
            thirdPartyAccountDao.updateData(thirdPartyAccount);
        }
    }
}
