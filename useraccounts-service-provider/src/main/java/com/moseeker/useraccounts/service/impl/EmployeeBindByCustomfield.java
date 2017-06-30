package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.useraccounts.service.EmployeeBinder;
import org.springframework.stereotype.Service;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service("auth_method_customfield")
public class EmployeeBindByCustomfield extends EmployeeBinder {

    private UserEmployeeDO employee;

    @Override
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", String.valueOf(bindingParams.getCompanyId()))
                .and("cname", bindingParams.getName())
                .and("custom_field", bindingParams.getCustomField())
                .and("disable", "0");

        employee = employeeDao.getData(query.buildQuery());
        if (employee == null || employee.getId() == 0) {
            throw new RuntimeException("员工认证信息不正确");
        } else if (employee.getActivation() == 0) {
            throw new RuntimeException("该员工已绑定");
        }
    }

    @Override
    protected int createEmployee(BindingParams bindingParams) {
        if (employee.getSysuserId() == 0) { // sysuserId =  0 说明员工信息是批量上传的未设置user_id
            if (userEmployee != null && userEmployee.getId() != 0) {
                return userEmployee.getId();
            } else {
                employee.setSysuserId(bindingParams.getUserId());
                int rownum = employeeDao.updateData(employee);
                if (rownum > 0){
                    return employee.getId();
                } else {
                    throw new RuntimeException("fail");
                }
            }
        } else if (employee.getSysuserId() == bindingParams.getUserId()) {
            if (userEmployee != null && userEmployee.getId() != 0) {
                return userEmployee.getId();
            }
        } else {  // 说明 employee.user_id != bindingParams.user_id 用户提供的信息与员工信息不匹配
            throw new RuntimeException("员工认证信息不匹配");
        }
        return userEmployee.getId();
    }
}
