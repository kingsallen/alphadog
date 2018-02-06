package com.moseeker.application.infrastructure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.application.domain.ApplicationBatchEntity;
import com.moseeker.application.domain.HREntity;
import com.moseeker.application.domain.component.state.ApplicationStateRoute;
import com.moseeker.application.domain.ApplicationEntity;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import org.jooq.Configuration;
import org.jooq.Record2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jack on 16/01/2018.
 */
@Component
public class ApplicationRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Configuration configuration;
    private JobApplicationJOOQDao jobApplicationDao;
    private UserHRAccountJOOQDao userHrAccountDao;
    private JobPositionJOOQDao positionJOOQDao;
    private HrOperationJOOQDao hrOperationJOOQDao;
    private HrCompanyJOOQDao companyJOOQDao;
    private WechatJOOQDao wechatJOOQDao;

    /**
     * es数据模板，tableName: 表名， user_id: 用户id
     */
    private final String ms = "{'tableName':'job_application'}";

    @Resource(name = "cacheClient")
    private RedisClient client;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    public ApplicationRepository(Configuration configuration) {
        this.configuration = configuration;
        jobApplicationDao = new JobApplicationJOOQDao(configuration);
        userHrAccountDao = new UserHRAccountJOOQDao(configuration);
        positionJOOQDao = new JobPositionJOOQDao(configuration);
        hrOperationJOOQDao = new HrOperationJOOQDao(configuration);
        companyJOOQDao = new HrCompanyJOOQDao(configuration);
        wechatJOOQDao = new WechatJOOQDao(configuration);
    }

    public JobApplicationJOOQDao getJobApplicationDao() {
        return jobApplicationDao;
    }

    public UserHRAccountJOOQDao getUserHrAccountDao() {
        return userHrAccountDao;
    }

    public JobPositionJOOQDao getPositionJOOQDao() {
        return positionJOOQDao;
    }

    /**
     * 查找申请投递的职位名称
     * @param applicationIdList 申请编号
     * @return 申请对应的职位名称 key 申请编号 value 职位名称
     */
    public Map<Integer, String> getPositionName(List<Integer> applicationIdList) {

        Map<Integer, String> positionNameMap= new HashMap<>();

        List<Record2<Integer, Integer>> record1List = jobApplicationDao.fetchPositionIdsByIdList(applicationIdList);
        if (record1List != null) {

            List<Integer> positionIdList = record1List.stream().map(map -> map.value2()).collect(Collectors.toList());
            List<Record2<Integer, String>> positionList = positionJOOQDao.fetchPositionNamesByIdList(positionIdList);

            if (positionList != null) {
                record1List.stream().forEach(record2 -> {
                    Optional<Record2<Integer, String>> optional= positionList
                            .stream()
                            .filter(p -> record2.value2().intValue() == p.value1().intValue())
                            .findAny();
                    if (optional.isPresent()) {
                        positionNameMap.put(record2.value1(), optional.get().value2());
                    }
                });
            }
        }
        return positionNameMap;
    }

    /**
     * 查找申请和公司的关系
     * @param applicationIdList 申请编号
     * @return 申请和公司的关系
     */
    public Map<Integer, HrCompany> getCompaniesByApplicationIdList(List<Integer> applicationIdList) {
        List<Record2<Integer, Integer>> appIdCompanyIdList = jobApplicationDao.fetchCompanyIdListByApplicationIdList(applicationIdList);
        List<Integer> companyIdList = appIdCompanyIdList.stream().map(record -> record.value2()).collect(Collectors.toList());
        List<HrCompany> companyList = companyJOOQDao.fetchByIdList(companyIdList);
        Map<Integer, HrCompany> map = new HashMap<>();

        applicationIdList.forEach(appid -> {
            Optional<Record2<Integer, Integer>> optional = appIdCompanyIdList
                    .stream()
                    .filter(ac -> ac.value1().intValue() == appid)
                    .findAny();
            if (optional.isPresent()) {
                Optional<HrCompany> companyOptional = companyList
                        .stream()
                        .filter(hrCompany -> hrCompany.getId().intValue() == optional.get().value2().intValue())
                        .findAny();
                if (companyOptional.isPresent()) {
                    map.put(appid, companyOptional.get());
                }
            }
        });

        return map;
    }

    /**
     * 查找公司signature信息  key是公司编号， value是signature
     * @param companyIdList 公司编号集合
     * @return 公司signature信息集合 key是公司编号， value是signature
     */
    public Map<Integer,String> getSignatureByCompanyId(List<Integer> companyIdList) {
        Map<Integer, String> map = new HashMap<>();
        List<Record2<Integer,String>> result = wechatJOOQDao.getCompanyIdAndSignatureByCompanyId(companyIdList);
        if (result != null) {
            result.forEach(record -> {
                map.put(record.value1(), record.value2());
            });
        }
        return map;
    }

    /**
     * 查找申请人编号
     * @param applicationIdList 申请编号
     * @return 申请人编号
     */
    public Map<Integer,Integer> getAppliers(List<Integer> applicationIdList) {
        Map<Integer,Integer> result = new HashMap<>();
        List<Record2<Integer,Integer>> list = jobApplicationDao.fetchApplierIdListByIdList(applicationIdList);
        if (list != null) {
            list.forEach(record -> {
                result.put(record.value1(), record.value2());
            });
        }
        return result;
    }

    public HREntity fetchHREntity(int hrId) throws CommonException {
        //理论上获取用户信息应该访问用户服务
        UserHrAccount userHrAccount = getUserHrAccountDao().fetchActiveHRByID(hrId);
        if (userHrAccount == null) {
            throw ApplicationException.APPLICATION_HR_ILLEGAL;
        }
        HRAccountType hrAccountType = HRAccountType.initFromType(userHrAccount.getAccountType());
        if (hrAccountType == null) {
            throw ApplicationException.APPLICATION_HR_ACCOUT_TYPE_ILLEGAL;
        }

        return new HREntity(userHrAccount.getId(), hrAccountType, userHrAccount.getCompanyId(),
                this);
    }

    /**
     * 生成申请实体
     * @param applicationIdList 申请编号
     * @return 申请实体
     * @throws CommonException (41014,  "申请信息不正确!" )
     */
    public ApplicationBatchEntity fetchApplicationEntity(List<Integer> applicationIdList) throws CommonException {

        //查找申请记录
        List<JobApplication> applicationList = getJobApplicationDao()
                .fetchActiveApplicationByIdList(applicationIdList);
        if (applicationList == null || applicationList.size() == 0) {
            throw ApplicationException.APPLICATION_APPLICATION_ELLEGAL;
        }
        //查找申请和HR的关系--通过职位信息
        List<JobPosition> positionList = getPositionJOOQDao()
                .fetchPublisherByAppIds(applicationList
                        .stream()
                        .map(jobApplication -> jobApplication.getPositionId())
                        .collect(Collectors.toList()));

        //根据公司编号集合查找超级账号与公司的记录 Record2.value1 公司信息， Record2.value2 HR编号
        List<Record2<Integer, Integer>> superAccountList = userHrAccountDao
                .fetchActiveSuperHRByCompanyIDList(positionList
                        .stream().map(p -> p.getCompanyId())
                        .collect(Collectors.toList()));

        //组合申请数据
        List<ApplicationEntity> applications = packageApplication(applicationList, positionList, superAccountList);

        ApplicationBatchEntity applicationBatchEntity = new ApplicationBatchEntity(this, applications);

        return applicationBatchEntity;
    }

    /**
     * 组合申请数据（申请实体的聚合根）
     *
     * 查看申请需要申请编号，申请当前所处的状态，能够处理申请的HR编号集合。其中申请编号和申请当前所处的状态可以直接从申请记录中获取。
     * 但是申请和可以操作申请的HR编号需要通过申请对应的职位，职位对应的发布者和职位所处公司的超级账号来确定。
     * 所以在组合申请记录时，还需要查找对应的职位信息（获取职位的发布人和公司编号），以及公司下的超级账号。
     *
     * @param applicationList 申请编号集合
     * @param positionList 职位集合
     * @param superAccountList 超级账号集合
     * @return 申请数据
     */
    private List<ApplicationEntity> packageApplication(List<JobApplication> applicationList, List<JobPosition> positionList,
                                                       List<Record2<Integer, Integer>> superAccountList) {
        return applicationList.stream().map(jobApplication -> {

            int id = jobApplication.getId();
            ApplicationStateRoute status = ApplicationStateRoute.initFromState(jobApplication.getAppTplId());
            if (status == null) {
                logger.error("ApplicationRepository status is null! application:{}", jobApplication);
            }
            int state = status.getState();
            int viewNumber = jobApplication.getViewCount();
            List<Integer> hrIdList = new ArrayList<>();
            if (positionList != null && positionList.size() > 0) {
                Optional<JobPosition> jobPositionOptional = positionList
                        .stream()
                        .filter(jobPosition -> jobApplication.getPositionId().intValue()
                                == jobPosition.getId()).findAny();

                if (jobPositionOptional.isPresent()) {
                    hrIdList.add(jobPositionOptional.get().getPublisher());
                    Optional<Record2<Integer, Integer>> optional
                            = superAccountList
                            .stream()
                            .filter(record -> record.value1().intValue() == jobPositionOptional.get().getCompanyId().intValue())
                            .findAny();
                    if (optional.isPresent()) {
                        hrIdList.add(optional.get().value2());
                    }
                }
            }
            ApplicationEntity application = new ApplicationEntity(id, state, hrIdList, viewNumber);
            return application;
        }).collect(Collectors.toList());
    }

    public void addHROperationRecordList(List<HrOperationRecord> operationRecordList) {
        hrOperationJOOQDao.insert(operationRecordList);
    }

    /**
     * 修改申请实体
     * @param applicationList 申请实体列表
     * @return 被持久化的申请实体列表
     */
    public List<ApplicationEntity> updateApplications(List<ApplicationEntity> applicationList) {

        /** 申请的浏览次数加一 */
        List<ApplicationEntity> addViewList = applicationList
                .stream()
                .filter(applicationEntity ->
                        applicationEntity.getViewNumber() != applicationEntity.getInitViewNumber()
                                && applicationEntity.getState().getStatus()
                                .equals(applicationEntity.getInitState().getStatus()))
                .collect(Collectors.toList());
        jobApplicationDao.updateViewNumber(addViewList);

        /** 修改申请的状态 */
        List<ApplicationEntity> changeStateList = applicationList
                .stream()
                .filter(applicationEntity
                        -> applicationEntity.getViewNumber()
                        == applicationEntity.getInitViewNumber()
                        && !applicationEntity.getState().getStatus().equals(applicationEntity.getInitState().getStatus()))
                .collect(Collectors.toList());
        List<ApplicationEntity> changeStateResult = jobApplicationDao.changeState(changeStateList);

        /** 修改申请状态，并且浏览次数加一 */
        List<ApplicationEntity> addViewAndChangeStateList = applicationList
                .stream()
                .filter(applicationEntity
                        -> !applicationEntity.getState().getStatus()
                        .equals(applicationEntity.getInitState().getStatus())
                        && applicationEntity.getViewNumber() != applicationEntity.getInitViewNumber())
                .collect(Collectors.toList());

        List<ApplicationEntity> addViewAndChangeStateResult = jobApplicationDao.changeViewNumberAndState(addViewAndChangeStateList);

        changeStateResult.addAll(addViewAndChangeStateResult);


        JSONObject jsb = JSONObject.parseObject(ms);
        jsb.put("application_id", applicationList
                .stream()
                .map(applicationEntity -> applicationEntity.getId())
                .collect(Collectors.toList()));
        client.lpush(Constant.APPID_ALPHADOG,
                "ES_REALTIME_UPDATE_INDEX_USER_IDS", jsb.toJSONString());
        logger.info("lpush ES_REALTIME_UPDATE_INDEX_USER_IDS:{} success", jsb.toJSONString());

        return changeStateResult;
    }
}
