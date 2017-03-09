package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserEmployeeDao;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class UserEmployeeService implements com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService.Iface {

    UserEmployeeDao.Iface userEmployeeDao = ServiceManager.SERVICEMANAGER.getService(UserEmployeeDao.Iface.class);

    @Override
    public Response getUserEmployee(CommonQuery query) throws TException {
        return userEmployeeDao.getResource(query);
    }

    @Override
    public Response delUserEmployee(UserEmployeeStruct userEmployee) throws TException {
        return userEmployeeDao.delResource(userEmployee);
    }

    @Override
    public Response postPutUserEmployeeBatch(List<UserEmployeeStruct> update) throws TException {
        return userEmployeeDao.postPutResources(update);
    }
}
