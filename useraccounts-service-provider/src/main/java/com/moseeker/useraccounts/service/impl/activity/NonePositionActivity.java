package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.constant.ActivityCheckState;
import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.ActivityVO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 职位无关红包
 * 职位无关红包包括员工认证红包和推荐评价红包
 * @Author: jack
 * @Date: 2018/11/6
 */
public class NonePositionActivity extends Activity {

    public NonePositionActivity(int id,
                                HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                                HrHbItemsDao itemsDao) {
        super(id, configDao, positionBindingDao, itemsDao);
    }

    /**
     * 开启活动
     * @param activityVO 活动参数
     * @throws UserAccountException
     */
    @Override
    public void start(ActivityVO activityVO) throws UserAccountException {

        super.start(activityVO);

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredOneValidate("红包金额", activityVO.getAmounts());
        String result = validateUtil.validate();
        if (StringUtils.isNotBlank(result)) {
            throw UserAccountException.validateFailed(result);
        }

        HrHbConfigRecord hrHbConfig = configDao.fetchById(id);
        if (hrHbConfig == null ) {
            throw UserAccountException.ACTIVITY_NOT_EXISTS;
        }

        List<HrHbConfigRecord> hrHbConfigRecords = configDao.fetchActiveByCompanyIdExceptId(hrHbConfig.getCompanyId(), hrHbConfig.getId());
        if (hrHbConfigRecords != null && hrHbConfigRecords.size() > 0) {
            for (HrHbConfigRecord record : hrHbConfigRecords) {
                if ((hrHbConfig.getStartTime().getTime() < record.getStartTime().getTime()
                        && hrHbConfig.getEndTime().getTime() > record.getStartTime().getTime())
                        || (hrHbConfig.getStartTime().getTime() < record.getEndTime().getTime()
                        && hrHbConfig.getEndTime().getTime() > record.getEndTime().getTime())) {
                    throw UserAccountException.ACTIVITY_CONFLICT;
                }
            }
        }

        int count = itemsDao.countItemsByActivity(id);
        if (count == 0) {
            List<HrHbItemsRecord>  items = new ArrayList<>();
            BigDecimal totalAmount = new BigDecimal(0);
            for(int i = 0; i<activityVO.getAmounts().size(); i++){
                HrHbItemsRecord hrHbItemsRecord = amountToRecord(i+1, activityVO.getAmounts().get(i), id, 0);
                items.add(hrHbItemsRecord);
                totalAmount.add(hrHbItemsRecord.getAmount());
            }
            if (totalAmount.doubleValue() > activityVO.getTotalAmount()) {
                throw UserAccountException.ACTIVITY_AMOUNT_ERROR;
            }
            itemsDao.insertIfNotExistForStartActivity(items);
        }

        updateInfo(activityVO);
        configDao.updateStatus(id, ActivityStatus.Running.getValue());
    }

    @Override
    public void updateInfo(ActivityVO activityVO) {
        if (StringUtils.isNotBlank(activityVO.getStartTime()) || StringUtils.isNotBlank(activityVO.getEndTime())) {
            HrHbConfigRecord hrHbConfig = configDao.fetchById(id);
            if (hrHbConfig == null ) {
                throw UserAccountException.ACTIVITY_NOT_EXISTS;
            }
            if (StringUtils.isNotBlank(activityVO.getStartTime())) {
                hrHbConfig.setStartTime(new Timestamp(DateTime.parse(activityVO.getStartTime()).getMillis()));
            }
            if (StringUtils.isNotBlank(activityVO.getEndTime())) {
                hrHbConfig.setEndTime(new Timestamp(DateTime.parse(activityVO.getEndTime()).getMillis()));
            }
            List<HrHbConfigRecord> hrHbConfigRecords = configDao.fetchActiveByCompanyIdExceptId(hrHbConfig.getCompanyId(), hrHbConfig.getId());
            if (hrHbConfigRecords != null && hrHbConfigRecords.size() > 0) {
                for (HrHbConfigRecord record : hrHbConfigRecords) {
                    if ((hrHbConfig.getStartTime().getTime() < record.getStartTime().getTime()
                            && hrHbConfig.getEndTime().getTime() > record.getStartTime().getTime())
                            || (hrHbConfig.getStartTime().getTime() < record.getEndTime().getTime()
                            && hrHbConfig.getEndTime().getTime() > record.getEndTime().getTime())) {
                        throw UserAccountException.ACTIVITY_CONFLICT;
                    }
                }
            }

        }
        super.updateInfo(activityVO);
    }
}
