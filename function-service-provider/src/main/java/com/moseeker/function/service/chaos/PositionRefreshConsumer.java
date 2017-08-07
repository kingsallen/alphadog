package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 监听刷新完成队列
 *
 * @author wjf
 */
@Component
public class PositionRefreshConsumer extends RedisConsumer<PositionForSyncResultPojo> {

    private static Logger logger = LoggerFactory.getLogger(PositionRefreshConsumer.class);

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    PositionSyncFailedNotification syncFailedNotification;


    @PostConstruct
    public void startTask() {
        loopTask(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH_COMPLETED_QUEUE.toString());
    }

    @Override
    protected PositionForSyncResultPojo convertData(String redisString) {
        return JSON.parseObject(redisString, PositionForSyncResultPojo.class);
    }

    /**
     * 刷新信息回写到数据库
     *
     * @param pojo
     */
    @CounterIface
    @Override
    protected void onComplete(PositionForSyncResultPojo pojo) {

        if (pojo == null) return;

        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        data.setChannel(Byte.valueOf(pojo.getChannel()));
        data.setPositionId(Integer.valueOf(pojo.getPosition_id()));
        data.setThirdPartyAccountId(pojo.getAccount_id());
        if (pojo.getStatus() == 0) {
            data.setIsRefresh((byte) PositionRefreshType.refreshed.getValue());
            data.setRefreshTime(pojo.getSync_time());
        } else {
            data.setIsRefresh((byte) PositionRefreshType.failed.getValue());
        }
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", pojo.getPosition_id());
        JobPositionDO moseekerPosition = positionDao.getData(qu.buildQuery());
        if (moseekerPosition == null || moseekerPosition.getId() < 1) {
            logger.warn("刷新完成队列中包含不存在的职位:{}", pojo.getPosition_id());
            return;
        }

        HrThirdPartyPositionDO thirdPartyPositionDO = null;
        try {
            thirdPartyPositionDO = thirdpartyPositionDao.upsertThirdPartyPosition(data);
        } catch (BIZException e) {
            e.printStackTrace();
            logger.error("读取职位刷新队列后无法更新到数据库:{}", JSON.toJSONString(data));
        }

        if (pojo.getStatus() == 2 || pojo.getStatus() == 9) {
            syncFailedNotification.sendRefreshFailedMail(moseekerPosition, thirdPartyPositionDO, pojo);
        }

        if (pojo.getStatus() == 0 && pojo.getRemain_number() > -1 && pojo.getResume_number() > -1) {
            HrThirdPartyAccountDO thirdPartyAccount = new HrThirdPartyAccountDO();
            thirdPartyAccount.setId(pojo.getAccount_id());
            thirdPartyAccount.setRemainNum(pojo.getRemain_number());
            thirdPartyAccount.setRemainProfileNum(pojo.getResume_number());
            thirdPartyAccount.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            thirdPartyAccount.setSyncTime(thirdPartyAccount.getUpdateTime());
            logger.info("刷新完成队列中更新第三方帐号信息:{}", JSON.toJSONString(thirdPartyAccount));
            thirdPartyAccountDao.updateData(thirdPartyAccount);
        }
    }

}
