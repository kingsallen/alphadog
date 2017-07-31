package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.useraccounts.service.EmployeeBinder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service("auth_method_email")
public class EmployeeBindByEmail extends EmployeeBinder{

    private static final Logger log = LoggerFactory.getLogger(EmployeeBindByEmail.class);

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Override
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        // 邮箱校验后缀
        if (JSONObject.parseArray(certConf.getEmailSuffix()).stream().noneMatch(m -> bindingParams.getEmail()
                .endsWith(String.valueOf(m)))) {
           throw new RuntimeException("员工认证信息不正确");
        }

        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where(new Condition("company_id", employeeEntity.getCompanyIds(bindingParams.getCompanyId()), ValueOp.IN)).and("email", bindingParams.getEmail())
                .and("disable", "0");

        // 判断该邮箱现在已被占用 或 正在被人认证
        List<UserEmployeeDO> userEmployees = employeeDao.getDatas(query.buildQuery());
        userEmployees = userEmployees.stream().filter(e -> e.getSysuserId() != bindingParams.getUserId() && e.getId() > 0).collect(Collectors.toList());
        if (userEmployees.stream().anyMatch(e -> e.getActivation() == 0 || StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, e.getActivationCode())))) {
            log.info("邮箱:{} 已被占用", bindingParams.getEmail());
            throw new RuntimeException("该邮箱已被认证\n请使用其他邮箱");
        }

        if (userEmployeeDOThreadLocal.get() != null && StringUtils.isNotNullOrEmpty(userEmployeeDOThreadLocal.get().getActivationCode())) {
            if (StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, userEmployeeDOThreadLocal.get().getActivationCode()))) {
                throw new RuntimeException("已发送过邮件到指定邮箱，24h内请勿重复该操作");
            }
        }
    }

    @Override
    protected Result doneBind(BindingParams bindingParams, int employeeId) throws TException {
        Result response = new Result();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.clear();
        query.where("id", String.valueOf(bindingParams.getCompanyId()));
        HrCompanyDO companyDO = companyDao.getData(query.buildQuery());
        query.clear();
        query.where("company_id", String.valueOf(bindingParams.getCompanyId()));
        HrWxWechatDO hrwechatResult = hrWxWechatDao.getData(query.buildQuery());
        if (companyDO != null && companyDO.getId() != 0 && hrwechatResult != null && hrwechatResult.getId() != 0) {
            // 激活码(MD5)： employee-email-timestamp
            String activationCode = MD5Util.encryptSHA(userEmployeeDOThreadLocal.get().getId()+"-"+bindingParams.getEmail()+"-"+System.currentTimeMillis());
            Map<String, String> mesBody = new HashMap<>();
            mesBody.put("#company_logo#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getLogo(), ""));
            mesBody.put("#employee_name#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(userEmployeeDOThreadLocal.get().getCname(), userAccountEntity.genUsername(userEmployeeDOThreadLocal.get().getSysuserId())));
            mesBody.put("#company_abbr#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getAbbreviation(), ""));
            mesBody.put("#official_account_name#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(hrwechatResult.getName(), ""));
            mesBody.put("#official_account_qrcode#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(hrwechatResult.getQrcode(), ""));
            mesBody.put("#date_today#",  LocalDate.now().toString());
            mesBody.put("#auth_url#", ConfigPropertiesUtil.getInstance().get("platform.url", String.class).concat("m/employee/bindemail?activation_code=").concat(activationCode).concat("&wechat_signature=").concat(hrwechatResult.getSignature()));
            // 发件人信息
            ConfigPropertiesUtil propertiesUtil = ConfigPropertiesUtil.getInstance();
            String senderName = propertiesUtil.get("email.verify.sendName", String.class);
            String subject = "请验证邮箱完成员工认证-".concat(org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getAbbreviation(), ""));
            String senderDisplay = org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getAbbreviation(), "");
            // 发送认证邮件
            Response mailResponse = mqService.sendAuthEMail(mesBody, Constant.EVENT_TYPE_EMPLOYEE_AUTH, bindingParams.getEmail(), subject, senderName, senderDisplay);
            // 邮件发送成功
            if (mailResponse.getStatus() == 0) {
                String redStr = client.set(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, activationCode, JSONObject.toJSONString(bindingParams));
                log.info("set redis result: ", redStr);
                // 修改用户邮箱
                userEmployeeDOThreadLocal.get().setEmail(bindingParams.getEmail());
                userEmployeeDOThreadLocal.get().setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                userEmployeeDOThreadLocal.get().setActivationCode(activationCode);
                employeeDao.updateData(userEmployeeDOThreadLocal.get());
                response.setSuccess(true);
                response.setMessage("发送激活邮件成功");
            } else {
                response.setMessage("发送激活邮件失败");
            }
        }
        log.info("BindingParams response: {}", response);
        return response;
    }

    public Result emailActivation(String activationCode) throws TException {
        log.info("emailActivation param: activationCode={}", activationCode);
        Result response = new Result();
        response.setSuccess(false);
        response.setMessage("激活信息不正确");
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("activation_code", activationCode);
        UserEmployeeDO userEmployeeDO = employeeDao.getData(query.buildQuery());
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0) {
            String value = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, activationCode);
            if (StringUtils.isNotNullOrEmpty(value)) {
                BindingParams bindingParams = JSONObject.parseObject(value, BindingParams.class);
                // 判断当前公司是否还支持邮箱认证
                query.clear();
                query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("disable", String.valueOf(0));
                HrEmployeeCertConfDO certConf = hrEmployeeCertConfDao.getData(query.buildQuery());
                if(certConf == null || certConf.getCompanyId() == 0 || certConf.getAuthMode() == 2 || certConf.getAuthMode() == 3 || certConf.getAuthMode() == 5) {
                    log.warn("公司 company_id = {}, 暂不支持邮箱认证，员工认证邮箱激活失败", bindingParams.getCompanyId());
                    client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, activationCode);
                } else {
                    response = super.doneBind(bindingParams, userEmployeeDO.getId());
                    response.setEmployeeId(userEmployeeDO.getId());
                }
            }
        }
        log.info("emailActivation response: {}", response);
        return response;
    }
}