package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@CounterIface
public class McdUatServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserEmployeeDao userEmployeeDao;



    public UserEmployee getSingleUserEmployee(int userId) {
        UserEmployee result = userEmployeeDao.getSingleEmployeeByUserId(userId);


        return result;

    }


}
