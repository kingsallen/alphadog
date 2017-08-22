package com.moseeker.entity;

import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.EmployeeType;
import com.moseeker.entity.biz.EmployeeEntityBiz;
import com.moseeker.entity.exception.ExceptionCategory;
import com.moseeker.entity.exception.ExceptionFactory;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.*;
import com.moseeker.thrift.gen.employee.struct.Reward;
import com.moseeker.thrift.gen.employee.struct.RewardVO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
@CounterIface
public class EmployeeEntity {

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private HrGroupCompanyRelDao hrGroupCompanyRelDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private UserEmployeePointsRecordDao ueprDao;

    @Autowired
    private UserEmployeePointsRecordDao employeePointsRecordDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private UserEmployeePointsRecordCompanyRelDao ueprcrDao;

    @Autowired
    private ConfigSysPointsConfTplDao configSysPointsConfTplDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    @Autowired
    private SearchengineEntity searchengineEntity;


    @Autowired
    private UserWxUserDao userWxUserDao;


    private static final Logger logger = LoggerFactory.getLogger(EmployeeEntity.class);

    /**
     * 判断某个用户是否属于某个公司的员工
     *
     * @param userId
     * @param companyId 如果公司属于集团公司，需验证用户是否在集团下的所有公司中认证过员工
     * @return
     */
    public boolean isEmployee(int userId, int companyId) {
        UserEmployeeDO employee = getCompanyEmployee(userId, companyId);
        if (employee != null && employee.getId() > 0 && employee.getActivation() == 0) {
            return true;
        }
        return false;
    }

