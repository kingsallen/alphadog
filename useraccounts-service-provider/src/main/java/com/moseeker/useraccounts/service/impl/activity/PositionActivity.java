package com.moseeker.useraccounts.service.impl.activity;

import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.ActivityVO;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/11/6
 */
public abstract class PositionActivity extends Activity {

    public PositionActivity(int id,
                            HrHbConfigDao configDao, HrHbPositionBindingDao positionBindingDao,
                            HrHbItemsDao itemsDao, JobPositionDao positionDao) {
        super(id, configDao, positionBindingDao, itemsDao);
        this.positionDao = positionDao;
    }

    @Override
    public void start(ActivityVO activityVO) throws UserAccountException {

        super.start(activityVO);

        if (!activityStatus.equals(ActivityStatus.Pause)) {
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredOneValidate("红包金额", activityVO.getAmounts());
            String result = validateUtil.validate();
            if (StringUtils.isNotBlank(result)) {
                throw UserAccountException.validateFailed(result);
            }
        }
        List<HrHbItemsRecord> items = new ArrayList<>();

        //是否生成红包数据 如果已经生成就不需要再生成了
        int count = itemsDao.countItemsByActivity(id);
        if (count == 0) {
            List<HrHbPositionBindingRecord> recordList = positionBindingDao.fetchByActivity(id);
            if (recordList == null || recordList.size() == 0) {
                throw UserAccountException.ACTIVITY_POSITIONS_ERROR;
            }

            BigDecimal totalAmount = new BigDecimal(0);
            BigDecimal totalAmount1 = new BigDecimal(0);
            for (HrHbPositionBindingRecord position : recordList) {
                totalAmount1.add(position.getTotalAmount());
                for(int i = 0; i<activityVO.getAmounts().size(); i++){
                    HrHbItemsRecord hrHbItemsRecord = amountToRecord(i+1, activityVO.getAmounts().get(i), id, position.getId());
                    items.add(hrHbItemsRecord);
                    totalAmount.add(hrHbItemsRecord.getAmount());
                }
                position.setTotalNum(activityVO.getAmounts().size());
            }
            positionBindingDao.updateRecords(recordList);
            if (totalAmount.doubleValue() > this.totalAmount || totalAmount1.doubleValue() > this.totalAmount) {
                throw UserAccountException.ACTIVITY_AMOUNT_ERROR;
            }
            itemsDao.insertIfNotExistForStartActivity(items);

            activityVO.setActualTotal(activityVO.getAmounts().size()*recordList.size());
        }
        updateInfo(activityVO, false);
        configDao.updateStatus(id, ActivityStatus.Running.getValue());
    }

    /**
     * 更新红包活动数据
     * @param activityVO 红包活动参数
     */
    @Override
    public void updateInfo(ActivityVO activityVO, boolean checked) {
        //更新活动的基本信息
        super.updateInfo(activityVO, checked);
        //如果是未审核或者审核未通过，那么依然可以重新选择参与红包的职位。
        if (activityVO.getPositionIds() != null && activityVO.getPositionIds().size() > 0) {
            logger.info("PositionActivity updateInfo activityVO.getPositionIds().size:{}", activityVO.getPositionIds());
            if (activityStatus.equals(ActivityStatus.Checked)
                    || activityStatus.equals(ActivityStatus.UnChecked)
                    || activityStatus.equals(ActivityStatus.UnStart)) {

                logger.info("PositionActivity updateInfo activityStatus:{}", activityStatus);
                List<HrHbPositionBindingRecord> bindingRecords = positionBindingDao.fetchByActivity(id);
                List<JobPosition> positionList = positionDao.getJobPositionByIdList(activityVO.getPositionIds());

                logger.info("PositionActivity updateInfo bindingRecords:{}, positionList", bindingRecords, positionList);
                //如果选择的职位和之前配置的职位一直，则不需要做任何处理
                if (checkIfChangePosition(activityVO.getPositionIds(), bindingRecords)) {
                    logger.info("PositionActivity updateInfo change position");
                    if (positionList == null || positionList.size() != activityVO.getPositionIds().size()) {
                        throw UserAccountException.ACTIVITY_POSITIONS_ERROR;
                    } else {
                        for (JobPosition position : positionList) {
                            if ((position.getHbStatus().intValue() | 1) == position.getHbStatus()) {
                                throw UserAccountException.ACTIVITY_POSITION_ALREADY_IN_ACTIVITY;
                            }
                        }
                    }

                    //将之前参与活动的职位删除，并修改职位参与活动的状态。
                    releasePosition(bindingRecords);
                    positionBindingDao.deleteByActivityId(id);

                    //重新创建参与红包活动的职位信息，并更新这些职位的红包状态
                    List<HrHbPositionBindingRecord> bindings = new ArrayList<>();
                    Map<Integer, Byte> newStatus = new HashMap<>();
                    HrHbConfigRecord hrHbConfig = configDao.fetchById(id);
                    for (JobPosition position : positionList) {
                        HrHbPositionBindingRecord binding = new HrHbPositionBindingRecord();
                        binding.setHbConfigId(id);
                        binding.setPositionId(position.getId());
                        binding.setTriggerWay(getTriggerWay());
                        binding.setTotalAmount(BigDecimal.valueOf(hrHbConfig.getTotalAmount()));
                        bindings.add(binding);
                        newStatus.put(position.getId(), (byte)(position.getHbStatus() | positionHBStatus.getValue()));
                    }
                    logger.info("PositionActivity updateInfo insert bindings:{}", bindings);
                    positionBindingDao.insert(bindings);

                    try {
                        logger.info("PositionActivity updateInfo 职位参加活动 positionList:{}, newStatus:{}",positionList, newStatus);
                        positionDao.updateHBStatus(positionList, newStatus);
                    } catch (CommonException e) {
                        throw UserAccountException.ACTIVITY_POSITION_HB_STATUS_UPDATE_FAILURE;
                    }
                }
            }
        }
    }

