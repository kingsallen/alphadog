package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.ThemeDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.ActivityVO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
public abstract class NonePositionActivity extends Activity {

    public NonePositionActivity(int id,
                                HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                                HrHbItemsDao itemsDao, ThemeDao themeDao) {
        super(id, configDao, positionBindingDao, itemsDao, themeDao);
    }

    /**
     * 开启活动
     * @param activityVO 活动参数
     * @throws UserAccountException
     */
    @Override
    public void start(ActivityVO activityVO) throws UserAccountException {

        super.start(activityVO);
        if (activityStatus.equals(ActivityStatus.Pause)) {
            configDao.updateStatus(id, ActivityStatus.Running.getValue());
            return;
        }

        HrHbConfigRecord hrHbConfig = configDao.fetchById(id);
        if (hrHbConfig == null ) {
            throw UserAccountException.ACTIVITY_NOT_EXISTS;
        }

        //检查红包活动是否已经生成红包记录了，如果有则没有必要再次生成红包记录。
        int count = itemsDao.countItemsByActivity(id);
        if (count == 0) {
            List<HrHbItemsRecord>  items = new ArrayList<>();
            BigDecimal totalAmount = new BigDecimal(0);
            for(int i = 0; i<activityVO.getAmounts().size(); i++){
                HrHbItemsRecord hrHbItemsRecord = amountToRecord(i+1, activityVO.getAmounts().get(i), id, 0);
                items.add(hrHbItemsRecord);
                totalAmount.add(hrHbItemsRecord.getAmount());
            }
            if (totalAmount.doubleValue() > this.totalAmount) {
                throw UserAccountException.ACTIVITY_AMOUNT_ERROR;
            }
            itemsDao.insertIfNotExistForStartActivity(items);
        }

        if (activityVO.getTotalAmount() != null) {
            activityVO.setActualTotal(activityVO.getAmounts().size());
        }
        updateInfo(activityVO, false);
        configDao.updateStatus(id, ActivityStatus.Running.getValue());
        if (activityVO.getTheme() != null) {
            themeDao.upsert(id, activityVO.getTheme());
        }
    }

    @Override
    public void updateInfo(ActivityVO activityVO, boolean checked) {
        if (StringUtils.isNotBlank(activityVO.getStartTime()) || StringUtils.isNotBlank(activityVO.getEndTime())) {
            HrHbConfigRecord hrHbConfig = configDao.fetchById(id);
            if (hrHbConfig == null ) {
                throw UserAccountException.ACTIVITY_NOT_EXISTS;
            }
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(activityVO.getStartTime())) {
                hrHbConfig.setStartTime(new Timestamp(DateTime.parse(activityVO.getStartTime(), format).getMillis()));
            }
            if (StringUtils.isNotBlank(activityVO.getEndTime())) {
                hrHbConfig.setEndTime(new Timestamp(DateTime.parse(activityVO.getEndTime(), format).getMillis()));
            }
            //红包活动的活动时间是否有交集的情况。非职位相关的红包活动不能有时间交集。
            List<HrHbConfigRecord> hrHbConfigRecords
                    = configDao.fetchSameTypeActiveByCompanyIdExceptId(hrHbConfig.getCompanyId(), hrHbConfig.getId(), activityType.getValue());
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
        super.updateInfo(activityVO, checked);
    }

    protected ActivityType activityType;
}
