package com.moseeker.position.service.schedule.delay;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author cjm
 * @date 2018-07-02 9:29
 **/
public abstract class AbstractSyncStateRefresh implements Runnable, IChannelType{

    public int hrThirdPartyPositionId;

    static Random random = new Random();

    @Autowired
    HRThirdPartyPositionDao hrThirdPartyPositionDao;

    @Autowired
    PositionTaskQueueDaemonThread delayQueueThread;

    @Autowired
    PositionEmailNotification emailNotification;

    String errMsg = "审核不通过，请修改职位信息后重新发布。(审核失败原因可能是:1、校招职位在社招渠道发布;2、职位信息中包含网站链接等。)";

    public AbstractSyncStateRefresh(int hrThirdPartyPositionId) {
        this.hrThirdPartyPositionId = hrThirdPartyPositionId;
    }

    /**
     * 单位：毫秒
     */
    public long timeout = 2 * 60 * 60 *1000;

    @Override
    public void run() {
        int isSynchronization = PositionSync.binding.getValue();
        HrThirdPartyPositionDO hrThirdPartyPositionDO = getHrThirdPartyPosition(hrThirdPartyPositionId, (short) isSynchronization);
        if(hrThirdPartyPositionDO == null){
            return;
        }
        // todo 同步失败，设置失败原因
        hrThirdPartyPositionDao.updateErrmsg(errMsg, hrThirdPartyPositionDO.getPositionId(), getChannelType().getValue(), PositionSync.failed.getValue());
    }

    public int getThirdPartyPositionId() {
        return hrThirdPartyPositionId;
    }

    public void setThirdPartyPositionId(int hrThirdPartyPositionId) {
        this.hrThirdPartyPositionId = hrThirdPartyPositionId;
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
