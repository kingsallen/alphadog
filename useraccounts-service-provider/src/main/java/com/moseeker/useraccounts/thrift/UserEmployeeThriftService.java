package com.moseeker.useraccounts.thrift;

import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class UserEmployeeThriftService implements UserEmployeeService.Iface {

    @Autowired
    com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl employeeService;

    @Autowired
    private EmployeeEntity employeeEntity;


    @Override
    public Response getUserEmployee(CommonQuery query) throws TException {
        return employeeService.getUserEmployee(query);
    }

    @Override
    public Response getUserEmployees(CommonQuery query) throws TException {
        return employeeService.getUserEmployees(query);
    }

    @Override
    public Response delUserEmployee(CommonQuery query) throws TException {
        return employeeService.delUserEmployee(query);
    }


    @Override
    public Response postPutUserEmployeeBatch(List<UserEmployeeStruct> update) throws TException {
        return employeeService.postPutUserEmployeeBatch(update);
    }

    @Override
    public boolean isEmployee(int userId, int companyId) throws BIZException, TException {
        return employeeEntity.isEmployee(userId, companyId);
    }
}
