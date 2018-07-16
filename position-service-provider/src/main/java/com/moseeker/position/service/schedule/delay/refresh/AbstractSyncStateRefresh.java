package com.moseeker.position.service.schedule.delay.refresh;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.position.service.schedule.delay.PositionTaskQueueDaemonThread;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author cjm
 * @date 2018-07-02 9:29
 **/
public abstract class AbstractSyncStateRefresh implements IChannelType{

    Logger logger = LoggerFactory.getLogger(AbstractSyncStateRefresh.class);

    static Random random = new Random();

    @Autowired
    HRThirdPartyPositionDao hrThirdPartyPositionDao;

    @Autowired
    PositionTaskQueueDaemonThread delayQueueThread;

    @Autowired
    PositionEmailNotification emailNotification;

    protected static final String ERRMSG = "审核未通过";

    /**
     * 单位：毫秒
     */
    public static final long TIMEOUT = 1 * 5 * 60 * 1000;

    public void refresh(PositionSyncStateRefreshBean refreshBean) {
        try{
            int hrThirdPartyPositionId = refreshBean.getHrThirdPartyPositionId();
            int isSynchronization = PositionSync.binding.getValue();
            HrThirdPartyPositionDO hrThirdPartyPositionDO = getHrThirdPartyPosition(hrThirdPartyPositionId, (short) isSynchronization);
            if(hrThirdPartyPositionDO == null){
                return;
            }
            // todo 同步失败，设置失败原因
            hrThirdPartyPositionDao.updateErrmsg(ERRMSG, hrThirdPartyPositionDO.getPositionId(), getChannelType().getValue(), PositionSync.failed.getValue());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            emailNotification.sendRefreshSyncStateFailEmail("default channel refresh sync state error", e);
        }

    }

    protected HrThirdPartyPositionDO getHrThirdPartyPosition(int hrThirdPartyPositionId, short isSynchronization){
        Query query = new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID.getName(), hrThirdPartyPositionId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.getName(), getChannelType().getValue())
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), isSynchronization)
                .buildQuery();
        return hrThirdPartyPositionDao.getSimpleData(query);
    }
}
