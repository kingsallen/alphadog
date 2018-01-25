package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.constant.AwardEvent;
import com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class UserEmployeeThriftService implements UserEmployeeService.Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserEmployeeServiceImpl employeeService;

    @Autowired
    private EmployeeEntity employeeEntity;


    @Override
    public Response getUserEmployee(CommonQuery query) throws TException {
        try {
            return employeeService.getUserEmployee(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getUserEmployees(CommonQuery query) throws TException {
        try {
            return employeeService.getUserEmployees(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response delUserEmployee(CommonQuery query) throws TException {
        try {
            return employeeService.delUserEmployee(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    @Override
    public Response postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws TException {
        try {
            return employeeService.postPutUserEmployeeBatch(batchForm);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean isEmployee(int userId, int companyId) throws BIZException, TException {
        try {
            return employeeEntity.isEmployee(userId, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response putUserEmployee(UserEmployeeStruct userEmployee) throws BIZException, TException {
        try {
            return employeeService.putResource(userEmployee);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public void addEmployeeAward(List<Integer> applicationIdList, int eventType) throws BIZException, TException {
        /** 初始化业务编号 */
        AwardEvent awardEvent = AwardEvent.initFromSate(eventType);
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredOneValidate("申请编号", applicationIdList, null, null);
        vu.addRequiredValidate("积分事件", awardEvent, null, null);
        String result = vu.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ExceptionConvertUtil.convertCommonException(UserAccountException.validateFailed(result));
        }
        employeeService.addEmployeeAward(applicationIdList, awardEvent);
    }
}
