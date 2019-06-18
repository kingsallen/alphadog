package com.moseeker.useraccounts.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCertConfDao;
import com.moseeker.baseorm.dao.referraldb.ReferralEmployeeRegisterLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeRegisterLog;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.pojo.ExecuteResult;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.*;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.EmployeeBindByEmail;
import com.moseeker.useraccounts.kafka.KafkaSender;
import java.util.*;

import com.moseeker.useraccounts.service.impl.employee.BatchValidate;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.moseeker.common.constants.Constant.EMPLOYEE_FIRST_REGISTER_EXCHNAGE_ROUTINGKEY;
import static com.moseeker.common.constants.Constant.EMPLOYEE_REGISTER_EXCHNAGE;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Component
public abstract class EmployeeBinder {

    private static final Logger log = LoggerFactory.getLogger(EmployeeBinder.class);

    protected MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    @Resource(name = "cacheClient")
    protected RedisClient client;

    @Autowired
    protected UserUserDao userDao;

    @Autowired
    protected UserEmployeeDao employeeDao;

    @Autowired
    protected UserWxEntity wxEntity;

    @Autowired
    protected EmployeeEntity employeeEntity;

    @Autowired
    protected UserAccountEntity userAccountEntity;

    @Autowired
    protected HrEmployeeCertConfDao hrEmployeeCertConfDao;

    @Autowired
    protected SearchengineEntity searchengineEntity;

    @Autowired
    protected CandidateCompanyDao candidateCompanyDao;

    @Autowired
    protected LogEmployeeOperationLogEntity logEmployeeOperationLogEntity;

    @Autowired
    EmployeeBindByEmail employeeBindByEmail;

    @Autowired
    protected ReferralEmployeeRegisterLogDao referralEmployeeRegisterLogDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    private Neo4jService neo4jService;

    @Autowired
    BatchValidate batchValidate;

    protected ThreadLocal<UserEmployeeDO> userEmployeeDOThreadLocal = new ThreadLocal<>();

    ScheduledThread scheduledThread = ScheduledThread.Instance;

    /**
     * 员工认证
     * @param bindingParams 认证参数
     * @return 认证结果
     */
    public Result bind(BindingParams bindingParams,Integer bingSource) {
        log.info("bind param: BindingParams={}", bindingParams);
        Result response = new Result();
        try {
            validate(bindingParams);
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("disable", String.valueOf(0));
            HrEmployeeCertConfDO certConf = hrEmployeeCertConfDao.getData(query.buildQuery());
            if(certConf == null || certConf.getCompanyId() == 0) {
                throw UserAccountException.EMPLOYEE_VERIFICATION_NOT_SUPPORT;
            }
            paramCheck(bindingParams, certConf);
            validateCustomFieldValues(bindingParams);
            UserEmployeeDO userEmployee = createEmployee(bindingParams);
            response = doneBind(userEmployee,bingSource);
        } catch (CommonException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setEmployeeId(e.getCode());
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        log.info("bind response: {}", response);
        return response;
    }

    /**
     * 校验自定义信息
     * @param bindingParams 认证参数
     */
    protected void validateCustomFieldValues(BindingParams bindingParams) {
        boolean customValidateResult = batchValidate.validateCustomFieldValues(bindingParams.getCustomFieldValues(),
                bindingParams.getCompanyId());

        if (!customValidateResult) {
            throw UserAccountException.EMPLOYEE_CUSTOM_FIELD_ERROR;
        }
    }

    /**
     * 认证前校验
     * @param bindingParams 认证参数
     */
    protected void validate(BindingParams bindingParams) {
        UserEmployeeDO userEmployeeDO = employeeEntity.getCompanyEmployee(bindingParams.getUserId(), bindingParams.getCompanyId());
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0
                && userEmployeeDO.getActivation() == EmployeeActiveState.Actived.getState()) {
            throw UserAccountException.EMPLOYEE_ALREADY_VERIFIED;
        } else {
            userEmployeeDOThreadLocal.set(userEmployeeDO);
        }
    }

