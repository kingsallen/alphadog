package com.moseeker.entity;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.pojo.ApplicationSaveResultVO;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.exception.ApplicationException;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/7/18
 */
@Service
@CounterIface
public class ApplicationEntity {

    @Autowired
    JobApplicationDao applicationDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadPool tp = ThreadPool.Instance;

    /**
     * 计算给定时间内的员工投递次数
     * @param userIdList 用户编号集合
     * @param positionIdList 职位编号集合
     * @param lastFriday 开始时间（>开始时间）
     * @param currentFriday 结束时间（<=结束时间）
     * @return 计算给定时间内的员工投递次数
     */
    public Map<Integer,Integer> countEmployeeApply(List<Integer> userIdList, List<Integer> positionIdList, LocalDateTime lastFriday,
                                                   LocalDateTime currentFriday) {

        Result<Record2<Integer, Integer>> result = applicationDao.countEmployeeApply(userIdList, positionIdList, lastFriday,
                currentFriday);

        if (result != null && result.size() > 0) {
            return result.stream().collect(Collectors.toMap(Record2::value1, Record2::value2));
        } else {
            return new HashMap<>();
        }
    }

    /**
     * 保存员工主动推荐的申请信息
     * @param referenceId 员工
     * @param applierId 申请人
     * @param companyId 公司编号
     * @param positionList 职位编号集合
     * @return 申请编号列表
     * @throws ApplicationException
     */
    @Transactional
    public List<ApplicationSaveResultVO> storeEmployeeProxyApply(int referenceId, int applierId, int companyId, List<Integer> positionList) throws ApplicationException {

        List<ApplicationSaveResultVO> applyIdList = new ArrayList<>();
        for (Integer positionId : positionList) {
            JobApplicationRecord jobApplicationRecord = new JobApplicationRecord();
            jobApplicationRecord.setAppTplId(Constant.RECRUIT_STATUS_APPLY);
            jobApplicationRecord.setCompanyId(companyId);
            jobApplicationRecord.setAtsStatus(0);
            jobApplicationRecord.setRecommenderUserId(referenceId);
            jobApplicationRecord.setPositionId(positionId);
            jobApplicationRecord.setApplierId(applierId);
            jobApplicationRecord.setOrigin(ApplicationSource.EMPLOYEE_REFERRAL.getValue());

            Future<ApplicationSaveResultVO> future = tp.startTast(() -> applicationDao.addIfNotExists(jobApplicationRecord));
            try {
                applyIdList.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
                throw ApplicationException.APPLICATION_CUSTOM_POSITION_VALIDATE_FAILED;
            }
        }

        return applyIdList;
    }
}
