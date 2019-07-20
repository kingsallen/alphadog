package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyReferralConf;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrLeaderBoard;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.*;
import com.moseeker.entity.pojos.EmployeeInfo;
import com.moseeker.entity.pojos.PositionInfo;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeReferralPolicyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.useraccounts.constant.LeaderBoardType;
import com.moseeker.useraccounts.domain.LeaderBoardEntity;
import com.moseeker.useraccounts.domain.UpVoteEntity;
import com.moseeker.useraccounts.domain.UserEmployeeEntity;
import com.moseeker.useraccounts.domain.pojo.EmployeeLeaderBoardInfo;
import com.moseeker.useraccounts.exception.ExceptionCategory;
import com.moseeker.useraccounts.exception.ExceptionFactory;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.EmployeeBinder;
import com.moseeker.useraccounts.service.impl.employee.EmployeeExtInfoTool;
import com.moseeker.useraccounts.service.impl.pojos.City;
import com.moseeker.useraccounts.service.impl.pojos.LeaderBoardInfo;
import com.moseeker.useraccounts.service.impl.pojos.ReferralCard;
import com.moseeker.useraccounts.service.impl.pojos.*;
import com.moseeker.useraccounts.service.impl.vo.EmployeeInfoVO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ltf 员工服务业务实现 2017年3月3日
 */
@Service
@CounterIface
public class EmployeeService {

    private Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Resource(name = "cacheClient")
    private RedisClient client;

