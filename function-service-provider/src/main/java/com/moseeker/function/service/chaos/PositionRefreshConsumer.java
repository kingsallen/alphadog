package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

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
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        data.setChannel(Byte.valueOf(pojo.getChannel()));
        data.setPositionId(Integer.valueOf(pojo.getPosition_id()));
        if (pojo.getStatus() == 0) {
            data.setIsRefresh((byte) PositionRefreshType.refreshed.getValue());
            data.setRefreshTime(pojo.getSync_time());
        } else {
            data.setIsRefresh((byte) PositionRefreshType.failed.getValue());
            if (pojo.getStatus() == 2) {
                data.setSyncFailReason(Constant.POSITION_SYNCHRONIZATION_FAILED);
            } else {
                data.setSyncFailReason(pojo.getMessage());
            }
        }
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", pojo.getPosition_id());
        JobPositionDO positionDO = positionDao.getData(qu.buildQuery());
        if (positionDO == null || positionDO.getId() < 1) {
            logger.warn("刷新完成队列中包含不存在的职位:{}", pojo.getPosition_id());
            return;
        }
        thirdpartyPositionDao.upsertThirdPartyPosition(data);
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
