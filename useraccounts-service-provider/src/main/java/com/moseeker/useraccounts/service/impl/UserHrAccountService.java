package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.moseeker.baseorm.config.HRAccountActivationType;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.constant.EmployeeAuthMethod;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.employeedb.EmployeeCustomOptionJooqDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.referraldb.ReferralEmployeeRegisterLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.employeedb.tables.pojos.EmployeeOptionValue;
import com.moseeker.baseorm.db.hrdb.tables.HrAccountApplicationNotify;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeRegisterLog;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.baseorm.util.SmsSender;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.CompanyConf;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.HREntity;
import com.moseeker.entity.PositionEntity;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.entity.exception.HRException;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.employee.struct.BonusVO;
import com.moseeker.thrift.gen.employee.struct.BonusVOPageVO;
import com.moseeker.thrift.gen.employee.struct.RewardVO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.constant.HRAccountStatus;
import com.moseeker.useraccounts.constant.OptionType;
import com.moseeker.useraccounts.constant.ResultMessage;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.pojo.EmployeeList;
import com.moseeker.useraccounts.pojo.EmployeeRank;
import com.moseeker.useraccounts.pojo.EmployeeRankObj;
import com.moseeker.useraccounts.service.impl.employee.BatchValidate;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.moseeker.common.constants.Constant.FIVE_THOUSAND;
import static com.moseeker.useraccounts.exception.UserAccountException.HR_UPDATEMOBILE_FAILED;
import static com.moseeker.useraccounts.exception.UserAccountException.ILLEGAL_MOBILE;

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

    CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);

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
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private UserUserDao userUserDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private Environment env;

    @Autowired
    private HrImporterMonitorDao hrImporterMonitorDao;

    @Autowired
    private SearchengineEntity searchengineEntity;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    CandidateCompanyDao candidateCompanyDao;

    @Autowired
    HREntity hrEntity;

    @Autowired
    HrSuperaccountApplyDao accountLimitDao;

    @Autowired
    HrCompanyAccountDao companyAccountDao;

    @Autowired
    JobPositionDao positionDao;

    @Autowired
    private PositionEntity positionEntity;

    @Autowired
    private HrAppExportFieldsDao exportFieldsDao;

    @Autowired
    private HrAppCvConfDao appCvConfDao;

    @Autowired
    private HrWxHrChatListDao chatListDao;

    @Autowired
    private HrAccountApplicationNotifyDao hrAccountApplicationNotifyDao;

    @Autowired
    private ReferralEmployeeRegisterLogDao referralEmployeeRegisterLogDao;

    @Autowired
    protected HrEmployeeCustomFieldsDao customFieldsDao;

    @Autowired
    protected EmployeeCustomOptionJooqDao customOptionJooqDao;

    @Autowired
    BatchValidate batchValidate;
    /**
     * 修改手机号码
     *
     * @param mobile 手机号码
     * @param hrID   HR编号
     * @throws CommonException
     */
    public void updateMobile(int hrID, String mobile) throws CommonException {
        if (!FormCheck.isMobile(mobile)) {
            throw ILLEGAL_MOBILE;
        }

        boolean result = hrEntity.updateMobile(hrID, mobile);
        if (!result) {
            throw HR_UPDATEMOBILE_FAILED;
        }
    }

    /**
     * 添加子账号
     * @param hrAccountDO 子账号
     * @return 子账号编号
     * @throws CommonException 90015 参数不存在(公司信息不正确;参数校验不通过) 42022 不允许添加子账号(子账号达到上线) 42023 HRAccount已经存在
     */
    public int addSubAccount(UserHrAccountDO hrAccountDO) throws CommonException {

        ValidateUtil vu = new ValidateUtil();
        vu.addSensitiveValidate("用户名称", hrAccountDO.getUsername(), null, null);
        vu.addStringLengthValidate("用户名称", hrAccountDO.getUsername(), null, null, 0, 60);
        vu.addRequiredValidate("手机号码", hrAccountDO.getMobile(), null, null);
        vu.addStringLengthValidate("手机号码", hrAccountDO.getMobile(), null, null, 0, 30);
        vu.addStringLengthValidate("邮箱", hrAccountDO.getEmail(), null, null, 0, 50);
        vu.addStringLengthValidate("密码", hrAccountDO.getPassword(), null, null, 0, 64);
        vu.addIntTypeValidate("来源", hrAccountDO.getSource(), null, null, 0, 99);
        vu.addStringLengthValidate("注册IP", hrAccountDO.getRegisterIp(), null, null, 0, 50);
        vu.addRequiredValidate("公司", hrAccountDO.getCompanyId(), null, null);
        vu.addIntTypeValidate("公司", hrAccountDO.getCompanyId(), null, null, 0, Integer.MAX_VALUE);
        vu.addStringLengthValidate("头像", hrAccountDO.getHeadimgurl(), null, null, 0, 120);

        String message = vu.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(message)) {
            throw CommonException.PROGRAM_PARAM_NOTEXIST.setMess(message);
        }

        /** 持久化数据校验 */
        HrCompanyDO companyDO = hrCompanyDao.getCompanyById(hrAccountDO.getCompanyId());
        if (companyDO == null) {
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        HrCompanyDO parentCompanyDO = companyDO;
        if (companyDO.getParentId() != 0) {
            parentCompanyDO = hrCompanyDao.getCompanyById(companyDO.getParentId());
            if (parentCompanyDO == null) {
                throw CommonException.PROGRAM_PARAM_NOTEXIST;
            }
        }

        /** 查看是否能够继续添加子账号 */
        boolean allowedAddHRSubAccount = allowAddSubAccount(parentCompanyDO.getId());
        if (!allowedAddHRSubAccount) {
            throw UserAccountException.NOT_ALLOWED_ADD_SUBACCOUNT;
        }

        UserHrAccountRecord userHrAccountRecord = BeanUtils.structToDB(hrAccountDO, UserHrAccountRecord.class);

        /** 初始化账号信息 */
        userHrAccountRecord.setLoginCount(0);
        userHrAccountRecord.setAccountType(HRAccountType.SubAccount.getType());
        if (userHrAccountRecord.getDisable() == null) {
            userHrAccountRecord.setDisable(HRAccountStatus.Enabled.getStatus());
        }
        if (userHrAccountRecord.getActivation() == null) {
            userHrAccountRecord.setActivation((byte)HRAccountActivationType.Actived.getValue());
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        userHrAccountRecord.setCreateTime(now);
        userHrAccountRecord.setUpdateTime(now);
        userHrAccountRecord.setLastLoginTime(now);
        userHrAccountRecord.setDownloadToken(null);
        userHrAccountRecord.setLastLoginIp(null);
        if (userHrAccountRecord.getPassword() == null) {
            userHrAccountRecord.setPassword("");
        }

        int id = userHrAccountDao.addIfNotExist(userHrAccountRecord);
        if (id > 0) {
            HrCompanyAccountDO companyAccount = new HrCompanyAccountDO();
            companyAccount.setAccountId(id);
            companyAccount.setCompanyId(hrAccountDO.getCompanyId());
            companyAccountDao.addData(companyAccount);
        } else {
            throw UserAccountException.HRACCOUNT_EXIST;
        }

        return id;
    }

    /**
     * 是否可以添加子帐号
     */
    public boolean ifAddSubAccountAllowed(int hrId) throws CommonException {

        UserHrAccountDO hrAccountDO = requiresNotNullAccount(hrId);

        return allowAddSubAccount(hrAccountDO.getCompanyId());

    }

    /**
     * 是否可以继续添加子账号
     * @param companyId 公司编号
     * @return
     */
    private boolean allowAddSubAccount(int companyId) {
        Query query = new Query.QueryBuilder()
                .where(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.COMPANY_ID.getName(), companyId)
                .buildQuery();
        HrSuperaccountApplyDO superaccountApplyDO = accountLimitDao.getData(query);
        if (superaccountApplyDO == null) {
            return false;
        }
        com.moseeker.baseorm.db.userdb.tables.UserHrAccount hrTable = com.moseeker.baseorm.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT;
        query = new Query.QueryBuilder()
                .where(hrTable.COMPANY_ID.getName(), companyId)
                .and(hrTable.ACCOUNT_TYPE.getName(), 1)
                .and(hrTable.DISABLE.getName(), 1)
                .and(hrTable.ACTIVATION.getName(), 1)
                .buildQuery();
        return superaccountApplyDO.getAccountLimit() > userHrAccountDao.getCount(query);
    }

    /**
     * 添加子帐号
     */
    @Transactional
    public UserHrAccountDO addAccount(UserHrAccountDO userHrAccountDO) throws CommonException {

        if (userHrAccountDO == null || StringUtils.isNullOrEmpty(userHrAccountDO.getMobile()) || userHrAccountDO.getCompanyId() < 1) {
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }

        com.moseeker.baseorm.db.userdb.tables.UserHrAccount accoutTable = com.moseeker.baseorm.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT;
        Query query = new Query.QueryBuilder()
                .where(accoutTable.MOBILE.getName(), userHrAccountDO.getMobile())
                .and(accoutTable.DISABLE.getName(), AbleFlag.ENABLE.getValue())
                .buildQuery();
        UserHrAccountDO existAccount = userHrAccountDao.getData(query);

        if (existAccount != null) {
            throw HRException.MOBILE_EXIST;
        }

        try {
            userHrAccountDO = userHrAccountDao.addData(userHrAccountDO);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            throw HRException.USER_EXIST;
        }

        HrCompanyAccountDO companyAccount = new HrCompanyAccountDO();
        companyAccount.setAccountId(userHrAccountDO.getId());
        companyAccount.setCompanyId(userHrAccountDO.getCompanyId());
        companyAccountDao.addData(companyAccount);
        query = new Query.QueryBuilder().where(com.moseeker.baseorm.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT.ID.getName(),userHrAccountDO.getId()).buildQuery();
        return userHrAccountDao.getData(query);
    }

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
            UserHrAccountDO accountDO = userHrAccountDao.getUserHrAccountById((int)userHrAccount.getId());
            if(checkNameMobify(accountDO, userHrAccount)){
                chatListDao.updateWelcomeStatusByHrAccountId(userHrAccountRecord.getId());
            }
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
    private boolean checkNameMobify(UserHrAccountDO oldAccount, UserHrAccount newAccount){
        String username = newAccount.getUsername();
        if(!"".equals(oldAccount.getUsername()) && !newAccount.isSetUsername()){
            return false;
        }else if(newAccount.isSetUsername() && !oldAccount.getUsername().equals(username.trim())){
            return true;
        }
        UserWxUserDO oldWxUser = userWxUserDao.getWXUserById(oldAccount.getWxuserId());
        UserWxUserDO newWxUser = userWxUserDao.getWXUserById((int)newAccount.getWxuser_id());
        if(oldWxUser != null && !"".equals(oldWxUser.getNickname()) && (newWxUser == null || "".equals(newWxUser.getNickname()))){
            return false;
        }else if(newWxUser != null && !"".equals(newWxUser.getNickname()) && (oldWxUser == null || !oldWxUser.getNickname().equals(newWxUser.getNickname()))){
            return true;
        }
        String oldMobile = oldAccount.getMobile();
        oldMobile = StringUtils.isNullOrEmpty(oldMobile) && oldMobile.length()==11
                ? "" : oldMobile.substring(0,3)+"****"+oldMobile.substring(7);
        String newMobile = newAccount.getMobile();
        newMobile = StringUtils.isNullOrEmpty(oldMobile) && oldMobile.length()==11
                ? "" : oldMobile.substring(0,3)+"****"+oldMobile.substring(7);
        if(oldMobile.equals(newMobile)){
            return false;
        }
        return true;
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
        List<Map<String, Object>> list;
        try {
            list = hrSearchConditionDao.getMaps(query.buildQuery());
            logger.info("UserHrAccountService - getSearchCondition  result:{}", list);
            if(StringUtils.isEmptyList(list)){
                return ResponseUtils.success("");
            }
            return ResponseUtils.success(list);
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
            boolean positionStatusFlag=searchCondition.isSetPosition_status();
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
                return ResponseUtils.fail("{'status':42004,'message':'保存失败，该筛选项名称已存在'}");
            }

            HrSearchConditionRecord record=BeanUtils.structToDB(searchCondition, HrSearchConditionRecord.class);

            if(record.getIsPublic()==0){
                record.setIsPublic(null);
            }
            if(record.getIsRecommend()==0){
                record.setIsRecommend(null);
            }

            if(!positionStatusFlag){
                record.setPositionStatus(-1);
            }


            int primaryKey = hrSearchConditionDao.addRecord(record).getId();
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
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("%");
        stringBuffer.append(keyWord);
        stringBuffer.append("%");
        // 名字
        Condition cname = new Condition(UserEmployee.USER_EMPLOYEE.CNAME.getName(), stringBuffer.toString(), ValueOp.LIKE);
       /* // 自定义字段
        Condition customField = new Condition(UserEmployee.USER_EMPLOYEE.CUSTOM_FIELD.getName(), stringBuffer.toString(), ValueOp.LIKE);
        // 邮箱
        Condition email = new Condition(UserEmployee.USER_EMPLOYEE.EMAIL.getName(), stringBuffer.toString(), ValueOp.LIKE);
        // 手机号码
        Condition mobile = new Condition(UserEmployee.USER_EMPLOYEE.MOBILE.getName(), stringBuffer.toString(), ValueOp.LIKE);*/

        queryBuilder.andInnerCondition(cname);
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
            int cancelCount=0;
            if (!StringUtils.isEmptyList(result)) {
                logger.info("=======================");
                logger.info(JSON.toJSONString(result));
                logger.info("=======================");
                for (Map<String, Object> map : result) {
                    if (map.get("activation") != null) {
                        if ((Byte) map.get("activation") == 0) {
                            logger.info("getListNum regcount:{}", cancelCount);
                            userEmployeeNumStatistic.setRegcount((Integer) map.get("activation_count"));
                        } else if ((Byte) map.get("activation") == 1
                                || (Byte) map.get("activation") == 2
                                || (Byte) map.get("activation") == 4
                                || (Byte) map.get("activation") == 5) {
                            cancelCount+=(Integer) map.get("activation_count");
                        } else if ((Byte) map.get("activation") == 3) {
                            logger.info("getListNum unregcount:{}", map.get("activation_count"));
                            userEmployeeNumStatistic.setUnregcount((Integer) map.get("activation_count"));
                        }
                    }
                }
                logger.info("getListNum cancelCount:{}", cancelCount);
                userEmployeeNumStatistic.setCancelcount(cancelCount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CommonException.PROGRAM_EXCEPTION;
        }
        logger.info("getListNum userEmployeeNumStatistic:{}", JSON.toJSONString(userEmployeeNumStatistic));
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
        Query query = queryBuilder.buildQuery();
        logger.info("UserHrAccountService employeeList query:{}", query);
        // 员工数据
        userEmployeeDOS = userEmployeeDao.getDatas(queryBuilder.buildQuery());
        logger.info("UserHrAccountService employeeList userEmployeeDOS:{}", JSONArray.toJSONString(userEmployeeDOS));

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
                            List customFieldValues = new ArrayList();
                            if (userEmployeeDO.getCustomFieldValues() != null) {
                                customFieldValues.addAll(JSONObject.parseObject(userEmployeeDO.getCustomFieldValues(), List.class));
                            }
                            userEmployeeVO.setCustomFieldValues(customFieldValues);
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
                            userEmployeeVO.setBonus(new BigDecimal(userEmployeeDO.getBonus()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toPlainString().replace(".00",""));
                            userEmployeeVO.setAuthMethod(userEmployeeDO.getAuthMethod());
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
                    List customFieldValues = new ArrayList();
                    if (userEmployeeDO.getCustomFieldValues() != null) {
                        customFieldValues.addAll(JSONObject.parseObject(userEmployeeDO.getCustomFieldValues(), List.class));
                    }
                    userEmployeeVO.setCustomFieldValues(customFieldValues);
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
                    userEmployeeVO.setBonus(new BigDecimal(userEmployeeDO.getBonus()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toPlainString().replace(".00",""));
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
    public UserEmployeeVOPageVO employeeList(String keyword, Integer companyId, Integer filter, String order, String asc, Integer pageNumber, Integer pageSize, String timespan,String emailValidate) throws CommonException {
        logger.info("UserHrAccountService employeeList filter:{}, order:{}, asc:{}, pageNumber:{}, pageSize:{}, timespan:{}, keyword:{}", filter, order, asc, pageNumber, pageSize, timespan, keyword);
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
                queryBuilder.and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Actived.getState());
            } else if (filter == 2) {
                queryBuilder.and(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), EmployeeActiveState.Init.getState());
            } else if(filter == 3) {
                List<Integer> activations = new ArrayList<Integer>(){{
                    add((int)EmployeeActiveState.Cancel.getState());
                    add((int)EmployeeActiveState.Failure.getState());
                    add((int)EmployeeActiveState.MigrateToOtherCompany.getState());
                    add((int)EmployeeActiveState.UnFollow.getState());
                }};
                queryBuilder.and(new Condition(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), activations, ValueOp.IN));
            }
        }
        if(StringUtils.isNotNullOrEmpty(emailValidate)){
            queryBuilder.and(UserEmployee.USER_EMPLOYEE.EMAIL_ISVALID.getName(), Integer.parseInt(emailValidate));
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
            logger.info("UserHrAccountService employeeList timespan:{}", timespan);
            userEmployeeVOPageVO.setData(employeeList(queryBuilder, 0, companyIds, null));
            return userEmployeeVOPageVO;
        }
        // 员工列表，从ES中获取积分月，季，年榜单数据
        Response response = null;
        try {
            logger.info("UserHrAccountService employeeList queryAwardRanking companyIds:{}, timespan:{}, pageSize:{}, pageNumber:{}, keyword:{}, filter:{}", companyIds, timespan, pageSize, pageNumber, keyword, filter);
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
                // 根据totalHits 条件命中条数重新设置分页信息
                userEmployeeVOPageVO.setTotalRow(rankObj.getTotal());
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
                logger.info("ES ThirdPartyInfoData is empty!!!!");
                userEmployeeVOPageVO.setData(employeeList(queryBuilder, 1, companyIds, null));
            }
        } else {
            throw UserAccountException.SEARCH_ES_ERROR;
        }
        return userEmployeeVOPageVO;
    }

    /**
     * 员工列表
     * @param keyword    关键字搜索
     * @param companyId  公司ID
     * @param filter     过滤条件，0：全部，1：已认证，2：未认证， 3 撤销认证,默认：0
     * @param order      排序条件
     * @param asc        正序，倒序 0: 正序,1:倒序 默认
     * @param pageNumber 第几页
     * @param pageSize   每页的条数
     * @param timeSpan   时间区间
     * @param selectIds
     */
    public UserEmployeeVOPageVO getEmployees(String keyword, Integer companyId, Integer filter, String order, String asc,
                                             Integer pageNumber, Integer pageSize, String emailValidate,
                                             Integer balanceType, String timeSpan, String selectIds) throws CommonException {
        UserEmployeeVOPageVO userEmployeeVOPageVO = new UserEmployeeVOPageVO();
        // 公司ID未设置
        if (companyId == 0) {
            throw UserAccountException.COMPANY_DATA_EMPTY;
        }

        // 取公司ID
        List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
        Response response;
        if(StringUtils.isNotNullOrEmpty(keyword)){
            keyword = keyword.toLowerCase();
        }
        try {
            logger.info("getEmployees pageNum:{}, pageSize:{}", pageNumber, pageSize);
            /*if (org.apache.commons.lang.StringUtils.isNotBlank(order)) {
                order = order.replace("unbind_time", "unbind_time_long").replace("import_time", "import_time_long");
            }*/
            response = searchengineServices.fetchEmployees(companyIds, keyword, filter, order, asc, emailValidate,
                    pageSize, pageNumber,balanceType, timeSpan, selectIds);
        } catch (Exception e) {
            throw UserAccountException.SEARCH_ES_ERROR;
        }
        // ES取到数据
        if (response.getStatus() == 0) {
            EmployeeList rankObj = JSONObject.parseObject(response.getData(), EmployeeList.class);
            List<UserEmployeeDO> employees = rankObj.getData();
            if (employees != null && employees.size() > 0) {
                logger.info("ES Data Size:{}", employees.size());
                // 根据totalHits 条件命中条数重新设置分页信息
                userEmployeeVOPageVO.setTotalRow(rankObj.getTotal());
                // 封装查询条件
                userEmployeeVOPageVO.setData(packageEmployeeVOs(employees, companyIds));
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
            List customFieldValues = new ArrayList();
            if (userEmployeeDO.getCustomFieldValues() != null) {
                customFieldValues.addAll(JSONObject.parseObject(userEmployeeDO.getCustomFieldValues(), List.class));
            }
            userEmployeeVO.setCustomFieldValues(customFieldValues);
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

        if (userEmployeeMap.size() > FIVE_THOUSAND) {
            throw UserAccountException.EMPLOYEE_BATCH_UPDAT_OVER_LIMIT;
        }

        // 判断是否有重复数据
        ImportUserEmployeeStatistic importUserEmployeeStatistic = repetitionFilter(userEmployeeMap, companyId);

        //校验自定义信息填写是否正确
        if (importUserEmployeeStatistic != null && !importUserEmployeeStatistic.insertAccept) {
            throw UserAccountException.IMPORT_DATA_WRONG;
        }

        // 通过手机号查询那些员工数据是更新，那些数据是新增
        List<String> moblies = new ArrayList<>();
        List<UserEmployeeDO> userEmployeeList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userEmployeeMap.forEach((k, v) -> {
            v.setImportTime(now.format(dateTimeFormatter));
            v.setActivation(EmployeeActiveState.Init.getState());
            v.setAuthMethod((byte) EmployeeAuthMethod.CUSTOM_AUTH.getCode());
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
            batchValidate.convertToOptionId(userEmployeeDOS, companyId);

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
                searchengineEntity.updateEmployeeAwards(updateUserEmployee.stream().filter(f -> f.getId() > 0).map(m -> m.getId()).collect(Collectors.toList()), false);
                // 去掉需要更新的数据
                userEmployeeList.removeAll(updateUserEmployee);
            }
        }
        // 新增数据
        if (!StringUtils.isEmptyList(userEmployeeList)) {
            employeeEntity.addEmployeeListIfNotExist(userEmployeeList);

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
     * 员工信息导入
     *
     * @param companyId       公司ID
     * @param userEmployeeMap 员工信息列表
     */
    @Transactional
    public ImportUserEmployeeStatistic updateEmployees(Integer companyId, List<UserEmployeeDO> userEmployeeMap, String filePath, String
            fileName, Integer type, Integer hraccountId) throws CommonException {
        logger.info("开始批量修改");

        // 员工导入信息日志
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("导入的数据类型", type, "不能为空", null, 0, 100);
        vu.addIntTypeValidate("HR账号", hraccountId, "不能为空", null, 1, 1000000);
        vu.addStringLengthValidate("导入文件的绝对路径", filePath, null, null, 0, 257);
        vu.addStringLengthValidate("导入的文件名", fileName, null, null, 0, 257);

        String errorMessage = vu.validate();
        if (!StringUtils.isNullOrEmpty(errorMessage)) {
            throw UserAccountException.validateFailed(errorMessage);
        }

        if (userEmployeeMap.size() > FIVE_THOUSAND) {
            throw UserAccountException.EMPLOYEE_BATCH_UPDAT_OVER_LIMIT;
        }

        logger.info("UserHrAccountService before query");
        // 查找已经存在的数据
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.clear();
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyId)
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
        // 数据库中取出来的数据
        List<UserEmployeeDO> dbEmployeeDOList = userEmployeeDao.getDatas(queryBuilder.buildQuery());

        logger.info("UserHrAccountService before batchValidate.updateCheck");

        ImportUserEmployeeStatistic importUserEmployeeStatistic = batchValidate.updateCheck(userEmployeeMap, companyId, dbEmployeeDOList);

        logger.info("UserHrAccountService after batchValidate.updateCheck");

        List<UserEmployeeDO> updateCustomFieldList = new ArrayList<>();
        List<UserEmployeeDO> updateActivationList = new ArrayList<>();

        List<Integer> employeeIdList = new ArrayList<>();

        List<Integer> errorEmployeeIdList = new ArrayList<>();
        if (importUserEmployeeStatistic.getUserEmployeeDO() != null
                && importUserEmployeeStatistic.getUserEmployeeDO().size() > 0) {
            for (ImportErrorUserEmployee employeeDO : importUserEmployeeStatistic.getUserEmployeeDO()) {
                errorEmployeeIdList.add(employeeDO.getUserEmployeeDO().getId());
            }
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        logger.info("UserHrAccountService before batchValidate.convertToOptionId");
        batchValidate.convertToOptionId(userEmployeeMap, companyId);
        logger.info("UserHrAccountService after batchValidate.convertToOptionId");

        for (UserEmployeeDO userEmployee : userEmployeeMap) {

            if (errorEmployeeIdList.contains(userEmployee.getId())) {
                continue;
            }

            Optional<UserEmployeeDO> optional = dbEmployeeDOList.parallelStream()
                    .filter(dbEmployee -> dbEmployee.getId() == userEmployee.getId()
                                    && !userEmployee.getCustomFieldValues().equals(dbEmployee.getCustomFieldValues()))
                    .findAny();

            if (optional.isPresent()) {
                updateCustomFieldList.add(userEmployee);
                employeeIdList.add(userEmployee.getId());
            }

            Optional<UserEmployeeDO> optional1 = dbEmployeeDOList
                    .parallelStream()
                    .filter(dbEmployee -> dbEmployee.getId() == userEmployee.getId())
                    .findAny();

            if (optional1.isPresent()) {
                if (userEmployee.getActivation() != optional1.get().getActivation()
                        && optional1.get().getActivation() == EmployeeActiveState.Actived.getState()
                        && userEmployee.getActivation() == EmployeeActiveState.Cancel.getState()) {
                    userEmployee.setUnbindTime(now.format(dateTimeFormatter));
                    updateActivationList.add(userEmployee);
                }
            }
        }

        logger.info("UserHrAccountService before userEmployeeDao.updateRecords");
        if (updateCustomFieldList.size() > 0) {
            List<UserEmployeeRecord> records = updateCustomFieldList
                    .parallelStream()
                    .map(userEmployeeDO -> {
                        UserEmployeeRecord userEmployeeRecord = new UserEmployeeRecord();
                        userEmployeeRecord.setId(userEmployeeDO.getId());
                        userEmployeeRecord.setCustomFieldValues(userEmployeeDO.getCustomFieldValues());
                        return userEmployeeRecord;
                    })
                    .collect(Collectors.toList());
            userEmployeeDao.updateRecords(records);
        }

        logger.info("UserHrAccountService after userEmployeeDao.updateRecords");

        if (employeeIdList.size() == 0 && updateActivationList.size() == 0) {
            throw UserAccountException.USEREMPLOYEES_EMPTY_OR_NO_NEED_UPDATE;
        }

        logger.info("UserHrAccountService before searchengineEntity.updateEmployeeAwards");
        if (employeeIdList.size() > 0) {
            logger.info("UserHrAccountService updateEmployees employeeIdList.size:{}", employeeIdList.size());
            searchengineEntity.updateEmployeeAwards(Lists.newArrayList(employeeIdList), false);
        }
        logger.info("UserHrAccountService after searchengineEntity.updateEmployeeAwards");
        if (updateActivationList.size() > 0) {
            logger.info("UserHrAccountService updateEmployees updateActivationList.size:{}", updateActivationList.size());
            employeeEntity.unbind(updateActivationList);
        }
        logger.info("UserHrAccountService after employeeEntity.unbind");

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
        logger.info("批量修改结束");
        return importUserEmployeeStatistic;
    }

    /**
     * 检查员工重复(批量导入之前验证)
     *
     * @param userEmployeeMap
     * @param companyId
     * @return
     */

    public ImportUserEmployeeStatistic checkBatchInsert(Map<Integer, UserEmployeeDO> userEmployeeMap, Integer companyId) throws CommonException {
        logger.info("UserHrAccountServiceImpl checkBatchInsert");
        return repetitionFilter(userEmployeeMap, companyId);
    }

    /**
     * 查询是否有重复数据
     *
     * @param userEmployeeMap
     * @param companyId
     */
    private ImportUserEmployeeStatistic repetitionFilter(Map<Integer, UserEmployeeDO> userEmployeeMap, Integer companyId) throws CommonException {
        LocalDateTime initDateTime = LocalDateTime.now();
        logger.info("自定义认证导入2 UserHrAccountService repetitionFilter initDateTime:{}", initDateTime.toString());
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

        // 查找已经存在的数据
        queryBuilder.clear();
        queryBuilder.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyId)
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 0);
        // 数据库中取出来的数据
        List<UserEmployeeDO> dbEmployeeDOList = userEmployeeDao.getDatas(queryBuilder.buildQuery());

        ImportUserEmployeeStatistic importUserEmployeeStatistic = batchValidate.importCheck(userEmployeeMap,
                companyId, dbEmployeeDOList);
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
        org.springframework.beans.BeanUtils.copyProperties(userEmployeeDO, userEmployeeDetailVO);
        userEmployeeDetailVO.setUsername(userEmployeeDO.getCname());
        userEmployeeDetailVO.setActivation((new Double(userEmployeeDO.getActivation())).intValue());
        userEmployeeDetailVO.setAuthMethod(new Integer(userEmployeeDO.getAuthMethod()).intValue());
        userEmployeeDetailVO.setBonus(new BigDecimal(userEmployeeDO.getBonus()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toPlainString().replace(".00",""));

        if (userEmployeeDO.getCustomFieldValues() != null) {
            List<Map<String, String>> list = batchValidate.parseCustomFieldValues(userEmployeeDO.getCustomFieldValues());
            userEmployeeDetailVO.setCustomFieldValues(list);
        }
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

        // 设置解绑时间
        ReferralEmployeeRegisterLog referralEmployeeRegisterLog = referralEmployeeRegisterLogDao.getRegisterLogByEmployeeId(userEmployeeId, 0);
        if(referralEmployeeRegisterLog!=null) {
            Timestamp timestamp = referralEmployeeRegisterLog.getOperateTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userEmployeeDetailVO.setUnbindingTime(df.format(timestamp));
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
            customField, Integer userEmployeeId, Integer companyId, String customFieldValues) throws CommonException {
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
            //先查询原数据，在手机号，姓名没有传值的时候，不予更新
            UserEmployeeDO userEmployeeDO = userEmployeeDao.getEmployeeById(userEmployeeId);
            if (StringUtils.isNotNullOrEmpty(cname)) {
                userEmployeeDO.setCname(cname);
            }
            if (StringUtils.isNotNullOrEmpty(mobile)) {
                userEmployeeDO.setMobile(mobile);
            }
            if (StringUtils.isNotNullOrEmpty(customField)) {
                userEmployeeDO.setCustomField(customField);
            }
            if (StringUtils.isNotNullOrEmpty(email)) {
                userEmployeeDO.setEmail(email);
            }
            if (StringUtils.isNotNullOrEmpty(customFieldValues)) {
                userEmployeeDO.setCustomFieldValues(customFieldValues);
            }
            int i = userEmployeeDao.updateData(userEmployeeDO);
            if (i > 0) {
                response = ResultMessage.SUCCESS.toResponse();
                searchengineEntity.updateEmployeeAwards(Lists.newArrayList(userEmployeeId), false);
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
     * @param companyId  公司编号
     * @param pageNumber 分页信息之页码
     * @param pageSize   分页信息之每页信息数量
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
                List<CandidateCompanyDO> candidateCompanyDOList = candidateCompanyDao.getCandidateCompanyByCompanyIDAndUserID(companyId, beRecomIDList);
                if (candidateCompanyDOList != null && candidateCompanyDOList.size() > 0) {
                    Map<Integer, CandidateCompanyDO> userUserDOSMap =
                            candidateCompanyDOList.stream().collect(Collectors.toMap(CandidateCompanyDO::getSysUserId,
                                    Function.identity()));
                    for (RewardVO rewardVO : rewardVOPageVO.getData()) {
                        if (userUserDOSMap.get(rewardVO.getBerecomId()) == null) {
                            rewardVO.setBerecomId(0);
                        }
                    }
                } else {
                    for (RewardVO rewardVO : rewardVOPageVO.getData()) {
                        rewardVO.setBerecomId(0);
                    }
                }
            }
        }
        logger.info("===============rewardVOPageVO:{}", JSON.toJSONString(rewardVOPageVO));
        return rewardVOPageVO;
    }

    public List<HrAppExportFieldsDO> getExportFields(int companyId, int hraccountId) {
        List<Integer> appConfigCvIds = positionEntity.getAppCvConfigIdByCompany(companyId, hraccountId);
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.orderBy("display_order");
        Set<String> configFieldName=new HashSet<>();
        List<HrAppExportFieldsDO> hrAppExportFieldsDOList = exportFieldsDao.getDatas(query.buildQuery());
        boolean isReferral = positionEntity.isReferralByHr(companyId, hraccountId);
        logger.info("getExportFields isReferral:{}", isReferral);
        if (!appConfigCvIds.isEmpty()) {
            query.clear();
            query.where(new Condition("id", appConfigCvIds, ValueOp.IN));
            List<HrAppCvConfDO> hrAppCvConfDOList = appCvConfDao.getDatas(query.buildQuery());
            if (hrAppCvConfDOList != null && !hrAppCvConfDOList.isEmpty()) {
//                Set<String> configFieldName = hrAppCvConfDOList.stream().flatMap(m -> JSONArray.parseArray(m.getFieldValue()).getJSONObject(0).getJSONArray("fields").stream()).map(p -> JSONObject.parseObject(String.valueOf(p)).getString("field_name")).collect(Collectors.toSet());

                for(HrAppCvConfDO hrAppCvConfDO:hrAppCvConfDOList){
                    String fieldValue=hrAppCvConfDO.getFieldValue();
                    if(StringUtils.isNotNullOrEmpty(fieldValue)){
                        JSONArray array=JSONArray.parseArray(fieldValue);
                        if(array!=null&&array.size()>0){
                            for(int i=0;i<array.size();i++){
                                JSONObject jsonObject=array.getJSONObject(i);
                                JSONArray jsonArray=jsonObject.getJSONArray("fields");
                                if(jsonArray!=null&&jsonArray.size()>0){
                                    for(int j=0;j<jsonArray.size();j++){
                                        JSONObject object=jsonArray.getJSONObject(j);
                                        String fieldName=object.getString("field_name");
                                        if(StringUtils.isNotNullOrEmpty(fieldName)){
                                            configFieldName.add(fieldName) ;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }
        }
        if(isReferral){
            configFieldName.add("companyBrand");
            configFieldName.add("degree");
            configFieldName.add("current_position");
        }
        hrAppExportFieldsDOList.stream().forEach(e -> {
            if (configFieldName.contains(e.getFieldName())) {
                e.showed = 1;
            }
        });
        List<HrAppExportFieldsDO> showedApplicationExportFieldsList = hrAppExportFieldsDOList.stream().filter(f -> f.showed == 1).collect(Collectors.toList());
        List<HrAppExportFieldsDO> result=new ArrayList<>();
        if (showedApplicationExportFieldsList != null) {
            result.addAll(fetchDefaultExportFields());
            result.addAll(showedApplicationExportFieldsList);
        }
        return result;
    }

    /**
     * 查找简历导出默认导出的字段
     * todo 待老王回来一起讨论，默认的导出字段配置如何建表
     * @return 简历导出默认导出的字段
     */
    private List<HrAppExportFieldsDO> fetchDefaultExportFields() {
        List<HrAppExportFieldsDO> fieldsDOList = new ArrayList<>();
        HrAppExportFieldsDO hrAppExportFieldsDO7 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO7.setFieldTitle("职位编号");
        hrAppExportFieldsDO7.setSample("001");
        hrAppExportFieldsDO7.setFieldName("jobnumber");
        hrAppExportFieldsDO7.setShowed(0);
        hrAppExportFieldsDO7.setSelected(0);
        hrAppExportFieldsDO7.setDisplayOrder(1);
        fieldsDOList.add(hrAppExportFieldsDO7);

        HrAppExportFieldsDO hrAppExportFieldsDO8 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO8.setFieldTitle("申请职位");
        hrAppExportFieldsDO8.setSample("前端工程师");
        hrAppExportFieldsDO8.setFieldName("title");
        hrAppExportFieldsDO8.setShowed(0);
        hrAppExportFieldsDO8.setSelected(0);
        hrAppExportFieldsDO8.setDisplayOrder(1);
        fieldsDOList.add(hrAppExportFieldsDO8);

        HrAppExportFieldsDO hrAppExportFieldsDO1 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO1.setFieldTitle("职位发布人");
        hrAppExportFieldsDO1.setSample("张三");
        hrAppExportFieldsDO1.setFieldName("position_publisher_name");
        hrAppExportFieldsDO1.setShowed(0);
        hrAppExportFieldsDO1.setSelected(0);
        hrAppExportFieldsDO1.setDisplayOrder(3);
        fieldsDOList.add(hrAppExportFieldsDO1);

        HrAppExportFieldsDO hrAppExportFieldsDO2 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO2.setFieldTitle("职位所属部门");
        hrAppExportFieldsDO2.setSample("财务部");
        hrAppExportFieldsDO2.setFieldName("position_department");
        hrAppExportFieldsDO2.setShowed(0);
        hrAppExportFieldsDO2.setSelected(0);
        hrAppExportFieldsDO2.setDisplayOrder(4);
        fieldsDOList.add(hrAppExportFieldsDO2);

        HrAppExportFieldsDO hrAppExportFieldsDO9 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO9.setFieldTitle("招聘地点");
        hrAppExportFieldsDO9.setSample("北京,上海");
        hrAppExportFieldsDO9.setFieldName("city");
        hrAppExportFieldsDO9.setShowed(0);
        hrAppExportFieldsDO9.setSelected(0);
        hrAppExportFieldsDO9.setDisplayOrder(5);
        fieldsDOList.add(hrAppExportFieldsDO9);

        HrAppExportFieldsDO hrAppExportFieldsDO3 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO3.setFieldTitle("职位招聘类型");
        hrAppExportFieldsDO3.setSample("社招");
        hrAppExportFieldsDO3.setFieldName("position_candidate_source");
        hrAppExportFieldsDO3.setShowed(0);
        hrAppExportFieldsDO3.setSelected(0);
        hrAppExportFieldsDO3.setDisplayOrder(6);
        fieldsDOList.add(hrAppExportFieldsDO3);

        HrAppExportFieldsDO hrAppExportFieldsDO4 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO4.setFieldTitle("职位招聘性质");
        hrAppExportFieldsDO4.setSample("全职");
        hrAppExportFieldsDO4.setFieldName("position_employment_type");
        hrAppExportFieldsDO4.setShowed(0);
        hrAppExportFieldsDO4.setSelected(0);
        hrAppExportFieldsDO4.setDisplayOrder(7);
        fieldsDOList.add(hrAppExportFieldsDO4);

        HrAppExportFieldsDO hrAppExportFieldsDO5 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO5.setFieldTitle("自定义字段１");
        hrAppExportFieldsDO5.setSample("abc");
        hrAppExportFieldsDO5.setFieldName("position_occupation_name");
        hrAppExportFieldsDO5.setShowed(0);
        hrAppExportFieldsDO5.setSelected(0);
        hrAppExportFieldsDO5.setDisplayOrder(8);
        fieldsDOList.add(hrAppExportFieldsDO5);

        HrAppExportFieldsDO hrAppExportFieldsDO6 = new HrAppExportFieldsDO();
        hrAppExportFieldsDO6.setFieldTitle("自定义字段2");
        hrAppExportFieldsDO6.setSample("def");
        hrAppExportFieldsDO6.setFieldName("position_custom_name");
        hrAppExportFieldsDO6.setShowed(0);
        hrAppExportFieldsDO6.setSelected(0);
        hrAppExportFieldsDO6.setDisplayOrder(9);
        fieldsDOList.add(hrAppExportFieldsDO6);

        return fieldsDOList;
    }


    public UserHrAccountDO requiresNotNullAccount(int hrId){
        UserHrAccountDO hrAccountDO = userHrAccountDao.getValidAccount(hrId);
        if (hrAccountDO == null) {
            throw HRException.USER_NOT_EXISTS;
        }
        return hrAccountDO;
    }


    /**
     * 获取HR账号信息以及公司信息
     * @param wechat_id 微信公众号编号
     * @param unionId    HR微信unionId
     * @return
     */
    public Response getHrCompanyInfo(int wechat_id, String unionId, int account_id){
        UserHrAccountDO accountDO = null;
        UserWxUserDO userWxUserDO = null;
        //当HR编号不存在时使用unionid获取Hr账号信息
        if(account_id<=0) {
            Query query = new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), wechat_id)
                    .and(UserWxUser.USER_WX_USER.UNIONID.getName(), unionId)
                    .and(UserWxUser.USER_WX_USER.IS_SUBSCRIBE.getName(), "1").buildQuery();
            userWxUserDO = userWxUserDao.getData(query);
            if (userWxUserDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
            Query accountQuery = new Query.QueryBuilder().where(com.moseeker.baseorm.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT.WXUSER_ID.getName(), userWxUserDO.getId()).buildQuery();
            accountDO = userHrAccountDao.getData(accountQuery);
            if (accountDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
            account_id = accountDO.getId();
        }else{
            Query accountQuery = new Query.QueryBuilder().where(com.moseeker.baseorm.db.userdb.tables.UserHrAccount.USER_HR_ACCOUNT.ID.getName(), account_id).buildQuery();
            accountDO = userHrAccountDao.getData(accountQuery);
            if (accountDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
            Query query = new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.ID.getName(), accountDO.getWxuserId()).buildQuery();
            userWxUserDO = userWxUserDao.getData(query);
            if (userWxUserDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.USERACCOUNT_NOTEXIST);
        }
        Query companyAccountQuery = new Query.QueryBuilder().where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.getName(), account_id).buildQuery();
        HrCompanyAccountDO companyAccountDO = hrCompanyAccountDao.getData(companyAccountQuery);
        if(companyAccountDO == null)
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        Query companyQuery = new Query.QueryBuilder().where(HrCompany.HR_COMPANY.ID.getName(), companyAccountDO.getCompanyId()).buildQuery();
        HrCompanyDO companyDO = hrCompanyDao.getData(companyQuery);
        if(companyDO == null)
            return ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);

        Map<String, Object> params = new HashMap<>();
        params.put("accountId",account_id);
        params.put("abbreviation", companyDO.getAbbreviation());
        String name = accountDO.getUsername();
        if(!StringUtils.isNotNullOrEmpty(name) && StringUtils.isNotNullOrEmpty(accountDO.getMobile())){
            name = accountDO.getMobile().substring(0,3)+"****"+accountDO.getMobile().substring(7,11);
        }
        params.put("username", name);
        params.put("type", false);
        if(companyDO.getType() == 0)
            params.put("type", true);
        String logo = "";
        String cdn = env.getProperty("cdn.url");
        if(StringUtils.isNotNullOrEmpty(accountDO.getHeadimgurl())){
            if(accountDO.getHeadimgurl().startsWith("http")){
                logo = accountDO.getHeadimgurl();
            }else{
                logo = cdn+accountDO.getHeadimgurl();
            }
        }else if(StringUtils.isNotNullOrEmpty(userWxUserDO.getHeadimgurl())){
            if(userWxUserDO.getHeadimgurl().startsWith("http")){
                logo = userWxUserDO.getHeadimgurl();
            }else{
                logo = cdn+userWxUserDO.getHeadimgurl();
            }
        }else if(StringUtils.isNotNullOrEmpty(companyDO.getLogo())){
            if(companyDO.getLogo().startsWith("http")){
                logo = companyDO.getLogo();
            }else{
                logo = cdn +companyDO.getLogo();
            }
        }
        if(!logo.startsWith("https") && logo.startsWith("http")){
            logo = logo.replace("http", "https");
        }
        params.put("headImgUrl",logo);
        return ResponseUtils.success(params);

    }

    /**
     * 设置HR聊天是否托管给智能招聘助手
     * @param accountId HR账号ID
     * @param leaveToMobot 是否托管给智能招聘助手，0 不托管，1 托管
     * @return 设置完成后的hr账号信息
     * @throws TException
     */
    public UserHrAccountDO switchChatLeaveToMobot(int accountId,byte leaveToMobot) throws TException {
        if(accountId <= 0 ||
                (leaveToMobot != CompanyConf.LeaveToMobot.OFF && leaveToMobot != CompanyConf.LeaveToMobot.ON)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }

        UserHrAccountDO hrAccount = requiresNotNullAccount(accountId);

        HrCompanyConfDO hrCompanyConf = companyServices.getCompanyConfById(hrAccount.getCompanyId());

        // 未开启ChatBot功能的企业，点击开关提示尚未采购该功能，详情请联系您的客户成功顾问。
        if(hrCompanyConf.getHrChat() != CompanyConf.HRCHAT.ON_AND_MOBOT){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.CANT_SET_LEAVE_TO_MOBOT);
        }

        // 已经是要设置的值就不做修改
        if(hrAccount.getLeaveToMobot() == leaveToMobot){
            return hrAccount;
        }

        // CAS修改要设置的值
        userHrAccountDao.switchChatLeaveToMobot(hrAccount.getId(),hrAccount.getLeaveToMobot(),leaveToMobot);

        return requiresNotNullAccount(accountId);
    }

    public HRInfo getHR(int id) throws UserAccountException {
        UserHrAccountDO hrAccountDO = userHrAccountDao.getValidAccount(id);
        if (hrAccountDO == null) {
            throw UserAccountException.HRACCOUNT_NOT_EXIST;
        }
        HrCompanyDO hrCompanyDO = hrCompanyDao.getCompanyById(hrAccountDO.getCompanyId());
        if (hrAccountDO == null) {
            throw UserAccountException.HRACCOUNT_NOT_EXIST;
        }
        HRInfo hrInfo = new HRInfo();
        packageHRInfo(hrInfo, hrAccountDO, hrCompanyDO);
        return hrInfo;
    }

    private void packageHRInfo(HRInfo hrInfo, UserHrAccountDO hrAccountDO, HrCompanyDO hrCompanyDO) {
        hrInfo.setId(hrAccountDO.getId());
        HRAccountType accountType = HRAccountType.initFromType(hrAccountDO.getAccountType());
        hrInfo.setAccountType(accountType.toString());
        hrInfo.setEmail(hrAccountDO.getEmail());
        hrInfo.setHeadImg(hrAccountDO.getHeadimgurl());
        hrInfo.setMobile(hrAccountDO.getMobile());
        hrInfo.setName(hrAccountDO.getUsername());
        hrInfo.setCompanyAbbreviation(hrCompanyDO.getAbbreviation());
        hrInfo.setCompany(hrCompanyDO.getName());
        hrInfo.setCompanyId(hrCompanyDO.getId());
    }

    /**
     * 员工数据转成管理的列表数据
     * @param employees 员工数据
     * @param companyIds 公司编号集合（集团公司存在多个公司编号）
     * @return 员工管理的列表数据
     */
    private List<UserEmployeeVO> packageEmployeeVOs(List<UserEmployeeDO> employees, List<Integer> companyIds) {
        List<UserEmployeeVO> userEmployeeVOS = new ArrayList<>();
        if (employees != null && employees.size() > 0) {
            Set<Integer> sysuserId = employees.stream().filter(userUserDO -> userUserDO.getSysuserId() > 0)
                    .map(UserEmployeeDO::getSysuserId).collect(Collectors.toSet());
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.clear();
            queryBuilder.where(new Condition(UserUser.USER_USER.ID.getName(), sysuserId, ValueOp.IN));
            // 查询微信昵称
            List<UserUserDO> userUserDOList = userUserDao.getDatas(queryBuilder.buildQuery());
            Map<Integer, UserUserDO> userMap = userUserDOList.stream().collect(Collectors.toMap(UserUserDO::getId, Function.identity()));

            queryBuilder.clear();
            queryBuilder.where(new Condition(HrCompany.HR_COMPANY.ID.getName(), companyIds, ValueOp.IN));
            List<HrCompanyDO> companyList = hrCompanyDao.getDatas(queryBuilder.buildQuery());

            List<HrEmployeeCustomFields> fieldsList = customFieldsDao.listSystemCustomFieldByCompanyIdList(companyIds);
            List<EmployeeOptionValue> employeeOptionValues;
            if (fieldsList != null && fieldsList.size() > 0) {
                List<Integer> fieldIdList = fieldsList
                        .parallelStream()
                        .filter(hrEmployeeCustomFields -> hrEmployeeCustomFields.getOptionType() == OptionType.Select.getValue())
                        .map(HrEmployeeCustomFields::getId)
                        .collect(Collectors.toList());
                employeeOptionValues = customOptionJooqDao.listByCustomIdList(fieldIdList);
            } else {
                employeeOptionValues = new ArrayList<>(0);
            }


            // 查询公司信息
            Map<Integer, HrCompanyDO> companyMap = companyList.stream().collect(Collectors.toMap(HrCompanyDO::getId, Function.identity()));
            for (UserEmployeeDO userEmployeeDO : employees) {
                UserEmployeeVO userEmployeeVO = new UserEmployeeVO();
                org.springframework.beans.BeanUtils.copyProperties(userEmployeeDO, userEmployeeVO);
                userEmployeeVO.setUsername(userEmployeeDO.getCname());
                //将绑定时间2018-10-09T18:09:09.766+08:00格式化成2018-10-09 18:09:09
                if(StringUtils.isNotNullOrEmpty(userEmployeeDO.getBindingTime())) {
                    userEmployeeVO.setBindingTime(new DateTime(userEmployeeDO.getBindingTime()).toString("yyyy-MM-dd HH:mm:ss"));
                }
                if (StringUtils.isNotNullOrEmpty(userEmployeeDO.getUnbindTime())) {
                    userEmployeeVO.setUnbindTime(new DateTime(userEmployeeDO.getUnbindTime()).toString("yyyy-MM-dd HH:mm:ss"));
                }
                if (StringUtils.isNotNullOrEmpty(userEmployeeDO.getImportTime())) {
                    userEmployeeVO.setImportTime(new DateTime(userEmployeeDO.getImportTime()).toString("yyyy-MM-dd HH:mm:ss"));
                }
                if(userEmployeeVO.getAward()<0){
                    userEmployeeVO.setAward(0);
                }
                List<Map<String, String>> customFieldValues = new ArrayList(3);

                if (userEmployeeDO.getCustomFieldValues() != null) {

                    List<Map<String, String>> list = batchValidate.parseCustomFieldValues(userEmployeeDO.getCustomFieldValues());
                    userEmployeeVO.setCustomFieldValues(list);

                    List<Map<String, String>> list1 = batchValidate.convertToListDisplay(list, fieldsList, employeeOptionValues, userEmployeeDO.getCompanyId());
                    customFieldValues.addAll(list1);
                }
                userEmployeeVO.setSystemFields(customFieldValues);
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
                userEmployeeVO.setBonus(new BigDecimal(userEmployeeDO.getBonus()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP).toPlainString().replace(".00",""));
                userEmployeeVO.setAuthMethod(userEmployeeDO.getAuthMethod());
                userEmployeeVOS.add(userEmployeeVO);
            }
        }
        return userEmployeeVOS;
    }


    /**
     *
     * @param hrAccountId
     * @param flag
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Transactional
    public Response setApplicationNotify(int hrAccountId, boolean flag) throws BIZException, TException {

       com.moseeker.baseorm.db.hrdb.tables.pojos.HrAccountApplicationNotify hrAccountApplicationNotify = hrAccountApplicationNotifyDao.fetchOne(HrAccountApplicationNotify.HR_ACCOUNT_APPLICATION_NOTIFY.HR_ACCOUNT_ID,hrAccountId);
       LocalDateTime now = LocalDateTime.now();
       if(hrAccountApplicationNotify == null)  {

           hrAccountApplicationNotify = new com.moseeker.baseorm.db.hrdb.tables.pojos.HrAccountApplicationNotify();
           hrAccountApplicationNotify.setFlag((flag?(byte)1:(byte)0));
           hrAccountApplicationNotify.setHrAccountId(hrAccountId);
           hrAccountApplicationNotify.setCreateTime(Timestamp.valueOf(now));
           hrAccountApplicationNotify.setUpdateTime(Timestamp.valueOf(now));

           hrAccountApplicationNotifyDao.insert(hrAccountApplicationNotify);
       }else {
           hrAccountApplicationNotify.setFlag((flag?(byte)1:(byte)0));
           hrAccountApplicationNotify.setUpdateTime(Timestamp.valueOf(now));
           hrAccountApplicationNotifyDao.update(hrAccountApplicationNotify);
       }
       return ResponseUtils.success(true);
    }

    /**
     *
     *
     *
     * @param hrAccountId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Transactional
    public Response getApplicationNotify(int hrAccountId) throws BIZException, TException {

        com.moseeker.baseorm.db.hrdb.tables.pojos.HrAccountApplicationNotify hrAccountApplicationNotify = hrAccountApplicationNotifyDao.fetchOne(HrAccountApplicationNotify.HR_ACCOUNT_APPLICATION_NOTIFY.HR_ACCOUNT_ID,hrAccountId);

        if(hrAccountApplicationNotify  != null && hrAccountApplicationNotify.getFlag() == (byte)0) {
            return ResponseUtils.success(false);

        } else {
            return ResponseUtils.success(true);
        }
    }

    @Transactional
    public BonusVOPageVO getEmployeeBonus(int employeeId, int companyId, int pageNumber, int pageSize) throws TException {
        BonusVOPageVO bonusVOPageVO = employeeEntity.getEmployeeBonusRecords(employeeId, pageNumber, pageSize);
        /**
         * 查询公司下候选人信息，如果候选人不存在则将berecomID 置为0，用以通知前端不需要拼接潜在候选人的url链接。
         */
        if (bonusVOPageVO.getData() != null && bonusVOPageVO.getData().size() > 0) {
            List<Integer> beRecomIDList = bonusVOPageVO.getData().stream().filter(m -> m.getBerecomId() != 0)
                    .map(m -> m.getBerecomId()).collect(Collectors.toList());
            if (beRecomIDList != null && beRecomIDList.size() > 0) {
                List<CandidateCompanyDO> candidateCompanyDOList = candidateCompanyDao.getCandidateCompanyByCompanyIDAndUserID(companyId, beRecomIDList);
                if (candidateCompanyDOList != null && candidateCompanyDOList.size() > 0) {
                    Map<Integer, CandidateCompanyDO> userUserDOSMap =
                            candidateCompanyDOList.stream().collect(Collectors.toMap(CandidateCompanyDO::getSysUserId,
                                    Function.identity()));
                    for (BonusVO bonusVO : bonusVOPageVO.getData()) {
                        if (userUserDOSMap.get(bonusVO.getBerecomId()) == null) {
                            bonusVO.setBerecomId(0);
                        }
                    }
                } else {
                    for (BonusVO bonusVO : bonusVOPageVO.getData()) {
                        bonusVO.setBerecomId(0);
                    }
                }
            }
        }
        return bonusVOPageVO;
    }

}