    SearchengineServices.Iface searchService = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);

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
    private UserEmployeeReferralPolicyDao policyDao;

    @Autowired
    private UserUserDao userDao;

    @Autowired
    private CustomUpVoteDao upvoteDao;

    @Autowired
    private UpVoteEntity upVoteEntity;

    @Autowired
    private UserEmployeeEntity userEmployeeEntity;

    @Autowired
    private LeaderBoardEntity leaderBoardEntity;

    @Autowired
    private PositionEntity positionEntity;

    @Autowired
    private ReferralEntity referralEntity;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private HrCompanyDao companyDao;


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
            throws UserAccountException {
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
                response.setExists(true);
            } else {
                response.setExists(false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }

    public EmployeeVerificationConfResponse getEmployeeVerificationConfByUserId(int userId) throws UserAccountException {
        UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(userId);
        if (employeeDO == null) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        return getEmployeeVerificationConf(employeeDO.getCompanyId());
    }

    public Result bind(BindingParams bindingParams,int bindSource) throws TException {
        Result response = new Result();
        String authMethod = "auth_method_".concat(bindingParams.getType().toString().toLowerCase());
        if (!employeeBinder.containsKey(authMethod)) {
            response.setSuccess(false);
            response.setMessage("暂不支持该认证方式");
        } else {
            if (bindingParams.getCustomFieldValues() != null && !bindingParams.getCustomFieldValues().equals("")) {
                Map<Integer, String> customFieldValues = new HashMap<>(bindingParams.getCustomFieldValues().size());
                bindingParams.getCustomFieldValues().forEach((key, value) -> {
                    if (value != null && !value.trim().equals("")) {
                        customFieldValues.put(key, value);
                    }
                });
                bindingParams.setCustomFieldValues(customFieldValues);
            }
            response = employeeBinder.get(authMethod).bind(bindingParams,bindSource);
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

    public Pagination awardRanking(int employeeId, int companyId, String timespan, int pageNum, int pageSize) {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start("head");
        Pagination pagination = new Pagination();
        if (pageNum <0) {
            pageNum = 0;
        }
        pagination.setPageNum(pageNum);
        if (pageSize <= 0 || pageSize > Constant.DATABASE_PAGE_SIZE) {
            pageSize = Constant.PAGE_SIZE;
        }
        pagination.setPageSize(pageSize);

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId);
        UserEmployeeDO employeeDO = employeeDao.getData(query.buildQuery());
        // 判断员工是否已认证
        if (employeeDO == null || employeeDO.getId() == 0 || employeeDO.getActivation() != 0) {
            log.info("员工信息不存在或未认证，employeeInfo = {}", employeeDO);
            return pagination;
        }
        List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
        int count = 0;
        try {
            count = searchService.countLeaderBoard(companyIds, timespan);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        pagination.setTotalRow(count);
        List<EmployeeAward> data = new ArrayList<>();
        stopWatch.stop();
        try {
            stopWatch.start("listLeaderBoard");
            Response result = searchService.listLeaderBoard(companyIds, timespan, employeeId, pageNum, pageSize);
            stopWatch.stop();
            stopWatch.start("package info");
            log.info("awardRanking:", result);
            if (result.getStatus() == 0){

                // 解析数据
                Map<Integer, JSONObject> map = JSON.parseObject(result.getData(), Map.class);
                query.clear();
                query.where(new Condition("id", map.keySet(), ValueOp.IN));
                Map<Integer, UserEmployeeDO> employeeDOMap = new HashMap<>();
                employeeDOMap.putAll(employeeDao.getDatas(query.buildQuery())
                        .stream().filter(m -> m != null && m.getId() > 0)
                        .collect(Collectors.toMap(k -> k.getId(), v -> v)));
                log.info("employeeDOMap:{}", employeeDOMap);
                List<Integer> userIds = employeeDOMap.values().stream().map(m -> m.getSysuserId()).collect(Collectors.toList());
                // 用户头像获取，获取顺序 user_user.headimg > user_wx_user.headimgurl > ""(默认头像)
                query.clear();
                query.where(new Condition("company_id", employeeEntity.getCompanyIds(companyId), ValueOp.IN));
                List<Integer> wechatIds = wxWechatDao.getDatas(query.buildQuery())
                        .stream()
                        .filter(m -> m != null && m.getId() > 0).map(m -> m.getId())
                        .collect(Collectors.toList());
                query.clear();
                query.where(new Condition("sysuser_id", userIds, ValueOp.IN))
                        .and(new Condition("wechat_id", wechatIds, ValueOp.IN));
                Map<Integer, UserWxUserDO> wxUserHeadimg = wxUserDao.getDatas(query.buildQuery())
                        .stream()
                        .filter(m -> m != null && m.getSysuserId() > 0 && StringUtils.isNotNullOrEmpty(m.getHeadimgurl()))
                        .collect(Collectors.toMap(k -> k.getSysuserId(), v -> v, (newKey, oldKey) -> newKey));
                query.clear();
                query.where(new Condition("id", userIds, ValueOp.IN));
                Map<Integer, UserUserDO> userHeadimg = userDao.getDatas(query.buildQuery()).stream().collect(Collectors.toMap(k -> k.getId(), v -> v));
                log.info("userHeadimg:{}", userHeadimg);
                map.entrySet().stream().forEach(e -> {
                    EmployeeAward employeeAward = new EmployeeAward();
                    JSONObject value = e.getValue();
                    employeeAward.setEmployeeId(e.getKey());
                    employeeAward.setAwardTotal(value.getInteger("award"));
                    String name = "";
                    if (employeeDOMap.get(e.getKey()) != null) {
                        name = employeeDOMap.get(e.getKey()).getCname();
                    }
                    if(value.getInteger("award")<0){
                        employeeAward.setAwardTotal(0);
                    }
                   String headImg = "";
                    if (employeeDOMap.get(e.getKey()) != null) {
                        if (userHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()) != null) {
                            if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                                log.info("key:{},  value:{}", employeeDOMap.get(e.getKey()).getSysuserId(), userHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()));
                                name = org.apache.commons.lang.StringUtils
                                        .isNotBlank(userHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()).getName())?
                                        userHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()).getName():
                                        userHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()).getNickname();
                            }
                            headImg = userHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()).getHeadimg();
                        }
                        if (wxUserHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()) != null) {
                            if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                                name = wxUserHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()).getNickname();
                            }
                            if (org.apache.commons.lang.StringUtils.isBlank(headImg)) {
                                headImg = wxUserHeadimg.get(employeeDOMap.get(e.getKey()).getSysuserId()).getHeadimgurl();
                            }
                        }
                    }
                    employeeAward.setName(Optional.ofNullable(name).orElse(""));
                    employeeAward.setHeadimgurl(Optional.ofNullable(headImg).orElse(""));
                    employeeAward.setRanking(value.getIntValue("ranking"));
                    data.add(employeeAward);
                });
                List<Integer> employeeIdList = data.stream().map(EmployeeAward::getEmployeeId).collect(Collectors.toList());
                List<com.moseeker.useraccounts.domain.pojo.UpVoteData> list= upVoteEntity.fetchUpVote(employeeId, employeeIdList);
                log.info("fetchUpVote list:{}", JSON.toJSONString(list));
                if (list != null && list.size() > 0) {
                    data.forEach(employeeAward -> {
                        Optional<com.moseeker.useraccounts.domain.pojo.UpVoteData> upVoteDataOptional =
                                list
                                        .stream()
                                        .filter(upVoteData -> upVoteData.getReceiver() == employeeAward.getEmployeeId())
                                        .findAny();
                        if (upVoteDataOptional.isPresent()) {
                            log.info("upVote exist employeeId:{}   upVote:{}", employeeAward.getEmployeeId(), JSON.toJSONString(upVoteDataOptional.get()));
                            employeeAward.setPraised(upVoteDataOptional.get().isUpVote());
                            employeeAward.setPraise(upVoteDataOptional.get().getReceiverUpVoteCount());
                        }
                    });
                }

            } else {
                log.error("query awardRanking data error");
            }
            stopWatch.stop();
            pagination.setData(data);
        } catch (TException e) {
            log.error(e.getMessage(), e);
        }
        log.info(stopWatch.prettyPrint());
        return pagination;
    }


    // 邮箱认证的员工记录保存在redis中所以要更新缓存数据
    public Result setCacheEmployeeCustomInfo(int userId, int companyId, String customValues)
            throws TException {
        log.info("setCacheEmployeeCustomInfo param: userId={}, companyId={}", userId, companyId, customValues);
        Result response = new Result();

        // 查询已经认证的员工
        UserEmployeeRecord userEmployee = employeeDao.getActiveEmployee(userId,companyId);

        // 如果员工已经认证，则更新custom_field_values
        if (userEmployee != null) {
            if ((org.apache.commons.lang.StringUtils.isBlank(userEmployee.getCustomFieldValues())
                    || "[]".equals(userEmployee.getCustomFieldValues()))
                    && StringUtils.isNotNullOrEmpty(customValues)) {
                userEmployee.setCustomFieldValues(customValues);
                employeeDao.updateRecord(userEmployee);
            }

            response.setSuccess(true);
            response.setMessage("success");
            return response;
        }
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
           return referralConfDao.upsertHrCompanyReferralConf(conf);
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
        if(userId > 0){
            if(employeeEntity.isEmployee(userId, companyId)){
                UserEmployeeDO employeeDO = employeeEntity.getCompanyEmployee(userId, companyId);
                retryUpdateReferralPolicyCount(employeeDO, 0);
                return;
            }
            throw ExceptionFactory.buildException(ExceptionCategory.PERMISSION_DENIED);
        }
        throw ExceptionFactory.buildException(ExceptionCategory.PROGRAM_PARAM_NOTEXIST);
    }

    private void retryUpdateReferralPolicyCount(UserEmployeeDO employeeDO, int index) throws CommonException {
        if (index >= Constant.RETRY_UPPER_LIMIT) {
            throw ExceptionFactory.buildException(ExceptionCategory.REFERRAL_POLICY_UPDATE_FIALED);
        }
        index++;
        int count = 0;
        UserEmployeeReferralPolicyDO policyDO = policyDao.getEmployeeReferralPolicyDOByEmployeeId(employeeDO.getId());
        if(policyDO == null){
            count = policyDao.upsertCompanyReferralConfCount(employeeDO.getId());
        }else{
            count = policyDao.updateReferralPolicyByEmployeeIdAndCount(employeeDO.getId(),policyDO.getCount());
        }

        if (count == 0) {
            retryUpdateReferralPolicyCount(employeeDO, index);
        }
    }

    /**
     * 统计点赞数量
     * @param employeeId 员工编号
     * @return 点赞数
     * @throws UserAccountException 异常信息
     */
    public int countUpVote(int employeeId) throws UserAccountException {
        UserEmployeeDO userEmployeeDO = employeeEntity.getEmployeeByID(employeeId);
        if (userEmployeeDO == null || userEmployeeDO.getId() == 0) {
            throw UserAccountException.AWARD_EMPLOYEE_ELEGAL;
        }
        return upvoteDao.countUpVote(employeeId);
    }

    /**
     * 点赞
     * @param employeeId 点赞的员工编号
     * @param colleague 被点赞的员工编号
     * @return 点赞记录编号
     * @throws UserAccountException 业务异常
     */
    public int upVote(int employeeId, int colleague) throws UserAccountException {

        UpVoteData upVoteData = userEmployeeEntity.findEmployee(employeeId, colleague);

        return upVoteEntity.upVote(upVoteData.getReceiver(), upVoteData.getSender());
    }

    /**
     * 取消点赞
     * @param employeeId 点赞的员工编号
     * @param colleague 被点赞的员工编号
     * @throws UserAccountException 业务异常
     */
    public void removeUpVote(int employeeId, int colleague) throws UserAccountException {
        UpVoteData upVoteData = userEmployeeEntity.findEmployee(employeeId, colleague);
        upVoteEntity.cancelUpVote(upVoteData.getReceiver(), upVoteData.getSender());
    }

    /**
     * 查找指定员工的榜单信息
     * @param id 员工编号
     * @param type 榜单类型
     * @return 榜单信息
     * @throws CommonException 业务异常
     */
    public LeaderBoardInfo fetchLeaderBoardInfo(int id, int type) throws CommonException {
        EmployeeInfo employeeInfo = employeeEntity.fetchEmployeeInfo(id);
        if (employeeInfo == null) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        LeaderBoardType leaderBoardType = LeaderBoardType.instanceFromValue(type);
        if (leaderBoardType == null) {
            throw UserAccountException.EMPLOYEE_LEADERBOARDER_NOT_EXISTS;
        }
        LeaderBoardInfo leaderBoardInfo = new LeaderBoardInfo();
        leaderBoardInfo.setId(id);
        leaderBoardInfo.setUsername(employeeInfo.getName());
        leaderBoardInfo.setIcon(employeeInfo.getHeadImg());
        EmployeeLeaderBoardInfo info = leaderBoardEntity.fetchLeaderBoardInfo(employeeInfo, leaderBoardType);
        leaderBoardInfo.setLevel(info.getSort());
        leaderBoardInfo.setPoint(info.getAward());
        leaderBoardInfo.setPraised(upVoteEntity.isPraise(id, id));
        leaderBoardInfo.setPraise(upVoteEntity.countUpVote(id));
        return leaderBoardInfo;
    }

    /**
     * 查找非指定员工的最后一名的员工榜单信息
     * @param id
     * @param type
     * @return
     */
    public LeaderBoardInfo fetchLastLeaderBoardInfo(int id, int type) {
        EmployeeInfo employeeInfo = employeeEntity.fetchEmployeeInfo(id);
        if (employeeInfo == null) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        LeaderBoardType leaderBoardType = LeaderBoardType.instanceFromValue(type);
        if (leaderBoardType == null) {
            throw UserAccountException.EMPLOYEE_LEADERBOARDER_NOT_EXISTS;
        }
        LeaderBoardInfo leaderBoardInfo = new LeaderBoardInfo();
        EmployeeLeaderBoardInfo info = leaderBoardEntity.fetchLasteaderBoardInfo(employeeInfo, leaderBoardType);
        if (info != null) {
            leaderBoardInfo.setId(info.getId());
            leaderBoardInfo.setPoint(info.getAward());
            leaderBoardInfo.setLevel(info.getSort());
            EmployeeInfo lastEmployee = employeeEntity.fetchEmployeeInfo(info.getId());
            leaderBoardInfo.setUsername(lastEmployee.getName());
            leaderBoardInfo.setIcon(lastEmployee.getHeadImg());
            leaderBoardInfo.setPraised(upVoteEntity.isPraise(id, info.getId()));
            leaderBoardInfo.setPraise(upVoteEntity.countUpVote(info.getId()));
        }

        return leaderBoardInfo;
    }

    public HrLeaderBoard fetchLeaderBoardType(int companyId) {
        return leaderBoardEntity.fetchLeaderBoardType(companyId);
    }

    /**
     * 修改榜单那信息
     * @param companyId
     * @param type
     */
    public void updateLeaderBoardType(int companyId, byte type) {
        leaderBoardEntity.update(companyId, type);
    }

    /**
     * 查找认证员工数量
     * @param companyId 公司编号
     * @return 员工数量
     */
    public int countEmployee(int companyId) {
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        return employeeEntity.countActiveEmployeeByCompanyIds(companyIdList);
    }

    /**
     * 计算最近点赞数。最近点赞数是指上次看榜单的时间距现在的点赞数
     * @param employeeId 员工编号
     * @return 点赞数
     */
    public int countRecentUpVote(int employeeId) {
        return upVoteEntity.countRecentUpVote(employeeId);
    }

    /**
     * 清空点赞数
     */
    public void clearUpVoteWeekly() {
        upVoteEntity.clearUpVoteWeekly();
    }

    /**
     * 设置员工推荐简历的方式
     * 目前主要于电脑端扫码上传，需要标记员工上传的是哪个职位。
     * @param employeeId 员工编号
     * @param positionId 职位编号
     * @param type 1 手机文件上传 2 电脑扫码上传 3 推荐
     */
    public void setUploadType(int employeeId, int positionId, byte type) {

        if (employeeEntity.getEmployeeByID(employeeId) == null) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }

        JobPositionRecord record = positionEntity.getPositionByID(positionId);
        if (record == null || record.getStatus() > 0) {
            throw UserAccountException.AWARD_POSITION_ALREADY_DELETED;
        }

        ReferralType referralType = new ReferralType();
        referralType.setEmployeeId(employeeId);
        referralType.setPositionId(positionId);
        referralType.setType(type);

        client.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE_TYPE.toString(),
                String.valueOf(employeeId), null, JSON.toJSONString(referralType), 24*60*60);
    }

    /**
     * 查找电脑端上传配置的职位信息
     * @param employeeId 员工编号
     * @return 配置的职位信息
     */
    public ReferralPositionInfo getUploadType(int employeeId) throws UserAccountException {

        UserEmployeeDO userEmployeeDO = employeeEntity.getEmployeeByID(employeeId);
        if (userEmployeeDO == null || userEmployeeDO.getId() == 0) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }

        String referralTypeInfoString = client.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE_TYPE.toString(),
                String.valueOf(userEmployeeDO.getId()), (String) null);
        if (org.apache.commons.lang.StringUtils.isBlank(referralTypeInfoString)) {
            throw UserAccountException.ERMPLOYEE_REFERRAL_TYPE_NOT_EXIST;
        }
        ReferralType referralType = JSONObject.parseObject(referralTypeInfoString, ReferralType.class);

        PositionInfo positionInfo = positionEntity.getPositionInfo(referralType.getPositionId());
        if (positionInfo == null) {
            throw UserAccountException.AWARD_POSITION_ALREADY_DELETED;
        }
        List<Integer> companyIdList = employeeEntity.getCompanyIds(userEmployeeDO.getCompanyId());
        if (!companyIdList.contains(positionInfo.getCompanyId())) {
            throw UserAccountException.NO_PERMISSION_EXCEPTION;
        }

        ReferralPositionInfo referralPositionInfo = new ReferralPositionInfo();
        referralPositionInfo.setId(positionInfo.getId());
        referralPositionInfo.setTitle(positionInfo.getTitle());
        referralPositionInfo.setSalaryBottom(positionInfo.getSalaryBottom());
        referralPositionInfo.setSalaryTop(positionInfo.getSalaryTop());
        try {
            int experience = Integer.valueOf(positionInfo.getExperience());
            referralPositionInfo.setExperience(experience);
        } catch (NumberFormatException e) {
            log.info("getUploadType positioniId:{} 工作经验不是数值类型。experience:{}", positionInfo.getId(), positionInfo.getExperience());
        }
        referralPositionInfo.setExperienceAbove(positionInfo.getExperienceAbove() != null && positionInfo.getExperienceAbove() == 1);
        referralPositionInfo.setCompanyAbbreviation(positionInfo.getCompanyAbbreviation());
        referralPositionInfo.setCompanyName(positionInfo.getCompanyName());
        referralPositionInfo.setLogo(positionInfo.getLogo());
        referralPositionInfo.setTeam(positionInfo.getTeamName());
        if (positionInfo.getCities() != null && positionInfo.getCities().size() > 0) {
            referralPositionInfo.setCities(positionInfo.getCities().stream().map(dictCity -> {
                City city = new City();
                BeanUtils.copyProperties(dictCity, city);
                return city;
            }).collect(Collectors.toList()));
        }

        return referralPositionInfo;
    }

    /**
     * 获取推荐名片信息
     * @param referralLogId 推荐记录编号
     * @return 推荐名片
     */
    public ReferralCard getReferralCard(int referralLogId) throws UserAccountException {
        ReferralLog referralLog = referralEntity.fetchReferralLog(referralLogId);
        if (referralLog == null) {
            throw UserAccountException.ERMPLOYEE_REFERRAL_LOG_NOT_EXIST;
        }

        UserEmployeeDO userEmployeeDO = employeeEntity.getEmployeeByID(referralLog.getEmployeeId());
        if (userEmployeeDO == null || userEmployeeDO.getId() == 0) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        if (org.apache.commons.lang.StringUtils.isBlank(userEmployeeDO.getCname())) {
            UserUserDO employeeUser = userDao.getUser(userEmployeeDO.getSysuserId());
            if (employeeUser != null) {
                userEmployeeDO.setCname(
                        org.apache.commons.lang.StringUtils.isNotBlank(employeeUser.getName()) ?
                                employeeUser.getName():employeeUser.getNickname());
            }
        }


        PositionInfo positionInfo = positionEntity.getPositionInfo(referralLog.getPositionId());
        if (positionInfo == null) {
            throw UserAccountException.AWARD_POSITION_ALREADY_DELETED;
        }

        UserUserDO userUserDO = userDao.getUser(referralLog.getReferenceId());
        if (userUserDO == null) {
            throw UserAccountException.ERMPLOYEE_REFERRAL_USER_NOT_EXIST;
        }

        ReferralCard referralCard = new ReferralCard();
        referralCard.setUserId(userUserDO.getId());
        referralCard.setUserName(org.apache.commons.lang.StringUtils.isNotBlank(userUserDO.getName()) ?
                userUserDO.getName():userUserDO.getNickname());
        referralCard.setEmployeeId(userEmployeeDO.getId());
        referralCard.setEmployeeName(userEmployeeDO.getCname());
        referralCard.setCompanyName(positionInfo.getCompanyName());
        referralCard.setCompanyAbbreviation(positionInfo.getCompanyAbbreviation());
        referralCard.setPosition(positionInfo.getTitle());
        referralCard.setMobile(String.valueOf(userUserDO.getMobile()));
        referralCard.setClaim(referralLog.getClaim() == 1);
        JobApplication application = applicationDao.getByUserIdAndPositionId(referralLog.getReferenceId(), referralLog.getPositionId());
        if (application != null) {
            referralCard.setApplyId(application.getId());
        }
        return referralCard;
    }

    /**
     * 根据用户编号查找员工以及所在公司的信息
     * @param userId 用户编号
     * @return 员工信息
     * @throws UserAccountException 业务异常
     */
    public EmployeeInfoVO getEmployeeInfo(int userId) throws UserAccountException{
        UserEmployeeDO userEmployeeDO = employeeEntity.getActiveEmployeeDOByUserId(userId);
        if (userEmployeeDO == null) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
        employeeInfoVO.setId(userEmployeeDO.getId());
        employeeInfoVO.setName(userEmployeeDO.getCname());
        employeeInfoVO.setUserId(userId);
        employeeInfoVO.setCompanyId(userEmployeeDO.getCompanyId());

        HrCompanyDO companyDO = companyDao.getCompanyById(employeeInfoVO.getCompanyId());
        if (companyDO != null) {
            employeeInfoVO.setCompanyAbbreviation(companyDO.getAbbreviation());
            employeeInfoVO.setCompanyName(companyDO.getName());
        }

        HrWxWechatDO hrWxWechatDO = wxWechatDao.getHrWxWechatByCompanyId(employeeInfoVO.getCompanyId());
        if (hrWxWechatDO != null) {
            employeeInfoVO.setSignature(hrWxWechatDO.getSignature());
        }

        if (org.apache.commons.lang.StringUtils.isBlank(employeeInfoVO.getName())) {
            UserUserDO userUserDO = userDao.getUser(employeeInfoVO.getUserId());
            if (userUserDO != null) {
                employeeInfoVO.setName(org.apache.commons.lang.StringUtils.isNotBlank(userUserDO.getName())
                        ? userUserDO.getName() : userUserDO.getNickname());
            }
            if (org.apache.commons.lang.StringUtils.isBlank(employeeInfoVO.getName())) {
                UserWxUserRecord record = wxUserDao.getWXUserByUserId(userId);
                if (record != null) {
                    employeeInfoVO.setName(record.getNickname());
                }
            }
        }


        return employeeInfoVO;
    }

    /**
     * 修改员工补填信息
     * @param userId 员工编号
     * @param companyId 公司编号
     * @param customValues 自定义信息
     * @throws UserAccountException 异常信息
     */
    public void patchEmployeeCustomFieldValues(int userId, int companyId,  Map<Integer, List<String>> customValues)
            throws UserAccountException {

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", String.valueOf(companyId));

        List<HrEmployeeCustomFields> confs = hrEmployeeCustomFieldsDao.getEmployeeExtConfByCompanyId(companyId);

        if (!EmployeeExtInfoTool.verifyCustomFieldInfo(customValues, confs)) {
            throw UserAccountException.ERMPLOYEE_EXTINFO_PARAM_ERROR;
        }

        JSONArray jsonArray = new JSONArray();
        customValues.forEach((key, value) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key.toString(), value);
            jsonArray.add(jsonObject);
        });
        //查询已经认证的员工
        UserEmployeeRecord userEmployee = employeeDao.getActiveEmployee(userId,companyId);
        if (userEmployee != null) {
            userEmployee.setCustomFieldValues(EmployeeExtInfoTool.mergeCustomFieldValue(userEmployee.getCustomFieldValues(), customValues));
            employeeDao.updateRecord(userEmployee);
        }

        String pendingEmployee = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO,
                employeeEntity.getAuthInfoKey(userId, companyId));
        if(StringUtils.isNotNullOrEmpty(pendingEmployee)) {
            UserEmployeeDO employeeDO = JSONObject.parseObject(pendingEmployee, UserEmployeeDO.class);
            employeeDO.setCustomFieldValues(EmployeeExtInfoTool.mergeCustomFieldValue(employeeDO.getCustomFieldValues(), customValues));
            client.set(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO,
                    employeeEntity.getAuthInfoKey(userId, companyId), JSONObject.toJSONString(employeeDO));
        }

    }

    public void retrySendVerificationMail(int userId, int companyId, int source) throws Exception {
        // 查询集团公司companyID列表
        List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("sysuser_id", String.valueOf(userId)).and(new Condition("company_id", companyIds, ValueOp.IN))
                .and("disable", String.valueOf(0)).and("activation", "0");
        List<UserEmployeeDO> employees = employeeDao.getDatas(query.buildQuery(), UserEmployeeDO.class);

        if (employees != null && employees.size() > 0) {
            throw UserAccountException.EMPLOYEE_ALREADY_VERIFIED;
        }

        String pendingEmployee = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO,
                employeeEntity.getAuthInfoKey(userId, companyId));
        if (org.apache.commons.lang3.StringUtils.isBlank(pendingEmployee)) {
            throw UserAccountException.EMPLOYEE_VERIFICATION_ACTIVATION_EXPIRED;
        }

        EmployeeBindByEmail bindByEmail = (EmployeeBindByEmail)employeeBinder.get("auth_method_email");
        bindByEmail.retrySendVerificationMail(userId, companyId, source);
    }
}
