package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrSearchConditionDao;
import com.moseeker.baseorm.dao.hrdb.HrTalentpoolDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserSearchConditionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTalentpoolDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.constant.ResultMessage;
import com.moseeker.useraccounts.service.thirdpartyaccount.ThirdPartyAccountSynctor;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    private UserSearchConditionDao searchConditionDao;

    @Autowired
    private ThirdPartyAccountSynctor thirdPartyAccountSynctor;

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
     * 第三方账号绑定
     *
     * @param hrId
     * @param account
     * @return
     */
    public HrThirdPartyAccountDO bindThirdAccount(int hrId, HrThirdPartyAccountDO account) throws Exception {
        logger.info("-------bindThirdAccount--------");
        // 判断是否需要进行帐号绑定
        Query qu = new Query.QueryBuilder().where("id", String.valueOf(hrId)).buildQuery();

        UserHrAccountDO userHrAccount = userHrAccountDao.getData(qu);

        if (userHrAccount == null || userHrAccount.getActivation() != Byte.valueOf("1") || userHrAccount.getDisable() != 1) {
            //没有找到该hr账号
            throw new BIZException(-1, "无效的HR帐号");
        }

        logger.info("thirdPartyAccount: {}", userHrAccount);
        account.setCompanyId(userHrAccount.getCompanyId());

        int allowStatus = allowBind(userHrAccount, account);

        logger.info("bindThirdAccount allowStatus:{}", allowStatus);

        HrThirdPartyAccountDO result = thirdPartyAccountSynctor.bindThirdPartyAccount(hrId, account, true);


        return result;
    }

    /**
     * 是否允许执行绑定
     */
    @CounterIface
    public int allowBind(UserHrAccountDO hrAccount, HrThirdPartyAccountDO thirdPartyAccount) throws Exception {

        //主账号或者没有绑定第三方账号，检查公司下该渠道已经绑定过相同的第三方账号
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", thirdPartyAccount.getCompanyId());
        qu.and("channel", String.valueOf(thirdPartyAccount.getChannel()));
        qu.and("username", thirdPartyAccount.getUsername());
        qu.and(new Condition("binding", Arrays.asList(1, 2), ValueOp.IN));//绑定中或者已经绑定
        ThirdPartAccountData data = hrThirdPartyAccountDao.getData(qu.buildQuery(), ThirdPartAccountData.class);

        //数据库中username是不区分大小写的，如果大小写不同，那么认为不是一个账号
        if (data != null && !thirdPartyAccount.getUsername().equals(data.username)) {
            data = null;
        }

        if (data == null || data.getId() == 0) {
            //检查该用户是否绑定了其它相同渠道的账号
            HrThirdPartyAccountDO thirdPartAccount = hrThirdPartyAccountDao.getThirdPartyAccountByUserId((int) hrAccount.getId(), thirdPartyAccount.getChannel());
            if (thirdPartAccount != null && thirdPartAccount.getId() > 0) {
                if (hrAccount.getAccountType() == 0) {
                    //如果主账号已经绑定该渠道第三方账号，那么绑定人为空,并允许绑定
                    return 1;
                } else {
                    //已经绑定该渠道第三方账号，并且不是主账号，那么不允许绑定
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_BINDING_LIMIT);
                }
            } else {
                return 0;
            }
        } else {
            //公司下已经有人绑定了这个第三方账号，则这个公司谁都不能再绑定这个账号了
            if (data.getBinding() == 1) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
            } else if (data.getBinding() == 2) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_BINDING);
            } else {
                return 0;
            }
        }
    }


    /**
     * 同步第三方账号信息
     *
     * @param id
     * @return
     */

    public HrThirdPartyAccountDO synchronizeThirdpartyAccount(int id) throws Exception {
        //查找第三方帐号
        Query qu = new Query.QueryBuilder().where("id", id).buildQuery();
        HrThirdPartyAccountDO hrThirdPartyAccount = hrThirdPartyAccountDao.getData(qu);

        if (hrThirdPartyAccount == null || hrThirdPartyAccount.getBinding() == 0) {
            throw new BIZException(-1, "无效的第三方帐号");
        }
        //如果是绑定状态，则进行
        hrThirdPartyAccount = thirdPartyAccountSynctor.syncThirdPartyAccount(hrThirdPartyAccount, true);

        //刷新成功
        return hrThirdPartyAccount;
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

    /**
     * 判断是否有权限发布职位
     *
     * @param companyId 公司编号
     * @param channel   渠道号
     * @return
     */
    public Response ifSynchronizePosition(int companyId, int channel) {
        Response response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", String.valueOf(companyId)).and("channel", String.valueOf(channel));
        try {
            ThirdPartAccountData data = hrThirdPartyAccountDao.getData(qu.buildQuery(), ThirdPartAccountData.class);
            if (data.getId() == 0 || data.getBinding() != 1) {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
            if (data.getRemain_num() == 0) {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_HAVE_NO_REMAIN_NUM.toResponse();
            }
            if (data.getId() > 0 && data.binding == 1 && data.getRemain_num() > 0) {
                response = ResultMessage.SUCCESS.toResponse();
            } else {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return response;
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
}
