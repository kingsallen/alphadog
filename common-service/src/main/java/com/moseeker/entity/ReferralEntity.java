package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.HBType;
import com.moseeker.baseorm.constant.ReferralType;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionDao;
import com.moseeker.baseorm.dao.candidatedb.CandidatePositionShareRecordDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrHbConfigDao;
import com.moseeker.baseorm.dao.hrdb.HrHbPositionBindingDao;
import com.moseeker.baseorm.dao.hrdb.HrHbScratchCardDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.profiledb.ProfileAttachmentDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.referraldb.*;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.biz.ProfileCompletenessImpl;
import com.moseeker.entity.exception.EmployeeException;
import com.moseeker.entity.pojos.*;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAttachmentDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: jack
 * @Date: 2018/7/18
 */
@Service
//@CounterIface
public class ReferralEntity {

    @Autowired
    private HrHbConfigDao configDao;

    @Autowired
    private HrHbPositionBindingDao positionBindingDao;

    @Autowired
    private HrHbScratchCardDao scratchCardDao;

    @Autowired
    CandidateShareChainDao shareChainDao;

    @Autowired
    CandidatePositionShareRecordDao positionShareRecordDao;

    @Autowired
    UserEmployeePointsRecordDao pointsRecordDao;

    @Autowired
    private ReferralLogDao referralLogDao;

    @Autowired
    private ReferralConnectionLogDao connectionLogDao;

    @Autowired
    private ReferralSeekRecommendDao recommendDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;

    @Autowired
    private CandidateCompanyDao candidateCompanyDao;

    @Autowired
    private ReferralRecomEvaluationDao recomEvaluationDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileAttachmentDao attachmentDao;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private ReferralPositionBonusStageDetailDao stageDetailDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private HrOperationRecordDao operationRecordDao;

    @Autowired
    private ReferralRecomHbPositionDao referralRecomHbPositionDao;

    @Autowired
    private ProfileCompletenessImpl completeness;

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    SearchengineEntity searchengineEntity;

    @Autowired
    private UserWxEntity wxEntity;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private UserUserDao userDao;

    //redis的客户端
    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private ReferralEmployeeNetworkResourcesDao networkResourcesDao;



    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadPool threadPool = ThreadPool.Instance;

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

    public void logReferralOperation(int positionId, int applicationId,  List<String> referralReasons,String mobile,
                                     int postUserId, int presenteeUserId, byte shipType, String referralText) {
        ReferralRecomEvaluationRecord evaluationRecord = new ReferralRecomEvaluationRecord();
        evaluationRecord.setAppId(applicationId);
        evaluationRecord.setRecomReasonTag(referralReasons.stream().collect(Collectors.joining(",")));
        evaluationRecord.setRelationship(shipType);
        evaluationRecord.setRecomReasonText(referralText);
        evaluationRecord.setMobile(mobile);
        evaluationRecord.setPresenteeUserId(presenteeUserId);
        evaluationRecord.setPostUserId(postUserId);
        evaluationRecord.setPositionId(positionId);
        recomEvaluationDao.insertIfNotExist(evaluationRecord);
    }

    public int logReferralOperation(int employeeId, int userId, int attachmentId, int position, ReferralType referralType)
            throws EmployeeException {

        int referralId = referralLogDao.createReferralLog(employeeId, userId, position, referralType.getValue(), attachmentId);
        if (referralId == 0) {
            throw EmployeeException.EMPLOYEE_REPEAT_RECOMMEND;
        }
        return referralId;
    }

    public List<ReferralLog> fetchReferralLogs(List<Integer> referralLogIds) {
        return referralLogDao.fetchByIds(referralLogIds);
    }

    public ReferralLog fetchReferralLog(int referralLogId) {
        return referralLogDao.fetchOneById(referralLogId);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void claimReferralCard(UserUserDO userUserDO, ReferralLog referralLog) throws EmployeeException {
        logger.info("ReferralEntity claimReferralCard");

        logger.info("ReferralEntity claimReferralCard userUserDO:{}, referralLog:{}",
                JSONObject.toJSONString(userUserDO), JSONObject.toJSONString(referralLog));
        // 判断是否重复认证
        if (!referralLogDao.claim(referralLog, userUserDO.getId())) {
            logger.info("ReferralEntity claimReferralCard 判断是否重复认证");
            throw EmployeeException.EMPLOYEE_REPEAT_CLAIM;
        }
        // 更新申请记录的申请人
        JobApplication application = applicationDao.getByUserIdAndPositionId(referralLog.getReferenceId(),
                referralLog.getPositionId());
        logger.info("ReferralEntity claimReferralCard userUserDO:{}", JSONObject.toJSONString(application));
        logger.info("ReferralEntity claimReferralCard application:{}", JSONObject.toJSONString(userUserDO));
        if (application != null) {

            logger.info("ReferralEntity claimReferralCard");
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            applicationDao.updateIfNotExist(application.getId(), userUserDO.getId(), userUserDO.getName(),
                    ApplicationSource.EMPLOYEE_REFERRAL.andSource(application.getOrigin()), updateTime,
                    application.getPositionId());
            logger.info("ReferralEntity claimReferralCard updateIfNotExist");

            logger.info("ReferralEntity claimReferralCard before removeApplication");
            searchengineEntity.removeApplication(application.getApplierId(), application.getId(),
                    application.getApplierId(), application.getApplierName(), updateTime);
            logger.info("ReferralEntity claimReferralCard removeApplication");
        }


        // 更新简历中的userId，计算简历完整度
        updateProfileUserIdAndCompleteness(userUserDO.getId(), referralLog.getReferenceId());
        logger.info("ReferralEntity claimReferralCard updateProfileUserIdAndCompleteness");

        // 更新候选人推荐记录中的推荐人
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
            recomEvaluationDao.changePostUserId(postUserId, referralLog.getPositionId(), referralLog.getReferenceId(), userUserDO.getId());
        }
        logger.info("ReferralEntity claimReferralCard end!");
    }

