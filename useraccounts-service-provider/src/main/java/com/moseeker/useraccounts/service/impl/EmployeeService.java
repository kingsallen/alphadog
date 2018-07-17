package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyReferralConf;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.CompanyConfigEntity;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyReferralConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCustomFieldsDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.useraccounts.exception.ExceptionCategory;
import com.moseeker.useraccounts.exception.ExceptionFactory;
import com.moseeker.useraccounts.service.EmployeeBinder;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ltf 员工服务业务实现 2017年3月3日
 */
@Service
@CounterIface
public class EmployeeService {

    private Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Resource(name = "cacheClient")
    private RedisClient client;

    SearchengineServices.Iface searchService = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private UserWxEntity wxEntity;

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private HrEmployeeCertConfDao hrEmployeeCertConfDao;

    @Autowired
    private HrEmployeeCustomFieldsDao hrEmployeeCustomFieldsDao;

    @Autowired
    private Map<String, EmployeeBinder> employeeBinder;

    @Autowired
    private CompanyConfigEntity companyConfigEntity;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private HrWxWechatDao wxWechatDao;

    @Autowired
    private HrCompanyReferralConfDao referralConfDao;

    @Autowired
    private UserUserDao userDao;

    public EmployeeResponse getEmployee(int userId, int companyId) throws TException {
        log.info("getEmployee param: userId={} , companyId={}", userId, companyId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        List<UserEmployeeDO> employees = new ArrayList<>();
        EmployeeResponse response = new EmployeeResponse();
        try {
            // 查询集团公司companyID列表
            List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
            query.where("sysuser_id", String.valueOf(userId)).and(new Condition("company_id", companyIds, ValueOp.IN))
                    .and("disable", String.valueOf(0)).and("activation", "0");
            employees = employeeDao.getDatas(query.buildQuery(), UserEmployeeDO.class);
            String pendingEmployee = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO,employeeEntity.getAuthInfoKey(userId, companyId));
            Employee emp = new Employee();
            if (employees != null && !employees.isEmpty()) {
                employees.stream().filter(f -> f.getId() > 0).forEach(employee -> {
                    if (employee.getActivation() == 0) {
                        response.setBindStatus(BindStatus.BINDED);
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
                        return;
                    }
                });
            } else if(StringUtils.isNotNullOrEmpty(pendingEmployee)) {
                JSONObject jsonObject = JSONObject.parseObject(pendingEmployee);
                emp.setEmployeeId(jsonObject.getString("employeeId"));
                emp.setCompanyId(jsonObject.getIntValue("companyId"));
                emp.setSysuerId(jsonObject.getIntValue("sysuserId"));
                emp.setMobile(jsonObject.getString("mobile"));
                emp.setCname(jsonObject.getString("cname"));
                emp.setAward(jsonObject.getIntValue("award"));
                emp.setIsRpSent(jsonObject.getBooleanValue("isRpSent"));
                emp.setCustomField(jsonObject.getString("customField"));
                emp.setWxuserId(wxEntity.getWxuserId(userId, companyId));
                emp.setEmail(jsonObject.getString("email"));
                emp.setAuthMethod(jsonObject.getIntValue("authMethod"));
                emp.setCustomFieldValues(jsonObject.getString("customFieldValues"));
                response.setEmployee(emp);
                response.setBindStatus(BindStatus.PENDING);
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

    @SuppressWarnings({"unchecked", "rawtypes"})
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
                evc.setEmailSuffix(JSONObject.parseArray(employeeCertConf.getEmailSuffix())
                        .stream().map(m -> String.valueOf(m))
                        .collect(Collectors.toList()));
                evc.setAuthMode((short) employeeCertConf.getAuthMode());
                evc.setAuthCode(employeeCertConf.getAuthCode());
                evc.setCustom(employeeCertConf.getCustom());
                // 为解决gradle build时无法完成类推导的问题，顾list不指定类型
                JSONArray jsonArray = JSONObject.parseArray(employeeCertConf.getQuestions());
                if (jsonArray != null) {
                    List questions = jsonArray
                            .stream().map(m -> JSONObject.parseObject(String.valueOf(m), Map.class))
                            .collect(Collectors.toList());
                    evc.setQuestions(questions);
                }
                evc.setCustomHint(employeeCertConf.getCustomHint());
                HrCompanyConfDO hrCompanyConfig = hrCompanyConfDao.getData(query.buildQuery());
                evc.setBindSuccessMessage(hrCompanyConfig == null ? "" : hrCompanyConfig.getEmployeeBinding());
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
        String authMethod = "auth_method_".concat(bindingParams.getType().toString().toLowerCase());
        if (!employeeBinder.containsKey(authMethod)) {
            response.setSuccess(false);
            response.setMessage("暂不支持该认证方式");
        } else {
            response = employeeBinder.get(authMethod).bind(bindingParams);
        }
        return response;
    }

    public Result unbind(int employeeId, int companyId, int userId) {
        log.info("unbind param: employeeId={}, companyId={}, userId={}", employeeId, companyId, userId);
        Result response = new Result();
        response.setSuccess(true);
        response.setMessage("解绑成功");

        // 如果是email激活发送了激活邮件，但用户未激活(状态为PENDING)，此时用户进行取消绑定操作，删除员工认证的redis信息
        String employeeJson = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO, employeeEntity.getAuthInfoKey(userId, companyId));
        if (StringUtils.isNotNullOrEmpty(employeeJson)) {
            client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, JSONObject.parseObject(employeeJson).getString("activationCode"));
            client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO, employeeEntity.getAuthInfoKey(userId, companyId));
            return response;
        }

