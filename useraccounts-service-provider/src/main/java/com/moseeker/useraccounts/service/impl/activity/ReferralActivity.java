package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.ThemeDao;

/**
 * @Author: jack
 * @Date: 2018/11/7
 */
public class ReferralActivity extends NonePositionActivity {
    public ReferralActivity(int id, HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                            HrHbItemsDao itemsDao, ThemeDao themeDao) {
        super(id, configDao, positionBindingDao, itemsDao, themeDao);
        activityType = ActivityType.Referral;
    }
}