    @Override
    public void finish() throws UserAccountException {
        super.finish();
        releasePosition();
    }

    @Override
    public void delete() throws UserAccountException {
        super.delete();
        releasePosition();
    }

    /**
     * 释放参与红包活动的职位的红包状态
     */
    private void releasePosition() {
        List<HrHbPositionBindingRecord> bindingRecords = positionBindingDao.fetchByActivity(id);
        logger.info("PositionActivity releasePosition bindingRecords:{}", bindingRecords);
        releasePosition(bindingRecords);
    }

    /**
     * 释放参与红包活动的职位的红包状态
     * @param bindingRecords
     */
    private void releasePosition(List<HrHbPositionBindingRecord> bindingRecords) {
        if (bindingRecords != null && bindingRecords.size() > 0) {
            List<Integer> positionIdList = bindingRecords
                    .stream()
                    .map(HrHbPositionBindingRecord::getPositionId)
                    .collect(Collectors.toList());
            List<JobPosition> positions = positionDao.getJobPositionByIdList(positionIdList);
            logger.info("PositionActivity releasePosition positions:{}", positions);
            Map<Integer, Byte> newStatus = new HashMap<>();
            for (JobPosition position : positions) {
                //获取当前
                newStatus.put(position.getId(), (byte)(position.getHbStatus()^positionHBStatus.getValue()));
                //处理职位是否在参加活动数据
            }
            logger.info("PositionActivity releasePosition newStatus:{}", newStatus);
            try {
                positionDao.updateHBStatus(positions, newStatus);
            } catch (CommonException e) {
                throw UserAccountException.ACTIVITY_POSITION_HB_STATUS_UPDATE_FAILURE;
            }
        }
    }

    /**
     * 校验是否需要做职位参与红包活动的状态变更
     * @param positionIds 所选的职位
     * @param bindingRecords 之前配置的职位
     * @return
     */
    private boolean checkIfChangePosition(List<Integer> positionIds, List<HrHbPositionBindingRecord> bindingRecords) {
        if (positionIds.size() == bindingRecords.size()) {
            Optional<Integer> optional = positionIds.stream().filter(integer -> {
                Optional<HrHbPositionBindingRecord> optional1 = bindingRecords
                        .stream()
                        .filter(hrHbPositionBindingRecord -> hrHbPositionBindingRecord.getPositionId().equals(integer))
                        .findAny();
                return !optional1.isPresent();
            }).findAny();
            return optional.isPresent();
        } else {
            return false;
        }
    }

    public abstract byte getTriggerWay() throws UserAccountException;

    protected JobPositionDao positionDao;

    protected PositionHBStatus positionHBStatus;
}
