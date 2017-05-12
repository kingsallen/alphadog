package com.moseeker.baseorm.Thriftservice;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 12/05/2017.
 */
public class UserDBDaoThriftServiceTest {

    com.moseeker.thrift.gen.dao.service.UserDBDao.Iface userDao = ServiceManager.SERVICEMANAGER
            .getService(com.moseeker.thrift.gen.dao.service.UserDBDao.Iface.class);

    //@Test
    public void saveUser() throws Exception {
        UserUserDO userUserDO = new UserUserDO();
        userUserDO.setUsername("testtesttest");
        userUserDO.setPassword("");
        userUserDO.setSource((byte)100);
        userUserDO.setMobile(11111111);
        userDao.saveUser(userUserDO);
    }

}