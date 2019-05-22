package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.useraccounts.service.EmployeeBinder;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service("auth_method_McdUatSysUserId")
public class EmployeeBindAndUpdateByMcdUatSysUserId extends EmployeeBinder {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeBindAndUpdateByMcdUatSysUserId.class);
    private ThreadLocal<UserEmployeeDO> employeeThreadLocal = new ThreadLocal<>();

    @Override
    protected void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception {
        //目的：1.针对传入email和工号进行查重验证;
        employeeThreadLocal.set(employeeDao.findbyCustomFiledAndEmail(bindingParams.getCompanyId(), bindingParams.getEmail(), bindingParams.getCustomField()));
        if (employeeThreadLocal.get() != null) {
            ResponseUtils.fail(ConstantErrorCodeMessage.MCD_USER_EMAIL_AND_CUSTOM_FIELD);
            //throw new RuntimeException("员工认证信息不正确!");
        } else if (employeeThreadLocal.get().getActivation() == 0) {

            ResponseUtils.fail(ConstantErrorCodeMessage.MCD_USER_BIND_DONE);
        }

    }

    @Override
    protected UserEmployeeDO createEmployee(BindingParams bindingParams) {
        UserEmployeeDO userEmployeeDO = employeeThreadLocal.get();
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
        } else {  // 说明 employee.user_id != bindingParams.user_id 用户提供的信息与员工信息不匹配
            throw new RuntimeException("员工认证信息不匹配!");
        }
        userEmployeeDO.setCompanyId(bindingParams.getCompanyId());
        userEmployeeDO.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
        userEmployeeDO.setSysuserId(bindingParams.getUserId());
        userEmployeeDO.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), userEmployeeDO.getCname()));
        userEmployeeDO.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), userEmployeeDO.getMobile()));
        userEmployeeDO.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), userEmployeeDO.getEmail()));
        if (!(StringUtils.isNotNullOrEmpty(bindingParams.getCustomField())) && (!(StringUtils.isNotNullOrEmpty(bindingParams.getEmail())))) {
            //设置注册来源来自eamil
            userEmployeeDO.setAuthMethod((byte) 0);
        }
        if ((StringUtils.isNotNullOrEmpty(bindingParams.getCustomField())) && (!(StringUtils.isNotNullOrEmpty(bindingParams.getEmail())))) {
            //设置注册来源来自自定义认证
            userEmployeeDO.setAuthMethod((byte) 1);
        }
        if ((StringUtils.isNotNullOrEmpty(bindingParams.getCustomField())) && ((StringUtils.isNotNullOrEmpty(bindingParams.getEmail())))) {
            //设置注册来源来自自定义认证
            userEmployeeDO.setAuthMethod((byte) 0);
        }
        userEmployeeDO.setActivation((byte) 0);
        userEmployeeDO.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setCustomField(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getCustomField(), userEmployeeDO.getCustomField()));
        return userEmployeeDO;
    }


    @Override
    protected Result doneBind(UserEmployeeDO useremployee, int bindSource) throws TException, InvalidArgumentException {
        Result result = new Result();
        int source = 11;
        BindingParams bindingParams = new BindingParams();
        //1)查询activation的员工在否存在如果存在则做 Update操作把用户更新
        UserEmployeeRecord getActiveEmployee = employeeDao.getActiveEmployee(useremployee.getSysuserId(),
                useremployee.getCompanyId());
        if (getActiveEmployee.getActivation() == 0) {
            employeeDao.updateActiveUserInfo(useremployee.getSysuserId(),
                    useremployee.getCompanyId(),
                    useremployee.getCustomField(),
                    useremployee.getMobile(),
                    useremployee.getEmail(),
                    useremployee.getCname());
        }

        //2查询新用户的预埋数据如果是则删除。
        //没有更新的话就
        if (StringUtils.isNotNullOrEmpty(getActiveEmployee.getCustomField())){
            employeeDao.delete(useremployee.getSysuserId(),
                    useremployee.getCompanyId());
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(employeeThreadLocal.get().getCustomField())) {
            //查询sysuserid在该公司下;1)如果不在看custom_filed 是否有预埋数据，有数据则则把sysuserid更新为userid；
            logger.info("查询到用户工号的记录，并将用户的sysuerid 回填回去，并开始执行");
            //   employeeDao.addSysUserId(bindingParams.getCompanyId() , bindingParams.getUserId(),bindingParams.getCustomField());
        }


        return result;


    }


}
