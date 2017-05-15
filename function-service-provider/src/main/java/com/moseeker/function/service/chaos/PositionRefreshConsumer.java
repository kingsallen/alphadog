package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 监听刷新完成队列
 *
 * @author wjf
 */
public class PositionRefreshConsumer {

    private static Logger logger = LoggerFactory.getLogger(PositionRefreshConsumer.class);

    UserHrAccountDao.Iface userHrAccountDao = ServiceManager.SERVICEMANAGER.getService(UserHrAccountDao.Iface.class);
    PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER
            .getService(PositionDao.Iface.class);

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

                logger.info(" refresh completed queue :" + sync);

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
        RedisClient redisClient = RedisClientFactory.getCacheClient();
        List<String> result = redisClient.brpop(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH_COMPLETED_QUEUE.toString());
        if (result != null && result.size() > 0) {
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
    private void writeBack(PositionForSyncResultPojo pojo) {
        ThirdPartyPositionData data = new ThirdPartyPositionData();
        data.setChannel(Byte.valueOf(pojo.getChannel()));
        data.setPosition_id(Integer.valueOf(pojo.getPosition_id()));
        if (pojo.getStatus() == 0) {
            data.setIs_refresh((byte) PositionRefreshType.refreshed.getValue());
            data.setRefresh_time(pojo.getSync_time());
        } else {
            data.setIs_refresh((byte) PositionRefreshType.failed.getValue());
            if (pojo.getStatus() == 2) {
                data.setSync_fail_reason(Constant.POSITION_SYNCHRONIZATION_FAILED);
            } else {
                data.setSync_fail_reason(pojo.getMessage());
            }
        }
        try {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", pojo.getPosition_id());
            logger.info("refresh completed queue search position:" + pojo.getPosition_id());
            Position p = positionDao.getPosition(qu);
            if (p != null && p.getId() > 0) {
                logger.info("refresh completed queue position existî");
                logger.info("refresh completed queue update thirdpartyposition to synchronized");
                positionDao.upsertThirdPartyPositions(data);
                if (pojo.getStatus() == 0) {
                    BindAccountStruct bindAccountStruct = new BindAccountStruct();
                    bindAccountStruct.setCompany_id(p.getCompany_id());
                    bindAccountStruct.setRemainNum(pojo.getRemain_number());
                    bindAccountStruct.setRemainProfileNum(pojo.getResume_number());
                    bindAccountStruct.setChannel(Byte.valueOf(pojo.getChannel().trim()));
                    //positionDao.updatePosition(p);
                    logger.info("refresh completed queue update thirdpartyposition to synchronized");
                    userHrAccountDao.updateThirdPartyAccount(pojo.getAccount_id(), bindAccountStruct);
                }
            }
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            //do nothing
        }
    }
}
