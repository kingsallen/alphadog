package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.retriveprofile.parameters.ApplicationTaskParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 处理申请业务
 * Created by jack on 10/07/2017.
 */
@Component
public class ApplicationTask implements Task<ApplicationTaskParam, Integer> {

    // 申请次数redis key
    private static final String REDIS_KEY_APPLICATION_COUNT_CHECK = "APPLICATION_COUNT_CHECK";

    // 申请次数限制 3次
    private static final int APPLICATION_COUNT_LIMIT = 3;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JobApplicationDao jobApplicationDao;

    @Autowired
    HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    JobPositionDao positionDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    public Integer handler(ApplicationTaskParam param) throws CommonException {

        JobPositionRecord positionRecord = positionDao.getPositionById(param.getPositionId());
        if (positionRecord == null || positionRecord.getStatus() != 0) {
            throw ExceptionFactory.buildException(Category.PROFILE_POSITION_NOTEXIST);
        }

        if (!checkoutApplyLimit(param.getUserId(), positionRecord.getCompanyId())) {
            throw ExceptionFactory.buildException(Category.VALIDATION_APPLICATION_UPPER_LIMIT);
        }

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("position_id", positionRecord.getId()).and("applier_id", param.getUserId());
        JobApplicationRecord applicationRecord = jobApplicationDao.getRecord(queryBuilder.buildQuery());
        if (applicationRecord == null) {
            applicationRecord = initApplication(param.getUserId(), positionRecord.getId(),
                    positionRecord.getCompanyId());
            applicationRecord = jobApplicationDao.addRecord(applicationRecord);
            redisClient.incr(Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(applicationRecord.getApplierId()), String.valueOf(applicationRecord.getCompanyId()));
            logger.info("ApplicationTask 简历回收 生成申请信息。 id:{}", applicationRecord.getId());
            //是否需要用户申请次数。这回关系到用户投递这家公司的次数限制
        } else {
            //do nothing
        }
        return applicationRecord.getId();
    }

    /**
     * 初始化一个申请记录
     * @param applierId 申请编号
     * @param positionId 职位编号
     * @param companyId 公司编号
     * @return
     */
    private JobApplicationRecord initApplication(int applierId, int positionId, int companyId) {
        JobApplicationRecord jobApplicationRecord = new JobApplicationRecord();
        jobApplicationRecord.setApplierId(applierId);
        jobApplicationRecord.setPositionId(positionId);
        jobApplicationRecord.setCompanyId(companyId);
        jobApplicationRecord.setAppTplId(Constant.RECRUIT_STATUS_APPLY);
        return jobApplicationRecord;
    }

    /**
     * 检查是否达到投递上线
     * @param userId
     * @param companyId
     * @return
     */
    private boolean checkoutApplyLimit(int userId, int companyId) {
        try {
            String applicationCountCheck = redisClient.get(
                    Constant.APPID_ALPHADOG, REDIS_KEY_APPLICATION_COUNT_CHECK,
                    String.valueOf(userId), String.valueOf(companyId));
            logger.info("checkoutApplyLimit applicationCountCheck:{}", applicationCountCheck);
            // 超出申请次数限制, 每月每家公司一个人只能申请3次
            if (applicationCountCheck == null
                    || Integer.valueOf(applicationCountCheck) < getApplicationCountLimit(companyId)) {
                return true;
            }
        } catch (RedisException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取申请限制次数
     * 默认3次
     * 企业有自己的配置,使用企业的配置
     *
     * @param companyId 公司ID
     */
    private int getApplicationCountLimit(int companyId) {
        int applicaitonCountLimit = APPLICATION_COUNT_LIMIT;
        Query query=new Query.QueryBuilder().where("company_id", companyId).buildQuery();
        HrCompanyConfRecord hrCompanyConfRecord =hrCompanyConfDao.getRecord(query);
        if (hrCompanyConfRecord != null && hrCompanyConfRecord.getApplicationCountLimit().shortValue() > 0) {
            applicaitonCountLimit = hrCompanyConfRecord.getApplicationCountLimit().shortValue();
        }
        logger.info("getApplicationCountLimit applicaitonCountLimit:{}", applicaitonCountLimit);
        return applicaitonCountLimit;
    }
}
