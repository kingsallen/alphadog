package com.moseeker.entity;

import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.baseorm.dao.candidatedb.CandidateRecomRecordDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.referraldb.ReferralLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.biz.ProfileCompletenessImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/7/18
 */
@Service
@CounterIface
public class ReferralEntity {

    @Autowired
    CandidateShareChainDao shareChainDao;

    @Autowired
    UserEmployeePointsRecordDao pointsRecordDao;

    @Autowired
    private ReferralLogDao referralLogDao;

    @Autowired
    private CandidateRecomRecordDao candidateRecomRecordDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private ProfileCompletenessImpl completeness;

    @Autowired
    private HrPointsConfDao pointsConfDao;

    @Autowired
    EmployeeEntity employeeEntity;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 计算给定时间内的员工内推带来的转发次数
     * @param userIdList 用户编号
     * @param positionIdList 职位编号
     * @param lastFriday 开始时间 (大于开始时间)
     * @param currentFriday 结束时间 （小于等于结束时间）
     * @return 员工与转发次数的关系
     */
    public Map<Integer, Integer> countEmployeeForward(List<Integer> userIdList, List<Integer> positionIdList, LocalDateTime lastFriday,
                                                      LocalDateTime currentFriday) {

        Result<Record2<Integer, Integer>> result = shareChainDao.countEmployeeForward(userIdList, positionIdList,
                lastFriday, currentFriday);

        if (result != null && result.size() > 0) {


            Map<Integer, Integer> employeeForward = result
                    .stream()
                    .collect(Collectors.toMap(Record2::value1, Record2::value2));

            //重复推荐数量
            Result<Record2<Integer, Integer>> repeatRecommand = shareChainDao.countRepeatRecommend(userIdList,
                    positionIdList, lastFriday, currentFriday);
            if (repeatRecommand != null && repeatRecommand.size() > 0) {
                repeatRecommand.forEach(integerIntegerRecord2 -> {
                    if (employeeForward.get(integerIntegerRecord2.value1()) != null) {
                        employeeForward.put(integerIntegerRecord2.value1(),
                                employeeForward.get(integerIntegerRecord2.value1())
                                        - integerIntegerRecord2.value2() + 1);
                    }
                });
            }

            return employeeForward;
        } else {
            return new HashMap<>();
        }
    }

    public Map<Integer,Integer> countEmployeeAwards(List<Integer> employeeIdList, LocalDateTime lastFriday,
                                                    LocalDateTime currentFriday) {
        Result<Record2<Long, BigDecimal>> result = pointsRecordDao.countEmployeeAwards(employeeIdList, lastFriday,
                currentFriday);

        if (result != null && result.size() > 0) {
            return result
                    .stream()
                    .filter(longBigDecimalRecord2 -> longBigDecimalRecord2.value1() != null)
                    .collect(Collectors.toMap(record->record.value1().intValue(),
                    record -> record.value2().intValue()));
        } else {
            return new HashMap<>();
        }
    }

    public void logReferralOperation(int position, int applicationId, int depth, List<String> referralReasons,
                                     String mobile, UserEmployeeDO employeeDO, int presenteeUserId, byte gender, String email) {
        CandidateRecomRecordRecord recomRecordRecord = new CandidateRecomRecordRecord();
        recomRecordRecord.setPositionId(position);
        recomRecordRecord.setAppId(applicationId);
        recomRecordRecord.setRecomTime(new Timestamp(System.currentTimeMillis()));
        recomRecordRecord.setDepth(depth);
        recomRecordRecord.setRecomReason(referralReasons.stream().collect(Collectors.joining(",")));
        recomRecordRecord.setMobile(mobile);
        recomRecordRecord.setPresenteeId(presenteeUserId);
        recomRecordRecord.setPostUserId(employeeDO.getSysuserId());
        recomRecordRecord.setGender(gender);
        recomRecordRecord.setEmail(email);
        candidateRecomRecordDao.insertIfNotExist(recomRecordRecord);
        try {
            employeeEntity.addAwardBefore(employeeDO.getId(), employeeDO.getCompanyId(), position, 16, presenteeUserId, applicationId);
            employeeEntity.addAwardBefore(employeeDO.getId(), employeeDO.getCompanyId(), position, 13, presenteeUserId, applicationId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public int logReferralOperation(int employeeId, int userId, int position, ReferralType referralType) {

        return referralLogDao.createReferralLog(employeeId, userId, position, referralType.getValue());
    }

    public ReferralLog fetchReferralLog(int referralLogId) {
        return referralLogDao.fetchOneById(referralLogId);
    }

    public void claimReferralCard(UserUserDO userUserDO, ReferralLog referralLog) {
        JobApplication application = applicationDao.getByUserIdAndPositionId(referralLog.getReferenceId(),
                referralLog.getPositionId());
        if (application != null) {
            application.setApplierId(userUserDO.getId());
            application.setApplierName(userUserDO.getName());
        }

        ProfileProfileRecord profileProfileRecord = profileDao.getProfileByUserId(userUserDO.getId());
        if (profileProfileRecord == null) {
            ProfileProfileRecord record = profileDao.getProfileByUserId(referralLog.getReferenceId());
            if (record != null) {
                if (profileDao.changUserId(record, userUserDO.getId()) > 0) {
                    completeness.reCalculateProfileCompleteness(record.getId());
                }
            }
        }

        int postUserId = 0;
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Query query = queryBuilder.where(UserEmployee.USER_EMPLOYEE.ID.getName(), referralLog.getEmployeeId()).buildQuery();
        UserEmployeeDO userEmployeeDO = employeeDao.getEmployee(query);
        if (userEmployeeDO != null) {
            postUserId = userEmployeeDO.getSysuserId();
        } else {
            UserEmployeeDO userEmployeeDO1 = historyUserEmployeeDao.getData(query);
            if (userEmployeeDO1 != null) {
                postUserId = userEmployeeDO1.getSysuserId();
            }
        }
        if (postUserId > 0) {
            candidateRecomRecordDao.changePostUserId(postUserId, referralLog.getReferenceId(), userUserDO.getId());
        }

        referralLog.setReferenceId(userUserDO.getId());
        referralLogDao.update(referralLog);
    }
}
