package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.useraccounts.exception.UserAccountException;

/**
 * 转发点击红包
 * @Author: jack
 * @Date: 2018/11/6
 */
public class RetransmitClickActivity extends PositionActivity {

    private byte triggerWay = 1;

    public RetransmitClickActivity(int id,
                                   HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                                   HrHbItemsDao itemsDao, JobPositionDao positionDao) {
        super(id, configDao, positionBindingDao, itemsDao, positionDao);
        positionHBStatus = PositionHBStatus.RetransmitClick;
    }

    @Override
    public byte getTriggerWay() throws UserAccountException {
        return triggerWay;
    }
}
