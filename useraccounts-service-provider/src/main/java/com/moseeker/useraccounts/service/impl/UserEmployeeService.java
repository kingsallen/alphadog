package com.moseeker.useraccounts.service.impl;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserEmployeeDao;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class  UserEmployeeService {

    UserEmployeeDao.Iface userEmployeeDao = ServiceManager.SERVICEMANAGER.getService(UserEmployeeDao.Iface.class);

    public Response getUserEmployee(CommonQuery query) throws TException {
        return userEmployeeDao.getResource(query);
    }

    public Response getUserEmployees(CommonQuery query) throws TException {
        return userEmployeeDao.getResources(query);
    }

    public Response delUserEmployee(CommonQuery query) throws TException {
        return userEmployeeDao.delResource(query);
    }

    public Response postPutUserEmployeeBatch(List<UserEmployeeStruct> update) throws TException {
        return userEmployeeDao.postPutUserEmployeeBatch(update);
    }
}
