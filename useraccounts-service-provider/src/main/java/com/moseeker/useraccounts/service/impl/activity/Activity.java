package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.constant.ActivityCheckState;
import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.ThemeDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.ActivityVO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 红包活动
 * @Author: jack
 * @Date: 2018/11/6
 */
public abstract class Activity {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Activity(int id, HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao, HrHbItemsDao itemsDao,
                    ThemeDao themeDao)
            throws UserAccountException {
        this.id = id;

        this.configDao = configDao;
        this.positionBindingDao = positionBindingDao;
        this.itemsDao = itemsDao;
        this.themeDao = themeDao;

        HrHbConfigRecord record = configDao.fetchById(this.id);
        if (record == null) {
            throw UserAccountException.ACTIVITY_NOT_EXISTS;
        }
        this.totalAmount = record.getTotalAmount();
        this.activityStatus = ActivityStatus.instanceFromValue(record.getStatus());
        this.activityCheckState = ActivityCheckState.instanceFromValue(record.getChecked());

        logger.info("Activity Activity id:{}, activityStatus:{}, activityCheckState:{}", id, activityStatus, activityCheckState);
        if (this.activityStatus == null || this.activityCheckState == null) {
            throw UserAccountException.ACTIVITY_STATUS_ERROR;
        }
    }

    /**
     * 开始红包活动
     * @param activityVO
     * @throws UserAccountException
     */
    public void start(ActivityVO activityVO) throws UserAccountException {
        if (activityStatus.equals(ActivityStatus.Running)) {
            throw UserAccountException.ACTIVITY_UNCHECKED_OR_IN_RUNNING;
        }
        logger.info("Activity start id:{}, activityStatus:{}, activityCheckState:{}", id, activityStatus, activityCheckState);
        if (activityCheckState.equals(ActivityCheckState.UnChecked) || activityStatus.equals(ActivityStatus.Finish)
                || activityStatus.equals(ActivityStatus.Deleted)) {
            throw UserAccountException.ACTIVITY_UNCHECKED_OR_FINISHED;
        }
        if (activityStatus.equals(ActivityStatus.Pause)) {
            configDao.updateStatus(id, ActivityStatus.Running.getValue());
            return;
        }

    }

    /**
     * 开始红包活动
     * @throws UserAccountException
     */
    public void restart() throws UserAccountException {
        if (!activityStatus.equals(ActivityStatus.Pause)) {
            throw UserAccountException.ACTIVITY_STATUS_ERROR;
        }
        configDao.updateStatus(id, ActivityStatus.Running.getValue());
    }

    /**
     * 结束宏奥活动
     * @throws UserAccountException
     */
    public void finish() throws UserAccountException {
        configDao.updateStatus(id, ActivityStatus.Finish.getValue());
    }

    /**
     * 删除红包活动
     * @throws UserAccountException
     */
    public void delete() throws UserAccountException {
        configDao.updateStatus(id, ActivityStatus.Deleted.getValue());
    }

    /**
     * 暂停红包活动
     */
    public void pause() {
        configDao.updateStatus(id, ActivityStatus.Pause.getValue());
    }

