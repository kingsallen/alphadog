package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.useraccounts.constant.EmployeeDBLength;
import com.moseeker.useraccounts.constant.EmployeeSource;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.EmployeeBinder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Service("auth_method_McdUatSysUserId")
public class EmployeeBindAndUpdateByMcdUatSysUserId extends EmployeeBinder {

    private final Logger logger = LoggerFactory.getLogger(EmployeeBindAndUpdateByMcdUatSysUserId.class);

    private ThreadLocal<UserEmployeeDO> employeeThreadLocal = new ThreadLocal<>();

    @Override
    protected void validate(BindingParams bindingParams) {
        logger.info("EmployeeBindAndUpdateByMcdUatSysUserId validate bindingParams:{}", bindingParams);
        UserEmployeeDO userEmployeeDO = fetchEmployeeByEmailOrCustomField(bindingParams.getEmail(),
                bindingParams.getCustomField(), bindingParams.getCompanyId());
        logger.info("EmployeeBindAndUpdateByMcdUatSysUserId validate userEmployeeDO:{}", userEmployeeDO);
        if (userEmployeeDO != null) {
            throw UserAccountException.EMPLOYEE_VERIFICATION_INVALID;
        }
        userEmployeeDO = employeeEntity.getCompanyEmployee(bindingParams.getUserId(), bindingParams.getCompanyId());
        employeeThreadLocal.set(userEmployeeDO);

    }

    @Override
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("公司编号",bindingParams.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addStringLengthValidate("工号", bindingParams.getCustomField(), 1, EmployeeDBLength.CUSTOM_FIELD.getLengthLimit());
        validateUtil.addStringLengthValidate("邮箱", bindingParams.getEmail(), 1, EmployeeDBLength.EMAIL.getLengthLimit());
        validateUtil.addStringLengthValidate("手机号码", bindingParams.getMobile(), 1, EmployeeDBLength.MOBILE.getLengthLimit());
        validateUtil.addStringLengthValidate("员工姓名", bindingParams.getName(), 1, EmployeeDBLength.CNAME.getLengthLimit());
        String result = validateUtil.validate();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(result)) {
            throw UserAccountException.validateFailed(result);
        }
    }

    @Override
    protected UserEmployeeDO createEmployee(BindingParams bindingParams) {
        UserEmployeeDO userEmployeeDO = employeeThreadLocal.get() == null ? new UserEmployeeDO():employeeThreadLocal.get();

        userEmployeeDO.setCompanyId(bindingParams.getCompanyId());
        userEmployeeDO.setEmployeeid(
            org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
        userEmployeeDO.setSysuserId(bindingParams.getUserId());
        userEmployeeDO.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), userEmployeeDO.getCname()));
        userEmployeeDO.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), userEmployeeDO.getMobile()));
        if (org.apache.commons.lang.StringUtils.isNotBlank(bindingParams.getEmail())) {
            userEmployeeDO.setEmailIsvalid((byte) 1);
        }
        userEmployeeDO.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), userEmployeeDO.getEmail()));
        userEmployeeDO.setAuthMethod((byte) generateAuthMethod(bindingParams.getEmail()));
        //设置用户注册来源于joywork
        userEmployeeDO.setSource(EmployeeSource.Joywork.getValue());
        userEmployeeDO.setActivation((byte) 0);
        userEmployeeDO.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
        userEmployeeDO.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setCustomField(org.apache.commons.lang.StringUtils
            .defaultIfBlank(bindingParams.getCustomField(), userEmployeeDO.getCustomField()));
        return userEmployeeDO;
    }

    /**
     * 优先查找工号一直的未认证记录。
     * @param useremployee 员工信息
     * @return 非认证成功的员工记录
     */
    @Override
    protected UserEmployeeRecord fetchUnActiveEmployee(UserEmployeeDO useremployee) {
        UserEmployeeRecord record = employeeDao.fetchByCustomField(useremployee.getCustomField(),
                useremployee.getCompanyId());
        if (record != null) {
            if (record.getActivation() == EmployeeActiveState.Actived.getState()) {
                if (record.getSysuserId() != useremployee.getSysuserId()) {
                    throw UserAccountException.EMPLOYEE_VERIFICATION_INVALID;
                }
            }
            return record;
        } else {
            return super.fetchUnActiveEmployee(useremployee);
        }
    }

    @Override
    protected void updateInfo(UserEmployeeRecord unActiveEmployee, UserEmployeeDO useremployee, int employeeId, DateTime currentTime) {
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
        unActiveEmployee.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        if (useremployee.getAuthMethod() == 1 && unActiveEmployee.getBindingTime() == null) {
            employeeFirstRegister(employeeId, useremployee.getCompanyId(), currentTime.getMillis(), useremployee.getSysuserId());
        }
        employeeDao.updateRecord(unActiveEmployee);

        if (useremployee.getId() > 0 && useremployee.getId() != unActiveEmployee.getId()) {
            employeeDao.deleteData(useremployee);
            searchengineEntity.deleteEmployeeDO(new ArrayList<Integer>(){{add(useremployee.getId());}});
        }
    }

    /**
     * 查找email和工号下是否存在已经认证的员工信息
     * @param email 邮箱
     * @param customField 工号
     * @param companyId 公司信息
     * @return 员工信息
     */
    private UserEmployeeDO fetchEmployeeByEmailOrCustomField(String email, String customField, int companyId) {
        UserEmployeeDO userEmployeeDO = null;
        if (org.apache.commons.lang.StringUtils.isNotBlank(email)) {
            userEmployeeDO = employeeDao.fetchActivedByEmail(email, companyId);
        }
        if (userEmployeeDO == null && org.apache.commons.lang.StringUtils.isNotBlank(customField)) {
            userEmployeeDO = employeeDao.fetchActivedByCustomField(customField, companyId);
        }
        return userEmployeeDO;
    }

    /**
     * Joywork对接需要根据参数对应到仟寻支持的认证方式上。
     * 如果存在邮箱，则认为是邮箱认证；否则认为是工号认证
     * @param email
     * @return
     */
    private int generateAuthMethod(String email) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(email)) {
            return 0;
        } else {
            return 1;
        }
    }
}