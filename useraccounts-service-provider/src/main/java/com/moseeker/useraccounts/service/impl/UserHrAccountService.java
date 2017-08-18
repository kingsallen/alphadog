package com.moseeker.useraccounts.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.CandidateCommonEntity;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTalentpoolDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.employee.struct.RewardVO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.constant.ResultMessage;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.pojo.EmployeeRank;
import com.moseeker.useraccounts.pojo.EmployeeRankObj;
import com.moseeker.useraccounts.service.thirdpartyaccount.ThirdPartyAccountSynctor;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * HR账号服务
 * <p>
 * <p>
 * Created by zzh on 16/5/31.
 */
@Service
@CounterIface
public class UserHrAccountService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER
            .getService(SearchengineServices.Iface.class);

    private static final String REDIS_KEY_HR_SMS_SIGNUP = "HR_SMS_SIGNUP";

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private HrSearchConditionDao hrSearchConditionDao;

    @Autowired
    private HrTalentpoolDao hrTalentpoolDao;

    @Autowired
    private SmsSender smsSender;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Autowired
    private HRThirdPartyAccountHrDao thirdPartyAccountHrDao;

    @Autowired
    private ThirdPartyAccountSynctor thirdPartyAccountSynctor;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private HrImporterMonitorDao hrImporterMonitorDao;

    @Autowired
    private SearchengineEntity searchengineEntity;

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    CandidateCommonEntity candidateCommonEntity;

    /**
     * HR在下载行业报告是注册
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param source 系统区分 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
     */
    public Response sendMobileVerifiyCode(String mobile, String code, int source) throws TException {
        try {
            // HR账号验证码校验
            Response response = validateSendMobileVertifyCode(mobile, code, source);
            if (response.status > 0) {
                return response;
            }

            // 发送HR注册的验证码
            return ResponseUtils.success(smsSender.sendHrMobileVertfyCode(mobile, REDIS_KEY_HR_SMS_SIGNUP, source));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 下载行业报告，添加HR记录
     *
     * @param downloadReport
     * @return
     * @throws TException
     */
    public Response postResource(DownloadReport downloadReport) throws TException {
        try {
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredStringValidate("手机号码", downloadReport.getMobile(), null, null);
            vu.addRequiredStringValidate("验证码", downloadReport.getCode(), null, null);
            vu.addRequiredStringValidate("公司名称", downloadReport.getCompany_name(), null, null);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {

                String redisCode = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_HR_SMS_SIGNUP,
                        Constant.HR_ACCOUNT_SIGNUP_SOURCE_ARRAY[downloadReport.getSource() - 1],
                        downloadReport.getMobile());
                if (!downloadReport.getCode().equals(redisCode)) {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
                }

                String[] passwordArray = this.genPassword(null);
                // 密码生成及加密, 谨慎使用, 防止密码泄露, 有个漏洞, source不是官网以外的时候, 会生成密码, 无法告知
                UserHrAccountRecord userHrAccountRecord = new UserHrAccountRecord();
                if (downloadReport.isSetSource()) {
                    userHrAccountRecord.setSource(downloadReport.getSource());
                }
                userHrAccountRecord.setMobile(downloadReport.getMobile());
                userHrAccountRecord.setPassword(passwordArray[1]);
                userHrAccountRecord.setAccountType(2);
                userHrAccountRecord.setUsername(downloadReport.getMobile());
                if (downloadReport.isSetRegister_ip()) {
                    userHrAccountRecord.setRegisterIp(downloadReport.getRegister_ip());
                }
                if (downloadReport.isSetLast_login_ip()) {
                    userHrAccountRecord.setLastLoginIp(downloadReport.getLast_login_ip());
                }
                HrCompanyRecord companyRecord = new HrCompanyRecord();
                companyRecord.setType((byte) (1));
                if (downloadReport.isSetCompany_name()) {
                    companyRecord.setName(downloadReport.getCompany_name());
                }
                companyRecord.setSource((byte) (Constant.COMPANY_SOURCE_DOWNLOAD));
                int result = userHrAccountDao.createHRAccount(userHrAccountRecord, companyRecord);

                if (result > 0 && downloadReport.getSource() == Constant.HR_ACCOUNT_SIGNUP_SOURCE_WWW) {
                    smsSender.sendHrSmsSignUpForDownloadIndustryReport(downloadReport.getMobile(), passwordArray[0]);
                }
                if (result > 0) {
                    return ResponseUtils.success(new HashMap<String, Object>() {
                        {
                            put("userHrAccountId", result);
                        }
                    }); // 返回 userFavoritePositionId
                } else {
                    return ResponseUtils.success(result);
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", message));
            }
        } catch (RedisException e) {
            WarnService.notify(e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 更新HR账户信息
     *
     * @param userHrAccount 用户实体
     */
    public Response putResource(UserHrAccount userHrAccount) throws TException {
        try {
            // 必填项验证
            Response response = validatePutResource(userHrAccount);
            if (response.status > 0) {
                return response;
            }

            // 密码加密
            userHrAccount.setPassword(MD5Util.encryptSHA(userHrAccount.password));

            // 添加HR用户
            UserHrAccountRecord userHrAccountRecord = (UserHrAccountRecord) BeanUtils.structToDB(userHrAccount,
                    UserHrAccountRecord.class);

            int userHrAccountId = userHrAccountDao.updateRecord(userHrAccountRecord);
            if (userHrAccountId > 0) {
                return ResponseUtils.success(new HashMap<String, Object>() {
                    private static final long serialVersionUID = -5929607838950864392L;

                    {
                        put("userHrAccountId", userHrAccountId);
                    }
                }); // 返回 userFavoritePositionId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("putResource UserHrAccountRecord error: ", e);
        } finally {
            // do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 添加HR账号验证
     *
     * @param userHrAccount hr用户实体
     */
    private Response validatePostResource(UserHrAccount userHrAccount, String code) {

        Response response = new Response(0, "ok");
        if (userHrAccount == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if (userHrAccount.mobile == null || userHrAccount.mobile.equals("")) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        if (userHrAccount.source <= 0 || userHrAccount.source > 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE);
        }
        if (code == null || code.equals("")) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "code"));
        }

        String redisCode = null;
        try {
            redisCode = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_HR_SMS_SIGNUP,
                    Constant.HR_ACCOUNT_SIGNUP_SOURCE_ARRAY[userHrAccount.source - 1], userHrAccount.mobile);
        } catch (RedisException e) {
            WarnService.notify(e);
        }
        // 验证码无法验证
        if (!code.equals(redisCode)) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        return response;
    }

    /**
     * 更新HR账号验证
     *
     * @param userHrAccount hr用户实体
     */
    private Response validatePutResource(UserHrAccount userHrAccount) {

        Response response = new Response(0, "ok");
        if (userHrAccount == null) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if (userHrAccount.id <= 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "id"));
        }
        if (userHrAccount.mobile == null || userHrAccount.mobile.equals("")) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        if (userHrAccount.password == null || userHrAccount.password.equals("")) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "password"));
        }
        if (userHrAccount.source <= 0 || userHrAccount.source > 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE);
        }
        return response;
    }


    /**
     * HR账号验证码校验
     * <p>
     */
    private Response validateSendMobileVertifyCode(String mobile, String code, int source) {

        Response response = new Response(0, "ok");
        if (mobile == null || mobile.equals("")) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        if (code == null || code.equals("")) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "code"));
        }
        if (source <= 0 || source > 5) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE);
        }
        return response;
    }

    /**
     * 判断密码为空时生成密码 密码加密
     * <p>
     *
     * @return String[] 0:原始密码 1:加密后密码
     */
    private String[] genPassword(String passowrd) {
        String[] passwordArray = new String[2];
        String plainPassword = "8E69c6";
        if (passowrd == null || passowrd.trim().equals("")) {
            plainPassword = StringUtils.getRandomString(6);
        } else {
            plainPassword = passowrd;
        }

        passwordArray[0] = plainPassword;
        passwordArray[1] = MD5Util.encryptSHA(MD5Util.md5(plainPassword));

        return passwordArray;
    }

    /**
     * 获取常用筛选项
     *
     * @param hrAccountId
     * @param type        (0:候选人列表筛选项， 1：人才库列表筛选)
     * @return
     * @throws TException
     */
    public Response getSearchCondition(int hrAccountId, int type) {
        logger.info("UserHrAccountService - getSearchCondition ");
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("hr_account_id", String.valueOf(hrAccountId)).and("type", String.valueOf(type));
        List<SearchCondition> list;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            list = hrSearchConditionDao.getDatas(query.buildQuery(), SearchCondition.class);
            logger.info("UserHrAccountService - getSearchCondition  list:{}", list);
            list.forEach(sc -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", sc.getId());
                map.put("name", org.apache.commons.lang.StringUtils.defaultString(sc.getName()));
                map.put("publisher", sc.getPublisher());
                map.put("position_id", sc.getPosition_id());
                map.put("keyword", org.apache.commons.lang.StringUtils.defaultString(sc.getKeyword()));
                map.put("submit_time", org.apache.commons.lang.StringUtils.defaultString(sc.getSubmit_time()));
                map.put("work_years", sc.getWork_years());
                map.put("city_name", org.apache.commons.lang.StringUtils.defaultString(sc.getCity_name()));
                map.put("degree", org.apache.commons.lang.StringUtils.defaultString(sc.getDegree()));
                map.put("past_position", org.apache.commons.lang.StringUtils.defaultString(sc.getPast_position()));
                map.put("in_last_job_search_position", sc.getIn_last_job_search_position());
                map.put("min_age", sc.getMin_age());
                map.put("max_age", sc.getMax_age());
                map.put("intention_city_name", org.apache.commons.lang.StringUtils.defaultString(sc.getIntention_city_name()));
                map.put("sex", sc.getSex());
                map.put("intention_salary_code", org.apache.commons.lang.StringUtils.defaultString(sc.getIntention_salary_code()));
                map.put("company_name", org.apache.commons.lang.StringUtils.defaultString(sc.getCompany_name()));
                map.put("in_last_job_search_company", sc.getIn_last_job_search_company());
                map.put("hr_account_id", sc.getHr_account_id());
                map.put("update_time", sc.getUpdate_time());
                map.put("type", sc.getType());
                result.add(map);
            });
            logger.info("UserHrAccountService - getSearchCondition  result:{}", result);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.info("UserHrAccountService - getSearchCondition  error:{}", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 添加常用筛选条件
     *
     * @param searchCondition
     * @return
     */
    public Response postSearchCondition(SearchCondition searchCondition) {
        try {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("hr_account_id", String.valueOf(searchCondition.getHr_account_id())).and("type", String.valueOf(searchCondition.getType()));
            int row = hrSearchConditionDao.getCount(query.buildQuery());
            // 每个hr最多只能添加10条常用筛选
            if (row >= 10) {
                logger.warn("保存常用筛选失败，hr={}，已拥有{}条常用筛选项", searchCondition.getHr_account_id(), row);
                return ResponseUtils.fail("{'status':42004,'message':'添加失败，最多只能添加10条筛选项'}");
            }
            // 筛选项名字保证不重复
            query.and("name", searchCondition.getName());
            row = hrSearchConditionDao.getCount(query.buildQuery());
            if (row > 0) {
                logger.warn("保存常用筛选失败，筛选项名称={}，已存在", searchCondition.getName());
                return ResponseUtils.fail("{'status':42004,'message':'保存失败，改筛选项名称已存在'}");
            }
            int primaryKey = hrSearchConditionDao.addRecord(BeanUtils.structToDB(searchCondition, HrSearchConditionRecord.class)).getId();
            if (primaryKey > 0) {
                return ResponseUtils.success(primaryKey);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 删除常用筛选条件
     *
     * @param hrAccountId
     * @param id
     * @return
     */
    public Response delSearchCondition(int hrAccountId, int id) {
        int resultRow = 0;
        try {
            resultRow = hrSearchConditionDao.delResource(hrAccountId, id);
            if (resultRow > 0) {
                return ResponseUtils.success("");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 加入人才库
     *
     * @param hrAccountId
     * @param applierIds
     * @return
     */
    @UpdateEs(tableName = "hr_talentpool", argsIndex = 1, argsName = "user_id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Response joinTalentpool(int hrAccountId, List<Integer> applierIds) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("hr_account_id", String.valueOf(hrAccountId));
        int resultRow = 0;
        for (Integer applierId : applierIds) {
            query.and("applier_id", String.valueOf(applierId));
            HrTalentpoolDO talentpool = hrTalentpoolDao.getData(query.buildQuery());
            if (talentpool == null || talentpool.getId() == 0) {
                // 将用户加入人才库
                talentpool = new HrTalentpoolDO();
                talentpool.setApplierId(Integer.valueOf(applierId));
                talentpool.setHrAccountId(hrAccountId);
                resultRow += hrTalentpoolDao.addData(talentpool).getId();
            } else {
                // 将状态改为正常
                talentpool.setStatus(0);
                resultRow += hrTalentpoolDao.updateData(talentpool);
            }
        }
        if (resultRow > 0) {
            return ResponseUtils.success("");
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
        }
    }

    /**
     * 移出人才库
     *
     * @param hrAccountId
     * @param applierIds
     * @return
     */
    @UpdateEs(tableName = "hr_talentpool", argsIndex = 1, argsName = "user_id")
    public Response shiftOutTalentpool(int hrAccountId, List<Integer> applierIds) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("hr_account_id", String.valueOf(hrAccountId));
        try {
            int resultRow = 0;
            for (Integer applierId : applierIds) {
                query.and("applier_id", String.valueOf(applierId));
                HrTalentpoolDO talentpool = hrTalentpoolDao.getData(query.buildQuery());
                if (talentpool != null && talentpool.getId() > 0) {
                    // 将状态改为删除
                    talentpool.setStatus(1);
                    resultRow += hrTalentpoolDao.updateData(talentpool);
                }
            }
            if (resultRow <= 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            } else {
                return ResponseUtils.success("");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }


    /**
     * 分页获取userHrAccount数据
     *
     * @param companyId
     * @param disable
     * @return
     */
    public Response userHrAccount(int companyId, int disable, int page, int per_age) {
        try {
            Query.QueryBuilder query = new Query.QueryBuilder();
            query.where("company_id", String.valueOf(companyId)).and("disable", String.valueOf(disable));
            query.setPageNum(page);
            query.setPageSize(per_age);
            return ResponseUtils.success(userHrAccountDao.getDatas(query.buildQuery(), UserHrAccount.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public HrNpsResult npsStatus(int userId, String startDate, String endDate) throws Exception {
        return userHrAccountDao.npsStatus(userId, startDate, endDate);
    }

    public HrNpsResult npsUpdate(HrNpsUpdate npsUpdate) throws Exception {
        return userHrAccountDao.npsUpdate(npsUpdate);
    }

    public HrNpsStatistic npsList(String startDate, String endDate, int page, int pageSize) throws Exception {
        return userHrAccountDao.npsList(startDate, endDate, page, pageSize);
    }

    public List<HrThirdPartyAccountDO> getThirdPartyAccounts(Query query) throws TException {
        return hrThirdPartyAccountDao.getDatas(query);
    }

    public int updateThirdPartyAccount(HrThirdPartyAccountDO account) throws CommonException, TException {
        return hrThirdPartyAccountDao.updateData(account);
    }

    /**
     * 员工列表，员工数量查询条件封装
     *
     * @param queryBuilder 查询条件
     * @param keyWord      关键字
     * @param companyId    公司ID
     * @return
     */
    private Query.QueryBuilder getQueryBuilder(Query.QueryBuilder queryBuilder, String keyWord, Integer companyId) throws CommonException {
        List<UserEmployeeDO> userEmployeeDOList = employeeEntity.getUserEmployeeDOList(companyId);
        List<Integer> sysIdsTemp = userEmployeeDOList.stream().filter(userEmployeeDO -> userEmployeeDO.getSysuserId() > 0)
                .map(userEmployeeDO -> userEmployeeDO.getSysuserId()).collect(Collectors.toList());
        Condition sysuserId = new Condition(UserUser.USER_USER.ID.getName(), sysIdsTemp, ValueOp.IN);
        Condition nickName = new Condition(UserUser.USER_USER.NICKNAME.getName(), keyWord, ValueOp.LIKE);

        Query.QueryBuilder nicknameCondition = new Query.QueryBuilder();
        nicknameCondition.where(sysuserId).and(nickName);
        List<UserUserDO> userUserDOList = userUserDao.getDatas(nicknameCondition.buildQuery());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("%");
        stringBuffer.append(keyWord);
        stringBuffer.append("%");
        List<Integer> sysIds = userUserDOList.stream().map(userUserDO -> userUserDO.getId()).collect(Collectors.toList());
        // 名字
        Condition cname = new Condition(UserEmployee.USER_EMPLOYEE.CNAME.getName(), stringBuffer.toString(), ValueOp.LIKE);
        // 自定义字段
        Condition customField = new Condition(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.getName(), stringBuffer.toString(), ValueOp.LIKE);
        // 邮箱
        Condition email = new Condition(UserEmployee.USER_EMPLOYEE.EMAIL.getName(), stringBuffer.toString(), ValueOp.LIKE);
        // 手机号码
        Condition mobile = new Condition(UserEmployee.USER_EMPLOYEE.MOBILE.getName(), stringBuffer.toString(), ValueOp.LIKE);

        queryBuilder.andInnerCondition(cname).or(customField).or(email).or(mobile);

        if (!StringUtils.isEmptyList(userEmployeeDOList)) {
            Condition sysIdsCon = new Condition(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), sysIds, ValueOp.IN);
            queryBuilder.or(sysIdsCon);
        }
        // sysuser_id
        Condition sysIdsCon = new Condition(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName(), sysIds, ValueOp.IN);
        queryBuilder.andInnerCondition(cname).or(customField).or(email).or(mobile).or(sysIdsCon);
        return queryBuilder;
    }


    /**
     * 获取列表number
     * 通过公司ID,查询认证员工和未认证员工数量
     *
     * @param keyWord   关键字
     * @param companyId 公司ID
     * @return
     */
    public UserEmployeeNumStatistic getListNum(String keyWord, Integer companyId) throws CommonException {
        UserEmployeeNumStatistic userEmployeeNumStatistic = new UserEmployeeNumStatistic();
        userEmployeeNumStatistic.setUnregcount(0);
        userEmployeeNumStatistic.setRegcount(0);
        if (companyId == 0) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        // 是否是子公司，如果是查询母公司ID
        HrCompanyDO hrCompanyDO = hrCompanyDao.getData(queryBuilder.buildQuery());

        if (hrCompanyDO == null) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        try {
            List<Integer> list = employeeEntity.getCompanyIds(hrCompanyDO.getParentId() > 0 ? hrCompanyDO.getParentId() : companyId);
            queryBuilder.clear();
            queryBuilder.select(new Select(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), SelectOp.COUNT))
                    .select(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName());
            Condition companyIdCon = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), list, ValueOp.IN);
            queryBuilder.where(companyIdCon).and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
            if (!StringUtils.isNullOrEmpty(keyWord)) {
                getQueryBuilder(queryBuilder, keyWord, companyId);
            }
            queryBuilder.groupBy(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName());
            // 查询
            List<Map<String, Object>> result = userEmployeeDao.getMaps(queryBuilder.buildQuery());
            if (!StringUtils.isEmptyList(result)) {
                for (Map<String, Object> map : result) {
                    if (map.get("activation") != null) {
                        if ((Byte) map.get("activation") == 0) {
                            userEmployeeNumStatistic.setRegcount((Integer) map.get("activation_count"));
                        } else if ((Byte) map.get("activation") == 1
                                || (Byte) map.get("activation") == 2
                                || (Byte) map.get("activation") == 3
                                || (Byte) map.get("activation") == 4) {
                            userEmployeeNumStatistic.setUnregcount(userEmployeeNumStatistic.getUnregcount() + (Integer) map.get("activation_count"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CommonException.PROGRAM_EXCEPTION;
        }
        return userEmployeeNumStatistic;
    }


    /**
     * 员工列表
     *
     * @param flag 是否设置总积分，0 :是，1:否
     */
    public List<UserEmployeeVO> employeeList(Query.QueryBuilder queryBuilder, Integer flag, List<Integer> companyIds, LinkedHashMap<Integer, Integer> employeeMap) {
        List<UserEmployeeVO> userEmployeeVOS = new ArrayList<>();
        List<UserEmployeeDO> userEmployeeDOS;
        Boolean reward = false;
        if (employeeMap != null && employeeMap.size() > 0) {
            reward = true;
        }
        // 员工数据
        userEmployeeDOS = userEmployeeDao.getDatas(queryBuilder.buildQuery());
        if (userEmployeeDOS != null && userEmployeeDOS.size() > 0) {
            Set<Integer> sysuserId = userEmployeeDOS.stream().filter(userUserDO -> userUserDO.getSysuserId() > 0)
                    .map(UserEmployeeDO::getSysuserId).collect(Collectors.toSet());
            queryBuilder.clear();
            queryBuilder.where(new Condition(UserUser.USER_USER.ID.getName(), sysuserId, ValueOp.IN));
            // 查询微信昵称
            List<UserUserDO> userUserDOList = userUserDao.getDatas(queryBuilder.buildQuery());
            Map<Integer, UserUserDO> userMap = userUserDOList.stream().collect(Collectors.toMap(UserUserDO::getId, Function.identity()));

            queryBuilder.clear();
            queryBuilder.where(new Condition(HrCompany.HR_COMPANY.ID.getName(), companyIds, ValueOp.IN));
            List<HrCompanyDO> companyList = hrCompanyDao.getDatas(queryBuilder.buildQuery());
            // 查询公司信息
            Map<Integer, HrCompanyDO> companyMap = companyList.stream().collect(Collectors.toMap(HrCompanyDO::getId, Function.identity()));
            if (reward) { // 需要排序
                for (Map.Entry<Integer, Integer> entry : employeeMap.entrySet()) {
                    for (UserEmployeeDO userEmployeeDO : userEmployeeDOS) {
                        if (entry.getKey().intValue() == userEmployeeDO.getId()) {
                            UserEmployeeVO userEmployeeVO = new UserEmployeeVO();
                            org.springframework.beans.BeanUtils.copyProperties(userEmployeeDO, userEmployeeVO);
                            userEmployeeVO.setUsername(userEmployeeDO.getCname());
                            // 微信昵称
                            if (userMap.size() > 0 && userMap.get(userEmployeeDO.getSysuserId()) != null) {
                                userEmployeeVO.setNickName(userMap.get(userEmployeeDO.getSysuserId()).getNickname());
                            } else {
                                userEmployeeVO.setNickName("未知");
                            }
                            // 公司名称
                            if (companyMap.size() > 0 && companyMap.get(userEmployeeDO.getCompanyId()) != null) {
                                HrCompanyDO hrCompanyDOTemp = companyMap.get(userEmployeeDO.getCompanyId());
                                userEmployeeVO.setCompanyName(hrCompanyDOTemp.getName() != null ? hrCompanyDOTemp.getName() : "");
                                userEmployeeVO.setCompanyAbbreviation(hrCompanyDOTemp.getAbbreviation() != null ? hrCompanyDOTemp.getAbbreviation() : "");
                            }
                            userEmployeeVO.setActivation((new Double(userEmployeeDO.getActivation())).intValue());
                            // 判断是否需要设置积分
                            if (flag.intValue() == 0) {
                                if (reward) {
                                    userEmployeeVO.setAward(employeeMap.get(userEmployeeDO.getId()));
                                } else {
                                    userEmployeeVO.setAward(userEmployeeDO.getAward());
                                }
                            } else if (flag.intValue() == 1) {
                                userEmployeeVO.setAward(0);
                            }
                            userEmployeeVOS.add(userEmployeeVO);
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                for (UserEmployeeDO userEmployeeDO : userEmployeeDOS) {
                    UserEmployeeVO userEmployeeVO = new UserEmployeeVO();
                    org.springframework.beans.BeanUtils.copyProperties(userEmployeeDO, userEmployeeVO);
                    userEmployeeVO.setUsername(userEmployeeDO.getCname());
                    // 判断是否需要设置积分
                    if (flag.intValue() == 0) {
                        userEmployeeVO.setAward(userEmployeeDO.getAward());
                    } else if (flag.intValue() == 1) {
                        userEmployeeVO.setAward(0);
                    }
                    // 微信昵称
                    if (userMap.size() > 0 && userMap.get(userEmployeeDO.getSysuserId()) != null) {
                        userEmployeeVO.setNickName(userMap.get(userEmployeeDO.getSysuserId()).getNickname());
                    } else {
                        userEmployeeVO.setNickName("未知");
                    }
                    // 公司名称
                    if (companyMap.size() > 0 && companyMap.get(userEmployeeDO.getCompanyId()) != null) {
                        HrCompanyDO hrCompanyDOTemp = companyMap.get(userEmployeeDO.getCompanyId());
                        userEmployeeVO.setCompanyName(hrCompanyDOTemp.getName() != null ? hrCompanyDOTemp.getName() : "");
                        userEmployeeVO.setCompanyAbbreviation(hrCompanyDOTemp.getAbbreviation() != null ? hrCompanyDOTemp.getAbbreviation() : "");
                    }
                    userEmployeeVO.setActivation((new Double(userEmployeeDO.getActivation())).intValue());
                    userEmployeeVOS.add(userEmployeeVO);
                }
            }
        } else {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        return userEmployeeVOS;
    }

    /**
     * 员工列表
     *
     * @param keyword    关键字搜索
     * @param companyId  公司ID
     * @param filter     过滤条件，0：全部，1：已认证，2：未认证,默认：0
     * @param order      排序条件
     * @param asc        正序，倒序 0: 正序,1:倒序 默认
     * @param pageNumber 第几页
     * @param timespan   月，季，年
     * @param pageSize   每页的条数
     */
    public UserEmployeeVOPageVO employeeList(String keyword, Integer companyId, Integer filter, String order, String asc, Integer pageNumber, Integer pageSize, String timespan) throws CommonException {
        UserEmployeeVOPageVO userEmployeeVOPageVO = new UserEmployeeVOPageVO();
        // 公司ID未设置
        if (companyId == 0) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        // 是否是子公司，如果是查询母公司ID
        HrCompanyDO hrCompanyDO = hrCompanyDao.getData(queryBuilder.buildQuery());
        if (StringUtils.isEmptyObject(hrCompanyDO)) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        List<Integer> list = employeeEntity.getCompanyIds(hrCompanyDO.getParentId() > 0 ? hrCompanyDO.getParentId() : companyId);
        queryBuilder.clear();
        Condition companyIdCon = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), list, ValueOp.IN);
        queryBuilder.where(companyIdCon).and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
        // 取公司ID
        List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
        // 如果有关键字，拼接关键字
        if (!StringUtils.isNullOrEmpty(keyword)) {
            getQueryBuilder(queryBuilder, keyword, companyId);
        }
        // 过滤条件
        if (filter != 0) {
            if (filter == 1) {
                queryBuilder.and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), 0);
            } else if (filter == 2) {
                List<Integer> filters = new ArrayList<>();
                filters.add(1);
                filters.add(2);
                filters.add(3);
                filters.add(4);
                queryBuilder.and(new Condition(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), filters, ValueOp.IN));
            }
        }
        // 排序条件
        if (!StringUtils.isNullOrEmpty(order)) {
            // 多个条件
            if (order.indexOf(",") > -1 && asc.indexOf(",") > -1) {
                String[] orders = order.split(",");
                String[] ascs = asc.split(",");
                // 排序条件设置错误
                if (orders.length != ascs.length) {
                    throw UserAccountException.ORDER_ERROR;
                }
                for (int i = 0; i < orders.length; i++) {
                    // 首先判断排序的条件是否正确
                    if (UserEmployee.USER_EMPLOYEE.field(orders[i]) != null) {
                        if (Integer.valueOf(ascs[i]).intValue() == 1) {   //倒序
                            queryBuilder.orderBy(UserEmployee.USER_EMPLOYEE.field(orders[i]).getName(), Order.DESC);
                        } else if (Integer.valueOf(ascs[i]).intValue() == 0) {// 正序
                            queryBuilder.orderBy(UserEmployee.USER_EMPLOYEE.field(orders[i]).getName(), Order.ASC);
                        }
                    }
                }
            } else {
                // 首先判断排序的条件是否正确
                if (UserEmployee.USER_EMPLOYEE.field(order) != null) {
                    if (Integer.valueOf(asc).intValue() == 0) {  // 正序
                        queryBuilder.orderBy(UserEmployee.USER_EMPLOYEE.field(order).getName(), Order.ASC);
                    } else if (Integer.valueOf(asc).intValue() == 1) { //倒序
                        queryBuilder.orderBy(UserEmployee.USER_EMPLOYEE.field(order).getName(), Order.DESC);
                    }
                }
            }

        }
        // 查询总条数
        int counts = userEmployeeDao.getCount(queryBuilder.buildQuery());
        if (counts == 0) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        // 分页数据
        if (pageNumber > 0 && pageSize > 0) {
            // 取的数据超过了分页数，取最最后一页数据
            if ((pageNumber * pageSize) > counts) {
                queryBuilder.setPageSize(pageSize);
                if ((counts % pageSize) == 0) {
                    pageNumber = counts / pageSize;
                } else {
                    pageNumber = counts / pageSize + 1;
                }
                queryBuilder.setPageNum(pageNumber);
            } else {
                queryBuilder.setPageNum(pageNumber);
                queryBuilder.setPageSize(pageSize);
            }
        }

        // 不管ES中有没有数据，员工的分页数据用于一样
        if (pageSize > 0) {
            userEmployeeVOPageVO.setPageSize(pageSize);
        }
        if (pageNumber > 0) {
            userEmployeeVOPageVO.setPageNumber(pageNumber);
        }
        userEmployeeVOPageVO.setTotalRow(counts);
        // 员工列表，不需要取排行榜
        if (StringUtils.isNullOrEmpty(timespan)) {
            logger.info("timespan:{}", timespan);
            userEmployeeVOPageVO.setData(employeeList(queryBuilder, 0, companyIds, null));
            return userEmployeeVOPageVO;
        }
        // 员工列表，从ES中获取积分月，季，年榜单数据
        Response response = null;
        try {
            response = searchengineServices.queryAwardRanking(companyIds, timespan, pageSize, pageNumber, keyword, filter);
        } catch (Exception e) {
            throw UserAccountException.SEARCH_ES_ERROR;
        }
        logger.info("ES date:{}", response.getData());
        logger.info("ES date:{}", response.getStatus());
        // ES取到数据
        if (response != null && response.getStatus() == 0) {
            logger.info("ES date:{}", response.getData());
            EmployeeRankObj rankObj = JSONObject.parseObject(response.getData(), EmployeeRankObj.class);
            List<EmployeeRank> employeeRankList = rankObj.getData();
            if (employeeRankList != null && employeeRankList.size() > 0) {
                logger.info("ES Data Size:{}", employeeRankList.size());
                // 封装查询条件
                LinkedHashMap<Integer, Integer> employeeMap = new LinkedHashMap();
                List<Integer> employeeIds = new ArrayList<>();
                for (EmployeeRank employeeRank : employeeRankList) {
                    employeeIds.add(employeeRank.getEmployeeId());
                    employeeMap.put(employeeRank.getEmployeeId(), employeeRank.getAward());
                }
                queryBuilder.clear();
                queryBuilder.where(new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), employeeIds, ValueOp.IN));
                userEmployeeVOPageVO.setData(employeeList(queryBuilder, 0, companyIds, employeeMap));
            } else {
                // ES没取到数据，从数据库中取数据，但是不设置任何积分
                logger.info("ES Data is empty!!!!");
                userEmployeeVOPageVO.setData(employeeList(queryBuilder, 1, companyIds, null));
            }
        } else {
            throw UserAccountException.SEARCH_ES_ERROR;
        }
        return userEmployeeVOPageVO;
    }

    /**
     * 员工信息导出
     *
     * @param userEmployees 员工ID
     * @param companyId     公司ID
     * @param type          1:导出所有，0:按照userEmployees导出
     * @return
     */
    public List<UserEmployeeVO> employeeExport(List<Integer> userEmployees, Integer companyId, Integer type) throws CommonException {
        List<UserEmployeeVO> userEmployeeVOS = new ArrayList<>();
        if (companyId == 0) {
            throw UserAccountException.COMPANYID_ENPTY;
        }

        if (userEmployees == null && type.intValue() == 0) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();

        if (type.intValue() == 1) {  // 导出所有，取该公司下所有的员工ID
            List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
            queryBuilder.where(new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN))
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
        } else {
            queryBuilder.where(new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), userEmployees, ValueOp.IN))
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
            // 查询是否有权限修改
            if (!employeeEntity.permissionJudge(userEmployees, companyId)) {
                throw UserAccountException.PERMISSION_DENIED;
            }
        }
        // 员工列表
        List<UserEmployeeDO> list = userEmployeeDao.getDatas(queryBuilder.buildQuery());
        if (StringUtils.isEmptyList(list)) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        Set<Integer> sysuserId = list.stream().filter(userUserDO -> userUserDO.getSysuserId() > 0)
                .map(UserEmployeeDO::getSysuserId).collect(Collectors.toSet());
        queryBuilder.clear();
        queryBuilder.where(new Condition(UserUser.USER_USER.ID.getName(), sysuserId, ValueOp.IN));
        // 查询微信昵称
        List<UserUserDO> userUserDOList = userUserDao.getDatas(queryBuilder.buildQuery());
        Map<Integer, UserUserDO> userMap = userUserDOList.stream().collect(Collectors.toMap(UserUserDO::getId, Function.identity()));
        // 公司ID列表
        Set<Integer> companyIds = list.stream().filter(userEmployeeDO -> userEmployeeDO.getCompanyId() > 0)
                .map(userEmployeeDO -> userEmployeeDO.getCompanyId()).collect(Collectors.toSet());
        queryBuilder.clear();
        queryBuilder.where(new Condition(HrCompany.HR_COMPANY.ID.getName(), companyIds, ValueOp.IN));
        List<HrCompanyDO> companyList = hrCompanyDao.getDatas(queryBuilder.buildQuery());
        // 查询公司信息
        Map<Integer, HrCompanyDO> companyMap = companyList.stream().collect(Collectors.toMap(HrCompanyDO::getId, Function.identity()));
        for (UserEmployeeDO userEmployeeDO : list) {
            UserEmployeeVO userEmployeeVO = new UserEmployeeVO();
            userEmployeeVO.setId(userEmployeeDO.getId());
            userEmployeeVO.setUsername(userEmployeeDO.getCname());
            userEmployeeVO.setMobile(userEmployeeDO.getMobile());
            userEmployeeVO.setCustomField(userEmployeeDO.getCustomField());
            userEmployeeVO.setEmail(userEmployeeDO.getEmail());
            userEmployeeVO.setCompanyId(userEmployeeDO.getCompanyId());
            if (userMap != null && userMap.size() > 0 && userMap.get(userEmployeeDO.getSysuserId()) != null) {
                userEmployeeVO.setNickName(userMap.get(userEmployeeDO.getSysuserId()).getNickname());
            } else {
                userEmployeeVO.setNickName("未知");
            }
            // 公司名称
            if (companyMap != null && companyMap.size() > 0 && companyMap.get(userEmployeeDO.getCompanyId()) != null) {
                HrCompanyDO companyDO = companyMap.get(userEmployeeDO.getCompanyId());
                userEmployeeVO.setCompanyName(companyDO.getName() != null ? companyDO.getName() : "");
                userEmployeeVO.setCompanyAbbreviation(companyDO.getAbbreviation() != null ? companyDO.getAbbreviation() : "");
            }
            userEmployeeVO.setActivation((new Double(userEmployeeDO.getActivation())).intValue());
            userEmployeeVO.setAward(userEmployeeDO.getAward());
            userEmployeeVO.setBindingTime(userEmployeeDO.getBindingTime());
            userEmployeeVOS.add(userEmployeeVO);
        }
        return userEmployeeVOS;
    }


    /**
     * 员工信息导入
     *
     * @param userEmployeeMap 员工信息列表
     * @param companyId       公司ID
     */
    @Transactional
    public Response employeeImport(Integer companyId, Map<Integer, UserEmployeeDO> userEmployeeMap, String filePath, String
            fileName, Integer type, Integer hraccountId) throws CommonException {
        Response response = new Response();
        logger.info("开始导入员工信息");
        // 判断是否有重复数据
        ImportUserEmployeeStatistic importUserEmployeeStatistic = repetitionFilter(userEmployeeMap, companyId);
        if (importUserEmployeeStatistic != null && !importUserEmployeeStatistic.insertAccept) {
            throw UserAccountException.IMPORT_DATA_WRONG;
        }
        // 通过手机号查询那些员工数据是更新，那些数据是新增
        List<String> moblies = new ArrayList<>();
        List<UserEmployeeDO> userEmployeeList = new ArrayList<>();
        userEmployeeMap.forEach((k, v) -> {
            userEmployeeList.add(v);
            moblies.add(v.getMobile());
        });
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.MOBILE.getName(), moblies, ValueOp.IN);
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyId).and(condition);
        // 数据库中已经有的员工列表
        List<UserEmployeeDO> userEmployeeDOS = userEmployeeDao.getDatas(queryBuilder.buildQuery());
        List<UserEmployeeDO> updateUserEmployee = new ArrayList<>();
        if (!StringUtils.isEmptyList(userEmployeeDOS)) {
            // 查询出需要更新的数据
            for (UserEmployeeDO userEmployeeDOTemp : userEmployeeList) {
                for (UserEmployeeDO user : userEmployeeDOS) {
                    if (userEmployeeDOTemp.getMobile().equals(user.getMobile())) {
                        userEmployeeDOTemp.setId(user.getId());
                        userEmployeeDOTemp.setSource(8);
                        updateUserEmployee.add(userEmployeeDOTemp);
                    }
                }
            }
            if (!StringUtils.isEmptyList(updateUserEmployee)) {
                // 更新数据
                userEmployeeDao.updateDatas(updateUserEmployee);
                searchengineEntity.updateEmployeeAwards(updateUserEmployee.stream().filter(f -> f.getId() > 0).map(m -> m.getId()).collect(Collectors.toList()));
                // 去掉需要更新的数据
                userEmployeeList.removeAll(updateUserEmployee);
            }
        }
        // 新增数据
        if (!StringUtils.isEmptyList(userEmployeeList)) {
            employeeEntity.addEmployeeList(userEmployeeList);
        }
        // 员工导入信息日志
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("导入的数据类型", type, "不能为空", null, 0, 100);
        vu.addIntTypeValidate("HR账号", hraccountId, "不能为空", null, 1, 1000000);
        vu.addStringLengthValidate("导入文件的绝对路径", filePath, null, null, 0, 257);
        vu.addStringLengthValidate("导入的文件名", fileName, null, null, 0, 257);

        String errorMessage = vu.validate();
        if (!StringUtils.isNullOrEmpty(errorMessage)) {
            throw UserAccountException.ADD_IMPORTERMONITOR_PARAMETER.setMess(errorMessage);
        }
        try {
            HrImporterMonitorDO hrImporterMonitorDO = new HrImporterMonitorDO();
            hrImporterMonitorDO.setSys(2);
            hrImporterMonitorDO.setFile(filePath);
            hrImporterMonitorDO.setCompanyId(companyId);
            hrImporterMonitorDO.setName(fileName);
            hrImporterMonitorDO.setStatus(2);
            hrImporterMonitorDO.setType(type);
            hrImporterMonitorDO.setMessage("导入成功");
            hrImporterMonitorDO.setHraccountId(hraccountId);
            hrImporterMonitorDao.addData(hrImporterMonitorDO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CommonException.PROGRAM_EXCEPTION;
        }
        response = ResultMessage.SUCCESS.toResponse();
        logger.info("导入员工信息结束");
        return response;
    }


    /**
     * 检查员工重复(批量导入之前验证)
     *
     * @param userEmployeeMap
     * @param companyId
     * @return
     */

    public ImportUserEmployeeStatistic checkBatchInsert(Map<Integer, UserEmployeeDO> userEmployeeMap, Integer companyId) throws CommonException {
        return repetitionFilter(userEmployeeMap, companyId);
    }

    /**
     * 查询是否有重复数据
     *
     * @param userEmployeeMap
     * @param companyId
     */
    public ImportUserEmployeeStatistic repetitionFilter(Map<Integer, UserEmployeeDO> userEmployeeMap, Integer companyId) throws CommonException {
        if (companyId == 0) {
            throw UserAccountException.COMPANYID_ENPTY;
        }
        if (userEmployeeMap.size() == 0) {
            throw UserAccountException.IMPORT_DATA_EMPTY;
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        HrCompanyDO company = hrCompanyDao.getData(queryBuilder.buildQuery());
        // 公司ID设置错误
        if (company == null) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        ImportUserEmployeeStatistic importUserEmployeeStatistic = new ImportUserEmployeeStatistic();
        // 查找已经存在的数据
        queryBuilder.clear();
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyId)
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
        // 数据库中取出来的数据
        List<UserEmployeeDO> dbEmployeeDOList = userEmployeeDao.getDatas(queryBuilder.buildQuery());
        // 重复的对象
        List<ImportErrorUserEmployee> importErrorUserEmployees = new ArrayList<>();
        int repetitionCounts = 0;
        int errorCounts = 0;
        // 提交上的数据
        for (Map.Entry<Integer, UserEmployeeDO> entry : userEmployeeMap.entrySet()) {
            UserEmployeeDO userEmployeeDO = entry.getValue();
            ImportErrorUserEmployee importErrorUserEmployee = new ImportErrorUserEmployee();
            // 姓名不能为空
            if (StringUtils.isNullOrEmpty(userEmployeeDO.getCname())) {
                importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                importErrorUserEmployee.setMessage("cname不能为空");
                errorCounts = errorCounts + 1;
                importErrorUserEmployee.setRowNum(entry.getKey());
                importErrorUserEmployees.add(importErrorUserEmployee);
                continue;
            }
            if (userEmployeeDO.getCompanyId() == 0) {
                userEmployeeDO.setCompanyId(companyId);
            }
            if (StringUtils.isNullOrEmpty(userEmployeeDO.getCustomField())) {
                continue;
            }
            if (!StringUtils.isEmptyList(dbEmployeeDOList)) {
                // 数据库的数据
                for (UserEmployeeDO dbUserEmployeeDO : dbEmployeeDOList) {
                    // 非自定义员工,忽略检查
                    if (StringUtils.isNullOrEmpty(dbUserEmployeeDO.getCustomField())
                            || StringUtils.isNullOrEmpty(dbUserEmployeeDO.getCname())) {
                        continue;
                    }
                    // 当提交的数据和数据库中的数据，cname和customField都相等时候，认为是重复数据
                    if (userEmployeeDO.getCname().equals(dbUserEmployeeDO.getCname())
                            && userEmployeeDO.getCustomField().equals(dbUserEmployeeDO.getCustomField())) {
                        repetitionCounts = repetitionCounts + 1;
                        importErrorUserEmployee.setUserEmployeeDO(userEmployeeDO);
                        importErrorUserEmployee.setRowNum(entry.getKey());
                        importErrorUserEmployee.setMessage("cname和customField和数据库的数据一致");
                        importErrorUserEmployees.add(importErrorUserEmployee);
                    }
                }
            }
        }
        importUserEmployeeStatistic.setTotalCounts(userEmployeeMap.size());
        importUserEmployeeStatistic.setErrorCounts(errorCounts);
        importUserEmployeeStatistic.setRepetitionCounts(repetitionCounts);
        importUserEmployeeStatistic.setUserEmployeeDO(importErrorUserEmployees);
        if (repetitionCounts == 0 && errorCounts == 0) {
            importUserEmployeeStatistic.setInsertAccept(true);
        } else {
            importUserEmployeeStatistic.setInsertAccept(false);
        }
        return importUserEmployeeStatistic;
    }


    /**
     * 员工信息
     *
     * @param userEmployeeId 员工ID
     * @param companyId      公司id
     */
    public UserEmployeeDetailVO userEmployeeDetail(Integer userEmployeeId, Integer companyId) throws
            CommonException {
        UserEmployeeDetailVO userEmployeeDetailVO = new UserEmployeeDetailVO();
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.ID.getName(), userEmployeeId)
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
        // 员工基本信息
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getData(queryBuilder.buildQuery());

        if (userEmployeeDO == null) {
            throw UserAccountException.USEREMPLOYEES_WRONG;
        }
        queryBuilder.clear();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        HrCompanyDO company = hrCompanyDao.getData(queryBuilder.buildQuery());
        // 公司ID设置错误
        if (company == null) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }
        // 查询是否有权限修改
        if (!employeeEntity.permissionJudge(userEmployeeId, companyId)) {
            throw UserAccountException.PERMISSION_DENIED;
        }
        userEmployeeDetailVO.setId(userEmployeeDO.getId());
        userEmployeeDetailVO.setUsername(userEmployeeDO.getCname());
        userEmployeeDetailVO.setCompanyId(userEmployeeDO.getCompanyId());
        userEmployeeDetailVO.setMobile(userEmployeeDO.getMobile());
        userEmployeeDetailVO.setCustomField(userEmployeeDO.getCustomField());
        userEmployeeDetailVO.setEmail(userEmployeeDO.getEmail());
        userEmployeeDetailVO.setAward(userEmployeeDO.getAward());
        userEmployeeDetailVO.setBindingTime(userEmployeeDO.getBindingTime());
        userEmployeeDetailVO.setActivation((new Double(userEmployeeDO.getActivation())).intValue());
        // 查询微信信息
        if (userEmployeeDO.getSysuserId() > 0) {
            queryBuilder.clear();
            queryBuilder.where(UserUser.USER_USER.ID.getName(), userEmployeeDO.getSysuserId());
            UserUserDO userUserDO = userUserDao.getData(queryBuilder.buildQuery());
            if (userUserDO != null) {
                userEmployeeDetailVO.setNickName(userUserDO.getNickname());
                userEmployeeDetailVO.setHeadImg(userUserDO.getHeadimg());
            }
        }
        // 查询公司信息
        if (userEmployeeDO.getCompanyId() > 0) {
            queryBuilder.clear();
            queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), userEmployeeDO.getCompanyId());
            HrCompanyDO hrCompanyDO = hrCompanyDao.getData(queryBuilder.buildQuery());
            if (hrCompanyDO != null) {
                userEmployeeDetailVO.setCompanyName(hrCompanyDO.getName() != null ? hrCompanyDO.getName() : "");
                userEmployeeDetailVO.setCompanyAbbreviation(hrCompanyDO.getAbbreviation() != null ? hrCompanyDO.getAbbreviation() : "");

            }
        }

        return userEmployeeDetailVO;
    }

    /**
     * 编辑公司员工信息
     *
     * @param cname          姓名
     * @param mobile         手机号
     * @param email          邮箱
     * @param customField    自定义字段
     * @param userEmployeeId user_employee.id
     * @param companyId      公司ID
     * @return
     * @throws Exception
     */
    public Response updateUserEmployee(String cname, String mobile, String email, String
            customField, Integer userEmployeeId, Integer companyId) throws CommonException {
        Response response = new Response();
        if (userEmployeeId == 0) {
            throw UserAccountException.USEREMPLOYEES_DATE_EMPTY;
        }
        if (companyId == 0) {
            throw UserAccountException.COMPANYID_ENPTY;
        }
        // 查询是否有权限修改
        if (!employeeEntity.permissionJudge(userEmployeeId, companyId)) {
            throw UserAccountException.PERMISSION_DENIED;
        }
        // 判断邮箱是否重复,公司重复检查
        if (!StringUtils.isNullOrEmpty(email)) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(UserEmployee.USER_EMPLOYEE.EMAIL.getName(), email)
                    .and(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyId)
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), 0)
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0)
                    .and(new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), userEmployeeId, ValueOp.NEQ));
            UserEmployeeDO userEmployeeDO = userEmployeeDao.getData(queryBuilder.buildQuery());

            if (userEmployeeDO != null) {
                throw UserAccountException.EMAIL_REPETITION;
            }
        }
        // 判断自定义字段是否重复
        if (!StringUtils.isNullOrEmpty(cname) && !StringUtils.isNullOrEmpty(customField)) {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(UserEmployee.USER_EMPLOYEE.CNAME.getName(), cname)
                    .and(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.getName(), customField)
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0)
                    .and(new Condition(UserEmployee.USER_EMPLOYEE.ID.getName(), userEmployeeId, ValueOp.NEQ));
            UserEmployeeDO userEmployeeDO = userEmployeeDao.getData(queryBuilder.buildQuery());

            if (userEmployeeDO != null) {
                throw UserAccountException.CUSTOM_FIELD_REPETITION;
            }
        }

        try {
            UserEmployeeDO userEmployeeDO = new UserEmployeeDO();
            if (!StringUtils.isNullOrEmpty(cname)) {
                userEmployeeDO.setCname(cname);
            }
            if (!StringUtils.isNullOrEmpty(mobile)) {
                userEmployeeDO.setMobile(mobile);
            }
            if (!StringUtils.isNullOrEmpty(customField)) {
                userEmployeeDO.setCustomField(customField);
            }
            if (!StringUtils.isNullOrEmpty(email)) {
                userEmployeeDO.setEmail(email);
            }
            userEmployeeDO.setId(userEmployeeId);
            int i = userEmployeeDao.updateData(userEmployeeDO);
            if (i > 0) {
                response = ResultMessage.SUCCESS.toResponse();
            } else {
                response = ResultMessage.PROGRAM_EXCEPTION.toResponse();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CommonException.PROGRAM_EXCEPTION;
        }
        return response;
    }

    /**
     * 查找员工积分列表
     *
     * @param employeeId 员工编号
     * @param companyId 公司编号
     * @param pageNumber 分页信息之页码
     * @param pageSize 分页信息之每页信息数量
     * @return 员工分页信息
     * @throws CommonException
     */
    public RewardVOPageVO getEmployeeRewards(int employeeId, int companyId, int pageNumber, int pageSize) throws CommonException {
        RewardVOPageVO rewardVOPageVO = employeeEntity.getEmployeePointsRecords(employeeId, pageNumber, pageSize);

        /**
         * 查询公司下候选人信息，如果候选人不存在则将berecomID 置为0，用以通知前端不需要拼接潜在候选人的url链接。
         */
        if (rewardVOPageVO.getData() != null && rewardVOPageVO.getData().size() > 0) {
            List<Integer> beRecomIDList = rewardVOPageVO.getData().stream().filter(m -> m.getBerecomId() != 0)
                    .map(m -> m.getBerecomId()).collect(Collectors.toList());
            if (beRecomIDList != null && beRecomIDList.size() > 0) {
                List<CandidateCompanyDO> candidateCompanyDOList = candidateCommonEntity.getCandidateCompanyByCompanyID(companyId);
                if (candidateCompanyDOList != null && candidateCompanyDOList.size() > 0) {
                    Map<Integer, CandidateCompanyDO> userUserDOSMap =
                            candidateCompanyDOList.stream().collect(Collectors.toMap(CandidateCompanyDO::getId,
                                    Function.identity()));
                    for (RewardVO rewardVO : rewardVOPageVO.getData()) {
                        if (userUserDOSMap.get(rewardVO.getBerecomId()) == null) {
                            rewardVO.setBerecomId(0);
                        }
                    }
                }
            }
        }

        return rewardVOPageVO;
    }
}
