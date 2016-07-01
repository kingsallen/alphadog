package com.moseeker.useraccounts.service.impl;

import java.util.HashMap;

import org.apache.thrift.TException;
import org.jooq.types.UByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.sms.SmsSender;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.DownloadReport;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.useraccounts.dao.UserHrAccountDao;

/**
 * HR账号服务
 * <p>
 *
 * Created by zzh on 16/5/31.
 */
@Service
public class UserHrAccountServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String REDIS_KEY_HR_SMS_SIGNUP = "HR_SMS_SIGNUP";

    private RedisClient redisClient = RedisClientFactory.getCacheClient();

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    /**
     * HR在下载行业报告是注册
     *
     * @param mobile 手机号
     * @param code 验证码
     * @param source 系统区分
     *               1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
     * */
    @Override
    public Response sendMobileVerifiyCode(String mobile, String code, int source) throws TException {
        try {
            // HR账号验证码校验
            Response response = validateSendMobileVertifyCode(mobile, code, source);
            if (response.status > 0){
                return response;
            }

            // 发送HR注册的验证码
            return ResponseUtils.success(SmsSender.sendHrMobileVertfyCode(mobile, REDIS_KEY_HR_SMS_SIGNUP, source));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 添加HR记录
     *
     * @param userHrAccount hr用户实体
     * @param code 验证码
     *
     * */
    @Override
    public Response postResource(DownloadReport downloadReport) throws TException {
        try {
        	ValidateUtil vu = new ValidateUtil();
        	vu.addRequiredStringValidate("手机号码", downloadReport.getMobile(), null, null);
        	vu.addRequiredStringValidate("验证码", downloadReport.getCode(), null, null);
            vu.addRequiredStringValidate("公司名称", downloadReport.getCompany_name(), null, null);
            String message = vu.validate();
            if(StringUtils.isNullOrEmpty(message)) {
            	
            	 String redisCode = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_HR_SMS_SIGNUP,
                         Constant.HR_ACCOUNT_SIGNUP_SOURCE_ARRAY[downloadReport.getSource()-1], downloadReport.getMobile());
                 if(!downloadReport.getCode().equals(redisCode)){
                     return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
                 }
            	
            	 String[] passwordArray = this.genPassword(null);
            	  // 密码生成及加密, 谨慎使用, 防止密码泄露, 有个漏洞, source不是官网以外的时候, 会生成密码, 无法告知
                 UserHrAccountRecord userHrAccountRecord = new UserHrAccountRecord();
                 if(downloadReport.isSetSource()) {
                 	 userHrAccountRecord.setSource(downloadReport.getSource());
                 }
                 userHrAccountRecord.setMobile(downloadReport.getMobile());
                 userHrAccountRecord.setPassword(passwordArray[1]);
                 userHrAccountRecord.setAccountType(2);
                 userHrAccountRecord.setUsername(downloadReport.getMobile());
                 if(downloadReport.isSetRegister_ip()) {
                	 userHrAccountRecord.setRegisterIp(downloadReport.getRegister_ip());
                 }
                 if(downloadReport.isSetLast_login_ip()) {
                	 userHrAccountRecord.setLastLoginIp(downloadReport.getLast_login_ip());
                 }
                 HrCompanyRecord companyRecord = new HrCompanyRecord();
                 companyRecord.setType(UByte.valueOf(1));
                 if(downloadReport.isSetCompany_name()) {
                	 companyRecord.setName(downloadReport.getName());
                 }
                 int result = userHrAccountDao.createHRAccount(userHrAccountRecord, companyRecord);
                 
                 if (result > 0 && downloadReport.getSource() == Constant.HR_ACCOUNT_SIGNUP_SOURCE_WWW ) {
                	 SmsSender.sendHrSmsSignUpForDownloadIndustryReport(downloadReport.getMobile(), passwordArray[0]);
                 }
                 if(result > 0) {
                	 return ResponseUtils.success(new HashMap<String, Object>(){
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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("postUserFavoritePosition UserFavPositionRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 更新HR账户信息
     *
     * @param userHrAccount 用户实体
     *
     * */
    @Override
    public Response putResource(UserHrAccount userHrAccount) throws TException {
        try {
            // 必填项验证
            Response response = validatePutResource(userHrAccount);
            if (response.status > 0){
                return response;
            }

            // 密码加密
            userHrAccount.setPassword(MD5Util.encryptSHA(userHrAccount.password));

            // 添加HR用户
            UserHrAccountRecord userHrAccountRecord = (UserHrAccountRecord) BeanUtils.structToDB(userHrAccount,
                    UserHrAccountRecord.class);

            int userHrAccountId = userHrAccountDao.putResource(userHrAccountRecord);
            if (userHrAccountId > 0) {
                return ResponseUtils.success(new HashMap<String, Object>(){
                    {
                        put("userHrAccountId", userHrAccountId);
                    }
                }); // 返回 userFavoritePositionId
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("putResource UserHrAccountRecord error: ", e);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    /**
     * 添加HR账号验证
     *
     * @param userHrAccount hr用户实体
     *
     * */
    private Response validatePostResource(UserHrAccount userHrAccount, String code){

        Response response = new Response(0, "ok");
        if(userHrAccount == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if(userHrAccount.mobile == null || userHrAccount.mobile.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        if(userHrAccount.source <= 0 || userHrAccount.source > 5 ){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE);
        }
        if(code == null || code.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "code"));
        }

        String redisCode = redisClient.get(Constant.APPID_ALPHADOG, REDIS_KEY_HR_SMS_SIGNUP,
                Constant.HR_ACCOUNT_SIGNUP_SOURCE_ARRAY[userHrAccount.source-1], userHrAccount.mobile);
        // 验证码无法验证
        if(!code.equals(redisCode)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.INVALID_SMS_CODE);
        }

        return response;
    }

    /**
     * 更新HR账号验证
     *
     * @param userHrAccount hr用户实体
     *
     * */
    private Response validatePutResource(UserHrAccount userHrAccount){

        Response response = new Response(0, "ok");
        if(userHrAccount == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        if(userHrAccount.id <= 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "id"));
        }
        if(userHrAccount.mobile == null || userHrAccount.mobile.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        if(userHrAccount.password == null || userHrAccount.password.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "password"));
        }
        if(userHrAccount.source <= 0 || userHrAccount.source > 5 ){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE);
        }
        return response;
    }

    /**
     * HR账号验证码校验
     * <p>
     *
     * */
    private Response validateSendMobileVertifyCode(String mobile, String code, int source){

        Response response = new Response(0, "ok");
        if(mobile == null || mobile.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "mobile"));
        }
        if(code == null || code.equals("")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "code"));
        }
        if(source <= 0 || source > 5 ){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE);
        }
        return response;
    }

    /**
     * 判断密码为空时生成密码
     * 密码加密
     * <p>
     *
     * @return String[] 0:原始密码 1:加密后密码
     *
     * */
    private String[] genPassword(String passowrd){
        String[] passwordArray = new String[2];
        String plainPassword = "8E69c6";
        if(passowrd == null || passowrd.trim().equals("")){
            plainPassword = StringUtils.getRandomString(6);
        }else{
            plainPassword = passowrd;
        }

        passwordArray[0] = plainPassword;
        passwordArray[1] = MD5Util.encryptSHA(MD5Util.md5(plainPassword));

        return passwordArray;
    }
}
