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
    public Result bind(BindingParams bindingParams,Integer bingSource) {
    logger.info("bind param: BindingParams={}", bindingParams);
        Result response = new Result();
        try {

            userEmployeeDOThreadLocal.set(employeeEntity.getCompanyEmployee(bindingParams.getUserId(),
                    bindingParams.getCompanyId()));
            if (userEmployeeDOThreadLocal.get() != null && userEmployeeDOThreadLocal.get().getId() > 0
                    && userEmployeeDOThreadLocal.get().getActivation() == 0) {
                throw new RuntimeException("该员工已绑定");
            }
            UserEmployeeDO userEmployee = createEmployee(bindingParams);
            response = doneBind(userEmployee,bingSource);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
    logger.info("bind response: {}", response);
        return response;
    }


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
        userEmployeeDO.setSource(11);//设置用户注册来源于joywork
        userEmployeeDO.setActivation((byte) 0);
        userEmployeeDO.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDO.setCustomField(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getCustomField(), userEmployeeDO.getCustomField()));
        return userEmployeeDO;
    }


    @Override
    protected Result doneBind(UserEmployeeDO useremployee, int bindSource) throws TException, InvalidArgumentException {
        Result result = new Result();
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

            //2查询新用户的预埋数据如果是则删除。
            //并且重新插入一条新的数据
            if (StringUtils.isNotNullOrEmpty(getActiveEmployee.getCustomField())){
                employeeDao.deleteEmptyCustomFiledBySysUuer(useremployee.getCustomField(), useremployee.getSysuserId());

                employeeDao.insertActiveUserInfo(useremployee.getSysuserId(),
                        useremployee.getCompanyId(),
                        useremployee.getCustomField(),
                        useremployee.getMobile(),
                        useremployee.getEmail(),
                        useremployee.getCname());
                result.setSuccess(true);
                result.setMessage("success");
            }
        }

        if (getActiveEmployee.getActivation() > 0) {
            employeeDao.updateUnActiveUserInfo(useremployee.getSysuserId(),
                    useremployee.getCompanyId(),
                    useremployee.getCustomField(),
                    useremployee.getMobile(),
                    useremployee.getEmail(),
                    useremployee.getCname());
            result.setSuccess(true);
            result.setMessage("success");
        }
        result.setSuccess(true);
        result.setMessage("success");

        return result;


    }


}
