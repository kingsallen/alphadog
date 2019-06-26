package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.dao.candidatedb.CandidateApplicationReferralDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.redpacketdb.RedpacketActivityPositionJOOQDao;
import com.moseeker.baseorm.dao.referraldb.*;
import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryUserEmployeeRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.*;
import static com.moseeker.baseorm.db.userdb.tables.UserEmployee.USER_EMPLOYEE;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.Constant.EmployeeType;
import com.moseeker.entity.exception.EmployeeException;
import com.moseeker.entity.exception.ExceptionCategory;
import com.moseeker.entity.exception.ExceptionFactory;
import com.moseeker.entity.pojos.EmployeeInfo;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateApplicationReferralDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.*;
import com.moseeker.thrift.gen.employee.struct.BonusVO;
import com.moseeker.thrift.gen.employee.struct.BonusVOPageVO;
import com.moseeker.thrift.gen.employee.struct.RewardVO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
@CounterIface
public class EmployeeEntity {

    @Autowired
    private ReferralEmployeeNetworkResourcesDao networkResourcesDao;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private Environment env;

    @Autowired
    private HrGroupCompanyRelDao hrGroupCompanyRelDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private UserEmployeePointsRecordDao ueprDao;

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
    CandidateApplicationReferralDao applicationPscDao;
    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    private ReferralCompanyConfDao referralCompanyConfDao;

    @Autowired
    private ReferralEmployeeBonusRecordDao referralEmployeeBonusRecordDao;

    @Autowired
    ReferralApplicationStatusCountDao referralApplicationStatusCountDao;

    @Autowired
    private ReferralEmployeeRegisterLogDao referralEmployeeRegisterLogDao;

    @Autowired
    private ReferralPositionBonusStageDetailDao referralPositionBonusStageDetailDao;

    @Autowired
    JobApplicationDao applicationDao;

    @Autowired
    JobPositionDao jobPositionDao;

    @Autowired
    RedpacketActivityPositionJOOQDao activityPositionJOOQDao;

    private DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AmqpTemplate amqpTemplate;

    ThreadPool tp =ThreadPool.Instance;

    @Resource(name = "cacheClient")
    private RedisClient client;

    private static final String APLICATION_STATE_CHANGE_EXCHNAGE = "cvpass_exchange";
    private static final String APLICATION_STATE_CHANGE_ROUTINGKEY = "cvpass_exchange.redpacket";

    private static final String ADD_BONUS_CHANGE_EXCHNAGE = "add_bonus_change_exchange";
    private static final String ADD_BONUS_CHANGE_ROUTINGKEY = "add_bonus_change_routingkey.add_bonus";