    /**
     * 创建员工记录
     * @param bindingParams
     * @return
     */
    protected UserEmployeeDO createEmployee(BindingParams bindingParams) {
        UserEmployeeDO userEmployee = userEmployeeDOThreadLocal.get() == null ? new UserEmployeeDO() : userEmployeeDOThreadLocal.get();
        userEmployee.setCompanyId(bindingParams.getCompanyId());
        userEmployee.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
        userEmployee.setSysuserId(bindingParams.getUserId());
        userEmployee.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), userEmployee.getCname()));
        userEmployee.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), userEmployee.getMobile()));
        userEmployee.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), userEmployee.getEmail()));
        userEmployee.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
        userEmployee.setAuthMethod((byte)bindingParams.getType().getValue());
        userEmployee.setActivation((byte)0);
        userEmployee.setSource(bindingParams.getSource());
        userEmployee.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (bindingParams.getCustomFieldValues() != null && bindingParams.getCustomFieldValues().size() > 0) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(bindingParams.getCustomFieldValues());
            userEmployee.setCustomFieldValues(jsonArray.toJSONString());
        }
        userEmployeeDOThreadLocal.set(userEmployee);
        return userEmployee;
    }


    /**
     *  认证数据校验
     * @param bindingParams
     * @param certConf
     * @throws Exception
     */
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("公司信息", bindingParams.getCompanyId(), null, null, 1, null);
        validateUtil.addIntTypeValidate("用户信息", bindingParams.getUserId(), null, null, 1, null);

        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            throw UserAccountException.validateFailed(result);
        }
    }


    /**
     * step 1: 认证当前员工   step 2: 将其他公司的该用户员工设为未认证
     * todo 需要优化  代码过长
     * todo 积分添加也需要移动到队列里
     * @param useremployee
     * @return
     * @throws TException
     */
    protected Result doneBind(UserEmployeeDO useremployee,int bindSource) throws TException, InvalidArgumentException {
        log.info("doneBind param: useremployee.email:{}", useremployee.getEmail());
        log.info("useremployee.authMethod:{}", useremployee.getAuthMethod());
        Result response = new Result();

        DateTime currentTime = new DateTime();
        int employeeId;
        log.info("doneBind now:{}", currentTime.toString("YYYY-MM-dd HH:mm:ss"));
        log.info("doneBind param: useremployee.email:{}", useremployee.getEmail());

        UserEmployeeRecord unActiveEmployee = fetchUnActiveEmployee(useremployee);

        if (unActiveEmployee != null) {
            log.info("userEmployee.bindingTime:{}", unActiveEmployee.getBindingTime());
            log.info("userEmployee != null  userEmployee:{}", unActiveEmployee);
            employeeId = unActiveEmployee.getId();
            log.info("userEmployee active:{}", unActiveEmployee.getActivation());
            updateInfo(unActiveEmployee, useremployee, employeeId, currentTime);
        } else {
            if (useremployee.getId() > 0) {
                employeeDao.updateData(useremployee);
                employeeId = useremployee.getId();
                employeeFirstRegister(employeeId, useremployee.getCompanyId(), currentTime.getMillis(), useremployee.getSysuserId());
            } else {
                log.info("employeeDao.registerEmployee");
                ExecuteResult executeResult = employeeDao.registerEmployee(useremployee);
                employeeId = executeResult.getId();
                if (executeResult.getExecute() > 0) {
                    log.info("employee add award");
                    employeeFirstRegister(employeeId, useremployee.getCompanyId(), currentTime.getMillis(), useremployee.getSysuserId());
                }
            }
        }

        publishEmployeeRegister(employeeId, useremployee.getCompanyId(), currentTime.getMillis(), useremployee.getSysuserId());

        referralEmployeeRegisterLogDao.addRegisterLog(employeeId, currentTime);
        if (useremployee.getSysuserId() > 0
                && org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getCname())) {
            UserUserDO userUserDO = new UserUserDO();
            userUserDO.setId(useremployee.getSysuserId());
            userUserDO.setName(useremployee.getCname());
            userDao.updateData(userUserDO);
        }

        searchengineEntity.updateEmployeeAwards(new ArrayList<Integer>(){{add(employeeId);}});
        neo4jService.updateUserEmployeeCompany(useremployee.getSysuserId(),useremployee.getCompanyId());
        kafkaSender.sendEmployeeCertification(useremployee);
        //将属于本公司的潜在候选人设置为无效
        cancelCandidate(useremployee.getSysuserId(),useremployee.getCompanyId());
        // 将其他公司的员工认证记录设为未认证
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("sysuser_id", String.valueOf(useremployee.getSysuserId())).and("disable", "0");
        List<UserEmployeeDO> employees = employeeDao.getDatas(query.buildQuery());
        log.info("select employees by: {}, result = {}", query, Arrays.toString(employees.toArray()));
        List<ReferralEmployeeRegisterLog> referralEmployeeRegisterLogs = new ArrayList<>();
        if (!StringUtils.isEmptyList(employees)) {
            Timestamp current = new Timestamp(currentTime.getMillis());
            employees.forEach(e -> {
                if (e.getId() != employeeId && e.getActivation() == 0) {
                    e.setEmailIsvalid((byte)0);
                    e.setActivation((byte)1);
                    e.setCustomFieldValues("[]");

                    ReferralEmployeeRegisterLog log = new ReferralEmployeeRegisterLog();
                    log.setEmployeeId(employeeId);
                    log.setOperateTime(current);
                    log.setRegister((byte)0);
                    referralEmployeeRegisterLogs.add(log);
                }
            });
            log.info("employees========"+JSON.toJSONString(employees));
            if(!StringUtils.isEmptyList(employees)){
                for(UserEmployeeDO DO:employees){
                    int employeeIdOther=DO.getId();
                    if(employeeIdOther==employeeId){
                        continue;
                    }
                    int userId=DO.getSysuserId();
                    int companyId=DO.getCompanyId();
                    convertCandidatePerson(userId,companyId);
                }
            }

        }
        referralEmployeeRegisterLogDao.insert(referralEmployeeRegisterLogs);
        log.info("update employess = {}", Arrays.toString(employees.toArray()));
        int[] updateResult = employeeDao.updateDatas(employees);
        if (Arrays.stream(updateResult).allMatch(m -> m == 1)){
            response.setSuccess(true);
            response.setMessage("success");
            if (StringUtils.isNotNullOrEmpty(useremployee.getActivationCode())) {
                client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, useremployee.getActivationCode());
                client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO, employeeEntity.getAuthInfoKey(useremployee.getSysuserId(), useremployee.getCompanyId()));
            }
            // 更新ES中useremployee信息
            searchengineEntity.updateEmployeeDOAwards(employees);
        } else {
            response.setSuccess(false);
            response.setMessage("fail");
        }
        if(response.success){
            if(bindSource == EmployeeOperationEntrance.IMEMPLOYEE.getKey()){
                logEmployeeOperationLogEntity.insertEmployeeOperationLog(useremployee.getSysuserId(),bindSource, EmployeeOperationType.EMPLOYEEVALID.getKey(),EmployeeOperationIsSuccess.SUCCESS.getKey(),useremployee.getCompanyId(),null);
                log.error("insertLogSuccess","我是员工");
            }
        }
        log.info("updateEmployee response : {}", response);
        useremployee.setId(employeeId);
        response.setEmployeeId(employeeId);
        this.updateEsUsersAndProfile(useremployee.getSysuserId());
        return response;
    }

    protected void updateInfo(UserEmployeeRecord unActiveEmployee, UserEmployeeDO useremployee, int employeeId, DateTime currentTime) {
        if (unActiveEmployee.getActivation() != EmployeeActiveState.Actived.getState()) {
            log.info("userEmployee not active");
            if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getEmail())) {
                unActiveEmployee.setEmail(useremployee.getEmail());
                unActiveEmployee.setEmailIsvalid(useremployee.getEmailIsvalid());
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getMobile())) {
                unActiveEmployee.setMobile(useremployee.getMobile());
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getCname())) {
                unActiveEmployee.setCname(useremployee.getCname());
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getCustomField())) {
                unActiveEmployee.setCustomField(useremployee.getCustomField());
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getCustomFieldValues()) &&
                    !Constant.EMPLOYEE_DEFAULT_CUSTOM_FIELD_VALUE.equals(useremployee.getCustomFieldValues())) {
                unActiveEmployee.setCustomFieldValues(useremployee.getCustomFieldValues());
            }
            unActiveEmployee.setActivation(EmployeeActiveState.Actived.getState());
            log.info("doneBind unActiveEmployee update record");
            log.info("unActiveEmployee.authMethod:{}, bindingTime:{}", useremployee.getAuthMethod(), unActiveEmployee.getBindingTime());
            if (useremployee.getAuthMethod() == 1 && unActiveEmployee.getBindingTime() == null) {
                employeeFirstRegister(employeeId, useremployee.getCompanyId(), currentTime.getMillis(), useremployee.getSysuserId());
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(useremployee.getBindingTime())) {
                unActiveEmployee.setBindingTime(new Timestamp(LocalDateTime.parse(useremployee.getBindingTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        .atZone(ZoneId.systemDefault()).toInstant().getEpochSecond()* 1000));
            } else {
                useremployee.setBindingTime(currentTime.toString("yyyy-MM-dd HH:mm:ss"));
                unActiveEmployee.setBindingTime(new Timestamp(currentTime.getMillis()));
            }
            unActiveEmployee.setAuthMethod(useremployee.getAuthMethod());
            if(useremployee.getSource()>0){
                unActiveEmployee.setSource((byte)useremployee.getSource());
            }
            log.info("userEmployee:{}", unActiveEmployee);
            unActiveEmployee.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            employeeDao.updateRecord(unActiveEmployee);

            if (useremployee.getId() > 0 && useremployee.getId() != unActiveEmployee.getId()) {
                employeeDao.deleteData(useremployee);
                searchengineEntity.deleteEmployeeDO(new ArrayList<Integer>(){{add(useremployee.getId());}});
            }
        }
    }

    protected UserEmployeeRecord fetchUnActiveEmployee(UserEmployeeDO useremployee) {
        return employeeDao.getUnActiveEmployee(useremployee.getSysuserId(),
                useremployee.getCompanyId());
    }

    private void updateEsUsersAndProfile(int userId){
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", userId);
        result.put("tableName","user_meassage");
        scheduledThread.startTast(()->{
            client.lpush(Constant.APPID_ALPHADOG, "ES_REALTIME_UPDATE_INDEX_USER_IDS", JSON.toJSONString(result));
            client.lpush(Constant.APPID_ALPHADOG,"ES_CRON_UPDATE_INDEX_PROFILE_COMPANY_USER_IDS",String.valueOf(userId));
        },2000);
    }

    /**
     * 发布员工认证消息
     * @param employeeId 员工编号
     * @param companyId 公司编号
     * @param bindingTime 注册时间
     * @param userId 用户编号
     */
    private void publishEmployeeRegister(int employeeId, int companyId, long bindingTime, int userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "employee verification");
        jsonObject.put("ID", UUID.randomUUID().toString());
        jsonObject.put("employee_id", employeeId);
        jsonObject.put("company_id", companyId);
        jsonObject.put("verify_time", new DateTime(bindingTime).toString("yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("user_id", userId);
        log.info("EmployeeBinder employeeFirstRegister param:{}, exchange:{}, routing:{}",
                jsonObject.toJSONString(), EMPLOYEE_REGISTER_EXCHNAGE, EMPLOYEE_FIRST_REGISTER_EXCHNAGE_ROUTINGKEY);
        amqpTemplate.send(EMPLOYEE_REGISTER_EXCHNAGE,
                EMPLOYEE_FIRST_REGISTER_EXCHNAGE_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                        .build());
    }

    /**
     * 初次注册成为员工，添加积分与红包
     * @param employeeId 员工编号
     * @param companyId 公司编号
     * @param bindingTime 员工注册时间
     */
    protected void employeeFirstRegister(int employeeId, int companyId, long bindingTime, int userId) {
        employeeEntity.addRewardByEmployeeVerified(employeeId, companyId);
    }
    /*
        员工认证成功时，需要将潜在候选人置为无效
     */
    public void cancelCandidate(int userId,int companyId) {
        Query query = new Query.QueryBuilder().where("sys_user_id", userId).and("company_id", companyId).and("status", 1).buildQuery();
        List<CandidateCompanyDO> list = candidateCompanyDao.getDatas(query);
        if (!StringUtils.isEmptyList(list)) {
            log.info(JSON.toJSONString(list));
            for (CandidateCompanyDO DO : list) {
                DO.setStatus(0);

            }
            candidateCompanyDao.updateDatas(list);

        }
    }
    /*
        员工取消后，需要将潜在候选人置为有效
     */
    public void convertCandidatePerson(int userId,int companyId){
        Query query=new Query.QueryBuilder().where("sys_user_id",userId).and("company_id",companyId).and("status",0).buildQuery();
        List<CandidateCompanyDO> list=candidateCompanyDao.getDatas(query);
        if(!StringUtils.isEmptyList(list)){
            for(CandidateCompanyDO DO:list){
                DO.setStatus(1);

            }
            candidateCompanyDao.updateDatas(list);
        }
    }

}
