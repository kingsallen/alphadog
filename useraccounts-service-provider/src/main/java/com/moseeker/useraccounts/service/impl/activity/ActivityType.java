package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.useraccounts.exception.UserAccountException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/11/6
 */
public enum ActivityType {

    EmployeeVerification(0), Referral(1), retransmitClick(2), retransmitApply(3), CVPassed(4);

    private byte value;

    ActivityType(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static ActivityType instanceFromValue(byte value) {
        return storage.get(value);
    }

    private static Map<Byte, ActivityType> storage = new HashMap<>();

    static {
        for (ActivityType activityType : values()) {
            storage.put(activityType.getValue(), activityType);
        }
    }

    public static Activity buildActivity(int id, HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                                         HrHbItemsDao itemsDao, JobPositionDao positionDao) {
        Activity activity = null;
        HrHbConfigRecord record = configDao.fetchById(id);
        if (record == null) {
            throw UserAccountException.ACTIVITY_NOT_EXISTS;
        }
        switch (ActivityType.instanceFromValue(record.getType())) {
            case EmployeeVerification:
                activity = new EmployeeVerificationActivity(id, configDao, positionBindingDao, itemsDao); break;
            case Referral:
                activity = new ReferralActivity(id, configDao, positionBindingDao, itemsDao); break;
            case retransmitClick:
                activity = new RetransmitClickActivity(id, configDao, positionBindingDao, itemsDao, positionDao); break;
            case retransmitApply:
                activity = new RetransmitApplyActivity(id, configDao, positionBindingDao, itemsDao, positionDao); break;
            case CVPassed:
                activity = new CVPassedActivity(id, configDao, positionBindingDao, itemsDao, positionDao); break;
            default:;
        }
        return activity;
    }
}
