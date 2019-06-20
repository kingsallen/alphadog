package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.useraccounts.service.EmployeeBinder;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service("auth_method_customfield")
public class EmployeeBindByCustomfield extends EmployeeBinder {

    private ThreadLocal<UserEmployeeDO> employeeThreadLocal = new ThreadLocal<>();

    @Override
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        super.paramCheck(bindingParams, certConf);

        UserEmployeeDO userEmployeeDO = employeeDao.fetchUnActiveEmployeeByCustom(bindingParams.getCompanyId(),
                bindingParams.getName(), bindingParams.getCustomField());
        employeeThreadLocal.set(userEmployeeDO);
        if (employeeThreadLocal.get() == null || employeeThreadLocal.get().getId() == 0) {
            throw new RuntimeException("员工认证信息不正确");
        } else if (employeeThreadLocal.get().getActivation() == 0) {
            throw new RuntimeException("该员工已绑定");
        }
    }

    @Override
    protected UserEmployeeDO createEmployee(BindingParams bindingParams) {
        UserEmployeeDO userEmployeeDO = employeeThreadLocal.get();
        log.info("EmployeeBindByCustomfield createEmployee userEmployeeDO:{}", userEmployeeDO);
        log.info("EmployeeBindByCustomfield createEmployee bindingParams:{}", bindingParams);
        if (employeeThreadLocal.get().getSysuserId() == 0) { // sysuserId =  0 说明员工信息是批量上传的未设置user_id
            if (userEmployeeDOThreadLocal.get() != null && userEmployeeDOThreadLocal.get().getId() != 0) {
                userEmployeeDO = userEmployeeDOThreadLocal.get();
            } else {
                userEmployeeDO = employeeThreadLocal.get();
            }
        } else if (employeeThreadLocal.get().getSysuserId() == bindingParams.getUserId()) {
            if (userEmployeeDOThreadLocal.get() != null && userEmployeeDOThreadLocal.get().getId() != 0) {
                userEmployeeDO = userEmployeeDOThreadLocal.get();
            }
        } else if(employeeThreadLocal.get().getSysuserId() != bindingParams.getUserId()
                && employeeThreadLocal.get().getActivation() == EmployeeActiveState.Cancel.getState()) {
            userEmployeeDO = userEmployeeDOThreadLocal.get();
            userEmployeeDO.setSysuserId(bindingParams.getUserId());
            log.info("取消认证的自定义信息可以被其他用户使用!");

        } else {  // 说明 employee.user_id != bindingParams.user_id 用户提供的信息与员工信息不匹配
            throw new RuntimeException("员工认证信息不匹配");
        }
        userEmployeeDO.setCompanyId(bindingParams.getCompanyId());
        userEmployeeDO.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
        userEmployeeDO.setSysuserId(bindingParams.getUserId());
        userEmployeeDO.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), userEmployeeDO.getCname()));
        userEmployeeDO.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), userEmployeeDO.getMobile()));
        userEmployeeDO.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), userEmployeeDO.getEmail()));
        userEmployeeDO.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
        userEmployeeDO.setAuthMethod((byte)bindingParams.getType().getValue());
        userEmployeeDO.setActivation((byte)0);
        userEmployeeDO.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setCustomField(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getCustomField(), userEmployeeDO.getCustomField()));
        if (bindingParams.getCustomFieldValues() != null && bindingParams.getCustomFieldValues().size() > 0) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(bindingParams.getCustomFieldValues());
            userEmployeeDO.setCustomFieldValues(jsonArray.toJSONString());
        }
        log.info("EmployeeBindByCustomfield createEmployee customFieldValues:{}", userEmployeeDO.getCustomFieldValues());
        return userEmployeeDO;
    }
}