    public UserEmployeeDO getCompanyEmployee(int userId, int companyId) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        // 查找集团公司列表
        List<Integer> companyIds = getCompanyIds(companyId);
        companyIds.add(companyId);
        query.where(new Condition("company_id", companyIds, ValueOp.IN)).and("sysuser_id", String.valueOf(userId))
                .and("disable", "0");
        return employeeDao.getData(query.buildQuery());
    }

    // 转发点击操作 前置
    public void addAwardBefore(int employeeId, int companyId, int positionId, int templateId, int berecomUserId) throws Exception {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", companyId).and("template_id", templateId);
        HrPointsConfDO hrPointsConfDO = hrPointsConfDao.getData(query.buildQuery());
        if (hrPointsConfDO != null && hrPointsConfDO.getId() > 0) {
            query.where("employee_id", employeeId).and("position_id", positionId).and("award_config_id", hrPointsConfDO).and("berecom_user_id", berecomUserId);
            UserEmployeePointsRecordDO userEmployeePointsRecordDO = employeePointsRecordDao.getData(query.buildQuery());
            if (userEmployeePointsRecordDO != null && userEmployeePointsRecordDO.getId() > 0) {
                logger.error("重复的加积分操作, employeeId:{}, positionId:{}, templateId:{}, berecomUserId:{}", employeeId, positionId, templateId, berecomUserId);
                throw new Exception("重复的加积分操作");
            }
        }
        // 进行加积分操作
        addReward(employeeId, companyId, "", 0, positionId, templateId, berecomUserId);
    }

    /**
     * 增加员工积点
     *
     * @param employeeId
     * @return 员工当前总积分
     */
    @Transactional
    public int addReward(int employeeId, int companyId, UserEmployeePointsRecordDO ueprDo) throws Exception {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId);
        UserEmployeeDO userEmployeeDO = employeeDao.getData(query.buildQuery());
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0 && ueprDo != null) {
            // 修改用户总积分, 产品说 积分不能扣成负数 所以为负数 填为 0
            if ((userEmployeeDO.getAward() + ueprDo.getAward()) >= 0) {
                userEmployeeDO.setAward(userEmployeeDO.getAward() + ueprDo.getAward());
            } else {
                userEmployeeDO.setAward(0);
            }
            int row = employeeDao.updateData(userEmployeeDO);
            // 积分记录
            if (row > 0) {
                ueprDo = ueprDao.addData(ueprDo);
                if (ueprDo.getId() > 0) {
                    logger.info("增加用户积分成功：为用户{},添加积分{}点, reason:{}", employeeId, ueprDo.getAward(), ueprDo.getReason());
                    // 记录积分来源公司
                    UserEmployeePointsRecordCompanyRelDO ueprcrDo = new UserEmployeePointsRecordCompanyRelDO();
                    ueprcrDo.setCompanyId(companyId);
                    ueprcrDo.setEmployeePointsRecordId(ueprDo.getId());
                    ueprcrDao.addData(ueprcrDo);
                    // 更新ES中的user_employee数据，以便积分排行实时更新
                    searchengineEntity.updateEmployeeAwards(Arrays.asList(employeeId));
                    return userEmployeeDO.getAward();
                } else {
                    logger.error("增加用户积分失败：为用户{},添加积分{}点, reason:{}", employeeId, ueprDo.getAward(), ueprDo.getReason());
                    throw new RuntimeException("增加积分失败");
                }
            }
        }
        return 0;
    }


    @Transactional
    public boolean addReward(int employeeId, int companyId, String reason, int applicationId, int positionId, int templateId, int berecomUserId) throws Exception {
        // 获取积分点数
        if (companyId == 0 || templateId == 0) {
            throw new Exception("参数不完整");
        } else {
            int award;
            int awardConfigId = 0;
            Query.QueryBuilder query = new Query.QueryBuilder().where("company_id", companyId).and("template_id", templateId);
            HrPointsConfDO hrPointsConfDO = hrPointsConfDao.getData(query.buildQuery());
            if (hrPointsConfDO != null && hrPointsConfDO.getReward() != 0) {
                award = (int) hrPointsConfDO.getReward();
                reason = org.apache.commons.lang.StringUtils.defaultIfBlank(reason, hrPointsConfDO.getStatusName());
                awardConfigId = hrPointsConfDO.getId();
            } else {
                query.clear();
                query.where("id", templateId);
                ConfigSysPointsConfTplDO confTplDO = configSysPointsConfTplDao.getData(query.buildQuery());
                if (confTplDO != null && confTplDO.getAward() != 0) {
                    award = confTplDO.getAward();
                    reason = org.apache.commons.lang.StringUtils.defaultIfBlank(reason, confTplDO.getStatus());
                } else {
                    throw new Exception("添加积分点数不能为0");
                }
            }
            UserEmployeePointsRecordDO ueprDo = new UserEmployeePointsRecordDO();
            ueprDo.setReason(reason);
            ueprDo.setAward(award);
            ueprDo.setApplicationId(applicationId);
            ueprDo.setAwardConfigId(awardConfigId);
            ueprDo.setBerecomUserId(berecomUserId);
            ueprDo.setPositionId(positionId);
            ueprDo.setEmployeeId(employeeId);
            addReward(employeeId, companyId, ueprDo);
        }
        return true;
    }

    /**
     * 积分列表
     *
     * @param employeeId
     * @return
     */
    public List<Reward> getEmployeePointsRecords(int employeeId) {
        // 用户积分记录：
        List<Reward> rewards = new ArrayList<>();
        List<com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO> points = employeePointsRecordDao.getDatas(new Query.QueryBuilder()
                .where("employee_id", employeeId).orderBy("update_time", Order.DESC).buildQuery());
        if (!StringUtils.isEmptyList(points)) {
            List<Double> aids = points.stream().map(m -> m.getApplicationId()).collect(Collectors.toList());
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where(new Condition("id", aids, ValueOp.IN));
            List<JobApplicationDO> applications = applicationDao.getDatas(query.buildQuery());
            final Map<Integer, Integer> appMap = new HashMap<>();
            final Map<Integer, String> positionMap = new HashMap<>();
            // 转成map -> k: applicationId, v: positionId
            if (!StringUtils.isEmptyList(applications)) {
                appMap.putAll(applications.stream().collect(Collectors.toMap(JobApplicationDO::getId, JobApplicationDO::getPositionId)));
                query.clear();
                query.where(new Condition("id", appMap.values().toArray(), ValueOp.IN));
                List<JobPositionDO> positions = positionDao.getPositions(query.buildQuery());
                // 转成map -> k: positionId, v: positionTitle
                if (!StringUtils.isEmptyList(points)) {
                    positionMap.putAll(positions.stream().collect(Collectors.toMap(JobPositionDO::getId, JobPositionDO::getTitle)));
                }
            }
            points.stream().filter(p -> p.getAward() != 0).forEach(point -> {
                Reward reward = new Reward();
                reward.setReason(point.getReason());
                reward.setPoints(point.getAward());
                reward.setUpdateTime(point.getUpdateTime());
                reward.setTitle(positionMap.getOrDefault(appMap.get(point.getApplicationId()), ""));
                rewards.add(reward);
            });
        }
        return rewards;
    }

    /**
     * 积分列表
     *
     * @param employeeId
     * @return
     */
    public RewardVOPageVO getEmployeePointsRecords(int employeeId, Integer pageNumber, Integer pageSize) throws CommonException {
        RewardVOPageVO rewardVOPageVO = new RewardVOPageVO();
        List<RewardVO> rewardVOList = new ArrayList<>();
        Query.QueryBuilder query = new Query.QueryBuilder();
        // 默认取100条数据
        if (pageNumber != null && pageNumber.intValue() > 0 && pageSize != null && pageSize > 0) {
            query.setPageSize(pageSize.intValue());
            query.setPageNum(pageNumber.intValue());
        }
        query.where(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID.getName(), employeeId)
                .and(new Condition(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD.getName(), 0, ValueOp.NEQ))
                .orderBy(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.UPDATE_TIME.getName(), Order.DESC);
        int totalRow = employeePointsRecordDao.getCount(query.buildQuery());
        // 总条数
        rewardVOPageVO.setTotalRow(totalRow);
        rewardVOPageVO.setPageNumber(pageNumber);
        rewardVOPageVO.setPageSize(pageSize);
        if (totalRow > 0) {
            List<com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO> points = employeePointsRecordDao.getDatas(query.buildQuery());
            // 申请记录信息
            Map<Integer, JobApplicationDO> appMap = new HashMap<>();
            // 申请的职位信息
            Map<Integer, JobPositionDO> positionMap = new HashMap<>();
            // 职位发布的HR信息
            Map<Integer, UserHrAccountDO> userHrAccountDOMap = new HashMap<>();
            // 员工信息
            Map<Integer, UserEmployeeDO> userEmployeeDOMap = new HashMap<>();
            // 积分类型
            Map<Integer, HrPointsConfDO> hrPointsConfMap = new HashMap<>();
            // 被推荐人信息
            Map<Integer, UserUserDO> userUserDOSMap = new HashMap<>();
            // 申请信息ID
            List<Integer> applicationIds = points.stream().filter(m -> m.getApplicationId() != 0).map(m -> new Double(m.getApplicationId()).intValue()).collect(Collectors.toList());
            // 职位信息Id
            List<Integer> positionIds = points.stream().filter(m -> m.getPositionId() != 0).map(m -> new Double(m.getPositionId()).intValue()).collect(Collectors.toList());
            // 获取被推荐人信息
            List<Integer> berecomIds = points.stream().filter(m -> m.getBerecomUserId() != 0).map(m -> new Double(m.getBerecomUserId()).intValue()).collect(Collectors.toList());
            // 加积分类型
            List<Integer> types = points.stream().filter(m -> m.getAwardConfigId() != 0).map(m -> new Double(m.getBerecomUserId()).intValue()).collect(Collectors.toList());

            query.clear();
            query.where(new Condition("id", applicationIds, ValueOp.IN));
            List<JobApplicationDO> applications = applicationDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(applications)) {
                appMap.putAll(applications.stream().collect(Collectors.toMap(JobApplicationDO::getId, Function.identity())));
            }
            // 查询职位信息
            query.clear();
            query.where(new Condition("id", positionIds, ValueOp.IN));
            List<JobPositionDO> positions = positionDao.getPositions(query.buildQuery());
            if (!StringUtils.isEmptyList(points)) {
                positionMap.putAll(positions.stream().collect(Collectors.toMap(JobPositionDO::getId, Function.identity())));
                // 获取职位发布者Id
                List<Integer> hrIds = positions.stream().map(position -> position.getPublisher()).collect(Collectors.toList());
                query.clear();
                query.where(new Condition(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), hrIds, ValueOp.IN));
                List<UserHrAccountDO> userHrAccountDOS = userHrAccountDao.getDatas(query.buildQuery());
                userHrAccountDOMap.putAll(userHrAccountDOS.stream().collect(Collectors.toMap(UserHrAccountDO::getId, Function.identity())));
            }
            List<Integer> companyIds = getCompanyIdsByUserId(employeeId);
            query.clear();
            query.where(new Condition(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), berecomIds, ValueOp.IN))
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), 0)
                    .and(new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN));
            List<UserEmployeeDO> userEmployeeDOList = employeeDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(userEmployeeDOList)) {
                userEmployeeDOMap.putAll(userEmployeeDOList.stream().collect(Collectors.toMap(UserEmployeeDO::getId, Function.identity())));
            }

            query.clear();
            query.where(new Condition(UserUser.USER_USER.ID.getName(), berecomIds, ValueOp.IN));
            List<UserUserDO> userUserDOS = userUserDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(userUserDOS)) {
                userUserDOSMap.putAll(userUserDOS.stream().collect(Collectors.toMap(UserUserDO::getId, Function.identity())));
            }
            query.clear();
            query.where(new Condition(HrPointsConf.HR_POINTS_CONF.ID.getName(), types, ValueOp.IN));
            List<HrPointsConfDO> hrPointsConfDOList = hrPointsConfDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(hrPointsConfDOList)) {
                hrPointsConfMap.putAll(hrPointsConfDOList.stream().collect(Collectors.toMap(HrPointsConfDO::getId, Function.identity())));
            }

            for (UserEmployeePointsRecordDO point : points) {

                // 拼装数据
                RewardVO reward = new RewardVO();
                // 加积分说明
                reward.setReason(point.getReason());
                // 积分
                reward.setPoints(point.getAward());
                // 加积分时间
                reward.setUpdateTime(point.getUpdateTime());
                // 职位ID
                reward.setPositionId(new Double(point.getPositionId()).intValue());
                HrPointsConfDO hrPointsConfDO = hrPointsConfMap.get(point.getAwardConfigId());
                if (hrPointsConfDO != null) {
                    reward.setType(hrPointsConfDO.getTemplateId());
                } else {
                    String reason = reward.getReason();
                    if (reason.indexOf("被推荐人投递简历") > -1) {
                        reward.setType(1);
                    } else if (reason.indexOf("HR已经安排面试") > -1) {
                        reward.setType(2);
                    } else if (reason.indexOf("入职") > -1) {
                        reward.setType(3);
                    } else if (reason.indexOf("拒绝") > -1) {
                        reward.setType(4);
                    } else if (reason.indexOf("MGR面试后表示先等待") > -1) {
                        reward.setType(5);
                    } else if (reason.indexOf("简历被HR查看/简历被下载") > -1) {
                        reward.setType(6);
                    } else if (reason.indexOf("转发职位被点击") > -1) {
                        reward.setType(7);
                    } else if (reason.indexOf("HR将简历转给MGR评审") > -1) {
                        reward.setType(8);
                    } else if (reason.indexOf("MGR评审后表示先等待") > -1) {
                        reward.setType(9);
                    } else if (reason.indexOf("简历评审合格") > -1) {
                        reward.setType(10);
                    } else if (reason.indexOf("接受录取通知") > -1) {
                        reward.setType(11);
                    } else if (reason.indexOf("面试通过") > -1) {
                        reward.setType(12);
                    } else if (reason.indexOf("完善被推荐人信息") > -1) {
                        reward.setType(13);
                    } else {
                        reward.setType(0); // 未知
                    }
                }
                JobPositionDO jobPositionDO = positionMap.get(reward.getPositionId());
                if (jobPositionDO != null) {
                    // 职位名称
                    reward.setPositionName(jobPositionDO.getTitle());
                    // 发布职位的hrID
                    reward.setPublisherId(jobPositionDO.getPublisher());
                    // 发布职位的HR姓名
                    UserHrAccountDO userHrAccountDO = userHrAccountDOMap.get(reward.getPublisherId());
                    if (userHrAccountDO != null) {
                        reward.setPublisherName(userHrAccountDOMap.get(reward.getPublisherId()).getUsername());
                    } else {
                        reward.setPublisherName("");
                    }
                } else {
                    reward.setPositionName("");
                    // 发布职位的hrID
                    reward.setPublisherId(0);
                }
                UserEmployeeDO userEmployeeDO = userEmployeeDOMap.get(point.getBerecomUserId());
                if (userEmployeeDO != null) {
                    reward.setEmployeId(userEmployeeDO.getId());
                    reward.setEmployeName(userEmployeeDO.getCname());
                } else {
                    // 被推荐人ID
                    reward.setBerecomId(point.getBerecomUserId());
                    // userdb.useruser.name > userdb.useruser.nickname > userdb.userwxuser.nickname
                    if (userUserDOSMap.containsKey(point.getBerecomUserId())) {
                        UserUserDO userUserDO = userUserDOSMap.get(point.getBerecomUserId());
                        if (userUserDO.getName() != null) {
                            reward.setBerecomName(userUserDO.getName());
                        } else if (userUserDO.getName() == null && userUserDO.getNickname() != null) {
                            reward.setBerecomName(userUserDO.getNickname());
                        }
                    }
                    if (reward.getBerecomName() == null) {
                        query.clear();
                        query.where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(), point.getBerecomUserId());
                        UserWxUserDO userWxUserDO = userWxUserDao.getData(query.buildQuery());
                        if (userWxUserDO != null) {
                            reward.setBerecomName(userWxUserDO.getNickname());
                        }
                    }
                }
                rewardVOList.add(reward);
            }
            rewardVOPageVO.setData(rewardVOList);
        }
        return rewardVOPageVO;
    }


    /**
     * 员工取消认证（支持批量）
     *
     * @param employeeIds
     * @return
     */
    public boolean unbind(Collection<Integer> employeeIds) throws CommonException {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.and(new Condition("id", employeeIds, ValueOp.IN))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), 0);
        List<UserEmployeeDO> employeeDOList = employeeDao.getDatas(query.buildQuery());
        return unbind(employeeDOList);
    }

    /**
     * 员工取消认证（支持批量）-- 重载
     *
     * @param employees
     * @return
     */
    public boolean unbind(List<UserEmployeeDO> employees) throws CommonException {
        if (employees != null && employees.size() > 0) {
            employees.stream().filter(f -> f.getActivation() == 0).forEach(e -> {
                e.setActivation((byte) 1);
                e.setEmailIsvalid((byte) 0);
            });
            int[] rows = employeeDao.updateDatas(employees);
            if (Arrays.stream(rows).sum() > 0) {
                // 更新ES中useremployee信息
                searchengineEntity.updateEmployeeAwards(employees.stream().map(m -> m.getId()).collect(Collectors.toList()));
                return true;
            } else {
                throw ExceptionFactory.buildException(ExceptionCategory.EMPLOYEE_IS_UNBIND);
            }
        }
        return false;
    }


    /**
     * 员工删除(支持批量)
     * 1.将数据移入到history_user_employee中
     * 2.user_employee中做物理删除
     */
    @Transactional
    public boolean removeEmployee(List<Integer> employeeIds) throws CommonException {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where(new Condition("id", employeeIds, ValueOp.IN));
        List<UserEmployeeDO> userEmployeeDOList = employeeDao.getDatas(query.buildQuery());
        if (userEmployeeDOList != null && userEmployeeDOList.size() > 0) {
            int[] rows = employeeDao.deleteDatas(userEmployeeDOList);
            // 受影响行数大于零，说明删除成功， 将数据copy到history_user_employee中
            if (Arrays.stream(rows).sum() > 0) {
                historyUserEmployeeDao.addAllData(userEmployeeDOList);
                // 更新ES中useremployee信息
                searchengineEntity.deleteEmployeeDO(employeeIds);
                return true;
            } else {
                throw ExceptionFactory.buildException(ExceptionCategory.EMPLOYEE_HASBEENDELETEOR);
            }
        }
        return false;
    }


    /**
     * 权限的判断
     *
     * @param userEmployeeIds 员工ID
     * @param companyIds      HR所在公司ID
     * @return
     */
    public Boolean permissionJudge(List<Integer> userEmployeeIds, List<Integer> companyIds) {
        Condition companyIdCondition = null;
        if (companyIds.size() == 1) {
            companyIdCondition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds.get(0), ValueOp.EQ);
        } else {
            companyIdCondition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
        }
        Condition userEmployeeId = null;
        if (userEmployeeIds.size() == 1) {
            userEmployeeId = new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), userEmployeeIds.get(0), ValueOp.EQ);
        } else {
            userEmployeeId = new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), userEmployeeIds, ValueOp.IN);
        }
        // 查询公司的员工ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(companyIdCondition).and(userEmployeeId)
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);

        List<UserEmployeeDO> list = employeeDao.getDatas(queryBuilder.buildQuery());
        if (StringUtils.isEmptyList(list)) {
            return false;
        }
        if (list.size() != userEmployeeIds.size()) {
            return false;
        }
        return true;
    }

    /**
     * 权限的判断
     *
     * @param userEmployeeIds 员工ID列表
     * @param companyId       HR所在公司ID
     * @return
     */
    public Boolean permissionJudge(List<Integer> userEmployeeIds, Integer companyId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        // 是否是子公司，如果是查询母公司ID
        HrCompanyDO hrCompanyDO = hrCompanyDao.getData(queryBuilder.buildQuery());
        if (hrCompanyDO == null) {
            return false;
        }
        List<Integer> list = getCompanyIds(hrCompanyDO.getParentId() > 0 ? hrCompanyDO.getParentId() : companyId);
        return permissionJudge(userEmployeeIds, list);
    }


    /**
     * 权限的判断
     *
     * @param userEmployeeId 员工ID
     * @param companyId      HR所在公司ID
     * @return
     */
    public Boolean permissionJudge(Integer userEmployeeId, Integer companyId) {
        List<Integer> sysuserIds = new ArrayList<>();
        sysuserIds.add(userEmployeeId);
        return permissionJudge(sysuserIds, companyId);
    }

    /**
     * 通过companyId 查询集团下所有的公司ID列表
     *
     * @return
     */
    public List<Integer> getCompanyIds(Integer companyId) {
        List<Integer> list = new ArrayList<>();
        logger.info("compangId:{}", companyId);
        // 查询集团ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.COMPANY_ID.getName(), companyId);
        HrGroupCompanyRelDO hrGroupCompanyRelDO = hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
        // 没有集团信息，返回当前companyId
        if (hrGroupCompanyRelDO == null) {
            logger.info("未查询到该公司的集团ID");
            list.add(companyId);
            return list;
        }
        queryBuilder.clear();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.GROUP_ID.getName(), hrGroupCompanyRelDO.getGroupId());
        List<HrGroupCompanyRelDO> hrGroupCompanyRelDOS = hrGroupCompanyRelDao.getDatas(queryBuilder.buildQuery());
        if (StringUtils.isEmptyList(hrGroupCompanyRelDOS)) {
            return list;
        }
        // 返回CompanyList
        hrGroupCompanyRelDOS.forEach(hrGroupCompanyRelDOTemp ->
                list.add(hrGroupCompanyRelDOTemp.getCompanyId())
        );
        return list;
    }

    /**
     * 通过companyId 查询group id
     *
     * @return
     */
    public int getGroupIdByCompanyId(int companyId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.COMPANY_ID.getName(), companyId);
        HrGroupCompanyRelDO hrGroupCompanyRelDO = hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
        // 没有集团信息，返回0
        if (hrGroupCompanyRelDO == null) {
            return 0;
        }
        return hrGroupCompanyRelDO.getId();
    }

    /**
     * 通过员工ID 查询集团下所有的公司ID列表
     *
     * @param userId userdb.user_user.id
     * @return
     */
    public List<Integer> getCompanyIdsByUserId(Integer userId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), userId);
        queryBuilder.and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeType.AUTH_SUCCESS.getValue());
        queryBuilder.and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        UserEmployeeDO userEmployeeDO = employeeDao.getData(queryBuilder.buildQuery());
        if (userEmployeeDO == null) {
            return new ArrayList<>();
        }
        return getCompanyIds(userEmployeeDO.getCompanyId());
    }

    /**
     * 通过公司ID查集团下所有的员工列表
     *
     * @param companyId
     * @return
     */
    public List<UserEmployeeDO> getUserEmployeeDOList(Integer companyId) {
        List<UserEmployeeDO> userEmployeeDOS = new ArrayList<>();
        if (companyId != 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            List<Integer> companyIds = getCompanyIds(companyId);
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.select(UserEmployee.USER_EMPLOYEE.ID.getName())
                    .select(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName())
                    .select(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName());
            Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
            queryBuilder.where(condition).and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
            userEmployeeDOS = employeeDao.getDatas(queryBuilder.buildQuery());
        }
        return userEmployeeDOS;
    }

    /**
     * 通过公司ID查集团认证过的员工数据
     *
     * @param companyId 公司编号
     * @return
     */
    public List<UserEmployeeDO> getVerifiedUserEmployeeDOList(Integer companyId) {
        List<UserEmployeeDO> userEmployeeDOS = new ArrayList<>();
        if (companyId != 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            List<Integer> companyIds = getCompanyIds(companyId);
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.select(UserEmployee.USER_EMPLOYEE.ID.getName())
                    .select(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName())
                    .select(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName());
            Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
            queryBuilder.where(condition)
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue())
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeType.AUTH_SUCCESS.getValue());
            userEmployeeDOS = employeeDao.getDatas(queryBuilder.buildQuery());
        }
        return userEmployeeDOS;
    }

    /**
     * 添加员工记录集合。
     * 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
     *
     * @param userEmployeeList 员工记录集合
     * @return 添加好的员工记录。如果参数是空，那么返回值是null
     * @throws CommonException
     */
    public List<UserEmployeeDO> addEmployeeList(List<UserEmployeeDO> userEmployeeList) throws CommonException {
        if (userEmployeeList != null && userEmployeeList.size() > 0) {
            List<UserEmployeeDO> employeeDOS = employeeDao.addAllData(userEmployeeList);
            // ES 索引更新
            searchengineEntity.updateEmployeeAwards(employeeDOS.stream().map(m -> m.getId()).collect(Collectors.toList()));
            return employeeDOS;
        } else {
            return null;
        }
    }

    /**
     * 添加员工记录集合。
     * 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
     *
     * @param userEmployeeList 员工记录集合
     * @return 添加好的员工记录。如果参数是空，那么返回值是null
     * @throws CommonException
     */
    public List<UserEmployeeRecord> addEmployeeRecordList(List<UserEmployeeRecord> userEmployeeList) throws CommonException {
        if (userEmployeeList != null && userEmployeeList.size() > 0) {
            List<UserEmployeeRecord> employeeDOS = employeeDao.addAllRecord(userEmployeeList);

            searchengineEntity.updateEmployeeAwards(employeeDOS.stream().map(m -> m.getId()).collect(Collectors.toList()));

            return employeeDOS;
        } else {
            return null;
        }
    }

    /**
     *
     * 删除员工记录或者员工数据
     * 会向员工记录中删除数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
     *
     * @param userEmployeeDOList
     * @throws CommonException
     */
