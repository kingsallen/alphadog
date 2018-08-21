package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrPointsConf;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecord;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.useraccounts.domain.AwardEntity;
import com.moseeker.useraccounts.service.aggregate.ApplicationsAggregateId;
import com.moseeker.useraccounts.service.constant.AwardEvent;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.userdb.tables.UserEmployee.USER_EMPLOYEE;
import static org.jooq.impl.DSL.recordType;
import static org.jooq.impl.DSL.using;

/**
 *
 * Repository
 *
 * Created by jack on 16/01/2018.
 */
@Component
public class AwardRepository {

    private Logger logger = LoggerFactory.getLogger(AwardRepository.class);

    private final Configuration configuration;
    private JobApplicationJOOQDao jobApplicationDao;
    private JobPositionJOOQDao positionJOOQDao;
    private UserEmployeeJOOQDao userEmployeeJOOQDao;
    private HrPointsConfJOOQDao confJOOQDao;
    private UserEmployeePointsRecordJOOQDao employeePointsRecordJOOQDao;

    @Autowired
    SearchengineEntity searchengineEntity;

    @Autowired
    public AwardRepository(Configuration configuration) {
        this.configuration = configuration;
        jobApplicationDao = new JobApplicationJOOQDao(configuration);
        positionJOOQDao = new JobPositionJOOQDao(configuration);
        confJOOQDao = new HrPointsConfJOOQDao(configuration);
        userEmployeeJOOQDao = new UserEmployeeJOOQDao(configuration);
        employeePointsRecordJOOQDao = new UserEmployeePointsRecordJOOQDao(configuration);
    }

    public JobApplicationJOOQDao getJobApplicationDao() {
        return jobApplicationDao;
    }

    public JobPositionJOOQDao getPositionJOOQDao() {
        return positionJOOQDao;
    }

    /**
     * 生成需要添加的积分
     * @param applicationIdList 申请编号集合
     * @return 需要被添加的积分集合
     * @throws CommonException (42024, "职位已经下架!") (42025, "积分配置确实!")
     */
    public List<UserEmployeePointsRecord> fetchEmployeePointsByApplicationIdList(List<Integer> applicationIdList, AwardEvent awardEvent) throws CommonException {

        List<UserEmployeePointsRecord> userEmployeePointsRecords = new ArrayList<>();

        List<JobApplication> applicationList = jobApplicationDao.fetchByIdList(applicationIdList);
        if (applicationIdList != null && applicationIdList.size() > 0) {
            /** 职位与公司的关系，Record2的第一个数据是职位，第二个数据是公司 */
            Result<Record2<Integer, Integer>> positionIdAndCompanyId
                    = positionJOOQDao.fetchPositionIdAndCompanyIdByPositionIdList(applicationList
                    .stream()
                    .map(app -> app.getPositionId())
                    .collect(Collectors.toSet()));
            if (positionIdAndCompanyId == null) {
                throw UserAccountException.AWARD_POSITION_ALREADY_DELETED;
            }
            List<HrPointsConf> confList = confJOOQDao.fetchByCompanyIdList(positionIdAndCompanyId
                    .stream()
                    .map(pc -> pc.value2())
                    .collect(Collectors.toList()));
            if (confList == null || confList.size() == 0) {
                throw UserAccountException.AWARD_POINTS_CONF_LOST;
            }

            /** 查找员工的参数，key是C端用户的编号，value是公司编号 */
            Map<Integer, Integer> employeeParam = new HashMap<>();
            applicationList.forEach(app -> {
                Optional<Record2<Integer, Integer>> optional
                        = positionIdAndCompanyId
                        .stream()
                        .filter(pc -> pc.value1().intValue() == app.getPositionId().intValue())
                        .findAny();
                if (optional.isPresent()) {
                    employeeParam.put(app.getRecommenderUserId(), optional.get().value2());
                }
            });
            List<UserEmployee> userEmployeeList = userEmployeeJOOQDao.fetchByUserIdAndCompanyId(employeeParam);

            if (userEmployeeList == null && userEmployeeList.size() == 0) {
                throw UserAccountException.AWARD_EMPLOYEE_ELEGAL;
            }

            /** 拼装员工积分数据 */
            applicationList.forEach(app -> {
                UserEmployeePointsRecord pointsRecord = new UserEmployeePointsRecord();

                pointsRecord.setApplicationId(app.getId().longValue());
                pointsRecord.setRecomUserId(app.getRecommenderUserId());
                pointsRecord.setBerecomUserId(app.getApplierId());

                /** 查找员工编号 */
                Optional<UserEmployee> userEmployeeOptional = userEmployeeList
                        .stream()
                        .filter(employee -> employee.getSysuserId().intValue() == app.getRecommenderUserId().intValue())
                        .findAny();
                if (userEmployeeOptional.isPresent()) {
                    pointsRecord.setEmployeeId(userEmployeeOptional.get().getId().longValue());
                }  else {
                    logger.error("fetchEmployeePointsByApplicationIdList 员工不存在!");
                    return;
                }
                Optional<Record2<Integer, Integer>> record2Optional = positionIdAndCompanyId
                        .stream()
                        .filter(r -> r.value1().intValue() == app.getPositionId().intValue())
                        .findAny();
                if (record2Optional.isPresent()) {

                    //职位编号
                    pointsRecord.setPositionId(record2Optional.get().value1().longValue());

                    Optional<HrPointsConf> confOptional = confList
                            .stream()
                            .filter(hrPointsConf
                                    -> hrPointsConf.getCompanyId().intValue() == record2Optional.get().value2().intValue()
                                    && hrPointsConf.getTemplateId() == awardEvent.getState())
                            .findAny();
                    /** 公司积分配置的积分添加的解释和添加的积分数值 */
                    if (confOptional.isPresent()) {
                        HrPointsConf hrPointsConf = confOptional.get();
                        pointsRecord.setReason(hrPointsConf.getStatusName());
                        pointsRecord.setAward(hrPointsConf.getReward().intValue());
                    } else {
                        logger.error("fetchEmployeePointsByApplicationIdList 及分配至不存在或者配置为0分!");
                        return;
                    }
                } else {
                    logger.error("fetchEmployeePointsByApplicationIdList 职位与公司的关系不正确!");
                    return;
                }

                userEmployeePointsRecords.add(pointsRecord);
            });
        }
        return userEmployeePointsRecords;
    }