    /**
     * 修改红包活动数据
     * 如果
     * @param activityVO
     * @param checked
     */
    public void updateInfo(ActivityVO activityVO, boolean checked) {
        if (!activityStatus.equals(ActivityStatus.UnStart) && !activityStatus.equals(ActivityStatus.UnChecked)
                && !activityStatus.equals(ActivityStatus.Checked)) {
            throw UserAccountException.ACTIVITY_RUNNING;
        } else {

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addDateValidate("活动开始时间", activityVO.getStartTime(), DateType.longDate);
            validateUtil.addDateValidate("活动结束时间", activityVO.getEndTime(), DateType.longDate);
            validateUtil.addIntTypeValidate("红包总预算", activityVO.getTotalAmount(), 10, 8888889);
            validateUtil.addDoubleTypeValidate("红包下限", activityVO.getRangeMin(), 1d, 201d);
            validateUtil.addDoubleTypeValidate("红包上限", activityVO.getRangeMax(), 1d, 201d);
            validateUtil.addIntTypeValidate("中奖概率", activityVO.getProbability(), 1, 101);
            validateUtil.addIntTypeValidate("分布类型", activityVO.getdType(), 0, 2);
            validateUtil.addStringLengthValidate("抽奖页面", activityVO.getHeadline(), 0, 513);
            validateUtil.addStringLengthValidate("抽象失败页面标题", activityVO.getHeadlineFailure(), 0, 513);
            validateUtil.addStringLengthValidate("转发消息标题", activityVO.getShareTitle(), 0, 513);
            validateUtil.addStringLengthValidate("转发消息摘要", activityVO.getShareDesc(), 0, 513);
            validateUtil.addStringLengthValidate("转发消息背景图地址", activityVO.getShareImg(), 0, 513);
            String result = validateUtil.validate();
            if (StringUtils.isNotBlank(result)) {
                throw UserAccountException.validateFailed(result);
            }

            HrHbConfigRecord hrHbConfig = configDao.fetchById(id);

            if (activityVO.getTarget() != null) {
                hrHbConfig.setTarget(activityVO.getTarget().byteValue());
            }
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(activityVO.getStartTime())) {
                hrHbConfig.setStartTime(new Timestamp(DateTime.parse(activityVO.getStartTime(), format).getMillis()));
            }
            if (StringUtils.isNotBlank(activityVO.getEndTime())) {
                hrHbConfig.setEndTime(new Timestamp(DateTime.parse(activityVO.getEndTime(), format).getMillis()));
            }
            if (activityVO.getTotalAmount() != null) {
                hrHbConfig.setTotalAmount(activityVO.getTotalAmount());
                totalAmount = activityVO.getTotalAmount();
            }
            if (activityVO.getRangeMax() != null) {
                hrHbConfig.setRangeMax(activityVO.getRangeMax());
            }
            if (activityVO.getRangeMin() != null) {
                hrHbConfig.setRangeMin(activityVO.getRangeMin());
            }
            if (hrHbConfig.getRangeMax() != null && hrHbConfig.getRangeMin() != null
                    && hrHbConfig.getRangeMax() < hrHbConfig.getRangeMin()) {
                throw UserAccountException.validateFailed("红包上下限制设置有误。");
            }
            if (activityVO.getProbability() != null) {
                hrHbConfig.setProbability(activityVO.getProbability().doubleValue());
            }
            if (activityVO.getdType() != null) {
                hrHbConfig.setDType(activityVO.getdType().byteValue());
            }
            if (activityVO.getHeadline() != null) {
                hrHbConfig.setHeadline(activityVO.getHeadline());
            }
            if (activityVO.getHeadlineFailure() != null) {
                hrHbConfig.setHeadlineFailure(activityVO.getHeadlineFailure());
            }
            if (activityVO.getShareTitle() != null) {
                hrHbConfig.setShareTitle(activityVO.getShareTitle());
            }
            if (activityVO.getShareDesc() != null) {
                hrHbConfig.setShareDesc(activityVO.getShareDesc());
            }
            if (activityVO.getShareImg() != null) {
                hrHbConfig.setShareImg(activityVO.getShareImg());
            }
            if (activityVO.getEstimatedTotal() != null) {
                hrHbConfig.setEstimatedTotal(activityVO.getEstimatedTotal());
            }
            if (activityVO.getActualTotal() != null) {
                hrHbConfig.setActualTotal(activityVO.getActualTotal());
            }
            logger.info("Activity updateInfo checked:{} activityStatus:{}", checked, activityStatus);
            if (activityVO.getTheme() != null) {
                themeDao.upsert(id, activityVO.getTheme());
            }
            if (checked) {
                hrHbConfig.setChecked(ActivityCheckState.UnChecked.getValue());
            }
            configDao.update(hrHbConfig);
            if (activityVO.getTheme() != null) {
                themeDao.upsert(id, activityVO.getTheme());
            }
        }
    }

    /**
     * 通用数据封装
     * @param index 位置
     * @param amount 金额
     * @param configId 红包活动编号
     * @param configPositionId 红包活动与职位关系表编号
     * @return 红包数据
     */
    protected static HrHbItemsRecord amountToRecord(int index, double amount, int configId, int configPositionId) {
        HrHbItemsRecord hrHbItemsRecord = new HrHbItemsRecord();
        hrHbItemsRecord.setHbConfigId(configId);
        hrHbItemsRecord.setAmount(BigDecimal.valueOf(amount));
        hrHbItemsRecord.setIndex(index);
        hrHbItemsRecord.setStatus((byte) 0);
        hrHbItemsRecord.setBindingId(configPositionId);
        return hrHbItemsRecord;
    }

    protected HrHbConfigDao configDao;
    protected HrHbPositionBindingDao positionBindingDao;
    protected HrHbItemsDao itemsDao;
    protected ThemeDao themeDao;

    protected Integer id;
    protected Integer totalAmount;

    protected ActivityStatus activityStatus;
    protected ActivityCheckState activityCheckState;
}