//    public void deleteEmployeeList(List<UserEmployeeDO> userEmployeeDOList) throws CommonException {
//        if (userEmployeeDOList == null || userEmployeeDOList.size() == 0) {
//            return;
//        }
//        JSONObject jobj = new JSONObject();
//        jobj.put("employee_id", userEmployeeDOList.stream().map(m -> m.getId()).collect(Collectors.toList()));
//        employeeDao.deleteDatas(userEmployeeDOList);
//
//        searchengineEntity.deleteEmployeeDO(userEmployeeDOList.stream().map(m -> m.getId()).collect(Collectors.toList()));
//    }

    /**
     * 添加员工记录或者员工数据
     * 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
     *
     * @param userEmployee
     * @return
     * @throws CommonException
     */
    public UserEmployeeDO addEmployee(UserEmployeeDO userEmployee) throws CommonException {
        if (userEmployee == null) {
            return null;
        }
        UserEmployeeDO employeeDO = employeeDao.addData(userEmployee);

        searchengineEntity.updateEmployeeAwards(Arrays.asList(employeeDO.getId()));

        return employeeDO;
    }

    /**
     * ATS对接 批量修改员工信息
     *
     * @param batchForm
     */
    public int[] postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws CommonException {
        if (batchForm.getData() == null || batchForm.getData().size() == 0) {
            return new int[0];
        }

        logger.info("postPutUserEmployeeBatch {},总数据:{}条", batchForm.getCompany_id(), batchForm.getData().size());

        Query.QueryBuilder builder = null;

        //这批数据的特征值集合
        List<String> uniqueFlags = new ArrayList<>();

        int[] dataStatus = new int[batchForm.getData().size()];//对应的数据的操作类型1，插入，2：更新，0：无效的数据

        EmployeeEntityBiz.getUniqueFlagsAndStatus(batchForm.getData(), uniqueFlags, dataStatus);

        logger.info("postPutUserEmployeeBatch {},有效的数据:{}条", batchForm.getCompany_id(), uniqueFlags.size());

        Set<Integer> delIds = new HashSet<>();
        int page = 1;
        int pageSize = 1000;

        List<UserEmployeeRecord> records;

        //每次取出1000条检查，把不在userEmployees里面的数据的id记录到delIds
        while (true) {
            logger.info("postPutUserEmployeeBatch {},检查数据:page:{}", batchForm.getCompany_id(), page);
            builder = new Query.QueryBuilder();
            builder.where("company_id", batchForm.getCompany_id());
            builder.setPageSize(pageSize);
            builder.setPageNum(page);
            records = employeeDao.getRecords(builder.buildQuery());

            //取完了
            if (records == null || records.size() == 0) {
                break;
            }
            int index;
            String flag1, flag2;
            //开始检查
            for (UserEmployeeRecord record : records) {
                if (record.getCustomField() == null) record.setCustomField("");
                if (record.getCname() == null) record.setCname("");
                flag1 = record.getCompanyId() + "_custom_field_" + record.getCustomField().trim();
                if (uniqueFlags.contains(flag1)) {
                    //这条数据需要更新
                    index = uniqueFlags.indexOf(flag1);
                    dataStatus[index] = 2;
                    batchForm.getData().get(index).setId(record.getId());
                } else {
                    flag2 = record.getCompanyId() + "_cname_" + record.getCname().trim();
                    if (uniqueFlags.contains(flag2)) {
                        //这条数据需要更新
                        index = uniqueFlags.indexOf(flag2);
                        dataStatus[index] = 2;
                        batchForm.getData().get(index).setId(record.getId());
                    } else {
                        //这条数据需要删除
                        delIds.add(record.getId());
                    }
                }
            }

            //取完了
            if (records.size() != pageSize) {
                break;
            }

            //继续取下1000条记录检查
            page++;

        }

        logger.info("postPutUserEmployeeBatch {},不在集合中的数据:{}条", batchForm.getCompany_id(), delIds.size());

        if (batchForm.isDel_not_include() && delIds.size() > 0) {
            logger.info("postPutUserEmployeeBatch {},删除数据:{}条", batchForm.getCompany_id(), delIds.size());
            //把不在userEmployees中的数据从数据库中删除
            int batchDeleteSize = 500;
            Iterator<Integer> delIterator = delIds.iterator();
            List<Integer> delBatch = new ArrayList<>();

            //数据太多一次最多删除500个
            while (delIterator.hasNext()) {
                delBatch.add(delIterator.next());
                delIterator.remove();
                if (delBatch.size() >= 500) {
                    Condition condition = new Condition("id", delBatch, ValueOp.IN);
                    delete(condition);
                    delBatch.clear();
                }
            }

            if (delBatch.size() > 0) {
                Condition condition = new Condition("id", delBatch, ValueOp.IN);
                delete(condition);
                delBatch.clear();
            }
        }

        //要更新的数据
        List<UserEmployeeStruct> updateDatas = new ArrayList<>();

        //要添加的数据
        List<UserEmployeeStruct> addDatas = new ArrayList<>();

        for (int i = 0; i < dataStatus.length; i++) {
            if (dataStatus[i] == 1) {
                addDatas.add(batchForm.getData().get(i));
            } else if (dataStatus[i] == 2) {
                updateDatas.add(batchForm.getData().get(i));
            }
        }

        logger.info("postPutUserEmployeeBatch {},需要添加的数据:{}条", batchForm.getCompany_id(), addDatas.size());
        logger.info("postPutUserEmployeeBatch {},需要更新的数据:{}条", batchForm.getCompany_id(), updateDatas.size());

        int batchSize = 500;

        if (addDatas.size() > 0) {
            //每次最多一次插入100条
            int start = 0;
            while ((start + batchSize) < addDatas.size()) {
                addEmployeeRecordList(BeanUtils.structToDB(addDatas.subList(start, start + batchSize), UserEmployeeRecord.class));
                start += batchSize;
                logger.info("postPutUserEmployeeBatch {},批量插入数据{}条,剩余{}条", batchForm.getCompany_id(), batchSize, addDatas.size() - start);
            }
            addEmployeeRecordList(BeanUtils.structToDB(addDatas.subList(start, addDatas.size()), UserEmployeeRecord.class));
            logger.info("postPutUserEmployeeBatch {},批量插入数据{}条,剩余{}条", batchForm.getCompany_id(), addDatas.size() - start, 0);
        }

        if (updateDatas.size() > 0) {
            //每次最多一次更新100条
            int start = 0;
            while ((start + batchSize) < updateDatas.size()) {
                employeeDao.updateRecords(BeanUtils.structToDB(updateDatas.subList(start, start + batchSize), UserEmployeeRecord.class));
                start += batchSize;
                logger.info("postPutUserEmployeeBatch {},批量更新数据{}条,剩余{}条", batchForm.getCompany_id(), batchSize, updateDatas.size() - start);
            }
            employeeDao.updateRecords(BeanUtils.structToDB(updateDatas.subList(start, updateDatas.size()), UserEmployeeRecord.class));
            logger.info("postPutUserEmployeeBatch {},批量更新数据{}条,剩余{}条", batchForm.getCompany_id(), updateDatas.size() - start, 0);
        }

        logger.info("postPutUserEmployeeBatch {},result:{},", batchForm.getCompany_id(), dataStatus.length < 500 ? Arrays.toString(dataStatus) : ("成功处理" + dataStatus.length + "条"));

        return dataStatus;
    }

    /**
     * 根据条件删除员工数据
     *
     * @param condition 条件
     */
    private void delete(Condition condition) throws CommonException {
        if (condition != null) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.select("id").where(condition);
            List<UserEmployeeDO> employeeRecordList = employeeDao.getDatas(queryBuilder.buildQuery());
            removeEmployee(employeeRecordList.stream().map(m -> m.getId()).collect(Collectors.toList()));
        } else {
            //do nothing
        }
    }
}