        // 解绑
        if (!employeeEntity.unbind(Arrays.asList(employeeId))) {
            response.setSuccess(false);
            response.setMessage("fail");
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
            if (!StringUtils.isEmptyList(customFields)) {
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


    public RewardsResponse getEmployeeRewards(int employeeId, int companyId, int pageNumber, int pageSize) throws CommonException {
        log.info("getEmployeeRewards param: employeeId={}, companyId={}", employeeId, companyId);
        RewardsResponse response = new RewardsResponse();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId);
        UserEmployeeDO userEmployeeDO = employeeDao.getData(query.buildQuery());
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0) {
            /*
             * 开始查询积分规则：
			 */
            response.setRewardConfigs(companyConfigEntity.getRerawConfig(companyId, true));
            // 查询申请职位list
            response.setTotal(userEmployeeDO.getAward());
            response.setRewards(employeeEntity.getEmployeePointsRecords(employeeId, pageNumber, pageSize).getData());
        } else {
            throw ExceptionFactory.buildException(ExceptionCategory.USEREMPLOYEES_EMPTY);
        }
        log.info("getEmployeeRewards response: {}", response);
        return response;
    }

    public Result setEmployeeCustomInfo(int employeeId, String customValues)
            throws TException {
        log.info("setEmployeeCustomInfo param: employeeId={}, customValues={}", employeeId, customValues);
        Result response = new Result();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", String.valueOf(employeeId));
        List<UserEmployeeDO> userEmployeesDO = employeeDao.getDatas(query.buildQuery());
        log.info("select userEmployee by: {}, result = {}", query, Arrays.toString(userEmployeesDO.toArray()));
        if (StringUtils.isEmptyList(userEmployeesDO)) {
            response.setSuccess(false);
            response.setMessage("员工信息不存在");
        } else {
            userEmployeesDO.get(0).setCustomFieldValues(customValues);
            int result = employeeEntity.updateData(userEmployeesDO.get(0));
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

    public List<EmployeeAward> awardRanking(int employeeId, int companyId, String timespan) {
        List<EmployeeAward> response = new ArrayList<>();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId);
        UserEmployeeDO employeeDO = employeeDao.getData(query.buildQuery());
        // 判断员工是否已认证
        if (employeeDO == null || employeeDO.getId() == 0 || employeeDO.getActivation() != 0) {
            log.info("员工信息不存在或未认证，employeeInfo = {}", employeeDO);
            return response;
        }
        List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
        try {
            Response result = searchService.queryAwardRankingInWx(companyIds, timespan, employeeId);
            log.info("awardRanking:", result);
            if (result.getStatus() == 0){

                // 解析数据
                Map<Integer, JSONObject> map = JSON.parseObject(result.getData(), Map.class);
                query.clear();
                query.where(new Condition("id", map.keySet(), ValueOp.IN));
                Map<Integer, UserEmployeeDO> employeeDOMap = new HashMap<>();
                employeeDOMap.putAll(employeeDao.getDatas(query.buildQuery()).stream().filter(m -> m != null && m.getId() > 0).collect(Collectors.toMap(k -> k.getId(), v -> v)));
                log.info("employeeDOMap:{}", employeeDOMap);
                List<Integer> userIds = employeeDOMap.values().stream().map(m -> m.getSysuserId()).collect(Collectors.toList());
                // 用户头像获取，获取顺序 user_user.headimg > user_wx_user.headimgurl > ""(默认头像)
                String defaultHeadImg = "https://cdn.moseeker.com/weixin/images/hr-avatar-default.png";
                query.clear();
                query.where(new Condition("company_id", employeeEntity.getCompanyIds(companyId), ValueOp.IN));
                List<Integer> wechatIds = wxWechatDao.getDatas(query.buildQuery()).stream().filter(m -> m != null && m.getId() > 0).map(m -> m.getId()).collect(Collectors.toList());
                query.clear();
                query.where(new Condition("sysuser_id", userIds, ValueOp.IN)).and(new Condition("wechat_id", wechatIds, ValueOp.IN));
                Map<Integer, String> wxUserHeadimg = wxUserDao.getDatas(query.buildQuery()).stream().filter(m -> m != null && m.getSysuserId() > 0 && StringUtils.isNotNullOrEmpty(m.getHeadimgurl())).collect(Collectors.toMap(k -> k.getSysuserId(), v -> v.getHeadimgurl(), (newKey, oldKey) -> newKey));
                query.clear();
                query.where(new Condition("id", userIds, ValueOp.IN));
                Map<Integer, String> userHeadimg = userDao.getDatas(query.buildQuery()).stream().map(m -> m.setHeadimg(StringUtils.isNullOrEmpty(m.getHeadimg())?wxUserHeadimg.getOrDefault(m.getId(),defaultHeadImg):m.getHeadimg())).collect(Collectors.toMap(k -> k.getId(), v -> v.getHeadimg(), (newKey, oldKey) -> newKey));
                log.info("userHeadimg:{}", userHeadimg);
                map.entrySet().stream().forEach(e -> {
                    EmployeeAward employeeAward = new EmployeeAward();
                    JSONObject value = e.getValue();
                    employeeAward.setEmployeeId(e.getKey());
                    employeeAward.setAwardTotal(value.getInteger("award"));
                    employeeAward.setName(Optional.ofNullable(employeeDOMap.get(e.getKey())).map(UserEmployeeDO::getCname).orElse(""));
                    employeeAward.setHeadimgurl(Optional.ofNullable(employeeDOMap.get(e.getKey())).map(m -> userHeadimg.getOrDefault(m.getSysuserId(), defaultHeadImg)).orElse(defaultHeadImg));
                    employeeAward.setRanking(value.getIntValue("ranking"));
                    response.add(employeeAward);
                });
            } else {
                log.error("query awardRanking data error");
            }
        } catch (TException e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }


    // 邮箱认证的员工记录保存在redis中所以要更新缓存数据
    public Result setCacheEmployeeCustomInfo(int userId, int companyId, String customValues)
            throws TException {
        log.info("setCacheEmployeeCustomInfo param: userId={}, companyId={}", userId, companyId, customValues);
        Result response = new Result();
        String pendingEmployee = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO, employeeEntity.getAuthInfoKey(userId, companyId));
        if(StringUtils.isNotNullOrEmpty(pendingEmployee)) {
            UserEmployeeDO employeeDO = JSONObject.parseObject(pendingEmployee, UserEmployeeDO.class);
            employeeDO.setCustomFieldValues(customValues);
            client.set(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO, employeeEntity.getAuthInfoKey(userId, companyId), JSONObject.toJSONString(employeeDO));
            response.setSuccess(true);
            response.setMessage("success");
        } else {
            response.setSuccess(false);
            response.setMessage("员工信息不存在");
        }
        log.info("setCacheEmployeeCustomInfo response: {}", response);
        return response;
    }

    public int upsertCompanyReferralConf(HrCompanyReferralConfDO conf) throws CommonException{
        if(conf != null){
            ValidateUtil vu = new ValidateUtil();
            if(StringUtils.isNotNullOrEmpty(conf.getText())){
                vu.addSensitiveValidate("內推文案", conf.getText(), null, null);
            }
            if(StringUtils.isNotNullOrEmpty(conf.getLink())){
                vu.addRegExpressValidate("內推链接", conf.getLink(), "^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",null, null);
            }
            vu.addIntTypeValidate("内推政策优先级", conf.getPriority(), null, null, 0,3);
            String message = vu.validate();
            if(StringUtils.isNullOrEmpty(message)){
                return referralConfDao.upsertHrCompanyReferralConf(conf);
            }else{
                throw ExceptionFactory.buildException(ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getCode(),
                        ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getMsg().replace("{MESSAGE}", message));
            }
        }
        throw ExceptionFactory.buildException(ExceptionCategory.REFERRAL_CONF_DATA_EMPTY);
    }

    public HrCompanyReferralConfDO getCompanyReferralConf(int companyId) throws CommonException{
        if(companyId > 0){
            Query query=new Query.QueryBuilder().where(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.COMPANY_ID.getName(), companyId)
                    .buildQuery();
            HrCompanyReferralConfDO confDO = referralConfDao.getData(query);
            return confDO;
        }
        throw  ExceptionFactory.buildException(ExceptionCategory.COMPANYID_ENPTY);
    }

    public void upsertReferralPolicy(int companyId, int userId) throws CommonException{
        if(companyId > 0){
            Query query=new Query.QueryBuilder().where(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.COMPANY_ID.getName(), companyId)
                    .buildQuery();
            HrCompanyReferralConfDO confDO = referralConfDao.getData(query);

        }
        throw  ExceptionFactory.buildException(ExceptionCategory.COMPANYID_ENPTY);
    }

}
