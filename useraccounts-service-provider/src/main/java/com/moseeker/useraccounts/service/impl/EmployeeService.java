package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.useraccounts.service.EmployeeBinder;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ltf
 * 员工服务业务实现
 * 2017年3月3日
 */
@Service
@CounterIface
public class EmployeeService {

    private Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Resource(name = "cacheClient")
    private RedisClient client;

    MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);


    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private UserWxEntity wxEntity;

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    @Autowired
    private HrEmployeeCertConfDao hrEmployeeCertConfDao;

    @Autowired
    private HrEmployeeCustomFieldsDao hrEmployeeCustomFieldsDao;

    @Autowired
    private ConfigSysPointsConfTplDao configSysPointsConfTplDao;

    @Autowired
    private UserEmployeePointsRecordDao employeePointsRecordDao;

    @Autowired
    private Map<String, EmployeeBinder> employeeBinder;

    public EmployeeResponse getEmployee(int userId, int companyId) throws TException {
        log.info("getEmployee param: userId={} , companyId={}", userId, companyId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        UserEmployeeDO employee;
        EmployeeResponse response = new EmployeeResponse();
        try {
            query.where("sysuser_id", String.valueOf(userId)).and("company_id", String.valueOf(companyId))
                    .and("disable", String.valueOf(0));
            employee = employeeDao.getEmployee(query.buildQuery());
            if (employee != null && employee.getId() != 0) {
                Employee emp = new Employee();
                emp.setId(employee.getId());
                emp.setEmployeeId(employee.getEmployeeid());
                emp.setCompanyId(employee.getCompanyId());
                emp.setSysuerId(employee.getSysuserId());
                emp.setMobile(employee.getMobile());
                emp.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(employee.getCname(), ""));
                emp.setAward(employee.getAward());
                emp.setIsRpSent(employee.getIsRpSent() == 0 ? false : true);
                emp.setCustomFieldValues(employee.getCustomFieldValues());
                emp.setWxuserId(wxEntity.getWxuserId(userId, companyId));
                emp.setEmail(employee.getEmail());
                emp.setCustomField(employee.getCustomField());
                emp.setAuthMethod(employee.getAuthMethod());
                response.setEmployee(emp);

                if (employee.getActivation() == 0) {
                    response.setBindStatus(BindStatus.BINDED);
                } else if (StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, employee.getActivationCode()))) {
                    response.setBindStatus(BindStatus.PENDING);
                } else {
                    response.setBindStatus(BindStatus.UNBIND);
                }
            } else {
                response.setBindStatus(BindStatus.UNBIND);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setBindStatus(BindStatus.UNBIND);
        }
        log.info("getEmployee response: {}", response);
        return response;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public EmployeeVerificationConfResponse getEmployeeVerificationConf(int companyId)
            throws TException {
        log.info("getEmployeeVerificationConf param: companyId={}", companyId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        EmployeeVerificationConfResponse response = new EmployeeVerificationConfResponse();
        try {
            query.where("company_id", String.valueOf(companyId));
            HrEmployeeCertConfDO employeeCertConf = hrEmployeeCertConfDao.getData(query.buildQuery());
            log.info("HrEmployeeCertConfDO: {}", employeeCertConf);
            if (employeeCertConf != null && employeeCertConf.getEmailSuffix() != null && employeeCertConf.getQuestions() != null) {
                EmployeeVerificationConf evc = new EmployeeVerificationConf();
                evc.setCompanyId(employeeCertConf.getCompanyId());
                evc.setEmailSuffix(JSONObject.parseArray(employeeCertConf.getEmailSuffix()).stream().map(m -> String.valueOf(m)).collect(Collectors.toList()));
                evc.setAuthMode((short)employeeCertConf.getAuthMode());
                evc.setAuthCode(employeeCertConf.getAuthCode());
                evc.setCustom(employeeCertConf.getCustom());
                // 为解决gradle build时无法完成类推导的问题，顾list不指定类型
                List questions = JSONObject.parseArray(employeeCertConf.getQuestions()).stream().map(m -> JSONObject.parseObject(String.valueOf(m), Map.class)).collect(Collectors.toList());
                evc.setQuestions(questions);
                evc.setCustomHint(employeeCertConf.getCustomHint());
                HrCompanyConfDO hrCompanyConfig = hrCompanyConfDao.getData(query.buildQuery());
                evc.setBindSuccessMessage(hrCompanyConfig == null ? "":hrCompanyConfig.getEmployeeBinding());
                response.setEmployeeVerificationConf(evc);
                log.info("EmployeeVerificationConfResponse: {}", response.getEmployeeVerificationConf());
                response.setExists(true);
            } else {
                response.setExists(false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public Result bind(BindingParams bindingParams) throws TException {
        Result response = new Result();
//        Query.QueryBuilder query = new Query.QueryBuilder();
//        query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("disable", String.valueOf(0));
//        HrEmployeeCertConfDO certConf = hrEmployeeCertConfDao.getData(query.buildQuery());
//        if(certConf == null || certConf.getCompanyId() == 0) {
//            response.setSuccess(false);
//            response.setMessage("暂时不接受员工认证");
//            return response;
//        }
//        switch(bindingParams.getType()) {
//            case EMAIL:
//
//                // 邮箱校验后缀
//                if (JSONObject.parseArray(certConf.getEmailSuffix()).stream().noneMatch(m -> bindingParams.getEmail()
//                        .endsWith(String.valueOf(m)))) {
//                    response.setSuccess(false);
//                    response.setMessage("员工认证信息不正确");
//                    break;
//                }
//
//                // 判断该邮箱是否被占用
//                query.clear();
//                query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("email", bindingParams.getEmail())
//                        .and("disable", "0");
//
//                // 判断该邮箱现在已被占用 或 正在被人认证
//                List<UserEmployeeDO> userEmployees = employeeDao.getDatas(query.buildQuery());
//                log.info("使用了邮箱:{}, 的用户有:{}", bindingParams.getEmail(), Arrays.toString(userEmployees.toArray()));
//                userEmployees = userEmployees.stream().filter(e -> e.getSysuserId() != bindingParams.getUserId() && e.getId() > 0).collect(Collectors.toList());
//                if (userEmployees.stream().anyMatch(e -> e.getActivation() == 0 || StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, e.getActivationCode())))) {
//                    log.info("邮箱:{} 已被占用", bindingParams.getEmail());
//                    response.setSuccess(false);
//                    response.setMessage("该邮箱已被认证\n请使用其他邮箱");
//                    break;
//                }
//
//                // 验证员工是否已认证
//                query.clear();
//                query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("sysuser_id", String.valueOf(bindingParams.getUserId()))
//                        .and("disable", "0");
//                UserEmployeeDO employee = employeeDao.getData(query.buildQuery());
//
//                if (employee != null && employee.getId() > 0 && employee.getActivation() == 0) {
//                    response.setSuccess(false);
//                    response.setMessage("该员工已绑定");
//                    break;
//                }
//
//
//                // 员工信息不存在，创建员工信息
//                if (employee == null || employee.getId() == 0) {
//                    employee = new UserEmployeeDO();
//                    employee.setCompanyId(bindingParams.getCompanyId());
//                    employee.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
//                    employee.setSysuserId(bindingParams.getUserId());
//                    employee.setCname(bindingParams.getName());
//                    employee.setMobile(bindingParams.getMobile());
//                    employee.setEmail(bindingParams.getEmail());
//
//                    query.clear();
//                    query.where("sysuser_id", String.valueOf(bindingParams.getUserId()));
//
//                    employee.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
//                    employee.setAuthMethod((byte)bindingParams.getType().getValue());
//                    employee.setActivation((byte)3);
//                    employee.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                    int primaryKey = employeeDao.addData(employee).getId();
//                    if( primaryKey == 0) {
//                        response.setSuccess(false);
//                        response.setMessage("认证失败，请检查员工信息");
//                        log.info("员工邮箱认证，保存员工信息失败 employee={}", employee);
//                        break;
//                    }
//                    employee.setId(primaryKey);
//                }
//
//                // 防止用户频繁认证，24h内不重复发认证邮件
//                if (StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, employee.getActivationCode()))) {
//                    response.setSuccess(false);
//                    response.setMessage("已发送过邮件到指定邮箱，24h内请勿重复该操作");
//                    break;
//                }
//                // step 1: 发送认证邮件 step 2：将信息存入redis
//                query.clear();
//                query.where("id", String.valueOf(bindingParams.getCompanyId()));
//                HrCompanyDO companyDO = companyDao.getData(query.buildQuery());
//                query.clear();
//                query.where("company_id", String.valueOf(bindingParams.getCompanyId()));
//                HrWxWechatDO hrwechatResult = hrWxWechatDao.getData(query.buildQuery());
//                if (companyDO != null && companyDO.getId() != 0 && hrwechatResult != null && hrwechatResult.getId() != 0) {
//                    // 激活码(MD5)： employee-email-timestamp
//                    String activationCode = MD5Util.encryptSHA(employee.getId()+"-"+bindingParams.getEmail()+"-"+System.currentTimeMillis());
//                    Map<String, String> mesBody = new HashMap<>();
//                    mesBody.put("#company_log#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getLogo(), ""));
//                    mesBody.put("#employee_name#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(employee.getCname(), userAccountEntity.genUsername(employee.getSysuserId())));
//                    mesBody.put("#company_abbr#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getAbbreviation(), ""));
//                    mesBody.put("#official_account_name#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(hrwechatResult.getName(), ""));
//                    mesBody.put("#official_account_qrcode#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(hrwechatResult.getQrcode(), ""));
//                    mesBody.put("#date_today#",  LocalDate.now().toString());
//                    mesBody.put("#auth_url#", ConfigPropertiesUtil.getInstance().get("platform.url", String.class).concat("m/employee/bindemail?activation_code=").concat(activationCode).concat("&wechat_signature=").concat(hrwechatResult.getSignature()));
//                    // 发件人信息
//                    ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance();
//                    String senderName = propertiesUtil.get("email.verify.sendName", String.class);
//                    String subject = "请验证邮箱完成员工认证-".concat(org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getAbbreviation(), ""));
//                    String senderDisplay = org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getAbbreviation(), "");
//                    // 发送认证邮件
//                    Response mailResponse = mqService.sendAuthEMail(mesBody, Constant.EVENT_TYPE_EMPLOYEE_AUTH, bindingParams.getEmail(), subject, senderName, senderDisplay);
//                    // 邮件发送成功
//                    if (mailResponse.getStatus() == 0) {
//                        String redStr = client.set(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, activationCode, JSONObject.toJSONString(bindingParams));
//                        log.info("set redis result: ", redStr);
//                        // 修改用户邮箱
//                        employee.setEmail(bindingParams.getEmail());
//                        employee.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                        employee.setActivationCode(activationCode);
//                        employeeDao.updateData(employee);
//                        response.setSuccess(true);
//                        response.setMessage("发送激活邮件成功");
//                    } else {
//                        response.setMessage("发送激活邮件失败");
//                    }
//                }
//                break;
//            case CUSTOMFIELD:
//
//                /**
//                 *
//                 * 验证员工是否已存在
//                 * 如果存在就在老数据上做认证，如果不存在则新员工信息上做认证
//                 */
//                query.clear();
//                query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("sysuser_id", String.valueOf(bindingParams.getUserId()))
//                        .and("disable", "0");
//                UserEmployeeDO oldEmployee = employeeDao.getData(query.buildQuery());
//
//                // 员工信息验证
//                query.clear();
//                query.where("company_id", String.valueOf(bindingParams.getCompanyId()))
//                        .and("cname", bindingParams.getName())
//                        .and("custom_field", bindingParams.getCustomField())
//                        .and("disable", "0");
//
//                employee = employeeDao.getData(query.buildQuery());
//                if (employee == null || employee.getId() == 0) {
//                    response.setSuccess(false);
//                    response.setMessage("员工认证信息不正确");
//                } else if (employee.getActivation() == 0) {
//                    response.setSuccess(false);
//                    response.setMessage("该员工已绑定");
//                } else if (employee.getSysuserId() == 0) { // sysuserId =  0 说明员工信息是批量上传的未设置user_id
//                    if (oldEmployee != null && oldEmployee.getId() != 0) {
//                        response = updateEmployee(bindingParams, oldEmployee.getId());
//                    } else {
//                        employee.setSysuserId(bindingParams.getUserId());
//                        int rownum = employeeDao.updateData(employee);
//                        if (rownum > 0){
//                            response = updateEmployee(bindingParams, employee.getId());
//                        } else {
//                            response.setSuccess(false);
//                            response.setMessage("fail");
//                        }
//                    }
//                } else if (employee.getSysuserId() == bindingParams.getUserId()) {
//                    if (oldEmployee != null && oldEmployee.getId() != 0) {
//                        employee =  oldEmployee;
//                    }
//                    response = updateEmployee(bindingParams, employee.getId());
//                } else {  // 说明 employee.user_id != bindingParams.user_id 用户提供的信息与员工信息不匹配
//                    response.setSuccess(false);
//                    response.setMessage("员工认证信息不匹配");
//                }
//                break;
//            case QUESTIONS:
//
//                // 验证员工是否已认证
//                query.clear();
//                query.where("company_id", String.valueOf(bindingParams.getCompanyId()))
//                        .and("sysuser_id", String.valueOf(bindingParams.getUserId()))
//                        .and("disable", "0");
//                employee = employeeDao.getData(query.buildQuery());
//
//                if (employee == null || employee.getId() == 0) { // 找不到员工信息，创建一条员工信息
//                    employee = new UserEmployeeDO();
//                    employee.setCompanyId(bindingParams.getCompanyId());
//                    employee.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
//                    employee.setSysuserId(bindingParams.getUserId());
//                    employee.setCname(bindingParams.getName());
//                    employee.setMobile(bindingParams.getMobile());
//                    employee.setEmail(bindingParams.getEmail());
//                    query.clear();
//                    query.where("sysuser_id", String.valueOf(bindingParams.getUserId()));
//
//                    employee.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
//                    employee.setAuthMethod((byte)bindingParams.getType().getValue());
//                    employee.setActivation((byte)3);
//                    employee.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                    int primaryKey = employeeDao.addData(employee).getId();
//                    if(primaryKey== 0) {
//                        response.setSuccess(false);
//                        response.setMessage("认证失败，请检查员工信息");
//                        log.info("员工认证(question模式)，保存员工信息失败 employee={}", employee);
//                        break;
//                    }
//                    employee.setId(primaryKey);
//                } else if (employee.getActivation() == 0) {
//                    response.setSuccess(false);
//                    response.setMessage("该员工已绑定");
//                    break;
//                }
//
//                // 问题校验
//                List<String> answers = JSONObject.parseArray(certConf.getQuestions()).stream().map(m -> JSONObject.parseObject(String.valueOf(m)).getString("a")).collect(Collectors.toList());
//                log.info("answers: {}", answers);
//                String[] replys = {bindingParams.getAnswer1().trim(), bindingParams.getAnswer2().trim()};
//                boolean bool = true;
//                if (!StringUtils.isEmptyList(answers)) {
//                    for (int i = 0; i < answers.size(); i++) {
//                        if (!org.apache.commons.lang.StringUtils.defaultString(answers.get(i), "").equals(replys[i])) {
//                            bool = false;
//                            break;
//                        }
//                    }
//                } else {
//                    bool = false;
//                }
//                if (!bool) {
//                    response.setSuccess(false);
//                    response.setMessage("员工认证信息不正确");
//                    break;
//                }
//                response = updateEmployee(bindingParams, employee.getId());
//				/*
//				 * TODO 员工认证发送消息模板
//	             *  a: 认证成功以后发送消息模板,点击消息模板填写自定义字段 (company_id 为奇数)
//	             *  b: 直接填写自定义字段 (company_id 为偶数)
//				 * */
//                break;
//            default:
//                break;
//        }
        String authMethod = "auth_method_".concat(bindingParams.getType().toString().toLowerCase());
        if(!employeeBinder.containsKey(authMethod)) {
            response.setSuccess(false);
            response.setMessage("暂不支持该认证方式");
        } else {
            response = employeeBinder.get(authMethod).bind(bindingParams);
        }
        return response;
    }


    /**
     * step 1: 认证当前员工   step 2: 将其他公司的该用户员工设为未认证
     * @return
     * @throws TException
     */
//    private Result updateEmployee(BindingParams bindingParams, int employeeId) throws TException {
//        log.info("updateEmployee param: BindingParams={}", bindingParams);
//        Result response = new Result();
//        Query.QueryBuilder query = new Query.QueryBuilder();
//        query.where("sysuser_id", String.valueOf(bindingParams.getUserId())).and("disable", "0");
//        List<UserEmployeeDO> employees = employeeDao.getDatas(query.buildQuery());
//        log.info("select employees by: {}, result = {}", query, Arrays.toString(employees.toArray()));
//        if (!StringUtils.isEmptyList(employees)) {
//            employees.forEach(e -> {
//                if (e.getId() == employeeId) {
//                    e.setActivation((byte)0);
//                    e.setAuthMethod((byte)bindingParams.getType().getValue());
//                    e.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
//                    e.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), e.getEmail()));
//                    e.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                    e.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//                    e.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), e.getCname()));
//                    e.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), e.getMobile()));
//                    e.setCustomField(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getCustomField(), e.getCustomField()));
//                } else {
//                    e.setActivation((byte)4);
//                }
//            });
//        }
//        log.info("update employess = {}", Arrays.toString(employees.toArray()));
//        int[] updateResult = employeeDao.updateDatas(employees);
//        if (Arrays.stream(updateResult).allMatch(m -> m == 1)){
//            response.setSuccess(true);
//            response.setMessage("success");
//        } else {
//            response.setSuccess(false);
//            response.setMessage("fail");
//        }
//        log.info("updateEmployee response : {}", response);
//        return response;
//    }

    public Result unbind(int employeeId, int companyId, int userId)
            throws TException {
        log.info("unbind param: employeeId={}, companyId={}, userId={}", employeeId, companyId, userId);
        Result response = new Result();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("sysuser_id", String.valueOf(userId)).and("company_id", String.valueOf(companyId))
                .and("id", String.valueOf(employeeId));
        UserEmployeeDO employee = employeeDao.getData(query.buildQuery());
        log.info("select employee by: {} , result: {}", query, employee);
        if (employee == null || employee.getId() == 0) {
            response.setSuccess(false);
            response.setMessage("员工信息不存在");
        }
        // 如果是email激活发送了激活邮件，但用户未激活(状态为PENDING)，此时用户进行取消绑定操作，删除员工认证的redis信息
        if (StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, employee.getActivationCode()))) {
            client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, employee.getActivationCode());
            response.setSuccess(true);
            response.setMessage("解绑成功");
        } else {
            if (employeeEntity.unbind(Arrays.asList(employeeId))){
                response.setSuccess(true);
                response.setMessage("success");
            } else {
                response.setSuccess(false);
                response.setMessage("fail");
            }
        }
        log.info("unbind response: {}", response);
        return response;
    }

    public List<EmployeeCustomFieldsConf> getEmployeeCustomFieldsConf(int companyId)
            throws TException {
        log.info("getEmployeeCustomFieldsConf param: companyId={}", companyId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", String.valueOf(companyId));
        List<HrEmployeeCustomFieldsDO> customFields = new ArrayList<>();
        List<EmployeeCustomFieldsConf> response = new ArrayList<>();
        try {
            customFields = hrEmployeeCustomFieldsDao.getDatas(query.buildQuery());
            log.info("select EmployeeCustomField by: {}, result = {}", query, customFields);
            if(!StringUtils.isEmptyList(customFields)) {
                customFields.forEach(m -> {
                    EmployeeCustomFieldsConf efc = new EmployeeCustomFieldsConf();
                    efc.setId(m.getId());
                    efc.setCompanyId(m.getCompanyId());
                    efc.setFieldValues(JSONObject.parseArray(m.getFvalues()).stream().map(value -> String.valueOf(value)).collect(Collectors.toList()));
                    efc.setMandatory(m.getMandatory() == 0 ? false : true);
                    efc.setOrder(m.getForder());
                    efc.setFieldName(m.getFname());
                    response.add(efc);
                });
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("getEmployeeCustomFieldsConf response: {}", response);
        return response;
    }


    public RewardsResponse getEmployeeRewards(int employeeId, int companyId)
            throws TException {
        log.info("getEmployeeRewards param: employeeId={}, companyId={}", employeeId, companyId);
        RewardsResponse response = new RewardsResponse();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", String.valueOf(companyId));
        try {
			/*
			 * 开始查询积分规则：
			 */
            List<RewardConfig> pcfList = new ArrayList<>();
            List<HrPointsConfDO> pointsConfs = hrPointsConfDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(pointsConfs)) {
                List<Integer> tpIds = pointsConfs.stream().map(m -> m.getTemplateId()).collect(Collectors.toList());
                query.clear();
                query.where(new Condition("id", tpIds, ValueOp.IN));
                List<ConfigSysPointsConfTplDO> configTpls = configSysPointsConfTplDao.getDatas(query.buildQuery());
                if (!StringUtils.isEmptyList(configTpls)) {
                    List<Integer> ctpIds = configTpls.stream().filter(m -> m.isInitAward == 0)
                            .sorted(Comparator.comparingInt(m -> m.getPriority()))
                            .map(m -> m.getId()).collect(Collectors.toList());
                    if(ctpIds != null) {
                        for (int tempId : ctpIds) {
                            for (HrPointsConfDO pcf : pointsConfs) {
                                if (pcf.getReward() != 0 && tempId == pcf.getTemplateId()) {
                                    RewardConfig rewardConfig = new RewardConfig();
                                    rewardConfig.setId(pcf.getId());
                                    rewardConfig.setPoints((int)pcf.getReward());
                                    rewardConfig.setStatusName(pcf.getStatusName());
                                    pcfList.add(rewardConfig);
                                }
                            }
                        }
                    }
                }
            }
            response.setRewardConfigs(pcfList);
            // 查询申请职位list
            List<com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO> points = employeePointsRecordDao.getDatas(new Query.QueryBuilder().where("employee_id", employeeId).buildQuery());
            if (!StringUtils.isEmptyList(points)) {
                List<Double> aids = points.stream().map(m -> m.getApplicationId()).collect(Collectors.toList());
                query.clear();
                query.where(new Condition("id", aids, ValueOp.IN));
                List<JobApplicationDO> applications = applicationDao.getDatas(query.buildQuery());
                final Map<Integer, Integer>  appMap = new HashMap<>();
                final Map<Integer, String> positionMap = new HashMap<>();
                // 转成map -> k: applicationId, v: positionId
                if (!StringUtils.isEmptyList(applications)) {
                    appMap.putAll(applications.stream().collect(Collectors.toMap(JobApplicationDO::getId, JobApplicationDO::getPositionId)));
                    query.clear();
                    query.where(new Condition("id", appMap.values().toArray(), ValueOp.IN));
                    List<JobPositionDO> positions = positionDao.getPositions(query.buildQuery());
                    // 转成map -> k: positionId, v: positionTitle
                    if (!StringUtils.isEmptyList(points)){
                        positionMap.putAll(positions.stream().collect(Collectors.toMap(JobPositionDO::getId, JobPositionDO::getTitle)));
                    }
                }

                // 计算积分总合
                int total = points.stream().mapToInt(m -> m.getAward()).sum();
                // 用户积分记录：
                List<Reward> rewards = new ArrayList<>();
                points.stream().filter(p -> p.getAward() != 0).forEach(point -> {
                    Reward reward = new Reward();
                    reward.setReason(point.getReason());
                    reward.setPoints(point.getAward());
                    reward.setUpdateTime(point.getUpdateTime());
                    reward.setTitle(positionMap.getOrDefault(appMap.get(point.getApplicationId()), ""));
                    rewards.add(reward);
                });
                response.setTotal(total);
                response.setRewards(rewards);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("getEmployeeRewards response: {}", response);
        return response;
    }

    public Result setEmployeeCustomInfo(int employeeId, String customValues)
            throws TException {
        log.info("setEmployeeCustomInfo param: employeeId={}, customValues={}", employeeId, customValues);
        Result response = new Result();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("employeeid", String.valueOf(employeeId));
        List<UserEmployeeDO> userEmployeesDO = employeeDao.getDatas(query.buildQuery());
        log.info("select userEmployee by: {}, result = {}", query, Arrays.toString(userEmployeesDO.toArray()));
        if(StringUtils.isEmptyList(userEmployeesDO)) {
            response.setSuccess(false);
            response.setMessage("员工信息不存在");
        } else {
            userEmployeesDO.get(0).setCustomFieldValues(customValues);
            int result = employeeDao.updateData(userEmployeesDO.get(0));
            if (result > 0) {
                response.setSuccess(true);
                response.setMessage("success");
            } else {
                response.setSuccess(false);
                response.setMessage("fail");
            }
        }
        log.info("setEmployeeCustomInfo response: {}", response);
        return response;
    }
}
