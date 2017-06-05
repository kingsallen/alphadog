package com.moseeker.baseorm.Thriftservice;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import com.moseeker.thrift.gen.position.struct.Position;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 12/05/2017.
 */
public class UserDBDaoThriftServiceTest {

//    com.moseeker.thrift.gen.dao.service.UserDBDao.Iface userDao = ServiceManager.SERVICEMANAGER
//            .getService(com.moseeker.thrift.gen.dao.service.UserDBDao.Iface.class);
//
//    //@Test
//    public void saveUser() throws Exception {
//        UserUserDO userUserDO = new UserUserDO();
//        userUserDO.setUsername("testtesttest");
//        userUserDO.setPassword("");
//        userUserDO.setSource((byte)100);
//        userUserDO.setMobile(11111111);
//        userDao.saveUser(userUserDO);
//    }

    PositionDao.Iface positionDao;

    @Before
    public void be(){
       positionDao = ServiceManager.SERVICEMANAGER.getService(PositionDao.Iface.class);
    }

//    @Test
//    public void testPosition() throws TException {
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("id","1000655");
//        Position position = positionDao.getPositionWithCityCode(queryUtil);
//        System.out.println(JSON.toJSONString(position));
//    }
//
//    @Test
//    public void testPosition2() throws TException {
//        QueryUtil queryUtil = new QueryUtil();
//        queryUtil.addEqualFilter("id","1000655");
//        Position position = positionDao.getPosition(queryUtil);
//        System.out.println(JSON.toJSONString(position));
//    }

}