    private static final String EMPLOYEE_ACTIVATION_CHANGE_NEO4J_EXCHNAGE = "employee_neo4j_exchange";
    private static final String EMPLOYEE_ACTIVATION_CHANGE_NEO4J_ROUTINGKEY = "user_neo4j.employee_company_update";

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEntity.class);

    /**
     * 判断某个用户是否属于某个公司的员工
     *
     * @param userId
     * @param companyId 如果公司属于集团公司，需验证用户是否在集团下的所有公司中认证过员工
     * @redpacket_exchange
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
                .and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState());
        return employeeDao.getData(query.buildQuery());
    }

    // 转发点击操作 前置
    @Transactional
    public void addAwardBefore(int employeeId, int companyId, int positionId, int templateId, int berecomUserId,
                               int applicationId) throws Exception {
        // for update 对employeee信息加行锁 避免多个端同时对同一个用户加积分
        logger.info("addAwardHandler");
        ReferralCompanyConf companyConf = referralCompanyConfDao.fetchOneByCompanyId(companyId);
        if (companyConf != null && companyConf.getPositionPointsFlag() != null
                && companyConf.getPositionPointsFlag() == 1) {
            logger.info("addAwardHandler 有配置信息");
            JobPositionPojo positionPojo = positionDao.getPosition(positionId);
            if (positionPojo != null) {
                logger.info("addAwardBefore positionPojo is_referral:{}", positionPojo.is_referral);
            } else {
                logger.info("addAwardBefore positionPojo is null!");
            }
            if (positionPojo != null && positionPojo.is_referral == 0) {
                logger.info("公司开启只针对内推职位奖励，并且职位不是内推职位，所以不做积分奖励操作！");
                return;
            }
        }
        // for update 对employeee信息加行锁 避免多个端同时对同一个用户加积分
        employeeDao.getUserEmployeeForUpdate(employeeId);
        logger.info("addAwardHandler 锁表");
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", companyId).and("template_id", templateId);
        HrPointsConfDO hrPointsConfDO = hrPointsConfDao.getData(query.buildQuery());

        int awardConfigId = hrPointsConfDO == null ? 0 : hrPointsConfDO.getId();
        query.clear();
        query.where("employee_id", employeeId).and("position_id", positionId).and("award_config_id", awardConfigId).and("berecom_user_id", berecomUserId);
        UserEmployeePointsRecordDO userEmployeePointsRecordDO = ueprDao.getData(query.buildQuery());
        logger.info("addAwardHandler 查找记录");
        if (userEmployeePointsRecordDO != null && userEmployeePointsRecordDO.getId() > 0) {
            logger.warn("重复的加积分操作, employeeId:{}, positionId:{}, templateId:{}, berecomUserId:{}", employeeId, positionId, templateId, berecomUserId);
            throw EmployeeException.EMPLOYEE_AWARD_REPEAT_PLUS;
        }
        logger.info("addAwardHandler 添加积分");
        // 进行加积分操作
        addReward(employeeId, companyId, "", applicationId, positionId, templateId, berecomUserId);
    }


    /**
     * 增加员工积点
     *
     * @param employeeId
     * @return 员工信息
     */
    @Transactional(rollbackFor = Exception.class)
    public UserEmployeePointsRecordDO addRewardReturnPointDO(int employeeId, int companyId, UserEmployeePointsRecordDO ueprDo) throws EmployeeException {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId).and("disable", 0).and("activation", 0);
        UserEmployeeDO userEmployeeDO = employeeDao.getUserEmployeeForUpdate(employeeId);
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0 && ueprDo != null) {
            // 修改用户总积分, 积分不能扣成负数
            int totalAward = userEmployeeDO.getAward() + ueprDo.getAward();
            if (totalAward < 0) {
                logger.error("增加用户积分失败，用户积分不足：为用户{},用户当前积分{}点,添加积分{}点, reason:{}", employeeId, userEmployeeDO.getAward(), ueprDo.getAward(), ueprDo.getReason());
                throw EmployeeException.EMPLOYEE_AWARD_NOT_ENOUGH;
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
                } else {
                    logger.error("增加用户积分失败：为用户{},添加积分{}点, reason:{}", employeeId, ueprDo.getAward(), ueprDo.getReason());
                    throw EmployeeException.EMPLOYEE_AWARD_ADD_FAILED;
                }
            }
        }
        return ueprDo;
    }


    /**
     * 增加员工积点
     *
     * @param employeeId
     * @return 员工当前总积分
     */
    @Transactional
    public int addReward(int employeeId, int companyId, UserEmployeePointsRecordDO ueprDo) throws EmployeeException {
        logger.info("addReward employeeId:{}, companyId:{}, ueprdDo:{}", employeeId, companyId, ueprDo);
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId).and("disable", 0).and("activation", 0);
        UserEmployeeDO userEmployeeDO = employeeDao.getUserEmployeeForUpdate(employeeId);
        logger.info("addReward userEmployeeDO:{}", userEmployeeDO);
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0 && ueprDo != null) {
            logger.info("addReward  userEmployee exist!");
            // 修改用户总积分, 积分不能扣成负数
            int totalAward = userEmployeeDO.getAward() + ueprDo.getAward();
            logger.info("addReward  userEmployee totalAward:{}", totalAward);
            if (totalAward < 0) {
                logger.error("增加用户积分失败，用户积分不足：为用户{},用户当前积分{}点,添加积分{}点, reason:{}", employeeId, userEmployeeDO.getAward(), ueprDo.getAward(), ueprDo.getReason());
                throw EmployeeException.EMPLOYEE_AWARD_NOT_ENOUGH;
            }
            int row = employeeDao.addAward(userEmployeeDO.getId(), totalAward, userEmployeeDO.getAward());
            logger.info("addReward  userEmployee row:{}", row);
            // 积分记录
            if (row > 0) {
                ueprDo = ueprDao.addData(ueprDo);
                logger.info("addReward  userEmployee ueprDo:{}", ueprDo);
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
                    throw EmployeeException.EMPLOYEE_AWARD_ADD_FAILED;
                }
            }
        } else {
            logger.info("addReward  userEmployee not exist!");
        }
        return 0;
    }

    @Transactional
    public boolean addReward(int employeeId, int companyId, String reason, int applicationId, int positionId, int templateId, int berecomUserId) throws Exception {
        // 获取积分点数
        if (companyId == 0 || templateId == 0) {
            throw EmployeeException.PROGRAM_PARAM_NOTEXIST;
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
                    throw EmployeeException.EMPLOYEE_AWARD_ZERO;
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
     * 员工认证，新增员工认证积分并且修改认证时间
     * @param employeeId 员工编号
     * @param companyId 公司编号
     * @return true 修改成功；false 修改失败
     * @throws EmployeeException
     */
    public boolean addRewardByEmployeeVerified(int employeeId, int companyId) throws EmployeeException {

        logger.info("addRewardByEmployeeVerified employeeId:{}, companyId:{}", employeeId, companyId);
        HrPointsConfRecord record = hrPointsConfDao.getEmployeeVerified(companyId);
        String reason;
        int award;
        if (record == null) {
            ConfigSysPointsConfTplRecord configSysPointsConfTplRecord = configSysPointsConfTplDao.getEmployeeVerified();
            if (configSysPointsConfTplRecord == null) {
                throw EmployeeException.EMPLOYEE_VERIFY_AWARD_NOT_EXIST;
            }
            reason = configSysPointsConfTplRecord.getStatus();
            award = configSysPointsConfTplRecord.getAward();
        } else {
            reason = record.getStatusName();
            award = record.getReward().intValue();
        }

        logger.info("addRewardByEmployeeVerified reason:{}, award:{}", reason, award);
        UserEmployeePointsRecordDO ueprDo = new UserEmployeePointsRecordDO();
        ueprDo.setReason(reason);
        ueprDo.setAward(award);
        ueprDo.setApplicationId(0);
        ueprDo.setAwardConfigId(0);
        ueprDo.setBerecomUserId(0);
        ueprDo.setPositionId(0);
        ueprDo.setEmployeeId(employeeId);
        addReward(employeeId, companyId, ueprDo);
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
            query.where(new Condition(USER_EMPLOYEE.SYSUSER_ID.getName(), berecomIds, ValueOp.IN))
                    .and(USER_EMPLOYEE.ACTIVATION.getName(), 0)
                    .and(new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN));
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
                    } else if (reason.indexOf("完成员工认证") > -1) {
                        reward.setType(14);
                    } else if (reason.indexOf("员工上传人才简历") > -1) {
                        reward.setType(15);
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
    @Transactional
    public boolean unbind(Collection<Integer> employeeIds) throws CommonException {
        logger.info("EmployeeEntity unbind employeeIds:{}", JSONObject.toJSONString(employeeIds));
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.and(new Condition("id", employeeIds, ValueOp.IN))
                .and(USER_EMPLOYEE.ACTIVATION.getName(), 0);
        List<UserEmployeeDO> employeeDOList = employeeDao.getDatas(query.buildQuery());
        employeeDOList.forEach(userEmployeeDO -> userEmployeeDO.setUnbindTime(LocalDateTime.now().format(sdf)));
        boolean result = unbind(employeeDOList);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<ReferralEmployeeRegisterLog> logs = employeeIds
                .stream()
                .distinct()
                .map(integer -> {
                    ReferralEmployeeRegisterLog log = new ReferralEmployeeRegisterLog();
                    log.setEmployeeId(integer);
                    log.setRegister((byte)0);
                    log.setOperateTime(timestamp);
                    return log;
                })
                .collect(Collectors.toList());
        referralEmployeeRegisterLogDao.insert(logs);
        return result;
    }

    /**
     * 员工取消认证（支持批量）-- 重载
     *
     * @param employees
     * @return
     */

    public boolean unbind(List<UserEmployeeDO> employees) throws CommonException {
        logger.info("EmployeeEntity unbind employees:{}", JSONObject.toJSONString(employees));
        if (employees != null && employees.size() > 0) {
            String now = DateUtils.dateToShortTime(new Date());
            List<Integer> idList = employees.stream()
                    .filter(f -> f.getActivation() == EmployeeType.AUTH_SUCCESS.getValue())
                    .map(employee ->employee.getSysuserId()).collect(Collectors.toList());
            employees.stream().filter(f -> f.getActivation() == 0).forEach(e -> {
                e.setActivation((byte) 1);
                e.setUnbindTime(now);
            });
            logger.info("EmployeeEntity unbind after change employees:{}", JSONObject.toJSONString(employees));
            for (UserEmployeeDO DO : employees) {
                int userId = DO.getSysuserId();
                int companyId = DO.getCompanyId();
                convertCandidatePerson(userId, companyId);
            }
            int[] rows = employeeDao.updateDatas(employees);
            logger.info("EmployeeEntity unbind rows:{}", rows.length);
            if (Arrays.stream(rows).sum() > 0) {
                // 更新ES中useremployee信息
                List<Integer> employeeIdList = employees
                        .stream()
                        .map(UserEmployeeDO::getId).filter(id -> id > 0)
                        .collect(Collectors.toList());
                logger.info("EmployeeEntity unbind employeeIdList:{}", JSONObject.toJSONString(employeeIdList));
                searchengineEntity.updateEmployeeAwards(employeeIdList, false);
                List<Integer> companyIdList = employees
                        .stream()
                        .map(UserEmployeeDO::getCompanyId).distinct().filter(id -> id > 0)
                        .collect(Collectors.toList());

                companyIdList.forEach(companyId -> {
                    client.set(Constant.APPID_ALPHADOG, KeyIdentifier.USER_EMPLOYEE_UNBIND.toString(),
                            String.valueOf(companyId),  JSON.toJSONString(employeeIdList));
                });
                networkResourcesDao.updateNetworkResourcesRecordByPosyUserIds(idList, (byte)Constant.DISABLE_OLD);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("companyId", 0);
                jsonObject.put("userIds", idList);
                logger.info("employeeActivationChange :jsonObject{}", jsonObject);
                amqpTemplate.sendAndReceive(EMPLOYEE_ACTIVATION_CHANGE_NEO4J_EXCHNAGE,
                        EMPLOYEE_ACTIVATION_CHANGE_NEO4J_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                                .build());

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
        Map<Integer, List<Integer>> params = this.handerUserEmployee(userEmployeeDOList);
        logger.info("=====取消认证2=======" + JSON.toJSONString(userEmployeeDOList));
        if (userEmployeeDOList != null && userEmployeeDOList.size() > 0) {
            for (UserEmployeeDO DO : userEmployeeDOList) {
                int userId = DO.getSysuserId();
                int companyId = DO.getCompanyId();
                convertCandidatePerson(userId, companyId);
            }
            int[] rows = employeeDao.deleteDatas(userEmployeeDOList);
            // 受影响行数大于零，说明删除成功， 将数据copy到history_user_employee中
            if (Arrays.stream(rows).sum() > 0) {
                List<HistoryUserEmployeeRecord> historyUserEmployeeRecords = userEmployeeDOList.stream()
                        .map(userEmployeeDO -> {
                            HistoryUserEmployeeRecord record = new HistoryUserEmployeeRecord();
                            org.springframework.beans.BeanUtils.copyProperties(userEmployeeDO, record);
                            return record;
                        }).collect(Collectors.toList());
                historyUserEmployeeDao.addAllRecord(historyUserEmployeeRecords);
                tp.startTast(() -> {
                    // 更新ES中useremployee信息
                    this.deleteEsEmployee(employeeIds);
                    List<Integer> idList = userEmployeeDOList.stream()
                            .filter(f -> f.getActivation() == EmployeeType.AUTH_SUCCESS.getValue())
                            .map(employee ->employee.getSysuserId()).collect(Collectors.toList());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("companyId", 0);
                    jsonObject.put("userIds", idList);
                    logger.info("employeeActivationChange :jsonObject{}", jsonObject);
                    amqpTemplate.sendAndReceive(EMPLOYEE_ACTIVATION_CHANGE_NEO4J_EXCHNAGE,
                            EMPLOYEE_ACTIVATION_CHANGE_NEO4J_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                                    .build());
                    return 0;
                });
                if(params != null){
                    Set<Integer> entry = params.keySet();
                    for(Integer companyId : entry){
                        List<Integer> list = params.get(companyId);
                        client.set(Constant.APPID_ALPHADOG, KeyIdentifier.USER_EMPLOYEE_DELETE.toString(),
                                String.valueOf(companyId),  JSON.toJSONString(list));
                    }
                }
                return true;
            } else {
                throw ExceptionFactory.buildException(ExceptionCategory.EMPLOYEE_HASBEENDELETEOR);
            }
        }
        return false;
    }

    private void deleteEsEmployee(List<Integer> employeeIds)   {
        Response result = searchengineEntity.deleteEmployeeDO(employeeIds);
        if(Constant.OK != result.getStatus()){
            logger.error("批量删除员工信息ES部分失败，员工编号:{}",employeeIds);
            WarnBean warn = new WarnBean(String.valueOf(Constant.APPID_ALPHADOG),Constant.EMPLOYEE_BATCH_DELETE_FAILED,
                    null, "批量删除员工信息ES部分失败，员工编号"+StringUtils.listToString(employeeIds,","), "");
            try {
                String client = HttpClient.sendPost(env.getProperty("http.api.url")+"sendWarn", JSON.toJSONString(warn));
            } catch (ConnectException e) {
                logger.error("sendOperator error:", e);
            }

        }
    }

    private Map<Integer, List<Integer>> handerUserEmployee(List<UserEmployeeDO> employees){

        if(!StringUtils.isEmptyList(employees)){
            Map<Integer, List<Integer>> params = new HashMap<>();
            for(UserEmployeeDO employee : employees){
                Integer companyId = employee.getCompanyId();
                List<Integer> list = params.get(companyId);
                if(list == null){
                    list = new ArrayList<>();
                }
                list.add(employee.getId());
                params.put(companyId, list);
            }
            return params;
        }
        return null;
    }

    protected void convertCandidatePerson(int userId, int companyId) {
        Query query = new Query.QueryBuilder().where("sys_user_id", userId).and("company_id", companyId).and("status", 0).buildQuery();
        List<CandidateCompanyDO> list = candidateCompanyDao.getDatas(query);
        logger.info("CandidateCompanyDO=====" + JSON.toJSONString(list));
        if (!StringUtils.isEmptyList(list)) {
            for (CandidateCompanyDO DO : list) {
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
            companyIdCondition = new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds.get(0), ValueOp.EQ);
        } else {
            companyIdCondition = new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
        }
        Condition userEmployeeId = null;
        if (userEmployeeIds.size() == 1) {
            userEmployeeId = new Condition(USER_EMPLOYEE.ID.getName(), userEmployeeIds.get(0), ValueOp.EQ);
        } else {
            userEmployeeId = new Condition(USER_EMPLOYEE.ID.getName(), userEmployeeIds, ValueOp.IN);
        }
        // 查询公司的员工ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(companyIdCondition).and(userEmployeeId)
                .and(USER_EMPLOYEE.DISABLE.getName(), 0);

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
        long startTime = System.currentTimeMillis();

        List<Integer> list = new ArrayList<>();
        logger.info("compangId:{}", companyId);
        // 查询集团ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.COMPANY_ID.getName(), companyId);
        HrGroupCompanyRelDO hrGroupCompanyRelDO = hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
        // 没有集团信息，返回当前companyId
        long groupCompanyRelTime = System.currentTimeMillis();
        logger.info("profile tab getCompanyIds groupCompanyRelTime:{}", groupCompanyRelTime- startTime);
        if (hrGroupCompanyRelDO == null) {
            logger.info("未查询到该公司的集团ID");
            list.add(companyId);
            return list;
        }
        queryBuilder.clear();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.GROUP_ID.getName(), hrGroupCompanyRelDO.getGroupId());
        List<HrGroupCompanyRelDO> hrGroupCompanyRelDOS = hrGroupCompanyRelDao.getDatas(queryBuilder.buildQuery());
        long groupCompanyRelEndTime = System.currentTimeMillis();
        logger.info("profile tab getCompanyIds groupCompanyRelEndTime:{}", groupCompanyRelEndTime - groupCompanyRelTime);

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
        queryBuilder.where(USER_EMPLOYEE.SYSUSER_ID.getName(), userId);
        queryBuilder.and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeType.AUTH_SUCCESS.getValue());
        queryBuilder.and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
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
     * 通过公司ID查集团下所有的员工列表
     *
     * @param companyId
     * @return
     */
    public Set<Integer> getActiveEmployeeUserIdList(Integer companyId) {
        List<UserEmployeeDO> employeeDOList = employeeDao.getEmployeeBycompanyId(companyId);
        if(StringUtils.isEmptyList(employeeDOList)){
            return new HashSet<>();
        }
        return employeeDOList.stream().map(m -> m.getSysuserId()).collect(Collectors.toSet());
    }

    /**
     * 分页获取有效员工数据
     *
     * @param companyIdList 公司集合
     * @param pageNum       页码
     * @param pageSize      每页数量
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
     *
     * @param companyIdList 公司编号集合
     * @return 有效员工数量
     */
    public int countActiveEmployeeByCompanyIds(List<Integer> companyIdList) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIdList, ValueOp.IN))
                .and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                .and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
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
            queryBuilder.select(USER_EMPLOYEE.ID.getName())
                    .select(USER_EMPLOYEE.COMPANY_ID.getName())
                    .select(USER_EMPLOYEE.SYSUSER_ID.getName());
            Condition condition = new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
            queryBuilder.where(condition)
                    .and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue())
                    .and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeType.AUTH_SUCCESS.getValue());
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
            List<UserEmployeeRecord> employeeDOS = new ArrayList<>();
            for(UserEmployeeDO employee : userEmployeeList){
                UserEmployeeRecord record = BeanUtils.structToDB(employee, UserEmployeeRecord.class);
                record.setAuthMethod(Constant.AUTH_METHON_TYPE_CUSTOMIZE);
                record = employeeDao.addRecord(record);
                employeeDOS.add(record);
            }
            // ES 索引更新
            searchengineEntity.updateEmployeeAwards(employeeDOS.stream().map(m -> m.getId()).collect(Collectors.toList()), false);
            return BeanUtils.DBToStruct(UserEmployeeDO.class, employeeDOS);
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

        searchengineEntity.updateEmployeeAwards(Arrays.asList(employeeDO.getId()), false);

        return employeeDO;
    }

    /**
     * 根据员工编号查询员工数据
     *
     * @param employeeId 员工编号
     * @return 员工数据
     */
    public UserEmployeeDO getEmployeeByID(int employeeId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(USER_EMPLOYEE.ID.getName(), employeeId)
                .and(USER_EMPLOYEE.ACTIVATION.getName(), 0)
                .and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        return employeeDao.getData(queryBuilder.buildQuery());
    }

    public int updateData(UserEmployeeDO userEmployeeDO) {
        int result = employeeDao.updateData(userEmployeeDO);
        searchengineEntity.updateEmployeeAwards(Arrays.asList(userEmployeeDO.getId()), true);
        return result;
    }

    private Query.QueryBuilder buildFindEmployeeQuery(int companyId) {
        // 首先通过CompanyId 查询到该公司集团下所有的公司ID
        List<Integer> companyIds = getCompanyIds(companyId);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.select(USER_EMPLOYEE.ID.getName())
                .select(USER_EMPLOYEE.COMPANY_ID.getName())
                .select(USER_EMPLOYEE.SYSUSER_ID.getName());
        Condition condition = new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
        queryBuilder.where(condition).and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        return queryBuilder;
    }

    private Query.QueryBuilder buildFindActiveEmployeeQuery(List<Integer> companyIdList) {
        // 首先通过CompanyId 查询到该公司集团下所有的公司ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.select(USER_EMPLOYEE.ID.getName())
                .select(USER_EMPLOYEE.COMPANY_ID.getName())
                .select(USER_EMPLOYEE.SYSUSER_ID.getName());
        Condition condition = new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIdList, ValueOp.IN);
        queryBuilder.where(condition).and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue())
                .and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                .and(new Condition(USER_EMPLOYEE.SYSUSER_ID.getName(), 0, ValueOp.NEQ));
        return queryBuilder;
    }

    /**
     * 获取员工认证信息的redisKey <br/>
     * 集团公司： key=userId_groupId, 非集团公司：key=userId-companyId
     */
    public final String getAuthInfoKey(int userId, int companyId) {
        int groupId = getGroupIdByCompanyId(companyId);
        return userId + (groupId == 0 ? "-" + companyId : "_" + groupId);
    }

    public Map<Integer, Integer> getEmployeeAwardSum(Date date) {
        return ueprcrDao.handerEmployeeAwards(date);
    }

    public Map<Integer, Integer> getEmployeeNum(List<Integer> companyIds) {
        return employeeDao.getEmployeeNum(companyIds);
    }

    public List<UserEmployeeDO> getUserEmployeeByIdList(Set<Integer> idList) {
        return employeeDao.getUserEmployeeForidList(idList);
    }

    /**
     * 根据用户编号查找用户的员工信息
     *
     * @param userId 用户编号
     * @return 员工信息
     */
    public UserEmployeeDO getActiveEmployeeDOByUserId(int userId) {
        if (userId > 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(USER_EMPLOYEE.SYSUSER_ID.getName(), userId)
                    .and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                    .and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
            return employeeDao.getData(queryBuilder.buildQuery());
        } else {
            return null;
        }
    }

    /**
     * 根据用户编号查找用户的员工信息
     *
     * @param userId 用户编号
     * @return 员工信息
     */
    public UserEmployeeDO getActiveEmployeeDOByUserId(int userId,Set<Integer> companyIds) {
        if (userId > 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(USER_EMPLOYEE.SYSUSER_ID.getName(), userId)
                    .and(USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState())
                    .and(USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue())
                    .and(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds);
            return employeeDao.getData(queryBuilder.buildQuery());
        } else {
            return null;
        }
    }
    public void followWechat(int userId, int wechatId, long subscribeTime) throws EmployeeException {
        if(userId <= 0 || wechatId <= 0){
            throw EmployeeException.NODATA_EXCEPTION;
        }
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
        DateTime currentDateTime = new DateTime(subscribeTime);
        employeeDao.followWechat(employeeDO.getId(), employeeDO.getSysuserId(), currentDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        referralEmployeeRegisterLogDao.addRegisterLog(employeeDO.getId(), new DateTime(subscribeTime));
        searchengineEntity.updateEmployeeAwards(new ArrayList<Integer>() {{
            add(employeeDO.getId());
        }}, false);

    }

    public void unfollowWechat(int userId, int wechatId, long subscribeTime) throws EmployeeException {
        if(userId <= 0 || wechatId <= 0){
            throw EmployeeException.NODATA_EXCEPTION;
        }
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
        referralEmployeeRegisterLogDao.addCancelLog(employeeDO.getId(), new DateTime(subscribeTime));
        searchengineEntity.updateEmployeeAwards(new ArrayList<Integer>() {{
            add(employeeDO.getId());
        }}, false);
    }

    /**
     * 查找员工信息。员工姓名 cname > user_user.name > user_user.nickname > user_wx_user.nickname
     *
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

    public void publishInitalScreenHbEvent(JobApplication jobApplication, JobPositionRecord jobPositionRecord,
                                           Integer userId, Integer nextStage){
        logger.info("publishInitalScreenHbEvent  jobApplication {}",jobApplication);
        logger.info("publishInitalScreenHbEvent  jobPositionRecord {}",jobPositionRecord);
        logger.info("EmployeeEntity publishInitalScreenHbEvent");
        if(jobApplication != null && jobPositionRecord != null) {
            int hbStatus = jobPositionRecord.getHbStatus();
            logger.info("publishInitalScreenHbEvent  nextStage {}",nextStage);
            boolean inActivity = activityPositionJOOQDao.isInActivity(jobPositionRecord.getId());
            logger.info("publishInitalScreenHbEvent  bool {}",((hbStatus >> 2) & 1) == 1);
            if (inActivity && nextStage == Constant.RECRUIT_STATUS_CVPASSED) {
                ConfigSysPointsConfTplRecord confTplDO = configSysPointsConfTplDao.getTplById(nextStage);
                ReferralApplicationStatusCount statusCount = referralApplicationStatusCountDao
                        .fetchApplicationStatusCountByAppicationIdAndTplId(confTplDO.getId(), jobApplication.getId());
                logger.info("EmployeeEntity publishInitalScreenHbEvent statusCount:{}",statusCount);
                if(statusCount == null){
                    statusCount = new ReferralApplicationStatusCount();
                    statusCount.setAppicationTplStatus(confTplDO.getId());
                    statusCount.setApplicationId(jobApplication.getId());
                    statusCount.setCount(1);
                    int result = referralApplicationStatusCountDao.addReferralApplicationStatusCount(statusCount);
                    if(result >0 ){

                        JSONObject eventMessage = new JSONObject();
                        eventMessage.put("name", "application cvpass");
                        eventMessage.put("ID", UUID.randomUUID().toString());
                        eventMessage.put("hr_id", jobPositionRecord.getPublisher());
                        eventMessage.put("application_id", jobApplication.getId());
                        eventMessage.put("recommend_user_id", jobApplication.getRecommenderUserId());
                        eventMessage.put("position_id", jobPositionRecord.getId());
                        eventMessage.put("applier_id", jobApplication.getApplierId());
                        eventMessage.put("cvpass_time", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                        eventMessage.put("company_id", jobApplication.getCompanyId());
                        logger.info("EmployeeEntity publishInitalScreenHbEvent param:{}, exchange:{}, routing:{}",
                                eventMessage.toJSONString(), APLICATION_STATE_CHANGE_EXCHNAGE, APLICATION_STATE_CHANGE_ROUTINGKEY);
                        amqpTemplate.sendAndReceive(APLICATION_STATE_CHANGE_EXCHNAGE,
                                APLICATION_STATE_CHANGE_ROUTINGKEY, MessageBuilder.withBody(eventMessage.toJSONString().getBytes())
                                        .build());
                    }
                }else{
                    int i =0;
                    while (i<3){
                        statusCount = referralApplicationStatusCountDao
                                .fetchApplicationStatusCountByAppicationIdAndTplId(confTplDO.getId(), jobApplication.getId());
                        int count = statusCount.getCount();
                        statusCount.setCount(count+1);
                        int result = referralApplicationStatusCountDao.updateReferralApplicationStatusCount(statusCount);
                        if(result > 0){
                            break;
                        }
                        i++;
                    }
                }
            }
        }
    }


    /**
     *
     * @param applicationId
     * @param nowStage
     * @param nextStage
     * @param move
     * @param positionId
     * @param applierId
     * @throws Exception
     */
    @Transactional
    public void addReferralBonus(Integer applicationId, Integer nowStage, Integer nextStage, Integer move,Integer positionId,Integer applierId) throws Exception {

        logger.info("EmployeeEntity addReferralBonus");
        JobApplication jobApplication = applicationDao.fetchOneById(applicationId);

        JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById(positionId);
        Integer userId = jobApplication.getRecommenderUserId();
        logger.info("EmployeeEntity addReferralBonus nextStage:{}", nextStage);
        if(nextStage == Constant.RECRUIT_STATUS_CVPASSED) {
            tp.startTast(() -> {
                this.publishInitalScreenHbEvent(jobApplication, jobPositionRecord, userId, nextStage);
                return 0;
            });
            return;
        }
        //如果职位不是一个内推职位(is_referral=0), 直接返回不做后续操作
        if(jobPositionRecord == null || Integer.valueOf(jobPositionRecord.getIsReferral()).equals(0)) {
            logger.info("addReferralBonus 不是内推职位 不发内推奖金 positionId {}  isReferral {} ",positionId,jobPositionRecord.getIsReferral());
            return;
        }
        //现在节点奖金主数据
        ReferralPositionBonusStageDetail nowStageDetail = referralPositionBonusStageDetailDao.fetchByReferralPositionIdAndStageType(positionId,nowStage);

        //下个节点奖金主数据
        ReferralPositionBonusStageDetail nextStageDetail = referralPositionBonusStageDetailDao.fetchByReferralPositionIdAndStageType(positionId,nextStage);
        UserEmployeeRecord userEmployeeRecord = employeeDao.getActiveEmployeeByUserId(userId);
        if(userEmployeeRecord == null) {

            logger.info("addReferralBonus 不是已认证员工,不能发内推奖金 employeeId {}",userId);
            throw new BIZException(-1, userId +" 不是已认证员工,不能发内推奖金");
        }

        Integer employeeId = Integer.valueOf(userEmployeeRecord.getId());


        LocalDateTime localDateTime = LocalDateTime.now();
        ReferralEmployeeBonusRecord referralEmployeeBonusRecord = new ReferralEmployeeBonusRecord();
        UserEmployeeDO userEmployeeDO = employeeDao.getUserEmployeeForUpdate(employeeId);
        Integer employeeBonus =  userEmployeeDO.getBonus();

        logger.info("addReferralBonus params  applicationId {} nowStage {} nextStage {} move {} positionId {}  employeeId {} userId {} employeeBonus {} applierId{}",
                applicationId,nowStage,nextStage,move,positionId,employeeId,userId,employeeBonus,applierId);

        //添加奖金
        if(nextStageDetail !=null && move == 1 ) {

            ReferralEmployeeBonusRecord latestOne = referralEmployeeBonusRecordDao.fetchByEmployeeIdStageDetailIdLastOne(employeeId, nextStageDetail.getId(),applicationId);
            //取id最大的一条,如果此节点已经有一条最新的增加奖金记录 直接返回
            if(latestOne != null && latestOne.getBonus() > 0 ) {
                return;
            }

            Integer stageBonus  = nextStageDetail.getStageBonus();

            Integer newBonus  = employeeBonus + stageBonus;

            if(newBonus >=0) {
                try{
                    //更新员工总奖金
                    userEmployeeDO.setBonus(newBonus);
                    userEmployeeDO.setUpdateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                    employeeDao.updateData(userEmployeeDO);

                    //ES更新员工总奖金
                    Response response  = searchengineEntity.updateEmployeeBonus(Lists.newArrayList(employeeId),newBonus);
                    logger.info("addReferralBonus es response {}",JSON.toJSONString(response));
                    // 添加员工发放奖金记录
                    referralEmployeeBonusRecord.setBonusStageDetailId(nextStageDetail.getId());
                    referralEmployeeBonusRecord.setBonus(stageBonus);
                    referralEmployeeBonusRecord.setEmployeeId(employeeId);
                    referralEmployeeBonusRecord.setApplicationId(applicationId);
                    referralEmployeeBonusRecord.setClaim((byte)0);
                    referralEmployeeBonusRecord.setCreateTime(Timestamp.valueOf(localDateTime));
                    referralEmployeeBonusRecord.setUpdateTime(Timestamp.valueOf(localDateTime));
                    referralEmployeeBonusRecord.setDisable(0);
                    referralEmployeeBonusRecordDao.insert(referralEmployeeBonusRecord);
                    DateTime dateTime = new DateTime();
                    publishAddBonusChangeEvent(applicationId,nowStage,nextStage,applierId,positionId,move.byteValue(),dateTime);
                    }catch (Exception e) {
                        logger.error(e.getClass().getName(),e);
                    }

            }
        }

        //减少奖金
        if( move == 0 &&  nowStageDetail!=null) {

            ReferralEmployeeBonusRecord latestOne = referralEmployeeBonusRecordDao.fetchByEmployeeIdStageDetailIdLastOne(employeeId, nowStageDetail.getId(),applicationId);
            //取id最大的一条 如果此节点已经有一条最新的扣减记录 直接返回
            if(latestOne != null && latestOne.getBonus() < 0 ) {
                return;
            }

            //获取用户当前节点发放的奖金,
            ReferralEmployeeBonusRecord recordGTZero = referralEmployeeBonusRecordDao.fetchByEmployeeIdStageDetailIdGTZero(employeeId, nowStageDetail.getId(),applicationId);

            //如果有该节点发放奖金,复制一条，将奖金设为负存入DB
            if(recordGTZero !=null) {
                ReferralEmployeeBonusRecord newRecord = new ReferralEmployeeBonusRecord();

                Integer stageBonus = recordGTZero.getBonus()* -1;
                Integer newBonus  =employeeBonus + stageBonus;

                //更新员工总奖金
                userEmployeeDO.setBonus(newBonus);
                employeeDao.updateData(userEmployeeDO);

                //ES更新员工总奖金
                searchengineEntity.updateEmployeeBonus(Lists.newArrayList(employeeId),newBonus);

                // 添加员工扣减奖金记录
                newRecord.setBonusStageDetailId(recordGTZero.getBonusStageDetailId());
                newRecord.setBonus(recordGTZero.getBonus() * -1);
                newRecord.setEmployeeId(recordGTZero.getEmployeeId());
                newRecord.setApplicationId(recordGTZero.getApplicationId());
                newRecord.setClaim((byte)1);
                newRecord.setCreateTime(Timestamp.valueOf(localDateTime));
                newRecord.setUpdateTime(Timestamp.valueOf(localDateTime));
                newRecord.setDisable(0);
                referralEmployeeBonusRecordDao.insert(newRecord);

                // 将上笔添加入职奖金设置为不可领取disable=1
                recordGTZero.setDisable(1);
                recordGTZero.setUpdateTime(Timestamp.valueOf(localDateTime));
                referralEmployeeBonusRecordDao.update(recordGTZero);
            }
        }


    }


    /**
     * 奖金列表
     *
     * @param employeeId
     * @return
     */
    public BonusVOPageVO getEmployeeBonusRecords(Integer employeeId, Integer pageNumber, Integer pageSize) throws CommonException {
        BonusVOPageVO bonusVOPageVO = new BonusVOPageVO();
        List<BonusVO> bonusVOList = new ArrayList<>();

        Query.QueryBuilder query = new Query.QueryBuilder();
        int totalRow = referralEmployeeBonusRecordDao.countByEmployeeId(employeeId);
        // 总条数
        bonusVOPageVO.setTotalRow(totalRow);
        bonusVOPageVO.setPageNumber(pageNumber);
        bonusVOPageVO.setPageSize(pageSize);

        if (totalRow > 0) {
            List<ReferralEmployeeBonusRecord> referralEmployeeBonusRecordList = referralEmployeeBonusRecordDao.fetchByEmployeeId(employeeId,pageNumber,pageSize);

            // 申请记录信息
            Map<Integer, JobApplicationDO> appMap = new HashMap<>();
            // 申请的职位信息
            Map<Integer, JobPositionDO> positionMap = new HashMap<>();
            // 职位发布的HR信息
            Map<Integer, UserHrAccountDO> userHrAccountDOMap = new HashMap<>();
            // 员工信息
            Map<Integer, UserEmployeeDO> userEmployeeDOMap = new HashMap<>();
            // 被推荐人信息
            Map<Integer, UserUserDO> userUserDOSMap = new HashMap<>();
            // 申请信息ID
            List<Integer> applicationIds = referralEmployeeBonusRecordList.stream().filter(m -> m.getApplicationId() != 0).map(m -> new Double(m.getApplicationId()).intValue()).collect(Collectors.toList());

            query.clear();
            query.where(new Condition("id", applicationIds, ValueOp.IN));
            List<JobApplicationDO> applications = applicationDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(applications)) {
                appMap.putAll(applications.stream().collect(Collectors.toMap(JobApplicationDO::getId, Function.identity())));
            }
            // 职位信息Id
            List<Integer> positionIds = applications.stream().filter(m -> m.getPositionId() != 0).map(m -> m.getPositionId()).collect(Collectors.toList());
            // 查询职位信息
            query.clear();
            query.where(new Condition("id", positionIds, ValueOp.IN));
            List<JobPositionDO> positions = positionDao.getPositions(query.buildQuery());

            // 获取被推荐人信息
            Set<Integer> berecomIds = applications.stream().filter(m -> m.getApplierId() != 0).map(m -> new Double(m.getApplierId()).intValue()).collect(Collectors.toSet());

            if (!StringUtils.isEmptyList(positionIds)) {
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
            query.where(new Condition(USER_EMPLOYEE.SYSUSER_ID.getName(), berecomIds, ValueOp.IN))
                    .and(USER_EMPLOYEE.ACTIVATION.getName(), 0)
                    .and(new Condition(USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN));
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

            for (ReferralEmployeeBonusRecord bonusRecord : referralEmployeeBonusRecordList) {

                JobApplicationDO jobApplicationDO = appMap.get(bonusRecord.getApplicationId());
                Integer bonusStageDetailId = bonusRecord.getBonusStageDetailId();
                ReferralPositionBonusStageDetail referralPositionBonusStageDetail = referralPositionBonusStageDetailDao.findById(bonusStageDetailId);
                // 拼装数据
                BonusVO bonusVO = new BonusVO();
                // 加奖金时间
                bonusVO.setUpdateTime(new DateTime(bonusRecord.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                // 职位ID
                bonusVO.setPositionId(jobApplicationDO.getPositionId());
                //被推荐人ID
                bonusVO.setBerecomId(jobApplicationDO.getApplierId());
                bonusVO.setType(referralPositionBonusStageDetail.getStageType());
                bonusVO.setBonus(new BigDecimal(bonusRecord.getBonus()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toPlainString().replace(".00",""));
                bonusVO.setDisable(bonusRecord.getDisable());
                JobPositionDO jobPositionDO = positionMap.get(bonusVO.getPositionId());
                if (jobPositionDO != null) {
                    // 职位名称
                    bonusVO.setPositionName(jobPositionDO.getTitle());
                    // 发布职位的hrID
                    bonusVO.setPublisherId(jobPositionDO.getPublisher());
                    // 发布职位的HR姓名
                    UserHrAccountDO userHrAccountDO = userHrAccountDOMap.get(bonusVO.getPublisherId());
                    if (userHrAccountDO != null) {
                        bonusVO.setPublisherName(userHrAccountDOMap.get(bonusVO.getPublisherId()).getUsername());
                    } else {
                        bonusVO.setPublisherName("");
                    }
                } else {
                    bonusVO.setPositionName("");
                    // 发布职位的hrID
                    bonusVO.setPublisherId(0);
                }
                UserEmployeeDO userEmployeeDO = userEmployeeDOMap.get(bonusVO.getBerecomId());
                if (userEmployeeDO != null) {
                    bonusVO.setEmployeeId(userEmployeeDO.getId());
                    bonusVO.setEmployeeName(userEmployeeDO.getCname());
                }

                if (userUserDOSMap.containsKey(bonusVO.getBerecomId())) {
                    UserUserDO userUserDO = userUserDOSMap.get(bonusVO.getBerecomId());
                    if (userUserDO.getName() != null && !userUserDO.getName().equals("")) {
                        bonusVO.setBerecomName(userUserDO.getName());
                    } else if (userUserDO.getName() == null || userUserDO.getName().equals("")) {
                        if (userUserDO.getNickname() != null && !userUserDO.getNickname().equals("")) {
                            bonusVO.setBerecomName(userUserDO.getNickname());
                        }
                    }
                }
                if (bonusVO.getBerecomName() == null) {
                    query.clear();
                    query.where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(), bonusVO.getBerecomId());
                    UserWxUserDO userWxUserDO = userWxUserDao.getData(query.buildQuery());
                    if (userWxUserDO != null && org.apache.commons.lang.StringUtils.isNotBlank(userWxUserDO.getNickname())) {
                        bonusVO.setBerecomName(userWxUserDO.getNickname());
                    }
                }
                if(new BigDecimal(bonusVO.getBonus()).doubleValue()>0) {
                    bonusVO.setCancel(0);
                } else {
                    bonusVO.setCancel(1);
                }
                bonusVOList.add(bonusVO);
            }
            bonusVOPageVO.setData(bonusVOList);
        }
        return bonusVOPageVO;
    }

    /**
     * 添加奖金成功后,推送MQ消息,微信端发送入职奖金消息通知
     * @param appId
     * @param stage
     * @param nextStage
     * @param applierId
     * @param positionId
     * @param move
     * @param operationTime
     */
    @Transactional
    public void publishAddBonusChangeEvent(int appId, int stage, int nextStage, int applierId, int positionId, byte move, DateTime operationTime) {

        logger.info("publishAddBonusChangeEvent appId:{}, stage:{}, nextStage:{}, " +
                        "applierId:{}, positionId:{}, move:{}, operationTime:{}",
                appId, stage, nextStage, applierId, positionId, move,
                operationTime.toString("yyyy-MM-dd HH:mm:ss"));
        if (nextStage == Constant.RECRUIT_STATUS_HIRED || stage == Constant.RECRUIT_STATUS_HIRED) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("applicationId", appId);
            jsonObject.put("nowStage", stage);
            jsonObject.put("nextStage", nextStage);
            jsonObject.put("applierId", applierId);
            jsonObject.put("positionId", positionId);
            jsonObject.put("move", move);
            jsonObject.put("operationTime", operationTime.getMillis());

            logger.info("publishAddBonusChangeEventstage change: {} -> {}",
                    stage, nextStage);

            amqpTemplate.sendAndReceive(ADD_BONUS_CHANGE_EXCHNAGE,
                    ADD_BONUS_CHANGE_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                            .build());
        }
    }

    /**
     * 增加重复添加的校验
     * @param userEmployeeList 需要添加的自定义员工信息
     * @return 添加的数量
     */
    public int addEmployeeListIfNotExist(List<UserEmployeeDO> userEmployeeList) {
        if (userEmployeeList != null && userEmployeeList.size() > 0) {
            int count = 0;
            List<UserEmployeeRecord> employeeDOS = new ArrayList<>();
            for(UserEmployeeDO employee : userEmployeeList){
                UserEmployeeRecord record = BeanUtils.structToDB(employee, UserEmployeeRecord.class);
                logger.info("EmployeeEntity addEmployeeListIfNotExist employee:{}", employee);
                logger.info("EmployeeEntity addEmployeeListIfNotExist record:{}", record);
                record.setAuthMethod(Constant.AUTH_METHON_TYPE_CUSTOMIZE);
                UserEmployeeRecord userEmployeeRecord = employeeDao.insertCustomEmployeeIfNotExist(record);

                if (userEmployeeRecord != null) {
                    employeeDOS.add(userEmployeeRecord);
                    count += 1;
                }
            }
            // ES 索引更新
            searchengineEntity.updateEmployeeAwards(employeeDOS.stream().map(m -> m.getId()).collect(Collectors.toList()), false);
            return count;
        } else {
            return 0;
        }
    }
}