    public ReferralLog fetchReferralLog(Integer employeeId, Integer positionId, int referenceId) {
        return referralLogDao.fetchByEmployeeIdReferenceIdUserId(employeeId, referenceId, positionId);
    }

    public ReferralLog fetchReferralLog(Integer employeeId,  int referenceId) {
        return referralLogDao.fetchByEmployeeIdReferenceId(employeeId, referenceId);
    }

    public List<Integer> fetchReferenceIdList(int userId) {
        UserEmployeeRecord employeeRecord = employeeDao.getActiveEmployeeByUserId(userId);
        if (employeeRecord != null) {
            return referralLogDao.fetchReferenceIdByEmployeeId(employeeRecord.getId());
        }
        return new ArrayList<>();
    }

    /**
     * 查找个人中心红包列表所需的数据
     * @param itemsRecords 红包记录
     * @return 数据对象
     */
    public HBData fetchHBData(List<HrHbItems> itemsRecords) {
        HBData data = new HBData();
        //查找职位title
        List<Integer> positionBindingIdList = itemsRecords.stream().map(HrHbItems::getBindingId).collect(Collectors.toList());
        Future<List<Record2<Integer, String>>> titleFuture = threadPool.startTast(() -> positionBindingDao.fetchPositionTitle(positionBindingIdList));

        //查找候选人姓名
        List<Integer> triggerWxUserIdList = itemsRecords.stream().map(HrHbItems::getTriggerWxuserId).collect(Collectors.toList());
        Future<Map<Integer, String>> candidateNamesFuture = threadPool.startTast(() -> wxEntity.fetchUserNamesByWxUserIdList(triggerWxUserIdList));

        //查找刮刮卡cardno
        List<Integer> itemIdList = itemsRecords.stream().map(HrHbItems::getId).collect(Collectors.toList());
        Future<List<HrHbScratchCardRecord>> cardNoFuture = threadPool.startTast(() -> scratchCardDao.fetchCardNosByItemIdList(itemIdList));

        //查找红包类型
        List<Integer> configIdList = itemsRecords.stream().map(HrHbItems::getHbConfigId).distinct().collect(Collectors.toList());
        Future<List<HrHbConfigRecord>> configListFuture = threadPool.startTast(() -> configDao.fetchByIdList(configIdList));

        Map<Integer, String> titleMap = new HashMap<>();
        try {
            List<Record2<Integer, String>> titleList = titleFuture.get();
            if (titleList != null && titleList.size() > 0) {
                titleList.forEach(integerStringRecord2 -> {
                    titleMap.put(integerStringRecord2.value1(), integerStringRecord2.value2());
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        data.setTitleMap(titleMap);

        Map<Integer, String> candidateNameMap = new HashMap<>();
        try {
            candidateNameMap = candidateNamesFuture.get();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        data.setCandidateNameMap(candidateNameMap);
        logger.info("ReferralEntity fetchHBData triggerWxUserIdList:{}", triggerWxUserIdList);
        logger.info("ReferralEntity fetchHBData candidateNameMap:{}", data.getCandidateNameMap());

        Map<Integer, HrHbScratchCardRecord> cardNoMap = new HashMap<>();
        try {
            List<HrHbScratchCardRecord> cardNoList = cardNoFuture.get();
            if (cardNoList != null && cardNoList.size() > 0) {
                for (HrHbScratchCardRecord record2 : cardNoList) {
                    cardNoMap.put(record2.getHbItemId(), record2);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        data.setCardNoMap(cardNoMap);

        Map<Integer, HrHbConfigRecord> configMap = new HashMap<>();
        try {
            List<HrHbConfigRecord> configRecordList= configListFuture.get();
            if (configRecordList != null && configRecordList.size() > 0) {
                configRecordList.forEach(hrHbConfigRecord -> configMap.put(hrHbConfigRecord.getId(), hrHbConfigRecord));


                List<HrHbConfigRecord> recomTypes = configRecordList
                        .stream().filter(hrHbConfigRecord ->
                                hrHbConfigRecord.getType().equals(HBType.Recommend.getValue()))
                        .collect(Collectors.toList());

                if (recomTypes != null && recomTypes.size() > 0) {
                    List<Integer> recomItemIdList = itemsRecords
                            .stream()
                            .filter(hrHbItems -> {
                                Optional<HrHbConfigRecord> recomTypeOptional = recomTypes
                                        .stream()
                                        .filter(hrHbConfigRecord -> hrHbConfigRecord.getId().equals(hrHbItems.getHbConfigId()))
                                        .findAny();
                                return recomTypeOptional.isPresent();
                            })
                            .map(hrHbItems -> hrHbItems.getId())
                            .collect(Collectors.toList());
                    if (recomItemIdList != null && recomItemIdList.size() > 0) {
                        List<Record3<Integer, String, String>> result = referralRecomHbPositionDao.fetchRecommendHBData(recomItemIdList);

                        if (result != null && result.size() > 0) {
                            Map<Integer, RecommendHBData> recommendHBDataMap = new HashMap<>();

                            result.forEach(integerStringStringRecord3 -> {
                                RecommendHBData recommendHBData = new RecommendHBData();
                                recommendHBData.setPositionTitle(integerStringStringRecord3.value2());
                                recommendHBData.setCandidateName(integerStringStringRecord3.value3());
                                recommendHBDataMap.put(integerStringStringRecord3.value1(), recommendHBData);
                            });
                            data.setRecomPositionTitleMap(recommendHBDataMap);
                        }
                    }
                }

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        data.setConfigMap(configMap);

        return data;
    }

    public BonusData fetchBonusData(List<ReferralEmployeeBonusRecord> referralEmployeeBonusRecordList) {
        BonusData bonusData = new BonusData();

        List<Integer> applicationIdList = referralEmployeeBonusRecordList
                .stream()
                .map(ReferralEmployeeBonusRecord::getApplicationId)
                .collect(Collectors.toList());
        List<JobApplicationRecord> applicationRecordList = applicationDao.fetchByIdList(applicationIdList);

        if (applicationRecordList != null && applicationRecordList.size() > 0) {

            List<Integer> applierIdList = applicationRecordList
                    .stream()
                    .map(JobApplicationRecord::getApplierId)
                    .collect(Collectors.toList());
            Future<Map<Integer, String>> candidateNameFuture = threadPool
                    .startTast(() -> userAccountEntity.fetchUserName(applierIdList));

            List<Integer> positionIdList = applicationRecordList
                    .stream()
                    .map(JobApplicationRecord::getPositionId)
                    .collect(Collectors.toList());
            Future<List<JobPosition>> positionTitleFuture = threadPool
                    .startTast(() -> positionDao.fetchPosition(positionIdList));

            List<Integer> appIdList = applicationRecordList
                    .stream()
                    .map(JobApplicationRecord::getId)
                    .collect(Collectors.toList());
            Future<List<HrOperationRecord>> hiredFuture = threadPool
                    .startTast(()
                            -> operationRecordDao.fetchLastOperationByAppIdListAndSate(appIdList,
                            Constant.RECRUIT_STATUS_HIRED));

            try {
                Map<Integer, String> candidateMap = new HashMap<>();
                Map<Integer, String> userNameMap = candidateNameFuture.get();
                userNameMap.forEach((key, value) -> {
                    Optional<JobApplicationRecord> optional = applicationRecordList
                            .stream()
                            .filter(jobApplicationRecord -> jobApplicationRecord.getApplierId().equals(key))
                            .findAny();
                    if (optional.isPresent()) {
                        candidateMap.put(optional.get().getId(), value);
                    }
                });
                bonusData.setCandidateMap(candidateMap);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            Map<Integer, String> positionTitleMap = new HashMap<>();
            try {
                List<JobPosition> positionList = positionTitleFuture.get();
                if (positionIdList != null && positionList.size() > 0) {
                    applicationRecordList.forEach(jobApplicationRecord -> {
                        Optional<JobPosition> positionOptional = positionList
                                .stream()
                                .filter(jobPosition -> jobPosition.getId().equals(jobApplicationRecord.getPositionId()))
                                .findAny();
                        if (positionOptional.isPresent()) {
                            positionTitleMap.put(jobApplicationRecord.getId(), positionOptional.get().getTitle());
                        }
                    });
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            bonusData.setPositionTitleMap(positionTitleMap);

            Map<Integer, Long> employmentDateMap = new HashMap<>();
            try {
                List<HrOperationRecord> operationRecordList = hiredFuture.get();
                if (operationRecordList != null && operationRecordList.size() > 0) {
                    logger.info("ReferralEntity fetchBonusData operationRecordList:{}", operationRecordList);
                    operationRecordList.forEach(operation -> {
                        employmentDateMap.put(operation.getAppId().intValue(), operation.getOptTime().getTime());
                    });
                } else {
                    logger.info("ReferralEntity fetchBonusData operationRecordList is null!");
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            bonusData.setEmploymentDateMap(employmentDateMap);
            logger.info("ReferralEntity fetchBonusData employmentDateMap:{}", bonusData.getEmploymentDateMap());
        }

        List<Integer> stageIdList = referralEmployeeBonusRecordList
                .stream()
                .map(ReferralEmployeeBonusRecord::getBonusStageDetailId)
                .collect(Collectors.toList());

        Future<List<ReferralPositionBonusStageDetailRecord>> stageDetailFuture = threadPool
                .startTast(() -> stageDetailDao.fetchByIdList(stageIdList));
        Map<Integer, ReferralPositionBonusStageDetailRecord> stageDetailRecordMap = new HashMap<>();
        try {
            List<ReferralPositionBonusStageDetailRecord> stageDetailRecordList = stageDetailFuture.get();
            if (stageDetailRecordList != null && stageDetailRecordList.size() > 0) {
                stageDetailRecordList
                        .forEach(referralPositionBonusStageDetailRecord
                                -> stageDetailRecordMap
                                .put(referralPositionBonusStageDetailRecord.getId(),
                                        referralPositionBonusStageDetailRecord));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        bonusData.setStageDetailMap(stageDetailRecordMap);

        return bonusData;
    }

    private void updateProfileUserIdAndCompleteness(int userId, Integer referenceId) {
        ProfileProfileRecord profileProfileRecord = profileDao.getProfileByUserId(userId);
        if (profileProfileRecord == null) {
            ProfileProfileRecord record = profileDao.getProfileByUserId(referenceId);
            if (record != null) {
                if (profileDao.changUserId(record, userId) > 0) {
                    completeness.reCalculateProfileCompleteness(record.getId());
                }
            }
        }
    }

    public  List<ReferralLog> fetchReferralLog(int userId, List<Integer> companyIds, int hrId){
        logger.info("ReferralEntity fetchReferralLog userId:{}, companyIds:{}, hrId:{}", userId, companyIds, hrId);
        ReferralProfileData data = new ReferralProfileData();
        long startTime = System.currentTimeMillis();
        Future<UserHrAccountDO> accountFuture = threadPool.startTast(
                () -> userHrAccountDao.getUserHrAccountById(hrId));
        List<Integer> employeeIds = new ArrayList<>();
        UserHrAccountDO account = new UserHrAccountDO();
        List<Integer> positionIds = new ArrayList<>();
        List<ReferralLog> logs = new ArrayList<>();
        try {
            account = accountFuture.get();
            long accountTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralLog accountTime:{}", accountTime- startTime);
            if(account == null){
                throw CommonException.PROGRAM_PARAM_NOTEXIST;
            }
            long positionTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralLog positionTime:{}", positionTime- accountTime);
            logger.info("ReferralEntity fetchReferralLog account:{}", JSONObject.toJSONString(account));
            if(account.getAccountType() == Constant.ACCOUNT_TYPE_SUBORDINATE){
                positionIds = positionDao.getPositionIdByPublisher(hrId);
                logs = referralLogDao.fetchByEmployeeIdsAndRefenceIdAndPosition(userId, positionIds);
            }else {
                logs = referralLogDao.fetchByEmployeeIdsAndRefenceId(userId);
            }
            logger.info("ReferralEntity fetchReferralLog logs:{}", JSONObject.toJSONString(logs));
            List<Integer> employeeIdList = new ArrayList<>();
            if(!StringUtils.isEmptyList(logs)){
                employeeIdList = logs.stream().map(ReferralLog::getEmployeeId).collect(Collectors.toList());
            }
            logger.info("ReferralEntity fetchReferralLog referralLog.employeeIdList:{}", JSONObject.toJSONString(employeeIdList));
            long employeeTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralLog employeeTime:{}", employeeTime- positionTime);
            List<Integer> temp = employeeIdList;
            Future<List<UserEmployeeDO>> employeeListFeature = threadPool.startTast(
                    () -> employeeDao.getUserEmployeeForIdList(temp));;
            Future<List<UserEmployeeDO>> historyEmployeeListFeature = threadPool.startTast(
                    () -> historyUserEmployeeDao.getHistoryEmployeeByIds(temp));;
            List<UserEmployeeDO> employeeList = employeeListFeature.get();
            if (!StringUtils.isEmptyList(employeeList)){
                logger.info("ReferralEntity fetchReferralLog employeeList{}", JSONObject.toJSONString(employeeList));
                List<Integer> employeeIds1 = employeeList.stream().filter(f -> companyIds.contains(f.getCompanyId()))
                        .map(m -> m.getId()).collect(Collectors.toList());
                logger.info("ReferralEntity fetchReferralLog employeeIds1{}", JSONObject.toJSONString(employeeIds1));
                employeeIds.addAll(employeeIds1);
            }
            long midTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralLog employeeTime:{}", employeeTime- midTime);
            List<UserEmployeeDO> historyUserEmployees = historyEmployeeListFeature.get();
            if (!StringUtils.isEmptyList(historyUserEmployees)){
                logger.info("ReferralEntity fetchReferralLog historyUserEmployees{}", JSONObject.toJSONString(historyUserEmployees));
                List<Integer> employeeIds2 = historyUserEmployees.stream().filter(f -> companyIds.contains(f.getCompanyId()))
                        .map(m -> m.getId()).collect(Collectors.toList());
                logger.info("ReferralEntity fetchReferralLog employeeIds2{}", JSONObject.toJSONString(employeeIds2));
                employeeIds.addAll(employeeIds2);
            }
            long historyUserEmployeeTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralLog historyUserEmployeeTime:{}", historyUserEmployeeTime- employeeTime);
            logger.info("ReferralEntity fetchReferralLog employeeIds:{}", JSONObject.toJSONString(employeeIds));

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw CommonException.PROGRAM_EXCEPTION;
        }
        List<ReferralLog> logList = new ArrayList<>();
        if(!StringUtils.isEmptyList(logs) && !StringUtils.isEmptyList(employeeIds)){
            Set<Integer> idList = new HashSet<>();
            for (ReferralLog log :logs){
                if(!idList.contains(log.getEmployeeId()) && employeeIds.contains(log.getEmployeeId())){
                    logList.add(log);
                    idList.add(log.getEmployeeId());
                }
            }
        }
        logger.info("ReferralEntity fetchReferralLog logList:{}", JSONObject.toJSONString(logList));
        long endTime = System.currentTimeMillis();
        logger.info("profile tab fetchReferralLog endTime:{}", endTime- startTime);
        return logList;
    }

    public ReferralProfileData fetchReferralProfileData(List<ReferralLog> logs){
        ReferralProfileData data = new ReferralProfileData();
        if(StringUtils.isEmptyList(logs)){
            logger.info("ReferralServiceImpl getReferralProfileTabList logs is null");
            return null;
        }
        long startTime = System.currentTimeMillis();List<Integer> positionIds = logs.stream().map(m -> m.getPositionId()).collect(Collectors.toList());
        List<Integer> empolyeeReferralIds = logs.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
        List<Integer> attementIds = logs.stream().map(m -> m.getAttementId()).collect(Collectors.toList());
        Future<List<ProfileAttachmentDO>> attachmentListFuture = threadPool.startTast(
                () -> attachmentDao.fetchAttachmentByIds(attementIds));
        Future<List<JobPositionDO>> positionListFuture = threadPool.startTast(
                () -> positionDao.getPositionListWithoutStatus(positionIds));
        Future<List<UserEmployeeDO>> empListFuture  = threadPool.startTast(
                () -> employeeDao.getEmployeeByIds(empolyeeReferralIds));
        Future<List<UserEmployeeDO>> historyEmpListFuture  = threadPool.startTast(
                () -> historyUserEmployeeDao.getHistoryEmployeeByIds(empolyeeReferralIds));
        try {
            List<JobPositionDO> positionList = positionListFuture.get();
            logger.info("ReferralServiceImpl getReferralProfileTabList positionList:{}", JSONObject.toJSONString(positionList));
            if(!StringUtils.isEmptyList(positionList)){
                Map<Integer, String> positionTitileMap = new HashMap<>();
                positionList.forEach(position
                        -> positionTitileMap
                        .put(position.getId(),
                                position.getTitle()));
                data.setPositionTitleMap(positionTitileMap);
            }
            long positionListTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralProfileData positionListTime:{}", positionListTime-startTime);
            Map<Integer, String> employeeNameMap = new HashMap<>();
            List<UserEmployeeDO> empList = empListFuture.get();
            logger.info("ReferralServiceImpl getReferralProfileTabList empList:{}", JSONObject.toJSONString(empList));
            if(!StringUtils.isEmptyList(empList)){
                empList.forEach( employee ->
                    employeeNameMap.put(employee.getId(), employee.getCname())
                );
            }
            long empTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralProfileData empTime:{}", empTime- positionListTime);
            List<UserEmployeeDO> historyEmpList = historyEmpListFuture.get();
            logger.info("ReferralServiceImpl getReferralProfileTabList historyEmpList:{}", JSONObject.toJSONString(historyEmpList));
            if(!StringUtils.isEmptyList(historyEmpList)){
                historyEmpList.forEach(employee ->
                    employeeNameMap.put(employee.getId(), employee.getCname())
                );
            }
            data.setEmployeeNameMap(employeeNameMap);
            long hisEmpTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralProfileData empTime:{}", hisEmpTime - empTime);
            List<ProfileAttachmentDO> attachmentList = attachmentListFuture.get();
            logger.info("ReferralServiceImpl getReferralProfileTabList attachmentList:{}", JSONObject.toJSONString(attachmentList));
            long attachmentTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralProfileData attachmentTime:{}", attachmentTime - empTime);
            if(StringUtils.isEmptyList(attachmentList)){
                return null;
            }
            Map<Integer, ProfileAttachmentDO> attachmentMap = new HashMap<>();
            attachmentList.forEach( attac ->
                attachmentMap.put(attac.getId(), attac)
            );
            data.setAttchmentMap(attachmentMap);
            data.setLogList(logs);
            long endTime = System.currentTimeMillis();
            logger.info("profile tab fetchReferralProfileData endTime:{}", endTime- attachmentTime);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        return data;

    }

    public List<ReferralRecomEvaluationRecord> fetchEvaluationListByUserId(int userId, List<Integer> appidList){
        return recomEvaluationDao.getEvaluationListByUserId(userId, appidList);
    }

   public EmployeeRadarData fetchEmployeeRadarData(List<ReferralEmployeeNetworkResourcesRecord> records, int postUserId, int companyId){
        List<Integer> userIdList = records.stream().map(m -> m.getPresenteeUserId()).collect(Collectors.toList());
        List<Integer> positionIdList = records.stream().map(m -> m.getPositionId()).collect(Collectors.toList());
        EmployeeRadarData data = new EmployeeRadarData();
        if(StringUtils.isEmptyList(userIdList)){
            return data;
        }
        try {Future<List<UserWxUserRecord>> wxUserListFuture = threadPool.startTast(
                    () -> wxEntity.getUserWxUserData(userIdList));
            Future<List<ReferralSeekRecommendRecord>> recommendListFuture = threadPool.startTast(
                    () -> recommendDao.fetchSeekRecommendByPostUserAndPresentee(postUserId, userIdList));
            Future<List<UserUserRecord>> userListFuture = threadPool.startTast(
                    () -> userDao.fetchByIdList(userIdList));
            Future<List<CandidatePositionRecord>> candidatePositionListFuture = threadPool.startTast(
                    () -> candidatePositionDao.fetchViewedByUserIdsAndPidList(userIdList, positionIdList));

            Map<Integer, Timestamp> timeMap = new HashMap<>();
            Set<Integer> recommendUserSet = new HashSet<>();
            Map<Integer, Integer> recommendMap = new HashMap<>();
            Map<Integer, Integer> positionView = new HashMap<>();
            Map<Integer, Integer> positionIdMap = new HashMap<>();
            List<CandidatePositionRecord> candidatePositionList = candidatePositionListFuture.get();
            logger.info("fetchEmployeeRadarData candidatePositionList:{}", candidatePositionList);
            if(!StringUtils.isEmptyList(candidatePositionList)) {
                for (CandidatePositionRecord candidatePosition : candidatePositionList) {
                    for (ReferralEmployeeNetworkResourcesRecord record : records){
                        if (candidatePosition.getUserId().intValue() == record.getPresenteeUserId().intValue()
                                && candidatePosition.getPositionId().intValue() == record.getPositionId().intValue()){
                            timeMap.put(record.getPresenteeUserId(), candidatePosition.getUpdateTime());
                            positionView.put(record.getPresenteeUserId(), candidatePosition.getViewNumber());
                            positionIdMap.put(record.getPresenteeUserId(), record.getPositionId());
                        }
                    }
                }
            }
            logger.info("fetchEmployeeRadarData positionId:{}, userIdList:{}", positionIdList, userIdList);
            Future<List<CandidateShareChainDO>> shareChainListFuture = threadPool.startTast(
                    () -> shareChainDao.getShareChainByPositionAndPresenteeOrderTime(positionIdList, userIdList, postUserId));
            Future<List<JobPositionDO>> positionListFuture =  threadPool.startTast(
                    () -> positionDao.getPositionListWithoutStatus(positionIdList));
            Map<Integer, Integer> root2Map = new HashMap<>();
            Set<Integer> root2Set = new HashSet<>();
            List<CandidateShareChainDO> shareChainList = shareChainListFuture.get();
            logger.info("fetchEmployeeRadarData shareChainList:{}", shareChainList);
            List<Integer> shareChainIdList = new ArrayList<>();
            Map<Integer, Integer> shareChainIdMap = new HashMap<>();
            Map<Integer, Byte> userFromMap = new HashMap<>();
            if(!StringUtils.isEmptyList(shareChainList)) {
                shareChainList.forEach(share -> {
                    for(ReferralEmployeeNetworkResourcesRecord record : records) {
                        if (record.getPositionId().intValue() == share.getPositionId()
                                && record.getPresenteeUserId() == share.getPresenteeUserId()
                                && root2Map.get(share.getPresenteeUserId()) == null) {
                            root2Map.put(share.getPresenteeUserId(), share.getRoot2RecomUserId());
                            root2Set.add(share.root2RecomUserId);
                            try {
                                timeMap.put(share.getPresenteeUserId(), new Timestamp(DateUtils.shortTimeToDate(share.getClickTime()).getTime()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            shareChainIdMap.put(share.getId(), share.getPresenteeUserId());
                            shareChainIdList.add(share.getId());
                            userFromMap.put(share.getPresenteeUserId(), share.getClickFrom());
                        }
                    }
                });
            }
            logger.info("fetchEmployeeRadarData timeMap:{}", timeMap);
            List<ReferralSeekRecommendRecord>recommendList =recommendListFuture.get();
            if(!StringUtils.isEmptyList(recommendList)){
                recommendList.forEach( recommend -> {
                            if (!recommendUserSet.contains(recommend.getPresenteeId())) {
                                recommendUserSet.add(recommend.getPresenteeId());
                                recommendMap.put(recommend.getPresenteeId(), recommend.getId());
                                timeMap.put(recommend.getPresenteeId(), recommend.getRecommendTime());
                            }
                        }
                );
            }
            logger.info("fetchEmployeeRadarData timeMap:{}", timeMap);
            Future<List<UserUserRecord>> root2ListFuture = threadPool.startTast(
                    () -> userDao.fetchByIdList(new ArrayList<>(root2Set)));
            if (!StringUtils.isEmptyList(wxUserListFuture.get())){
                Map<Integer, UserWxUserRecord> wxUserRecordMap = new HashMap<>();
                wxUserListFuture.get().forEach(wx -> wxUserRecordMap.put(wx.getSysuserId(), wx));
                data.setWxUserRecordList(wxUserRecordMap);
            }
            Map<Integer, UserUserRecord> userMap = new HashMap<>();
            if(!StringUtils.isEmptyList(userListFuture.get())){
                userListFuture.get().forEach(fe -> userMap.put(fe.getId(), fe));
            }
            data.setTimeMap(timeMap);
            data.setUserRecordList(userMap);
            Map<Integer, JobPositionDO> positionMap = new HashMap<>();
            logger.info("fetchEmployeeRadarData candidatePositionList:{}", positionListFuture.get());
            if(!StringUtils.isEmptyList(positionListFuture.get())){
                positionListFuture.get().forEach(position ->positionMap.put(position.getId(), position));
            }
            Set<Map.Entry<Integer, Integer>> entries = positionIdMap.entrySet();
            Map<Integer, JobPositionDO> userPositionMap = new HashMap<>();
            for(Map.Entry<Integer, Integer> entry : entries){
                userPositionMap.put(entry.getKey(), positionMap.get(entry.getValue()));
            }
            data.setPositionMap(userPositionMap);
            data.setPositionView(positionView);
            data.setRecommendUserSet(recommendUserSet);
            Map<Integer, UserUserRecord> root2UserMap = new HashMap<>();
            if(!StringUtils.isEmptyList(root2ListFuture.get())){
                root2ListFuture.get().forEach(root2 ->root2UserMap.put(root2.getId(), root2));
            }
            Set<Map.Entry<Integer, Integer>> root2Entries = root2Map.entrySet();
            Map<Integer, UserUserRecord> userRoot2Map = new HashMap<>();
            for(Map.Entry<Integer, Integer> entry : root2Entries){
                userRoot2Map.put(entry.getKey(), root2UserMap.get(entry.getValue()));
            }
            data.setRoot2UserMap(userRoot2Map);
//            Map<Integer, Byte> fromMap = new HashMap<>();
//            if(!StringUtils.isEmptyList(positionShareRecordListFuture.get())){
//                positionShareRecordListFuture.get().forEach(fe -> {
//                    if (fromMap.get(fe.getShareChainId()) == null)
//                        fromMap.put(fe.getShareChainId(), fe.getClickFrom());
//                    }
//                );
//            }
//            Set<Map.Entry<Integer, Byte>> fromMapSet = fromMap.entrySet();
//            Map<Integer, Byte> userFromMap = new HashMap<>();
//            for(Map.Entry<Integer, Byte> entry : fromMapSet){
//                userFromMap.put(shareChainIdMap.get(entry.getValue()), entry.getValue());
//            }
            data.setUserFromMap(userFromMap);
            data.setRecommendMap(recommendMap);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        logger.info("fetchEmployeeRadarData data:{}", data);
        return data;
    }

    public List<ReferralSeekRecommendRecord> fetchSeekRecommendByPostUserId(int postUserId, List<Integer> positionIds, List<Integer> presenteeUserIds){
        return recommendDao.fetchSeekRecommendByPostAndPressentee(postUserId, positionIds, presenteeUserIds);
    }


    public EmployeeCardViewData fetchEmployeeViewCardData(List<CandidateRecomRecordRecord> recomRecordList, int postUserId, int companyId){
        long startTime = System.currentTimeMillis();
        EmployeeCardViewData data = new EmployeeCardViewData();
        if(StringUtils.isEmptyList(recomRecordList)){
            return data;
        }
        List<Integer> userIdList = new ArrayList<>();
        List<Integer> positionIdList = new ArrayList<>();
        recomRecordList.forEach(record ->{
            userIdList.add(record.getPresenteeUserId());
            positionIdList.add(record.getPositionId());
        });

        try {
            Future<List<CandidateShareChainDO>> shareChainListFuture = threadPool.startTast(
                    () -> shareChainDao.getShareChainByPositionAndPresenteeOrderTime(positionIdList, userIdList, postUserId));
            Future<List<ReferralConnectionLogRecord>> connectionLogListFuture = threadPool.startTast(
                    () -> connectionLogDao.fetchChainLogRecordByList(postUserId, userIdList, positionIdList));
            Future<List<UserWxUserRecord>> wxUserListFuture = threadPool.startTast(
                    () -> wxEntity.getUserWxUserData(userIdList));
            Future<List<UserUserRecord>> userListFuture = threadPool.startTast(
                    () -> userDao.fetchByIdList(userIdList));
            Future<List<CandidatePositionRecord>> candidatePositionListFuture = threadPool.startTast(
                    () -> candidatePositionDao.fetchRecentViewedByUserIdAndPosition(userIdList, positionIdList));
            Future<List<JobPositionDO>> positionListFuture =  threadPool.startTast(
                    () -> positionDao.getPositionListByIdList(positionIdList));
            Set<Integer> root2Set = new HashSet<>();
            List<CandidateShareChainDO> shareChainList = new ArrayList<>();
            List<Integer> shareChainIdList = new ArrayList<>();
            Map<Integer, Byte> fromMap = new HashMap<>();
            long futureTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView futureTime:{}", futureTime- startTime);
            List<CandidateShareChainDO> shareChainDOS = shareChainListFuture.get();
            if(!StringUtils.isEmptyList(shareChainDOS)) {
                for(CandidateRecomRecordRecord recom : recomRecordList){
                    for(CandidateShareChainDO share : shareChainDOS) {
                        if(recom.getPresenteeUserId().intValue() == share.getPresenteeUserId()
                                && recom.getPositionId().intValue() == share.getPositionId()){
                            root2Set.add(share.root2RecomUserId);
                            shareChainIdList.add(share.getId());
                            shareChainList.add(share);
                            fromMap.put(share.getId(),share.getClickFrom());
                            break;
                        }
                    }
                }
            }
            logger.info("fetchEmployeeForwardView shareChainIdList:{}", shareChainIdList);
            long shareChainTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView shareChainTime:{}", shareChainTime - futureTime);
            Future<List<UserUserRecord>> root2ListFuture = threadPool.startTast(
                    () -> userDao.fetchByIdList(new ArrayList<>(root2Set)));
            data.setShareChainList(shareChainList);
            List<ReferralConnectionLogRecord> connectionLogList = new ArrayList<>();
            long future2Time = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView futureTime:{}", future2Time -shareChainTime);
            if(!StringUtils.isEmptyList(connectionLogListFuture.get())){
                for(CandidateRecomRecordRecord record :recomRecordList){
                    for(ReferralConnectionLogRecord logRecord: connectionLogListFuture.get()){
                        if(record.getPositionId().intValue() == logRecord.getPositionId().intValue()
                                && record.getPresenteeUserId().intValue() == logRecord.getEndUserId().intValue()){
                            connectionLogList.add(logRecord);
                            break;
                        }
                    }
                }
                data.setConnectionLogList(connectionLogList);
            }
            long connectionTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView connectionTime:{}", connectionTime- future2Time);
            if (!StringUtils.isEmptyList(wxUserListFuture.get())){
                Map<Integer, UserWxUserRecord> wxUserRecordMap = new HashMap<>();
                wxUserListFuture.get().forEach(wx -> wxUserRecordMap.put(wx.getSysuserId(), wx));
                data.setWxUserRecordList(wxUserRecordMap);
            }
            long wxUserTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView wxUserTime:{}", wxUserTime- connectionTime);
            Map<Integer, UserUserRecord> userMap = new HashMap<>();
            if(!StringUtils.isEmptyList(userListFuture.get())){
                userListFuture.get().forEach(fe ->userMap.put(fe.getId(), fe) );
            }
            long userTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView userTime:{}", userTime- wxUserTime);
            data.setUserRecordList(userMap);
            Map<Integer, JobPositionDO> positionMap = new HashMap<>();
            if(!StringUtils.isEmptyList(positionListFuture.get())){
                positionListFuture.get().forEach(position ->positionMap.put(position.getId(), position));
            }
            long positionTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView positionTime:{}", positionTime- userTime);
            data.setPositionMap(positionMap);
            data.setCandidatePositionRecords(candidatePositionListFuture.get());
            Map<Integer, UserUserRecord> root2UserMap = new HashMap<>();
            if(!StringUtils.isEmptyList(root2ListFuture.get())){
                root2ListFuture.get().forEach(root2 ->root2UserMap.put(root2.getId(), root2));
            }
            long candidateTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView candidateTime:{}", candidateTime- positionTime);
            data.setRoot2UserMap(root2UserMap);
            long positionshareTime = System.currentTimeMillis();
            logger.info("fetchEmployeeForwardView positionshareTime:{}", positionshareTime- candidateTime);
            data.setUserFromMap(fromMap);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return data;
    }

    public EmployeeCardViewData fetchEmployeeSeekRecommendCardData(List<ReferralSeekRecommendRecord> recomRecordList, int postUserId, int companyId){
        EmployeeCardViewData data = new EmployeeCardViewData();
        if(StringUtils.isEmptyList(recomRecordList)){
            return data;
        }
        List<Integer> userIdList = new ArrayList<>();
        List<Integer> positionIdList = new ArrayList<>();
        recomRecordList.forEach(record ->{
            userIdList.add(record.getPresenteeId());
            positionIdList.add(record.getPositionId());
        });
        try {
            Future<List<CandidateShareChainDO>> shareChainListFuture = threadPool.startTast(
                    () -> shareChainDao.getShareChainByPositionAndPresenteeOrderTime(positionIdList, userIdList, postUserId));
            Future<List<UserWxUserRecord>> wxUserListFuture = threadPool.startTast(
                    () -> wxEntity.getUserWxUserData(userIdList));
            Future<List<UserUserRecord>> userListFuture = threadPool.startTast(
                    () -> userDao.fetchByIdList(userIdList));
            Future<List<CandidatePositionRecord>> candidatePositionListFuture = threadPool.startTast(
                    () -> candidatePositionDao.fetchRecentViewedByUserIdAndPosition(userIdList, positionIdList));
            Future<List<JobPositionDO>> positionListFuture =  threadPool.startTast(
                    () -> positionDao.getPositionListByIdList(positionIdList));
            Set<Integer> root2Set = new HashSet<>();
            List<CandidateShareChainDO> shareChainList = new ArrayList<>();
            List<Integer> shareChainIdList = new ArrayList<>();
            Map<Integer, Byte> fromMap = new HashMap<>();
            if(!StringUtils.isEmptyList(shareChainListFuture.get())) {
                shareChainListFuture.get().forEach(share -> {
                    recomRecordList.forEach( recom ->{
                        if(recom.getPresenteeId() == share.getPresenteeUserId() && recom.getPositionId() == share.getPositionId()){
                            root2Set.add(share.root2RecomUserId);
                            shareChainIdList.add(share.getId());
                            shareChainList.add(share);
                            fromMap.put(share.getId(),share.getClickFrom());
                        }
                    });

                });
            }
            Future<List<UserUserRecord>> root2ListFuture = threadPool.startTast(
                    () -> userDao.fetchByIdList(new ArrayList<>(root2Set)));
            data.setShareChainList(shareChainList);
            if (!StringUtils.isEmptyList(wxUserListFuture.get())){
                Map<Integer, UserWxUserRecord> wxUserRecordMap = new HashMap<>();
                wxUserListFuture.get().forEach(wx -> wxUserRecordMap.put(wx.getSysuserId(), wx));
                data.setWxUserRecordList(wxUserRecordMap);
            }
            Map<Integer, UserUserRecord> userMap = new HashMap<>();
            if(!StringUtils.isEmptyList(userListFuture.get())){
                userListFuture.get().forEach(fe ->userMap.put(fe.getId(), fe) );
            }
            data.setUserRecordList(userMap);
            Map<Integer, JobPositionDO> positionMap = new HashMap<>();
            if(!StringUtils.isEmptyList(positionListFuture.get())){
                positionListFuture.get().forEach(position ->positionMap.put(position.getId(), position));
            }
            data.setPositionMap(positionMap);
            data.setCandidatePositionRecords(candidatePositionListFuture.get());
            Map<Integer, UserUserRecord> root2UserMap = new HashMap<>();
            if(!StringUtils.isEmptyList(root2ListFuture.get())){
                root2ListFuture.get().forEach(root2 ->root2UserMap.put(root2.getId(), root2));
            }
            data.setRoot2UserMap(root2UserMap);
            data.setUserFromMap(fromMap);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return data;
    }



    public List<ReferralSeekRecommendRecord> fetchEmployeeSeekRecommend(int postUserId, List<Integer> positionIds, Set<Integer> presenteeUserIdList){
        List<ReferralSeekRecommendRecord> list =  recommendDao.fetchSeekRecommendByPost(postUserId, positionIds, presenteeUserIdList);
        if(!StringUtils.isEmptyList(list)) {
            List<Integer> positionIdList = new ArrayList<>();
            List<Integer> presenteeIdList = new ArrayList<>();
            list.forEach(m -> {
                positionIdList.add(m.getPositionId());
                presenteeIdList.add(m.getPresenteeId());
            });
            List<JobApplicationDO> applications = applicationDao.getApplyByaApplierAndPositionIds(positionIds, presenteeIdList);
            List<ReferralSeekRecommendRecord> records = new ArrayList<>();
            if(!StringUtils.isEmptyList(applications)){
                for(ReferralSeekRecommendRecord recommendRecord : list){
                    boolean status = true;
                    for(JobApplicationDO application :applications){
                        if(application.getPositionId()==recommendRecord.getPositionId().intValue() &&
                                application.getApplierId() == recommendRecord.getPresenteeId().intValue()){
                            status=false;
                            break;
                        }
                    }
                    if (status) {
                        records.add(recommendRecord);
                    }
                }
                return records;
            }
        }
        return list;
    }

    @Transactional
    public void fetchEmployeeNetworkResource(Object message){
        if(message != null) {
            String messageStr = (String)message;
            KafkaNetworkResource resource = JSONObject.parseObject(messageStr, KafkaNetworkResource.class);
            logger.info("fetchEmployeeNetworkResource resource:{}",JSON.toJSONString(resource));
            if (resource != null && !StringUtils.isEmptyList(resource.getData())){
                List<ReferralEmployeeNetworkResourcesRecord> list = networkResourcesDao.fetchByPostUserId(resource.getEmployee_id());
                List<ReferralEmployeeNetworkResourcesRecord> updateRecordList = new ArrayList<>();
                List<ReferralEmployeeNetworkResourcesRecord> insertRecordList = new ArrayList<>();
                int num = list.size()>resource.getData().size()?list.size():resource.getData().size();
                logger.info("fetchEmployeeNetworkResource num:{}",num);
                for(int i =0; i<num;i++){
                    if(i < list.size()) {
                    logger.info("fetchEmployeeNetworkResource i:{}",i);
                        ReferralEmployeeNetworkResourcesRecord record = list.get(i);
                        if (resource.getData().size() > i) {
                            record.setDisable((byte) Constant.DISABLE);
                            record.setPresenteeUserId(resource.getData().get(i).getUser_id());
                            record.setPositionId(resource.getData().get(i).getPosition_id());
                            record.setCompanyId(resource.getCompany_id());
                        } else {
                            record.setDisable((byte) Constant.ENABLE);
                        }
                        updateRecordList.add(record);
                    }else {
                        ReferralEmployeeNetworkResourcesRecord record = new ReferralEmployeeNetworkResourcesRecord();
                        record.setPostUserId(resource.getEmployee_id());
                        record.setPresenteeUserId(resource.getData().get(i).getUser_id());
                        record.setPositionId(resource.getData().get(i).getPosition_id());
                        record.setCompanyId(resource.getCompany_id());
                        insertRecordList.add(record);
                    }
                }
                logger.info("fetchEmployeeNetworkResource updateRecordList:{}",updateRecordList);
                networkResourcesDao.updateReferralEmployeeNetworkResourcesRecord(updateRecordList);
                logger.info("fetchEmployeeNetworkResource insertRecordList:{}",insertRecordList);
                networkResourcesDao.insertReferralEmployeeNetworkResourcesRecord(insertRecordList);
            }
        }
    }

}
