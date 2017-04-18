package com.moseeker.useraccounts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;
import com.moseeker.useraccounts.constant.BindingStatus;
import com.moseeker.useraccounts.constant.ResultMessage;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.types.UByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.SearcheConditionDao;
import com.moseeker.thrift.gen.dao.service.TalentpoolDao;
import com.moseeker.thrift.gen.dao.struct.Talentpool;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.foundation.passport.service.HRAccountFoundationServices;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.DownloadReport;
import com.moseeker.thrift.gen.useraccounts.struct.SearchCondition;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.useraccounts.dao.UserHrDao;

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

    private static final String REDIS_KEY_HR_SMS_SIGNUP = "HR_SMS_SIGNUP";

    RedisClient redisClient = RedisClientFactory.getCacheClient();

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface hraccountDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface.class);

    HRAccountFoundationServices.Iface hrAccountService = ServiceManager.SERVICEMANAGER
            .getService(HRAccountFoundationServices.Iface.class);

    SearcheConditionDao.Iface searchConditionDao = ServiceManager.SERVICEMANAGER.getService(SearcheConditionDao.Iface.class);

    TalentpoolDao.Iface talentpoolDao = ServiceManager.SERVICEMANAGER.getService(TalentpoolDao.Iface.class);

    @Autowired
    private UserHrDao userHrDao;

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
            return ResponseUtils.success(SmsSender.sendHrMobileVertfyCode(mobile, REDIS_KEY_HR_SMS_SIGNUP, source));

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
                companyRecord.setType(UByte.valueOf(1));
                if (downloadReport.isSetCompany_name()) {
                    companyRecord.setName(downloadReport.getCompany_name());
                }
                companyRecord.setSource(UByte.valueOf(Constant.COMPANY_SOURCE_DOWNLOAD));
                int result = userHrDao.createHRAccount(userHrAccountRecord, companyRecord);

                if (result > 0 && downloadReport.getSource() == Constant.HR_ACCOUNT_SIGNUP_SOURCE_WWW) {
                    SmsSender.sendHrSmsSignUpForDownloadIndustryReport(downloadReport.getMobile(), passwordArray[0]);
                }
                if (result > 0) {
                    return ResponseUtils.success(new HashMap<String, Object>() {
                        private static final long serialVersionUID = -496268769759269821L;

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

            int userHrAccountId = userHrDao.putResource(userHrAccountRecord);
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
     * @param account
     * @return
     */
    public Response bindThirdAccount(BindAccountStruct account) {
        try {
            logger.info("-------bindThirdAccount--------");
            logger.info("bindThirdAccount");
            // 判断是否需要进行帐号绑定
            if (account.getCompany_id() == 0 && account.getUser_id() != 0) {
                QueryUtil qu = new QueryUtil();
                qu.addEqualFilter("id", String.valueOf(account.getUser_id()));
                logger.info("search third party account");
                Response response = hraccountDao.getAccount(qu);
                if (response.getStatus() == 0) {
                    logger.info("thirdPartyAccount:" + response.getData());
                    JSONObject json = JSONObject.parseObject(response.getData());
                    account.setCompany_id(json.getIntValue("company_id"));
                }
            }
            logger.info("search allowBind");
            Response allowBindResponse = hrAccountService.allowBind(account.getUser_id(), account.getCompany_id(),
                    account.getChannel());
            if (allowBindResponse.getStatus() == 0) {
                logger.info("bindThirdAccount have permission");
                // 请求chaos，获取点数
                /**
                 * TODO 获取可发布职位数量
                 * 传递company name
                 */
                Response response = chaosService.binding(account.getUsername(), account.getPassword(),
                        account.getMember_name(), account.getChannel());
                if (response.getStatus() == 0) {
                    JSONObject data = JSONObject.parseObject(response.getData());
                    account.setRemainNum(data.getIntValue("remain_number"));
                    account.setRemainProfileNum(data.getIntValue("resume_number"));
                    return hrAccountService.createThirdPartyAccount(account);
                } else {
                    return response;
                }
            } else {
                return allowBindResponse;
            }

        } catch (TException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            // do nothing
        }
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
        CommonQuery query = new CommonQuery();
        Map<String, String> param = new HashMap<String, String>();
        query.setEqualFilter(param);
        param.put("hr_account_id", String.valueOf(hrAccountId));
        param.put("type", String.valueOf(type));
        List<SearchCondition> list;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            list = searchConditionDao.getResources(query);
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
        } catch (TException e) {
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
            CommonQuery query = new CommonQuery();
            Map<String, String> param = new HashMap<String, String>();
            query.setEqualFilter(param);
            param.put("hr_account_id", String.valueOf(searchCondition.getHr_account_id()));
            param.put("type", String.valueOf(searchCondition.getType()));
            int row = searchConditionDao.getResourceCount(query);
            // 每个hr最多只能添加10条常用筛选
            if (row >= 10) {
                logger.warn("保存常用筛选失败，hr={}，已拥有{}条常用筛选项", searchCondition.getHr_account_id(), row);
                return ResponseUtils.fail("{'status':42004,'message':'添加失败，最多只能添加10条筛选项'}");
            }
            // 筛选项名字保证不重复
            param.put("name", searchCondition.getName());
            row = searchConditionDao.getResourceCount(query);
            if (row > 0) {
                logger.warn("保存常用筛选失败，筛选项名称={}，已存在", searchCondition.getName());
                return ResponseUtils.fail("{'status':42004,'message':'保存失败，改筛选项名称已存在'}");
            }
            int primaryKey = searchConditionDao.postResource(searchCondition);
            if (primaryKey > 0) {
                return ResponseUtils.success(primaryKey);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            }
        } catch (TException e) {
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
            resultRow = searchConditionDao.delResource(hrAccountId, id);
            if (resultRow > 0) {
                return ResponseUtils.success("");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            }
        } catch (TException e) {
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
    public Response joinTalentpool(int hrAccountId, List<Integer> applierIds) {
        CommonQuery query = new CommonQuery();
        Map<String, String> param = new HashMap<String, String>();
        query.setEqualFilter(param);
        param.put("hr_account_id", String.valueOf(hrAccountId));
        int resultRow = 0;
        try {
            for (Integer applierId : applierIds) {
                param.put("applier_id", String.valueOf(applierId));
                Talentpool talentpool = talentpoolDao.getResource(query);
                if (talentpool == null || talentpool.getId() == 0) {
                    // 将用户加入人才库
                    talentpool = new Talentpool();
                    talentpool.setApplier_id(Integer.valueOf(applierId));
                    talentpool.setHr_account_id(hrAccountId);
                    resultRow += talentpoolDao.postResource(talentpool);
                } else {
                    // 将状态改为正常
                    talentpool.setStatus(0);
                    resultRow += talentpoolDao.putResource(talentpool);
                }
            }
            if (resultRow > 0) {
                return ResponseUtils.success("");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            }
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
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
        CommonQuery query = new CommonQuery();
        Map<String, String> param = new HashMap<String, String>();
        query.setEqualFilter(param);
        param.put("hr_account_id", String.valueOf(hrAccountId));
        try {
            int resultRow = 0;
            for (Integer applierId : applierIds) {
                param.put("applier_id", String.valueOf(applierId));
                Talentpool talentpool = talentpoolDao.getResource(query);
                if (talentpool != null && talentpool.getId() > 0) {
                    // 将状态改为删除
                    talentpool.setStatus(1);
                    resultRow += talentpoolDao.putResource(talentpool);
                }
            }
            if (resultRow <= 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_POSITION_UPSERT_FAILED);
            } else {
                return ResponseUtils.success("");
            }
        } catch (TException e) {
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
            CommonQuery commonQuery = new CommonQuery();
            Map<String, String> param = new HashMap<String, String>();
            param.put("company_id", String.valueOf(companyId));
            param.put("disable", String.valueOf(disable));
            commonQuery.setEqualFilter(param);
            commonQuery.setPage(page);
            commonQuery.setPer_page(per_age);
            Response response = hraccountDao.getAccounts(commonQuery);
            return response;
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }


    /**
     * 同步第三方职位信息
     * @param id
     * @return
     */
    public Response synchronizeThirdpartyAccount(int id) {
        long startMethodTime=System.currentTimeMillis();
        //查找第三方帐号
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(id));
        try {
            long startGetAccountData=System.currentTimeMillis();
            ThirdPartAccountData data = hraccountDao.getThirdPartyAccount(qu);
            long getAccountUseTime=System.currentTimeMillis()-startGetAccountData;
            logger.info("ThirdPartAccountData in CompanyService use  time========== "+getAccountUseTime);
            //如果是绑定状态，则进行
            //判断是否已经判定第三方帐号
            if(data!=null && data.getId() > 0 && data.getBinding() == BindingStatus.BOUND.getValue()) {
                ThirdPartyAccountStruct thirdPartyAccount = new ThirdPartyAccountStruct();
                thirdPartyAccount.setChannel((byte)data.getChannel());
                thirdPartyAccount.setMemberName(data.getMembername());
                thirdPartyAccount.setUsername(data.getUsername());
                thirdPartyAccount.setPassword(data.getPassword());
                //获取剩余点数
                long startRemindTime=System.currentTimeMillis();
                ThirdPartyAccountStruct synchronizeResult = chaosService.synchronization(thirdPartyAccount);
                long getRemindUseTime=System.currentTimeMillis()-startRemindTime;
                logger.info("get reminds in CompanyService Use time============== "+getRemindUseTime);
                if(synchronizeResult != null && synchronizeResult.getStatus() == 0) {
                    BindAccountStruct  thirdPartyAccount1 = new BindAccountStruct();
                    thirdPartyAccount1.setBinding(1);
                    thirdPartyAccount1.setChannel((byte) data.getChannel());
                    thirdPartyAccount1.setCompany_id(id);
                    thirdPartyAccount1.setMember_name(data.getMembername());
                    thirdPartyAccount1.setPassword(data.getPassword());
                    thirdPartyAccount1.setUsername(data.getUsername());
                    thirdPartyAccount1.setRemainNum(synchronizeResult.getRemainNum());
                    thirdPartyAccount1.setRemainProfileNum(synchronizeResult.getRemainProfileNum());
                    //更新第三方帐号信息
                    long startUpdateTime=System.currentTimeMillis();
                    Response response = hraccountDao.upsertThirdPartyAccount(thirdPartyAccount1);
                    long updateUseTime=System.currentTimeMillis() -startUpdateTime;
                    logger.info("update ThirdPartyAccount in CompanyService use time"+updateUseTime);
                    if(response.getStatus() == 0) {
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("remain_num", synchronizeResult.getRemainNum());
                        result.put("remain_profile_num", synchronizeResult.getRemainProfileNum());
                        result.put("sync_time", (new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                        return ResultMessage.SUCCESS.toResponse(result);
                    } else {
                        return ResultMessage.THIRD_PARTY_ACCOUNT_SYNC_FAILED.toResponse();
                    }
                } else {
                    return ResultMessage.THIRD_PARTY_ACCOUNT_SYNC_FAILED.toResponse();
                }
            } else {
                return ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
        } catch (TException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResultMessage.PROGRAM_EXCEPTION.toResponse();
        } finally {
            //do nothing
            long methodUseTime=System.currentTimeMillis()-startMethodTime;
            logger.info("synchronizeThirdpartyAccount method in CompanyService use time ============"+methodUseTime);
        }
    }

    /**
     * 判断是否有权限发布职位
     * @param companyId 公司编号
     * @param channel 渠道号
     * @return
     */
    public Response ifSynchronizePosition(int companyId, int channel) {
        Response response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("company_id", String.valueOf(companyId));
        qu.addEqualFilter("channel", String.valueOf(channel));
        try {
            ThirdPartAccountData data = hraccountDao.getThirdPartyAccount(qu);
            if(data.getId() == 0 || data.getBinding() != 1) {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
            if(data.getRemain_num() == 0) {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_HAVE_NO_REMAIN_NUM.toResponse();
            }
            if(data.getId() > 0 && data.binding == 1 && data.getRemain_num() > 0) {
                response = ResultMessage.SUCCESS.toResponse();
            } else {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
        } catch (TException e) {
            e.printStackTrace();
            response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return response;
    }

}
