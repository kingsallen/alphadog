package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.userdb.tables.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.EmployeeType;
import com.moseeker.entity.biz.EmployeeEntityBiz;
import com.moseeker.entity.exception.EmployeeException;
import com.moseeker.entity.exception.ExceptionCategory;
import com.moseeker.entity.exception.ExceptionFactory;
import com.moseeker.entity.pojos.EmployeeInfo;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.*;
import com.moseeker.thrift.gen.employee.struct.RewardVO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private CandidateCompanyDao candidateCompanyDao;
    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private HrWxWechatDao wechatDao;

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
                .and("disable", "0")
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), "0");
        return employeeDao.getData(query.buildQuery());
    }

    // 转发点击操作 前置
    @Transactional
    public void addAwardBefore(int employeeId, int companyId, int positionId, int templateId, int berecomUserId, int applicationId) throws Exception {
        // for update 对employeee信息加行锁 避免多个端同时对同一个用户加积分
        employeeDao.getUserEmployeeForUpdate(employeeId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", companyId).and("template_id", templateId);
        HrPointsConfDO hrPointsConfDO = hrPointsConfDao.getData(query.buildQuery());
        int awardConfigId = hrPointsConfDO == null ? 0 : hrPointsConfDO.getId();
        query.clear();
        query.where("employee_id", employeeId).and("position_id", positionId).and("award_config_id", awardConfigId).and("berecom_user_id", berecomUserId);
        UserEmployeePointsRecordDO userEmployeePointsRecordDO = ueprDao.getData(query.buildQuery());
        if (userEmployeePointsRecordDO != null && userEmployeePointsRecordDO.getId() > 0) {
            logger.warn("重复的加积分操作, employeeId:{}, positionId:{}, templateId:{}, berecomUserId:{}", employeeId, positionId, templateId, berecomUserId);
            throw new Exception("重复的加积分操作");
        }
        // 进行加积分操作
        addReward(employeeId, companyId, "", applicationId, positionId, templateId, berecomUserId);
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
        query.where("id", employeeId).and("disable", 0).and("activation", 0);
        UserEmployeeDO userEmployeeDO = employeeDao.getUserEmployeeForUpdate(employeeId);
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0 && ueprDo != null) {
            // 修改用户总积分, 积分不能扣成负数
            int totalAward = userEmployeeDO.getAward() + ueprDo.getAward();
            if (totalAward < 0) {
                logger.error("增加用户积分失败，用户积分不足：为用户{},用户当前积分{}点,添加积分{}点, reason:{}", employeeId, userEmployeeDO.getAward(), ueprDo.getAward(), ueprDo.getReason());
                throw new RuntimeException("用户积分不足");
            }
            int row = employeeDao.addAward(userEmployeeDO.getId(), totalAward, userEmployeeDO.getAward());
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
                    searchengineEntity.updateEmployeeAwards(employeeId, ueprDo.getId());
                    return totalAward;
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
            if (hrPointsConfDO != null) {
                if (hrPointsConfDO.getReward() == 0) {
                    throw new Exception("添加积分点数不能为0");
                } else {
                    award = (int) hrPointsConfDO.getReward();
                    reason = org.apache.commons.lang.StringUtils.defaultIfBlank(reason, hrPointsConfDO.getStatusName());
                    awardConfigId = hrPointsConfDO.getId();
                }
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
                .orderBy(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD._CREATE_TIME.getName(), Order.DESC);
        int totalRow = ueprDao.getCount(query.buildQuery());
        // 总条数
        rewardVOPageVO.setTotalRow(totalRow);
        rewardVOPageVO.setPageNumber(pageNumber);
        rewardVOPageVO.setPageSize(pageSize);
        if (totalRow > 0) {
            List<UserEmployeePointsRecordRecord> userEmployeePointsRecordList = ueprDao.getRecords(query.buildQuery());
            List<UserEmployeePointsRecordDO> points = new ArrayList<>();
            if (userEmployeePointsRecordList != null && userEmployeePointsRecordList.size() > 0) {
                for (UserEmployeePointsRecordRecord userEmployeePointsRecordRecord : userEmployeePointsRecordList) {
                    UserEmployeePointsRecordDO userEmployeePointsRecordDO =
                            BeanUtils.DBToStruct(UserEmployeePointsRecordDO.class, userEmployeePointsRecordRecord);
                    userEmployeePointsRecordDO.setCreateTime(new DateTime(userEmployeePointsRecordRecord.get_CreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                    points.add(userEmployeePointsRecordDO);
                }
            }
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
            Set<Integer> berecomIds = points.stream().filter(m -> m.getBerecomUserId() != 0).map(m -> new Double(m.getBerecomUserId()).intValue()).collect(Collectors.toSet());
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
            query.clear();
            query.where("id", employeeId);
            UserEmployeeDO userEmployeeDOTemp = employeeDao.getEmployee(query.buildQuery());
            List<Integer> companyIds = getCompanyIdsByUserId(userEmployeeDOTemp.getSysuserId());
            query.clear();
            query.where(new Condition(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), berecomIds, ValueOp.IN))
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), 0)
                    .and(new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN));
            List<UserEmployeeDO> userEmployeeDOList = employeeDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(userEmployeeDOList)) {
                userEmployeeDOMap.putAll(userEmployeeDOList.stream().collect(Collectors.toMap(UserEmployeeDO::getSysuserId, Function.identity())));
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
                reward.setUpdateTime(point.getCreateTime());
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
                    reward.setEmployeeId(userEmployeeDO.getId());
                    reward.setEmployeeName(userEmployeeDO.getCname());
                }
                // 被推荐人ID
                reward.setBerecomId(point.getBerecomUserId());
                // userdb.useruser.name > userdb.useruser.nickname > userdb.userwxuser.nickname
                if (userUserDOSMap.containsKey(point.getBerecomUserId())) {
                    UserUserDO userUserDO = userUserDOSMap.get(point.getBerecomUserId());
                    if (userUserDO.getName() != null && !userUserDO.getName().equals("")) {
                        reward.setBerecomName(userUserDO.getName());
                    } else if (userUserDO.getName() == null || userUserDO.getName().equals("")) {
                        if (userUserDO.getNickname() != null && !userUserDO.getNickname().equals("")) {
                            reward.setBerecomName(userUserDO.getNickname());
                        }
                    }
                }
                if (reward.getBerecomName() == null) {
                    query.clear();
                    query.where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(), point.getBerecomUserId());
                    UserWxUserDO userWxUserDO = userWxUserDao.getData(query.buildQuery());
                    if (userWxUserDO != null && org.apache.commons.lang.StringUtils.isNotBlank(userWxUserDO.getNickname())) {
                        reward.setBerecomName(userWxUserDO.getNickname());
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
                e.setCustomFieldValues("[]");
            });
            for(UserEmployeeDO DO:employees){
                int userId=DO.getSysuserId();
                int companyId=DO.getCompanyId();
                convertCandidatePerson(userId,companyId);
            }
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
     * 员工删除(支持批量) 1.将数据移入到history_user_employee中 2.user_employee中做物理删除
     */
    @Transactional
    public boolean removeEmployee(List<Integer> employeeIds) throws CommonException {

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where(new Condition("id", employeeIds, ValueOp.IN));
        List<UserEmployeeDO> userEmployeeDOList = employeeDao.getDatas(query.buildQuery());
        logger.info("=====取消认证2======="+JSON.toJSONString(userEmployeeDOList));
        if (userEmployeeDOList != null && userEmployeeDOList.size() > 0) {
            for(UserEmployeeDO DO:userEmployeeDOList){
                int userId=DO.getSysuserId();
                int companyId=DO.getCompanyId();
                convertCandidatePerson(userId,companyId);
            }
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
    protected void convertCandidatePerson(int userId,int companyId){
        Query query=new Query.QueryBuilder().where("sys_user_id",userId).and("company_id",companyId).and("status",0).buildQuery();
        List<CandidateCompanyDO> list=candidateCompanyDao.getDatas(query);
        logger.info("CandidateCompanyDO====="+JSON.toJSONString(list));
        if(!StringUtils.isEmptyList(list)){
            for(CandidateCompanyDO DO:list){
                DO.setStatus(1);
            }
            candidateCompanyDao.updateDatas(list);
        }
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
    public List<UserEmployeeDO> getActiveEmployeeDOList(Integer companyId) {
        List<UserEmployeeDO> userEmployeeDOS = new ArrayList<>();
        if (companyId != 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            Query.QueryBuilder queryBuilder = buildFindEmployeeQuery(companyId);
            userEmployeeDOS = employeeDao.getDatas(queryBuilder.buildQuery());
        }
        return userEmployeeDOS;
    }

    /**
     * 分页获取有效员工数据
     * @param companyIdList 公司集合
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 员工集合
     */
    public List<UserEmployeeDO> getActiveEmployeeDOList(List<Integer> companyIdList, int pageNum, int pageSize) {
        List<UserEmployeeDO> userEmployeeDOS = new ArrayList<>();
        if (companyIdList != null && companyIdList.size() > 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            Query.QueryBuilder queryBuilder = buildFindActiveEmployeeQuery(companyIdList);
            queryBuilder.setPageNum(pageNum);
            queryBuilder.setPageSize(pageSize);
            userEmployeeDOS = employeeDao.getDatas(queryBuilder.buildQuery());
        }
        return userEmployeeDOS;
    }

    /**
     * 查找指定公司下的有效员工数量
     * @param companyIdList 公司编号集合
     * @return 有效员工数量
     */
    public int countActiveEmployeeByCompanyIds(List<Integer> companyIdList) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIdList, ValueOp.IN))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        return employeeDao.getCount(queryBuilder.buildQuery());
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
     * 添加员工记录集合。 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
     *
     * @param userEmployeeList 员工记录集合
     * @return 添加好的员工记录。如果参数是空，那么返回值是null
     * @throws CommonException
     */
    public List<UserEmployeeDO> addEmployeeList(List<UserEmployeeDO> userEmployeeList) throws CommonException {
        if (userEmployeeList != null && userEmployeeList.size() > 0) {
            List<UserEmployeeRecord> employeeDOS = employeeDao.addAllRecord(BeanUtils.structToDB(userEmployeeList, UserEmployeeRecord.class));
            // ES 索引更新
            searchengineEntity.updateEmployeeAwards(employeeDOS.stream().map(m -> m.getId()).collect(Collectors.toList()));
            return BeanUtils.DBToStruct(UserEmployeeDO.class, employeeDOS);
        } else {
            return null;
        }
    }

    /**
     * 添加员工记录集合。 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
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
     * 添加员工记录或者员工数据 会向员工记录中添加数据的同时，往ES员工索引维护队列中增加维护员工记录的任务。
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
            searchengineEntity.updateEmployeeAwards(updateDatas.subList(start, updateDatas.size()).stream().map(m -> m.getId()).collect(Collectors.toList()));
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
    public void delete(Condition condition) throws CommonException {
        if (condition != null) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.select("id").where(condition);
            List<UserEmployeeDO> employeeRecordList = employeeDao.getDatas(queryBuilder.buildQuery());
            removeEmployee(employeeRecordList.stream().map(m -> m.getId()).collect(Collectors.toList()));
        } else {
            //do nothing
        }
    }

    /**
     * 根据员工编号查询员工数据
     *
     * @param employeeId 员工编号
     * @return 员工数据
     */
    public UserEmployeeDO getEmployeeByID(int employeeId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.ID.getName(), employeeId)
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), 0)
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        return employeeDao.getData(queryBuilder.buildQuery());
    }

    public int updateData(UserEmployeeDO userEmployeeDO) {
        int result = employeeDao.updateData(userEmployeeDO);
        searchengineEntity.updateEmployeeAwards(Arrays.asList(userEmployeeDO.getId()));
        return result;
    }

    private Query.QueryBuilder buildFindEmployeeQuery(int companyId) {
        // 首先通过CompanyId 查询到该公司集团下所有的公司ID
        List<Integer> companyIds = getCompanyIds(companyId);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.select(UserEmployee.USER_EMPLOYEE.ID.getName())
                .select(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName())
                .select(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName());
        Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
        queryBuilder.where(condition).and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        return queryBuilder;
    }

    private Query.QueryBuilder buildFindActiveEmployeeQuery(List<Integer> companyIdList) {
        // 首先通过CompanyId 查询到该公司集团下所有的公司ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.select(UserEmployee.USER_EMPLOYEE.ID.getName())
                .select(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName())
                .select(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName());
        Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIdList, ValueOp.IN);
        queryBuilder.where(condition).and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue())
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                .and(new Condition(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), 0, ValueOp.NEQ));
        return queryBuilder;
    }

    /**
     *  获取员工认证信息的redisKey <br/>
     *  集团公司： key=userId_groupId, 非集团公司：key=userId-companyId
     */
    public final String getAuthInfoKey(int userId, int companyId) {
        int groupId = getGroupIdByCompanyId(companyId);
        return userId + (groupId == 0 ? "-" + companyId : "_" + groupId);
    }

    public Map<Integer,Integer> getEmployeeAwardSum(Date date){
        return ueprcrDao.handerEmployeeAwards(date);
    }

    public Map<Integer,Integer> getEmployeeNum(List<Integer> companyIds){
        return employeeDao.getEmployeeNum(companyIds);
    }

    public List<UserEmployeeDO> getUserEmployeeByIdList(Set<Integer> idList){
        return employeeDao.getUserEmployeeForidList(idList);
    }

    /**
     * 根据用户编号查找用户的员工信息
     * @param userId 用户编号
     * @return 员工信息
     */
    public UserEmployeeDO getActiveEmployeeDOByUserId(int userId) {
        if (userId > 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), userId)
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
            return employeeDao.getData(queryBuilder.buildQuery());
        } else {
            return null;
        }
    }

    public void followWechat(int userId, int wechatId, long subscribeTime) throws EmployeeException {
        HrWxWechatDO wxWechatDO = wechatDao.fetchWechat(wechatId);
        if (wxWechatDO == null) {
            throw EmployeeException.NODATA_EXCEPTION;
        }
        UserEmployeeDO employeeDO = employeeDao.getUnFollowEmployeeByUserId(userId);
        if (employeeDO == null) {
            throw EmployeeException.NODATA_EXCEPTION;
        }
        if (wxWechatDO.getCompanyId() != employeeDO.getCompanyId()) {
            throw EmployeeException.NODATA_EXCEPTION;
        }
        employeeDao.followWechat(employeeDO.getId(), employeeDO.getSysuserId());
    }

    public void unfollowWechat(int userId, int wechatId, long subscribeTime) throws EmployeeException {
        HrWxWechatDO wxWechatDO = wechatDao.fetchWechat(wechatId);
        if (wxWechatDO == null) {
            throw EmployeeException.NODATA_EXCEPTION;
        }
        UserEmployeeDO employeeDO = getActiveEmployeeDOByUserId(userId);
        if (employeeDO == null) {
            throw EmployeeException.NODATA_EXCEPTION;
        }
        if (wxWechatDO.getCompanyId() != employeeDO.getCompanyId()) {
            throw EmployeeException.NODATA_EXCEPTION;
        }
        employeeDao.unFollowWechat(employeeDO.getId());
    }

    /**
     * 查找员工信息。员工姓名 cname > user_user.name > user_user.nickname > user_wx_user.nickname
     * @param id 员工编号
     * @return 员工信息
     */
    public EmployeeInfo fetchEmployeeInfo(int id) throws EmployeeException {

        UserEmployeeDO userEmployeeDO = getEmployeeByID(id);
        if (userEmployeeDO == null) {
            throw EmployeeException.EMPLOYEE_NOT_EXISTS;
        }
        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setId(id);
        employeeInfo.setAward(userEmployeeDO.getAward());
        employeeInfo.setCompanyId(userEmployeeDO.getCompanyId());
        employeeInfo.setEmployeeActiveState(EmployeeActiveState.instanceFromValue((byte) userEmployeeDO.getActivation()));
        employeeInfo.setName(userEmployeeDO.getCname());
        employeeInfo.setUserId(userEmployeeDO.getSysuserId());
        String name = userEmployeeDO.getCname();
        String headImg = null;

        UserUserDO userUserDO = userUserDao.getUser(userEmployeeDO.getSysuserId());
        if (userUserDO != null) {
            if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                name = org.apache.commons.lang.StringUtils.isNotBlank(userUserDO.getName()) ?
                        userUserDO.getName() : userUserDO.getNickname();
            }
            headImg = userUserDO.getHeadimg();

        } else {
            logger.error("员工编号:{} 对应的用户信息不存在！ 用户编号：{}", id, userEmployeeDO.getSysuserId());
        }
        if (org.apache.commons.lang.StringUtils.isBlank(name) || org.apache.commons.lang.StringUtils.isBlank(headImg)) {
            UserWxUserRecord wxUserDO = userWxUserDao.getWXUserByUserId(userUserDO.getId());
            if (wxUserDO != null) {
                if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                    name = wxUserDO.getNickname();
                }
                if (org.apache.commons.lang.StringUtils.isBlank(headImg)) {
                    headImg = wxUserDO.getHeadimgurl();
                }
            }
        }

        employeeInfo.setName(name);
        employeeInfo.setHeadImg(headImg);
        return employeeInfo;
    }
}
