package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.ThemeDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.useraccounts.exception.UserAccountException;

/**
 * 转发申请红包
 * @Author: jack
 * @Date: 2018/11/6
 */
public class CVPassedActivity extends PositionActivity {

    private byte triggerWay = 3;

    public CVPassedActivity(int id,
                            HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                            HrHbItemsDao itemsDao, ThemeDao themeDao, JobPositionDao positionDao) {
        super(id, configDao, positionBindingDao, itemsDao, themeDao, positionDao);
        positionHBStatus = PositionHBStatus.CVPassed;
    }

    @Override
    public byte getTriggerWay() throws UserAccountException {
        return triggerWay;
    }
}
