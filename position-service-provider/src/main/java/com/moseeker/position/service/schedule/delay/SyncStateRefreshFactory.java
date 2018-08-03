package com.moseeker.position.service.schedule.delay;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.position.service.schedule.delay.refresh.AbstractSyncStateRefresh;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjm
 * @date 2018-07-04 17:32
 **/
@Service
public class SyncStateRefreshFactory {

    @Autowired
    private List<AbstractSyncStateRefresh> syncStateRefreshList;

    public void refresh(PositionSyncStateRefreshBean refreshBean) throws BIZException {
        AbstractSyncStateRefresh syncStateRefresh = transferFactory(refreshBean.getChannel());
        syncStateRefresh.refresh(refreshBean);
    }

    /**
     * 根据渠道转换成不同的刷新任务实例
     * @param channel 职位同步渠道
     * @return 返回职位状态刷新实例
     */
    private AbstractSyncStateRefresh transferFactory(int channel) throws BIZException {
        for(AbstractSyncStateRefresh syncStateRefresh : syncStateRefreshList){
            if(syncStateRefresh.getChannelType().getValue() == channel){
                return syncStateRefresh;
            }
        }
        throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"no matched ISyncStateRefresh");
    }
}