    /**
     * 根据员工编号查找员工积分
     * @param employeeIdList 员工编号集合
     * @return
     */
    public Result<Record2<Integer,Integer>> fetchEmployeeAwardByEmployeeId(List<Integer> employeeIdList) {
        return using(configuration)
                .select(USER_EMPLOYEE.ID, USER_EMPLOYEE.AWARD)
                .from(USER_EMPLOYEE)
                .where(USER_EMPLOYEE.ID.in(employeeIdList))
                .and(USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                .and(USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .fetch();
    }

    /**
     * 修改员工积分
     * @param employeeList 员工积分参数
     */
    public void updateEmployeeAwards(List<UserEmployee> employeeList) {

        if (employeeList != null && employeeList.size() > 0) {
            employeeList.stream().forEach(userEmployee ->
                using(configuration)
                        .update(USER_EMPLOYEE)
                        .set(USER_EMPLOYEE.AWARD, userEmployee.getAward())
                        .where(USER_EMPLOYEE.ID.eq(userEmployee.getId()))
                        .and(USER_EMPLOYEE.AWARD.eq(USER_EMPLOYEE.AWARD))
                        .execute()
            );
        }

    }

    /**
     * 添加员工积分记录集合
     * @param userEmployeePointsRecordList 员工积分记录集合
     */
    public List<UserEmployeePointsRecord> addEmployeeAwards(List<UserEmployeePointsRecord> userEmployeePointsRecordList) {
        return employeePointsRecordJOOQDao.addEmployeeAwards(userEmployeePointsRecordList);
    }

    public AwardEntity loadAwardEntity(ApplicationsAggregateId applicationsAggregateId) {
        List<UserEmployeePointsRecord> userEmployeePointsRecordList
                = fetchEmployeePointsByApplicationIdList(applicationsAggregateId.getApplicationIdList(),
                applicationsAggregateId.getAwardEvent());
        List<UserEmployee> employeeList
                = fetchIdAndAwardListById(userEmployeePointsRecordList
                .stream()
                .map(p -> p.getEmployeeId().intValue())
                .collect(Collectors.toList()));
        AwardEntity awardEntity = new AwardEntity(this, searchengineEntity, userEmployeePointsRecordList, employeeList);
        return awardEntity;
    }

    /**
     * 查找员工与员工当前积分信息
     * @param idList 员工编号集合
     * @return 员工编号和积分信息列表
     */
    private List<UserEmployee> fetchIdAndAwardListById(List<Integer> idList) {
        return userEmployeeJOOQDao.fetchIdAndAwardListById(idList);
    }
}
