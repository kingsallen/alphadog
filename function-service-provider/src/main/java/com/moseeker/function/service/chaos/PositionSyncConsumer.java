package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听同步完成队列
 *
 * @author wjf
 */
@Component
public class PositionSyncConsumer {

    private static Logger logger = LoggerFactory.getLogger(PositionSyncConsumer.class);

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
            String sync = fetchCompledPosition();
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
     * 监听职位同步完成队列
     *
     * @return
     */
    private String fetchCompledPosition() {
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
        List<HrThirdPartyPositionDO> datas = new ArrayList<>();
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
        datas.add(data);

        try {
            Query.QueryBuilder qu = new Query.QueryBuilder();
            qu.where("id", pojo.getPosition_id());
            logger.info("completed queue search position:{}", pojo.getPosition_id());
            Position p = positionDao.getData(qu.buildQuery(), Position.class);
            if (p != null && p.getId() > 0) {
                logger.info("completed queue position existî");
                logger.info("completed queue update thirdpartyposition to synchronized");
                thirdpartyPositionDao.upsertThirdPartyPositions(datas);
                if (pojo.getStatus() == 0 && pojo.getResume_number() > -1 && pojo.getRemain_number() > -1) {
                    HrThirdPartyAccountDO thirdPartyAccount = new HrThirdPartyAccountDO();
                    thirdPartyAccount.setRemainNum(pojo.getRemain_number());
                    thirdPartyAccount.setRemainProfileNum(pojo.getResume_number());
                    thirdPartyAccount.setChannel(Short.valueOf(pojo.getChannel().trim()));
                    thirdPartyAccount.setSyncTime(pojo.getSync_time());
                    thirdPartyAccount.setId(pojo.getAccount_id());
                    //positionDao.updatePosition(p);
                    logger.info("completed queue update thirdpartyposition to synchronized");
                    thirdPartyAccountDao.updateData(thirdPartyAccount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
    }
}
