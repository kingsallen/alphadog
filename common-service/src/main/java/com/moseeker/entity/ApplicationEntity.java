package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.pojo.ApplicationSaveResultVO;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.biztools.ApplyType;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.Constant.ApplicationSource;
import com.moseeker.entity.application.UserApplyCount;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.apache.commons.lang.StringUtils;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private HrOperationRecordDao hrOperationRecordDao;

    @Autowired
    private PositionEntity positionEntity;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    // 申请次数redis key
    private static final String REDIS_KEY_APPLICATION_COUNT_CHECK = "APPLICATION_COUNT_CHECK";
    // 申请次数限制 3次
    private static final int APPLICATION_COUNT_LIMIT = 3;

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
            jobApplicationRecord.setAppTplId(Constant.RECRUIT_STATUS_UPLOAD_PROFILE);
            jobApplicationRecord.setCompanyId(companyId);
            jobApplicationRecord.setAtsStatus(0);
            jobApplicationRecord.setRecommenderUserId(referenceId);
            jobApplicationRecord.setPositionId(positionId);
            jobApplicationRecord.setApplierId(applierId);
            jobApplicationRecord.setOrigin(ApplicationSource.EMPLOYEE_CHATBOT.getValue());

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

    @Transactional(rollbackFor = Exception.class)
    public int applyByReferral(UserUserRecord userRecord, int position, UserEmployeeDO employeeDO) throws CommonException {
        try{
            JobPositionRecord positionRecord = positionEntity.getPositionByID(position);
            if (positionRecord == null || positionRecord.getStatus() != PositionStatus.ACTIVED.getValue()) {
                throw ApplicationException.APPLICATION_POSITION_NOTEXIST;
            }

            List<Integer> companyIdList = employeeEntity.getCompanyIds(employeeDO.getCompanyId());
            if (!companyIdList.contains(positionRecord.getCompanyId())) {
                throw ApplicationException.NO_PERMISSION_EXCEPTION;
            }

            checkApplicationCountAtCompany(userRecord.getId(), employeeDO.getCompanyId(), positionRecord.getCandidateSource());

            JobApplicationRecord jobApplicationRecord = initApplicationInfo(positionRecord, userRecord, employeeDO.getCompanyId(),
                    employeeDO, Constant.RECRUIT_STATUS_APPLY, ApplicationSource.EMPLOYEE_REFERRAL);

            ApplicationSaveResultVO resultVO = applicationDao.addIfNotExists(jobApplicationRecord);
            if (!resultVO.isCreate()) {
                HrOperationRecordRecord hrOperationRecord = getHrOperationRecordRecord(resultVO.getApplicationId(), jobApplicationRecord, positionRecord);
                hrOperationRecordDao.addRecord(hrOperationRecord);

                addApplicationCountAtCompany(userRecord.getId(), positionRecord.getCompanyId(), positionRecord.getCandidateSource());
            }
            return resultVO.getApplicationId();
        }catch (Exception e){
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.APPLICATION_SINGLETON.toString(),
                    userRecord.getId() + "", position + "");
            throw e;
        }
    }

    /**
     * 初始化申请信息
     * @param position 职位信息
     * @param userRecord 用户信息
     * @param companyId 公司编号
     * @param employeeDO 员工信息
     * @param recruitStatusApply 申请状态
     * @param applicationSource 申请来源
     * @return
     */
    private JobApplicationRecord initApplicationInfo(JobPositionRecord position, UserUserRecord userRecord, int companyId,
                                                     UserEmployeeDO employeeDO, int recruitStatusApply,
                                                     ApplicationSource applicationSource) {

        JobApplicationRecord applicationRecord = new JobApplicationRecord();
        applicationRecord.setPositionId(position.getId());
        applicationRecord.setApplierId(userRecord.getId());
        String applierName = StringUtils.isNotBlank(userRecord.getName())?userRecord.getName():userRecord.getNickname();
        applicationRecord.setApplierName(applierName);
        applicationRecord.setCompanyId(companyId);
        applicationRecord.setAppTplId(recruitStatusApply);
        applicationRecord.setApplyType(ApplyType.PROFILE.getValue());
        if (employeeDO != null && employeeDO.getSysuserId() != userRecord.getId()) {
            applicationRecord.setRecommenderUserId(employeeDO.getSysuserId());
        }
        applicationRecord.setOrigin(applicationSource.getValue());
        return applicationRecord;
    }

    /**
     * 获取用户当前申请数据
     * @param userId 用户编号
     * @param companyId 公司编号
     * @return 用户申请数据
     */
    public UserApplyCount getApplyCount(long userId, long companyId) {
        String applicationCountCheck = redisClient.get(
                Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(userId), String.valueOf(companyId));
        return UserApplyCount.initFromRedis(applicationCountCheck);
    }

    /**
     * 一个用户在一家公司的每月的申请次数校验 超出申请次数限制, 每月每家公司一个人只能申请10次 <p>
     *  @param userId    用户id
     * @param companyId 公司id
     * @param candidateSource
     */
    public void checkApplicationCountAtCompany(long userId, long companyId, byte candidateSource) throws ApplicationException {

        UserApplyCount userApplyCount = getApplyCount(userId, companyId);
        logger.info("userApplyCount使用校招参数：{};社招参数:{}", userApplyCount.getSchoolApplyCount(), userApplyCount.getSocialApplyCount());
        UserApplyCount conf = getApplicationCountLimit((int) companyId);
        logger.info("userApplyCount参数校招参数：{};社招参数:{}", userApplyCount.getSchoolApplyCount(), userApplyCount.getSocialApplyCount());
        if (candidateSource == 0) {
            if (userApplyCount.getSocialApplyCount() >= conf.getSocialApplyCount()) {
                throw ApplicationException.APPLICATION_VALIDATE_SOCIAL_COUNT_CHECK;
            }
        } else {
            if (userApplyCount.getSchoolApplyCount() >= conf.getSchoolApplyCount()) {
                throw ApplicationException.APPLICATION_VALIDATE_SCHOOL_COUNT_CHECK;
            }
        }
    }

    /**
     * 获取申请限制次数 默认3次 企业有自己的配置,使用企业的配置
     *
     * @param companyId 公司ID
     */
    public UserApplyCount getApplicationCountLimit(int companyId) {
        UserApplyCount userApplyCount = new UserApplyCount();

        userApplyCount.setSocialApplyCount(APPLICATION_COUNT_LIMIT);
        userApplyCount.setSchoolApplyCount(APPLICATION_COUNT_LIMIT);

        Query query = new Query.QueryBuilder().where("company_id", companyId).buildQuery();
        HrCompanyConfRecord hrCompanyConfRecord = hrCompanyConfDao.getRecord(query);
        if (hrCompanyConfRecord != null) {
            if (hrCompanyConfRecord.getApplicationCountLimit()  > 0) {
                userApplyCount.setSocialApplyCount(hrCompanyConfRecord.getApplicationCountLimit());
            }
            if (hrCompanyConfRecord.getSchoolApplicationCountLimit() > 0) {
                userApplyCount.setSchoolApplyCount(hrCompanyConfRecord.getSchoolApplicationCountLimit());
            }
        }
        logger.info("JobApplicataionService getApplicationCountLimit applicaitonCountLimit:{}", userApplyCount);
        return userApplyCount;
    }

    //构建hr操作记录record
    public HrOperationRecordRecord getHrOperationRecordRecord(long appId,
                                                               JobApplicationRecord jobApplicationRecord,
                                                               JobPositionRecord JobPositonrecord) {
        HrOperationRecordRecord hrOperationRecordRecord = new HrOperationRecordRecord();
        hrOperationRecordRecord.setAdminId(JobPositonrecord.getPublisher().longValue());
        hrOperationRecordRecord.setCompanyId(jobApplicationRecord.getCompanyId().longValue());
        hrOperationRecordRecord.setAppId(appId);
        hrOperationRecordRecord.setOperateTplId(jobApplicationRecord.getAppTplId());
        return hrOperationRecordRecord;
    }

    /**
     * 添加申请数据
     * @param applierId 申请人
     * @param companyId 公司
     * @param candidateSource 职位类型（社招或校招）
     */
    public void addApplicationCountAtCompany(int applierId, int companyId, byte candidateSource) {

        String applicationCountCheck = redisClient.get(Constant.APPID_ALPHADOG,
                REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(applierId),
                String.valueOf(companyId));
        // 获取当前申请次数 +1

        UserApplyCount userApplyCount = UserApplyCount.initFromRedis(applicationCountCheck);
        if (candidateSource == 0) {
            userApplyCount.setSocialApplyCount(userApplyCount.getSocialApplyCount()+1);
        } else {
            userApplyCount.setSchoolApplyCount(userApplyCount.getSchoolApplyCount()+1);
        }
        redisClient.set(Constant.APPID_ALPHADOG,
                REDIS_KEY_APPLICATION_COUNT_CHECK,
                String.valueOf(applierId),
                String.valueOf(companyId),
                JSON.toJSONString(userApplyCount),
                (int) DateUtils.calcCurrMonthSurplusSeconds());
    }
}
